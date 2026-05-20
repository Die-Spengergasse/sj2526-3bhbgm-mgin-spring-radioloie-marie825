-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 23. Apr 2026 um 15:34
-- Server-Version: 10.4.32-MariaDB
-- PHP-Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `radiologie_db`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `d_devices`
--

CREATE TABLE `d_devices` (
  `id` int(11) NOT NULL,
  `raumnr` int(11) NOT NULL,
  `art` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `d_devices`
--

INSERT INTO `d_devices` (`id`, `raumnr`, `art`) VALUES
(6, 1325, 'mrt'),
(7, 123, 'Infusionpumpe');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `p_patient`
--

CREATE TABLE `p_patient` (
  `birth` date DEFAULT NULL,
  `geschlecht` varchar(1) NOT NULL,
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `svnr` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `p_patient`
--

INSERT INTO `p_patient` (`birth`, `geschlecht`, `id`, `name`, `surname`, `svnr`) VALUES
('2009-07-05', 'm', 2, 'moritz', 'lembäcker', '1234050709');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `r_reservierung`
--

CREATE TABLE `r_reservierung` (
  `device_id` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL,
  `patient_id` int(11) DEFAULT NULL,
  `reservation_date` date DEFAULT NULL,
  `body_part` varchar(255) DEFAULT NULL,
  `commentar` varchar(255) DEFAULT NULL,
  `reservation_time` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `r_reservierung`
--

INSERT INTO `r_reservierung` (`device_id`, `id`, `patient_id`, `reservation_date`, `body_part`, `commentar`, `reservation_time`) VALUES
(6, 9, 2, '2009-12-12', 'abdomen', 'hallo', '12:30'),
(7, 10, 2, '2027-12-12', 'extremities', '', '13:00');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `d_devices`
--
ALTER TABLE `d_devices`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `p_patient`
--
ALTER TABLE `p_patient`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `r_reservierung`
--
ALTER TABLE `r_reservierung`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj98fmadiin4plshmwl2vbyb9h` (`device_id`),
  ADD KEY `FKs2v25cio9fo9bs2127pkdu7fc` (`patient_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `d_devices`
--
ALTER TABLE `d_devices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT für Tabelle `p_patient`
--
ALTER TABLE `p_patient`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT für Tabelle `r_reservierung`
--
ALTER TABLE `r_reservierung`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `r_reservierung`
--
ALTER TABLE `r_reservierung`
  ADD CONSTRAINT `FKj98fmadiin4plshmwl2vbyb9h` FOREIGN KEY (`device_id`) REFERENCES `d_devices` (`id`),
  ADD CONSTRAINT `FKs2v25cio9fo9bs2127pkdu7fc` FOREIGN KEY (`patient_id`) REFERENCES `p_patient` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
