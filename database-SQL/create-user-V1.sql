DROP USER IF EXISTS 'appuser'@'localhost';
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON `gradebook`.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;
