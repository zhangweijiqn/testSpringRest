#!/bin/sh

# user dir
SHDIR=$(cd "$(dirname "$0")"; pwd)
echo current path:$SHDIR

# if the server is already running
PIDFILE="./start.pid"
if [ -f $PIDFILE ]; then
    if kill -0 `cat $PIDFILE` > /dev/null 2>&1; then
        echo server already running as process `cat $PIDFILE`. 
        exit 0
    fi
fi

# prepare config file and log conf file
if [ $# == 2 ]; then
	confFile="../conf/config.${1}.${2}.json"
	if [ -f $confFile ]; then
		cp $confFile ../conf/config.json
	fi
	confFileLog="../conf/log4j.${1}.${2}.properties"
	if [ -f $confFileLog ]; then
		cp $confFileLog ../conf/log4j.properties
	fi
fi

# exec 
LOGFILE="nohup.out"
nohup java -cp $SHDIR:../conf/:../lib/* com.jd.jddp.dm.server.NettyServer > $LOGFILE &

# wirte pid to file
if [ $? -eq 0 ] 
then
    if /bin/echo -n $! > "$PIDFILE"
    then
        sleep 1
        echo STARTED SUCCESS
    else
        echo FAILED TO WRITE PID
        exit 1
    fi
else
    echo SERVER DID NOT START
    exit 1
fi
