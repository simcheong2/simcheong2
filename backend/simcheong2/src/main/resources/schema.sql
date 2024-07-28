CREATE TABLE `user`
(
    `user_id`       int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `input_id`      varchar(100) NOT NULL,
    `email`         varchar(100) NOT NULL,
    `password`      varchar(200) NOT NULL,
    `phone`         varchar(11)  NOT NULL,
    `birth`         DATE NULL,
    `name`          varchar(100) NOT NULL,
    `nickname`      varchar(100) NOT NULL,
    `sex`           ENUM('MALE', 'FEMALE') NULL,
    `introduce`     varchar(100) NULL,
    `profile_image` text NULL,
    `disabled`      tinyint(1) NOT NULL DEFAULT 1,
    `post_visible`  tinyint(1) NOT NULL DEFAULT 1,
    `created_date` DATETIME(6),
    `modified_date` DATETIME(6),
    `created_by` VARCHAR(255),
    `updated_by` VARCHAR(255)
);

CREATE TABLE `post`
(
    `post_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content` text NULL,
    `user_id` int NOT NULL,
    `created_date` DATETIME(6),
    `modified_date` DATETIME(6),
    `created_by` VARCHAR(255),
    `updated_by` VARCHAR(255)
);

CREATE TABLE `comment`
(
    `comment_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `content`    varchar(200) NULL,
    `user_id`    int NOT NULL,
    `post_id`    int NOT NULL
);

CREATE TABLE `image`
(
    `image_id`    int  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `file_url`    text NOT NULL,
    `post_id`     int  NOT NULL,
    `image_text`  text NULL,
    `image_index` int  NOT NULL
);

CREATE TABLE `user_post_like`
(
    `user_post_like_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`           int NOT NULL,
    `post_id`           int NOT NULL
);

CREATE TABLE `follow`
(
    `follow_id`    int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `follower_id`  int NOT NULL,
    `following_id` int NOT NULL
);

CREATE TABLE `user_blame`
(
    `user_blame_id`  int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `blamer_id`      int NOT NULL,
    `blamed_user_id` int NOT NULL
);

CREATE TABLE `comment_blame`
(
    `comment_blame_id`  int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `blamed_comment_id` int NOT NULL,
    `blamer_id`         int NOT NULL
);

CREATE TABLE `post_blame`
(
    `post_blame_id`  int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `blamed_post_id` int NOT NULL,
    `blamer_id`      int NOT NULL
);


ALTER TABLE `post`
    ADD CONSTRAINT `FK_user_TO_post_1` FOREIGN KEY (
                                                    `user_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

ALTER TABLE `comment`
    ADD CONSTRAINT `FK_user_TO_comment_1` FOREIGN KEY (
                                                       `user_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

ALTER TABLE `comment`
    ADD CONSTRAINT `FK_post_TO_comment_1` FOREIGN KEY (
                                                       `post_id`
        )
        REFERENCES `post` (
                           `post_id`
            ) ON DELETE CASCADE;

ALTER TABLE `image`
    ADD CONSTRAINT `FK_post_TO_image_1` FOREIGN KEY (
                                                     `post_id`
        )
        REFERENCES `post` (
                           `post_id`
            ) ON DELETE CASCADE;

ALTER TABLE `user_post_like`
    ADD CONSTRAINT `FK_user_TO_user_post_like_1` FOREIGN KEY (
                                                              `user_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

ALTER TABLE `user_post_like`
    ADD CONSTRAINT `FK_post_TO_user_post_like_1` FOREIGN KEY (
                                                              `post_id`
        )
        REFERENCES `post` (
                           `post_id`
            ) ON DELETE CASCADE;

ALTER TABLE `follow`
    ADD CONSTRAINT `FK_user_TO_follow_1` FOREIGN KEY (
                                                      `follower_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

ALTER TABLE `follow`
    ADD CONSTRAINT `FK_user_TO_follow_2` FOREIGN KEY (
                                                      `following_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

ALTER TABLE `user_blame`
    ADD CONSTRAINT `FK_user_TO_user_blame_1` FOREIGN KEY (
                                                          `blamer_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

ALTER TABLE `user_blame`
    ADD CONSTRAINT `FK_user_TO_user_blame_2` FOREIGN KEY (
                                                          `blamed_user_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

ALTER TABLE `comment_blame`
    ADD CONSTRAINT `FK_comment_TO_comment_blame_1` FOREIGN KEY (
                                                                `blamed_comment_id`
        )
        REFERENCES `comment` (
                              `comment_id`
            ) ON DELETE CASCADE;

ALTER TABLE `comment_blame`
    ADD CONSTRAINT `FK_user_TO_comment_blame_1` FOREIGN KEY (
                                                             `blamer_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

ALTER TABLE `post_blame`
    ADD CONSTRAINT `FK_post_TO_post_blame_1` FOREIGN KEY (
                                                          `blamed_post_id`
        )
        REFERENCES `post` (
                           `post_id`
            ) ON DELETE CASCADE;

ALTER TABLE `post_blame`
    ADD CONSTRAINT `FK_user_TO_post_blame_1` FOREIGN KEY (
                                                          `blamer_id`
        )
        REFERENCES `user` (
                           `user_id`
            ) ON DELETE CASCADE;

