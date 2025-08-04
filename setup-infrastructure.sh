#!/bin/bash

# Energy Company Infrastructure Setup Script
# This script helps set up the AWS infrastructure for the Energy Company application

set -e

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
AWS_REGION=${AWS_REGION:-us-east-1}
ENVIRONMENT=${1:-dev}

if [ "$ENVIRONMENT" != "dev" ] && [ "$ENVIRONMENT" != "prod" ]; then
    echo -e "${RED}Error: Environment must be 'dev' or 'prod'${NC}"
    echo "Usage: $0 [dev|prod]"
    exit 1
fi

echo -e "${BLUE}🚀 Setting up Energy Company infrastructure for ${ENVIRONMENT} environment${NC}"
echo -e "${BLUE}Region: ${AWS_REGION}${NC}"
echo ""

# Check if AWS CLI is installed and configured
if ! command -v aws &> /dev/null; then
    echo -e "${RED}AWS CLI is not installed. Please install it first.${NC}"
    exit 1
fi

# Check AWS credentials
if ! aws sts get-caller-identity &> /dev/null; then
    echo -e "${RED}AWS credentials not configured. Please run 'aws configure' first.${NC}"
    exit 1
fi

ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
echo -e "${GREEN}✓ AWS credentials configured for account: ${ACCOUNT_ID}${NC}"

# Prompt for database password
echo -e "${YELLOW}Please enter a secure database password (min 8 characters):${NC}"
read -s DB_PASSWORD
echo ""

