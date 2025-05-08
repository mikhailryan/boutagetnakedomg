-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2025 at 11:15 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `isthisadatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `logs`
--

CREATE TABLE `logs` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `action` varchar(255) NOT NULL,
  `timestamp` varchar(255) NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `reseller_id` int(11) NOT NULL,
  `seller_id` int(11) NOT NULL,
  `order_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT 1,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `seller_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int(11) DEFAULT 0,
  `status` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `seller_id`, `name`, `price`, `stock`, `status`, `created_at`) VALUES
(1, 111, 'Sachet Shampoo', 6.00, 100, 'Active', '2025-05-08 15:24:40'),
(2, 112, 'Cup Noodles', 15.00, 80, 'Active', '2025-05-08 15:24:40'),
(3, 111, 'Canned Sardines', 20.00, 50, 'Active', '2025-05-08 15:24:40'),
(4, 111, 'Bottled Water', 10.00, 100, 'Active', '2025-05-08 15:24:40'),
(5, 111, 'Softdrinks (500ml)', 18.00, 60, 'Active', '2025-05-08 15:24:40'),
(6, 112, 'Eggs', 7.00, 200, 'Active', '2025-05-08 15:24:40'),
(7, 112, 'Instant Coffee (Sachet)', 8.00, 150, 'Active', '2025-05-08 15:24:40'),
(8, 111, 'Laundry Soap Bar', 13.00, 40, 'Active', '2025-05-08 15:24:40'),
(9, 112, 'Biscuit Pack', 12.00, 90, 'Active', '2025-05-08 15:24:40'),
(10, 111, 'Candies', 1.00, 500, 'Active', '2025-05-08 15:24:40'),
(11, 111, 'Cigarettes (Stick)', 8.00, 300, 'Active', '2025-05-08 15:24:40'),
(12, 112, 'Toothpaste (Sachet)', 10.00, 100, 'Active', '2025-05-08 15:24:40'),
(13, 112, 'Soap (Bath)', 15.00, 70, 'Active', '2025-05-08 15:24:40'),
(14, 112, 'Soy Sauce (Sachet)', 5.00, 90, 'Active', '2025-05-08 15:24:40'),
(15, 112, 'Vinegar (Sachet)', 5.00, 90, 'Active', '2025-05-08 15:24:40'),
(16, 111, 'Cooking Oil (Sachet)', 12.00, 80, 'Active', '2025-05-08 15:24:40'),
(17, 111, 'Bread (Tasty)', 35.00, 20, 'Active', '2025-05-08 15:24:40'),
(18, 112, 'Pandesal', 2.00, 100, 'Active', '2025-05-08 15:24:40'),
(19, 111, 'Instant Noodles (Pack)', 12.00, 100, 'Active', '2025-05-08 15:24:40'),
(20, 112, 'Salt (Small)', 10.00, 50, 'Active', '2025-05-08 15:24:40'),
(21, 116, 'Sugar (Small)', 20.00, 50, 'Active', '2025-05-08 15:24:40'),
(22, 116, 'Coffee Creamer', 5.00, 60, 'Active', '2025-05-08 15:24:40'),
(23, 116, 'Face Mask (Piece)', 3.00, 100, 'Active', '2025-05-08 15:24:40'),
(24, 112, 'Notebook', 25.00, 30, 'Active', '2025-05-08 15:24:40'),
(25, 112, 'Ballpen', 10.00, 70, 'Active', '2025-05-08 15:24:40'),
(26, 112, 'Charcoal (Pack)', 15.00, 40, 'Active', '2025-05-08 15:24:40'),
(27, 112, 'Matches', 3.00, 80, 'Active', '2025-05-08 15:24:40'),
(28, 116, 'Candles (Piece)', 6.00, 50, 'Active', '2025-05-08 15:24:40'),
(29, 116, 'Ice Candy', 5.00, 150, 'Active', '2025-05-08 15:24:40'),
(30, 116, 'Prepaid Load (10)', 10.00, 100, 'Active', '2025-05-08 15:24:40'),
(31, 111, 'Sachet Shampoo', 6.00, 100, 'Active', '2025-05-08 15:24:40'),
(32, 112, 'Cup Noodles', 15.00, 80, 'Active', '2025-05-08 15:24:40'),
(33, 111, 'Canned Sardines', 20.00, 50, 'Active', '2025-05-08 15:24:40'),
(34, 111, 'Bottled Water', 10.00, 100, 'Active', '2025-05-08 15:24:40'),
(35, 111, 'Softdrinks (500ml)', 18.00, 60, 'Active', '2025-05-08 15:24:40'),
(36, 112, 'Eggs', 7.00, 200, 'Active', '2025-05-08 15:24:40'),
(37, 112, 'Instant Coffee (Sachet)', 8.00, 150, 'Active', '2025-05-08 15:24:40'),
(38, 111, 'Laundry Soap Bar', 13.00, 40, 'Active', '2025-05-08 15:24:40'),
(39, 112, 'Biscuit Pack', 12.00, 90, 'Active', '2025-05-08 15:24:40'),
(40, 111, 'Candies', 1.00, 500, 'Active', '2025-05-08 15:24:40'),
(41, 111, 'Cigarettes (Stick)', 8.00, 300, 'Active', '2025-05-08 15:24:40'),
(42, 112, 'Toothpaste (Sachet)', 10.00, 100, 'Active', '2025-05-08 15:24:40'),
(43, 112, 'Soap (Bath)', 15.00, 70, 'Active', '2025-05-08 15:24:40'),
(44, 112, 'Soy Sauce (Sachet)', 5.00, 90, 'Active', '2025-05-08 15:24:40'),
(45, 112, 'Vinegar (Sachet)', 5.00, 90, 'Active', '2025-05-08 15:24:40'),
(46, 111, 'Cooking Oil (Sachet)', 12.00, 80, 'Active', '2025-05-08 15:24:40'),
(47, 111, 'Bread (Tasty)', 35.00, 20, 'Active', '2025-05-08 15:24:40'),
(48, 112, 'Pandesal', 2.00, 100, 'Active', '2025-05-08 15:24:40'),
(49, 111, 'Instant Noodles (Pack)', 12.00, 100, 'Active', '2025-05-08 15:24:40'),
(50, 112, 'Salt (Small)', 10.00, 50, 'Active', '2025-05-08 15:24:40'),
(51, 116, 'Sugar (Small)', 20.00, 50, 'Active', '2025-05-08 15:24:40'),
(52, 116, 'Coffee Creamer', 5.00, 60, 'Active', '2025-05-08 15:24:40'),
(53, 116, 'Face Mask (Piece)', 3.00, 100, 'Active', '2025-05-08 15:24:40'),
(54, 112, 'Notebook', 25.00, 30, 'Active', '2025-05-08 15:24:40'),
(55, 112, 'Ballpen', 10.00, 70, 'Active', '2025-05-08 15:24:40'),
(56, 112, 'Charcoal (Pack)', 15.00, 40, 'Active', '2025-05-08 15:24:40'),
(57, 112, 'Matches', 3.00, 80, 'Active', '2025-05-08 15:24:40'),
(58, 116, 'Candles (Piece)', 6.00, 50, 'Active', '2025-05-08 15:24:40'),
(59, 116, 'Ice Candy', 5.00, 150, 'Active', '2025-05-08 15:24:40'),
(60, 116, 'Prepaid Load (10)', 10.00, 100, 'Active', '2025-05-08 15:24:40');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL,
  `status` varchar(50) NOT NULL,
  `email_verified` int(11) NOT NULL DEFAULT 0,
  `verification_code` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `email`, `password`, `role`, `status`, `email_verified`, `verification_code`, `created_at`) VALUES
(110, 'Test User', 'test', 'mikhail@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Admin', 'Active', 1, NULL, '2025-05-08 13:33:22'),
(111, 'Mikhail Muralla', 'miku', 'murallaxmike@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Reseller', 'Active', 1, NULL, '2025-05-08 13:33:22'),
(112, 'Akita Neru', 'piss_miku', 'yellowmiku@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 'Seller', 'Active', 0, NULL, '2025-05-08 13:33:22'),
(116, 'Hatsune Miku', 'mikuuu', 'mikubutalotdumber@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 'Seller', 'Active', 1, 615587, '2025-05-08 13:33:22'),
(117, 'Kasane Teto', 'pearto', 'teteteteto@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 'Seller', 'Active', 1, NULL, '2025-05-08 13:33:22'),
(119, 'Jane Doe', 'janedoe', 'janedoe@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(120, 'John Smith', 'johnsmith', 'johnsmith@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(121, 'Michael Jordan', 'michaeljordan', 'michaeljordan@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(122, 'Sarah Lee', 'sarahlee', 'sarahlee@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(123, 'David Brown', 'davidbrown', 'davidbrown@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(124, 'Linda Green', 'lindagreen', 'lindagreen@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(125, 'James White', 'jameswhite', 'jameswhite@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(126, 'Patricia Black', 'patriciablack', 'patriciablack@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(127, 'Robert Harris', 'robertharris', 'robertharris@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(128, 'Jennifer Clark', 'jenniferclark', 'jenniferclark@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(129, 'William Lewis', 'williamlewis', 'williamlewis@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(130, 'Elizabeth Walker', 'elizabethwalker', 'elizabethwalker@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(131, 'Charles Hall', 'charleshall', 'charleshall@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(132, 'Mary Allen', 'maryallen', 'maryallen@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(133, 'Joseph Young', 'josephyoung', 'josephyoung@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(134, 'Sophia King', 'sophiaking', 'sophiaking@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(135, 'Mason Scott', 'masonscott', 'masonscott@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(136, 'Amelia Martinez', 'ameliamartinez', 'ameliamartinez@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(137, 'Lucas Robinson', 'lucasrobinson', 'lucasrobinson@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(138, 'Ethan Martinez', 'ethanmartinez', 'ethanmartinez@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(139, 'Chloe Lewis', 'cholelewis', 'cholelewis@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(140, 'Isabella Harris', 'isabellaharris', 'isabellaharris@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(141, 'Jack Lee', 'jacklee', 'jacklee@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(142, 'Emily White', 'emilywhite', 'emilywhite@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(143, 'Oliver Johnson', 'oliverjohnson', 'oliverjohnson@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(144, 'Charlotte King', 'charlotteking', 'charlotteking@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(145, 'Benjamin Allen', 'benjaminallen', 'benjaminallen@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(146, 'Samantha Clark', 'samanthaclark', 'samanthaclark@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(147, 'Henry Davis', 'henrydavis', 'henrydavis@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(148, 'Harper Green', 'harpergreen', 'harpergreen@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(149, 'Jackson Scott', 'jacksonscott', 'jacksonscott@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(150, 'Victoria Moore', 'victoriamoore', 'victoriamoore@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(151, 'Daniel Wilson', 'danielwilson', 'danielwilson@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(152, 'Grace Taylor', 'gracetaylor', 'gracetaylor@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(153, 'Matthew Harris', 'matthewharris', 'matthewharris@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(154, 'Natalie Young', 'natalieyoung', 'natalieyoung@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(155, 'Zachary Lewis', 'zacharylewis', 'zacharylewis@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22'),
(156, 'Maya Walker', 'mayawalker', 'mayawalker@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', 0, NULL, '2025-05-08 13:33:22');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `logs`
--
ALTER TABLE `logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `reseller_id` (`reseller_id`),
  ADD KEY `seller_id` (`seller_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `seller_id` (`seller_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=157;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`reseller_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
