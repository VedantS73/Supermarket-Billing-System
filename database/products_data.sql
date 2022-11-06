-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 03, 2022 at 06:49 PM
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
-- Database: `products_data`
--

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `name`, `password`) VALUES
(1, 'admin', 'admin'),
(2, 'vedant', 'vedant'),
(3, 'anagha', 'anagha1234'),
(4, 'rahul', 'rahul1234'),
(5, 'ayushka', 'ayushka1234');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `quantity` int(11) UNSIGNED NOT NULL DEFAULT 1,
  `price` int(11) NOT NULL,
  `details` varchar(500) NOT NULL DEFAULT 'No Details'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `quantity`, `price`, `details`) VALUES
(1, 'Kurkure', 9, 10, 'Tasty Kurkure'),
(2, 'Bourbon Biscuit', 19, 20, 'Tasty Chocolate Biscuits\r\n'),
(3, 'Good Day', 14, 15, 'Sweet Cashew Biscuits'),
(4, 'Oreo Biscuits', 29, 30, 'creamy delicious'),
(5, 'Cologate', 30, 40, 'Tasty Toothpaste'),
(6, 'Toilet Cleaner Harpic', 200, 20, 'Clean your toilets\r\n'),
(7, 'NewsPaper', 20, 10, 'nice Newspaper'),
(8, 'Hide & Seek', 25, 30, 'Biscuits'),
(9, 'Dell Laptop', 5, 60000, 'Vostro 3000'),
(10, 'Kohinoor Rice 1kg Packet', 20, 200, 'Nice rice for your Biryani'),
(11, 'Bisleeri', 7, 10, 'water'),
(12, 'Mouse', 297, 300, 'USB Mouse');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
