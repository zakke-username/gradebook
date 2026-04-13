-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               12.0.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for gradebook
DROP DATABASE IF EXISTS `gradebook`;
CREATE DATABASE IF NOT EXISTS `gradebook` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci */;
USE `gradebook`;

-- Dumping structure for table gradebook.assignment
DROP TABLE IF EXISTS `assignment`;
CREATE TABLE IF NOT EXISTS `assignment` (
  `assignmentId` int(11) NOT NULL AUTO_INCREMENT,
  `maxScore` int(11) NOT NULL,
  `titleEn` varchar(50) NOT NULL,
  `titleFi` varchar(50) NOT NULL,
  `titleFa` varchar(50) NOT NULL,
  `typeEn` varchar(50) NOT NULL,
  `typeFi` varchar(50) NOT NULL,
  `typeFa` varchar(50) NOT NULL,
  `weight` float DEFAULT NULL,
  `courseId` int(11) NOT NULL,
  PRIMARY KEY (`assignmentId`),
  KEY `FKsl31bvfqah909gfpfmh1597nn` (`courseId`),
  CONSTRAINT `FKsl31bvfqah909gfpfmh1597nn` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table gradebook.course
DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course` (
  `courseId` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `nameEn` varchar(50) NOT NULL,
  `nameFi` varchar(50) NOT NULL,
  `nameFa` varchar(50) NOT NULL,
  `teacherId` int(11) NOT NULL,
  PRIMARY KEY (`courseId`),
  KEY `FKfmwmts7ypkg95gfax46r2a2pd` (`teacherId`),
  CONSTRAINT `FKfmwmts7ypkg95gfax46r2a2pd` FOREIGN KEY (`teacherId`) REFERENCES `teacher` (`teacherId`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table gradebook.enrollment
DROP TABLE IF EXISTS `enrollment`;
CREATE TABLE IF NOT EXISTS `enrollment` (
  `enrollmentId` int(11) NOT NULL AUTO_INCREMENT,
  `attendance` float DEFAULT NULL,
  `endDate` date NOT NULL,
  `finalGrade` int(11) DEFAULT NULL,
  `startDate` date NOT NULL,
  `courseId` int(11) NOT NULL,
  `studentId` int(11) NOT NULL,
  PRIMARY KEY (`enrollmentId`),
  KEY `FKdobknq7cffebj1equt6002eu1` (`courseId`),
  KEY `FKf3n3wlip17r774fadqw2q5qq7` (`studentId`),
  CONSTRAINT `FKdobknq7cffebj1equt6002eu1` FOREIGN KEY (`courseId`) REFERENCES `course` (`courseId`) ON DELETE CASCADE,
  CONSTRAINT `FKf3n3wlip17r774fadqw2q5qq7` FOREIGN KEY (`studentId`) REFERENCES `student` (`studentId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table gradebook.grade
DROP TABLE IF EXISTS `grade`;
CREATE TABLE IF NOT EXISTS `grade` (
  `gradeId` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `score` int(11) NOT NULL,
  `assignmentId` int(11) NOT NULL,
  `studentId` int(11) NOT NULL,
  PRIMARY KEY (`gradeId`),
  KEY `FKo3w430gfdycbc6pb3a1ctvsmh` (`assignmentId`),
  KEY `FKp63rttuyq55xx64xn0ph99pqq` (`studentId`),
  CONSTRAINT `FKo3w430gfdycbc6pb3a1ctvsmh` FOREIGN KEY (`assignmentId`) REFERENCES `assignment` (`assignmentId`) ON DELETE CASCADE,
  CONSTRAINT `FKp63rttuyq55xx64xn0ph99pqq` FOREIGN KEY (`studentId`) REFERENCES `student` (`studentId`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table gradebook.student
DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `studentId` int(11) NOT NULL AUTO_INCREMENT,
  `enrollmentYear` int(11) NOT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`studentId`),
  KEY `FK_student-user` (`userId`),
  CONSTRAINT `FK_student-user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FKhvfkot4qwtaw63wcg3vk2uvl5` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table gradebook.teacher
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE IF NOT EXISTS `teacher` (
  `teacherId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`teacherId`),
  KEY `FK_teacher-user` (`userId`),
  CONSTRAINT `FK_teacher-user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `FKqi3qe8vyhe32kywopya9ovyx4` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table gradebook.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(50) NOT NULL,
  `role` varchar(50) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
