-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-08-2017 a las 22:11:12
-- Versión del servidor: 5.6.16
-- Versión de PHP: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `sic`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_idtransaccion`(inout vid_transaccion int)
BEGIN
    set vid_transaccion=(select (max(id_transaccion)) from transacciones)+1;
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_iniciarperiodo`()
BEGIN
     declare capital double(10,2) default 0;
     DECLARE capitalHab DOUBLE(10,2) DEFAULT 0;
     set capital=(SELECT SUM(debe)-SUM(haber) FROM cuenta WHERE (estadofinanciero = 1 OR estadofinanciero = 2 OR estadofinanciero = 4) AND (debe != 0 OR haber != 0));
     INSERT INTO periodocontable(FECHAINICIO,FECHAFINAL) VALUES(CURDATE(),NULL);
     UPDATE cuenta SET DEBE=0.00,HABER=0.00, CANTIDAD=0, PRECIO=0.00 where  ESTADOFINANCIERO=1 or ESTADOFINANCIERO=2;
     delete from transacciones;
      if(capital>=0) then
      begin
      UPDATE cuenta SET DEBE=capital,HABER=0.00, CANTIDAD=0, PRECIO=0.00 WHERE(CODIGO=310101);
     end;
     end if;
 
     IF(capital<0) THEN
      BEGIN
      UPDATE cuenta SET DEBE=0.00,HABER=capital*-1, CANTIDAD=0, PRECIO=0.00 WHERE(CODIGO=310101);
     END;
     END IF;
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_cuentas`(
    vCodigo int,
    vCuenta varchar(50),
    vTipo VARCHAR(50),
    vSDeudor Double(10,2),
    vSAcreedor DOUBLE(10,2),
    vCantidad INT
    )
BEGIN
    DECLARE sumaDeudor DOUBLE(10,2) default 0 ;
    DECLARE sumaAcreedor DOUBLE(10,2) DEFAULT 0 ;
    DECLARE cantidadT int DEFAULT 0 ;
    declare vPrecio DOUBLE(10,2) DEFAULT 0 ;
    SET cantidadT=(SELECT SUM(CANTIDAD) FROM cuenta WHERE(CODIGO=vCodigo))+vCantidad;
    set sumaDeudor=(select sum(DEBE) from cuenta where(CODIGO=vCodigo))+vSDeudor;
    SET sumaAcreedor=(SELECT SUM(HABER) FROM cuenta WHERE(CODIGO=vCodigo))+vSAcreedor;
    set vPrecio=(sumaDeudor-sumaAcreedor)/cantidadT;
    if((select count(1) from cuenta where(CODIGO=vCodigo))>0) then
    update cuenta set DEBE=sumaDeudor,HABER=sumaAcreedor, CANTIDAD=cantidadT, PRECIO=vPrecio where(CODIGO=vCodigo);
    select "Ingresado con exito!!";
    end if;
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertar_orden`(
    vCodigo INT,
    vCuenta VARCHAR(50),
    vTipo VARCHAR(50),
    vSDeudor DOUBLE(10,2),
    vSAcreedor DOUBLE(10,2),
    vCantidad INT
    )
