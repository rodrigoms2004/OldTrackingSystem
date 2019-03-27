#! /bin/sh
# /etc/init.d/tomcat8.sh

# The following part always gets executed.
echo "This part always gets executed"

# The following part carries out specific functions depending on arguments.
case "$1" in
  start)
    echo "Starting Tomcat 8"
		sh /opt/tomcat/bin/catalina.sh start
    echo "Tomcat 8 is alive"
    ;;
  stop)
    echo "Stopping Tomcat 8"
    sh /opt/tomcat/bin/catalina.sh stop
    echo "Tomcat 8 is dead"
    ;;
  *)
    echo "Usage: /etc/init.d/tomcat8.sh {start|stop}"
    exit 1
    ;;
esac

exit 0

# tornar executavel e inicializavel no init.d
# chmod 755 /etc/init.d/tomcat8.sh
# update-rc.d tomcat8.sh defaults

