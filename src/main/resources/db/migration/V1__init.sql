
create table if not exists database_source
(
    id              bigint auto_increment
    primary key,
    datasource_name varchar(255)                      null
    unique,
    url             varchar(255)                      null,
    username        varchar(255)                      null,
    password        varchar(255)                      null,
    driver_class    varchar(255)                      null,
    database_type   varchar(255)                      null,
    db_source      varchar(255) default (database()) null
    );
