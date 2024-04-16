DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'ユーザID',
  mail varchar(255) NOT NULL UNIQUE COMMENT 'メールアドレス',
  password varchar(255) NOT NULL COMMENT 'パスワード',
  nickname varchar(255) NOT NULL COMMENT 'ニックネーム',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最終更新時刻',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '登録日'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ユーザ';

DROP TABLE IF EXISTS tweet;
CREATE TABLE tweet (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'フォローID',
  user_id int(11) NOT NULL COMMENT 'ユーザID',
  body varchar(255) NOT NULL COMMENT 'つぶやき',
  created_at datetime NOT NULL DEFAULT current_timestamp() COMMENT '登録日'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='つぶやき';

DROP TABLE IF EXISTS follow;
CREATE TABLE follow (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'フォローID',
  user_id int(11) NOT NULL COMMENT 'ユーザID',
  follow_user_id int(11) NOT NULL COMMENT 'フォローユーザID',
  UNIQUE ( user_id, follow_user_id )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='フォロー';

DROP TABLE IF EXISTS favorite;
CREATE TABLE favorite (
  id int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'お気に入りID',
  user_id int(11) NOT NULL COMMENT 'ユーザID',
  tweet_id int(11) NOT NULL COMMENT 'フォローID',
  modified_at datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最終更新時刻'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='お気に入り';

