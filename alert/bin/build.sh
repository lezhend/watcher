#!/bin/bash
#Constants
MODULE_NAME="alert"
REGION="us-west-2"
REPOSITORY_NAME="watcher/alert"
CLUSTER="cluster"
SERVICE_NAME="alert-springboot"
FAMILY="alert-springboot"
NAME="springboot"
TASKDEFNAME="alert-springboot"

cd ${MODULE_NAME}
$(aws ecr get-login --no-include-email --region us-west-2)
docker build -t v_$BUILD_NUMBER .
docker tag v_$BUILD_NUMBER 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:v_$BUILD_NUMBER
docker tag v_$BUILD_NUMBER 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:latest
docker push 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:v_$BUILD_NUMBER
docker push 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:latest

#Store the repositoryUri as a variable
REPOSITORY_URI=`aws ecr describe-repositories --repository-names ${REPOSITORY_NAME} --region ${REGION} | jq .repositories[].repositoryUri | tr -d '"'`
#Replace the build number and respository URI placeholders with the constants above
sed -e "s;%BUILD_NUMBER%;${BUILD_NUMBER};g" -e "s;%REPOSITORY_URI%;${REPOSITORY_URI};g" taskdef.json > ${NAME}-v_${BUILD_NUMBER}.json
#Register the task definition in the repository
aws ecs register-task-definition --family ${FAMILY} --cli-input-json file://${WORKSPACE}/${MODULE_NAME}/${NAME}-v_${BUILD_NUMBER}.json --region ${REGION}
SERVICES=`aws ecs describe-services --services ${SERVICE_NAME} --cluster ${CLUSTER} --region ${REGION} | jq .failures[]`
#Get latest revision
REVISION=`aws ecs describe-task-definition --task-definition ${TASKDEFNAME} --region ${REGION} | jq .taskDefinition.revision`

#Create or update service
if [ "$SERVICES" == "" ]; then
  echo "entered existing service"
  DESIRED_COUNT=`aws ecs describe-services --services ${SERVICE_NAME} --cluster ${CLUSTER} --region ${REGION} | jq .services[].desiredCount`
  if [ ${DESIRED_COUNT} = "0" ]; then
    DESIRED_COUNT="1"
  fi
  #ready record rollback to previous version number
  aws ecs update-service --cluster ${CLUSTER} --region ${REGION} --service ${SERVICE_NAME} --task-definition ${FAMILY}:${REVISION} --desired-count ${DESIRED_COUNT}
else
  echo "entered new service"
  aws ecs create-service --service-name ${SERVICE_NAME} --desired-count 1 --task-definition ${FAMILY} --cluster ${CLUSTER} --region ${REGION}
fi
