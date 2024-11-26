create table roles
(
    id   int auto_increment
        primary key,
    name varchar(255) null
);

create table tags
(
    id                int auto_increment
        primary key,
    name              varchar(255) null,
    short_description mediumtext   null
);

create table users
(
    id                int auto_increment
        primary key,
    username          varchar(255) not null,
    email             varchar(255) null,
    role_id           int          null,
    display_name      varchar(50)  null,
    password          varchar(100) null,
    introduction      mediumtext   null,
    created_at        datetime     null,
    avatar_image_path mediumtext   null,
    status            varchar(20)  null,
    constraint UKr43af9ap4edm43mmtq01oddj6
        unique (username),
    constraint email
        unique (email),
    constraint username
        unique (username),
    constraint users_ibfk_1
        foreign key (role_id) references roles (id),
    constraint FKp56c1712k691lhsyewcssf40f
        foreign key (role_id) references roles (id)
);

create table follows
(
    id          int auto_increment
        primary key,
    followed_id int      null,
    follower_id int      null,
    created_at  datetime null,
    constraint FK45sy1jkos9oy1j4by9y7225nm
        foreign key (followed_id) references users (id),
    constraint FKqnkw0cwwh6572nyhvdjqlr163
        foreign key (follower_id) references users (id)
);

create table notifications
(
    id           int auto_increment
        primary key,
    sender_id    int        null,
    receiver_id  int        null,
    content      mediumtext null,
    redirect_url mediumtext null,
    created_at   datetime   null,
    is_read      tinyint(1) null,
    constraint notifications_users_id_fk
        foreign key (receiver_id) references users (id),
    constraint notifications_users_id_fk_2
        foreign key (sender_id) references users (id)
);

create table posts
(
    id            int auto_increment
        primary key,
    title         mediumtext    null,
    content       longtext      null,
    author_id     int           null,
    created_at    datetime      null,
    updated_at    datetime      null,
    views         int default 0 null,
    thumbnail_url mediumtext    null,
    constraint posts_ibfk_1
        foreign key (author_id) references users (id),
    constraint FK6xvn0811tkyo3nfjk2xvqx6ns
        foreign key (author_id) references users (id)
);

create table comments
(
    id          int auto_increment
        primary key,
    post_id     int        null,
    author_id   int        null,
    content     longtext   null,
    parent_id   int        null,
    created_at  datetime   null,
    was_updated tinyint(1) null,
    constraint comments_comments_id_fk
        foreign key (parent_id) references comments (id),
    constraint FKlri30okf66phtcgbe5pok7cc0
        foreign key (parent_id) references comments (id),
    constraint comments_ibfk_1
        foreign key (post_id) references posts (id),
    constraint FKh4c7lvsc298whoyd4w9ta25cr
        foreign key (post_id) references posts (id),
    constraint comments_users_id_fk
        foreign key (author_id) references users (id),
    constraint FKn2na60ukhs76ibtpt9burkm27
        foreign key (author_id) references users (id)
);


create table chat_notifications
(
    id           bigint auto_increment
        primary key,
    content      varchar(255) null,
    recipient_id varchar(255) null,
    sender_id    varchar(255) null,
    read_status  tinyint(1)   null
);

create table chat_rooms
(
    id           bigint auto_increment
        primary key,
    chat_id      varchar(255) not null,
    recipient_id varchar(255) not null,
    sender_id    varchar(255) not null
);

create table comment_votes
(
    comment_id int not null,
    voter_id   int not null,
    vote_type  int null,
    primary key (comment_id, voter_id),
    check (`vote_type` in (1, -(1)))
);

create table post_votes
(
    post_id   int not null,
    voter_id  int not null,
    vote_type int null,
    primary key (post_id, voter_id),
    check (`vote_type` in (1, -(1)))
);




create table forgot_password_tokens
(
    id           int auto_increment
        primary key,
    requestor_id int         null,
    code         varchar(50) null,
    expire_at    datetime    null,
    is_used      tinyint(1)  null,
    constraint forgot_password_tokens_users_id_fk
        foreign key (requestor_id) references users (id)
);



create table posts_tags
(
    post_id int not null,
    tag_id  int not null,
    primary key (post_id, tag_id),
    constraint FK4svsmj4juqu2l8yaw6whr1v4v
        foreign key (tag_id) references tags (id),
    constraint FKcreclgob71ibo58gsm6l5wp6
        foreign key (post_id) references posts (id)
);

create table users_tags
(
    tag_id  int not null,
    user_id int not null,
    constraint FKa8hgm9e4ub2d2unw7kbcqxqi4
        foreign key (tag_id) references tags (id),
    constraint FKovfqrcopo5w1nt0a63k6h2lxt
        foreign key (user_id) references users (id)
);

create table chat_messages
(
    id           bigint auto_increment
        primary key,
    chat_id      varchar(255) null,
    content      varchar(255) null,
    recipient_id varchar(255) null,
    sender_id    varchar(255) null,
    sent_at      datetime     null,
    type         varchar(20)  null
);

insert into roles values
                      (1, 'ADMIN'),
                      (2, 'USER');

