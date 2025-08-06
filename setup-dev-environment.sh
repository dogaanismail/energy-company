#!/bin/bash

# Development Environment Setup and Deployment Script
set -e

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

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

# Configuration
AWS_REGION="us-east-1"
AWS_ACCOUNT_ID="480241654156"
ENVIRONMENT="dev"

print_header "Energy Company Development Environment Setup"

print_step "1. Testing AWS Connection"
if aws sts get-caller-identity > /dev/null 2>&1; then
    CURRENT_ACCOUNT=$(aws sts get-caller-identity --query Account --output text)
    print_info "✅ AWS connection successful. Account: $CURRENT_ACCOUNT"
else
    print_error "❌ AWS connection failed. Please check your AWS CLI configuration."
    exit 1
fi

print_step "2. Checking Development Infrastructure"
STACK_STATUS=$(aws cloudformation describe-stacks --stack-name "energy-company-dev" --query 'Stacks[0].StackStatus' --output text 2>/dev/null || echo "NOT_FOUND")

if [ "$STACK_STATUS" = "CREATE_COMPLETE" ] || [ "$STACK_STATUS" = "UPDATE_COMPLETE" ]; then
    print_info "✅ Development infrastructure is ready"

    # Get Load Balancer DNS
    ALB_DNS=$(aws elbv2 describe-load-balancers --names energy-company-alb-dev --query 'LoadBalancers[0].DNSName' --output text 2>/dev/null || echo "NOT_FOUND")
    if [ "$ALB_DNS" != "NOT_FOUND" ]; then
        print_info "✅ Load Balancer DNS: $ALB_DNS"
    else
        print_error "❌ Load Balancer not found"
    fi
else
    print_error "❌ Development infrastructure not ready. Status: $STACK_STATUS"
    print_info "Please run: ./deploy-to-aws.sh first to set up infrastructure"
    exit 1
fi

print_step "3. Checking ECS Services Status"
ECS_SERVICES=("eureka-server-dev" "customer-service-dev" "consumption-service-dev" "api-gateway-dev" "energy-company-client-dev")

for service in "${ECS_SERVICES[@]}"; do
    SERVICE_STATUS=$(aws ecs describe-services --cluster energy-company-dev --services $service --query 'services[0].status' --output text 2>/dev/null || echo "NOT_FOUND")

    if [ "$SERVICE_STATUS" = "ACTIVE" ]; then
        RUNNING_COUNT=$(aws ecs describe-services --cluster energy-company-dev --services $service --query 'services[0].runningCount' --output text 2>/dev/null || echo "0")
        DESIRED_COUNT=$(aws ecs describe-services --cluster energy-company-dev --services $service --query 'services[0].desiredCount' --output text 2>/dev/null || echo "0")

        if [ "$RUNNING_COUNT" = "$DESIRED_COUNT" ] && [ "$RUNNING_COUNT" != "0" ]; then
            print_info "✅ $service: Running ($RUNNING_COUNT/$DESIRED_COUNT)"
        else
            print_info "⚠️  $service: Deploying ($RUNNING_COUNT/$DESIRED_COUNT)"
        fi
    else
        print_info "❌ $service: Not deployed ($SERVICE_STATUS)"
    fi
done

print_step "4. Creating Pull Request for Development Deployment"
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

if [ "$CURRENT_BRANCH" = "master" ] || [ "$CURRENT_BRANCH" = "main" ]; then
    print_info "Creating development deployment branch..."

    # Create a new branch for development deployment
    BRANCH_NAME="deploy/dev-$(date +%Y%m%d-%H%M%S)"
    git checkout -b $BRANCH_NAME

    # Make a small change to trigger deployment
    echo "# Development deployment triggered at $(date)" >> .dev-deployment-log
    git add .dev-deployment-log
    git commit -m "Trigger development deployment

- Deploy all services to development environment
- Test microservices communication
- Verify frontend accessibility"

    git push origin $BRANCH_NAME

    print_info "✅ Development deployment branch created: $BRANCH_NAME"
    print_info "🚀 Creating pull request will trigger development deployment"

    # Try to create PR using GitHub CLI if available
    if command -v gh &> /dev/null; then
        print_step "5. Creating Pull Request"
        gh pr create \
            --title "🚀 Development Environment Deployment" \
            --body "This PR deploys all services to the development environment for testing.

## Services to Deploy
- ✅ Eureka Server (service discovery)
- ✅ Database migrations
- ✅ Customer Service
- ✅ Consumption Service
- ✅ Elering Adapter
- ✅ Authentication Service
- ✅ API Gateway
- ✅ Frontend Client

## Testing URLs
- **Frontend**: http://$ALB_DNS
- **API Gateway**: http://$ALB_DNS/api/v1/
- **Eureka Server**: http://$ALB_DNS:8761/

The development deployment will start automatically when this PR is created." \
            --head $BRANCH_NAME \
            --base master

        print_info "✅ Pull request created successfully!"
    else
        print_info "GitHub CLI not found. Please create a pull request manually:"
        print_info "1. Go to: https://github.com/YOUR_USERNAME/energy-company"
        print_info "2. Click 'Compare & pull request' for branch: $BRANCH_NAME"
        print_info "3. Create the pull request to trigger development deployment"
    fi

else
    print_info "You're already on branch: $CURRENT_BRANCH"
    print_info "Push this branch to create a PR for development deployment:"
    print_info "git push origin $CURRENT_BRANCH"
fi

print_step "6. Development Environment URLs"
print_info "Once deployment completes, access your application at:"
print_info "🌐 Frontend: http://$ALB_DNS"
print_info "🔧 API Gateway: http://$ALB_DNS/api/v1/"
print_info "📊 Eureka Server: http://$ALB_DNS:8761/"
print_info "📝 Service Logs: https://console.aws.amazon.com/cloudwatch/home?region=us-east-1#logsV2:log-groups"

print_step "7. Production Deployment (When Ready)"
print_info "To deploy to production later:"
print_info "1. Merge your PR to master (this WON'T trigger production deployment)"
print_info "2. Create a version tag: git tag v1.0.0 && git push origin v1.0.0"
print_info "3. OR use manual deployment from GitHub Actions"

print_header "✅ Development Environment Setup Complete!"
print_info "Your development deployment is now in progress."
print_info "Monitor progress in GitHub Actions: https://github.com/YOUR_USERNAME/energy-company/actions"
