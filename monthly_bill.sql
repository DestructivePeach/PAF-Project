-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2022 at 09:52 AM
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
-- Table structure for table `monthly_bill`
--

CREATE TABLE `monthly_bill` (
  `billId` int(10) NOT NULL,
  `year` int(4) NOT NULL,
  `month` varchar(15) NOT NULL,
  `totalUnits` int(10) NOT NULL,
  `powId` int(10) NOT NULL,
  `dueAmount` decimal(10,2) NOT NULL,
  `billAmount` decimal(10,2) NOT NULL,
  `accountNumber` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `monthly_bill`
--

INSERT INTO `monthly_bill` (`billId`, `year`, `month`, `totalUnits`, `powId`, `dueAmount`, `billAmount`, `accountNumber`) VALUES
(7, 2022, 'February', 80, 1, '600.00', '1800.00', 167810),
(8, 2022, 'February', 90, 1, '500.00', '1850.00', 168960),
(9, 2022, 'February', 105, 2, '400.00', '3550.00', 187890),
(16, 2022, 'February', 110, 2, '300.00', '3600.00', 189054);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `monthly_bill`
--
ALTER TABLE `monthly_bill`
  ADD PRIMARY KEY (`billId`),
  ADD UNIQUE KEY `un_mothly_bill` (`accountNumber`,`year`,`month`),
  ADD KEY `fk_monthlybill` (`powId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `monthly_bill`
--
ALTER TABLE `monthly_bill`
  MODIFY `billId` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `monthly_bill`
--
ALTER TABLE `monthly_bill`
  ADD CONSTRAINT `fk_monthlybill` FOREIGN KEY (`powId`) REFERENCES `power_consumption` (`powId`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_monthlybill2` FOREIGN KEY (`accountNumber`) REFERENCES `customer` (`accountNumber`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
