create static website bucket

aws s3 mb s3://mysfitsui

aws s3 website s3://mysfitsui --index-document index.html

aws s3api put-bucket-policy --bucket mysfitsui \
  --policy file://~/environment/aws-modern-application-workshop/module-1/aws-cli/website-bucket-policy.json

aws s3 cp ~/environment/aws-modern-application-workshop/module-1/web/index.html \
  s3://mysfitsui/index.html
  
  
aws cloudformation create-stack --stack-name MythicalMysfitsCoreStack \
  --capabilities CAPABILITY_NAMED_IAM \
  --template-body file://~/environment/aws-modern-application-workshop/module-2/cfn/core.yml
  
  
docker build . -t 807578712478.dkr.ecr.us-east-1.amazonaws.com/mythicalmysfits/service:latest


docker run -p 8080:8080 807578712478.dkr.ecr.us-east-1.amazonaws.com/mythicalmysfits/service:latest


$ aws ecr create-repository --repository-name mythicalmysfits/service
{
    "repository": {
        "registryId": "807578712478", 
        "repositoryName": "mythicalmysfits/service", 
        "repositoryArn": "arn:aws:ecr:us-east-1:807578712478:repository/mythicalmysfits/service", 
        "createdAt": 1536246599.0, 
        "repositoryUri": "807578712478.dkr.ecr.us-east-1.amazonaws.com/mythicalmysfits/service"
    }
}

docker push 807578712478.dkr.ecr.us-east-1.amazonaws.com/mythicalmysfits/service:latest

$ aws ecr describe-images --repository-name mythicalmysfits/service
{
    "imageDetails": [
        {
            "imageSizeInBytes": 208877015, 
            "imageDigest": "sha256:9e0cc268b9ed4948b0e62553c0016f5250882fae0dcac06c0c69336340595376", 
            "imageTags": [
                "latest"
            ], 
            "registryId": "807578712478", 
            "repositoryName": "mythicalmysfits/service", 
            "imagePushedAt": 1536246723.0
        }
    ]
}


$ aws ecs create-cluster --cluster-name MythicalMysfits-Cluster
{
    "cluster": {
        "status": "ACTIVE", 
        "statistics": [], 
        "clusterName": "MythicalMysfits-Cluster", 
        "registeredContainerInstancesCount": 0, 
        "pendingTasksCount": 0, 
        "runningTasksCount": 0, 
        "activeServicesCount": 0, 
        "clusterArn": "arn:aws:ecs:us-east-1:807578712478:cluster/MythicalMysfits-Cluster"
    }
}


$ aws ecs register-task-definition --cli-input-json file://~/environment/aws-modern-application-workshop/module-2/aws-cli/task-definition.json
{
    "taskDefinition": {
        "status": "ACTIVE", 
        "memory": "512", 
        "networkMode": "awsvpc", 
        "family": "mythicalmysfitsservice", 
        "placementConstraints": [], 
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
        "cpu": "256", 
        "executionRoleArn": "arn:aws:iam::807578712478:role/MythicalMysfitsCoreStack-EcsServiceRole-13QQ08XM9WQ2D", 
        "compatibilities": [
            "EC2", 
            "FARGATE"
        ], 
        "volumes": [], 
        "requiresCompatibilities": [
            "FARGATE"
        ], 
        "taskRoleArn": "arn:aws:iam::807578712478:role/MythicalMysfitsCoreStack-ECSTaskRole-1NUQQ16U5B0LB", 
        "taskDefinitionArn": "arn:aws:ecs:us-east-1:807578712478:task-definition/mythicalmysfitsservice:1", 
        "containerDefinitions": [
            {
                "environment": [], 
                "name": "MythicalMysfits-Service", 
                "mountPoints": [], 
                "image": "807578712478.dkr.ecr.us-east-1.amazonaws.com/mythicalmysfits/service:latest", 
                "cpu": 0, 
                "portMappings": [
                    {
                        "protocol": "tcp", 
                        "containerPort": 8080, 
                        "hostPort": 8080
                    }
                ], 
                "logConfiguration": {
                    "logDriver": "awslogs", 
                    "options": {
                        "awslogs-region": "us-east-1", 
                        "awslogs-stream-prefix": "awslogs-mythicalmysfits-service", 
                        "awslogs-group": "mythicalmysfits-logs"
                    }
                }, 
                "essential": true, 
                "volumesFrom": []
            }
        ], 
        "revision": 1
    }
}


