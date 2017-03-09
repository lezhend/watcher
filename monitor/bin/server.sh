#!/bin/bash

PRO_NAME="[m]onitor"

stop(){
  echo "Stop Monitor ..."
  ps aux|grep $PRO_NAME |awk '{system("kill -9 "$2)}'
}

start(){
  echo "Start Monitor..."
  nohup java -jar /opt/monitor/monitor-1.0.jar >/dev/null &
}

restart(){
   stop
   start
}
upgrade(){
	stop
        echo "Download monitor.jar..."
	    aws s3 cp s3://fcasb-data/devops/monitor/package/monitor-1.0.jar /opt/monitor/
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
