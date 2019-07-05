create table question
(
    id int auto_increment,
    title varchar(60),
    description TEXT,
    gmt_create BIGINT,
    gmt_modified BIGINT,
    creator int,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag varchar(100),
    constraint question_pk
        primary key (id)
);