-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2022 at 09:54 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `powergridsystem`
--

-- --------------------------------------------------------

--
-- Table structure for table `power_consumption`
--

CREATE TABLE `power_consumption` (
  `powId` int(10) NOT NULL,
  `unitDescription` varchar(200) NOT NULL,
  `unitPrice` decimal(5,2) NOT NULL
) ;

--
-- Dumping data for table `power_consumption`
--

INSERT INTO `power_consumption` (`powId`, `unitDescription`, `unitPrice`) VALUES
(1, 'Units 1-100', '15.00'),
(2, 'Units 101-200', '30.00'),
(3, 'Units 201-300', '40.00'),
(4, 'Units above 300', '65.00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `power_consumption`
--
ALTER TABLE `power_consumption`
  ADD PRIMARY KEY (`powId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
