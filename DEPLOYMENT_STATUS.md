# Energy Company Microservices - Deployment Status

This file tracks deployment information for the Energy Company microservices ecosystem.

## Current Deployment
- **Environment**: Development
- **Cluster**: energy-company-dev
- **Region**: us-east-1

## Database Configuration ✅
- **Consumption DB**: energy-consumption-db.c6bsckuo8rle.us-east-1.rds.amazonaws.com
- **Customer DB**: energy-customer-db.c6bsckuo8rle.us-east-1.rds.amazonaws.com
- **Status**: Database secrets configured in GitHub Actions

## Services
- ✅ Frontend: energy-company-client (React + Vite) - Port 5173
- ✅ API Gateway: Port 4000
- ✅ Eureka Server: Port 8761 (Service Discovery)
- ✅ Authentication Service: Port 8080
- ✅ Customer Service: Port 8080 → Connected to dedicated PostgreSQL DB
- ✅ Consumption Service: Port 5006 → Connected to dedicated PostgreSQL DB
- ✅ Elering Adapter: Port 8080
- ✅ Database Migrations: Ports 7005, 7006 → Connected to respective DBs

## Access Instructions
After deployment, run `./get-service-urls.sh` to get the public URLs of your deployed services.

## Database Architecture
Each microservice has its own dedicated PostgreSQL database following microservices best practices:
- **Consumption Service**: Uses `consumption_service_db`
- **Customer Service**: Uses `customer_service_db`
- **Data Isolation**: Complete database independence between services

Last updated: $(date)
