FROM 482025328369.dkr.ecr.us-west-2.amazonaws.com/ubuntu-16.04-jvm8:latest
RUN mkdir -p /opt/alert/
RUN rm -rf /opt/alert/*
RUN chmod -R 777 /opt/alert
ADD target/classes/application.yml /opt/alert/
ADD target/alert-1.0.jar /opt/alert/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/alert/alert-1.0.jar"]