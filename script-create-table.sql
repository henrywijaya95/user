CREATE TABLE `user` (
  `id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `age` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
CREATE TABLE `user_contact` (
  `id` varchar(10) NOT NULL,
  `address` varchar(200) NOT NULL,
  `user_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`id`, `name`, `age`)
VALUES
	('1', 'Henry', 15),
	('2', 'Jack', 20),
	('3', 'Lisa', 30),
	('4', 'Romy', 40);

INSERT INTO `user_contact` (`id`, `address`, `user_id`)
VALUES
	('1', 'Solo', '1'),
    ('2', 'Jogjakarta', '2'),
    ('3', 'Jakarta', '3'),
    ('4', 'Surabaya', '4');
	
commit;