BEGIN
    DECLARE sumaDeudor DOUBLE(10,2) DEFAULT 0 ;
    DECLARE sumaAcreedor DOUBLE(10,2) DEFAULT 0 ;
    DECLARE cantidadT INT DEFAULT 0 ;
    #DECLARE vPrecio DOUBLE(10,2) DEFAULT 0 ;
    SET cantidadT=(SELECT SUM(CANTIDAD) FROM cuenta WHERE(CODIGO=vCodigo))+vCantidad;
    SET sumaDeudor=(SELECT SUM(DEBE) FROM cuenta WHERE(CODIGO=vCodigo))+vSDeudor;
    SET sumaAcreedor=(SELECT SUM(HABER) FROM cuenta WHERE(CODIGO=vCodigo))+vSAcreedor;
    #SET vPrecio=(sumaDeudor-sumaAcreedor)/cantidadT;
    IF((SELECT COUNT(1) FROM cuenta WHERE(CODIGO=vCodigo))>0) THEN
    UPDATE cuenta SET DEBE=sumaDeudor,HABER=sumaAcreedor WHERE(CODIGO=vCodigo);
    SELECT "Ingresado con exito!!";
    END IF;
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_inventarios`()
BEGIN
      select CODIGO, PRECIO FROM cuenta WHERE CODIGO>=110401 && CODIGO<=110408;
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_nuevaCuenta`( IN  `vCodigo` INT, IN  `vCueCod` INT, IN  `vNombre` VARCHAR( 50 ) , IN  `vEstado` INT, IN  `vOperacion` INT )
BEGIN INSERT INTO cuenta( CODIGO, cue_codigo, NOMBRE, ESTADOFINANCIERO, OPERACIONENESTADO, precio, cantidad ) 
VALUES (
vCodigo, vCueCod, vNombre, vEstado, vOperacion, 0, 0
);
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_obtenerCodigo`( inout vCodigo int)
BEGIN
      DECLARE auxCodigo INT;
      if((SELECT MAX(CODIGO) FROM cuenta WHERE cue_codigo=vCodigo) is null) then
      set auxCodigo=(vCodigo*100)+1;
      set vCodigo=auxCodigo;
      else
      set auxCodigo=(select max(CODIGO) from cuenta where cue_codigo=vCodigo);
      set vCodigo=auxCodigo+1;
      end if;
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_orden`(inout orden double(10,2))
BEGIN
    set orden=(SELECT debe FROM cuenta WHERE codigo='110701');
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_proceso_periodo`(inout periodoProceso int, INOUT numPeriodo int)
BEGIN
     set numPeriodo=(select MAx(IDPERIODOCONTABLE)FROM periodocontable);
 
     if((select count(1) from periodocontable)>0)then
 
         if((select FECHAFINAL FROM periodocontable where (IDPERIODOCONTABLE=(select MAX(IDPERIODOCONTABLE) from periodocontable)))is null)then
             set periodoProceso=1;
         else
             SET periodoProceso=0;
         end if;
 
     else
       SET periodoProceso=2;
     end if;
    END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_saldarCuentas`( op INT, vCod INT)
BEGIN
 
    DECLARE deb DOUBLE(10,2) DEFAULT 0;
    DECLARE hab DOUBLE(10,2) DEFAULT 0;
    DECLARE dif DOUBLE(10,2) DEFAULT 0;
 
 IF(op=0) THEN
   BEGIN
     SELECT * FROM cuenta;
   END;
 END IF;
 
 IF(op=1) THEN
   BEGIN
     SET deb=(SELECT DEBE FROM cuenta WHERE CODIGO=vCod);
     SET hab=(SELECT HABER FROM cuenta WHERE CODIGO=vCod);
     SET dif=deb-hab;
     UPDATE periodocontable SET  FECHAFINAL=CURDATE() WHERE IDPERIODOCONTABLE=(SELECT MAX(IDPERIODOCONTABLE));
   
     IF(dif>0) THEN
      UPDATE cuenta SET DEBE=dif, HABER='0.00' WHERE CODIGO=vCod;
     ELSE
      UPDATE cuenta SET DEBE='0.00', HABER=(dif*-1) WHERE CODIGO=vCod;
     END IF;
   END;
 END IF;
 
 IF(op=2) THEN
   BEGIN
     SET deb=(SELECT debe FROM cuenta WHERE codigo=110701);
     SET hab=(SELECT haber FROM cuenta WHERE codigo=210501);
     SET dif=deb-hab;
     
     IF(dif>0) THEN
       UPDATE cuenta SET debe=dif, haber='0.00' WHERE codigo=110701;
       UPDATE cuenta SET debe='0.00', haber='0.00' WHERE codigo=210501;
     ELSE
       UPDATE cuenta SET debe='0.00', haber='0.00' WHERE codigo=110701;
       UPDATE cuenta SET debe='0.00', haber=(dif*(-1)) WHERE codigo=210501;
     END IF;
   END;
 END IF;
 
 IF(op=3) THEN
   BEGIN
     SET deb=(SELECT DEBE FROM cuenta WHERE CODIGO=vCod);
     SET hab=(SELECT HABER FROM cuenta WHERE CODIGO=vCod);
     SET dif=deb-hab;
     IF(dif>0) THEN
      UPDATE cuenta SET DEBE=dif, HABER='0.00' WHERE CODIGO=vCod;
     ELSE
      UPDATE cuenta SET DEBE='0.00', HABER=(dif*-1) WHERE CODIGO=vCod;
     END IF;
   END;
 END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_tablacatalogo`(vcodigo_cuenta int)
BEGIN
       SELECT s.CODIGO, s.NOMBRE, (SELECT t.tipo FROM tiposcuenta t, cuentascatalogo c  WHERE (t.id_tipocuenta=c.id_tipocuenta and c.codigo=vcodigo_cuenta)) as tipocuenta
       FROM cuenta s where s.cue_codigo=vcodigo_cuenta;      
    END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `afecta`
--

CREATE TABLE IF NOT EXISTS `afecta` (
  `id_transaccion` int(11) NOT NULL,
  `CODIGO` int(11) NOT NULL,
  PRIMARY KEY (`id_transaccion`,`CODIGO`),
  KEY `FK_AFECTA` (`CODIGO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE IF NOT EXISTS `cuenta` (
  `CODIGO` int(11) NOT NULL,
  `cue_codigo` int(11) DEFAULT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `DEBE` double(10,2) DEFAULT '0.00',
  `HABER` double(10,2) DEFAULT '0.00',
  `ESTADOFINANCIERO` int(11) NOT NULL,
  `OPERACIONENESTADO` int(11) NOT NULL,
  `PRECIO` double(10,2) DEFAULT NULL,
  `CANTIDAD` int(11) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`),
  KEY `FK_POSEE1` (`cue_codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`CODIGO`, `cue_codigo`, `NOMBRE`, `DEBE`, `HABER`, `ESTADOFINANCIERO`, `OPERACIONENESTADO`, `PRECIO`, `CANTIDAD`) VALUES
