-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 12, 2025 at 09:20 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectplanner`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` bigint(20) NOT NULL,
  `startHour` int(11) NOT NULL,
  `startMinute` int(11) NOT NULL,
  `endHour` int(11) NOT NULL,
  `endMinute` int(11) NOT NULL,
  `date_millis` bigint(20) DEFAULT NULL,
  `created` datetime NOT NULL DEFAULT current_timestamp(),
  `booking_title` varchar(50) DEFAULT 'Filmdag',
  `booking_description` text DEFAULT 'Ingen beskrivning'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `startHour`, `startMinute`, `endHour`, `endMinute`, `date_millis`, `created`, `booking_title`, `booking_description`) VALUES
(1, 12, 0, 16, 0, 1761177600504, '2025-10-08 16:10:38', NULL, NULL),
(2, 21, 0, 17, 0, 1761696000081, '2025-10-08 16:33:43', NULL, NULL),
(5, 4, 32, 4, 29, 1761782400483, '2025-10-08 18:24:56', NULL, NULL),
(11, 2, 0, 12, 22, 1761264000762, '2025-10-08 20:00:03', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `id` bigint(20) NOT NULL,
  `encryptedValue` longtext DEFAULT NULL,
  `created` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`id`, `encryptedValue`, `created`) VALUES
(1, 'ej', '2025-10-09 03:39:51'),
(2, 'ej', '2025-10-09 03:39:54'),
(3, 'd', '2025-10-09 03:41:44'),
(4, 'hej', '2025-10-09 03:51:59'),
(5, 'gXlMFJbdtcGhP8YoPhkHexGj9zHSdbObZlufy4Mylg==', '2025-10-12 19:09:59'),
(6, 'YLs+mX4gtLO1W+c2kkkOP8unYblulzG5wOjTzdwE6K0=', '2025-10-12 19:10:50');

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE `projects` (
  `id` bigint(20) NOT NULL,
  `projectName` varchar(50) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `created` datetime DEFAULT current_timestamp(),
  `request_rule` varchar(20) NOT NULL DEFAULT 'MANUAL'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`id`, `projectName`, `description`, `created`, `request_rule`) VALUES