$ aws elbv2 create-load-balancer --name mysfits-nlb \
  --scheme internet-facing --type network \
  --subnets subnet-05b1c5e1f9e3231c9 subnet-037abf3f7404f9f17
  
  {
    "LoadBalancers": [
        {
            "IpAddressType": "ipv4", 
            "VpcId": "vpc-06f6a28241980e385", 
            "LoadBalancerArn": "arn:aws:elasticloadbalancing:us-east-1:807578712478:loadbalancer/net/mysfits-nlb/c22833cf1892c876", 
            "State": {
                "Code": "provisioning"
            }, 
            "DNSName": "mysfits-nlb-c22833cf1892c876.elb.us-east-1.amazonaws.com", 
            "LoadBalancerName": "mysfits-nlb", 
            "CreatedTime": "2018-09-06T15:35:14.614Z", 
            "Scheme": "internet-facing", 
            "Type": "network", 
            "CanonicalHostedZoneId": "Z26RNL4JYFTOTI", 
            "AvailabilityZones": [
                {
                    "SubnetId": "subnet-037abf3f7404f9f17", 
                    "ZoneName": "us-east-1a"
                }, 
                {
                    "SubnetId": "subnet-05b1c5e1f9e3231c9", 
                    "ZoneName": "us-east-1b"
                }
            ]
        }
    ]
}

$ aws elbv2 create-target-group --name MythicalMysfits-TargetGroup \
  --port 8080 --protocol TCP --target-type ip \
  --vpc-id vpc-06f6a28241980e385 --health-check-interval-seconds 10 \
  --health-check-path / --health-check-protocol HTTP \
  --healthy-threshold-count 3 --unhealthy-threshold-count 3
  
    {
        "TargetGroups": [
            {
                "HealthCheckPath": "/", 
                "HealthCheckIntervalSeconds": 10, 
                "VpcId": "vpc-06f6a28241980e385", 
                "Protocol": "TCP", 
                "HealthCheckTimeoutSeconds": 6, 
                "TargetType": "ip", 
                "HealthCheckProtocol": "HTTP", 
                "UnhealthyThresholdCount": 3, 
                "HealthyThresholdCount": 3, 
                "TargetGroupArn": "arn:aws:elasticloadbalancing:us-east-1:807578712478:targetgroup/MythicalMysfits-TargetGroup/0ef3f7d254eef5d2", 
                "Matcher": {
                    "HttpCode": "200-399"
                }, 
                "HealthCheckPort": "traffic-port", 
                "Port": 8080, 
                "TargetGroupName": "MythicalMysfits-TargetGroup"
            }
        ]
    }
    
    
$ aws elbv2 create-listener \
  --default-actions TargetGroupArn=arn:aws:elasticloadbalancing:us-east-1:807578712478:targetgroup/MythicalMysfits-TargetGroup/0ef3f7d254eef5d2,Type=forward \
  --load-balancer-arn arn:aws:elasticloadbalancing:us-east-1:807578712478:loadbalancer/net/mysfits-nlb/c22833cf1892c876 \
  --port 80 --protocol TCP
  
  {
    "Listeners": [
        {
            "Protocol": "TCP", 
            "DefaultActions": [
                {
                    "TargetGroupArn": "arn:aws:elasticloadbalancing:us-east-1:807578712478:targetgroup/MythicalMysfits-TargetGroup/0ef3f7d254eef5d2", 
                    "Type": "forward"
                }
            ], 
            "LoadBalancerArn": "arn:aws:elasticloadbalancing:us-east-1:807578712478:loadbalancer/net/mysfits-nlb/c22833cf1892c876", 
            "Port": 80, 
            "ListenerArn": "arn:aws:elasticloadbalancing:us-east-1:807578712478:listener/net/mysfits-nlb/c22833cf1892c876/98857f053e49e7fc"
        }
    ]
  }
    
    
