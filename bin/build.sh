#!/bin/bash
echo "====="$WORKSPACE"==="
echo "Build parent=============="
/bin/bash $1/monitor/bin/build.sh $1 $2
#/bin/bash $1/alert/bin/build.sh $1 $2
