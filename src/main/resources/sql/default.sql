create table user (
    id int primary key auto_increment comment '用户编号',
    user_name varchar(255) not null comment '用户名称',
    password varchar(255) not null comment '密码',
    nick_name varchar(255) not null comment '昵称',
    mobile_phone varchar(25) comment '手机号',
    create_time timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '创建时间',
    update_time timestamp null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';



create table action_record(
    id int primary key auto_increment ,
    user_id int comment '用户编号',
    request_url varchar(1024) not null comment '请求地址',
    request longtext not null comment '请求参数',
    response text not null comment '响应数据',
    method varchar(20) comment '请求方式',
    time_take bigint comment '处理耗时',
    ip varchar(40) not null comment 'ip',
    dfp varchar(40) comment '设备指纹',
    `create_time` timestamp default CURRENT_TIMESTAMP comment '创建时间',
    constraint user_id foreign key (user_id) references user(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户行为记录表';