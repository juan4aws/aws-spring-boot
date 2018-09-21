# About 

A Java/Spring Boot project to test AWS services and API's.

# Docker & Spring

https://spring.io/guides/gs/spring-boot-docker/
https://www.callicoder.com/spring-boot-docker-example/


# Running Spring Boot app locally in Docker

Build: `docker build -t juan4aws/aws-spring-boot .`

Run on local dev environment: we have to mount location of aws credentials file.
- `docker run -p 8080:8080 --volume $HOME/.aws:/root/.aws -t juan4aws/aws-spring-boot`

Test
using HTTPie (you can use curl) `http localhost:8080/api/s3/list`

# ECR

Create your repository: `aws ecr create-repository --repository-name juan4aws/springbootaws`

Get repository URL: `aws ecr describe-repositories`

Tag your image: `docker tag juan4aws/aws-spring-boot:latest 674457222324.dkr.ecr.us-east-1.amazonaws.com/juan4aws/springbootaws:latest`

Login in to ECR: `$(aws ecr get-login --no-include-email --region us-east-1)`

Push image to ECR: `docker push 123412341234.dkr.ecr.us-east-1.amazonaws.com/juan4aws/springbootaws:latest`

aws ecr describe-images --repository-name juan4aws/springbootaws


For Fargate:    README-fargate.md
For EC2:        README-ec2.md