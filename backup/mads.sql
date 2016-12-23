-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 20-12-2016 a las 23:32:47
-- Versión del servidor: 5.7.16-0ubuntu0.16.04.1
-- Versión de PHP: 7.0.8-0ubuntu0.16.04.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mads`
--

--
-- Volcado de datos para la tabla `EMP_PROJ`
--

INSERT INTO `EMP_PROJ` (`EMP_ID`, `PROJ_ID`) VALUES
(4, 1);

--
-- Volcado de datos para la tabla `Estado`
--

INSERT INTO `Estado` (`id`, `nombre`, `proyectoId`) VALUES
(1, 'En marcha', 1),
(2, 'Finalizado', 1),
(3, 'Pull request', 1),
(4, 'Descartado', 1);

--
-- Volcado de datos para la tabla `Mensaje`
--

INSERT INTO `Mensaje` (`id`, `body`, `borrado`, `fechaEnvio`, `leido`, `subject`, `usuarioFrom`, `usuarioTo`) VALUES
(1, 'Me debes un almuerzo.', b'0', '20-12-2016 11:21:19', b'0', 'Almuerzo', 'magicadry', 'jorgest');

--
-- Volcado de datos para la tabla `Notificacion`
--

INSERT INTO `Notificacion` (`id`, `descripcion`, `fecha`, `leido`, `tipo`, `user`) VALUES
(1, 'Nuevo proyecto', '20-12-2016 09:34:05', b'0', 'Proyecto', 'allUsers'),
(2, 'Nuevo proyecto', '20-12-2016 09:34:38', b'0', 'Proyecto', 'allUsers'),
(3, 'Nuevo proyecto', '20-12-2016 09:34:59', b'0', 'Proyecto', 'allUsers'),
(4, 'Nuevo proyecto', '20-12-2016 09:35:17', b'0', 'Proyecto', 'allUsers'),
(5, 'Nuevo proyecto', '20-12-2016 09:35:28', b'0', 'Proyecto', 'allUsers'),
(6, 'Modificación de usuario: 5', '20-12-2016 09:42:51', b'0', 'Usuario', 'rubanana'),
(7, 'Modificación de usuario: 5', '20-12-2016 09:43:35', b'0', 'Usuario', 'rubanana'),
(8, 'Modificación de usuario: 5', '20-12-2016 09:44:00', b'0', 'Usuario', 'rubanana'),
(9, 'Modificación de usuario: 5', '20-12-2016 09:44:12', b'0', 'Usuario', 'rubanana'),
(10, 'Nuevo proyecto', '20-12-2016 09:45:32', b'0', 'Proyecto', 'allUsers'),
(11, 'Modificación de usuario: 1', '20-12-2016 09:56:25', b'1', 'Usuario', 'magicadry'),
(12, 'Modificación de usuario: 1', '20-12-2016 10:56:55', b'1', 'Usuario', 'magicadry'),
(13, 'Modificación de usuario: 1', '20-12-2016 10:57:29', b'0', 'Usuario', 'magicadry'),
(14, 'Nueva tarea', '20-12-2016 11:07:36', b'0', 'Tarea', 'magicadry'),
(15, 'Nueva tarea', '20-12-2016 11:07:50', b'0', 'Tarea', 'magicadry'),
(16, 'Tarea 1 asignada al proyecto: 1', '20-12-2016 11:07:56', b'0', 'Tarea', 'magicadry'),
(17, 'Modificación de tarea: 1', '20-12-2016 11:08:00', b'0', 'Tarea', 'magicadry'),
(18, 'Modificación de tarea: 2', '20-12-2016 11:09:25', b'0', 'Tarea', 'magicadry'),
(19, 'Modificación de tarea: 2', '20-12-2016 11:09:26', b'0', 'Tarea', 'magicadry'),
(20, 'Modificación de tarea: 1', '20-12-2016 11:09:29', b'0', 'Tarea', 'magicadry'),
(21, 'Nueva tarea', '20-12-2016 11:09:41', b'0', 'Tarea', 'magicadry'),
(22, 'Tarea 3 asignada al proyecto: 5', '20-12-2016 11:09:48', b'0', 'Tarea', 'magicadry'),
(23, 'Tarea 3 asignada al proyecto: 4', '20-12-2016 11:10:12', b'0', 'Tarea', 'magicadry'),
(24, 'Modificación de tarea: 2', '20-12-2016 11:10:48', b'0', 'Tarea', 'magicadry'),
(25, 'Modificación de tarea: 3', '20-12-2016 11:10:52', b'0', 'Tarea', 'magicadry'),
(26, 'Modificación de tarea: 3', '20-12-2016 11:10:54', b'0', 'Tarea', 'magicadry'),
(27, 'Tarea 2 asignada al proyecto: 3', '20-12-2016 11:10:57', b'0', 'Tarea', 'magicadry'),
(28, 'Tarea 3 asignada al proyecto: 3', '20-12-2016 11:11:04', b'0', 'Tarea', 'magicadry'),
(29, 'Tarea 3 asignada al proyecto: 1', '20-12-2016 11:11:09', b'0', 'Tarea', 'magicadry'),
(30, 'Tarea 2 asignada al proyecto: 1', '20-12-2016 11:11:12', b'0', 'Tarea', 'magicadry'),
(31, 'Tarea 3 asignada al proyecto: 3', '20-12-2016 11:14:14', b'0', 'Tarea', 'magicadry'),
(32, 'Modificación de tarea: 3', '20-12-2016 11:14:18', b'0', 'Tarea', 'magicadry'),
(33, 'Tarea 3 eliminada', '20-12-2016 11:14:23', b'0', 'Tarea', 'magicadry'),
(34, 'Nueva tarea', '20-12-2016 11:15:50', b'0', 'Tarea', 'magicadry'),
(35, 'Modificación de tarea: 4', '20-12-2016 11:15:54', b'0', 'Tarea', 'magicadry'),
(36, 'Tarea 4 eliminada', '20-12-2016 11:16:02', b'0', 'Tarea', 'magicadry'),
(37, 'Nuevo mensaje', '20-12-2016 11:21:19', b'0', 'Mensaje', 'jorgest'),
(38, 'Modificación de usuario: 2', '20-12-2016 11:21:40', b'0', 'Usuario', 'jorgest');

--
-- Volcado de datos para la tabla `Proyecto`
--

INSERT INTO `Proyecto` (`id`, `nombre`, `propietarioId`) VALUES
(1, 'Ecommerce Mis botas', 1),
(2, 'Safari "El repo"', 2),
(3, 'Proyecto youtuber', 3),
(4, 'Gestión de ftp', 1),
(5, 'Web 5.0', 1),
(6, 'Perros y gatos', 5);

--
-- Volcado de datos para la tabla `Tarea`
--

INSERT INTO `Tarea` (`id`, `descripcion`, `duracion`, `tamano`, `proyectoId`, `usuarioId`, `archivada`, `color`, `estado`, `fecha`) VALUES
(1, 'Mi tarea 1', 0, 'pequeña', 1, 1, b'0', '#FE2EC8', 'Finalizado', '2012-12-12'),
(2, 'Mi tarea 2', 1, 'mediana', NULL, 1, b'0', '#FFFFFF', '', '2012-12-13');

--
-- Volcado de datos para la tabla `Usuario`
--

INSERT INTO `Usuario` (`id`, `apellidos`, `eMail`, `fechaNacimiento`, `imagen`, `login`, `nombre`, `password`, `colordash`) VALUES
(1, 'Gosálvez Maciá', 'adrian@ua.es', '2001-01-01', '/assets/images/1-magicadry.jpg', 'magicadry', 'Adrián', NULL, '#fff'),
(2, 'Segovia Tormo', 'jorge@ua.es', '2003-03-02', NULL, 'jorgest', 'Jorge', '123123123', '#f4f3a6'),
(3, 'Sapiña Mora', 'sapi@ua.es', '2000-05-01', NULL, 'sapih', 'Alberto', NULL, 'white'),
(4, 'Molpeceres Gómez', 'molpe@ua.es', '2001-05-05', NULL, 'molpius', 'Javier', NULL, 'white'),
(5, 'López Moya', 'ruben@ya.es', '2000-02-18', '/assets/images/5-rubanana.jpg', 'rubanana', 'Rubén', '123123123', 'white');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
