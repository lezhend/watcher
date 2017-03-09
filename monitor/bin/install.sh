#!/bin/bash
#aws s3 cp s3://fcasb-data/devops/monitor/install.sh /opt/install_monitor.sh
echo "Monitor start install"
echo "mkdir monitor home path ..."
mkdir -p /opt/monitor/logs

echo "Downloading monitor from S3...."
aws s3 cp s3://fcasb-data/devops/monitor/package/monitor-1.0.jar /opt/monitor/

echo "Downloading monitor config from S3...."
aws s3 cp s3://fcasb-data/devops/monitor/config/monitor.properties /opt/monitor/
aws s3 cp s3://fcasb-data/devops/monitor/config/logback-monitor.xml /opt/monitor/
aws s3 cp s3://fcasb-data/devops/monitor/bin/server.sh /opt/monitor/
chmod a+x /opt/monitor/*.sh
echo "Monitor install complete!"
