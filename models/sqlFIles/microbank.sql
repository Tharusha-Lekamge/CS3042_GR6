-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 15, 2022 at 04:41 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

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

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `calculate_fd_interest` ()   BEGIN
	DECLARE id INT;
	DECLARE accNum VARCHAR(20) DEFAULT "";
    DECLARE accbal FLOAT DEFAULT 0;
    DECLARE val FLOAT DEFAULT 0;
    DECLARE opened DATE;
    DECLARE closed DATE;
    DECLARE today DATE;
    DECLARE fdtype_1 VARCHAR(10);
    DECLARE numdays INT;
    
    
    DECLARE longrate FLOAT DEFAULT 0;
    DECLARE mediumrate FLOAT DEFAULT 0;
    DECLARE shortrate FLOAT DEFAULT 0;
    
    DECLARE bdone INT;
    
    DECLARE cursor_2 CURSOR FOR 
    SELECT DISTINCT accountNumber,amount, openingDate, closingDate, planType,FD_ID FROM microbank.fixeddeposits WHERE closingDate>=NOW();
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET bdone = 1;
    
    SET longrate = (SELECT interestRate FROM microbank.fdinterest WHERE fdType = 'Type1');
    SET mediumrate  = (SELECT interestRate FROM microbank.fdinterest WHERE fdType = 'Type2');
    SET shortrate = (SELECT interestRate FROM microbank.fdinterest WHERE fdType = 'Type3');
    SELECT CAST(NOW() AS Date) INTO today ;
    OPEN cursor_2;
    SET bdone=0;
    
    REPEAT
		FETCH cursor_2 INTO accNum,val,opened,closed,fdtype_1,id;
        SET accbal = (SELECT accountBalance FROM microbank.accounts WHERE accountNumber = accNum);
        
        SET numdays = DATEDIFF(opened,today);
        IF (numdays%30 =0 AND fdtype_1='Type1') THEN
			SET accbal = accbal+val*(longrate/1200);
        ELSEIF (numdays%30 =0 AND fdtype_1='Type2') THEN
			SET accbal = accbal+val*(mediumrate/1200);
        ELSEIF (numdays%30 =0 AND fdtype_1='Type3') THEN
			SET accbal = accbal+val*(shortrate/1200);
		END IF;
        
        UPDATE accounts SET accountBalance = accbal WHERE accountNumber = accNum;
	UNTIL bdone END REPEAT;
    CLOSE cursor_2;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `calculate_interest` ()   BEGIN
	DECLARE num VARCHAR(20) DEFAULT "";
    DECLARE actype VARCHAR(10) DEFAULT "";
    DECLARE bal FLOAT DEFAULT 0.00;
    
	DECLARE jointAccInterest FLOAT DEFAULT 0.00;
    DECLARE childAccInterest FLOAT DEFAULT 0.00;
    DECLARE teenAccInterest FLOAT DEFAULT 0.00;
    DECLARE adultAccInterest FLOAT DEFAULT 0.00;
    DECLARE seniorAccInterest FLOAT DEFAULT 0.00;
    
    DECLARE bdone INT;
    
    DECLARE cursor_1 CURSOR FOR 
    SELECT accountNumber,accountType,accountBalance FROM microbank.accounts;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET bdone=1;
    
    SET jointAccInterest = (SELECT interestRate FROM interestrates WHERE accountType = 'joint');
    SET teenAccInterest = (SELECT interestRate FROM interestrates WHERE accountType = 'teen');
    SET adultAccInterest = (SELECT interestRate FROM interestrates WHERE accountType = 'adult');
    SET seniorAccInterest = (SELECT interestRate FROM interestrates WHERE accountType = 'senior');
    SET childAccInterest = (SELECT interestRate FROM interestrates WHERE accountType = 'child');
    
    OPEN cursor_1;
    SET bdone = 0;
    REPEAT
    	FETCH cursor_1 INTO num,actype,bal;
        IF actype='child' THEN
        	SET bal = bal + bal*(childAccInterest/100);
        ELSEIF actype = 'teen' THEN
        	SET bal = bal + bal*(teenAccInterest/100);
        ELSEIF actype = 'adult' THEN
        	SET bal = bal + bal*(adultAccInterest/100);
        ELSEIF actype = 'senior' THEN
        	SET bal = bal + bal*(seniorAccInterest/100);   
        ELSEIF actype = 'joint' THEN
        	SET bal = bal + bal*(jointAccInterest/100);
       	END IF;
        
        UPDATE accounts SET accountBalance = bal WHERE accountNumber = num;
     UNTIL bdone END REPEAT;
     CLOSE cursor_1;   
