#!/bin/bash
echo "====="$WORKSPACE"==="
mvn clean package
echo "Build parent=============="
/bin/bash $WORKSPACE/alert/bin/build.sh
ALERT_RESULT=$?
if [ $ALERT_RESULT -ne 0 ];then
    exit $ALERT_RESULT
fi
