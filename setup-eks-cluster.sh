#!/bin/bash

# EKS Cluster Setup Script for Energy Company
# This script creates a basic EKS cluster for testing Kubernetes deployment

set -e

AWS_REGION="us-east-1"
CLUSTER_NAME="energy-company-dev"
NODE_GROUP_NAME="energy-company-nodes"

echo "🚀 Setting up EKS cluster for Energy Company microservices..."

# Check if eksctl is installed
if ! command -v eksctl &> /dev/null; then
    echo "❌ eksctl is not installed. Please install it first:"
    echo "brew install weaveworks/tap/eksctl"
    exit 1
fi

# Check if kubectl is installed
if ! command -v kubectl &> /dev/null; then
    echo "❌ kubectl is not installed. Please install it first:"
    echo "brew install kubectl"
    exit 1
fi

# Create EKS cluster
echo "🏗️ Creating EKS cluster: $CLUSTER_NAME"
eksctl create cluster \
    --name $CLUSTER_NAME \
    --region $AWS_REGION \
    --nodegroup-name $NODE_GROUP_NAME \
    --node-type t3.medium \
    --nodes 2 \
    --nodes-min 1 \
    --nodes-max 4 \
    --managed

# Update kubeconfig
echo "🔧 Updating kubeconfig..."
aws eks update-kubeconfig --name $CLUSTER_NAME --region $AWS_REGION

# Verify cluster
echo "✅ Verifying cluster setup..."
kubectl get nodes

echo "🎉 EKS cluster setup complete!"
echo "You can now deploy your microservices with: kubectl apply -f k8s/"
