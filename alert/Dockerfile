FROM 34.213.137.99:8888/forticasb/ubuntu:latest
RUN mkdir -p /opt/alert/
RUN rm -rf /opt/alert/*
RUN chmod -R 777 /opt/alert
ADD target/classes/application.yml /opt/alert/
ADD target/classes/disconf.properties /opt/alert/
ADD target/alert-1.0.jar /opt/alert/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/alert/alert-1.0.jar"]