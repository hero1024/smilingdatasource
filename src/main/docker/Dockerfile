FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD smilingdatasource-0.0.1-SNAPSHOT.jar ai-manage.jar
COPY application-pro.yml /
COPY logback-spring.xml /
EXPOSE 8081
ENTRYPOINT [ "java","-Xms1024m","-Xmx1024m","-jar","/ai-manage.jar","--spring.config.location=/application-pro.yml","-c" ]