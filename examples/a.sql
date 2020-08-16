
/multiline on
/abolish
/show_compilations on

--
-- Base de datos: `a`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo`
--

DROP TABLE IF EXISTS articulo, venta, empleado, info_venta, cliente;

CREATE TABLE `articulo` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(30) NOT NULL,
  `VALOR` int NOT NULL,
  `CATEGORIA` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`)
);

--
-- Volcado de datos para la tabla `articulo`
--

INSERT INTO `articulo` (`ID`, `NOMBRE`, `VALOR`, `CATEGORIA`) VALUES
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
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(30) NOT NULL,
  `SUELDO` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
);

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`ID`, `NOMBRE`, `SUELDO`) VALUES
(1, 'Ana', 500),
(2, 'Laura', 300),
(3, 'Carmen', 300),
(4, 'Tamara', 1300),
(5, 'Gimena', 900),
(6, 'Natalia', 400),
(7, 'Fernanda', 900),
(8, 'Alexa', 1234),
(9, 'Siri', 600),
(10, 'Cortana', 1000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--
CREATE TABLE `venta` (
  `ID_VENTA` int(11) NOT NULL,
  `ID_CLIENTE` int(11) NOT NULL,
  `ID_EMPLEADO` int(11) NOT NULL,
  `TOTAL_VENTA` int(11) NOT NULL,
  `TOTAL_ARTICULOS` int(11) NOT NULL
);

--
-- Volcado de datos para la tabla `venta`
--

INSERT INTO `venta` (`ID_VENTA`, `ID_CLIENTE`, `ID_EMPLEADO`, `TOTAL_VENTA`, `TOTAL_ARTICULOS`) VALUES
(1, 8, 7, 80, 2),
(2, 9, 2, 600, 2),
(3, 1, 3, 375, 3),
(8, 5, 2, 1250, 8),
(9, 8, 5, 200, 1);


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `info_venta`
--

CREATE TABLE `info_venta` (
  `ID_VENTA` int(11) NOT NULL,
  `ID_ARTICULO` int(11) NOT NULL,
  `CANTIDAD` int(11) NOT NULL
);

--
-- Volcado de datos para la tabla `info_venta`
--

INSERT INTO `info_venta` (`ID_VENTA`, `ID_ARTICULO`, `CANTIDAD`) VALUES
(1, 5, 2),
(2, 14, 2),
(3, 13, 3),
(4, 9, 3),
(4, 7, 5),
(5, 1, 1);




-- --------------------------------------------------------

--
-- Estructura para la vista `ventas_cruzadas`
--

CREATE OR REPLACE VIEW ventas_cruzadas(ID_EMPLEADO, VENTA_CRUZADA)  AS  
select `venta`.`ID_EMPLEADO` AS `ID_EMPLEADO`,avg(`venta`.`TOTAL_ARTICULOS`) AS `VENTA_CRUZADA` 
from `venta` 
group by `venta`.`ID_EMPLEADO` 
having (avg(`venta`.`TOTAL_ARTICULOS`) >= 1.6) ;

--
-- Estructura para la vista `buenas_ventas`
--

CREATE OR REPLACE VIEW buenas_ventas(ID_EMPLEADO, NUMERO_VENTAS)  AS
  select `venta`.`ID_EMPLEADO` AS `ID_EMPLEADO`,count(0) AS `NUMERO_VENTAS`
  from `venta` 
  where (`venta`.`TOTAL_VENTA` >= 50) 
  group by `venta`.`ID_EMPLEADO` ;

--
-- Estructura para la vista `top_articulos`
--


CREATE OR REPLACE VIEW mejores_articulos(ID_ARTICULO, CANTIDAD)  AS  
  select `info_venta`.`ID_ARTICULO` AS `ID_ARTICULO`,sum(`info_venta`.`CANTIDAD`) AS `CANTIDAD` 
  from `info_venta` 
  group by `info_venta`.`ID_ARTICULO` 
  order by sum(`info_venta`.`CANTIDAD`) desc;
  --LIMIT 5;

--
-- Estructura para la vista `top_5`
--

CREATE OR REPLACE VIEW top_articulos(ID_ARTICULO) AS
  select mejores_articulos.ID_ARTICULO
  from mejores_articulos
  ORDER BY mejores_articulos.CANTIDAD DESC
  FETCH FIRST 5 ROWS ONLY;
--
-- Estructura para la vista `top_articulo_empleado`
--

CREATE OR REPLACE VIEW top_articulos_empleado(ID_EMPLEADO, ID_ARTICULO, CANTIDAD)  AS  
  select `venta`.`ID_EMPLEADO` AS `ID_EMPLEADO`,`info_venta`.`ID_ARTICULO` AS `ID_ARTICULO`,sum(`info_venta`.`CANTIDAD`) AS `CANTIDAD` 
  from (`venta` join `info_venta`) 
  where  (`venta`.`ID_VENTA` = `info_venta`.`ID_VENTA`) 
  group by `ID_ARTICULO`, `ID_EMPLEADO`
  order by sum(`info_venta`.`CANTIDAD`) desc;

--
-- Estructura para la vista `ventas_empleado`
--

CREATE OR REPLACE VIEW ventas_empleado(ID_EMPLEADO, TOTAL_VENTA) AS
  select venta.ID_EMPLEADO AS ID_EMPLEADO, sum(venta.TOTAL_VENTA) as TOTAL_VENTA
  from venta
  group by ID_EMPLEADO;

  --
-- Estructura para la vista `sueldos_rentables`
--

CREATE OR REPLACE VIEW sueldos_rentables(ID_EMPLEADO, SUELDO)  AS  
  select empleado.ID AS ID, empleado.SUELDO AS SUELDO
  from (empleado join ventas_empleado)
  where empleado.ID = ventas_empleado.ID_EMPLEADO and (ventas_empleado.TOTAL_VENTA >= (empleado.SUELDO * 5));

--
-- Estructura para la vista `empleado_en_top`
--

CREATE OR REPLACE VIEW empleado_en_top(ID_EMPLEADO)  AS  
  select `top_articulos_empleado`.`ID_EMPLEADO` AS `ID_EMPLEADO` 
  from `top_articulos_empleado`,`top_articulos` 
  where (`top_articulos_empleado`.`ID_ARTICULO` = `top_articulos`.`ID_ARTICULO`) ;

--
-- Estructura para la vista `mejores_empleados`
--
CREATE OR REPLACE VIEW mejores_empleados(ID_EMPLEADO)  AS  
  select `empleado_en_top`.`ID_EMPLEADO` AS `ID_EMPLEADO` 
  from (((`empleado_en_top` join `sueldos_rentables`) join `buenas_ventas`) join `ventas_cruzadas`) 
  where ((`empleado_en_top`.`ID_EMPLEADO` = `sueldos_rentables`.`ID_EMPLEADO`) 
  and (`sueldos_rentables`.`ID_EMPLEADO` = `buenas_ventas`.`ID_EMPLEADO`) 
  and (`buenas_ventas`.`ID_EMPLEADO` = `ventas_cruzadas`.`ID_EMPLEADO`)) ;

/development off
/dbschema
/development on
/dbschema
