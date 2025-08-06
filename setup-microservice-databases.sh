#!/bin/bash

# Quick setup for two separate PostgreSQL databases
# One for consumption service, one for customer service

set -e

AWS_REGION="us-east-1"
DB_PASSWORD="$1"

if [ -z "$DB_PASSWORD" ]; then
    echo "Usage: ./setup-microservice-databases.sh <database-password>"
    echo "Example: ./setup-microservice-databases.sh MySecurePassword123"
    exit 1
fi

echo "🗄️ Setting up separate databases for microservices..."

# Get default VPC info
DEFAULT_VPC=$(aws ec2 describe-vpcs --filters "Name=is-default,Values=true" --query "Vpcs[0].VpcId" --output text --region $AWS_REGION)
SUBNETS=$(aws ec2 describe-subnets --filters "Name=vpc-id,Values=$DEFAULT_VPC" --query "Subnets[*].SubnetId" --output text --region $AWS_REGION)
SUBNET_ARRAY=($(echo $SUBNETS))
DEFAULT_SG=$(aws ec2 describe-security-groups --filters "Name=vpc-id,Values=$DEFAULT_VPC" "Name=group-name,Values=default" --query "SecurityGroups[0].GroupId" --output text --region $AWS_REGION)

# Create DB subnet group if it doesn't exist
aws rds create-db-subnet-group \
    --db-subnet-group-name "energy-company-subnet-group" \
    --db-subnet-group-description "Subnet group for Energy Company databases" \
    --subnet-ids ${SUBNET_ARRAY[@]} \
    --region $AWS_REGION 2>/dev/null || echo "Subnet group already exists"

# Open PostgreSQL port
aws ec2 authorize-security-group-ingress --group-id $DEFAULT_SG --protocol tcp --port 5432 --cidr 0.0.0.0/0 --region $AWS_REGION 2>/dev/null || echo "Port 5432 already open"

echo "📊 Creating Consumption Service Database..."
aws rds create-db-instance \
    --db-instance-identifier "consumption-service-db" \
    --db-instance-class "db.t3.micro" \
    --engine "postgres" \
    --engine-version "15.4" \
    --master-username "consumption_admin" \
    --master-user-password "$DB_PASSWORD" \
    --allocated-storage 20 \
    --db-name "consumption_service_db" \
    --vpc-security-group-ids "$DEFAULT_SG" \
    --db-subnet-group-name "energy-company-subnet-group" \
    --backup-retention-period 7 \
    --storage-encrypted \
    --publicly-accessible \
    --region $AWS_REGION 2>/dev/null || echo "Consumption DB already exists"

echo "👥 Creating Customer Service Database..."
aws rds create-db-instance \
    --db-instance-identifier "customer-service-db" \
    --db-instance-class "db.t3.micro" \
    --engine "postgres" \
    --engine-version "15.4" \
    --master-username "customer_admin" \
    --master-user-password "$DB_PASSWORD" \
    --allocated-storage 20 \
    --db-name "customer_service_db" \
    --vpc-security-group-ids "$DEFAULT_SG" \
    --db-subnet-group-name "energy-company-subnet-group" \
    --backup-retention-period 7 \
    --storage-encrypted \
    --publicly-accessible \
    --region $AWS_REGION 2>/dev/null || echo "Customer DB already exists"

echo "⏳ Waiting for databases to become available..."
aws rds wait db-instance-available --db-instance-identifier "consumption-service-db" --region $AWS_REGION &
aws rds wait db-instance-available --db-instance-identifier "customer-service-db" --region $AWS_REGION &
wait

# Get endpoints
CONSUMPTION_ENDPOINT=$(aws rds describe-db-instances --db-instance-identifier "consumption-service-db" --query "DBInstances[0].Endpoint.Address" --output text --region $AWS_REGION)
CUSTOMER_ENDPOINT=$(aws rds describe-db-instances --db-instance-identifier "customer-service-db" --query "DBInstances[0].Endpoint.Address" --output text --region $AWS_REGION)

echo "✅ Databases created successfully!"
echo
echo "📋 Add these secrets to GitHub:"
echo
echo "🔸 Consumption Service:"
echo "   CONSUMPTION_DB_URL: jdbc:postgresql://$CONSUMPTION_ENDPOINT:5432/consumption_service_db"
echo "   CONSUMPTION_DB_USERNAME: consumption_admin"
echo "   CONSUMPTION_DB_PASSWORD: $DB_PASSWORD"
echo
echo "🔸 Customer Service:"
echo "   CUSTOMER_DB_URL: jdbc:postgresql://$CUSTOMER_ENDPOINT:5432/customer_service_db"
echo "   CUSTOMER_DB_USERNAME: customer_admin"
echo "   CUSTOMER_DB_PASSWORD: $DB_PASSWORD"
