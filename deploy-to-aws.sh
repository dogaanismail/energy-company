#!/bin/bash

# Energy Company AWS Deployment Setup Script
# This script will guide you through setting up your complete AWS infrastructure

set -e

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
AWS_REGION="us-east-1"
ENVIRONMENT=""
AWS_ACCOUNT_ID=""
DB_PASSWORD=""
DOMAIN_NAME=""

print_header() {
    echo -e "${BLUE}================================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}================================================${NC}"
}

print_step() {
    echo -e "${GREEN}[STEP] $1${NC}"
}

print_info() {
    echo -e "${YELLOW}[INFO] $1${NC}"
}

print_error() {
    echo -e "${RED}[ERROR] $1${NC}"
}

check_requirements() {
    print_header "Checking Requirements"

    # Check AWS CLI
    if ! command -v aws &> /dev/null; then
        print_error "AWS CLI is not installed. Please install it first."
        echo "Visit: https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html"
        exit 1
    fi

    # Check Docker
    if ! command -v docker &> /dev/null; then
        print_error "Docker is not installed. Please install it first."
        exit 1
    fi

    # Check if AWS is configured
    if ! aws sts get-caller-identity &> /dev/null; then
        print_error "AWS CLI is not configured. Please run 'aws configure' first."
        exit 1
    fi

    print_info "All requirements met!"
}

