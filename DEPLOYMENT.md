# GitHub Actions CI/CD Pipeline for Energy Company

This document describes the CI/CD pipeline setup for deploying the Energy Company microservices architecture to AWS.

## 🏗️ Architecture Overview

The pipeline deploys 9 services:
- **Backend Services (Java Spring Boot)**:
  - `eureka-server` - Service discovery
  - `api-gateway` - API Gateway and routing
  - `authentication-service` - JWT authentication
  - `customer-service` - Customer management
  - `consumption-service` - Energy consumption data
  - `elering-adapter` - External API integration
  - `customer-service-db-migration` - Database migrations
  - `consumption-service-db-migration` - Database migrations

- **Frontend Service (React)**:
  - `energy-company-client` - Web application

## 🚀 Deployment Strategy

### Environments

1. **Development Environment** (`energy-company-dev`)
   - Triggered on: Pull Request creation/updates
   - Purpose: Testing and validation
   - Infrastructure: Smaller instance types, single AZ

2. **Production Environment** (`energy-company-prod`)
   - Triggered on: Merge to main branch
   - Purpose: Live production system
   - Infrastructure: High availability, multi-AZ, backup enabled

### Deployment Order

Services are deployed in priority order to ensure dependencies are available:

1. **Priority 1**: `eureka-server` - Service discovery must be available first
2. **Priority 2**: Database migrations - Ensure schema is up to date
3. **Priority 3**: Core services - `customer-service`, `consumption-service`, `elering-adapter`
4. **Priority 4**: `authentication-service` - Authentication layer
5. **Priority 5**: `api-gateway` - External API entry point
6. **Priority 6**: `energy-company-client` - Frontend application

## 📋 Prerequisites

### AWS Resources Required

1. **AWS Account** with appropriate permissions
2. **ECR Repositories** (created automatically by workflows)
3. **ECS Cluster** 
4. **VPC with subnets**
5. **Application Load Balancer**
6. **RDS PostgreSQL instances**
7. **IAM roles and policies**

### GitHub Secrets Required

Configure the following secrets in your GitHub repository:

```
AWS_ACCESS_KEY_ID          # AWS Access Key ID
AWS_SECRET_ACCESS_KEY      # AWS Secret Access Key  
AWS_ACCOUNT_ID             # Your AWS Account ID (12 digits)
DB_PASSWORD                # Database password for RDS instances
```

Optional notification secrets:
```
SLACK_WEBHOOK_URL          # Slack webhook for deployment notifications
```

## 🛠️ Infrastructure Setup

### Option 1: CloudFormation (Recommended)

Deploy the infrastructure using the provided CloudFormation template:

```bash
# Deploy development environment
aws cloudformation deploy \
  --template-file .aws/infrastructure.yml \
  --stack-name energy-company-dev \
  --parameter-overrides Environment=dev DBPassword=YourSecurePassword \
  --capabilities CAPABILITY_IAM

# Deploy production environment  
aws cloudformation deploy \
  --template-file .aws/infrastructure.yml \
  --stack-name energy-company-prod \
  --parameter-overrides Environment=prod DBPassword=YourSecurePassword \
  --capabilities CAPABILITY_IAM
```

### Option 2: Manual Setup

If you prefer manual setup, create the following AWS resources:

#### 1. VPC and Networking
- VPC with CIDR `10.0.0.0/16`
- 2 public subnets for load balancer
- 2 private subnets for services and databases
- Internet Gateway and route tables

#### 2. Security Groups
- ALB security group (ports 80, 443)
- ECS security group (all ports from ALB)
- RDS security group (port 5432 from ECS)

#### 3. ECS Cluster
```bash
aws ecs create-cluster --cluster-name energy-company-dev
aws ecs create-cluster --cluster-name energy-company-prod
```

#### 4. RDS Databases
Create PostgreSQL instances:
- `energy-company-customer-dev/prod`
- `energy-company-consumption-dev/prod`

#### 5. Application Load Balancer
- Internet-facing ALB in public subnets
- Target groups for each service

## 🔄 Workflows

### 1. CI Workflow (`.github/workflows/ci.yml`)

**Triggers**: Pull requests and pushes to main
**Purpose**: Build, test, and validate code

**Jobs**:
- `test-backend`: Build and test Java services
- `test-frontend`: Build and test React application  
- `build-images`: Build Docker images (push events only)

### 2. Development Deployment (`.github/workflows/deploy-dev.yml`)

**Triggers**: Pull request creation/updates
**Purpose**: Deploy to development environment for testing

**Jobs**:
- `build-and-deploy`: Build and deploy all services
- `notify-deployment`: Comment on PR with deployment status

### 3. Production Deployment (`.github/workflows/deploy-prod.yml`)

**Triggers**: Push to main branch
**Purpose**: Deploy to production environment

**Jobs**:
- `deploy-infrastructure`: Ensure infrastructure is ready
- `build-and-deploy`: Build and push images to ECR
- `deploy-services`: Deploy services in priority order
- `run-health-checks`: Validate deployment health
- `notify-success`: Send success notifications
- `rollback-on-failure`: Automatic rollback if deployment fails

## 🏥 Health Checks and Monitoring

Each service should expose health endpoints:
- Spring Boot services: `/actuator/health`
- React application: Custom health endpoint

The pipeline performs health checks after deployment and automatically rolls back if any service is unhealthy.

## 🔧 Customization

### Adding New Services

1. Add service to the `strategy.matrix.service` in workflow files
2. Set appropriate priority for deployment order
3. Ensure Dockerfile exists in service directory
4. Add health check endpoint if needed

### Environment Variables

Services are configured through environment variables:
- Database connections
- Service URLs
- External API endpoints
- Spring profiles

### Scaling

Services can be scaled by updating ECS service desired count:
```bash
aws ecs update-service \
  --cluster energy-company-prod \
  --service api-gateway-prod \
  --desired-count 3
```

## 🚨 Troubleshooting

### Common Issues

1. **Build Failures**
   - Check Java version compatibility
   - Verify Gradle wrapper permissions
   - Review dependency conflicts

2. **Deployment Failures**
   - Verify AWS credentials and permissions
   - Check ECR repository exists
   - Validate ECS task definitions

3. **Service Health Check Failures**
   - Review service logs in CloudWatch
   - Check security group configurations
   - Verify database connectivity

### Rollback Procedure

Automatic rollback is built into the production workflow. For manual rollback:

```bash
# Get previous task definition revision
aws ecs describe-services --cluster energy-company-prod --services api-gateway-prod

# Update service to previous revision
aws ecs update-service \
  --cluster energy-company-prod \
  --service api-gateway-prod \
  --task-definition api-gateway-prod:PREVIOUS_REVISION
```

## 📊 Monitoring and Logging

- **CloudWatch Logs**: Service logs and metrics
- **ECS Service Metrics**: CPU, memory, task health
- **ALB Metrics**: Request count, latency, error rates
- **RDS Monitoring**: Database performance and connections

## 🔐 Security Considerations

- All secrets are stored in GitHub Secrets
- Database passwords are managed through AWS Secrets Manager (recommended)
- Images are scanned for vulnerabilities in ECR
- Network traffic is restricted through security groups
- RDS instances are in private subnets
- Encryption is enabled for storage and transit

## 🎯 Next Steps

1. **Set up monitoring dashboards** in CloudWatch
2. **Configure alerts** for service failures
3. **Implement blue-green deployment** for zero-downtime updates
4. **Add database backup automation**
5. **Set up log aggregation** with ELK stack or similar
6. **Implement infrastructure as code** with CDK or Terraform