(1, 'Lord of the Rings', 'Film trilogy project', '2025-10-08 15:13:09', 'MANUAL'),
(3, 'ddd', 'dd', '2025-10-09 03:46:30', 'MANUAL'),
(4, 'dd', 'dd', '2025-10-09 03:50:14', 'MANUAL'),
(5, 'Alexanders Film', 'Coolt', '2025-10-09 05:20:01', 'MANUAL'),
(10, 'cd', 'dd', '2025-10-09 07:31:34', 'MANUAL'),
(12, 'xdxx', 'xxxx', '2025-10-09 07:55:40', 'MANUAL'),
(13, 'ynyf', 'sdsd', '2025-10-09 08:31:49', 'MANUAL'),
(15, 'ssss', 'sss', '2025-10-09 09:05:46', 'MANUAL'),
(16, 'zzxc', 'dccdd', '2025-10-09 09:20:20', 'MANUAL'),
(17, 'ZccczcxczxcS', 'ss', '2025-10-09 09:41:46', 'MANUAL');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `publicKey` varchar(255) DEFAULT NULL,
  `secretKey` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `role` varchar(50) DEFAULT NULL,
  `work_role` enum('regissör','statist','fotograf','skådespelare','ljudtekniker','producent','redigerare','manusförfattare','kostymör','VFX/3D','Övrigt') DEFAULT NULL,
  `roles` varchar(25) DEFAULT NULL,
  `film_role` varchar(50) DEFAULT NULL
) ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `email`, `publicKey`, `secretKey`, `first_name`, `last_name`, `role`, `work_role`, `roles`, `film_role`) VALUES
(96, 'tomcruise', '11111111', NULL, NULL, NULL, 'Tom', 'Cruise', NULL, NULL, NULL, NULL),
(97, 'bradpitt', '11111111', NULL, NULL, NULL, 'Brad', 'Pitt', NULL, NULL, NULL, NULL),
(98, 'leodicaprio', '11111111', NULL, NULL, NULL, 'Leonardo', 'DiCaprio', NULL, NULL, NULL, NULL),
(99, 'margotrobb', '11111111', NULL, NULL, NULL, 'Margot', 'Robbie', NULL, NULL, NULL, NULL),
(100, 'mattdamon', '11111111', NULL, NULL, NULL, 'Matt', 'Damon', NULL, NULL, NULL, NULL),
(101, 'benaffleck', '11111111', NULL, NULL, NULL, 'Ben', 'Affleck', NULL, NULL, NULL, NULL),
(102, 'jlawrence', '11111111', NULL, NULL, NULL, 'Jennifer', 'Lawrence', NULL, NULL, NULL, NULL),
(103, 'angelinaj', '11111111', NULL, NULL, NULL, 'Angelina', 'Jolie', NULL, NULL, NULL, NULL),
(104, 'scarlettj', '11111111', NULL, NULL, NULL, 'Scarlett', 'Johansson', NULL, NULL, NULL, NULL),
(105, 'chrishemsw', '11111111', NULL, NULL, NULL, 'Chris', 'Hemsworth', NULL, NULL, NULL, NULL),
(106, 'chrispratt', '11111111', NULL, NULL, NULL, 'Chris', 'Pratt', NULL, NULL, NULL, NULL),
(107, 'chrisevans', '11111111', NULL, NULL, NULL, 'Chris', 'Evans', NULL, NULL, NULL, NULL),
(108, 'rdowneyjr', '11111111', NULL, NULL, NULL, 'Robert', 'DowneyJr', NULL, NULL, NULL, NULL),
(109, 'markruffalo', '11111111', NULL, NULL, NULL, 'Mark', 'Ruffalo', NULL, NULL, NULL, NULL),
(110, 'tomholland', '11111111', NULL, NULL, NULL, 'Tom', 'Holland', NULL, NULL, NULL, NULL),
(111, 'zendaya', '11111111', NULL, NULL, NULL, 'Zendaya', 'Coleman', NULL, NULL, NULL, NULL),
(112, 'natalieport', '11111111', NULL, NULL, NULL, 'Natalie', 'Portman', NULL, NULL, NULL, NULL),
(113, 'keanureeves', '11111111', NULL, NULL, NULL, 'Keanu', 'Reeves', NULL, NULL, NULL, NULL),
(114, 'winonaryder', '11111111', NULL, NULL, NULL, 'Winona', 'Ryder', NULL, NULL, NULL, NULL),
(115, 'samuelljack', '11111111', NULL, NULL, NULL, 'Samuel', 'Jackson', NULL, NULL, NULL, NULL),
(116, 'morganfreem', '11111111', NULL, NULL, NULL, 'Morgan', 'Freeman', NULL, NULL, NULL, NULL),
(117, 'denzelwash', '11111111', NULL, NULL, NULL, 'Denzel', 'Washington', NULL, NULL, NULL, NULL),
(118, 'tomhanks', '11111111', NULL, NULL, NULL, 'Tom', 'Hanks', NULL, NULL, NULL, NULL),
(119, 'emmastonex', '11111111', NULL, NULL, NULL, 'Emma', 'Stone', NULL, NULL, NULL, NULL),
(120, 'ryangosling', '11111111', NULL, NULL, NULL, 'Ryan', 'Gosling', NULL, NULL, NULL, NULL),
(121, 'ryanreynolds', '11111111', NULL, NULL, NULL, 'Ryan', 'Reynolds', NULL, NULL, NULL, NULL),
(122, 'sandrabull', '11111111', NULL, NULL, NULL, 'Sandra', 'Bullock', NULL, NULL, NULL, NULL),
(123, 'georgecloon', '11111111', NULL, NULL, NULL, 'George', 'Clooney', NULL, NULL, NULL, NULL),
(124, 'brucewillis', '11111111', NULL, NULL, NULL, 'Bruce', 'Willis', NULL, NULL, NULL, NULL),
(125, 'nicholascage', '11111111', NULL, NULL, NULL, 'Nicolas', 'Cage', NULL, NULL, NULL, NULL),
(126, 'johntravolt', '11111111', NULL, NULL, NULL, 'John', 'Travolta', NULL, NULL, NULL, NULL),
(127, 'umathurman', '11111111', NULL, NULL, NULL, 'Uma', 'Thurman', NULL, NULL, NULL, NULL),
(128, 'harrisonf', '11111111', NULL, NULL, NULL, 'Harrison', 'Ford', NULL, NULL, NULL, NULL),
(129, 'carriefishe', '11111111', NULL, NULL, NULL, 'Carrie', 'Fisher', NULL, NULL, NULL, NULL),
(130, 'markhamill', '11111111', NULL, NULL, NULL, 'Mark', 'Hamill', NULL, NULL, NULL, NULL),
(131, 'daisyridley', '11111111', NULL, NULL, NULL, 'Daisy', 'Ridley', NULL, NULL, NULL, NULL),
(132, 'adamdriver', '11111111', NULL, NULL, NULL, 'Adam', 'Driver', NULL, NULL, NULL, NULL),
(133, 'oscarisaac', '11111111', NULL, NULL, NULL, 'Oscar', 'Isaac', NULL, NULL, NULL, NULL),
(134, 'pedropascal', '11111111', NULL, NULL, NULL, 'Pedro', 'Pascal', NULL, NULL, NULL, NULL),
(135, 'bellaramsey', '11111111', NULL, NULL, NULL, 'Bella', 'Ramsey', NULL, NULL, NULL, NULL),
(136, 'christianb', '11111111', NULL, NULL, NULL, 'Christian', 'Bale', NULL, NULL, NULL, NULL),
(137, 'heathledger', '11111111', NULL, NULL, NULL, 'Heath', 'Ledger', NULL, NULL, NULL, NULL),
(138, 'joaquinpho', '11111111', NULL, NULL, NULL, 'Joaquin', 'Phoenix', NULL, NULL, NULL, NULL),
(139, 'robertpatts', '11111111', NULL, NULL, NULL, 'Robert', 'Pattinson', NULL, NULL, NULL, NULL),
(140, 'kristenstew', '11111111', NULL, NULL, NULL, 'Kristen', 'Stewart', NULL, NULL, NULL, NULL),
(141, 'annehathaway', '11111111', NULL, NULL, NULL, 'Anne', 'Hathaway', NULL, NULL, NULL, NULL),
(142, 'merylstreep', '11111111', NULL, NULL, NULL, 'Meryl', 'Streep', NULL, NULL, NULL, NULL),
(143, 'amyadams', '11111111', NULL, NULL, NULL, 'Amy', 'Adams', NULL, NULL, NULL, NULL),
(144, 'juliaroberts', '11111111', NULL, NULL, NULL, 'Julia', 'Roberts', NULL, NULL, NULL, NULL),
(145, 'nicolekidman', '11111111', NULL, NULL, NULL, 'Nicole', 'Kidman', NULL, NULL, NULL, NULL),
(146, 'charlizether', '11111111', NULL, NULL, NULL, 'Charlize', 'Theron', NULL, NULL, NULL, NULL),
(147, 'galgadot', '11111111', NULL, NULL, NULL, 'Gal', 'Gadot', NULL, NULL, NULL, NULL),
(148, 'henrycavill', '11111111', NULL, NULL, NULL, 'Henry', 'Cavill', NULL, NULL, NULL, NULL),
(149, 'jasonmomoa', '11111111', NULL, NULL, NULL, 'Jason', 'Momoa', NULL, NULL, NULL, NULL),
(150, 'dwaynejohns', '11111111', NULL, NULL, NULL, 'Dwayne', 'Johnson', NULL, NULL, NULL, NULL),
(151, 'vindiesel', '11111111', NULL, NULL, NULL, 'Vin', 'Diesel', NULL, NULL, NULL, NULL),
(152, 'paulwalker', '11111111', NULL, NULL, NULL, 'Paul', 'Walker', NULL, NULL, NULL, NULL),
(153, 'michellerodr', '11111111', NULL, NULL, NULL, 'Michelle', 'Rodriguez', NULL, NULL, NULL, NULL),
(154, 'jordanabrew', '11111111', NULL, NULL, NULL, 'Jordana', 'Brewster', NULL, NULL, NULL, NULL),
(155, 'emiliaclarke', '11111111', NULL, NULL, NULL, 'Emilia', 'Clarke', NULL, NULL, NULL, NULL),
(156, 'kitharingto', '11111111', NULL, NULL, NULL, 'Kit', 'Harington', NULL, NULL, NULL, NULL),
(157, 'sophiaturner', '11111111', NULL, NULL, NULL, 'Sophie', 'Turner', NULL, NULL, NULL, NULL),
(158, 'maisiewillia', '11111111', NULL, NULL, NULL, 'Maisie', 'Williams', NULL, NULL, NULL, NULL),
(159, 'seanbean', '11111111', NULL, NULL, NULL, 'Sean', 'Bean', NULL, NULL, NULL, NULL),
(160, 'ianmckellen', '11111111', NULL, NULL, NULL, 'Ian', 'McKellen', NULL, NULL, NULL, NULL),
(161, 'patrickstewa', '11111111', NULL, NULL, NULL, 'Patrick', 'Stewart', NULL, NULL, NULL, NULL),
(162, 'hughjackman', '11111111', NULL, NULL, NULL, 'Hugh', 'Jackman', NULL, NULL, NULL, NULL),
(163, 'taronedgert', '11111111', NULL, NULL, NULL, 'Taron', 'Egerton', NULL, NULL, NULL, NULL),
(164, 'colinfirth', '11111111', NULL, NULL, NULL, 'Colin', 'Firth', NULL, NULL, NULL, NULL),
(165, 'keiraknight', '11111111', NULL, NULL, NULL, 'Keira', 'Knightley', NULL, NULL, NULL, NULL),
(166, 'danielcraig', '11111111', NULL, NULL, NULL, 'Daniel', 'Craig', NULL, NULL, NULL, NULL),
(167, 'ralphfiennes', '11111111', NULL, NULL, NULL, 'Ralph', 'Fiennes', NULL, NULL, NULL, NULL),
(168, 'benedictc', '11111111', NULL, NULL, NULL, 'Benedict', 'Cumberbatch', NULL, NULL, NULL, NULL),
(169, 'martinfreem', '11111111', NULL, NULL, NULL, 'Martin', 'Freeman', NULL, NULL, NULL, NULL),
(170, 'andyserkis', '11111111', NULL, NULL, NULL, 'Andy', 'Serkis', NULL, NULL, NULL, NULL),
(171, 'orlandobloo', '11111111', NULL, NULL, NULL, 'Orlando', 'Bloom', NULL, NULL, NULL, NULL),
(172, 'viggomorten', '11111111', NULL, NULL, NULL, 'Viggo', 'Mortensen', NULL, NULL, NULL, NULL),
(173, 'seanastin', '11111111', NULL, NULL, NULL, 'Sean', 'Astin', NULL, NULL, NULL, NULL),
(174, 'elijahwood', '11111111', NULL, NULL, NULL, 'Elijah', 'Wood', NULL, NULL, NULL, NULL),
(175, 'katewinslet', '11111111', NULL, NULL, NULL, 'Kate', 'Winslet', NULL, NULL, NULL, NULL),
(176, 'cateblanche', '11111111', NULL, NULL, NULL, 'Cate', 'Blanchett', NULL, NULL, NULL, NULL),
(177, 'rooneymara', '11111111', NULL, NULL, NULL, 'Rooney', 'Mara', NULL, NULL, NULL, NULL),
(178, 'brielarson', '11111111', NULL, NULL, NULL, 'Brie', 'Larson', NULL, NULL, NULL, NULL),
(179, 'tessathomps', '11111111', NULL, NULL, NULL, 'Tessa', 'Thompson', NULL, NULL, NULL, NULL),
(180, 'anthonymack', '11111111', NULL, NULL, NULL, 'Anthony', 'Mackie', NULL, NULL, NULL, NULL),
(181, 'sebastianst', '11111111', NULL, NULL, NULL, 'Sebastian', 'Stan', NULL, NULL, NULL, NULL),
(182, 'paulrudd', '11111111', NULL, NULL, NULL, 'Paul', 'Rudd', NULL, NULL, NULL, NULL),
(183, 'evangelinel', '11111111', NULL, NULL, NULL, 'Evangeline', 'Lilly', NULL, NULL, NULL, NULL),
(184, 'tomhardy', '11111111', NULL, NULL, NULL, 'Tom', 'Hardy', NULL, NULL, NULL, NULL),
(185, 'michaelfass', '11111111', NULL, NULL, NULL, 'Michael', 'Fassbender', NULL, NULL, NULL, NULL),
(186, 'aliciavikand', '11111111', NULL, NULL, NULL, 'Alicia', 'Vikander', NULL, NULL, NULL, NULL),
(187, 'jakegyllen', '11111111', NULL, NULL, NULL, 'Jake', 'Gyllenhaal', NULL, NULL, NULL, NULL),
(188, 'naomiwatts', '11111111', NULL, NULL, NULL, 'Naomi', 'Watts', NULL, NULL, NULL, NULL),
(189, 'ewanmcgreg', '11111111', NULL, NULL, NULL, 'Ewan', 'McGregor', NULL, NULL, NULL, NULL),
(190, 'liamneeson', '11111111', NULL, NULL, NULL, 'Liam', 'Neeson', NULL, NULL, NULL, NULL),
(191, 'andrewgarf', '11111111', NULL, NULL, NULL, 'Andrew', 'Garfield', NULL, NULL, NULL, NULL),
(192, 'emmabluntx', '11111111', NULL, NULL, NULL, 'Emily', 'Blunt', NULL, NULL, NULL, NULL),
(193, 'johnkrasins', '11111111', NULL, NULL, NULL, 'John', 'Krasinski', NULL, NULL, NULL, NULL),
(194, 'florencepug', '11111111', NULL, NULL, NULL, 'Florence', 'Pugh', NULL, NULL, NULL, NULL),
(195, 'austinbutle', '11111111', NULL, NULL, NULL, 'Austin', 'Butler', NULL, NULL, NULL, NULL),
(196, 'zendesdaya', '11111111', NULL, NULL, NULL, 'Zendaya', 'Coleman2', NULL, NULL, NULL, NULL),
(197, 'timoshalame', '11111111', NULL, NULL, NULL, 'Timothée', 'Chalamet', NULL, NULL, NULL, NULL),
(198, 'anyataylorj', '11111111', NULL, NULL, NULL, 'Anya', 'Taylor-Joy', NULL, NULL, NULL, NULL),
(199, 'anasophiar', '11111111', NULL, NULL, NULL, 'Ana', 'deArmas', NULL, NULL, NULL, NULL),
(200, 'rosaperezx', '11111111', NULL, NULL, NULL, 'Rosie', 'Perez', NULL, NULL, NULL, NULL),
(201, 'salmahayekx', '11111111', NULL, NULL, NULL, 'Salma', 'Hayek', NULL, NULL, NULL, NULL),
(202, 'penelopecru', '11111111', NULL, NULL, NULL, 'Penélope', 'Cruz', NULL, NULL, NULL, NULL),
(203, 'sofiavergax', '11111111', NULL, NULL, NULL, 'Sofía', 'Vergara', NULL, NULL, NULL, NULL),
(204, 'javierbardx', '11111111', NULL, NULL, NULL, 'Javier', 'Bardem', NULL, NULL, NULL, NULL),
(205, 'monicabellx', '11111111', NULL, NULL, NULL, 'Monica', 'Bellucci', NULL, NULL, NULL, NULL),
(206, 'rdgosling2', '11111111', NULL, NULL, NULL, 'Ryan', 'Gosling2', NULL, NULL, NULL, NULL),
(207, 'nportman2', '11111111', NULL, NULL, NULL, 'Natalie', 'Portman2', NULL, NULL, NULL, NULL),
(208, 'cblanchet2', '11111111', NULL, NULL, NULL, 'Cate', 'Blanchett2', NULL, NULL, NULL, NULL),
(209, 'kjameswoods', '11111111', NULL, NULL, NULL, 'James', 'Woods', NULL, NULL, NULL, NULL),
(210, 'peterjackson', '11111111', NULL, NULL, NULL, 'Peter', 'Jackson', NULL, NULL, NULL, NULL),
(248, 'alexander01', '11111111', NULL, NULL, NULL, 'Alexander', 'Andersson', NULL, NULL, NULL, NULL),
(249, 'Samuel', '$2a$12$R6ZSIVucaVIumoaXCzKm7ufyu5VG.nWJOilATJ9Ww..IcvlYOi.Ry', 'ssss@gmail.com', NULL, 't3fvWIL/8YQrqo9EkCPnHtM18Mj04L0x79BQW2P2Nk0=', 'Samuel', 'Bromee', NULL, NULL, NULL, NULL),
(250, 'Benjamin', '$2a$12$oyeddo13vs2j4HlMM8bnE.ndkrW3JVvdV6pW8HYigseDFmUxtMHje', 'benjamin@gmail.com', NULL, 't8GqTMhpPkNEBfc4z/k7Z8psLIVvtBreEaKizaIOgDM=', 'Benjamin', 'Giordano', NULL, NULL, NULL, NULL),
(252, 'Alexander', '$2a$12$Qa0qqoKAHMxCsQD.RaBoh.F0Y.jdsgxwtr8fauddg8g8od5quSyYK', 'alex@jansson', NULL, 'MmI3mG0MNVOdvkizWGKS/drIkNHNx/V7XO2XteXZVuE=', 'Alexander', 'Jansson', NULL, NULL, NULL, NULL),
(253, 'Alexander1', '$2a$12$CIRJQ1QaslhAIg111il5zOxGg1qFgAfpBZDThd6h3MmvHSQxZoKmC', 'alexanderjsn@l', 'MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEvqtzhWeTdxszaQY/+QOiqC1RpAOUzNM6mUNjqjg2bJbWLvkYRQ1pFhmtMJKpZHxI9o9j4vV2qaOoBxGZgWjfUg==', 'MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCAUpMwLmMQsquS097LsutGlV81rXT45M5O2J8JptT3fTA==', 'Alexander', 'Jansson', NULL, NULL, NULL, NULL),
(254, 'Benjamin1', '$2a$12$iIbezuD.zoMuDt9KEW3FsuGfwlqb/gwKeTuXh8ffsrpBAbbOWePm.', 'benjamin@gmaillcom', 'MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEeSZY+H4GczURi1bLXQZD3PlQOaQsIGxxw/MzSHvByUNa137pdfSM6n0/a2O0KOI4jPhEPGXLrdNEx5RZ1cT7hA==', 'MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCCU6eO3ayxpvJWh+jyq1WOdcWVK72cpZ/0EnGIfskueEg==', 'Benjamin', 'Giordano', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `user_bookings`
--

CREATE TABLE `user_bookings` (
  `id` bigint(20) NOT NULL,
  `booking_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `status` varchar(25) DEFAULT 'UNASSIGNED'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_bookings`
--

INSERT INTO `user_bookings` (`id`, `booking_id`, `user_id`, `project_id`, `status`) VALUES
(3, 2, 171, 1, 'INVITE'),
(10, 5, 174, 1, 'INVITE');

-- --------------------------------------------------------

--
-- Table structure for table `user_messages`
--

CREATE TABLE `user_messages` (
  `id` bigint(20) NOT NULL,
  `isRead` tinyint(1) DEFAULT 0,
  `sender_id` bigint(20) NOT NULL,
  `recipient_id` bigint(20) NOT NULL,
  `message_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_messages`
--

INSERT INTO `user_messages` (`id`, `isRead`, `sender_id`, `recipient_id`, `message_id`) VALUES
(5, 0, 254, 253, 5),
(6, 0, 253, 254, 6);

-- --------------------------------------------------------

--
-- Table structure for table `user_project`
--

CREATE TABLE `user_project` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `project_id` bigint(20) NOT NULL,
  `role` varchar(15) DEFAULT NULL,
  `isCreator` tinyint(1) DEFAULT NULL,
  `isAdmin` tinyint(1) DEFAULT NULL,
  `hasJoined` tinyint(1) DEFAULT NULL,
  `isBlocked` tinyint(1) DEFAULT NULL,
  `requestedDate` datetime DEFAULT current_timestamp(),
  `join_status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_project`
--

INSERT INTO `user_project` (`id`, `user_id`, `project_id`, `role`, `isCreator`, `isAdmin`, `hasJoined`, `isBlocked`, `requestedDate`, `join_status`) VALUES
(4, 160, 1, NULL, 0, 0, 1, NULL, '2025-10-08 15:13:09', 'ACCEPTED'),
(5, 171, 1, NULL, 0, 0, 1, NULL, '2025-10-08 15:13:09', 'ACCEPTED'),
(6, 173, 1, NULL, 0, 0, 1, NULL, '2025-10-08 15:13:09', 'ACCEPTED'),
(7, 159, 1, NULL, 0, 0, 1, NULL, '2025-10-08 15:13:09', 'ACCEPTED'),
(10, 248, 1, NULL, 0, 1, 1, NULL, '2025-10-08 15:17:24', 'ACCEPTED'),
(28, 250, 10, NULL, 1, 1, 1, NULL, '2025-10-09 07:31:34', 'ACCEPTED'),
(30, 252, 12, NULL, 1, 1, 1, NULL, '2025-10-09 07:55:40', 'ACCEPTED'),
(31, 249, 12, NULL, 0, 0, 0, NULL, '2025-10-09 07:55:57', 'ACCEPTED'),
(32, 250, 12, NULL, 0, 0, 0, NULL, '2025-10-09 07:56:01', 'INVITE'),
(35, 250, 13, NULL, 0, 0, 0, NULL, '2025-10-09 08:32:03', 'INVITE'),
(36, 249, 13, NULL, 0, 0, 0, NULL, '2025-10-09 08:40:18', 'ACCEPTED'),
(41, 252, 15, NULL, 1, 1, 1, NULL, '2025-10-09 09:05:46', 'ACCEPTED'),
(43, 250, 15, NULL, 0, 0, 0, NULL, '2025-10-09 09:05:59', 'INVITE'),
(44, 249, 15, NULL, 0, 0, 0, NULL, '2025-10-09 09:07:04', 'REQUEST'),
(45, 252, 16, NULL, 1, 1, 1, NULL, '2025-10-09 09:20:20', 'ACCEPTED'),
(46, 252, 17, NULL, 1, 1, 1, NULL, '2025-10-09 09:41:46', 'ACCEPTED');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `y_o_e` decimal(4,1) DEFAULT NULL,
  `yoe` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`id`, `user_id`, `role_id`, `y_o_e`, `yoe`) VALUES
(2, 249, 31, NULL, NULL),
(3, 250, 29, NULL, NULL),
(7, 252, 35, NULL, NULL),
(8, 253, 29, NULL, NULL),
(9, 254, 33, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `workroles`
--

CREATE TABLE `workroles` (
  `id` bigint(20) NOT NULL,
  `roletype` enum('regissör','statist','fotograf','skådespelare','ljudtekniker','producent','redigerare','manusförfattare','kostymör','VFX/3D','Övrigt') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `workroles`
--

INSERT INTO `workroles` (`id`, `roletype`) VALUES
(29, 'regissör'),
(30, 'statist'),
(31, 'producent'),
(32, 'fotograf'),
(33, 'skådespelare'),
(34, 'ljudtekniker'),
(35, 'redigerare'),
(36, 'manusförfattare'),
(37, 'Övrigt');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_username` (`username`),
  ADD UNIQUE KEY `uq_email` (`email`);

--
-- Indexes for table `user_bookings`
--
ALTER TABLE `user_bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_userbooking_booking` (`booking_id`),
  ADD KEY `fk_userbooking_user` (`user_id`),
  ADD KEY `fk_userbooking_project` (`project_id`);

--
-- Indexes for table `user_messages`
--
ALTER TABLE `user_messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_messages_message` (`message_id`),
  ADD KEY `ix_um_sender_recipient` (`sender_id`,`recipient_id`),
  ADD KEY `ix_um_recipient_sender` (`recipient_id`,`sender_id`);

--
-- Indexes for table `user_project`
--
ALTER TABLE `user_project`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_user_project` (`project_id`,`user_id`),
  ADD KEY `ix_user_project_user` (`user_id`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uq_user_role` (`user_id`,`role_id`),
  ADD KEY `fk_user_roles_role` (`role_id`);

--
-- Indexes for table `workroles`
--
ALTER TABLE `workroles`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `projects`
--
ALTER TABLE `projects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_bookings`
--
ALTER TABLE `user_bookings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `user_messages`
--
ALTER TABLE `user_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `user_project`
--
ALTER TABLE `user_project`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `user_roles`
--
ALTER TABLE `user_roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `workroles`
--
ALTER TABLE `workroles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_bookings`
--
ALTER TABLE `user_bookings`
  ADD CONSTRAINT `fk_userbooking_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_userbooking_project` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_userbooking_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_messages`
--
ALTER TABLE `user_messages`
  ADD CONSTRAINT `fk_user_messages_message` FOREIGN KEY (`message_id`) REFERENCES `messages` (`id`),
  ADD CONSTRAINT `fk_user_messages_recipient` FOREIGN KEY (`recipient_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_user_messages_sender` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_project`
--
ALTER TABLE `user_project`
  ADD CONSTRAINT `fk_user_project_project` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  ADD CONSTRAINT `fk_user_project_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `fk_user_roles_role` FOREIGN KEY (`role_id`) REFERENCES `workroles` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_user_roles_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
