FROM 482025328369.dkr.ecr.us-west-2.amazonaws.com/ubuntu-16.04-jvm8:latest
RUN mkdir -p /opt/alert/
RUN rm -rf /opt/alert/*
RUN chmod -R 777 /opt/alert
ADD alert/target/classes/alert.properties /opt/alert/
ADD alert/target/classes/logback.xml /opt/alert/
ADD alert/target/classes/application.yml /opt/alert/
ADD alert/target /opt/alert