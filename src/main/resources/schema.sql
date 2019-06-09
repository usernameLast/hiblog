-- ----------------------------
-- Table structure for hb_area
-- ----------------------------
DROP TABLE IF EXISTS `hb_area`;
CREATE TABLE `hb_area` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '地区名字',
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '上级路径ID',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_parentid_vieworder` (`parent_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='统一地区库';

-- ----------------------------
-- Table structure for hb_article
-- ----------------------------
DROP TABLE IF EXISTS `hb_article`;
CREATE TABLE `hb_article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `introduction` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '简介',
  `labels` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '文章标签，逗号分隔',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '文章类型  1原创，2转载，3翻译',
  `category_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '分类',
  `is_private` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '是否是私密文章',
  `visited_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '被访问次数',
  `comment_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '评论次数',
  `top_timestamp` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '置顶时间戳',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(4) NOT NULL DEFAULT '2' COMMENT '状态   0禁用，1草稿，2已发布',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- ----------------------------
-- Table structure for hb_article_category
-- ----------------------------
DROP TABLE IF EXISTS `hb_article_category`;
CREATE TABLE `hb_article_category` (
  `article_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '文章id',
  `category_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '分类id',
  UNIQUE KEY `index` (`article_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类关联表';

-- ----------------------------
-- Table structure for hb_article_collect
-- ----------------------------
DROP TABLE IF EXISTS `hb_article_collect`;
CREATE TABLE `hb_article_collect` (
  `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `article_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '文章id',
  UNIQUE KEY `index` (`uid`,`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章收藏表';

-- ----------------------------
-- Table structure for hb_article_comment
-- ----------------------------
DROP TABLE IF EXISTS `hb_article_comment`;
CREATE TABLE `hb_article_comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `article_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '文章id',
  `article_uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '文章uid',
  `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '发布评论的用户id',
  `reply_uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '被回复的id(默认为发布文章的uid)',
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父级id',
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '0禁用，1正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章评论表';

-- ----------------------------
-- Table structure for hb_authorize
-- ----------------------------
DROP TABLE IF EXISTS `hb_authorize`;
CREATE TABLE `hb_authorize` (
  `role_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '角色id',
  `menu_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '菜单id',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `index` (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='授权表';

-- ----------------------------
-- Table structure for hb_catalogue
-- ----------------------------
DROP TABLE IF EXISTS `hb_catalogue`;
CREATE TABLE `hb_catalogue` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '1/分类，2/文章，3/链接地址',
  `type_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '类型id',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='博客菜单';

-- ----------------------------
-- Table structure for hb_category
-- ----------------------------
DROP TABLE IF EXISTS `hb_category`;
CREATE TABLE `hb_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户id  0为系统分类 >0 为博客用户设置的分类',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `is_show` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '是否显示1，显示，0不显示',
  `sn` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类表';

-- ----------------------------
-- Table structure for hb_follow
-- ----------------------------
DROP TABLE IF EXISTS `hb_follow`;
CREATE TABLE `hb_follow` (
  `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `follow_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '被关注的用户id',
  UNIQUE KEY `index` (`uid`,`follow_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户关注表';

-- ----------------------------
-- Table structure for hb_images
-- ----------------------------
DROP TABLE IF EXISTS `hb_images`;
CREATE TABLE `hb_images` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '地址',
  `original_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图片原始名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='上传图片地址表';

-- ----------------------------
-- Table structure for hb_industry
-- ----------------------------
DROP TABLE IF EXISTS `hb_industry`;
CREATE TABLE `hb_industry` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '行业名称',
  `sn` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户行业表';

-- ----------------------------
-- Table structure for hb_menu
-- ----------------------------
DROP TABLE IF EXISTS `hb_menu`;
CREATE TABLE `hb_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '地址',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '前端路由',
  `parent_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '父级id',
  `auth` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0不需要认证；1需要认证；2管理员菜单',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'icon',
  `sn` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态0隐藏1显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- ----------------------------
-- Table structure for hb_role
-- ----------------------------
DROP TABLE IF EXISTS `hb_role`;
CREATE TABLE `hb_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `describe` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for hb_user
-- ----------------------------
DROP TABLE IF EXISTS `hb_user`;
CREATE TABLE `hb_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `sex` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '性别，0未知，1男，2女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `province_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '省份id',
  `city_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '市id',
  `district_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '区id',
  `introduction` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '简介',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `industry_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '行业id',
  `position` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '职位',
  `follow_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '关注数',
  `fans_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '粉丝数',
  `visited_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '被访问次数',
  `article_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '原创文章发布数量',
  `comment_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '评论数量',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '状态 1正常0禁用',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '加密盐值',
  `role_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '角色id  (0/无角色，即博客用户，1管理员，>1 具体的角色)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
