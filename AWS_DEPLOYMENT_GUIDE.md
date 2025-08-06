# Complete AWS Deployment Guide for Energy Company Microservices

## Overview
This guide will help you deploy your Energy Company microservices architecture to AWS with automated CI/CD pipelines for both development and production environments.

## Architecture Overview
- **Frontend**: React app (energy-company-client) served via ECS
- **API Gateway**: Central routing for all microservices
- **Microservices**: Spring Boot services running on ECS Fargate
- **Databases**: PostgreSQL RDS instances for customer and consumption data
- **Service Discovery**: Eureka Server for microservice registration
- **Load Balancing**: Application Load Balancer for traffic distribution

## Prerequisites
1. AWS Account with administrative access
2. AWS CLI installed and configured
3. Docker installed
4. GitHub repository with your code
5. Domain name (optional, for production)

## Step-by-Step Deployment Process

### Phase 1: AWS Account Setup

#### 1.1 Install AWS CLI
```bash
# macOS
brew install awscli

# Or download from: https://aws.amazon.com/cli/
```

#### 1.2 Configure AWS CLI
```bash
aws configure
# Enter your AWS Access Key ID
# Enter your AWS Secret Access Key
# Default region: us-east-1
# Default output format: json
```

#### 1.3 Verify AWS Access
```bash
aws sts get-caller-identity
```

### Phase 2: Infrastructure Deployment

#### 2.1 Run the Automated Setup Script
```bash
cd /path/to/energy-company
chmod +x deploy-to-aws.sh
./deploy-to-aws.sh
```

The script will:
- Create IAM roles for ECS
- Deploy VPC, subnets, security groups
- Create ECS cluster
- Set up RDS databases
- Create ECR repositories
- Set up CloudWatch logging
- Create AWS Secrets for database passwords

#### 2.2 Manual Alternative (if you prefer step-by-step)

**Create IAM Roles:**
```bash
# ECS Task Execution Role
aws iam create-role --role-name ecsTaskExecutionRole --assume-role-policy-document file://trust-policy.json
aws iam attach-role-policy --role-name ecsTaskExecutionRole --policy-arn arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy

# ECS Task Role
aws iam create-role --role-name ecsTaskRole --assume-role-policy-document file://trust-policy.json
```

**Deploy Infrastructure:**
```bash
aws cloudformation deploy \
  --template-file .aws/infrastructure.yml \
  --stack-name energy-company-dev \
  --parameter-overrides Environment=dev DBPassword=YourSecurePassword123 \
  --capabilities CAPABILITY_IAM
```

### Phase 3: GitHub Actions Setup

#### 3.1 Add GitHub Secrets
Go to your GitHub repository → Settings → Secrets and variables → Actions

Add these secrets:
- `AWS_ACCESS_KEY_ID`: Your AWS access key
- `AWS_SECRET_ACCESS_KEY`: Your AWS secret access key  
- `AWS_ACCOUNT_ID`: Your 12-digit AWS account ID

#### 3.2 Update Task Definitions
Replace `ACCOUNT_ID` in all task definition files with your actual AWS account ID:
```bash
# Replace ACCOUNT_ID with your actual account ID in all task definition files
find .aws/task-definitions -name "*.json" -exec sed -i 's/ACCOUNT_ID/123456789012/g' {} \;
```

### Phase 4: Deploy Services

#### 4.1 Development Environment
- Create a pull request
- GitHub Actions will automatically:
  - Build and test your code
  - Build Docker images
  - Push to ECR
  - Deploy to development environment

#### 4.2 Production Environment
- Merge your pull request to main/master
- GitHub Actions will automatically:
  - Build and test your code
  - Build Docker images
  - Push to ECR
  - Deploy to production with proper ordering

### Phase 5: Domain and SSL Setup (Production Only)

#### 5.1 Get Load Balancer DNS
```bash
aws elbv2 describe-load-balancers --names energy-company-alb-prod --query 'LoadBalancers[0].DNSName'
```

