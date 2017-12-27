#!/bin/bash
echo "====="$WORKSPACE"==="
mvn clean package
echo "Build parent=============="
/bin/bash $WORKSPACE/alert/bin/build.sh
if [ $? -gt 0 ];then
    exit $?
fi
