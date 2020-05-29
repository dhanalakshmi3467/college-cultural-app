-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 29, 2020 at 01:39 PM
-- Server version: 10.4.10-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `college`
--

-- --------------------------------------------------------

--
-- Table structure for table `campus_news`
--

DROP TABLE IF EXISTS `campus_news`;
CREATE TABLE IF NOT EXISTS `campus_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(64) NOT NULL,
  `news_by` varchar(64) NOT NULL,
  `info` varchar(645) NOT NULL,
  `date` date NOT NULL,
  `image` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ti` (`title`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `campus_news`
--

INSERT INTO `campus_news` (`id`, `title`, `news_by`, `info`, `date`, `image`) VALUES
(1, 'vicharane drama', 'by:app admin', 'vicharane drama based is social issue and its long time duration of 1.30 hours and drama takes place in different states. ', '2016-11-03', 'http://ncbgudi.com/wp-content/uploads/2016/09/NCBGUDI-Vicharane-Programme-1-768x510.jpg'),
(3, 'Social Work', 'by:app admin', 'our college principle all teachers and students celebrate the gandhi jayanthi by doing the rally with the vartha anad sarvajanika deparment ', '2016-10-02', 'http://ncbgudi.com/wp-content/uploads/2016/09/NCBGUDI-Gandhi-Jayanthi-2.jpg'),
(4, 'Zoology-Museum', 'by: app admin', 'National college has Zoology-Museum which contains all biological stuff and students can see those things and learn about it', '2011-08-23', 'http://ncbgudi.com/wp-content/uploads/2016/09/NCBGUDI-Zoology-Museum-1.jpg'),
(5, 'Hour of Code', 'by:app admin', 'its workshop for all school and pu students\r\nto get basic knowledge by playing games with funny coding.  ', '2013-12-02', 'https://lh3.googleusercontent.com/wtiWTppANhdz01t4_tNnJZwlxetYiVj1xOUpeVYGDrCnp6RQILjiWg0He0AgJBnrGf8cfLDNKFVRjKc0HhG68EayfD7cqMrtk--ZvZ_w99qiqBIHNhKr1gJsoeNI2cOUU-IGKh7ZguGNPYfXcrvJJLFlHYEIL428tmqSJSE8yqDh-dR_Tcf44sI7lV78WbsmGc5OWIhEJMDH3Qk1a87yuYBsWWXxt-Mc5ZSQPCiCcemwBIGZ9pD4knhTF09AhcWWUSHQYaMSBWaR2ZKbfZxfUR_t6cFIdTrokkkjeJDeSm7ml1atuz0ajB4f2GeKpiz7S66oCKXvhaOjc1IPeWMF8o3XcJgB6txmxlhWyqKdoP5UQQCvV5K6-E5CYxPrcqZ6iC74LCRfXziVVQJg4kCkK6vPReFUSzV5AnsjlZidWtSh6AYkeCwMHuksqsdmkUtGZP6Q7AfIwzmyXkuv-GWVDnh41XjAsWZ2KNq7OuaQT6T0Tenc3ydBZhahSQzmgylGrddYJsC2dC1QdblRofl_9VIDz_lK8uM-GPiZEh61KcKPmSoAtOwqUxUvcvB08Jo2nJoSlDYO9zReQUfz2oif2ExS_opxdPSHQipfCZgC5m3l2BOT0O4SlXvaXorHCirqOEExLyLwOv7EzL_HU2ORFr89Vb2oQLDYS8iTwle1N9gaGIwwQQLg35ixJGI_82C66HHTu3V-yducSCTSXTv4qiPaacp9XEQh1FG8oITta7xAKCbUEYM3ZAY=w938-h625-no?authuser=0'),
(2, 'BeediNataka', 'by:app admin', 'beedi nataka its a kind of drama takes place in the ground people sit altogether and witnessed the drama. the drama is about social problems understand the situation to the village people  ', '2015-11-26', 'http://ncbgudi.com/wp-content/uploads/2016/09/NCBGUDI-Beedhi-Nataka-30-300x225.jpg'),
(6, 'Teacher Student Workshop', 'by: app admin', 'A special workshop takes place where teacher and student both have workshop from the research scholars.  ', '2015-06-18', 'http://ncbgudi.com/wp-content/uploads/2016/09/NCBGUDI-Workshop-3.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `dhanu`
--

DROP TABLE IF EXISTS `dhanu`;
CREATE TABLE IF NOT EXISTS `dhanu` (
  `title` varchar(1000) NOT NULL,
  `shortdesc` varchar(5000) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `price` double NOT NULL,
  `image` varchar(2000) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_by` int(11) NOT NULL,
  `approved` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type` (`type`),
  KEY `fk_create_by_id` (`created_by`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `dhanu`
--

INSERT INTO `dhanu` (`title`, `shortdesc`, `date`, `time`, `price`, `image`, `type`, `id`, `created_by`, `approved`) VALUES
('movie songs', 'songs related to movies which have decent meaning and good to hear.', '0000-00-00', '00:00:00', 0, 'https://image.shutterstock.com/image-photo/chikmag...', 1, 1, 15, 1),
('badminton', 'Badminton is a racquet sport played using racquets to hit a shuttlecock across a net. Although it may be played with larger teams, the most common forms of the game are \"singles\" and \"doubles\".', '0000-00-00', '00:00:00', 50, 'https://images.outlookindia.com/public/uploads/gallery/20171030/Badminton-4_20171030_402_602.jpg', 2, 2, 15, 1),
('robotics code', 'A robot competition is an event where the abilities and characteristics of robots may be tested and assessed. Usually they have to beat other robots in order to become the best one.', '0000-00-00', '00:00:00', 200, 'https://i.pinimg.com/originals/8a/b1/51/8ab151f5739b4186ff98bade1973586c.jpg', 3, 3, 20, 1),
('mona acting', 'Mono-action is all about a performance that is done by a sole person or by a single individual.', '0000-00-00', '00:00:00', 80, 'https://cdn.dnaindia.com/sites/default/files/styles/full/public/2018/03/18/661791-mono-acts.jpg', 1, 4, 20, 1),
('jdfkh g', 'gn rtkjhtr', '2020-05-07', '06:59:00', 12153, NULL, 2, 5, 21, 0),
('title1', 'dsauiydusiadhgui', '2020-05-10', '16:00:00', 200, 'siojhfsdklfds', 3, 6, 21, 1);

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
CREATE TABLE IF NOT EXISTS `events` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) NOT NULL,
  `shortdesc` varchar(5000) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `price` double NOT NULL,
  `image` varchar(2000) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `approved` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_type` (`type`),
  KEY `fk_created_by` (`created_by`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`id`, `title`, `shortdesc`, `date`, `time`, `price`, `image`, `type`, `created_by`, `approved`) VALUES
(38, 'classical dance', 'Classical music doesn\'t lack vocals, but rather, it doesn\'t need them. To better explain, popular music .\r\n', '2020-08-19', '12:00:00', 50, 'hfgf', 1, 21, 1),
(40, 'fokdance 1', 'Folk dances are performed for every possible occasion - to celebrate the arrival of seasons, birth of a child, a wedding and festivals, which are a plenty. The folk dances are extremely simple with minimum of steps or movement.', '2020-08-19', '15:00:00', 0, 'rer', 1, 21, 1),
(42, 'BasketBall', 'Basketball is a team sport in which two teams, most commonly of five players each, opposing one another on a rectangular court,', '2020-08-20', '16:00:00', 50, 'fdxd', 2, 21, 1),
(48, 'Flex and Mascot', 'sit back and enjoy the spectular war between the departments, as they put worth creativity and intellect, in the form od a mascot and flex.', '2020-08-21', '18:00:00', 1000000, 'gg', 3, 21, 1),
(55, 'debate', 'time duration is half an hour', '2020-08-19', '05:30:00', 20, NULL, 1, 15, 1),
(56, 'group dance ', 'four people has to participate', '2020-08-19', '05:30:00', 100, NULL, 1, 217, 0),
(57, 'stand up comedy ', 'half hour for one person', '2020-08-19', '03:30:00', 100, NULL, 1, 15, 1),
(63, 'gjrthgt', 'jtrhth', '2020-05-23', '15:42:00', 444, NULL, 1, 15, 1),
(65, 'Robo boat', 'sjndnnzmz sud zmc. dhif rhdjdmd dknd. djrkd dhemd', '2020-05-19', '12:24:00', 200, NULL, 3, 232, 0),
(67, 'Target Shooting ', 'Ever wanted to hold  rifle and fire a bullet. Target Shooting gives you all these experiences. ', '2020-08-20', '12:00:00', 75, '', 2, 15, 1),
(68, 'Code Uncode ', 'Do you have the smart eye in finding out meaning in utter garble?\r\nCan you crack the Da vinci code and discover the holly Frail?\r\nif yes, Code Uncode invites you all! ', '2020-08-21', '14:00:00', 50, NULL, 3, 15, 1),
(69, 'Roborace ', 'its time to assemble your mechanical Usain Bolts at the start of the track.\r\nEach team must have 2 members.', '2020-07-23', '13:00:00', 50, NULL, 3, 15, 1),
(73, 'efdf', 'ftrte c 4ewe4  othuo  tureot  r8tu reio rtior rt u r u rtu  ', '2020-05-22', '17:00:00', 50, NULL, 2, 15, 1),
(74, 'uyuyuy', 'yuyuu', '2020-05-06', '20:49:00', 20, NULL, 3, 15, 1),
(75, 'dnmdhf', 'hxjxkd', '2020-05-31', '12:38:00', 6898, NULL, 1, 20, 1),
(76, 'ghjf', 'fvdgfdgbf', '2020-05-29', '01:30:00', 200, NULL, 1, 15, 1);

-- --------------------------------------------------------

--
-- Table structure for table `events_booked`
--

DROP TABLE IF EXISTS `events_booked`;
CREATE TABLE IF NOT EXISTS `events_booked` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) NOT NULL,
  `event_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`event_id`),
  KEY `fk_booked_event` (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `events_booked`
--

INSERT INTO `events_booked` (`id`, `user_id`, `event_id`) VALUES
(95, 'dhanu@gmail.com', 40),
(91, 'dhanu@gmail.com', 68),
(68, 'sahana@gmail.com', 55),
(74, 'suma@gmail.com', 55),
(73, 'vani@gmail.com', 57);

-- --------------------------------------------------------

--
-- Table structure for table `event_type`
--

DROP TABLE IF EXISTS `event_type`;
CREATE TABLE IF NOT EXISTS `event_type` (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `type` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `event_type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event_type`
--

INSERT INTO `event_type` (`id`, `type`) VALUES
(1, 'CULTURAL'),
(2, 'SPORTS'),
(3, 'TECHNICAL');

-- --------------------------------------------------------

--
-- Table structure for table `recycle`
--

DROP TABLE IF EXISTS `recycle`;
CREATE TABLE IF NOT EXISTS `recycle` (
  `id` int(15) NOT NULL AUTO_INCREMENT,
  `title` varchar(1000) NOT NULL,
  `shortdesc` varchar(5000) NOT NULL,
  `price` double NOT NULL,
  `image` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `recycle`
--

INSERT INTO `recycle` (`id`, `title`, `shortdesc`, `price`, `image`) VALUES
(5, 'movie songs', 'songs related to movies which have decent meaning and good to hear..', 70, 'https://image.shutterstock.com/image-photo/chikmagalur-india-december-21-2018-600w-1282882300.jpg'),
(6, 'mona acting', 'Mono-action is all about a performance that is done by a sole person or by a single individual.', 80, 'https://cdn.dnaindia.com/sites/default/files/styles/full/public/2018/03/18/661791-mono-acts.jpg'),
(3, 'hip hop dance', 'Hip-hop dance refers to street dance styles primarily performed to hip-hop music or that have evolved as part of hip-hop culture.', 50, 'https://media.istockphoto.com/photos/hiphop-dancers-having-training-picture-id827891858'),
(4, 'hindustani music', 'Hindustani classical music is the art music of northern regions of the Indian subcontinent.', 50, 'https://media.gettyimages.com/photos/hindustani-dhrupad-musician-pandit-uday-bhawalkar-sings-with-his-a-picture-id493295479?s=2048x2048'),
(1, 'classical dance', 'Classical music doesn\'t lack vocals, but rather, it doesn\'t need them. To better explain, popular music .\r\n', 50, 'https://static8.depositphotos.com/1052036/956/v/950/depositphotos_9567681-stock-illustration-indian-classical-dancer.jpg'),
(2, 'fok dance ', 'Folk dances are performed for every possible occasion - to celebrate the arrival of seasons, birth of a child, a wedding and festivals, which are a plenty. The folk dances are extremely simple with minimum of steps or movement', 25, 'https://www.culturalindia.net/iliimages/Folk%20Dances.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `uuid` int(11) NOT NULL AUTO_INCREMENT,
  `register_number` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(64) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `rg` (`register_number`),
  UNIQUE KEY `em` (`email`) USING BTREE,
  UNIQUE KEY `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=236 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uuid`, `register_number`, `email`, `username`, `password`, `admin`) VALUES
(15, '98787', 'dhanu@gmail.com', 'dhanu', '12345678', 1),
(19, '388', 'divya@gmail.com', 'divya', '12345678', 0),
(20, 'admin', 'admin@gmail.com', 'admin', 'admin123', 1),
(21, '15s50346', 'syeda@gmail.com', 'syeda', 'syeda123', 0),
(216, '132645', 'sahana@gmail.com', 'sahana', '12345678', 0),
(217, '569375', 'vani@gmail.com', 'vani', 'vani1234', 0),
(218, '36455', 'suma@gmail.com', 'suma', 'suma1234', 0),
(225, '54628', 'varshini@gmail.com', 'varshini', '12345678', 0),
(228, '1242', 'varun@gmail.com', 'varun', '12345678', 0),
(230, '5678', 'ramya@gmail.com', 'ramya', '12345678', 0),
(231, '6958', 'rani@gmail.com', 'rani', '12345678', 0),
(232, '3562', 'lakshmi@gmail.com', 'lakshmi', '12345678', 0),
(233, '8569745123', 'abcxyz@gmail.com', 'abcxyz', 'abcxyz123', 0),
(234, '424548642154', 'yt5rrty@kfjgret.com', 'trtrytrty', 'yrtyrtyt', 0);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `dhanu`
--
ALTER TABLE `dhanu`
  ADD CONSTRAINT `dhanu_ibfk_1` FOREIGN KEY (`type`) REFERENCES `event_type` (`id`),
  ADD CONSTRAINT `fk_create_by_id` FOREIGN KEY (`created_by`) REFERENCES `users` (`uuid`);

--
-- Constraints for table `events`
--
ALTER TABLE `events`
  ADD CONSTRAINT `fk_created_by` FOREIGN KEY (`created_by`) REFERENCES `users` (`uuid`),
  ADD CONSTRAINT `fk_type` FOREIGN KEY (`type`) REFERENCES `event_type` (`id`);

--
-- Constraints for table `events_booked`
--
ALTER TABLE `events_booked`
  ADD CONSTRAINT `fk_booked_by` FOREIGN KEY (`user_id`) REFERENCES `users` (`email`),
  ADD CONSTRAINT `fk_booked_event` FOREIGN KEY (`event_id`) REFERENCES `events` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