#### 5.2 Configure DNS
Point your domain to the load balancer DNS name using a CNAME record.

#### 5.3 Set up SSL Certificate
```bash
aws acm request-certificate --domain-name yourdomain.com --validation-method DNS
```

## Service Ports and Endpoints

| Service | Port | Health Check |
|---------|------|--------------|
| eureka-server | 8761 | /actuator/health |
| api-gateway | 4000 | /actuator/health |
| authentication-service | 2000 | /actuator/health |
| customer-service | 5007 | /actuator/health |
| consumption-service | 5006 | /actuator/health |
| elering-adapter | 5005 | /actuator/health |
| energy-company-client | 5173 | / |

## Monitoring and Troubleshooting

### AWS Console Links
- **ECS Services**: https://console.aws.amazon.com/ecs/
- **ECR Repositories**: https://console.aws.amazon.com/ecr/
- **CloudWatch Logs**: https://console.aws.amazon.com/cloudwatch/
- **RDS Databases**: https://console.aws.amazon.com/rds/
- **Load Balancers**: https://console.aws.amazon.com/ec2/v2/home#LoadBalancers

### Useful Commands

**Check ECS Service Status:**
```bash
aws ecs describe-services --cluster energy-company-prod --services api-gateway-prod
```

**View Logs:**
```bash
aws logs tail /ecs/energy-company/api-gateway-prod --follow
```

**Force New Deployment:**
```bash
aws ecs update-service --cluster energy-company-prod --service api-gateway-prod --force-new-deployment
```

## Environment Variables Configuration

### Backend Services
- `SPRING_APPLICATION_NAME`: Service name for Eureka registration
- `EUREKA_SERVICE_URL`: Eureka server endpoint
- `DB_SERVICE_URL`: Database connection string
- `DB_SERVICE_USERNAME`: Database username
- `DB_SERVICE_PASSWORD`: Database password (from AWS Secrets Manager)

### Frontend
- `VITE_API_BASE_URL`: API gateway endpoint

## Database Migration Strategy

1. **Migration services run first** (priority 2 in deployment)
2. **Application services start after** migrations complete
3. **Database schema changes** are handled by Liquibase/Flyway

## Scaling and Performance

### Auto Scaling (Production)
Services are configured to auto-scale based on CPU/memory usage:
- Min capacity: 1
- Max capacity: 10
- Target CPU: 70%

### Database Performance
- Production: db.t3.small with Multi-AZ
- Development: db.t3.micro single AZ

## Security Features

- **VPC isolation**: Services run in private subnets
- **Security groups**: Restrict traffic between tiers
- **Secrets management**: Database passwords in AWS Secrets Manager
- **IAM roles**: Least privilege access for services
- **SSL/TLS**: HTTPS for all external traffic

## Cost Optimization

### Development Environment
- Uses smaller instance sizes
- Single AZ databases
- Minimal backup retention

### Production Environment
- Right-sized instances
- Multi-AZ for availability
- 7-day backup retention

## Troubleshooting Common Issues

### Service Won't Start
1. Check CloudWatch logs
2. Verify environment variables
3. Check security group rules
4. Ensure database connectivity

### Database Connection Issues
1. Verify security groups allow port 5432
2. Check database endpoint in environment variables
3. Verify secrets manager access

### Build Failures
1. Check GitHub Actions logs
2. Verify AWS credentials
3. Check ECR repository permissions

## Next Steps After Deployment

1. **Set up monitoring alerts** in CloudWatch
2. **Configure backup strategies** for RDS
3. **Set up SSL certificates** for production
4. **Configure custom domain** routing
5. **Set up log aggregation** and analysis
6. **Implement performance monitoring**

## Support and Maintenance

- Monitor CloudWatch metrics and logs
- Regular security updates via GitHub Actions
- Database maintenance windows
- Backup and disaster recovery testing