$ aws ecs create-service --cli-input-json file://~/environment/aws-modern-application-workshop/module-2/aws-cli/service-definition.json
{
    "service": {
        "status": "ACTIVE", 
        "taskDefinition": "arn:aws:ecs:us-east-1:807578712478:task-definition/mythicalmysfitsservice:1", 
        "pendingCount": 0, 
        "launchType": "FARGATE", 
        "loadBalancers": [
            {
                "containerName": "MythicalMysfits-Service", 
                "targetGroupArn": "arn:aws:elasticloadbalancing:us-east-1:807578712478:targetgroup/MythicalMysfits-TargetGroup/0ef3f7d254eef5d2", 
                "containerPort": 8080
            }
        ], 
        "roleArn": "arn:aws:iam::807578712478:role/aws-service-role/ecs.amazonaws.com/AWSServiceRoleForECS", 
        "placementConstraints": [], 
        "createdAt": 1536249133.507, 
        "desiredCount": 1, 
        "networkConfiguration": {
            "awsvpcConfiguration": {
                "subnets": [
                    "subnet-05459f43a888a7d50", 
                    "subnet-05a3bfaf5b7b32d4e"
                ], 
                "securityGroups": [
                    "sg-0f3ff2363708a5625"
                ], 
                "assignPublicIp": "DISABLED"
            }
        }, 
        "platformVersion": "LATEST", 
        "serviceName": "MythicalMysfits-Service", 
        "clusterArn": "arn:aws:ecs:us-east-1:807578712478:cluster/MythicalMysfits-Cluster", 
        "serviceArn": "arn:aws:ecs:us-east-1:807578712478:service/MythicalMysfits-Service", 
        "deploymentConfiguration": {
            "maximumPercent": 200, 
            "minimumHealthyPercent": 0
        }, 
        "deployments": [
            {
                "status": "PRIMARY", 
                "networkConfiguration": {
                    "awsvpcConfiguration": {
                        "subnets": [
                            "subnet-05459f43a888a7d50", 
                            "subnet-05a3bfaf5b7b32d4e"
                        ], 
                        "securityGroups": [
                            "sg-0f3ff2363708a5625"
                        ], 
                        "assignPublicIp": "DISABLED"
                    }
                }, 
                "pendingCount": 0, 
                "launchType": "FARGATE", 
                "createdAt": 1536249133.507, 
                "desiredCount": 1, 
                "taskDefinition": "arn:aws:ecs:us-east-1:807578712478:task-definition/mythicalmysfitsservice:1", 
                "updatedAt": 1536249133.507, 
                "platformVersion": "1.1.0", 
                "id": "ecs-svc/9223370500605642300", 
                "runningCount": 0
            }
        ], 
        "events": [], 
        "runningCount": 0, 
        "placementStrategy": []
    }
}


aws s3 cp ~/environment/aws-modern-application-workshop/module-2/web/index.html s3://mysfitsui/index.html


****** Creating the CI/CD pipeline

aws s3 mb s3://mysfitscicd-artifacts

aws s3api put-bucket-policy --bucket mysfitscicd-artifacts \
  --policy file://~/environment/aws-modern-application-workshop/module-2/aws-cli/artifacts-bucket-policy.json
  

$ aws codecommit create-repository --repository-name MythicalMysfitsService-Repository

{
    "repositoryMetadata": {
        "repositoryName": "MythicalMysfitsService-Repository", 
        "cloneUrlSsh": "ssh://git-codecommit.us-east-1.amazonaws.com/v1/repos/MythicalMysfitsService-Repository", 
        "lastModifiedDate": 1536251056.818, 
        "repositoryId": "a6f97490-6076-4e38-83b1-e624f76bfa39", 
        "cloneUrlHttp": "https://git-codecommit.us-east-1.amazonaws.com/v1/repos/MythicalMysfitsService-Repository", 
        "creationDate": 1536251056.818, 
        "Arn": "arn:aws:codecommit:us-east-1:807578712478:MythicalMysfitsService-Repository", 
        "accountId": "807578712478"
    }
}

aws codebuild create-project \
  --cli-input-json file://~/environment/aws-modern-application-workshop/module-2/aws-cli/code-build-project.json
  
