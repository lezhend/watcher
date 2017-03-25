#!/bin/bash
echo "====="$WORKSPACE"==="
echo "Build parent=============="
/bin/bash $1/monitor/bin/build.sh $1 $2
if [ $? -gt 0 ];then
    exit $?
fi
/bin/bash $1/alert/bin/build.sh $1 $2
if [ $? -gt 0 ];then
    exit $?
fi
