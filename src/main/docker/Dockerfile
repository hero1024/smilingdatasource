FROM chatdata_adm:v1
VOLUME /data
VOLUME /file
VOLUME /config
ADD smilingdatasource-0.0.1-SNAPSHOT.jar ai-manage.jar
COPY ./config /config
EXPOSE 8081
ENTRYPOINT [ "java","-Xms1024m","-Xmx1024m","-jar","/ai-manage.jar","--spring.profiles.active=pro","-c" ]

