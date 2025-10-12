-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 06, 2025 at 04:56 AM
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
  `booking_title` varchar(50) NOT NULL DEFAULT 'Filmdag',
  `booking_description` text DEFAULT 'Ingen beskrivning'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(27, 'hej samuel', '2025-10-05 23:34:53');

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
(27, 'Samuels Film', '12312312312', '2025-10-05 23:25:04', 'MANUAL');

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
(44, 'Samuel', '$2a$12$k.5FJIBx2myBeGi6kGr5Ru2ptTlgf8N2UHg/OWC3Jql26pmSARKmC', 'dfsfd@gmai.com', NULL, 'Ng6Uqc/5A+cP9OjjpLGLdDuxXiGGrEb8nEVhgcbledw=', 'Samuel', 'vsdvdsv', NULL, NULL, NULL, NULL),
(45, 'WesAnderson', '$2a$12$WnSXyyP7wrnA8zRw.cdZruzM6/sZNHznBK.wJYcMaB0VvhpGvY26.', 'pfds@gmail.com', NULL, 'gryxwhYeRy01hEVP6oIu3q5WW8c08tmuh7yhvyTpAew=', 'Wes', 'Anderson', NULL, NULL, NULL, NULL);

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
(1, 0, 45, 44, 27);

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
(1, 44, 27, NULL, 1, 1, 1, NULL, '2025-10-05 23:25:04', 'ACCEPTED'),
(2, 45, 27, NULL, 0, 0, 0, NULL, '2025-10-05 23:29:06', 'DECLINED');

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
(1, 44, 33, NULL, NULL),
(2, 45, 29, NULL, NULL);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `projects`
--
ALTER TABLE `projects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_bookings`
--
ALTER TABLE `user_bookings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_messages`
--
ALTER TABLE `user_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `user_project`
--
ALTER TABLE `user_project`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user_roles`
--
ALTER TABLE `user_roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
  ADD CONSTRAINT `fk_user_messages_recipient` FOREIGN KEY (`recipient_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_user_messages_sender` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `user_project`
--
ALTER TABLE `user_project`
  ADD CONSTRAINT `fk_user_project_project` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  ADD CONSTRAINT `fk_user_project_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

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
