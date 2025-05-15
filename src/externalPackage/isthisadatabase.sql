-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 15, 2025 at 11:07 PM
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
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `user_id`, `product_id`, `quantity`, `date_added`) VALUES
(38, 119, 102, 1, '2025-05-15 20:16:48');

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
  `total_amount` decimal(10,2) NOT NULL,
  `order_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` varchar(255) NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `reseller_id`, `total_amount`, `order_date`, `status`) VALUES
(1, 111, 828.00, '2025-05-15 17:29:07', 'Pending'),
(2, 111, 5017.50, '2025-05-15 17:31:45', 'Pending'),
(3, 111, 50.00, '2025-05-15 17:32:26', 'Pending'),
(4, 111, 18.00, '2025-05-15 18:10:06', 'Pending'),
(5, 111, 3020.00, '2025-05-15 18:18:10', 'Pending'),
(6, 119, 255.00, '2025-05-15 18:31:52', 'Pending'),
(7, 119, 1635.00, '2025-05-15 18:41:11', 'Pending'),
(8, 119, 750.00, '2025-05-15 18:52:32', 'Pending'),
(9, 119, 18.00, '2025-05-15 19:21:30', 'Pending');

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `seller_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT 1,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `order_id`, `seller_id`, `product_id`, `quantity`, `price`) VALUES
(9, 1, 117, 102, 1, 18.00),
(10, 1, 116, 92, 2, 150.00),
(11, 1, 116, 80, 6, 10.00),
(12, 1, 112, 76, 10, 45.00),
(13, 2, 117, 90, 5, 7.50),
(14, 2, 116, 92, 8, 150.00),
(15, 2, 117, 111, 60, 28.00),
(16, 2, 112, 82, 60, 35.00),
(17, 3, 117, 105, 1, 50.00),
(18, 4, 117, 102, 1, 18.00),
(19, 5, 116, 104, 1, 20.00),
(20, 5, 117, 105, 25, 50.00),
(21, 5, 116, 98, 50, 35.00),
(22, 6, 117, 108, 2, 35.00),
(23, 6, 116, 107, 5, 8.00),
(24, 6, 112, 64, 5, 10.00),
(25, 6, 112, 109, 4, 20.00),
(26, 6, 116, 95, 1, 15.00),
(27, 7, 117, 90, 50, 7.50),
(28, 7, 117, 102, 70, 18.00),
(29, 8, 117, 90, 100, 7.50),
(30, 9, 117, 102, 1, 18.00);

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
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `seller_id`, `name`, `price`, `stock`, `created_at`) VALUES
(64, 112, 'Chippy', 10.00, 45, '2025-05-14 16:00:00'),
(65, 116, 'Tao Po Instant Noodles', 12.50, 100, '2025-05-14 16:00:00'),
(66, 117, 'Dole Pineapple Juice', 35.00, 30, '2025-05-14 16:00:00'),
(67, 112, 'Sundrop Soy Sauce', 15.00, 60, '2025-05-14 16:00:00'),
(68, 116, 'Manggang hilaw (Green Mango)', 25.00, 200, '2025-05-14 16:00:00'),
(69, 117, 'C2 Lemon Iced Tea', 25.00, 40, '2025-05-14 16:00:00'),
(70, 112, 'SkyFlakes Cracker', 8.00, 75, '2025-05-14 16:00:00'),
(71, 116, 'Pineapple Tarts', 40.00, 20, '2025-05-14 16:00:00'),
(72, 117, 'Laundry Soap', 45.00, 10, '2025-05-14 16:00:00'),
(73, 112, 'Sarap Instant Coffee', 18.00, 120, '2025-05-14 16:00:00'),
(74, 116, 'Oishi Prawn Crackers', 12.00, 50, '2025-05-14 16:00:00'),
(75, 117, 'Milo', 35.00, 25, '2025-05-14 16:00:00'),
(76, 112, 'Nescafe Coffee', 45.00, 80, '2025-05-14 16:00:00'),
(77, 116, 'Tanduay Rhum', 150.00, 15, '2025-05-14 16:00:00'),
(78, 117, 'Sunkist Orange Juice', 40.00, 50, '2025-05-14 16:00:00'),
(79, 112, 'Lucky Me Pancit Canton', 15.00, 200, '2025-05-14 16:00:00'),
(80, 116, 'Nescafe 3-in-1', 10.00, 100, '2025-05-14 16:00:00'),
(81, 117, 'Purefoods Hotdog', 80.00, 50, '2025-05-14 16:00:00'),
(82, 112, 'Del Monte Ketchup', 35.00, 60, '2025-05-14 16:00:00'),
(83, 116, 'Juicy Lemonade Drink', 20.00, 150, '2025-05-14 16:00:00'),
(84, 117, 'Tropicana Fruit Drink', 40.00, 75, '2025-05-14 16:00:00'),
(85, 112, 'Cheese Rings', 8.00, 120, '2025-05-14 16:00:00'),
(86, 116, 'Moby Soda', 10.00, 100, '2025-05-14 16:00:00'),
(87, 117, 'Big Boss Chips', 15.00, 80, '2025-05-14 16:00:00'),
(88, 112, 'Muncher', 10.00, 90, '2025-05-14 16:00:00'),
(89, 116, 'Choco Pie', 18.00, 50, '2025-05-14 16:00:00'),
(90, 117, 'Fita Biscuits', 7.50, 0, '2025-05-14 16:00:00'),
(91, 112, 'Green Mango with Bagoong', 25.00, 60, '2025-05-14 16:00:00'),
(92, 116, 'Toblerone', 150.00, 30, '2025-05-14 16:00:00'),
(93, 117, 'Banana Chips', 15.00, 100, '2025-05-14 16:00:00'),
(94, 112, 'Gogo Snacks', 18.00, 100, '2025-05-14 16:00:00'),
(95, 116, 'Bottled Water', 15.00, 119, '2025-05-14 16:00:00'),
(96, 117, 'Datu Puti Vinegar', 20.00, 80, '2025-05-14 16:00:00'),
(97, 112, 'Sweet Corn', 25.00, 60, '2025-05-14 16:00:00'),
(98, 116, 'Oreo Biscuits', 35.00, 50, '2025-05-14 16:00:00'),
(99, 117, 'Choco Churros', 28.00, 40, '2025-05-14 16:00:00'),
(100, 112, 'Sundrop Vinegar', 25.00, 70, '2025-05-14 16:00:00'),
(101, 116, 'Knorr Chicken Cubes', 40.00, 30, '2025-05-14 16:00:00'),
(102, 117, 'Calamansi Juice', 18.00, 29, '2025-05-14 16:00:00'),
(103, 112, 'Lemonade Powder', 10.00, 80, '2025-05-14 16:00:00'),
(104, 116, 'Bread Roll', 20.00, 40, '2025-05-14 16:00:00'),
(105, 117, 'Bisugo Fish', 50.00, 25, '2025-05-14 16:00:00'),
(106, 112, 'Instant Pancake Mix', 25.00, 60, '2025-05-14 16:00:00'),
(107, 116, 'Maggi Magic Sarap', 8.00, 145, '2025-05-14 16:00:00'),
(108, 117, 'Instant Pudding', 35.00, 48, '2025-05-14 16:00:00'),
(109, 112, 'Toothpaste', 20.00, 96, '2025-05-14 16:00:00'),
(110, 116, 'Shampoo', 45.00, 50, '2025-05-14 16:00:00'),
(111, 117, 'Dishwashing Liquid', 28.00, 60, '2025-05-14 16:00:00'),
(112, 112, 'Bottled Sweet Mango', 55.00, 30, '2025-05-14 16:00:00'),
(113, 116, 'Soy Sauce', 10.00, 80, '2025-05-14 16:00:00'),
(114, 116, 'Product name', 100.00, 240, '2025-05-15 02:30:43');

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
  `profile_pic` varchar(255) DEFAULT NULL,
  `email_verified` int(11) NOT NULL DEFAULT 0,
  `verification_code` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `username`, `email`, `password`, `role`, `status`, `profile_pic`, `email_verified`, `verification_code`, `created_at`) VALUES
(110, 'Test User', 'test', 'mikhailryanmuralla@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Admin', 'Active', NULL, 1, NULL, '2025-05-08 13:33:22'),
(111, 'Mikhail Muralla', 'miku', 'murallaxmike@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Reseller', 'Active', NULL, 1, NULL, '2025-05-08 13:33:22'),
(112, 'Akita Neru', 'piss_miku', 'yellowmiku@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 'Seller', 'Active', NULL, 1, 970070, '2025-05-08 13:33:22'),
(116, 'Hatsune Miku', 'mikuuu', 'mikubutalotdumber@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 'Seller', 'Active', NULL, 1, 615587, '2025-05-08 13:33:22'),
(117, 'Kasane Teto', 'pearto', 'teteteteto@gmail.com', '932f3c1b56257ce8539ac269d7aab42550dacf8818d075f0bdf1990562aae3ef', 'Seller', 'Active', NULL, 1, NULL, '2025-05-08 13:33:22'),
(119, 'Jane Doe', 'janedoe', 'janedoe@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Reseller', 'Active', 'ProfilePics/1747342743032_Screenshot 2024-07-27 161629.png', 0, NULL, '2025-05-08 13:33:22'),
(120, 'John Smith', 'johnsmith', 'johnsmith@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(121, 'Michael Jordan', 'michaeljordan', 'michaeljordan@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(122, 'Sarah Lee', 'sarahlee', 'sarahlee@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(123, 'David Brown', 'davidbrown', 'davidbrown@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(124, 'Linda Green', 'lindagreen', 'lindagreen@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(125, 'James White', 'jameswhite', 'jameswhite@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(126, 'Patricia Black', 'patriciablack', 'patriciablack@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(127, 'Robert Harris', 'robertharris', 'robertharris@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(128, 'Jennifer Clark', 'jenniferclark', 'jenniferclark@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(129, 'William Lewis', 'williamlewis', 'williamlewis@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(130, 'Elizabeth Walker', 'elizabethwalker', 'elizabethwalker@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(131, 'Charles Hall', 'charleshall', 'charleshall@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(132, 'Mary Allen', 'maryallen', 'maryallen@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(133, 'Joseph Young', 'josephyoung', 'josephyoung@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(134, 'Sophia King', 'sophiaking', 'sophiaking@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(135, 'Mason Scott', 'masonscott', 'masonscott@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(136, 'Amelia Martinez', 'ameliamartinez', 'ameliamartinez@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(137, 'Lucas Robinson', 'lucasrobinson', 'lucasrobinson@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(138, 'Ethan Martinez', 'ethanmartinez', 'ethanmartinez@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(139, 'Chloe Lewis', 'cholelewis', 'cholelewis@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(140, 'Isabella Harris', 'isabellaharris', 'isabellaharris@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(141, 'Jack Lee', 'jacklee', 'jacklee@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(142, 'Emily White', 'emilywhite', 'emilywhite@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(143, 'Oliver Johnson', 'oliverjohnson', 'oliverjohnson@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(144, 'Charlotte King', 'charlotteking', 'charlotteking@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(145, 'Benjamin Allen', 'benjaminallen', 'benjaminallen@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(146, 'Samantha Clark', 'samanthaclark', 'samanthaclark@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(147, 'Henry Davis', 'henrydavis', 'henrydavis@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(148, 'Harper Green', 'harpergreen', 'harpergreen@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(149, 'Jackson Scott', 'jacksonscott', 'jacksonscott@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(150, 'Victoria Moore', 'victoriamoore', 'victoriamoore@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(151, 'Daniel Wilson', 'danielwilson', 'danielwilson@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(152, 'Grace Taylor', 'gracetaylor', 'gracetaylor@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(153, 'Matthew Harris', 'matthewharris', 'matthewharris@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(154, 'Natalie Young', 'natalieyoung', 'natalieyoung@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(155, 'Zachary Lewis', 'zacharylewis', 'zacharylewis@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22'),
(156, 'Maya Walker', 'mayawalker', 'mayawalker@gmail.com', '0d1ea4c256cd50a2a7ccbfd22b3d9959f6fd30bd840b9ff3c7c65ee4e21df06d', 'Seller', 'Pending', NULL, 0, NULL, '2025-05-08 13:33:22');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `product_id` (`product_id`);

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
  ADD KEY `reseller_id` (`reseller_id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `product_id` (`product_id`),
  ADD KEY `seller_id` (`seller_id`);

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
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `logs`
--
ALTER TABLE `logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=115;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=157;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Constraints for table `logs`
--
ALTER TABLE `logs`
  ADD CONSTRAINT `logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`reseller_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  ADD CONSTRAINT `order_items_ibfk_3` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