get_user_inputs() {
    print_header "Configuration Setup"

    # Get AWS Account ID
    AWS_ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
    print_info "AWS Account ID: $AWS_ACCOUNT_ID"

    # Get Environment
    echo -e "${YELLOW}Which environment are you setting up?${NC}"
    echo "1) Development (dev)"
    echo "2) Production (prod)"
    read -p "Enter choice (1 or 2): " env_choice

    if [ "$env_choice" = "1" ]; then
        ENVIRONMENT="dev"
    elif [ "$env_choice" = "2" ]; then
        ENVIRONMENT="prod"
    else
        print_error "Invalid choice. Exiting."
        exit 1
    fi

    # Get Database Password
    while [[ ${#DB_PASSWORD} -lt 8 ]]; do
        read -s -p "Enter database password (min 8 characters): " DB_PASSWORD
        echo
        if [[ ${#DB_PASSWORD} -lt 8 ]]; then
            print_error "Password must be at least 8 characters long."
        fi
    done

    # Get Domain Name (optional for prod)
    if [ "$ENVIRONMENT" = "prod" ]; then
        read -p "Enter your domain name (e.g., energy-company.com) [optional]: " DOMAIN_NAME
    fi

    print_info "Configuration complete!"
}

setup_iam_roles() {
    print_header "Setting up IAM Roles"

    print_step "Creating ECS Task Execution Role"
    aws iam create-role --role-name ecsTaskExecutionRole \
        --assume-role-policy-document '{
            "Version": "2012-10-17",
            "Statement": [
                {
                    "Effect": "Allow",
                    "Principal": {
                        "Service": "ecs-tasks.amazonaws.com"
                    },
                    "Action": "sts:AssumeRole"
                }
            ]
        }' 2>/dev/null || print_info "Role ecsTaskExecutionRole already exists"

    aws iam attach-role-policy \
        --role-name ecsTaskExecutionRole \
        --policy-arn arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy

    print_step "Creating ECS Task Role"
    aws iam create-role --role-name ecsTaskRole \
        --assume-role-policy-document '{
            "Version": "2012-10-17",
            "Statement": [
                {
                    "Effect": "Allow",
                    "Principal": {
                        "Service": "ecs-tasks.amazonaws.com"
                    },
                    "Action": "sts:AssumeRole"
                }
            ]
        }' 2>/dev/null || print_info "Role ecsTaskRole already exists"

    print_info "IAM roles setup complete!"
}

create_secrets() {
    print_header "Creating AWS Secrets"

    print_step "Creating database password secret"
    aws secretsmanager create-secret \
        --name "energy-company/db-password" \
        --description "Database password for Energy Company microservices" \
        --secret-string "$DB_PASSWORD" \
        --region $AWS_REGION 2>/dev/null || \
    aws secretsmanager update-secret \
        --secret-id "energy-company/db-password" \
        --secret-string "$DB_PASSWORD" \
        --region $AWS_REGION

    print_info "Secrets created successfully!"
}

deploy_infrastructure() {
    print_header "Deploying Infrastructure"

    print_step "Deploying CloudFormation stack"
    aws cloudformation deploy \
        --template-file .aws/infrastructure.yml \
        --stack-name "energy-company-$ENVIRONMENT" \
        --parameter-overrides \
            Environment=$ENVIRONMENT \
            DBPassword=$DB_PASSWORD \
        --capabilities CAPABILITY_IAM \
        --region $AWS_REGION

    print_info "Infrastructure deployed successfully!"
}

create_ecr_repositories() {
    print_header "Creating ECR Repositories"

    services=(
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

    for service in "${services[@]}"; do
        print_step "Creating ECR repository for $service"
        aws ecr describe-repositories --repository-names "energy-company-$service-$ENVIRONMENT" --region $AWS_REGION 2>/dev/null || \
        aws ecr create-repository --repository-name "energy-company-$service-$ENVIRONMENT" --region $AWS_REGION
    done

    print_info "ECR repositories created successfully!"
}

setup_github_secrets() {
    print_header "GitHub Actions Setup"

    print_info "Please add the following secrets to your GitHub repository:"
    echo -e "${YELLOW}Go to: GitHub Repository → Settings → Secrets and variables → Actions${NC}"
    echo
    echo -e "${BLUE}Required secrets:${NC}"
    echo "AWS_ACCESS_KEY_ID: Your AWS access key"
    echo "AWS_SECRET_ACCESS_KEY: Your AWS secret key"
    echo "AWS_ACCOUNT_ID: $AWS_ACCOUNT_ID"
    echo
    print_info "Your GitHub Actions workflows are already configured and will run automatically on push/PR!"
}

create_cloudwatch_logs() {
    print_header "Creating CloudWatch Log Groups"

    services=(
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

    for service in "${services[@]}"; do
        print_step "Creating log group for $service"
        aws logs create-log-group \
            --log-group-name "/ecs/energy-company/$service-$ENVIRONMENT" \
            --region $AWS_REGION 2>/dev/null || print_info "Log group for $service already exists"
    done

    print_info "CloudWatch log groups created successfully!"
}

show_next_steps() {
    print_header "Setup Complete! Next Steps"

    echo -e "${GREEN}✅ Infrastructure has been deployed successfully!${NC}"
    echo
    echo -e "${BLUE}What was created:${NC}"
    echo "• VPC with public/private subnets"
    echo "• ECS Cluster: energy-company-$ENVIRONMENT"
    echo "• RDS PostgreSQL databases (customer & consumption)"
    echo "• Application Load Balancer"
    echo "• Security Groups"
    echo "• ECR repositories for all services"
    echo "• CloudWatch log groups"
    echo "• IAM roles for ECS"
    echo "• Secrets Manager entries"
    echo
    echo -e "${BLUE}Next steps:${NC}"
    echo "1. Add GitHub secrets (AWS credentials)"
    echo "2. Push your code to trigger the CI/CD pipeline"
    echo "3. Your services will be automatically built and deployed!"
    echo
    echo -e "${BLUE}Useful AWS Console Links:${NC}"
    echo "• ECS Cluster: https://console.aws.amazon.com/ecs/home?region=$AWS_REGION#/clusters/energy-company-$ENVIRONMENT"
    echo "• ECR Repositories: https://console.aws.amazon.com/ecr/repositories?region=$AWS_REGION"
    echo "• CloudFormation Stack: https://console.aws.amazon.com/cloudformation/home?region=$AWS_REGION#/stacks"
    echo "• RDS Databases: https://console.aws.amazon.com/rds/home?region=$AWS_REGION#databases:"
    echo
    if [ "$ENVIRONMENT" = "prod" ] && [ ! -z "$DOMAIN_NAME" ]; then
        echo -e "${YELLOW}Don't forget to:${NC}"
        echo "• Set up your domain DNS to point to the Load Balancer"
        echo "• Configure SSL certificate in ACM"
    fi
}

main() {
    print_header "Energy Company AWS Deployment Setup"
    echo "This script will set up your complete AWS infrastructure for the Energy Company microservices."
    echo
    read -p "Do you want to continue? (y/N): " confirm

    if [[ ! $confirm =~ ^[Yy]$ ]]; then
        echo "Setup cancelled."
        exit 0
    fi

    check_requirements
    get_user_inputs
    setup_iam_roles
    create_secrets
    deploy_infrastructure
    create_ecr_repositories
    create_cloudwatch_logs
    setup_github_secrets
    show_next_steps
}

# Run main function
main
