#!/bin/bash
#Constants
K8S_HOME="/opt/fcasb/k8s"
MODULE_NAME="monitor"
SERVICE_NAME="monitor-alert"
REPOSITORY_NAME="/elk/${SERVICE_NAME}"
REPOSITORY_HOST=34.213.137.99:8888
REPOSITORY_URI=$REPOSITORY_HOST$REPOSITORY_NAME

cd alert
docker build -t $SERVICE_NAME .
docker tag $SERVICE_NAME $REPOSITORY_URI:v_$BUILD_NUMBER
docker push $REPOSITORY_URI:v_$BUILD_NUMBER

#Replace the build number and respository URI placeholders with the constants above
sed -e "s;%BUILD_NUMBER%;${BUILD_NUMBER};g" -e "s;%MODULE_NAME%;${MODULE_NAME};g" -e "s;%SERVICE_NAME%;${SERVICE_NAME};g" -e "s;%REPOSITORY_URI%;${REPOSITORY_URI};g" monitor-deploy.yaml > ${SERVICE_NAME}-v_${BUILD_NUMBER}.yaml
#Register the task definition in the repository

ssh k8s-master "mkdir -p $K8S_HOME/$MODULE_NAME/"
scp ${SERVICE_NAME}-v_${BUILD_NUMBER}.yaml k8s-master:$K8S_HOME/$MODULE_NAME/

echo `ssh k8s-master "kubectl apply -f $K8S_HOME/$MODULE_NAME/${SERVICE_NAME}-v_${BUILD_NUMBER}.yaml"`
echo `ssh k8s-master "kubectl rollout status deployment/${SERVICE_NAME} -n elk-test"`
