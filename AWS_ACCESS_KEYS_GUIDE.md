# AWS Access Keys Guide

## What Your AWS Access Keys Look Like

**AWS_ACCESS_KEY_ID**: `AKIAIOSFODNN7EXAMPLE`
- Always starts with "AKIA"
- 20 characters long
- Example: AKIA1234567890ABCDEF

**AWS_SECRET_ACCESS_KEY**: `wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY`
- 40 characters long
- Contains letters, numbers, and symbols
- Example: abc123def456ghi789jkl012mno345pqr678stu

**AWS_ACCOUNT_ID**: `480241654156`
- Your 12-digit account number
- We already found this: 480241654156

## Step-by-Step Screenshots Guide

1. **Go to IAM Console**: https://console.aws.amazon.com/iam/
2. **Click "Users"** in left sidebar
3. **Click your username**
4. **Click "Security credentials" tab**
5. **Scroll to "Access keys" section**
6. **Click "Create access key"**
7. **Select "Command Line Interface (CLI)"**
8. **Click "Next"**
9. **Add description**: "GitHub Actions for Energy Company"
10. **Click "Create access key"**
11. **COPY BOTH KEYS IMMEDIATELY!**

## Add to GitHub Secrets

After getting your keys, add them to GitHub:

1. Go to: https://github.com/YOUR_USERNAME/energy-company
2. Click: Settings → Secrets and variables → Actions
3. Click: "New repository secret"
4. Add these 3 secrets:

   **Name**: AWS_ACCESS_KEY_ID
   **Value**: AKIA1234567890ABCDEF (your actual key)

   **Name**: AWS_SECRET_ACCESS_KEY  
   **Value**: abc123def456ghi789jkl012mno345pqr678stu (your actual secret)

   **Name**: AWS_ACCOUNT_ID
   **Value**: 480241654156

## Security Tips ⚠️

- ✅ NEVER commit these keys to your code
- ✅ Only store them in GitHub Secrets
- ✅ Use descriptive names for the keys
- ✅ Rotate keys periodically
- ❌ NEVER share them publicly
- ❌ NEVER put them in environment files

## If You Lose Your Secret Key

If you forget to copy the Secret Access Key:
1. Delete the access key you just created
2. Create a new one
3. Copy both values immediately

## Testing Your Keys

Once you add them to GitHub, you can test them by creating a pull request. The GitHub Actions workflow will use these keys to deploy to AWS.
