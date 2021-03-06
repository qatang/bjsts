CREATE DATABASE `sts_mgr` DEFAULT CHARACTER SET=utf8;

USE `sts_mgr`;

CREATE TABLE `sts_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `salt` VARCHAR(64) NOT NULL,
  `email` VARCHAR(128) NOT NULL,
  `mobile` VARCHAR(32) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `real_name` VARCHAR(32) NOT NULL DEFAULT '',
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_created_time` (`created_time`),
  KEY `idx_updated_time` (`updated_time`),
  KEY `idx_valid` (`valid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(512) DEFAULT NULL,
  `identifier` varchar(32) NOT NULL,
  `is_default` tinyint(2) NOT NULL,
  `name` varchar(32) NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_identifier` (`identifier`),
  KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `identifier` varchar(64) DEFAULT NULL,
  `is_end` tinyint(2) NOT NULL,
  `memo` varchar(512) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `priority` int(11) NOT NULL,
  `tree_level` int(11) NOT NULL,
  `type` tinyint(2) NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `url` varchar(512) DEFAULT NULL,
  `valid` tinyint(2) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `resource_icon` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_created_time` (`created_time`),
  KEY `idx_valid` (`valid`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_resource_id` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `url` varchar(128) DEFAULT NULL,
  `params` text DEFAULT NULL,
  `remote_ip` varchar(255) DEFAULT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_url` (`url`),
  KEY `idx_params` (`params`(255)),
  KEY `idx_created_time` (`created_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_staff` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(20) NOT NULL,
  `real_name` VARCHAR(32) NOT NULL DEFAULT '',
  `male_type` TINYINT(2) NOT NULL,
  `position` VARCHAR(32) NOT NULL DEFAULT '',
  `id_card` VARCHAR(32) NOT NULL DEFAULT '',
  `entry_time` TIMESTAMP NULL DEFAULT NULL,
  `departure_time` TIMESTAMP NULL DEFAULT NULL,
  `education_type` TINYINT(3) NOT NULL,
  `polity_type` TINYINT(3) NOT NULL,
  `birthday` TIMESTAMP NULL DEFAULT NULL,
  `on_job` TINYINT(2) NOT NULL DEFAULT 0,
  `mobile` VARCHAR(32) NOT NULL DEFAULT '',
  `email` VARCHAR(128) NOT NULL DEFAULT '',
  `memo` VARCHAR(256) NOT NULL DEFAULT '',
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_created_time` (`created_time`),
  KEY `idx_updated_time` (`updated_time`),
  KEY `idx_valid` (`valid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_attendance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `staff_id` BIGINT(20) NOT NULL,
  `real_name` VARCHAR(20) NOT NULL,
  `department_id` BIGINT(20) NOT NULL,
  `start_time` TIMESTAMP NOT NULL,
  `time_type` TINYINT(2) NOT NULL,
  `type` TINYINT(2) NOT NULL,
  `memo` VARCHAR (200) NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_social_security` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `real_name` VARCHAR(20) NOT NULL,
  `id_card` VARCHAR(32) NOT NULL DEFAULT '',
  `mobile` VARCHAR(32) NOT NULL DEFAULT '',
  `memo` VARCHAR (200) NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` tinyint(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_type` TINYINT(2) NOT NULL,
  `company_name` VARCHAR(255) NOT NULL DEFAULT '',
  `contract` VARCHAR(255) NOT NULL DEFAULT '',
  `address` VARCHAR(255) NOT NULL DEFAULT '',
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company` varchar(255) DEFAULT NULL,
  `complete_time` TIMESTAMP NULL DEFAULT NULL,
  `contract_amount` bigint(20) DEFAULT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deliver_time` TIMESTAMP NULL DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expect_time` TIMESTAMP NULL DEFAULT NULL,
  `linkman` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pay_amount` bigint(20) DEFAULT NULL,
  `plan_no` varchar(255) NOT NULL,
  `plan_status` int(11) NOT NULL,
  `plan_type` int(11) NOT NULL,
  `price_time` TIMESTAMP NULL DEFAULT NULL,
  `receipt_amount` bigint(20) DEFAULT NULL,
  `source_type` int(11) NOT NULL,
  `updated_time`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` int(11) NOT NULL,
  `document_id` bigint(20) DEFAULT NULL,
  `quoter` VARCHAR(32) NULL DEFAULT NULL,
  `quoter_id` bigint(20) NULL DEFAULT NULL,
  `quote_time` TIMESTAMP NULL DEFAULT NULL,
  `quote_file_id` bigint(20) DEFAULT NULL,
  `booker` VARCHAR(32) NOT NULL,
  `booker_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_plan_trace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `real_name` VARCHAR(20) NOT NULL DEFAULT '',
  `description` VARCHAR(512) NOT NULL,
  `trace_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_contract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(255) NOT NULL DEFAULT '',
  `plan_name` varchar(255) NOT NULL DEFAULT '',
  `contract_no` VARCHAR(255) NOT NULL,
  `status` int(11) NOT NULL,
  `company` varchar(255) DEFAULT NULL,
  `linkman` varchar(255) DEFAULT NULL,
  `sign_time` TIMESTAMP NULL DEFAULT NULL,
  `quality_time` TIMESTAMP NULL DEFAULT NULL,
  `amount` bigint(20) NOT NULL DEFAULT 0,
  `contract_url` bigint(20) DEFAULT NULL,
  `sign` VARCHAR(32) DEFAULT NULL,
  `quality_amount` bigint(20) NOT NULL DEFAULT 0,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_plan_no` (`plan_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_plan_pay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(255) NOT NULL DEFAULT '',
  `contract_no` varchar(255) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `pay_model` VARCHAR(20) NOT NULL DEFAULT '',
  `pay_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `operator_id` bigint(20) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_no` varchar(255) NOT NULL,
  `proposer` VARCHAR(128) NOT NULL DEFAULT '',
  `operator` VARCHAR(128) NOT NULL DEFAULT '',
  `supplier_id` bigint(20) NOT NULL DEFAULT 0,
  `purchase_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` bigint(20) NOT NULL DEFAULT 0,
  `payed_amount` bigint(20) NOT NULL DEFAULT 0,
  `un_payed_amount` bigint(20) NOT NULL DEFAULT 0,
  `invoice_status` int(11) NOT NULL,
  `purchase_contract_url` bigint(20) DEFAULT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  `in_bound` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_purchase_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint(20) NOT NULL DEFAULT 0,
  `supplier_item_id` bigint(20) NOT NULL DEFAULT 0,
  `unit` VARCHAR(128) NOT NULL DEFAULT '',
  `quantity` bigint(20) NOT NULL DEFAULT 0,
  `unit_price` bigint(20) NOT NULL DEFAULT 0,
  `amount` bigint(20) NOT NULL DEFAULT 0,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(128) NOT NULL,
  `product_model` VARCHAR(128) NOT NULL,
  `quantity` bigint(20) NOT NULL DEFAULT 0,
  `receiptor` VARCHAR(128) NOT NULL DEFAULT '',
  `memo` VARCHAR (200) NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_name_model` (`product_name`, `product_model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_out_bound` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(255) NOT NULL,
  `stock_id` bigint(20) NOT NULL DEFAULT 0,
  `quantity` bigint(20) NOT NULL DEFAULT 0,
  `out_bound_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `receiptor` VARCHAR(128) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_in_bound` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_no` varchar(255) NOT NULL,
  `verify` TINYINT(2) NOT NULL,
  `sendee` VARCHAR(128) NOT NULL,
  `sendee_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `memo` VARCHAR (200) NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_purchase_no` (`purchase_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_express` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `express_no` varchar(255) NOT NULL DEFAULT '',
  `shipper` varchar(255) NOT NULL,
  `payer` varchar(255) NOT NULL,
  `cost` bigint(20) NOT NULL,
  `content` varchar(255) NOT NULL DEFAULT '',
  `receiver` varchar(255) NOT NULL DEFAULT '',
  `mobile` varchar(255) NOT NULL DEFAULT '',
  `address` varchar(255) NOT NULL DEFAULT '',
  `company` varchar(255) NOT NULL DEFAULT '',
  `deliver_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `receive_date` TIMESTAMP NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_normal_purchase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_no` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `buyer` VARCHAR(128) NOT NULL,
  `quantity` bigint(20) NOT NULL DEFAULT 0,
  `amount` bigint(20) NOT NULL DEFAULT 0,
  `description` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `purchase_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `id_generator` (
  `sequence`      VARCHAR(32) NOT NULL,
  `current_value` BIGINT      NOT NULL DEFAULT 0,
  `group_key`     VARCHAR(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `url` VARCHAR(255) NOT NULL,
  `group_key` VARCHAR(32) NOT NULL,
  `object_id` VARCHAR(32) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_group_object_id` (`group_key`, `object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(255) NOT NULL,
  `plan_content` VARCHAR(255) NULL DEFAULT NULL,
  `invoice_category` TINYINT(2) NOT NULL,
  `invoice_no` varchar(255) NOT NULL,
  `customer` VARCHAR(128) NOT NULL DEFAULT '',
  `invoice_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` bigint(20) NOT NULL DEFAULT 0,
  `content` VARCHAR(255) NOT NULL DEFAULT '',
  `deduction_date` TIMESTAMP NULL,
  `invoice_url` bigint(20) NOT NULL DEFAULT 0,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_sale_invoice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(255) NOT NULL,
  `plan_content` VARCHAR(255) NULL DEFAULT NULL,
  `invoice_category` TINYINT(2) NOT NULL,
  `invoice_no` varchar(255) NOT NULL,
  `customer` VARCHAR(128) NOT NULL DEFAULT '',
  `invoice_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` bigint(20) NOT NULL DEFAULT 0,
  `content` VARCHAR(255) NOT NULL DEFAULT '',
  `invoice_status` TINYINT(2) NOT NULL,
  `invoice_url` bigint(20) NOT NULL DEFAULT 0,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_plan_manage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(255) NOT NULL,
  `expect_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `plan_execute_status` TINYINT(2) NOT NULL,
  `actual_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_type` TINYINT(2) NOT NULL,
  `company_name` VARCHAR(255) NOT NULL DEFAULT '',
  `owner` VARCHAR(255) NOT NULL DEFAULT '',
  `tel` VARCHAR(255) NOT NULL DEFAULT '',
  `url` VARCHAR(255) NOT NULL DEFAULT '',
  `fax` VARCHAR(255) NOT NULL DEFAULT '',
  `address` VARCHAR(255) NOT NULL DEFAULT '',
  `postcode` VARCHAR(255) NOT NULL DEFAULT '',
  `memo` VARCHAR(255) NOT NULL DEFAULT '',
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_customer_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL,
  `real_name` VARCHAR(255) NOT NULL DEFAULT '',
  `mobile` VARCHAR(255) NOT NULL DEFAULT '',
  `email` VARCHAR(255) NOT NULL DEFAULT '',
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `company` VARCHAR(255) NULL DEFAULT NULL,
  `linkman` VARCHAR(255) NOT NULL DEFAULT '',
  `contact` VARCHAR(255) NOT NULL DEFAULT '',
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `bank_name` VARCHAR(255) NULL DEFAULT NULL,
  `bank_account` VARCHAR(255) NULL DEFAULT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_supplier_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `supplier_id` bigint(20) NOT NULL,
  `product_name` VARCHAR(128) NOT NULL,
  `product_model` VARCHAR(128) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_product_in_bound` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plan_no` varchar(255) NOT NULL,
  `plan_name` varchar(255) NOT NULL DEFAULT '',
  `company` varchar(255) NOT NULL DEFAULT '',
  `contract_no` varchar(255) NOT NULL DEFAULT '',
  `product_name` varchar(255) NOT NULL DEFAULT '',
  `quantity` bigint(20) NOT NULL DEFAULT 0,
  `unit` varchar(255) NOT NULL DEFAULT '',
  `in_bound_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_product_out_bound` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_out_bound_no` varchar(255) NOT NULL,
  `plan_name` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `linkman` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `delivery_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `product_name` varchar(255) NOT NULL,
  `product_model` varchar(255) NOT NULL,
  `quantity` bigint(20) NOT NULL DEFAULT 0,
  `unit` varchar(255) NOT NULL,
  `single_amount` bigint(20) NOT NULL DEFAULT 0,
  `total_amount` bigint(20) NOT NULL DEFAULT 0,
   `operator` VARCHAR(128) NOT NULL,
   `shipper` VARCHAR(128) NOT NULL,
   `memo` VARCHAR(255) NOT NULL DEFAULT '',
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_purchase_pay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_no` varchar(255) NOT NULL DEFAULT '',
  `amount` bigint(20) NOT NULL,
  `payed_amount` bigint(20) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_purchase_no` (`purchase_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sts_purchase_pay_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_pay_id` bigint(20) NOT NULL,
  `purchase_no` varchar(255) NOT NULL DEFAULT '',
  `amount` bigint(20) NOT NULL,
  `pay_model` VARCHAR(20) NOT NULL DEFAULT '',
  `pay_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `invoice_amount` bigint(20) NOT NULL,
  `operator_id` bigint(20) NOT NULL,
  `created_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `valid` TINYINT(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `sts_user` VALUES ('1', 'admin', '9dcffaac6b711a1dba34ce5a4c49ac9a', 'ab41738cd16e16552c11ab79a2a0486a', 'admin@admin.com', '', '2016-12-08 16:23:22', '2016-12-08 16:23:21', '', '1');

INSERT INTO `sts_role` VALUES (1, '2016-05-05 17:00:53', '系统管理员', 'admin', 0, '系统管理员', '2016-05-13 10:57:50', 1);

INSERT INTO `sts_user_role` VALUES (1, 1, 1);

INSERT INTO `sts_resource` VALUES (1, '2016-05-05 17:02:30', null, '0', null, '系统管理', '0', '0', '1', '2016-05-13 11:38:14', null, '1', null, 'menu-icon fa fa-desktop blue');
INSERT INTO `sts_resource` VALUES (2, '2016-05-05 17:04:35', null, '0', null, '用户管理', '0', '1', '1', '2016-05-05 17:05:07', '/user/list', '1', '1', 'menu-icon fa fa-users green');
INSERT INTO `sts_resource` VALUES (3, '2016-05-06 13:19:32', 'sts:user:list', '1', null, '用户列表', '0', '2', '2', '2016-05-06 13:20:04', '/user/list', '1', '2', null);
INSERT INTO `sts_resource` VALUES (4, '2016-05-10 11:09:18', 'sts:user:view', '1', null, '用户查看', '0', '2', '2', '2016-05-10 11:09:51', '/user/view/**', '1', '2', null);
INSERT INTO `sts_resource` VALUES (5, '2016-05-13 13:43:23', 'sts:user:changePassword', '1', null, '密码修改', '0', '2', '2', '2016-05-13 13:43:22', '/user/password/change', '1', '2', null);
INSERT INTO `sts_resource` VALUES (6, '2016-05-13 15:54:44', 'sts:user:role', '1', null, '分配角色', '0', '2', '2', '2016-05-16 11:34:24', '/user/role/allot/**', '1', '2', null);
INSERT INTO `sts_resource` VALUES (7, '2016-05-12 11:48:33', null, '0', null, '角色管理', '0', '1', '1', '2016-05-16 14:45:26', '/role/list', '1', '1', 'menu-icon fa fa-user blue');
INSERT INTO `sts_resource` VALUES (8, '2016-05-12 13:09:21', 'sts:role:list', '1', null, '角色列表', '0', '2', '2', '2016-05-12 13:09:50', '/role/list', '1', '7', null);
INSERT INTO `sts_resource` VALUES (9, '2016-05-12 15:07:24', 'sts:role:view', '1', null, '角色查看', '0', '2', '2', '2016-05-12 15:08:03', '/role/view/**', '1', '7', null);
INSERT INTO `sts_resource` VALUES (10, '2016-05-12 15:09:56', 'sts:role:enable', '1', null, '角色启用', '0', '2', '2', '2016-05-12 15:10:02', '/role/enable/**', '1', '7', null);
INSERT INTO `sts_resource` VALUES (11, '2016-05-12 15:46:57', 'sts:role:disable', '1', null, '角色禁用', '0', '2', '2', '2016-05-12 15:47:24', '/role/disable/**', '1', '7', null);
INSERT INTO `sts_resource` VALUES (12, '2016-05-12 15:48:16', 'sts:role:create', '1', null, '角色添加', '0', '2', '2', '2016-05-12 15:48:42', '/role/create', '1', '7', null);
INSERT INTO `sts_resource` VALUES (13, '2016-05-12 15:49:01', 'sts:role:update', '1', null, '角色修改', '0', '2', '2', '2016-05-12 15:49:21', '/role/update/**', '1', '7', null);
INSERT INTO `sts_resource` VALUES (14, '2016-05-13 17:42:50', 'sts:role:resource', '1', null, '分配权限', '0', '2', '2', '2016-05-16 11:44:02', '/role/resource/allot/**', '1', '7', null);
INSERT INTO `sts_resource` VALUES (15, '2016-05-12 18:42:51', null, '0', null, '资源管理', '0', '1', '1', '2016-05-12 18:43:22', '/resource/list', '1', '1', 'menu-icon fa fa-key orange2');
INSERT INTO `sts_resource` VALUES (16, '2016-05-12 18:45:29', 'sts:resource:list', '1', null, '资源列表', '0', '2', '2', '2016-05-12 18:46:04', '/resource/list', '1', '15', null);
INSERT INTO `sts_resource` VALUES (17, '2016-05-12 18:46:52', 'sts:resource:view', '1', null, '资源查看', '0', '2', '2', '2016-05-12 18:47:22', '/resource/view/**', '1', '15', null);
INSERT INTO `sts_resource` VALUES (18, '2016-05-12 18:47:53', 'sts:resource:enable', '1', null, '资源启用', '0', '2', '2', '2016-05-12 18:48:21', '/resource/enable/**', '1', '15', null);
INSERT INTO `sts_resource` VALUES (19, '2016-05-12 18:48:45', 'sts:resource:disable', '1', null, '资源禁用', '0', '2', '2', '2016-05-12 18:49:10', '/resource/disable/**', '1', '15', null);
INSERT INTO `sts_resource` VALUES (20, '2016-05-12 18:49:36', 'sts:resource:create', '1', null, '资源添加', '0', '2', '2', '2016-05-12 18:50:03', '/resource/create', '1', '15', null);
INSERT INTO `sts_resource` VALUES (21, '2016-05-12 18:50:20', 'sts:resource:update', '1', null, '资源修改', '0', '2', '2', '2016-05-12 18:50:46', '/resource/update/**', '1', '15', null);
INSERT INTO `sts_resource` VALUES (22, '2016-05-13 10:48:42', null, '0', null, '日志管理', '0', '1', '1', '2016-05-13 15:07:24', '/log/list', '1', '1', 'menu-icon fa fa-pencil-square-o grey');
INSERT INTO `sts_resource` VALUES (23, '2016-05-13 10:49:35', 'sts:log:list', '1', null, '日志列表', '0', '2', '2', '2016-05-13 10:54:22', '/log/list', '1', '22', null);
INSERT INTO `sts_resource` VALUES (24, '2016-05-13 10:56:39', 'sts:log:view', '1', null, '日志查看', '0', '2', '2', '2016-05-13 10:56:38', '/log/view/**', '1', '22', null);
INSERT INTO `sts_resource` VALUES ('25', '2016-12-08 16:14:36', 'sts:user:update', '1', '', '用户修改', '0', '2', '2', '2016-12-08 16:16:02', '/user/update/**', '1', '2', '');
INSERT INTO `sts_resource` VALUES ('26', '2016-12-08 16:15:16', 'sts:user:resetPassword', '1', '', '重置密码', '0', '2', '2', '2016-12-08 16:15:16', '/user/password/reset', '1', '2', '');
INSERT INTO `sts_resource` VALUES ('27', '2016-12-08 16:16:35', 'sts:user:create', '1', '', '新增用户', '0', '2', '2', '2016-12-08 16:16:34', '/user/create', '1', '2', '');

INSERT INTO `sts_role_resource` VALUES (1, 1, 1);
INSERT INTO `sts_role_resource` VALUES (2, 1, 2);
INSERT INTO `sts_role_resource` VALUES (3, 1, 3);
INSERT INTO `sts_role_resource` VALUES (4, 1, 4);
INSERT INTO `sts_role_resource` VALUES (5, 1, 5);
INSERT INTO `sts_role_resource` VALUES (6, 1, 6);
INSERT INTO `sts_role_resource` VALUES (7, 1, 7);
INSERT INTO `sts_role_resource` VALUES (8, 1, 8);
INSERT INTO `sts_role_resource` VALUES (9, 1, 9);
INSERT INTO `sts_role_resource` VALUES (10, 1, 10);
INSERT INTO `sts_role_resource` VALUES (11, 1, 11);
INSERT INTO `sts_role_resource` VALUES (12, 1, 12);
INSERT INTO `sts_role_resource` VALUES (13, 1, 13);
INSERT INTO `sts_role_resource` VALUES (14, 1, 14);
INSERT INTO `sts_role_resource` VALUES (15, 1, 15);
INSERT INTO `sts_role_resource` VALUES (16, 1, 16);
INSERT INTO `sts_role_resource` VALUES (17, 1, 17);
INSERT INTO `sts_role_resource` VALUES (18, 1, 18);
INSERT INTO `sts_role_resource` VALUES (19, 1, 19);
INSERT INTO `sts_role_resource` VALUES (20, 1, 20);
INSERT INTO `sts_role_resource` VALUES (21, 1, 21);
INSERT INTO `sts_role_resource` VALUES (22, 1, 22);
INSERT INTO `sts_role_resource` VALUES (23, 1, 23);
INSERT INTO `sts_role_resource` VALUES (24, 1, 24);
INSERT INTO `sts_role_resource` VALUES (25, 1, 25);
INSERT INTO `sts_role_resource` VALUES (26, 1, 26);
INSERT INTO `sts_role_resource` VALUES (27, 1, 27);