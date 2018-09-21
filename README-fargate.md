# About

Instructions to deploy your spring boot application in fargate.

# Fargate

https://docs.aws.amazon.com/AmazonECS/latest/developerguide/ECS_AWSCLI_Fargate.html

Create cluster: `aws ecs create-cluster --cluster-name springboot-cluster-fargate`

Now we create the log group defined our task definition
Create log group: `aws logs create-log-group --log-group-name awsECSSpringBootFargate-logs`

Permissions and Roles

executionRoleArn - https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task_execution_IAM_role.html
- trusted-entity = ecs-tasks.amazonaws.com 

taskRoleArn - https://docs.aws.amazon.com/AmazonECS/latest/developerguide/task-iam-roles.html#create_task_iam_policy_and_role
- trusted-entity = ecs-tasks.amazonaws.com 


register task: 
`aws ecs register-task-definition --cli-input-json file://./ecs//task-definition-fargate.json`


list task def: `aws ecs list-task-definitions`

aws ec2 describe-subnets \
  --query 'Subnets[?VpcId==`vpc-04295a0d49014d9ef`].{VpcId:VpcId, CidrBlock:CidrBlock, AvailabilityZone:AvailabilityZone, AvailableIpAddressCount:AvailableIpAddressCount, SubnetId:SubnetId}' \
  --output text

-- Run individual task (cant get this going via CLI - can run the task via console)
aws ecs run-task --task-definition awsSpringBootTaskFargate:2 --cluster springboot-cluster-fargate \
  --network-configuration "awsvpcConfiguration={subnets=[subnet-0f905e6d6d6c0a75f,subnet-09dcc6d213dc8cc6b],securityGroups=[sg-0cc339016bd7b3a8c]}"
  
  --network-configuration "awsvpcConfiguration={subnets=[subnet-0c282e68718915368,subnet-0c282e68718915368],securityGroups=[sg-0af46151c0b874ede],assignPublicIp=ENABLED}"

-- as a service

2 methods
full cli
aws ecs create-service --cluster springboot-cluster-fargate \
  --service-name springboot-service-fargate --task-definition awsSpringBootTaskFargate:2 \
  --desired-count 2 --launch-type "FARGATE" \
  --network-configuration "awsvpcConfiguration={subnets=[subnet-0f905e6d6d6c0a75f,subnet-09dcc6d213dc8cc6b],securityGroups=[sg-0cc339016bd7b3a8c]}"
  
- using json file
aws ecs create-service --cli-input-json file://./ecs/service-definition-fargate.json


aws ecs list-services --cluster springboot-cluster-fargate