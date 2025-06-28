-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 28, 2025 at 11:33 PM
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
-- Database: `management-asset`
--

-- --------------------------------------------------------

--
-- Table structure for table `assets`
--

CREATE TABLE `assets` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `merk` enum('Asus','Acer','Dell','Hp','Lenovo') NOT NULL,
  `type` varchar(50) NOT NULL,
  `spesifikasi` varchar(150) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `assets`
--

INSERT INTO `assets` (`id`, `merk`, `type`, `spesifikasi`, `created_at`, `updated_at`) VALUES
(1, 'Lenovo', 'Thinkpad E14', 'I5 Gen11|16 GB|256 GB|', '2025-06-28 13:52:58', '2025-06-28 13:52:58'),
(2, 'Dell', 'Latitude 5420', 'I5 Gen11|16 GB|256 GB|', '2025-06-28 13:53:15', '2025-06-28 13:53:15');

-- --------------------------------------------------------

--
-- Table structure for table `detailasset`
--

CREATE TABLE `detailasset` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `asset_id` bigint(20) UNSIGNED NOT NULL,
  `serialnumber` varchar(50) NOT NULL,
  `kondisi` enum('normal','rusak') NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `detailasset`
--

INSERT INTO `detailasset` (`id`, `asset_id`, `serialnumber`, `kondisi`, `created_at`, `updated_at`) VALUES
(1, 1, 'MJ0GHALK', 'normal', '2025-06-28 13:52:58', '2025-06-28 13:52:58'),
(2, 2, '9MZCNQ3', 'normal', '2025-06-28 13:53:15', '2025-06-28 13:53:15');

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(69, '2014_10_12_000000_create_users_table', 1),
(70, '2019_10_19_000000_create_project_table', 1),
(71, '2019_12_14_000001_create_asset_table', 1),
(72, '2019_12_14_000001_create_personal_access_tokens_table', 1),
(73, '2025_06_19_035639_create_detailasset_table', 1),
(74, '2025_06_19_035741_create_pengiriman_table', 1),
(75, '2025_06_19_035840_create_rental_table', 1),
(76, '2025_06_19_036060_create_tagihan_table', 1);

-- --------------------------------------------------------

--
-- Table structure for table `pengiriman`
--

CREATE TABLE `pengiriman` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `detailasset_id` bigint(20) UNSIGNED NOT NULL,
  `tanggal_pengiriman` date NOT NULL,
  `pic_pengirim` varchar(255) NOT NULL,
  `pic_penerima` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `pengiriman`
--

INSERT INTO `pengiriman` (`id`, `detailasset_id`, `tanggal_pengiriman`, `pic_pengirim`, `pic_penerima`, `created_at`, `updated_at`) VALUES
(1, 1, '2025-06-29', 'Danu', 'Acep', '2025-06-28 13:54:44', '2025-06-28 13:54:44'),
(2, 2, '2025-06-29', 'Danu', 'Acep', '2025-06-28 13:55:05', '2025-06-28 13:55:05');

-- --------------------------------------------------------

--
-- Table structure for table `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `token` varchar(64) NOT NULL,
  `abilities` text DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE `projects` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `nama` varchar(250) NOT NULL,
  `durasi_kontrak` int(11) NOT NULL,
  `harga_sewa` decimal(15,2) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`id`, `nama`, `durasi_kontrak`, `harga_sewa`, `created_at`, `updated_at`) VALUES
(1, 'iForte', 2, 550000.00, '2025-06-28 13:53:30', '2025-06-28 13:53:30');

-- --------------------------------------------------------

--
-- Table structure for table `rental`
--

CREATE TABLE `rental` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `pengiriman_id` bigint(20) UNSIGNED NOT NULL,
  `project_id` bigint(20) UNSIGNED NOT NULL,
  `status` enum('aktif') NOT NULL DEFAULT 'aktif',
  `periode_mulai` date NOT NULL,
  `periode_ahir` date NOT NULL,
  `total_tagihan` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `rental`
--

INSERT INTO `rental` (`id`, `pengiriman_id`, `project_id`, `status`, `periode_mulai`, `periode_ahir`, `total_tagihan`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'aktif', '2025-06-29', '2025-08-29', 550000, '2025-06-28 13:54:44', '2025-06-28 13:54:44'),
(2, 2, 1, 'aktif', '2025-06-29', '2025-08-29', 550000, '2025-06-28 13:55:05', '2025-06-28 13:55:05');

-- --------------------------------------------------------

--
-- Table structure for table `tagihan`
--

CREATE TABLE `tagihan` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `rental_id` bigint(20) UNSIGNED NOT NULL,
  `nomor_invoice` int(11) NOT NULL,
  `keterangan` varchar(255) NOT NULL,
  `tanggal_tagihan` date NOT NULL,
  `jumlah_unit` int(11) NOT NULL,
  `durasi_tagih` varchar(255) NOT NULL,
  `grand_total` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `password`, `created_at`, `updated_at`) VALUES
(1, 'Danu Febri Andi Prasetyo', 'danuprasetya573@gmail.com', NULL, '$2y$12$ddjhOryBLaVZu.4i5phGaO7pDqn6Qy6S7.5eX45w3fBUnf.a7Cy0q', '2025-06-28 13:36:03', '2025-06-28 13:36:03');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `assets`
--
ALTER TABLE `assets`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `detailasset`
--
ALTER TABLE `detailasset`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `detailasset_serialnumber_unique` (`serialnumber`),
  ADD KEY `detailasset_asset_id_foreign` (`asset_id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pengiriman`
--
ALTER TABLE `pengiriman`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pengiriman_detailasset_id_foreign` (`detailasset_id`);

--
-- Indexes for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Indexes for table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rental`
--
ALTER TABLE `rental`
  ADD PRIMARY KEY (`id`),
  ADD KEY `rental_pengiriman_id_foreign` (`pengiriman_id`),
  ADD KEY `rental_project_id_foreign` (`project_id`);

--
-- Indexes for table `tagihan`
--
ALTER TABLE `tagihan`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tagihan_nomor_invoice_unique` (`nomor_invoice`),
  ADD KEY `tagihan_rental_id_foreign` (`rental_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `assets`
--
ALTER TABLE `assets`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `detailasset`
--
ALTER TABLE `detailasset`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT for table `pengiriman`
--
ALTER TABLE `pengiriman`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `projects`
--
ALTER TABLE `projects`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `rental`
--
ALTER TABLE `rental`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tagihan`
--
ALTER TABLE `tagihan`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detailasset`
--
ALTER TABLE `detailasset`
  ADD CONSTRAINT `detailasset_asset_id_foreign` FOREIGN KEY (`asset_id`) REFERENCES `assets` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `pengiriman`
--
ALTER TABLE `pengiriman`
  ADD CONSTRAINT `pengiriman_detailasset_id_foreign` FOREIGN KEY (`detailasset_id`) REFERENCES `detailasset` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `rental`
--
ALTER TABLE `rental`
  ADD CONSTRAINT `rental_pengiriman_id_foreign` FOREIGN KEY (`pengiriman_id`) REFERENCES `pengiriman` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `rental_project_id_foreign` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `tagihan`
--
ALTER TABLE `tagihan`
  ADD CONSTRAINT `tagihan_rental_id_foreign` FOREIGN KEY (`rental_id`) REFERENCES `rental` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
