-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: house
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `房屋基本信息`
--

DROP TABLE IF EXISTS `房屋基本信息`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `房屋基本信息` (
  `id` varchar(45) COLLATE utf8_bin NOT NULL,
  `房屋信息来源` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `房屋唯一标识` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `房屋类型` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `名称` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `标题` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `网址` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `所在小区` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `坐标` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `户型` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `建筑面积` int(11) DEFAULT NULL,
  `实用面积` int(11) DEFAULT NULL,
  `朝向` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `装修` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `供暖` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `产权年限` int(11) DEFAULT NULL,
  `所在楼层` int(11) DEFAULT NULL,
  `总楼层` int(11) DEFAULT NULL,
  `户型结构` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `建筑类型` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `建筑结构` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `梯户比例` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `电梯` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `用电类型` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `用水类型` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `燃气价格` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `挂牌时间` date DEFAULT NULL,
  `上次交易时间` date DEFAULT NULL,
  `房本年限` int(11) DEFAULT NULL,
  `抵押信息` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `交易权属` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `房屋用途` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `产权所属` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `房本备注` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `联系人` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `联系信息` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `房屋基本信息`
--

LOCK TABLES `房屋基本信息` WRITE;
/*!40000 ALTER TABLE `房屋基本信息` DISABLE KEYS */;
/*!40000 ALTER TABLE `房屋基本信息` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-03 15:34:51