END$$

DELIMITER ;

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
('123432', '234897'),
('123432', '345632'),
('123432', '5451919'),
('123432', '5674563'),
('123432', '789678'),
('123432', '9089675'),
('1313234', '2345432'),
('1313234', '2345765'),
('1313234', '678567'),
('1313234', '678789'),
('214347', '3479133'),
('214347', '839423'),
('3456763', '1212345'),
('3456763', '23456'),
('3456763', '2345765'),
('3456763', '458461'),
('3456763', '7895460'),
('3456763', '9089789'),
('5627132', '1561336'),
('5627132', '372348'),
('5627132', '521747'),
('567456', '2949877'),
('567456', '675675'),
('567456', '789678'),
('567456', '9089078'),
('7890678', '1234765'),
('7890678', '2949877'),
('7890678', '678789'),
('79241893', '3479133'),
('79241893', '379240'),
('9067633', '3275646'),
('9067633', '458461'),
('9067633', '934184');

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `accountNumber` varchar(20) NOT NULL,
  `accountType` enum('Children','Teen','Adult','Senior','Joint') DEFAULT NULL,
  `accountBalance` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`accountNumber`, `accountType`, `accountBalance`) VALUES
('1212345', 'Adult', 224481),
('1234765', 'Teen', 30673.4),
('1561336', 'Senior', 287138),
('2345432', 'Teen', 78217.2),
('23456', 'Adult', 24221.7),
('2345765', 'Joint', 64866.6),
('234897', 'Senior', 146932),
('2949877', 'Joint', 216222),
('3275646', 'Teen', 30673.4),
('345632', 'Adult', 23411.6),
('3479133', 'Joint', 155680),
('372348', 'Adult', 121514),
('379240', 'Senior', 43554.7),
('458461', 'Joint', 32433.3),
('521747', 'Adult', 93160.5),
('5451919', 'Senior', 82592.6),
('5674563', 'Adult', 6750.76),
('675675', 'Children', 15000),
('678567', 'Adult', 27003),
('678789', 'Joint', 168653),
('7895460', 'Senior', 25358.5),
('789678', 'Joint', 21622.2),
('839423', 'Children', 12500),
('9089078', 'Teen', 46010.1),
('9089675', 'Adult', 24032.7),
('9089789', 'Senior', 145182),
('934184', 'Teen', 51290.8);

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

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`username`, `password`, `firstName`, `lastName`) VALUES
('SB1234', '$2a$12$8MKqongHv6LAu/qVTjiS7u0BfNpRv12dLzy.gbUVMCxb9UaBHWCqC', 'Sarath', 'Bandara'),
('MF90890', '$2a$12$owK19akiw0Sb3oQjEZG7oezRWCIG5uQT6/6uinMmN9XQb9X/RA1TG', 'Mario', 'Fonseka'),
('MD23543', '$2a$12$qBTSnuTxOsS77VHpjttmGe/vKZipS71jPEGwtNhKgJp2ogFlGwHpS\r\n', 'Mahesh', 'Dissanayake'),
('TB342334', '$2a$12$YvBrF4.ZH6DqHp3nZYXvqevBNy1qQlqRUXhE8djQ69UZE.HNPa8VG', 'Tony', 'Blaire');

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

--
-- Dumping data for table `agentdetails`
--

