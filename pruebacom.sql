-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-02-2019 a las 06:16:17
-- Versión del servidor: 10.1.38-MariaDB
-- Versión de PHP: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pruebacom`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `participante`
--

CREATE TABLE `participante` (
  `tipo_doc` varchar(3) COLLATE utf8mb4_spanish_ci NOT NULL,
  `numdoc_part` varchar(15) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL,
  `nombre` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `apellidos` varchar(50) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha_nac` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `participante`
--

INSERT INTO `participante` (`tipo_doc`, `numdoc_part`, `nombre`, `apellidos`, `fecha_nac`) VALUES
('cc', '1090382005', 'Erickson Jesus', 'Tirado Goyeneche', '2019-02-25'),
('cc', '78965727', 'Katy', 'Duran', '2019-02-25'),
('cc', '852369', 'Lina Fernanda', 'Lopez Mojica', '2019-02-25');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ppte_premio`
--

CREATE TABLE `ppte_premio` (
  `numdoc_part` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `cod_premio` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `num_sorteo` int(100) NOT NULL,
  `fecha_sorteo` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `ppte_premio`
--

INSERT INTO `ppte_premio` (`numdoc_part`, `cod_premio`, `num_sorteo`, `fecha_sorteo`) VALUES
('78965727', '1', 3, '2019-02-25'),
('78965727', '2', 5, '2019-02-25'),
('852369', '1', 8, '2019-02-25'),
('852369', '1', 10, '2019-02-25'),
('852369', '1', 14, '2019-02-25'),
('78965727', '2', 15, '2019-02-25'),
('78965727', '1', 18, '2019-02-25'),
('78965727', '2', 19, '2019-02-25'),
('78965727', '1', 20, '2019-02-25'),
('852369', '2', 27, '2019-02-25'),
('852369', '2', 28, '2019-02-25'),
('78965727', '1', 29, '2019-02-25'),
('78965727', '2', 30, '2019-02-25'),
('78965727', '1', 31, '2019-02-25'),
('78965727', '1', 32, '2019-02-25'),
('78965727', '1', 33, '2019-02-25');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `premio`
--

CREATE TABLE `premio` (
  `cod_premio` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `descrip` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `cant` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `premio`
--

INSERT INTO `premio` (`cod_premio`, `descrip`, `cant`) VALUES
('1', 'Smart TV', 32),
('2', 'Equipo', 5),
('3', 'bicicleta', 15);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `participante`
--
ALTER TABLE `participante`
  ADD PRIMARY KEY (`numdoc_part`);

--
-- Indices de la tabla `ppte_premio`
--
ALTER TABLE `ppte_premio`
  ADD PRIMARY KEY (`num_sorteo`),
  ADD KEY `cod_premio` (`cod_premio`),
  ADD KEY `numdoc_part` (`numdoc_part`);

--
-- Indices de la tabla `premio`
--
ALTER TABLE `premio`
  ADD PRIMARY KEY (`cod_premio`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ppte_premio`
--
ALTER TABLE `ppte_premio`
  ADD CONSTRAINT `ppte_premio_ibfk_1` FOREIGN KEY (`cod_premio`) REFERENCES `premio` (`cod_premio`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `ppte_premio_ibfk_2` FOREIGN KEY (`numdoc_part`) REFERENCES `participante` (`numdoc_part`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
