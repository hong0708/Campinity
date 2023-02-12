
--CREATE DATABASE IF NOT EXISTS `campinity`;
--USE `campinity`;

CREATE TABLE IF NOT EXISTS `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expired` bit DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `uuid` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE (`name`)
);

CREATE TABLE IF NOT EXISTS `campsite` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `allow_animal` varchar(255) DEFAULT NULL,
  `camp_name` varchar(255) DEFAULT NULL,
  `content_id` int NOT NULL,
  `day_operation` varchar(255) DEFAULT NULL,
  `do_name` varchar(255) DEFAULT NULL,
  `experience_program` varchar(255) DEFAULT NULL,
  `first_image_url` varchar(255) DEFAULT NULL,
  `homepage` varchar(255) DEFAULT NULL,
  `intro` longtext DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `line_intro` varchar(255) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `reserve_type` varchar(255) DEFAULT NULL,
  `sigungu_name` varchar(255) DEFAULT NULL,
  `sub_facility_etc` varchar(255) DEFAULT NULL,
  `uuid` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `glamp_fclty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `fclty_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `industry` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `industry_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `caravan_fclty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `fclty_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `theme` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `theme_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `open_season` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `season_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `amenity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `amenity_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `expired` bit DEFAULT NULL,
  `uuid` char(36) DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`)
);


CREATE TABLE IF NOT EXISTS `answer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `expired` BOOLEAN DEFAULT NULL,
  `uuid` char(36) DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
);

CREATE TABLE IF NOT EXISTS `campsite_and_amenity` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `amenity_id` int DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`),
  FOREIGN KEY (`amenity_id`) REFERENCES `amenity` (`id`)
);


CREATE TABLE IF NOT EXISTS `campsite_and_caravan_fclty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `caravan_fclty_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`caravan_fclty_id`) REFERENCES `caravan_fclty` (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`)
);


CREATE TABLE IF NOT EXISTS `campsite_and_glamp_fclty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `glamp_fclty_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`glamp_fclty_id`) REFERENCES `glamp_fclty` (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`)
);

CREATE TABLE IF NOT EXISTS `campsite_and_industry` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `industry_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`industry_id`) REFERENCES `industry` (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`)
);


CREATE TABLE IF NOT EXISTS `campsite_and_open_season` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `open_season_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`open_season_id`) REFERENCES `open_season` (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`)
);


CREATE TABLE IF NOT EXISTS `campsite_and_theme` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `theme_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`),
  FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`)
);


CREATE TABLE IF NOT EXISTS `campsite_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`)
);


CREATE TABLE IF NOT EXISTS `campsite_scrap` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `scrap_type` bit DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);


CREATE TABLE IF NOT EXISTS `curation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `curation_category` varchar(255) DEFAULT NULL,
  `first_image_path` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `uuid` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `curation_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `curation_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`curation_id`) REFERENCES `curation` (`id`)
);

CREATE TABLE IF NOT EXISTS `curation_scrap` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `curation_id` int DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`curation_id`) REFERENCES `curation` (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);

CREATE TABLE IF NOT EXISTS `fcm_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `hidden_body` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  `appointee_token` varchar(255) DEFAULT NULL,
  `expired` bit NOT NULL,
  `uuid` binary(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);


CREATE TABLE IF NOT EXISTS `fcm_token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `campsite_uuid` varchar(255) DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  `expired_date` date DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);


CREATE TABLE IF NOT EXISTS `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `expired` bit DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `message_category` varchar(255) DEFAULT NULL,
  `uuid` char(36) DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`)
);


CREATE TABLE IF NOT EXISTS `like_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `like_check` bit DEFAULT NULL,
  `message_id` int DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);



CREATE TABLE IF NOT EXISTS `my_collection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `campsite_name` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `expired` bit DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `uuid` char(36) DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);


CREATE TABLE IF NOT EXISTS `review` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `rate` int NOT NULL,
  `uuid` char(36) DEFAULT NULL,
  `campsite_id` int DEFAULT NULL,
  `member_id` int DEFAULT NULL,
  `expired` bit DEFAULT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`campsite_id`) REFERENCES `campsite` (`id`),
  FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
);

