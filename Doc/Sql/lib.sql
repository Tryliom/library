CREATE DATABASE IF NOT EXISTS library;
USE library;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE IF NOT EXISTS `book` (
  `id` int(15) NOT NULL,
  `title` varchar(2048) NOT NULL,
  `author` varchar(1024) NOT NULL,
  `date` int(5) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `edition` varchar(64) NOT NULL,
  `editeur` varchar(512) NOT NULL,
  `user_id` int(15) DEFAULT NULL,
  `library_id` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `library` (
  `id` int(15) NOT NULL,
  `name` varchar(64) NOT NULL,
  `adress` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `renter` (
  `id` int(15) NOT NULL,
  `book_id` int(15) DEFAULT NULL,
  `user_id` int(15) NOT NULL,
  `taken_date` datetime DEFAULT NULL,
  `max_return_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  `status` int(10) UNSIGNED NOT NULL COMMENT '3: en cours de validation; 2: Pris; 1: Pris en temps supp; 0: Rendu',
  `library_id` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(15) NOT NULL,
  `username` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `lastname` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL,
  `email` varchar(64) NOT NULL,
  `tel` varchar(64) NOT NULL,
  `token` varchar(128) DEFAULT NULL,
  `level_access` int(2) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `name`, `lastname`, `password`, `email`, `tel`, `level_access`) VALUES
('Admin', 'Admin', 'Admin', 'd033e22ae348aeb5660fc2140aec35850c4da997', 'Admin@gmail.com', '0776667772', '', 7),


ALTER TABLE `book`
  ADD PRIMARY KEY (`id`),
  ADD KEY `book_ibfk_1` (`library_id`),
  ADD KEY `book_ibfk_2` (`user_id`);

--
-- Indexes for table `library`
--
ALTER TABLE `library`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `renter`
--
ALTER TABLE `renter`
  ADD PRIMARY KEY (`id`),
  ADD KEY `renter_ibfk_1` (`library_id`),
  ADD KEY `renter_ibfk_2` (`book_id`),
  ADD KEY `renter_ibfk_3` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);


--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `book_ibfk_1` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `renter`
--
ALTER TABLE `renter`
  ADD CONSTRAINT `renter_ibfk_1` FOREIGN KEY (`library_id`) REFERENCES `library` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `renter_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `renter_ibfk_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
