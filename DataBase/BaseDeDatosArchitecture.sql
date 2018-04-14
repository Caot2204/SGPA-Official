-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: localhost    Database: sgpa_database
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `avance_programatico`
--

DROP TABLE IF EXISTS `avance_programatico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avance_programatico` (
  `Id` varchar(30) NOT NULL,
  `NRC` int(11) DEFAULT NULL,
  `Id_Docente` varchar(100) DEFAULT NULL,
  `Objetivo_General` varchar(500) DEFAULT NULL,
  `Estado` enum('en_edicion','concluido') DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avance_programatico`
--

LOCK TABLES `avance_programatico` WRITE;
/*!40000 ALTER TABLE `avance_programatico` DISABLE KEYS */;
/*!40000 ALTER TABLE `avance_programatico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avance_programatico_avance_unidad`
--

DROP TABLE IF EXISTS `avance_programatico_avance_unidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avance_programatico_avance_unidad` (
  `Id_Avance_Programatico` varchar(30) DEFAULT NULL,
  `Unidad` int(11) DEFAULT NULL,
  `Porcentaje_Avance` float DEFAULT NULL,
  `Observacion` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avance_programatico_avance_unidad`
--

LOCK TABLES `avance_programatico_avance_unidad` WRITE;
/*!40000 ALTER TABLE `avance_programatico_avance_unidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `avance_programatico_avance_unidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `avance_programatico_unidad_planeacion`
--

DROP TABLE IF EXISTS `avance_programatico_unidad_planeacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `avance_programatico_unidad_planeacion` (
  `Id_Avance_Programatico` varchar(30) DEFAULT NULL,
  `Unidad` int(11) DEFAULT NULL,
  `Tema_Desarrollado` varchar(150) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `Tarea_Practica_Realizada` varchar(200) DEFAULT NULL,
  `Tecnica_Didactica` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `avance_programatico_unidad_planeacion`
--

LOCK TABLES `avance_programatico_unidad_planeacion` WRITE;
/*!40000 ALTER TABLE `avance_programatico_unidad_planeacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `avance_programatico_unidad_planeacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calendario`
--

DROP TABLE IF EXISTS `calendario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `calendario` (
  `Nombre_Actividad` varchar(100) DEFAULT NULL,
  `Descripcion_Actividad` varchar(500) DEFAULT NULL,
  `Fecha_Actividad` date DEFAULT NULL,
  `Academia` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calendario`
--

LOCK TABLES `calendario` WRITE;
/*!40000 ALTER TABLE `calendario` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso` (
  `NRC` int(11) NOT NULL,
  `Nombre_EE` varchar(100) DEFAULT NULL,
  `Bloque` int(11) DEFAULT NULL,
  `Seccion` char(1) DEFAULT NULL,
  `Periodo_Escolar` varchar(50) DEFAULT NULL,
  `Id_Docente` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`NRC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso_horario`
--

DROP TABLE IF EXISTS `curso_horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso_horario` (
  `NRC` int(11) NOT NULL,
  `Dia` varchar(20) DEFAULT NULL,
  `Hora` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso_horario`
--

LOCK TABLES `curso_horario` WRITE;
/*!40000 ALTER TABLE `curso_horario` DISABLE KEYS */;
/*!40000 ALTER TABLE `curso_horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experiencia_educativa`
--

DROP TABLE IF EXISTS `experiencia_educativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `experiencia_educativa` (
  `Nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiencia_educativa`
--

LOCK TABLES `experiencia_educativa` WRITE;
/*!40000 ALTER TABLE `experiencia_educativa` DISABLE KEYS */;
/*!40000 ALTER TABLE `experiencia_educativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_curso`
--

DROP TABLE IF EXISTS `plan_curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_curso` (
  `Id` varchar(30) NOT NULL,
  `NRC` int(11) DEFAULT NULL,
  `Id_Docente` varchar(100) DEFAULT NULL,
  `Objetivo_General` varchar(500) DEFAULT NULL,
  `Estado` enum('en_edicion','concluido') DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_curso`
--

LOCK TABLES `plan_curso` WRITE;
/*!40000 ALTER TABLE `plan_curso` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_curso_bibliografia`
--

DROP TABLE IF EXISTS `plan_curso_bibliografia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_curso_bibliografia` (
  `Id_Plan_Curso` varchar(30) DEFAULT NULL,
  `Autor` varchar(100) DEFAULT NULL,
  `Titulo_Libro` varchar(150) DEFAULT NULL,
  `Editorial` varchar(100) DEFAULT NULL,
  `Año` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_curso_bibliografia`
--

LOCK TABLES `plan_curso_bibliografia` WRITE;
/*!40000 ALTER TABLE `plan_curso_bibliografia` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_curso_bibliografia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_curso_calendario_evaluacion_unidad`
--

DROP TABLE IF EXISTS `plan_curso_calendario_evaluacion_unidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_curso_calendario_evaluacion_unidad` (
  `Id_Plan_Curso` varchar(30) DEFAULT NULL,
  `Unidad` varchar(10) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `Criterio` varchar(150) DEFAULT NULL,
  `Instrumento` varchar(100) DEFAULT NULL,
  `Porcentaje` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_curso_calendario_evaluacion_unidad`
--

LOCK TABLES `plan_curso_calendario_evaluacion_unidad` WRITE;
/*!40000 ALTER TABLE `plan_curso_calendario_evaluacion_unidad` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_curso_calendario_evaluacion_unidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_curso_unidad_planeacion`
--

DROP TABLE IF EXISTS `plan_curso_unidad_planeacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_curso_unidad_planeacion` (
  `Id_Plan_Curso` varchar(30) DEFAULT NULL,
  `Unidad` int(11) DEFAULT NULL,
  `Tema` varchar(150) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `Tarea_Practica` varchar(200) DEFAULT NULL,
  `Tecnica_Didactica` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_curso_unidad_planeacion`
--

LOCK TABLES `plan_curso_unidad_planeacion` WRITE;
/*!40000 ALTER TABLE `plan_curso_unidad_planeacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_curso_unidad_planeacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_trabajo_academia`
--

DROP TABLE IF EXISTS `plan_trabajo_academia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_trabajo_academia` (
  `Id` varchar(30) NOT NULL,
  `Fecha_Aprobacion` date DEFAULT NULL,
  `Programa_Educativo` varchar(100) DEFAULT NULL,
  `Periodo_Escolar` varchar(50) DEFAULT NULL,
  `Id_Academia` varchar(100) DEFAULT NULL,
  `Id_Coordinador` varchar(100) DEFAULT NULL,
  `Objetivo_General` varchar(500) DEFAULT NULL,
  `Estado` enum('en_edicion','concluido') DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_trabajo_academia`
--

LOCK TABLES `plan_trabajo_academia` WRITE;
/*!40000 ALTER TABLE `plan_trabajo_academia` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_trabajo_academia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_trabajo_academia_examen_parcial`
--

DROP TABLE IF EXISTS `plan_trabajo_academia_examen_parcial`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_trabajo_academia_examen_parcial` (
  `Id_Plan_Academia` varchar(30) DEFAULT NULL,
  `Numero_Parcial` int(11) NOT NULL,
  `EE` varchar(100) DEFAULT NULL,
  `Tema_De_Parcial` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_trabajo_academia_examen_parcial`
--

LOCK TABLES `plan_trabajo_academia_examen_parcial` WRITE;
/*!40000 ALTER TABLE `plan_trabajo_academia_examen_parcial` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_trabajo_academia_examen_parcial` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_trabajo_academia_forma_evaluacion`
--

DROP TABLE IF EXISTS `plan_trabajo_academia_forma_evaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_trabajo_academia_forma_evaluacion` (
  `Id_Plan_Academia` varchar(30) DEFAULT NULL,
  `Elemento` varchar(100) DEFAULT NULL,
  `Porcentaje` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_trabajo_academia_forma_evaluacion`
--

LOCK TABLES `plan_trabajo_academia_forma_evaluacion` WRITE;
/*!40000 ALTER TABLE `plan_trabajo_academia_forma_evaluacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_trabajo_academia_forma_evaluacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_trabajo_academia_historico_de_revision`
--

DROP TABLE IF EXISTS `plan_trabajo_academia_historico_de_revision`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_trabajo_academia_historico_de_revision` (
  `Id_Plan_Academia` varchar(30) DEFAULT NULL,
  `No_Revision` int(11) DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `Seccion` varchar(100) DEFAULT NULL,
  `Descripcion` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_trabajo_academia_historico_de_revision`
--

LOCK TABLES `plan_trabajo_academia_historico_de_revision` WRITE;
/*!40000 ALTER TABLE `plan_trabajo_academia_historico_de_revision` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_trabajo_academia_historico_de_revision` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_trabajo_academia_objetivo_particular`
--

DROP TABLE IF EXISTS `plan_trabajo_academia_objetivo_particular`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_trabajo_academia_objetivo_particular` (
  `Id_Plan_Academia` varchar(30) DEFAULT NULL,
  `Id` varchar(50) DEFAULT NULL,
  `Descripcion` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_trabajo_academia_objetivo_particular`
--

LOCK TABLES `plan_trabajo_academia_objetivo_particular` WRITE;
/*!40000 ALTER TABLE `plan_trabajo_academia_objetivo_particular` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_trabajo_academia_objetivo_particular` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_trabajo_academia_objetivo_particular_meta`
--

DROP TABLE IF EXISTS `plan_trabajo_academia_objetivo_particular_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_trabajo_academia_objetivo_particular_meta` (
  `Id` varchar(50) DEFAULT NULL,
  `Descripcion` varchar(500) DEFAULT NULL,
  `Id_Objetivo_Particular` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_trabajo_academia_objetivo_particular_meta`
--

LOCK TABLES `plan_trabajo_academia_objetivo_particular_meta` WRITE;
/*!40000 ALTER TABLE `plan_trabajo_academia_objetivo_particular_meta` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_trabajo_academia_objetivo_particular_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_trabajo_academia_objetivo_particular_meta_actividad`
--

DROP TABLE IF EXISTS `plan_trabajo_academia_objetivo_particular_meta_actividad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan_trabajo_academia_objetivo_particular_meta_actividad` (
  `Fecha_Semana` varchar(50) DEFAULT NULL,
  `Actividad` varchar(200) DEFAULT NULL,
  `Forma_Operar` varchar(200) DEFAULT NULL,
  `Id_Meta` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_trabajo_academia_objetivo_particular_meta_actividad`
--

LOCK TABLES `plan_trabajo_academia_objetivo_particular_meta_actividad` WRITE;
/*!40000 ALTER TABLE `plan_trabajo_academia_objetivo_particular_meta_actividad` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_trabajo_academia_objetivo_particular_meta_actividad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programa_educativo`
--

DROP TABLE IF EXISTS `programa_educativo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programa_educativo` (
  `Id` varchar(10) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programa_educativo`
--

LOCK TABLES `programa_educativo` WRITE;
/*!40000 ALTER TABLE `programa_educativo` DISABLE KEYS */;
/*!40000 ALTER TABLE `programa_educativo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programa_educativo_experiencia_educativa`
--

DROP TABLE IF EXISTS `programa_educativo_experiencia_educativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `programa_educativo_experiencia_educativa` (
  `Id_Programa` varchar(10) DEFAULT NULL,
  `Nombre_EE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programa_educativo_experiencia_educativa`
--

LOCK TABLES `programa_educativo_experiencia_educativa` WRITE;
/*!40000 ALTER TABLE `programa_educativo_experiencia_educativa` DISABLE KEYS */;
/*!40000 ALTER TABLE `programa_educativo_experiencia_educativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `Id` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Grado_Estudios` varchar(150) DEFAULT NULL,
  `Rol` enum('docente','coordinador','coordinadora_general') NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13 21:55:46
