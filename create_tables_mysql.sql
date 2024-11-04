use social_network;

CREATE TABLE users (
  `id` int PRIMARY KEY auto_increment,
  `username` nvarchar(50) UNIQUE,
  `password` nvarchar(64),
  `display_name` nvarchar(50),
  `email` nvarchar(50) UNIQUE,
  `introduction` mediumtext,
  `avatar_image_path` nvarchar(100),
  `created_at` datetime,
  `role_id` int references roles(id)
);

CREATE TABLE `follows` (
  id int primary key auto_increment,
  `followed_id` int references users(id),
  `follower_id` int references users(id),
  `create_at` datetime
);

CREATE TABLE `posts` (
  `id` int PRIMARY KEY auto_increment,
  `title` mediumtext,
  `content` longtext,
  `author_id` int references users(id),
    views int default 0,
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `tags` (
  `id` int PRIMARY KEY auto_increment,
  `name` nvarchar(50)
);

CREATE TABLE `posts_tags` (
  `post_id` int references posts(id),
  `tag_id` int references tags(id),
  PRIMARY KEY (`post_id`, `tag_id`)
);

CREATE TABLE `comments` (
  `id` int PRIMARY KEY auto_increment,
  `post_id` int references posts(id),
  `author_id` int references users(id),
  `content` mediumtext,
  `parent_id` int references comments(id),
  `has_child` bool,
  `created_at` datetime,
  `was_updated` bool
);

CREATE TABLE `post_votes` (
  `post_id` int references posts(id),
  `voter_id` int references users(id),
  `vote_type` int,
  PRIMARY KEY (`post_id`, `voter_id`)
);

CREATE TABLE `comment_votes` (
  `comment_id` int references comments(id),
  `voter_id` int references users(id),
  `vote_type` int,
  PRIMARY KEY (`comment_id`, `voter_id`)
);

CREATE TABLE `roles` (
  `id` int PRIMARY KEY auto_increment,
  `name` nvarchar(50)
);

CREATE TABLE `authorities` (
  `id` int PRIMARY KEY auto_increment,
  `name` nvarchar(50),
  `api_path` nvarchar(50),
  `http_method` nvarchar(10)
);

CREATE TABLE `roles_authorities` (
  `role_id` int references roles(id),
  `authority_id` int references authorities(id),
  PRIMARY KEY (`role_id`, `authority_id`)
);

create table users_tags(
    user_id int,
    tag_id int,
    primary key (user_id, tag_id),
    foreign key (user_id) references users(id),
    foreign key (tag_id) references tags(id)
);

