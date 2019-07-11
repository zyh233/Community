create table notification
(
    id BIGINT auto_increment,
    notifier int not null,
    receiver int not null,
    outerId int not null,
    type int not null,
    gmt_create BIGINT not null,
    status int default 0 not null,
    constraint notification_pk
        primary key (id)
);