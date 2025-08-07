# 🚀 Quick Start: Deploy Energy Company to AWS

## What I've Created for You

✅ **Fixed infrastructure.yml** - Removed duplicate Parameters section  
✅ **All missing task definitions** - Created templates for all 9 services:
- api-gateway-template.json
- authentication-service-template.json  
- consumption-service-template.json
- consumption-service-db-migration-template.json
- customer-service-template.json *(already existed)*
- customer-service-db-migration-template.json
- elering-adapter-template.json
- eureka-server-template.json
- energy-company-client-template.json

✅ **Automated deployment script** - `deploy-to-aws.sh`  
✅ **Complete deployment guide** - `AWS_DEPLOYMENT_GUIDE.md`

## 🎯 What You Need to Do RIGHT NOW

### Step 1: One-Command AWS Setup
```bash
cd /Users/ismaildogan/IdeaProjects/energy-company
./deploy-to-aws.sh
```
This script will:
- Create all AWS infrastructure (VPC, ECS, RDS, Load Balancer)
- Set up IAM roles
- Create ECR repositories for your Docker images
- Configure CloudWatch logging
- Guide you through the entire process

### Step 2: Add GitHub Secrets
Go to your GitHub repo → Settings → Secrets and Variables → Actions

Add these 3 secrets:
- `AWS_ACCESS_KEY_ID` - Your AWS access key
- `AWS_SECRET_ACCESS_KEY` - Your AWS secret key  
- `AWS_ACCOUNT_ID` - Your 12-digit AWS account ID

### Step 3: Update Task Definitions
Replace `ACCOUNT_ID` in the task definition files with your real AWS account ID:
```bash
# Get your account ID
aws sts get-caller-identity --query Account --output text

# Replace in all files (use the actual number)
find .aws/task-definitions -name "*.json" -exec sed -i 's/ACCOUNT_ID/123456789012/g' {} \;
```

### Step 4: Deploy!
- **Development**: Create a pull request → Auto-deploys to dev environment
- **Production**: Merge to main/master → Auto-deploys to production

## 🏗️ What Gets Created in AWS

| AWS Service | Purpose | What It Hosts |
|-------------|---------|---------------|
| **ECS Fargate** | Container hosting | All your microservices + UI |
| **RDS PostgreSQL** | Databases | Customer & consumption data |
| **Application Load Balancer** | Traffic routing | Public access to your services |
| **ECR** | Docker registry | Your container images |
| **VPC** | Networking | Secure isolated network |
| **CloudWatch** | Monitoring | Logs and metrics |
| **Secrets Manager** | Security | Database passwords |

## 📋 Deployment Order (Automatic)

1. **Eureka Server** (service discovery)
2. **Database Migrations** (schema setup)
3. **Core Services** (customer, consumption, elering-adapter)
4. **Authentication Service** (security)
5. **API Gateway** (routing)
6. **Frontend Client** (UI)

## 🔍 How to Monitor Your Deployment

### AWS Console Quick Links (after deployment):
- **ECS Services**: Check service health and logs
- **ECR Repositories**: View your Docker images  
- **CloudWatch Logs**: Debug issues
- **RDS**: Monitor database performance
- **Load Balancer**: See traffic patterns

### GitHub Actions:
- Watch the Actions tab for build/deploy progress
- Green checkmarks = successful deployments
- Red X = check the logs for issues

## 🌐 Access Your Application

After deployment:
- **Development**: `http://your-dev-load-balancer-dns/`
- **Production**: `http://your-prod-load-balancer-dns/` (or your custom domain)

## 🚨 If Something Goes Wrong

1. **Check GitHub Actions logs** - Most issues show up here
2. **Check CloudWatch logs** - For runtime issues
3. **Verify AWS credentials** - Make sure GitHub secrets are correct
4. **Check service health** - Use kubectl commands

```bash
# Check all pods
kubectl get pods -n energy-company-dev

# Check service status  
kubectl get services -n energy-company-dev

# Check individual service health
kubectl port-forward service/eureka-server 8761:8761 -n energy-company-dev
```

## 💰 Cost Estimate

**Development Environment**: ~$25-40/month
- EKS Control Plane: $72/month (free tier available)
- t3.medium nodes (2 nodes): ~$60/month
- RDS databases: ~$30/month

**Production Environment**: ~$100-200/month  
- EKS Control Plane: $72/month
- Larger nodes with auto-scaling
- Multi-AZ databases
- Application Load Balancer
- Enhanced monitoring

## 🎉 You're Ready!

Your complete Kubernetes CI/CD pipeline is now set up. Every time you:
- Create a PR → Deploys to development via GitHub Actions
- Merge to main → Can deploy to production

The entire microservices architecture runs on Kubernetes with:
- **Service Discovery**: Built-in Kubernetes DNS
- **Load Balancing**: Automatic via Kubernetes Services  
- **Health Checks**: Kubernetes liveness/readiness probes
- **Auto-scaling**: Horizontal Pod Autoscaler ready
- **Zero-downtime deployments**: Rolling updates

## 🚀 Quick Deployment

```bash
# Set up EKS cluster (one-time)
./setup-eks-cluster.sh

# Deploy all microservices
kubectl apply -f k8s/

# Access your application
kubectl port-forward service/energy-company-client 5173:5173 -n energy-company-dev
```

Your microservices will automatically discover and communicate with each other using Kubernetes DNS!
