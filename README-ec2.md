
### ec2

NOTE: when creating a cluster this way there is no ECS instance in the cluster yet
https://docs.aws.amazon.com/AmazonECS/latest/developerguide/launch_container_instance.html

aws ecs create-cluster --cluster-name springboot-cluster-ec2

aws ecs list-container-instances --cluster springboot-cluster-ec2

aws ecs describe-container-instances --cluster springboot-cluster-ec2 --container-instances 8e311aee-ccac-426b-bdf6-e8d29bc38f10

aws ecs register-task-definition --cli-input-json file://./ecs/task-definition-ec2.json

aws ecs list-task-definitions


aws ecs run-task --task-definition awsSpringBootTaskEC2 --cluster springboot-cluster-ec2 \
  --network-configuration "awsvpcConfiguration={subnets=[xxxx,xxxx],securityGroups=[xxxx]}"

aws ecs create-service --cli-input-json file://./service-definition.json

aws ecs list-services --cluster fargate-cluster