{
    "project": {
        "timeoutInMinutes": 60, 
        "name": "MythicalMysfitsServiceCodeBuildProject", 
        "serviceRole": "arn:aws:iam::807578712478:role/MythicalMysfitsServiceCodeBuildServiceRole", 
        "created": 1536252735.219, 
        "artifacts": {
            "packaging": "NONE", 
            "type": "NO_ARTIFACTS", 
            "name": "MythicalMysfitsServiceCodeBuildProject"
        }, 
        "lastModified": 1536252735.219, 
        "cache": {
            "type": "NO_CACHE"
        }, 
        "environment": {
            "computeType": "BUILD_GENERAL1_SMALL", 
            "privilegedMode": true, 
            "image": "aws/codebuild/python:3.5.2", 
            "type": "LINUX_CONTAINER", 
            "environmentVariables": [
                {
                    "type": "PLAINTEXT", 
                    "name": "AWS_ACCOUNT_ID", 
                    "value": "807578712478"
                }, 
                {
                    "type": "PLAINTEXT", 
                    "name": "AWS_DEFAULT_REGION", 
                    "value": "us-east-1"
                }
            ]
        }, 
        "source": {
            "type": "CODECOMMIT", 
            "location": "https://git-codecommit.us-east-1.amazonaws.com/v1/repos/MythicalMysfitsService-Repository"
        }, 
        "badge": {
            "badgeEnabled": false
        }, 
        "encryptionKey": "arn:aws:kms:us-east-1:807578712478:alias/aws/s3", 
        "arn": "arn:aws:codebuild:us-east-1:807578712478:project/MythicalMysfitsServiceCodeBuildProject"
    }
}


aws codepipeline create-pipeline --cli-input-json file://~/environment/aws-modern-application-workshop/module-2/aws-cli/code-pipeline.json

{
    "pipeline": {
        "roleArn": "arn:aws:iam::807578712478:role/MythicalMysfitsServiceCodePipelineServiceRole", 
        "stages": [
            {
                "name": "Source", 
                "actions": [
                    {
                        "inputArtifacts": [], 
                        "name": "Source", 
                        "actionTypeId": {
                            "category": "Source", 
                            "owner": "AWS", 
                            "version": "1", 
                            "provider": "CodeCommit"
                        }, 
                        "outputArtifacts": [
                            {
                                "name": "MythicalMysfitsService-SourceArtifact"
                            }
                        ], 
                        "configuration": {
                            "BranchName": "master", 
                            "RepositoryName": "MythicalMysfitsService-Repository"
                        }, 
                        "runOrder": 1
                    }
                ]
            }, 
            {
                "name": "Build", 
                "actions": [
                    {
                        "inputArtifacts": [
                            {
                                "name": "MythicalMysfitsService-SourceArtifact"
                            }
                        ], 
                        "name": "Build", 
                        "actionTypeId": {
                            "category": "Build", 
                            "owner": "AWS", 
                            "version": "1", 
                            "provider": "CodeBuild"
                        }, 
                        "outputArtifacts": [
                            {
                                "name": "MythicalMysfitsService-BuildArtifact"
                            }
                        ], 
                        "configuration": {
                            "ProjectName": "MythicalMysfitsServiceCodeBuildProject"
                        }, 
                        "runOrder": 1
                    }
                ]
            }, 
            {
                "name": "Deploy", 
                "actions": [
                    {
                        "inputArtifacts": [
                            {
                                "name": "MythicalMysfitsService-BuildArtifact"
                            }
                        ], 
                        "name": "Deploy", 
                        "actionTypeId": {
                            "category": "Deploy", 
                            "owner": "AWS", 
                            "version": "1", 
                            "provider": "ECS"
                        }, 
                        "outputArtifacts": [], 
                        "configuration": {
                            "ClusterName": "MythicalMysfits-Cluster", 
                            "ServiceName": "MythicalMysfits-Service", 
                            "FileName": "imagedefinitions.json"
                        }, 
                        "runOrder": 1
                    }
                ]
            }
        ], 
        "artifactStore": {
            "type": "S3", 
            "location": "mysfitscicd-artifacts"
        }, 
        "name": "MythicalMysfitsServiceCICDPipeline", 
        "version": 1
    }
}


aws ecr set-repository-policy --repository-name mythicalmysfits/service \
  --policy-text file://~/environment/aws-modern-application-workshop/module-2/aws-cli/ecr-policy.json
  
  
  
***** Cognito

aws cognito-idp create-user-pool --pool-name MysfitsUserPool --auto-verified-attributes email

