FROM chatdata_adm:v1
VOLUME /data
ADD smilingdatasource-0.0.1-SNAPSHOT.jar ai-manage.jar
COPY application-pro.yml /
COPY logback-spring.xml /
EXPOSE 8081
ENTRYPOINT [ "java","-Xms1024m","-Xmx1024m","-jar","/ai-manage.jar","--spring.profiles.active=pro","-c" ]

