use social_network;

CREATE TABLE `users` (
  `id` int PRIMARY KEY auto_increment,
  `username` nvarchar(50) UNIQUE,
  `password` nvarchar(64),
  `display_name` nvarchar(20),
  `email` nvarchar(50) UNIQUE,
  `introduction` mediumtext,
  `avatar_image_path` nvarchar(50),
  `created_at` datetime,
  `role_id` int,
  `refresh_token` nvarchar(500)
);

CREATE TABLE `follows` (
  `followed_id` int,
  `following_id` int,
  `create_at` datetime,
  PRIMARY KEY (`followed_id`, `following_id`)
);

CREATE TABLE `posts` (
  `id` int PRIMARY KEY auto_increment,
  `title` nvarchar(200),
  `content` longtext,
  `author_id` int,
  `created_at` datetime,
  `updated_at` datetime
);

CREATE TABLE `tags` (
  `id` int PRIMARY KEY auto_increment,
  `name` nvarchar(50)
);

CREATE TABLE `posts_tags` (
  `post_id` int,
  `tag_id` int,
  PRIMARY KEY (`post_id`, `tag_id`)
);

CREATE TABLE `comments` (
  `id` int PRIMARY KEY auto_increment,
  `post_id` int,
  `author_id` int,
  `content` mediumtext,
  `parent_id` int,
  `has_child` bool,
  `created_at` datetime,
  `was_updated` bool
);

CREATE TABLE `post_votes` (
  `post_id` int,
  `voter_id` int,
  `vote_type` int,
  PRIMARY KEY (`post_id`, `voter_id`)
);

CREATE TABLE `comment_votes` (
  `comment_id` int,
  `voter_id` int,
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
  `role_id` int,
  `authority_id` int,
  PRIMARY KEY (`role_id`, `authority_id`)
);

create table users_tags(
    user_id int,
    tag_id int,
    primary key (user_id, tag_id),
    foreign key (user_id) references users(id),
    foreign key (tag_id) references tags(id)
)

ALTER TABLE `follows` ADD FOREIGN KEY (`followed_id`) REFERENCES `users` (`id`);

ALTER TABLE `follows` ADD FOREIGN KEY (`following_id`) REFERENCES `users` (`id`);

ALTER TABLE `posts` ADD FOREIGN KEY (`author_id`) REFERENCES `users` (`id`);

ALTER TABLE `posts_tags` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);

ALTER TABLE `posts_tags` ADD FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`);

ALTER TABLE `post_votes` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);

ALTER TABLE `post_votes` ADD FOREIGN KEY (`voter_id`) REFERENCES `users` (`id`);

ALTER TABLE `comment_votes` ADD FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`);

ALTER TABLE `comment_votes` ADD FOREIGN KEY (`voter_id`) REFERENCES `users` (`id`);

ALTER TABLE `comments` ADD FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`);

ALTER TABLE `roles_authorities` ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

ALTER TABLE `roles_authorities` ADD FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`);

ALTER TABLE `users` ADD FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);

