-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jul 27, 2022 at 01:36 PM
-- Server version: 10.5.13-MariaDB-cll-lve
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `andromob`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_category`
--

CREATE TABLE `tbl_category` (
  `cat_id` int(11) NOT NULL,
  `category_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `category_image` text CHARACTER SET utf8 NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_category`
--

INSERT INTO `tbl_category` (`cat_id`, `category_name`, `category_image`, `status`) VALUES
(1, 'Sports', '22755_sport.jpg', 1),
(2, 'Movies', '8941_movies.jpg', 1),
(3, 'News', '39733_news.jpg', 1),
(4, 'Kids', '67822_kids.jpg', 1),
(5, 'Entertainment', '92603_enter.jpeg', 1),
(6, 'Education', '82197_education.jpg', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_channels`
--

CREATE TABLE `tbl_channels` (
  `id` int(11) NOT NULL,
  `cid` int(11) NOT NULL,
  `channel_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `frequency` varchar(255) NOT NULL,
  `description` text CHARACTER SET utf8 NOT NULL,
  `channel_icon` text CHARACTER SET utf8 NOT NULL,
  `channel_banner` varchar(255) CHARACTER SET utf8 NOT NULL,
  `source_url` text CHARACTER SET utf8 NOT NULL,
  `views` int(11) NOT NULL,
  `channel_status` int(11) NOT NULL DEFAULT 1,
  `slider_status` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_channels`
--

INSERT INTO `tbl_channels` (`id`, `cid`, `channel_name`, `frequency`, `description`, `channel_icon`, `channel_banner`, `source_url`, `views`, `channel_status`, `slider_status`) VALUES
(1, 3, 'TVSN', '93.7', 'Welcome to TVSN - a shopping experience you will love!\r\nTVSN is Australia and New Zealand\'s favourite TV shopping network, showcasing all of your favourite products, prestigious brands and international trends.\r\nBrowse through our Fashion, Health, Beauty, Kitchen, Electronics, Homewares and Jewellery departments all from the comfort of your home, 24 hours a day, seven days a week. Our shows are an exhilarating ride through product features and benefits, brand stories, the product\'s journey to TVSN, international guests, customer testimonials, how-to’s, demonstrations, the odd blooper and of course lots of fun, laughter and sometimes even tears!', '93944_tvsn.png', '', 'http://sc-bb.1.fm:8017/;stream.l7u6', 1, 1, 1),
(2, 3, 'ABC News', '90.3', 'ABC began in 1943 as the NBC Blue Network, a radio network that was spun off from NBC, as ordered by the Federal Communications Commission (FCC) in 1942.[1] The reason for the order was to expand competition in radio broadcasting in the United States, specifically news and political broadcasting, and broaden the projected points of view. The radio market was dominated by only a few companies, such as NBC and CBS. NBC conducted the split voluntarily in case its appeal of the ruling was denied and it was forced to split its two networks into separate companies.', '24290_abcnews.png', '97041_abc_news.jpg', 'https://abc-iview-mediapackagestreams-2.akamaized.net/out/v1/6e1cc6d25ec0480ea099a5399d73bc4b/index.m3u8', 2, 1, 1),
(3, 6, 'Current Time TV', '88.6', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '63438_Nastoyashcheye vremya.png', '', 'http://sc-bb.1.fm:8017/;stream.l7u6', 1, 1, 0),
(4, 3, 'LN24', '94.3', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '99238_LN24.png', '87829_ln24.jpg', 'https://radio.mosaiquefm.net/mosalive', 2, 1, 1),
(5, 3, 'BX1', '88.7', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '35148_BX1.png', '', 'https://59959724487e3.streamlock.net/stream/live/playlist.m3u8', 1, 1, 0),
(6, 1, 'ALL Sports Brasil', '96.2', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '32826_SPORT.png', '', 'https://5cf4a2c2512a2.streamlock.net/dgrau/dgrau/chunklist.m3u8', 6, 1, 0),
(7, 3, 'Fala Brasil', '95.8', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '15390_falabrasil.png', '', 'https://radyo.imzamedya.web.tr:9981', 2, 1, 0),
(8, 4, 'Anime TV', '95.7', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '55281_anime.png', '38107_animetv.jpg', 'https://stmv1.srvif.com/animetv/animetv/playlist.m3u8', 3, 1, 1),
(9, 4, 'ISTV', '94.8', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. ', '31952_istv.png', '', 'https://cdn.jmvstream.com/w/LVW-9883/LVW9883_lFcfKysrHF/chunklist.m3u8', 2, 1, 0),
(10, 6, 'PMH Radio', '90.4', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '91553_pmh.jpeg', '', 'https://azura.shoutca.st/radio/8290/stream.mp3', 4, 1, 0),
(11, 3, 'ICI RDI', '70.9', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '34373_icirdi.png', '', 'http://sc-bb.1.fm:8017/;stream.l7u6', 1, 1, 0),
(12, 5, 'Pionera', '94.1', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '90010_pionera.png', '', 'http://192.99.100.217:8024/', 19, 1, 0),
(13, 6, 'Giallo', '84.7', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '32206_Giallo.png', '', 'http://sc-bb.1.fm:8017/;stream.l7u6', 24, 1, 0),
(14, 5, 'Radio Manele Mix', '85.6', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '46247_radiomanale.jpg', '', 'https://streaming.gometri.com/stream/8017/stream', 13, 1, 0),
(15, 6, 'Gold TV', '85.4', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '2516_goldtv.png', '90834_goldtv.jpg', 'https://streaming.softwarecreation.it/GoldTv/GoldTv/playlist.m3u8', 20, 1, 1),
(16, 2, 'Radio BTR90', '90.0', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '35323_btr90.png', '13852_test.jpg', 'https://stream.ets-sistemi.it/live.rete7/rete7/playlist.m3u8', 29, 1, 0),
(17, 4, 'Super Six', '95.4', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.', '38102_SuperSix.png', '74951_supersix.jpg', 'https://5db313b643fd8.streamlock.net/SUPERSIXLombardia/SUPERSIXLombardia/playlist.m3u8', 41, 1, 1),
(18, 6, 'IMC Broadcasting', '91.5', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged', '60105_imc.jpg', '14549_rtv.jpg', 'http://server.jvhost.net:8021/live', 34, 1, 1),
(19, 2, 'Mayotte One', '105.5', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged', '56337_mayotte.png', '', 'https://live.myradio.yt/mayotteone', 7, 1, 0),
(20, 2, 'Без москаля', '108.5', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged', '73817_7167.png', '', 'http://radio.vse.pp.ua:8002/live', 7, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_report`
--

CREATE TABLE `tbl_report` (
  `id` int(11) NOT NULL,
  `radio_id` int(11) NOT NULL,
  `radio_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `report` varchar(5000) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE `tbl_settings` (
  `id` int(11) NOT NULL,
  `app_name` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `api_key` varchar(255) NOT NULL,
  `app_logo` text NOT NULL,
  `fcm_server_key` varchar(355) NOT NULL,
  `ad_status` varchar(255) NOT NULL,
  `inter_clicks` int(11) NOT NULL,
  `ad_network` varchar(255) NOT NULL,
  `admob_small_banner` varchar(255) NOT NULL,
  `admob_medium_banner` varchar(255) NOT NULL,
  `admob_inter` varchar(255) NOT NULL,
  `admob_native` varchar(255) NOT NULL,
  `applovin_small_banner` varchar(255) NOT NULL,
  `applovin_medium_banner` varchar(255) NOT NULL,
  `applovin_inter` varchar(255) NOT NULL,
  `applovin_native` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tbl_settings`
--

INSERT INTO `tbl_settings` (`id`, `app_name`, `name`, `username`, `password`, `api_key`, `app_logo`, `fcm_server_key`, `ad_status`, `inter_clicks`, `ad_network`, `admob_small_banner`, `admob_medium_banner`, `admob_inter`, `admob_native`, `applovin_small_banner`, `applovin_medium_banner`, `applovin_inter`, `applovin_native`) VALUES
(1, 'AM Radio', 'Admin', 'admin', 'admin', 'andromob', '99879_app_icon.png', 'AAAAGhgs2s8:APA91bHPFdq9GlcUWU5Eq0EED28zd-4jEjouLApnC2l_WXk_0vtpCKzEjoa80go7CPTRhrysOsnr23NLFOD0zJPoakpNr9LwEm69tTfZFLPY13cRqdkF244W9qcN6CzR6xDIvWAFcB-M', 'on', 3, 'admob', 'ca-app-pub-3940256099942544/6300978111', 'ca-app-pub-3940256099942544/6300978111', 'ca-app-pub-3940256099942544/1033173712', 'ca-app-pub-3940256099942544/2247696110', 'fce47dc6109dead5', '41210be1cabdd074', '51a714683030352f', '448007b9f40ab763');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `id` int(11) NOT NULL,
  `token` text COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for table `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD PRIMARY KEY (`cat_id`);

--
-- Indexes for table `tbl_channels`
--
ALTER TABLE `tbl_channels`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_report`
--
ALTER TABLE `tbl_report`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_settings`
--
ALTER TABLE `tbl_settings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_category`
--
ALTER TABLE `tbl_category`
  MODIFY `cat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_channels`
--
ALTER TABLE `tbl_channels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `tbl_report`
--
ALTER TABLE `tbl_report`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;

--
-- AUTO_INCREMENT for table `tbl_settings`
--
ALTER TABLE `tbl_settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
