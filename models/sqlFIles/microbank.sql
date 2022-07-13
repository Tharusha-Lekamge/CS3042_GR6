-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 13, 2022 at 06:40 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `microbank`
--

-- --------------------------------------------------------

--
-- Table structure for table `accountholders`
--

CREATE TABLE `accountholders` (
  `customerID` varchar(16) NOT NULL,
  `accountNumber` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accountholders`
--

INSERT INTO `accountholders` (`customerID`, `accountNumber`) VALUES
('11112222', '13');

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `accountNumber` varchar(20) NOT NULL,
  `accountType` enum('Children','Teen','Adult','Senior') DEFAULT NULL,
  `agentID` varchar(10) DEFAULT NULL,
  `accountBalance` float DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`accountNumber`, `accountType`, `agentID`, `accountBalance`) VALUES
('11112222', 'Senior', '1', 1111),
('1234567894', 'Teen', '123', 1149),
('1239999994', 'Children', '1234', 69570);

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `username` varchar(32) NOT NULL,
  `password` varchar(128) NOT NULL,
  `firstName` varchar(32) NOT NULL,
  `lastName` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `agentdetails`
--

CREATE TABLE `agentdetails` (
  `agentID` int(11) NOT NULL,
  `agentNIC` varchar(12) NOT NULL,
  `contactNo` text NOT NULL,
  `agentName` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerID` varchar(16) NOT NULL,
  `password` varchar(64) NOT NULL,
  `firstName` varchar(32) NOT NULL,
  `lastName` varchar(32) NOT NULL,
  `customerNIC` varchar(10) NOT NULL,
  `contactNumber` varchar(12) NOT NULL,
  `address` varchar(128) NOT NULL,
  `birthday` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerID`, `password`, `firstName`, `lastName`, `customerNIC`, `contactNumber`, `address`, `birthday`) VALUES
('13', '$2a$12$LoU8SAJxLmfm.zZ13bSC9eyTiphseqX9wAFun2xJixpBSo/cX5Yye', 'Hello', 'Horld', '12345667', '123456', 'who knows', '2022-07-01');

-- --------------------------------------------------------

--
-- Table structure for table `fdinterest`
--

CREATE TABLE `fdinterest` (
  `fdType` varchar(16) NOT NULL,
  `duration` tinyint(4) NOT NULL,
  `interestRate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fdinterest`
--

INSERT INTO `fdinterest` (`fdType`, `duration`, `interestRate`) VALUES
('long', 36, 15),
('medium', 12, 14),
('short', 6, 13);

-- --------------------------------------------------------

--
-- Table structure for table `fixeddeposits`
--

CREATE TABLE `fixeddeposits` (
  `accountNumber` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `amount` float NOT NULL,
  `openingDate` date NOT NULL,
  `planType` enum('Type1','Type2','Type3','') NOT NULL,
  `FD_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `interestrates`
--

CREATE TABLE `interestrates` (
  `accountType` enum('children','teen','adult','senior','joint') NOT NULL,
  `interestRate` float NOT NULL,
  `minAmount` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `interestrates`
--

INSERT INTO `interestrates` (`accountType`, `interestRate`, `minAmount`) VALUES
('children', 12, 0),
('teen', 11, 500),
('adult', 10, 1000),
('senior', 13, 1000),
('joint', 7, 5000);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transactionID` int(10) NOT NULL,
  `accountNumber` varchar(15) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `transactionType` enum('Withdraw','Deposit') DEFAULT NULL,
  `transactionAmount` float DEFAULT NULL,
  `transactionCharge` float DEFAULT 30,
  `agentID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transactionID`, `accountNumber`, `date`, `transactionType`, `transactionAmount`, `transactionCharge`, `agentID`) VALUES
(7, '1234567894', '2022-05-16', 'Withdraw', 150, 1, 0),
(8, '1239999994', '2022-05-16', 'Withdraw', 150, 1, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accountholders`
--
ALTER TABLE `accountholders`
  ADD PRIMARY KEY (`customerID`,`accountNumber`),
  ADD KEY `accountNumber` (`accountNumber`);

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`accountNumber`),
  ADD KEY `AccountType` (`accountType`),
  ADD KEY `AgentID` (`agentID`);

--
-- Indexes for table `agentdetails`
--
ALTER TABLE `agentdetails`
  ADD PRIMARY KEY (`agentID`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`customerID`),
  ADD UNIQUE KEY `customerNIC` (`customerNIC`);

--
-- Indexes for table `fdinterest`
--
ALTER TABLE `fdinterest`
  ADD PRIMARY KEY (`fdType`);

--
-- Indexes for table `fixeddeposits`
--
ALTER TABLE `fixeddeposits`
  ADD PRIMARY KEY (`FD_ID`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transactionID`),
  ADD KEY `AccountNumber` (`accountNumber`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transactionID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
