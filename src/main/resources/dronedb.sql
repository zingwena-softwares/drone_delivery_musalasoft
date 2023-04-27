-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 27, 2023 at 04:41 PM
-- Server version: 8.0.32-0ubuntu0.20.04.2
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dronedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `drone`
--

CREATE TABLE `drone` (
  `serial_number` varchar(100) NOT NULL,
  `capacity` int NOT NULL,
  `model` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `weight` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `drone`
--

INSERT INTO `drone` (`serial_number`, `capacity`, `model`, `state`, `weight`) VALUES
('w78888290', 100, 'LIGHTWEIGHT', 'LOADING', 100),
('w788882pl90', 100, 'LIGHTWEIGHT', 'IDLE', 0),
('w8978888290', 100, 'HEAVYWEIGHT', 'IDLE', 0);

-- --------------------------------------------------------

--
-- Table structure for table `medication`
--

CREATE TABLE `medication` (
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `weight` int NOT NULL,
  `fk_drone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `medication`
--

INSERT INTO `medication` (`code`, `name`, `picture`, `weight`, `fk_drone`) VALUES
('00005665556', 'paracetamo23', 'picture String', 100, 'w78888290'),
('0000655', 'paracetamo', 'picture String', 100, NULL),
('000065556', 'paracetamo', 'picture String', 100, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `drone`
--
ALTER TABLE `drone`
  ADD PRIMARY KEY (`serial_number`);

--
-- Indexes for table `medication`
--
ALTER TABLE `medication`
  ADD PRIMARY KEY (`code`),
  ADD KEY `FKlo0csewd9gb4tj82388mwsmq4` (`fk_drone`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `medication`
--
ALTER TABLE `medication`
  ADD CONSTRAINT `FKlo0csewd9gb4tj82388mwsmq4` FOREIGN KEY (`fk_drone`) REFERENCES `drone` (`serial_number`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
