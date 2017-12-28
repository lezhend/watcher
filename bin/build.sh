#!/bin/bash
echo "====="$WORKSPACE"==="
mvn clean package
echo "Build parent=============="
/bin/bash $WORKSPACE/alert/bin/build.sh
if [ $? -ne 0 ];then
    exit $?
fi
