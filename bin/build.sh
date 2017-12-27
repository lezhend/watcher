#!/bin/bash
echo "====="$WORKSPACE"==="
echo "Build parent=============="

/bin/bash $WORKSPACE/alert/bin/build.sh
if [ $? -gt 0 ];then
    exit $?
fi
