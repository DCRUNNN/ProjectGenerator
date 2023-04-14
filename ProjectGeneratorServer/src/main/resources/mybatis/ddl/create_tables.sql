CREATE TABLE `t_users` (
    `user_id` int(9) NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `role_id` int(9) NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=innoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_role` (
    `role_id` int(9) NOT NULL,
    `role_name` varchar(255) NOT NULL,
    PRIMARY KEY (`role_id`)
) ENGINE=innoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_permission` (
    `permission_id` int(9) NOT NULL,
    `resource` varchar(255) NOT NULL,
    `operation` varchar(255) NOT NULL,
    `role_id` int(9) NOT NULL,
    `authority_code` varchar(255) NOT NULL,
    PRIMARY KEY (`permission_id`)
) ENGINE=innoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_param` (
    `id` int(9) NOT NULL AUTO_INCREMENT,
    `name_cn` varchar(255) NOT NULL,
    `name_en` varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL,
    `type` int(9) NOT NULL,
    `field_type` varchar(255) NOT NULL,
    `create_time` varchar(255) NOT NULL,
    `update_time` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=innoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_template` (
    `id` int(9) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL,
    `author` varchar(255) NOT NULL,
    `content_id` int(9) NOT NULL,
    `create_time` varchar(255) NOT NULL,
    `update_time` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=innoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_template_param` (
    `template_id` int(9) NOT NULL,
    `param_id` int(9) NOT NULL
) ENGINE=innoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_template_content` (
    `content_id` int(9) NOT NULL AUTO_INCREMENT,
    `content` longtext NOT NULL,
    PRIMARY KEY (`content_id`)
) ENGINE=innoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `t_log` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `description` varchar(255) NOT NULL,
    `method_name` varchar(255) NOT NULL,
    `params` longtext,
    `log_type` varchar(255) NOT NULL,
    `request_ip` varchar(255) NOT NULL,
    `exception_details` longtext,
    `cost_time` varchar(255) NOT NULL,
    `create_time` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=innoDB DEFAULT CHARSET=utf8mb4