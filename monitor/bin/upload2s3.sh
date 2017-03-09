#!/bin/bash
echo "=============="$WORKSPACE"=============="
aws s3 cp $WORKSPACE/monitor/target/monitor-1.0.jar s3://fcasb-data/devops/monitor/package/$BUILD_TAG/
aws s3 cp $WORKSPACE/monitor/target/monitor-1.0.jar s3://fcasb-data/devops/monitor/package/
aws s3 cp $WORKSPACE/monitor/bin/server.sh s3://fcasb-data/devops/monitor/bin/server.sh
aws s3 cp $WORKSPACE/monitor/bin/install.sh s3://fcasb-data/devops/monitor/install.sh
aws s3 cp $WORKSPACE/monitor/src/main/resources/monitor.properties s3://fcasb-data/devops/monitor/config/
aws s3 cp $WORKSPACE/monitor/src/main/resources/logback-monitor.xml s3://fcasb-data/devops/monitor/config/