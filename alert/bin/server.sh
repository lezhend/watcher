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

if [ "$1" == "start" ]; then
   start
elif [ "$1" == "stop" ]; then
   stop
elif [ "$1" == "restart" ]; then
   restart
elif [ "$1" == "upgrade" ]; then
   upgrade
else
   echo "e.g. $0 start|stop|restart|upgrade"
fi