{
    "UserPool": {
        "SchemaAttributes": [
            {
                "Name": "sub", 
                "StringAttributeConstraints": {
                    "MinLength": "1", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": true, 
                "AttributeDataType": "String", 
                "Mutable": false
            }, 
            {
                "Name": "name", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "given_name", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "family_name", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "middle_name", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "nickname", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "preferred_username", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "profile", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "picture", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "website", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "email", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "AttributeDataType": "Boolean", 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "Name": "email_verified", 
                "Mutable": true
            }, 
            {
                "Name": "gender", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "birthdate", 
                "StringAttributeConstraints": {
                    "MinLength": "10", 
                    "MaxLength": "10"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "zoneinfo", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "locale", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "phone_number", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "AttributeDataType": "Boolean", 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "Name": "phone_number_verified", 
                "Mutable": true
            }, 
            {
                "Name": "address", 
                "StringAttributeConstraints": {
                    "MinLength": "0", 
                    "MaxLength": "2048"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "String", 
                "Mutable": true
            }, 
            {
                "Name": "updated_at", 
                "NumberAttributeConstraints": {
                    "MinValue": "0"
                }, 
                "DeveloperOnlyAttribute": false, 
                "Required": false, 
                "AttributeDataType": "Number", 
                "Mutable": true
            }
        ], 
        "MfaConfiguration": "OFF", 
        "Name": "MysfitsUserPool", 
        "VerificationMessageTemplate": {
            "DefaultEmailOption": "CONFIRM_WITH_CODE"
        }, 
        "LastModifiedDate": 1536428864.959, 
        "AdminCreateUserConfig": {
            "UnusedAccountValidityDays": 7, 
            "AllowAdminCreateUserOnly": false
        }, 
        "EmailConfiguration": {}, 
        "AutoVerifiedAttributes": [
            "email"
        ], 
        "Policies": {
            "PasswordPolicy": {
                "RequireLowercase": true, 
                "RequireSymbols": true, 
                "RequireNumbers": true, 
                "MinimumLength": 8, 
                "RequireUppercase": true
            }
        }, 
        "CreationDate": 1536428864.959, 
        "EstimatedNumberOfUsers": 0, 
        "Id": "us-east-1_EWwlF7qpV", 
        "LambdaConfig": {}
    }
}

aws cognito-idp create-user-pool-client --user-pool-id us-east-1_EWwlF7qpV --client-name MysfitsUserPoolClient

    {
        "UserPoolClient": {
            "UserPoolId": "us-east-1_EWwlF7qpV", 
            "LastModifiedDate": 1536428985.507, 
            "ClientId": "1eq20mu2cpfor96v18mih8ruqa", 
            "AllowedOAuthFlowsUserPoolClient": false, 
            "RefreshTokenValidity": 30, 
            "CreationDate": 1536428985.507, 
            "ClientName": "MysfitsUserPoolClient"
        }
    }

aws apigateway create-vpc-link --name MysfitsApiVpcLink \
  --target-arns arn:aws:elasticloadbalancing:us-east-1:807578712478:loadbalancer/net/mysfits-nlb/c22833cf1892c876
  
    {
        "status": "PENDING", 
        "targetArns": [
            "arn:aws:elasticloadbalancing:us-east-1:807578712478:loadbalancer/net/mysfits-nlb/c22833cf1892c876"
        ], 
        "id": "na4md7", 
        "name": "MysfitsApiVpcLink"
    }



aws apigateway import-rest-api --parameters endpointConfigurationTypes=REGIONAL \
  --body file://~/environment/aws-modern-application-workshop/module-4/aws-cli/api-swagger.json \
  --fail-on-warnings
  
 {
    "name": "MysfitsApi", 
    "endpointConfiguration": {
        "types": [
            "REGIONAL"
        ]
    }, 
    "id": "yo3g2hy8n4", 
    "createdDate": 1536429850
}


aws apigateway create-deployment --rest-api-id yo3g2hy8n4 \
  --stage-name prod
  
{
    "id": "3z6xrc", 
    "createdDate": 1536430043
}


curl https://yo3g2hy8n4.execute-api.us-east-1.amazonaws.com/prod/mysfits


aws s3 cp --recursive ~/environment/aws-modern-application-workshop/module-4/web/ s3://mysfitsui/