INSERT INTO `agentdetails` (`agentID`, `agentNIC`, `contactNo`, `agentName`) VALUES
(45632, '67857432V', '071456543', 'Saman Kumara'),
(89078, '89876553V', '072345456', 'Upul Shantha'),
(358978, '5678908V', '0714534567', 'Anura Rajapaksha'),
(786567, '678567453V', '071345343', 'Kalum Lenin');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `customerID` varchar(16) NOT NULL,
  `password` varchar(64) NOT NULL,
  `firstName` varchar(32) NOT NULL,
  `lastName` varchar(32) NOT NULL,
  `agentID` varchar(10) DEFAULT NULL,
  `customerNIC` varchar(10) NOT NULL,
  `contactNumber` varchar(12) NOT NULL,
  `address` varchar(128) NOT NULL,
  `birthday` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customerID`, `password`, `firstName`, `lastName`, `agentID`, `customerNIC`, `contactNumber`, `address`, `birthday`) VALUES
('123432', '$2a$12$ZU7dcYM.RaXA9vPRhpaH7egMGEc9sDWUDrOyi7cNowIqagyLug5VW', 'John', 'Rodrigo', '45632', '199945675V', '0712345444', 'No:234, John Rodrigo Mwt, Katubedda', '1992-02-04'),
('1313234', '$2a$12$3cTmo0t8LoWU1SScQpkeiOHu.f91qVysDk5c.pHc7b9.ZVVmIEwfi', 'Ross', 'Geller', '358978', '7789678V', '067567897', '23/A, Chatham Street, Colombo', '1977-03-04'),
('214347', '$2a$12$yw06uqe8k0gBrwMuobLcDuOslxM5EsohfEIl/Ft4ej3UCNaPIQ8eu', 'Eren ', 'Yeager', '786567', '84832782v', '078283923', 'No:45/A, Old Kent Road, Jafna', '1978-06-05'),
('3456763', '$2a$12$qY.UBUdZmIoxndXg8Y4uPe2aqucdUmfFayYnA50Sjk9juU.MGs/4a', 'Nandasena', 'Dissanayake', '45632', '56781212V', '0112345342', '78?A, Temple Trees, Colombo 10', '1965-06-07'),
('5627132', '$2a$12$uOXdQIkj2PO2IWump40OqONmZUlv/1q3OFL9URU6U7z2E9zahlXmi', 'Hermione', 'Granger', '786567', '67214883v', '081345432', '23/A, Pettah Road, Galle', '1990-05-08'),
('567456', '$2a$12$hL/asIVUuaJC8sAc2zHlqenbrDx0DGbKrv0eL0f80ltOZjI8yEplS', 'Arwen', 'Smith', '89078', '9234567V', '078456789', 'No: 90/B, Duke Street, Mordor', '1990-03-04'),
('7890678', '$2a$12$6HfdeM6Xx.dZ04PjOfGvjultGNG3hCdxIMKcCaQxAC9Bx75pNkAMi', 'Rachel', 'Green', '89078', '789675678V', '070890678', 'No: 56, Central Perk, New York', '1978-12-06'),
('9067633', '$2a$12$KS9aQmwGCj8ldC9TGeo25e49tPne3RR4ax1cul8IcAPDBb/vNW0dq', 'Newt', 'Scamander', '358978', '9967889v', '089234342', 'No:4/1/3, Dursley Street, Mannar', '1984-07-04');

-- --------------------------------------------------------

--
-- Table structure for table `fdinterest`
--

CREATE TABLE `fdinterest` (
  `fdType` varchar(16) NOT NULL,
  `duration` tinyint(4) NOT NULL,
  `interestRate` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fdinterest`
--

INSERT INTO `fdinterest` (`fdType`, `duration`, `interestRate`) VALUES
('Type1', 36, 15),
('Type2', 12, 14),
('Type3', 6, 13);

-- --------------------------------------------------------

--
-- Table structure for table `fixeddeposits`
--

