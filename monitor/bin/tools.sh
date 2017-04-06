#!/bin/bash
PRO_NAME="monitor"
VER_NAME="1.0"
PS_NAME="[j]ava .*monitor"


stop(){
  echo "Stop $PRO_NAME ..."
  ps aux|grep "$PS_NAME" |awk '{system("kill -9 "$2)}'
}

start(){
  echo "Start $PRO_NAME..."
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
    echo "$PRO_NAME start install"
    echo "mkdir $PRO_NAME home path ..."
    mkdir -p /opt/$PRO_NAME/logs
    mkdir -p /opt/$PRO_NAME/config
    mkdir -p /opt/$PRO_NAME/

    echo "Downloading $PRO_NAME from S3...."
    aws s3 cp s3://fcasb-data/devops/$PRO_NAME/package/$PRO_NAME-$VER_NAME.jar /opt/$PRO_NAME/

    echo "Downloading $PRO_NAME config from S3...."
    if [ -e "/opt/$PRO_NAME/$PRO_NAME.properties" ]; then
       echo "configuration file already exist"
    else
       aws s3 cp s3://fcasb-data/devops/$PRO_NAME/config/$PRO_NAME.properties /opt/$PRO_NAME/
    fi
    aws s3 cp s3://fcasb-data/devops/$PRO_NAME/config/logback-monitor.xml /opt/$PRO_NAME/
    aws s3 cp s3://fcasb-data/devops/$PRO_NAME/bin/tools.sh /opt/$PRO_NAME/
    aws s3 cp s3://fcasb-data/devops/$PRO_NAME/bin/monitor.task /opt/$PRO_NAME/
    chmod a+x /opt/$PRO_NAME/*.sh
    crontab  /opt/$PRO_NAME/monitor.task
    service crond restart
    echo "$PRO_NAME install complete!"

}

monitor(){
   RE=`ps aux|grep "$PS_NAME"`
  if [ "$RE" == "" ]; then
    start
    echo "$PRO_NAME start ...."
  else
    echo "Already $PRO_NAME is running"
  fi

}
case "$1" in
  monitor)
    monitor
    ;;
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
