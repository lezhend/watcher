#!/bin/bash
echo "====="$WORKSPACE"==="
echo "Build parent=============="
cd alert
docker build -t alert:v_$BUILD_NUMBER .
docker tag alert:v_$BUILD_NUMBER 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:latest
docker push 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:latest
/bin/bash $1/alert/bin/build.sh
if [ $? -gt 0 ];then
    exit $?
fi