(110101, 1101, 'Caja Chica', 6125.00, 0.00, 3, 0, NULL, NULL),
(110102, 1101, 'Banco', 1100.00, 0.00, 3, 0, NULL, NULL),
(110201, 1102, 'Cuentas Por Cobrar', 1221.00, 0.00, 3, 0, NULL, NULL),
(110202, 1102, 'Documentos Por Cobrar', 0.00, 0.00, 3, 0, NULL, NULL),
(110301, 1103, 'Reserva Cuentas Incobrables', 0.00, 0.00, 3, 1, NULL, NULL),
(110401, 1104, 'Harina', 0.00, 2.00, 3, 0, 0.00, 0),
(110402, 1104, 'Azucar', 0.00, 3.00, 3, 0, 0.00, 0),
(110403, 1104, 'Huevos', 0.00, 5.00, 3, 0, 0.00, 0),
(110404, 1104, 'Levadura', 0.00, 0.00, 3, 0, 0.00, 0),
(110405, 1104, 'Mantequilla', 0.00, 20.00, 3, 0, 0.00, 0),
(110406, 1104, 'Leche', 0.00, 2.00, 3, 0, 0.00, 0),
(110407, 1104, 'Agua', 0.00, 0.00, 3, 0, 0.00, 0),
(110643, 1106, 'OF43', 0.00, 0.00, 1, 0, 0.00, 0),
(110701, 1107, 'Iva Credito Fiscal', 260.00, 0.00, 3, 0, NULL, NULL),
(110801, 1108, 'Variacion GIF', 0.00, 72.08, 3, 0, NULL, NULL),
(110901, 1109, 'OF 1: Pichardines', 0.00, 0.00, 1, 0, 0.00, 0),
(110943, 1109, 'OF43', 0.00, 0.00, 1, 0, 0.00, 0),
(110953, 1109, 'OF53', 2.00, 0.00, 1, 0, 2.00, 1),
(120201, 1202, 'Terrenos', 0.00, 0.00, 3, 0, NULL, NULL),
(120202, 1202, 'Edificios', 15500.00, 0.00, 3, 0, NULL, NULL),
(120204, 1202, 'Mobiliario y Equipo De Oficina', 1800.00, 0.00, 3, 0, NULL, NULL),
(120205, 1202, 'Equipo de transporte', 0.00, 0.00, 3, 0, NULL, NULL),
(120206, 1202, 'Maquinaria y equipo de producción', 0.00, 0.00, 3, 0, NULL, NULL),
(120207, 1202, 'Otros Bienes', 200.00, 0.00, 3, 0, NULL, NULL),
(120301, 1203, 'Depreciación de edificios', 0.00, 0.00, 3, 1, NULL, NULL),
(120302, 1203, 'Depreciación de mobiliario y equipo', 0.00, 0.00, 3, 1, NULL, NULL),
(120303, 1203, 'Depreciación de equipo de transporte', 0.00, 0.00, 3, 1, NULL, NULL),
(120304, 1203, 'Depreciación de maquinaria y equipo de producción', 0.00, 0.00, 3, 1, NULL, NULL),
(120305, 1203, 'Depreciación de otros activos', 0.00, 0.00, 3, 1, NULL, NULL),
(120401, 1204, 'Patentes y Marcas', 0.00, 0.00, 3, 0, NULL, NULL),
(120501, 1205, 'Patentes y Marcas', 0.00, 0.00, 3, 1, NULL, NULL),
(210101, 2101, 'Proveedores', 0.00, 0.00, 3, 0, NULL, NULL),
(210102, 2101, 'Documentos por pagar', 0.00, 0.00, 3, 0, NULL, NULL),
(210103, 2101, 'Pago a seguridad privada', 0.00, 0.00, 3, 0, NULL, NULL),
(210201, 2102, 'Sobregiros bancarios', 0.00, 0.00, 3, 0, NULL, NULL),
(210202, 2102, 'Prestamos a corto plazo', 0.00, 0.00, 3, 0, NULL, NULL),
(210203, 2102, 'Porcion circulante de prestamos a largo plazo', 0.00, 0.00, 3, 0, NULL, NULL),
(210204, 2102, 'Otros prestamos', 0.00, 0.00, 3, 0, NULL, NULL),
(210301, 2103, 'Mano de Obra Directa', 0.00, 65.41, 3, 0, -32.70, 2),
(210302, 2103, 'Mano de Obra Indirecta', 0.00, 0.00, 3, 0, NULL, NULL),
(210303, 2103, 'Vacaciones', 0.00, 0.00, 3, 0, NULL, NULL),
(210304, 2103, 'Aguinaldos', 0.00, 0.00, 3, 0, NULL, NULL),
(210401, 2104, 'ISSS', 0.00, 0.00, 1, 0, 0.00, 0),
(210402, 2104, 'AFP', 0.00, 0.00, 1, 0, 0.00, 0),
(210403, 2104, 'Retencion del impuesto sobre la renta', 0.00, 0.00, 1, 0, 0.00, 0),
(210501, 2105, 'Iva debito fiscal', 0.00, 546.00, 3, 0, NULL, NULL),
(220101, 2201, 'Cuentas por pagar', 0.00, 0.00, 3, 0, NULL, NULL),
(220102, 2201, 'Documentos por pagar', 0.00, 0.00, 3, 0, NULL, NULL),
(310101, 3101, 'Capital social', 0.00, 25492.51, 4, 0, 0.00, 0),
(310201, 3102, 'Utilidad del ejercicio', 0.00, 0.00, 2, 0, 0.00, 0),
(310301, 3103, 'Perdida del ejercicio', 0.00, 0.00, 2, 0, 0.00, 0),
(320101, 3201, 'Gastos por robo', 0.00, 0.00, 2, 1, 0.00, 0),
(320102, 3201, 'Retiro por uso personal', 0.00, 0.00, 2, 1, 0.00, 0),
(410101, 4101, 'Gasto por salario', 0.00, 0.00, 1, 1, 0.00, 0),
(410102, 4101, 'Gasto por servicio prestado', 0.00, 0.00, 1, 1, 0.00, 0),
(410103, 4101, 'Costo de lo vendido', 0.00, 0.00, 1, 1, 0.00, 0),
(410104, 4101, 'Gasto por servicios basicos', 0.00, 0.00, 1, 1, 0.00, 0),
(410105, 4101, 'Gasto por gasolina', 0.00, 0.00, 1, 1, 0.00, 0),
(410106, 4101, 'Gasto de venta', 0.00, 0.00, 1, 1, 0.00, 0),
(410107, 4101, 'Gasto de administracion', 0.00, 0.00, 1, 1, 0.00, 0),
(410108, 4101, 'Gasto por descuento no aprovechado', 0.00, 0.00, 1, 1, 0.00, 0),
(420101, 4201, 'Ingreso por ventas', 0.00, 0.00, 1, 0, 0.00, 0),
(420102, 4201, 'Ingreso por descuento aprovechados', 0.00, 0.00, 1, 0, 0.00, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuentascatalogo`
--

CREATE TABLE IF NOT EXISTS `cuentascatalogo` (
  `codigo` int(11) NOT NULL,
  `cuenta` varchar(100) DEFAULT NULL,
  `id_tipocuenta` int(11) NOT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_TIENE1` (`cuenta`),
  KEY `tiene1` (`id_tipocuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cuentascatalogo`
--

INSERT INTO `cuentascatalogo` (`codigo`, `cuenta`, `id_tipocuenta`) VALUES
(1101, 'Efectivo y equivalentes', 1),
(1102, 'Cuentas y documentos por cobrar', 1),
(1103, 'Estimacion para cuentas incobrables', 1),
(1104, 'Inventario de materia prima', 1),
(1105, 'Inventario de producto en proceso', 1),
(1106, 'Inventario de producto terminado', 1),
(1107, 'Iva credito fiscal', 1),
(1108, 'Varacion GIF', 1),
(1109, 'Ordenes de Fabricacion', 1),
(1110, 'Costos de fabricacion', 1),
(1202, 'Propiedad planta y equipo', 1),
(1203, 'Depreciacion acumulada', 1),
(1204, 'Activos intangibles', 1),
(1205, 'Amortizacion de intangibles ', 1),
(1206, 'Cuentas y documentos por cobrar a largo plazo', 1),
(1207, 'Estimacion cuentas incobrables a largo plazo', 1),
(2101, 'Cuentas y documentos por pagar', 2),
(2102, 'Prestamos y sobregiros bancarios', 2),
(2103, 'Remuneraciones y prestaciones por pagar a empleados', 2),
(2104, 'Retenciones y descuentos', 2),
(2105, 'Iva debito fiscal', 2),
(2201, 'Cuentas y documentos por pagar', 2),
(3101, 'Capital social', 3),
(3102, 'Utilidad', 3),
(3103, 'Perdida', 3),
(3201, 'Desinversiones', 3),
(4101, 'Gastos y costos', 4),
(4201, 'Ingresos', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleplanilla`
--

CREATE TABLE IF NOT EXISTS `detalleplanilla` (
  `iddetalleplanilla` int(11) NOT NULL AUTO_INCREMENT,
  `idplanilla` int(11) NOT NULL,
  `idempleado` int(11) NOT NULL,
  `aguinaldo` decimal(10,2) NOT NULL,
  `vacacion` decimal(10,2) NOT NULL,
  `ISSS` decimal(10,2) NOT NULL,
  `AFP` decimal(10,2) NOT NULL,
  `Devengado` decimal(10,2) NOT NULL,
  `Total` decimal(10,2) NOT NULL,
  `FactorRecargo` decimal(8,4) DEFAULT NULL,
  `FactorRecargoIneficiencia` decimal(8,4) DEFAULT NULL,
  `GastoPorIneficiencia` decimal(8,4) DEFAULT NULL,
  PRIMARY KEY (`iddetalleplanilla`),
  KEY `fk_empleado_detalleplanilla_idx` (`idempleado`),
  KEY `fk_planilla_detalleplanilla_idx` (`idplanilla`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=16 ;

--
-- Volcado de datos para la tabla `detalleplanilla`
--

INSERT INTO `detalleplanilla` (`iddetalleplanilla`, `idplanilla`, `idempleado`, `aguinaldo`, `vacacion`, `ISSS`, `AFP`, `Devengado`, `Total`, `FactorRecargo`, `FactorRecargoIneficiencia`, `GastoPorIneficiencia`) VALUES
(1, 1, 1, '0.00', '0.00', '8.75', '7.88', '52.08', '68.71', '1.0333', '1.1481', '0.1148'),
(2, 1, 2, '0.00', '0.00', '7.00', '6.30', '16.97', '30.27', '1.0333', '1.1481', '0.1148'),
(3, 1, 3, '0.00', '0.00', '7.00', '6.30', '16.97', '30.27', '1.0333', '1.1481', '0.1148'),
(4, 1, 4, '0.00', '0.00', '7.00', '6.30', '16.97', '30.27', '1.0333', '1.1481', '0.1148'),
(5, 1, 5, '0.00', '0.00', '1.75', '1.58', '4.24', '7.57', '1.0333', '2.0665', '1.0333'),
(6, 1, 6, '0.00', '0.00', '6.21', '5.59', '15.06', '26.86', '1.0333', '2.0665', '1.0333'),
(7, 1, 7, '0.00', '0.00', '7.00', '6.30', '16.97', '30.27', '1.0333', '1.4761', '0.4428'),
(8, 1, 8, '0.00', '0.00', '5.25', '4.73', '12.73', '22.70', '1.0333', '1.4761', '0.4428'),
(9, 1, 9, '0.00', '0.00', '4.81', '4.33', '11.67', '20.81', '1.0333', '1.4761', '0.4428'),
(10, 1, 10, '0.00', '0.00', '1.75', '1.58', '4.24', '7.57', '1.0333', '1.4761', '0.4428'),
(11, 1, 11, '0.00', '0.00', '7.00', '6.30', '16.97', '30.27', '1.0333', '1.7221', '0.6888'),
(12, 1, 12, '0.00', '0.00', '5.69', '5.12', '13.79', '24.59', '1.0333', '1.7221', '0.6888'),
(13, 1, 13, '0.00', '0.00', '3.50', '3.15', '8.48', '15.13', '1.0333', '1. javax4761', '0.4428'),
(14, 1, 14, '0.00', '0.00', '5.25', '4.73', '12.73', '22.70', '1.0333', '1.7221', '0.6888'),
(15, 1, 16, '3.04', '3.49', '4.38', '3.94', '10.61', '25.46', '1.0594', '1.7657', '0.7063');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE IF NOT EXISTS `empleado` (
  `IDEMPLEADO` int(11) NOT NULL AUTO_INCREMENT,
  `NOMBREEMPRESA` varchar(50) CHARACTER SET latin1 DEFAULT NULL,
  `NOMBREEMPLEADO` varchar(100) CHARACTER SET latin1 NOT NULL,
  `SALARIO` double NOT NULL,
  `EFICIENCIA` double NOT NULL,
  `ANIOS` int(11) NOT NULL,
  `DeptoProduccion` bit(1) DEFAULT NULL,
  PRIMARY KEY (`IDEMPLEADO`),
  KEY `FK_TIENE2` (`NOMBREEMPRESA`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=17 ;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`IDEMPLEADO`, `NOMBREEMPRESA`, `NOMBREEMPLEADO`, `SALARIO`, `EFICIENCIA`, `ANIOS`, `DeptoProduccion`) VALUES
(1, NULL, 'Omar Garcia', 500, 0.9, 0, b'1'),
(2, NULL, 'Raul Mayorga', 400, 0.9, 0, b'0'),
(3, NULL, 'Christian Sosa', 400, 0.9, 0, NULL),
(4, NULL, 'Ilich Morales', 400, 0.9, 0, NULL),
(5, NULL, 'Melvin Herrera', 100, 0.5, 0, NULL),
(6, NULL, 'María Sánchez', 355, 0.5, 0, NULL),
(7, NULL, 'Alberto Sánchez', 400, 0.7, 0, NULL),
(8, NULL, 'Carlos Campos', 300, 0.7, 0, NULL),
(9, NULL, 'Jhon Snow', 275, 0.7, 0, NULL),
(10, NULL, 'Guillermo Mejía', 100, 0.7, 0, NULL),
(11, NULL, 'Bladimir Rodriguez', 400, 0.6, 0, NULL),
(12, NULL, 'Karla Hernandez', 325, 0.6, 0, NULL),
(13, NULL, 'Ericka Pérez', 200, 0.7, 0, NULL),
(14, NULL, 'Fabiola Marroquín', 300, 0.6, 0, NULL),
(16, NULL, 'Jeanette de Pocasangre', 250, 0.6, 6, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE IF NOT EXISTS `empresa` (
  `NOMBREEMPRESA` varchar(50) NOT NULL,
  PRIMARY KEY (`NOMBREEMPRESA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`NOMBREEMPRESA`) VALUES
('PANADERIA DULCE PAN');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materiasprimas`
--

CREATE TABLE IF NOT EXISTS `materiasprimas` (
  `idmateriaprima` int(11) NOT NULL AUTO_INCREMENT,
  `nombremateriaprima` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `codigo` int(11) NOT NULL,
  PRIMARY KEY (`idmateriaprima`),
  KEY `fk_materiaprima_cuenta` (`codigo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=8 ;

--
-- Volcado de datos para la tabla `materiasprimas`
--

INSERT INTO `materiasprimas` (`idmateriaprima`, `nombremateriaprima`, `codigo`) VALUES
(1, 'Harina', 110401),
(2, 'Azucar', 110402),
(3, 'Huevos', 110403),
(4, 'Levadura', 110404),
(5, 'Mantequilla', 110405),
(6, 'Leche', 110406),
(7, 'Agua', 110407);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenfabricacion`
--

CREATE TABLE IF NOT EXISTS `ordenfabricacion` (
  `idorden` int(11) NOT NULL AUTO_INCREMENT,
  `noorden` int(11) NOT NULL,
  `codigo` int(11) NOT NULL,
  `nombre` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `fechacrea` date NOT NULL,
  `saldoactual` decimal(10,2) NOT NULL,
  `abierta` bit(1) NOT NULL,
  `materiaprima` decimal(10,2) NOT NULL,
  `manodeobra` decimal(10,2) NOT NULL,
  `costoindirectofab` decimal(10,2) NOT NULL,
  `porcentajemod` int(11) NOT NULL,
  `porcentajemp` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `costounitario` decimal(12,2) NOT NULL,
  PRIMARY KEY (`idorden`),
  KEY `fk_ordenfabricacion_cuenta` (`codigo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `ordenfabricacion`
--

INSERT INTO `ordenfabricacion` (`idorden`, `noorden`, `codigo`, `nombre`, `fechacrea`, `saldoactual`, `abierta`, `materiaprima`, `manodeobra`, `costoindirectofab`, `porcentajemod`, `porcentajemp`, `cantidad`, `costounitario`) VALUES
(1, 1, 110901, 'OF 1: Pichardines', '2016-11-25', '43.33', b'0', '10.00', '13.33', '20.00', 150, 0, 20, '2.17'),
(2, 43, 110943, 'OF43', '2016-11-27', '124.16', b'0', '20.00', '52.08', '52.08', 100, 0, 40, '3.10'),
(3, 53, 110953, 'OF53', '2016-11-27', '2.00', b'1', '2.00', '0.00', '0.00', 100, 0, 40, '0.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_manoobra`
--

CREATE TABLE IF NOT EXISTS `orden_manoobra` (
  `idordenmod` int(11) NOT NULL AUTO_INCREMENT,
  `idorden` int(11) NOT NULL,
  `idempleado` int(11) NOT NULL,
  `horas` decimal(10,2) NOT NULL,
  `monto` decimal(12,2) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`idordenmod`),
  KEY `fk_orden_ordenmo` (`idorden`),
  KEY `fk_empleado_ordenmo` (`idempleado`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `orden_manoobra`
--

INSERT INTO `orden_manoobra` (`idordenmod`, `idorden`, `idempleado`, `horas`, `monto`, `fecha`) VALUES
(1, 1, 4, '8.00', '13.33', '2016-11-25'),
(2, 2, 1, '25.00', '52.08', '2016-11-27');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden_materiaprima`
--

CREATE TABLE IF NOT EXISTS `orden_materiaprima` (
  `idordenmp` int(11) NOT NULL AUTO_INCREMENT,
  `idmateriaprima` int(11) NOT NULL,
  `idorden` int(11) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  PRIMARY KEY (`idordenmp`),
  KEY `fk_orden_ordenmp` (`idorden`),
  KEY `fk_materiaprima_ordenmp` (`idmateriaprima`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `orden_materiaprima`
--

INSERT INTO `orden_materiaprima` (`idordenmp`, `idmateriaprima`, `idorden`, `monto`) VALUES
(1, 3, 1, '5.00'),
(2, 6, 1, '2.00'),
(3, 2, 1, '3.00'),
(4, 5, 2, '20.00'),
(5, 1, 3, '2.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `periodocontable`
--

CREATE TABLE IF NOT EXISTS `periodocontable` (
  `FECHAINICIO` date NOT NULL,
  `FECHAFINAL` date DEFAULT NULL,
  `IDPERIODOCONTABLE` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IDPERIODOCONTABLE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Volcado de datos para la tabla `periodocontable`
--

INSERT INTO `periodocontable` (`FECHAINICIO`, `FECHAFINAL`, `IDPERIODOCONTABLE`) VALUES
('2016-11-14', '2016-11-27', 5),
('2016-11-27', '2016-11-27', 6),
('2016-11-27', NULL, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `planilla`
--

CREATE TABLE IF NOT EXISTS `planilla` (
  `idplanilla` int(11) NOT NULL AUTO_INCREMENT,
  `periodoplanilla` date NOT NULL,
  `descripcion` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `estatus` bit(1) NOT NULL,
  PRIMARY KEY (`idplanilla`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `planilla`
--

INSERT INTO `planilla` (`idplanilla`, `periodoplanilla`, `descripcion`, `estatus`) VALUES
(1, '2016-11-27', 'Planilla 5', b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tiposcuenta`
--

CREATE TABLE IF NOT EXISTS `tiposcuenta` (
  `id_tipocuenta` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(50) NOT NULL,
  PRIMARY KEY (`id_tipocuenta`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `tiposcuenta`
--

INSERT INTO `tiposcuenta` (`id_tipocuenta`, `tipo`) VALUES
(1, 'Activo'),
(2, 'Pasivo'),
(3, 'Capital'),
(4, 'Resultado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `transacciones`
--

CREATE TABLE IF NOT EXISTS `transacciones` (
  `id_transaccion` int(11) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`id_transaccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(30) NOT NULL,
  `contrasena` varchar(30) NOT NULL,
  `tipo_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `usuario`, `contrasena`, `tipo_usuario`) VALUES
(1, 'admin', 'asd', 2),
(2, 'omar', 'asd', 1),
(3, 'ilich', 'asd', 1),
(4, 'christian', 'asd', 1),
(5, 'raul', 'asd', 1),
(6, 'melvin', 'asd', 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `afecta`
--
ALTER TABLE `afecta`
  ADD CONSTRAINT `FK_AFECTA` FOREIGN KEY (`CODIGO`) REFERENCES `cuenta` (`CODIGO`);

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD CONSTRAINT `FK_POSEE1` FOREIGN KEY (`cue_codigo`) REFERENCES `cuentascatalogo` (`codigo`);

--
-- Filtros para la tabla `cuentascatalogo`
--
ALTER TABLE `cuentascatalogo`
  ADD CONSTRAINT `tiene1` FOREIGN KEY (`id_tipocuenta`) REFERENCES `tiposcuenta` (`id_tipocuenta`);

--
-- Filtros para la tabla `detalleplanilla`
--
ALTER TABLE `detalleplanilla`
  ADD CONSTRAINT `fk_empleado_detalleplanilla` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`IDEMPLEADO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_planilla_detalleplanilla` FOREIGN KEY (`idplanilla`) REFERENCES `planilla` (`idplanilla`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `FK_TIENE2` FOREIGN KEY (`NOMBREEMPRESA`) REFERENCES `empresa` (`NOMBREEMPRESA`);

--
-- Filtros para la tabla `materiasprimas`
--
ALTER TABLE `materiasprimas`
  ADD CONSTRAINT `fk_materiaprima_cuenta` FOREIGN KEY (`codigo`) REFERENCES `cuenta` (`CODIGO`);

--
-- Filtros para la tabla `ordenfabricacion`
--
ALTER TABLE `ordenfabricacion`
  ADD CONSTRAINT `fk_ordenfabricacion_cuenta` FOREIGN KEY (`codigo`) REFERENCES `cuenta` (`CODIGO`);

--
-- Filtros para la tabla `orden_manoobra`
--
ALTER TABLE `orden_manoobra`
  ADD CONSTRAINT `fk_empleado_ordenmo` FOREIGN KEY (`idempleado`) REFERENCES `empleado` (`IDEMPLEADO`),
  ADD CONSTRAINT `fk_orden_ordenmo` FOREIGN KEY (`idorden`) REFERENCES `ordenfabricacion` (`idorden`);

--
-- Filtros para la tabla `orden_materiaprima`
--
ALTER TABLE `orden_materiaprima`
  ADD CONSTRAINT `fk_materiaprima_ordenmp` FOREIGN KEY (`idmateriaprima`) REFERENCES `materiasprimas` (`idmateriaprima`),
  ADD CONSTRAINT `fk_orden_ordenmp` FOREIGN KEY (`idorden`) REFERENCES `ordenfabricacion` (`idorden`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
