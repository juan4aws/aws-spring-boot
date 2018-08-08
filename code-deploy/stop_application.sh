#!/usr/bin/env bash

APP_PID=`jps | grep AwsBasicsApplication | grep -v grep | awk '{print $1}'`

if [[ "" !=  "$APP_PID" ]]; then
  echo "killing $APP_PID"
  kill -9 $APP_PID
fi