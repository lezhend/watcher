#!/bin/bash
#Constants
MODULE_NAME="monitor"
REGION="us-west-2"
REPOSITORY_NAME="elk/monitor"
CLUSTER="cluster"
SERVICE_NAME="monitor-service"
FAMILY="alert-springboot"
NAME="springboot"
TASKDEFNAME="alert-springboot"
IS_FIRST=0

cd ${MODULE_NAME}
$(aws ecr get-login --no-include-email --region us-west-2)
docker build -t v_$BUILD_NUMBER .
docker tag v_$BUILD_NUMBER 34.213.137.99:8888/elk/monitor:v_$BUILD_NUMBER
docker push 34.213.137.99:8888/elk/monitor:v_$BUILD_NUMBER


LATEST_RUN_TASK_DEF_VERSION=`aws ecs describe-task-definition --task-definition ${TASKDEFNAME} --region ${REGION} | jq .taskDefinition.revision`

#Store the repositoryUri as a variable
REPOSITORY_URI=`aws ecr describe-repositories --repository-names ${REPOSITORY_NAME} --region ${REGION} | jq .repositories[].repositoryUri | tr -d '"'`
#Replace the build number and respository URI placeholders with the constants above
sed -e "s;%BUILD_NUMBER%;${BUILD_NUMBER};g" -e "s;%REPOSITORY_URI%;${REPOSITORY_URI};g" taskdef.json > ${NAME}-v_${BUILD_NUMBER}.json
#Register the task definition in the repository
aws ecs register-task-definition --family ${FAMILY} --cli-input-json file://${WORKSPACE}/${MODULE_NAME}/${NAME}-v_${BUILD_NUMBER}.json --region ${REGION}
SERVICES=`aws ecs describe-services --services ${SERVICE_NAME} --cluster ${CLUSTER} --region ${REGION} | jq .failures[]`
#Get latest revision
DEPLOY_TASK_DEF=`aws ecs describe-task-definition --task-definition ${TASKDEFNAME} --region ${REGION}`
DEPLOY_REVISION=`echo ${DEPLOY_TASK_DEF} | jq .taskDefinition.revision`
DEPLOY_TASK_DEF_ARN=`echo ${DEPLOY_TASK_DEF} | jq .taskDefinition.taskDefinitionArn`


#Create or update service
if [ "$SERVICES" == "" ]; then
  echo "entered existing service"
  LATEST_RUN_SERVICE=`aws ecs describe-services --services ${SERVICE_NAME} --cluster ${CLUSTER} --region ${REGION}`
  LATEST_RUN_DESIRED_COUNT=`echo ${LATEST_RUN_SERVICE}| jq .services[].desiredCount`
  LATEST_RUN_TASK_DEF_ARN=`echo ${LATEST_RUN_SERVICE} | jq .services[].taskDefinition`

  echo ${LATEST_RUN_TASK_DEF_ARN}
  if [ ${LATEST_RUN_DESIRED_COUNT} = "0" ]; then
    LATEST_RUN_DESIRED_COUNT="1"
  fi
  #ready record rollback to previous version number
  aws ecs update-service --cluster ${CLUSTER} --region ${REGION} --service ${SERVICE_NAME} --task-definition ${FAMILY}:${DEPLOY_REVISION} --desired-count ${LATEST_RUN_DESIRED_COUNT}
else
  echo "entered new service"
  aws ecs create-service --service-name ${SERVICE_NAME} --desired-count 1 --task-definition ${FAMILY} --cluster ${CLUSTER} --region ${REGION}
  IS_FIRST=1
fi

function rollback(){
    echo "start rollback"
    if [ ${IS_FIRST} == 0 ];then
        echo "set image to faild"
        docker tag v_$BUILD_NUMBER 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:failed_v_$BUILD_NUMBER
        docker push 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:failed_v_$BUILD_NUMBER
        echo "update service to task definition to ${FAMILY}:${LATEST_RUN_TASK_DEF_VERSION}"
        aws ecs update-service --cluster ${CLUSTER} --region ${REGION} --service ${SERVICE_NAME} --task-definition ${FAMILY}:${LATEST_RUN_TASK_DEF_VERSION} --desired-count ${LATEST_RUN_DESIRED_COUNT}
        echo "set task definition is inregist ${DEPLOY_TASK_DEF_ARN}"
        aws ecs deregister-task-definition --region ${REGION} --task-definition ${FAMILY}:${DEPLOY_REVISION}
    else
        echo "Don't need rollback"
    fi
}

sleep 10s
NEW_RUN_SERVICE=`aws ecs describe-services --services ${SERVICE_NAME} --cluster ${CLUSTER} --region ${REGION}`
NEW_RUN_TASK_DEF_ARN=`echo ${NEW_RUN_SERVICE}|jq .services[].taskDefinition`
NEW_RUN_TASK_STATUS=`echo ${NEW_RUN_SERVICE}|jq .services[].status`
if [ ${NEW_RUN_TASK_STATUS} != "\"ACTIVE\"" ]; then
   echo "start task faild ${NEW_RUN_TASK_STATUS}"
   rollback
  exit -1;
fi
if [ ${NEW_RUN_TASK_DEF_ARN} != ${DEPLOY_TASK_DEF_ARN} ];then
   echo "start task definition is error : ${NEW_RUN_TASK_DEF_ARN},${DEPLOY_TASK_DEF_ARN}"
   rollback
   exit -1;
fi

echo "Testing"
if [ "1" == "0" ]; then
   rollback
  exit -1;
else
  docker tag v_$BUILD_NUMBER 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:latest
  docker push 482025328369.dkr.ecr.us-west-2.amazonaws.com/watcher/alert:latest
  exit 0
fi
