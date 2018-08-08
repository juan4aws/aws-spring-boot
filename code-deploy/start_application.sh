#!/usr/bin/env bash

# aws s3 cp s3://gradfin-conf/log4j2-spring.xml /opt/gradfin/conf/log4j2-spring.xml
# export SPRING_PROFILES_ACTIVE=production-aws

export JAVA_HOME=/usr/lib/jvm/jre-1.8.0-openjdk.x86_64

cd /home/ec2-user/java-dev/aws-basics
nohup ./gradlew bootRun >output.log 2>&1 &

exit 0