CREATE TABLE `fixeddeposits` (
  `accountNumber` varchar(20) NOT NULL,
  `amount` float NOT NULL,
  `openingDate` date NOT NULL,
  `closingDate` date DEFAULT NULL,
  `planType` enum('Type1','Type2','Type3','') NOT NULL,
  `FD_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `fixeddeposits`
--

INSERT INTO `fixeddeposits` (`accountNumber`, `amount`, `openingDate`, `closingDate`, `planType`, `FD_ID`) VALUES
('9089789', 200000, '2022-03-01', '2023-03-01', 'Type2', 45345),
('678789', 250000, '2022-06-09', '2025-06-09', 'Type1', 78678),
('23456', 120000, '2022-07-01', '2023-01-01', 'Type3', 123564),
('5674564', 120000, '2020-01-05', '2020-07-05', 'Type3', 213243),
('234897', 150000, '2022-06-15', '2023-06-15', 'Type2', 243243),
('5674563', 100000, '2020-07-02', '2023-07-02', 'Type1', 765657);

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
  `customerID` varchar(16) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `transactionType` enum('Withdraw','Deposit') DEFAULT NULL,
  `transactionAmount` float DEFAULT NULL,
  `transactionCharge` float DEFAULT 30,
  `agentID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transactionID`, `accountNumber`, `customerID`, `date`, `transactionType`, `transactionAmount`, `transactionCharge`, `agentID`) VALUES
(11, '5674563', '123432', '2022-06-01', 'Deposit', 1000, 30, 45632),
(12, '5674563', '123432', '2022-05-11', 'Withdraw', 500, 30, 45632),
(13, '5674563', '123432', '2022-06-01', 'Deposit', 2000, 30, 45632),
(14, '5674563', '123432', '2022-04-06', 'Deposit', 2500, 60, 89078),
(15, '5674563', '123432', '2022-06-07', 'Withdraw', 2000, 60, 358978),
(16, '23456', '3456763', '2022-07-05', 'Deposit', 7500, 30, 89078),
(17, '23456', '3456763', '2022-07-08', 'Withdraw', 1500, 30, 89078),
(18, '23456', '3456763', '2022-04-05', 'Deposit', 6000, 60, 786567),
(19, '23456', '3456763', '2022-02-01', 'Deposit', 2000, 60, 358978),
(20, '23456', '3456763', '2022-04-20', 'Withdraw', 1200, 60, 358978),
(21, '1234765', '7890678', '2022-03-01', 'Deposit', 6000, 30, 358978),
(22, '1234765', '7890678', '2021-11-04', 'Withdraw', 1500, 30, 358978),
(23, '1234765', '7890678', '2022-02-09', 'Deposit', 12000, 30, 358978),
(24, '1234765', '7890678', '2022-05-04', 'Withdraw', 1000, 30, 358978),
(25, '1234765', '7890678', '2022-05-04', 'Withdraw', 5000, 60, 45632),
(26, '678567', '1313234', '2022-03-09', 'Deposit', 25000, 30, 786567),
(27, '678567', '1313234', '2022-05-03', 'Withdraw', 2500, 30, 786567),
(28, '678567', '1313234', '2022-02-14', 'Deposit', 2000, 30, 786567),
(29, '678567', '1313234', '2022-02-01', 'Withdraw', 2000, 30, 786567),
(30, '678567', '1313234', '2022-02-22', 'Withdraw', 2000, 60, 89078),
(31, '678789', '7890678', '2022-04-05', 'Deposit', 2500, 30, 45632),
(32, '678789', '1313234', '2022-07-05', 'Withdraw', 1000, 30, 45632),
(33, '789678', '567456', '2022-03-07', 'Deposit', 5000, 30, 89078),
(34, '789678', '123432', '2022-06-06', 'Withdraw', 2000, 60, 45632);

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
  ADD KEY `AccountType` (`accountType`);

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
  MODIFY `transactionID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `monthly_update_acc_interests` ON SCHEDULE EVERY 1 MONTH STARTS '2022-07-16 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL calculate_interest()$$

CREATE DEFINER=`root`@`localhost` EVENT `daily_update_fd_interests` ON SCHEDULE EVERY 1 DAY STARTS '2022-07-16 00:00:00' ON COMPLETION NOT PRESERVE ENABLE DO CALL calculate_fd_interest()$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
