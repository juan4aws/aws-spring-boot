
### ec2

NOTE: when creating a cluster this way there is no ECS instance in the cluster yet
https://docs.aws.amazon.com/AmazonECS/latest/developerguide/launch_container_instance.html

aws ecs create-cluster --cluster-name springboot-cluster-ec2
{
    "cluster": {
        "status": "ACTIVE", 
        "statistics": [], 
        "clusterName": "springboot-cluster-ec2", 
        "registeredContainerInstancesCount": 0, 
        "pendingTasksCount": 0, 
        "runningTasksCount": 0, 
        "activeServicesCount": 0, 
        "clusterArn": "arn:aws:ecs:us-east-1:249204832379:cluster/springboot-cluster-ec2"
    }
}

aws ecs list-container-instances --cluster springboot-cluster-ec2

aws ecs describe-container-instances --cluster springboot-cluster-ec2 --container-instances 8e311aee-ccac-426b-bdf6-e8d29bc38f10


aws ecs register-task-definition --cli-input-json file://./ecs/task-definition-ec2.json

{
    "taskDefinition": {
        "taskDefinitionArn": "arn:aws:ecs:us-east-1:249204832379:task-definition/awsSpringBootTaskEC2:1",
        "containerDefinitions": [
            {
                "name": "AWSSpringBoot-Container",
                "image": "249204832379.dkr.ecr.us-east-1.amazonaws.com/juan4aws/springbootaws:latest",
                "cpu": 0,
                "portMappings": [
                    {
                        "containerPort": 8080,
                        "hostPort": 8080,
                        "protocol": "tcp"
                    }
                ],
                "essential": true,
                "environment": [],
                "mountPoints": [],
                "volumesFrom": [],
                "logConfiguration": {
                    "logDriver": "awslogs",
                    "options": {
                        "awslogs-group": "awsECSSpringBootEC2-logs",
                        "awslogs-region": "us-east-1",
                        "awslogs-stream-prefix": "awslogs-awsECSSpringBoot"
                    }
                }
            }
        ],
        "family": "awsSpringBootTaskEC2",
        "taskRoleArn": "arn:aws:iam::249204832379:role/ecsSpringBootTaskRole",
        "executionRoleArn": "arn:aws:iam::249204832379:role/ecsSpringBootExecutionRole",
        "networkMode": "awsvpc",
        "revision": 1,
        "volumes": [],
        "status": "ACTIVE",
        "requiresAttributes": [
            {
                "name": "ecs.capability.execution-role-ecr-pull"
            },
            {
                "name": "com.amazonaws.ecs.capability.docker-remote-api.1.18"
            },
            {
                "name": "ecs.capability.task-eni"
            },
            {
                "name": "com.amazonaws.ecs.capability.ecr-auth"
            },
            {
                "name": "com.amazonaws.ecs.capability.task-iam-role"
            },
            {
                "name": "ecs.capability.execution-role-awslogs"
            },
            {
                "name": "com.amazonaws.ecs.capability.logging-driver.awslogs"
            },
            {
                "name": "com.amazonaws.ecs.capability.docker-remote-api.1.19"
            }
        ],
        "placementConstraints": [],
        "compatibilities": [
            "EC2",
            "FARGATE"
        ],
        "requiresCompatibilities": [
            "EC2"
        ],
        "cpu": "256",
        "memory": "512"
    }
}



aws ecs list-task-definitions


aws ecs run-task --task-definition awsSpringBootTaskEC2 --cluster springboot-cluster-ec2 \
  --network-configuration "awsvpcConfiguration={subnets=[subnet-0c282e68718915368,subnet-0c282e68718915368],securityGroups=[sg-0af46151c0b874ede]}"
  
OR-->  --network-configuration "awsvpcConfiguration={subnets=[subnet-0c282e68718915368,subnet-0c282e68718915368],securityGroups=[sg-0af46151c0b874ede],assignPublicIp=ENABLED}"

