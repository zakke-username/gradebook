-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.5.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping data for table gradebook.assignment: ~16 rows (approximately)
INSERT INTO `assignment` (`assignmentId`, `maxScore`, `title`, `type`, `weight`, `courseId`) VALUES
	(1, 5, 'Week 1 homework', 'Homework', 12.5, 1),
	(2, 5, 'Week 2 homework', 'Homework', 12.5, 1),
	(3, 5, 'Week 3 homework', 'Homework', 12.5, 1),
	(4, 5, 'Week 4 homework', 'Homework', 12.5, 1),
	(5, 30, 'Exam 1', 'Exam', 25, 1),
	(6, 30, 'Exam 2', 'Exam', 25, 1),
	(7, 6, '1: Variables and data types', 'Homework', 14, 2),
	(8, 6, '2: Operators and expressions', 'Homework', 14, 2),
	(9, 6, '3: Functions and methods', 'Homework', 14, 2),
	(10, 6, '4: Input and output', 'Homework', 14, 2),
	(11, 6, '5: Loops', 'Homework', 14, 2),
	(12, 40, 'Programming exam', 'Exam', 30, 2),
	(13, 10, 'Exercise 1', 'Homework', 25, 3),
	(14, 10, 'Exercise 2', 'Homework', 25, 3),
	(15, 10, 'Exercise 3', 'Homework', 25, 3),
	(16, 10, 'Exercise 4', 'Homework', 25, 3);

-- Dumping data for table gradebook.course: ~3 rows (approximately)
INSERT INTO `course` (`courseId`, `code`, `name`, `teacherId`) VALUES
	(1, 'MATH25', 'Math basics', 1),
	(2, 'PTH101', 'Python 101', 2),
	(3, 'CYBR25', 'Cybersecurity basics', 1);

-- Dumping data for table gradebook.enrollment: ~8 rows (approximately)
INSERT INTO `enrollment` (`enrollmentId`, `attendance`, `endDate`, `finalGrade`, `startDate`, `courseId`, `studentId`) VALUES
	(1, 10, '2026-03-01', NULL, '2026-01-01', 1, 1),
	(2, 30, '2026-03-01', NULL, '2026-01-01', 1, 2),
	(3, 30, '2026-03-01', NULL, '2026-01-01', 1, 3),
	(4, 25, '2026-03-01', NULL, '2026-01-01', 1, 4),
	(5, 30, '2026-03-01', NULL, '2026-01-01', 2, 1),
	(6, 20, '2026-03-01', NULL, '2026-01-01', 2, 4),
	(7, 30, '2026-03-01', NULL, '2026-01-01', 2, 5),
	(8, 100, '2025-11-29', 5, '2025-08-11', 3, 5);

-- Dumping data for table gradebook.grade: ~11 rows (approximately)
INSERT INTO `grade` (`gradeId`, `date`, `score`, `assignmentId`, `studentId`) VALUES
	(1, '2026-07-01', 5, 1, 1),
	(2, '2026-07-01', 6, 7, 2),
	(3, '2026-07-01', 6, 7, 3),
	(4, '2026-07-01', 3, 7, 4),
	(5, '2026-07-01', 5, 7, 4),
	(6, '2026-07-01', 3, 1, 2),
	(7, '2026-07-01', 4, 1, 3),
	(8, '2025-08-12', 10, 13, 5),
	(9, '2025-09-01', 10, 14, 5),
	(10, '2025-10-01', 10, 15, 5),
	(11, '2025-11-01', 10, 16, 5);

-- Dumping data for table gradebook.student: ~5 rows (approximately)
INSERT INTO `student` (`studentId`, `enrollmentYear`, `firstName`, `lastName`, `userId`) VALUES
	(1, 2025, 'Jane', 'Doe', 11),
	(2, 2025, 'Alice', 'Smith', 12),
	(3, 2025, 'Michael', 'Smith', 13),
	(4, 2024, 'Maria', 'Garcia', 14),
	(5, 2024, 'James', 'Johnson', 15);

-- Dumping data for table gradebook.teacher: ~2 rows (approximately)
INSERT INTO `teacher` (`teacherId`, `firstName`, `lastName`, `userId`) VALUES
	(1, 'John', 'Doe', 9),
	(2, 'David', 'Rodriguez', 10);

-- Dumping data for table gradebook.user: ~7 rows (approximately)
INSERT INTO `user` (`userId`, `password_hash`, `role`, `username`) VALUES
	(9, 'password', 'TEACHER', 'johndoe'),
	(10, 'password', 'TEACHER', 'davidrodriguez'),
	(11, 'password', 'STUDENT', 'janedoe'),
	(12, 'password', 'STUDENT', 'alicesmith'),
	(13, 'password', 'STUDENT', 'michaelsmith'),
	(14, 'password', 'STUDENT', 'mariagarcia'),
	(15, 'secretpassword', 'STUDENT', 'jamesjohnson');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
