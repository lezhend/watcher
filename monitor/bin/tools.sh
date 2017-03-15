#!/bin/bash
PRO_NAME="monitor"
VER_NAME="1.0"
PS_NAME="[m]onitor"

stop(){
  echo "Stop $PRO_NAME ..."
  ps aux|grep $PS_NAME |awk '{system("kill -9 "$2)}'
}

start(){
  echo "Start $BUILD_NAME..."
  nohup java -jar /opt/$PRO_NAME/$PRO_NAME-$VER_NAME.jar >/dev/null &
}

restart(){
   stop
   start
}
upgrade(){
	stop
        echo "Download $PRO_NAME.jar..."
	    aws s3 cp s3://fcasb-data/devops/$PRO_NAME/package/$PRO_NAME-$VER_NAME.jar /opt/$PRO_NAME/
	start
}

install(){
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
}
case "$1" in
  install)
    install
    ;;
  start)
    start
    ;;
  stop)
    stop
    ;;
  restart)
    restart
    ;;
  upgrade)
    upgrade
    RETVAL=$?
    ;;
  *)
    echo $"Usage: $0 {install|start|stop|restart|upgrade}"
    RETVAL=1
esac
exit $RETVAL
