#!/bin/bash
#Constants
MODULE_NAME="alert"
cd ${MODULE_NAME}
REPOSITORY_NAME="/elk/monitor"
SERVICE_NAME="monitor-alert"
REPOSITORY_HOST=34.213.137.99:8888
REPOSITORY_URI=$REPOSITORY_HOST$REPOSITORY_NAME

docker build -t v_$BUILD_NUMBER .
docker tag v_$BUILD_NUMBER $REPOSITORY_URI:v_$BUILD_NUMBER
docker push 34.213.137.99:8888/elk/monitor:v_$BUILD_NUMBER


#Replace the build number and respository URI placeholders with the constants above
sed -e "s;%BUILD_NUMBER%;${BUILD_NUMBER};g" -e "s;%REPOSITORY_URI%;${REPOSITORY_URI};g" monitor-deploy.yaml > ${SERVICE_NAME}-v_${BUILD_NUMBER}.yaml
#Register the task definition in the repository


function rollback(){
    echo "start rollback"
}

echo "Testing"
if [ "1" == "0" ]; then
   rollback
  exit -1;
else
  exit 0
fi
