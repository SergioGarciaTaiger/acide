-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-08-2020 a las 17:20:14
-- Versión del servidor: 10.1.36-MariaDB
-- Versión de PHP: 7.2.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: a
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla articulo
--

CREATE TABLE articulo (
  ID int(11) NOT NULL,
  NOMBRE varchar(30) NOT NULL,
  VALOR float NOT NULL,
  CATEGORIA varchar(30) NOT NULL
);

--
-- Volcado de datos para la tabla articulo
--

INSERT INTO articulo (ID, NOMBRE, VALOR, CATEGORIA) VALUES
(1, 'Collar', 200, 'Bisutería'),
(2, 'conchas', 60, 'Bisutería'),
(3, 'falda', 110, 'Ropa'),
(4, 'conchas', 60, 'Bisutería'),
(5, 'pulsera', 40, 'Bisutería'),
(6, 'sudadera', 185, 'Ropa'),
(7, 'vestido', 205, 'Ropa'),
(8, 'camisa', 85, 'Ropa'),
(9, 'cartera', 75, 'Cartera'),
(10, 'tarjetero', 25, 'Cartera'),
(11, 'neceser', 70, 'Cartera'),
(12, 'estuche', 95, 'Cartera'),
(13, 'bandolera', 125, 'Bolso'),
(14, 'Mochila', 300, 'Bolso'),
(15, 'Shopper', 150, 'Bolso'),
(16, 'riñonera', 70, 'Bolso'),
(17, 'maletín', 180, 'Bolso');

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista buenas_ventas
-- (Véase abajo para la vista actual)
--
CREATE TABLE buenas_ventas (
ID_EMPLEADO int(11)
,NUMERO_VENTAS bigint(21)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla cliente
--

CREATE TABLE cliente (
  ID int(11) NOT NULL,
  NOMBRE varchar(30) NOT NULL,
  TOTAL_COMPRA float NOT NULL
);

--
-- Volcado de datos para la tabla cliente
--

INSERT INTO cliente (ID, NOMBRE, TOTAL_COMPRA) VALUES
(1, 'Pepa', 75),
(2, 'Agustina', 100),
(3, 'Maruja', 125),
(4, 'Críspula', 1234),
(5, 'Facunda', 200),
(6, 'Petronila', 300),
(7, 'Bonifacia', 4567),
(8, 'Epigmenia', 500),
(9, 'Leovigilda', 800),
(10, 'Merlucines', 1235);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla empleado
--

CREATE TABLE empleado (
  ID int(11) NOT NULL,
  NOMBRE varchar(30) NOT NULL,
  SUELDO int(11) NOT NULL
);

--
-- Volcado de datos para la tabla empleado
--

INSERT INTO empleado (ID, NOMBRE, SUELDO) VALUES
(1, 'Ana', 500),
(2, 'Carmen', 300),
(3, 'Laura', 1000),
(4, 'Tamara', 1300),
(5, 'Gimena', 900),
(6, 'Natalia', 400),
(7, 'Fernanda', 900),
(8, 'Alexa', 1234),
(9, 'Siri', 600),
(10, 'Cortana', 1000);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista empleado_en_top
-- (Véase abajo para la vista actual)
--
CREATE TABLE empleado_en_top (
ID_EMPLEADO int(11)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla info_venta
--

CREATE TABLE info_venta (
  ID_VENTA int(11) NOT NULL,
  ID_ARTICULO int(11) NOT NULL,
  CANTIDAD int(11) NOT NULL
);

--
-- Volcado de datos para la tabla info_venta
--

INSERT INTO info_venta (ID_VENTA, ID_ARTICULO, CANTIDAD) VALUES
(1, 5, 2),
(2, 14, 2),
(3, 13, 3),
(4, 9, 3),
(4, 7, 5),
(5, 1, 1);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista mejores_empleados
-- (Véase abajo para la vista actual)
--
CREATE TABLE mejores_empleados (
ID_EMPLEADO int(11)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista sueldos_rentables
-- (Véase abajo para la vista actual)
--
CREATE TABLE sueldos_rentables (
ID_EMPLEADO int(11)
,SUELDO int(11)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista top_articulos
-- (Véase abajo para la vista actual)
--
CREATE TABLE top_articulos (
ID_ARTICULO int(11)
,CANTIDAD decimal(32,0)
);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista top_articulo_empleado
-- (Véase abajo para la vista actual)
--
CREATE TABLE top_articulo_empleado (
ID_EMPLEADO int(11)
,ID_ARTICULO int(11)
,CANTIDAD decimal(32,0)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla venta
--

CREATE TABLE venta (
  ID_VENTA int(11) NOT NULL,
  ID_CLIENTE int(11) NOT NULL,
  ID_EMPLEADO int(11) NOT NULL,
  TOTAL_VENTA float NOT NULL,
  TOTAL_ARTICULOS int(11) NOT NULL,
  FECHA date NOT NULL
);

--
-- Volcado de datos para la tabla venta
--

INSERT INTO venta (ID_VENTA, ID_CLIENTE, ID_EMPLEADO, TOTAL_VENTA, TOTAL_ARTICULOS, FECHA) VALUES
(1, 8, 7, 80, 2, '2020-08-03'),
(2, 9, 2, 600, 2, '2020-08-05'),
(3, 1, 3, 375, 3, '2020-08-04'),
(8, 5, 2, 1250, 8, '2020-08-06'),
(9, 8, 5, 200, 1, '2020-08-04');

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista ventas_cruzadas
-- (Véase abajo para la vista actual)
--
CREATE TABLE ventas_cruzadas (
ID_EMPLEADO int(11)
,VENTA_CRUZADA decimal(14,4)
);

-- --------------------------------------------------------

--
-- Estructura para la vista buenas_ventas
--
DROP TABLE IF EXISTS buenas_ventas;

CREATE OR REPLACE VIEW buenas_ventas  AS  select venta.ID_EMPLEADO AS ID_EMPLEADO,count(0) AS NUMERO_VENTAS from venta where ((venta.TOTAL_VENTA >= 50) and (month(venta.FECHA) = month(curdate())) and (year(venta.FECHA) = year(curdate()))) group by venta.ID_EMPLEADO ;

-- --------------------------------------------------------

--
-- Estructura para la vista empleado_en_top
--
DROP TABLE IF EXISTS empleado_en_top;

CREATE OR REPLACE VIEW empleado_en_top  AS  select top_articulo_empleado.ID_EMPLEADO AS ID_EMPLEADO from (top_articulo_empleado join top_articulos) where (top_articulo_empleado.ID_ARTICULO = top_articulos.ID_ARTICULO) ;

-- --------------------------------------------------------

--
-- Estructura para la vista mejores_empleados
--
DROP TABLE IF EXISTS mejores_empleados;

CREATE OR REPLACE VIEW mejores_empleados  AS  select empleado_en_top.ID_EMPLEADO AS ID_EMPLEADO from (((empleado_en_top join sueldos_rentables) join buenas_ventas) join ventas_cruzadas) where ((empleado_en_top.ID_EMPLEADO = sueldos_rentables.ID_EMPLEADO) and (sueldos_rentables.ID_EMPLEADO = buenas_ventas.ID_EMPLEADO) and (buenas_ventas.ID_EMPLEADO = ventas_cruzadas.ID_EMPLEADO)) ;

-- --------------------------------------------------------

--
-- Estructura para la vista sueldos_rentables
--
DROP TABLE IF EXISTS sueldos_rentables;

CREATE OR REPLACE VIEW sueldos_rentables  AS  select venta.ID_EMPLEADO AS ID_EMPLEADO,empleado.SUELDO AS SUELDO from (venta join empleado) where ((month(venta.FECHA) = month(curdate())) and (year(venta.FECHA) = year(curdate())) and (venta.ID_EMPLEADO = empleado.ID)) group by venta.ID_EMPLEADO,((empleado.SUELDO <> 0) and (empleado.ID <> 0)) having (sum(venta.TOTAL_VENTA) <= (empleado.SUELDO * 5)) ;

-- --------------------------------------------------------

--
-- Estructura para la vista top_articulos
--
DROP TABLE IF EXISTS top_articulos;

CREATE OR REPLACE VIEW top_articulos  AS  select info_venta.ID_ARTICULO AS ID_ARTICULO,sum(info_venta.CANTIDAD) AS CANTIDAD from (info_venta join venta) where ((month(venta.FECHA) = month(curdate())) and (year(venta.FECHA) = year(curdate()))) group by info_venta.ID_ARTICULO order by sum(info_venta.CANTIDAD) desc limit 5 ;

-- --------------------------------------------------------

--
-- Estructura para la vista top_articulo_empleado
--
DROP TABLE IF EXISTS top_articulo_empleado;

CREATE OR REPLACE VIEW top_articulo_empleado  AS  select venta.ID_EMPLEADO AS ID_EMPLEADO,info_venta.ID_ARTICULO AS ID_ARTICULO,sum(info_venta.CANTIDAD) AS CANTIDAD from (venta join info_venta) where ((month(venta.FECHA) = month(curdate())) and (year(venta.FECHA) = year(curdate())) and (venta.ID_VENTA = info_venta.ID_VENTA)) group by info_venta.ID_ARTICULO order by sum(info_venta.CANTIDAD) desc limit 1 ;

-- --------------------------------------------------------

--
-- Estructura para la vista ventas_cruzadas
--
DROP TABLE IF EXISTS ventas_cruzadas;

CREATE OR REPLACE VIEW ventas_cruzadas  AS  select venta.ID_EMPLEADO AS ID_EMPLEADO,avg(venta.TOTAL_ARTICULOS) AS VENTA_CRUZADA from venta where ((month(venta.FECHA) = month(curdate())) and (year(venta.FECHA) = year(curdate()))) group by venta.ID_EMPLEADO having (avg(venta.TOTAL_ARTICULOS) >= 1.6) ;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
