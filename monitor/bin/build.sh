#!/bin/bash
PRO_NAME="monitor"
VER_NAME="1.0"
echo "Build module ["$PRO_NAME $VER_NAME"]=============="

mvn clean package -f $1/$PRO_NAME/pom.xml
aws s3 cp $1/$PRO_NAME/target/$PRO_NAME-$VER_NAME.jar s3://fcasb-data/devops/$PRO_NAME/package/$2/
aws s3 cp $1/$PRO_NAME/target/$PRO_NAME-$VER_NAME.jar s3://fcasb-data/devops/$PRO_NAME/package/
aws s3 cp $1/$PRO_NAME/bin/tools.sh s3://fcasb-data/devops/$PRO_NAME/bin/tools.sh
aws s3 cp $1/$PRO_NAME/src/main/resources/$PRO_NAME.properties s3://fcasb-data/devops/$PRO_NAME/config/
aws s3 cp $1/$PRO_NAME/src/main/resources/logback-monitor.xml s3://fcasb-data/devops/$PRO_NAME/config/