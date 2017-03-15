#!/bin/bash
PRO_NAME="alert"
VER_NAME="1.0"
PS_NAME="[a]lert"

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
   #aws s3 cp s3://fcasb-data/devops/alert/install.sh /opt/tools.sh
    echo "Alert start install"
    echo "mkdir alert home path ..."
    mkdir -p /opt/alert/logs

    echo "Downloading alert from S3...."
    aws s3 cp s3://fcasb-data/devops/alert/package/alert-1.0.jar /opt/alert/

    echo "Downloading alert config from S3...."
    aws s3 cp s3://fcasb-data/devops/alert/config/monitor.properties /opt/alert/
    aws s3 cp s3://fcasb-data/devops/alert/config/logback-monitor.xml /opt/alert/
    aws s3 cp s3://fcasb-data/devops/alert/bin/server.sh /opt/alert/
    chmod a+x /opt/alert/*.sh
    echo "Alert install complete!"

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
    echo $"Usage: $0 {start|stop|restart|upgrade}"
    RETVAL=1
esac
exit $RETVAL