{
    "tasks": [
        {
            "taskArn": "arn:aws:ecs:us-east-1:249204832379:task/cd10d099-8445-4fe2-9910-2a37c9a58b73",
            "clusterArn": "arn:aws:ecs:us-east-1:249204832379:cluster/springboot-cluster-ec2",
            "taskDefinitionArn": "arn:aws:ecs:us-east-1:249204832379:task-definition/awsSpringBootTaskEC2:1",
            "containerInstanceArn": "arn:aws:ecs:us-east-1:249204832379:container-instance/8e311aee-ccac-426b-bdf6-e8d29bc38f10",
            "overrides": {
                "containerOverrides": [
                    {
                        "name": "AWSSpringBoot-Container"
                    }
                ]
            },
            "lastStatus": "PROVISIONING",
            "desiredStatus": "RUNNING",
            "cpu": "256",
            "memory": "512",
            "containers": [
                {
                    "containerArn": "arn:aws:ecs:us-east-1:249204832379:container/1557b9cf-dc1e-499e-9c1b-19774e549646",
                    "taskArn": "arn:aws:ecs:us-east-1:249204832379:task/cd10d099-8445-4fe2-9910-2a37c9a58b73",
                    "name": "AWSSpringBoot-Container",
                    "lastStatus": "PENDING",
                    "networkInterfaces": []
                }
            ],
            "version": 1,
            "createdAt": 1537189662.823,
            "group": "family:awsSpringBootTaskEC2",
            "launchType": "EC2",
            "attachments": [
                {
                    "id": "10927734-2c5e-4ba2-a4b0-69c962b956df",
                    "type": "ElasticNetworkInterface",
                    "status": "PRECREATED",
                    "details": [
                        {
                            "name": "subnetId",
                            "value": "subnet-0c282e68718915368"
                        }
                    ]
                }
            ]
        }
    ],
    "failures": []
}





aws ecs create-service --cli-input-json file://./service-definition.json
{
    "service": {
        "serviceArn": "arn:aws:ecs:us-east-1:807578712478:service/SpringBootAWS-Service",
        "serviceName": "SpringBootAWS-Service",
        "clusterArn": "arn:aws:ecs:us-east-1:807578712478:cluster/SpringBootAWS-Cluster",
        "loadBalancers": [],
        "serviceRegistries": [],
        "status": "ACTIVE",
        "desiredCount": 1,
        "runningCount": 0,
        "pendingCount": 0,
        "launchType": "FARGATE",
        "platformVersion": "LATEST",
        "taskDefinition": "arn:aws:ecs:us-east-1:807578712478:task-definition/awsspringbootservice:1",
        "deploymentConfiguration": {
            "maximumPercent": 200,
            "minimumHealthyPercent": 0
        },
        "deployments": [
            {
                "id": "ecs-svc/9223370500238926660",
                "status": "PRIMARY",
                "taskDefinition": "arn:aws:ecs:us-east-1:807578712478:task-definition/awsspringbootservice:1",
                "desiredCount": 1,
                "pendingCount": 0,
                "runningCount": 0,
                "createdAt": 1536615849.147,
                "updatedAt": 1536615849.147,
                "launchType": "FARGATE",
                "platformVersion": "1.2.0",
                "networkConfiguration": {
                    "awsvpcConfiguration": {
                        "subnets": [
                            "subnet-0b7af1b3b6d60a56c"
                        ],
                        "securityGroups": [
                            "sg-040e8bdd26afa89d6"
                        ],
                        "assignPublicIp": "ENABLED"
                    }
                }
            }
        ],
        "roleArn": "arn:aws:iam::807578712478:role/aws-service-role/ecs.amazonaws.com/AWSServiceRoleForECS",
        "events": [],
        "createdAt": 1536615849.147,
        "placementConstraints": [],
        "placementStrategy": [],
        "networkConfiguration": {
            "awsvpcConfiguration": {
                "subnets": [
                    "subnet-0b7af1b3b6d60a56c"
                ],
                "securityGroups": [
                    "sg-040e8bdd26afa89d6"
                ],
                "assignPublicIp": "ENABLED"
            }
        },
        "schedulingStrategy": "REPLICA"
    }
}

aws ecs list-services --cluster fargate-cluster