# About

A Java/Spring Boot project to test AWS services and API's.


# Setup



# Running


### Manual

* log into you ec2 instance and navigate to location of executable
* run `nohup ./gradlew bootRun >output.log 2>&1 &`

# Code Deploy

This section shows how to use code deploy to automatically release code 
changes to fleet of ec2 instances. 

### appspec.yml

Add appspec.yml at root of project. This file contains instructions on how to 
deploy and validate your application deployment.

### create deployment - cli


```
aws deploy create-deployment \
  --application-name aws-basic \
  --deployment-config-name CodeDeployDefault.OneAtATime \
  --deployment-group-name awsBasicJavaServers \
  --description "Github deployment" \
  --github-location repository=juan4aws/aws-spring-boot,commitId=4e042c36cfcb2513c66a2247977ca1191a5d0ca9
```


##### Code Deploy Reference

* [User Guide: tutorials-github-deploy-application](https://docs.aws.amazon.com/codedeploy/latest/userguide/tutorials-github-deploy-application.html) 
* [User Guide: integrations-partners-github](https://docs.aws.amazon.com/codedeploy/latest/userguide/integrations-partners-github.html)
* [Blog: automatically-deploy-from-github-using-aws-codedeploy](https://aws.amazon.com/blogs/devops/automatically-deploy-from-github-using-aws-codedeploy/)



### Docker

https://spring.io/guides/gs/spring-boot-docker/
https://www.callicoder.com/spring-boot-docker-example/


- `docker build -t juan4aws/aws-spring-boot .`
- `docker run -p 8080:8080 --volume $HOME/.aws:/root/.aws -t juan4aws/aws-spring-boot`




# Logs 

### Viewing logs local ec2 logs 

tail -f /opt/logs/aws-basics/[application.log | ]


#### Cloud Watch Logs - agent setup

Logging config file: /etc/awslogs/awslogs.conf


##### Logs Reference 

[Agent](https://docs.aws.amazon.com/AmazonCloudWatch/latest/monitoring/Install-CloudWatch-Agent.html)


# Issues
A problem occurred evaluating root project 'aws-basics'.
java.lang.UnsupportedClassVersionError: org/springframework/boot/gradle/plugin/SpringBootPlugin : Unsupported major.minor version 52.0

export JAVA_HOME=/usr/lib/jvm/jre-1.8.0-openjdk.x86_64 

to make sure app is compiled using this jdk




# Reference



[Code Deploy User Guide: agent-operations-install-linux](https://docs.aws.amazon.com/codedeploy/latest/userguide/codedeploy-agent-operations-install-linux.html)

[Cloud Watch Docs: QuickStartEC2Instance](https://docs.aws.amazon.com/AmazonCloudWatch/latest/logs/QuickStartEC2Instance.html)

[Cloud Watch Docs: AgentReference](https://docs.aws.amazon.com/AmazonCloudWatch/latest/logs/AgentReference.html)