if [ ${#DB_PASSWORD} -lt 8 ]; then
    echo -e "${RED}Password must be at least 8 characters long${NC}"
    exit 1
fi

echo -e "${BLUE}📦 Deploying CloudFormation stack...${NC}"

# Deploy infrastructure
STACK_NAME="energy-company-${ENVIRONMENT}"

aws cloudformation deploy \
    --template-file .aws/infrastructure.yml \
    --stack-name "${STACK_NAME}" \
    --parameter-overrides \
        Environment="${ENVIRONMENT}" \
        DBPassword="${DB_PASSWORD}" \
    --capabilities CAPABILITY_IAM \
    --region "${AWS_REGION}"

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Infrastructure deployed successfully${NC}"
else
    echo -e "${RED}✗ Infrastructure deployment failed${NC}"
    exit 1
fi

# Get stack outputs
echo -e "${BLUE}📋 Getting stack outputs...${NC}"

ECS_CLUSTER=$(aws cloudformation describe-stacks \
    --stack-name "${STACK_NAME}" \
    --query 'Stacks[0].Outputs[?OutputKey==`ECSClusterName`].OutputValue' \
    --output text \
    --region "${AWS_REGION}")

ALB_ARN=$(aws cloudformation describe-stacks \
    --stack-name "${STACK_NAME}" \
    --query 'Stacks[0].Outputs[?OutputKey==`ALBArn`].OutputValue' \
    --output text \
    --region "${AWS_REGION}")

CUSTOMER_DB_ENDPOINT=$(aws cloudformation describe-stacks \
    --stack-name "${STACK_NAME}" \
    --query 'Stacks[0].Outputs[?OutputKey==`CustomerDBEndpoint`].OutputValue' \
    --output text \
    --region "${AWS_REGION}")

CONSUMPTION_DB_ENDPOINT=$(aws cloudformation describe-stacks \
    --stack-name "${STACK_NAME}" \
    --query 'Stacks[0].Outputs[?OutputKey==`ConsumptionDBEndpoint`].OutputValue' \
    --output text \
    --region "${AWS_REGION}")

echo -e "${GREEN}✓ ECS Cluster: ${ECS_CLUSTER}${NC}"
echo -e "${GREEN}✓ Load Balancer: ${ALB_ARN}${NC}"
echo -e "${GREEN}✓ Customer DB: ${CUSTOMER_DB_ENDPOINT}${NC}"
echo -e "${GREEN}✓ Consumption DB: ${CONSUMPTION_DB_ENDPOINT}${NC}"

# Create ECR repositories
echo -e "${BLUE}🐳 Creating ECR repositories...${NC}"

SERVICES=(
    "api-gateway"
    "authentication-service"
    "consumption-service"
    "consumption-service-db-migration"
    "customer-service"
    "customer-service-db-migration"
    "elering-adapter"
    "eureka-server"
    "energy-company-client"
)

for service in "${SERVICES[@]}"; do
    REPO_NAME="energy-company-${service}-${ENVIRONMENT}"
    
    if aws ecr describe-repositories --repository-names "${REPO_NAME}" --region "${AWS_REGION}" &>/dev/null; then
        echo -e "${YELLOW}⚠ Repository ${REPO_NAME} already exists${NC}"
    else
        aws ecr create-repository \
            --repository-name "${REPO_NAME}" \
            --region "${AWS_REGION}" \
            --image-scanning-configuration scanOnPush=true \
            --encryption-configuration encryptionType=AES256 &>/dev/null
        echo -e "${GREEN}✓ Created repository: ${REPO_NAME}${NC}"
    fi
done

# Create CloudWatch log groups
echo -e "${BLUE}📊 Creating CloudWatch log groups...${NC}"

for service in "${SERVICES[@]}"; do
    LOG_GROUP="/ecs/energy-company/${service}-${ENVIRONMENT}"
    
    if aws logs describe-log-groups --log-group-name-prefix "${LOG_GROUP}" --region "${AWS_REGION}" | grep -q "${LOG_GROUP}"; then
        echo -e "${YELLOW}⚠ Log group ${LOG_GROUP} already exists${NC}"
    else
        aws logs create-log-group \
            --log-group-name "${LOG_GROUP}" \
            --region "${AWS_REGION}" &>/dev/null
        echo -e "${GREEN}✓ Created log group: ${LOG_GROUP}${NC}"
    fi
done

# Store database password in AWS Secrets Manager
echo -e "${BLUE}🔐 Storing database password in Secrets Manager...${NC}"

SECRET_NAME="energy-company/db-password-${ENVIRONMENT}"

if aws secretsmanager describe-secret --secret-id "${SECRET_NAME}" --region "${AWS_REGION}" &>/dev/null; then
    echo -e "${YELLOW}⚠ Secret ${SECRET_NAME} already exists${NC}"
else
    aws secretsmanager create-secret \
        --name "${SECRET_NAME}" \
        --description "Database password for Energy Company ${ENVIRONMENT} environment" \
        --secret-string "${DB_PASSWORD}" \
        --region "${AWS_REGION}" &>/dev/null
    echo -e "${GREEN}✓ Created secret: ${SECRET_NAME}${NC}"
fi

echo ""
echo -e "${GREEN}🎉 Infrastructure setup complete!${NC}"
echo ""
echo -e "${BLUE}Next steps:${NC}"
echo -e "${YELLOW}1. Configure GitHub Secrets in your repository:${NC}"
echo "   - AWS_ACCESS_KEY_ID"
echo "   - AWS_SECRET_ACCESS_KEY"
echo "   - AWS_ACCOUNT_ID: ${ACCOUNT_ID}"
echo ""
echo -e "${YELLOW}2. Create ECS Task Definitions for your services${NC}"
echo -e "${YELLOW}3. Set up Application Load Balancer Target Groups and Listeners${NC}"
echo -e "${YELLOW}4. Test the GitHub Actions workflows${NC}"
echo ""
echo -e "${BLUE}Database connection strings:${NC}"
echo "Customer DB: jdbc:postgresql://${CUSTOMER_DB_ENDPOINT}:5432/customerservicedb"
echo "Consumption DB: jdbc:postgresql://${CONSUMPTION_DB_ENDPOINT}:5432/consumptionservicedb"
echo ""
echo -e "${GREEN}Happy deploying! 🚀${NC}"