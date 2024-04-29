FROM java:8
VOLUME /tmp
ADD smilingdatasource-0.0.1-SNAPSHOT.jar ai-manage.jar
EXPOSE 8081
ENTRYPOINT [ "java","-Xms1024m","-Xmx1024m","-jar","/ai-manage.jar","--spring.profiles.active=pro","-c" ]