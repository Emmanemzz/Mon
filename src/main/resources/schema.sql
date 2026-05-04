/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
CREATE TABLE `usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rol` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `biografia` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `imagen_perfil` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5171l57faosmj8myawaucatdw` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
INSERT INTO `usuario` VALUES (1,'admin@mon.com','Admin','$2a$12$0t.8mLgahi7IDds4kAcigOvy3QKH45xkzvLd.iWd11q3YKZnLWwcK','ADMIN',NULL,NULL),(2,'emma@mon.com','Emma','$2a$10$dTquv.iKDXYbg26vgFStdefpJOQp0DuQP4yow.Bg/rLpewFpLniEG','USER',NULL,NULL),(3,'unai@mon.com','Unai','$2a$10$CbUu/gxpzSSf7Su44aVRS.gOucnS0Xsg91Xn812jm9OddwauanoKe','USER',NULL,NULL);
UNLOCK TABLES;

--
-- Table structure for table `recetas`
--

DROP TABLE IF EXISTS `recetas`;
CREATE TABLE `recetas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pais` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipo_dieta` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `alergias` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ingredientes` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usuario_id` bigint NOT NULL,
  `tiempo_preparacion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dificultad` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `metodo_preparacion` varchar(5000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `imagen1` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `imagen2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipo_plato` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `destacada` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKlw0u6y4dggaaen60prd15ilhf` (`usuario_id`),
  CONSTRAINT `FKlw0u6y4dggaaen60prd15ilhf` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Data for table `recetas`
--

LOCK TABLES `recetas` WRITE;
INSERT INTO `recetas` VALUES (4,'Imperiis successerit primos neglexerit aperiam, desiderat graviterque grata, exedunt quippiam accedere operam periculis eveniet intellegere turbulentaeque ingenia legat albucius secutus ii ita quia, penitus morati, discordiae omni consilio invenire inutile','Latinoamericana',NULL,'Ceviche',NULL,'Nomini\r\nLorem\r\nIpsum',2,'15-30 min','Media','Agatur eumque, controversia, adversarium amicitiae, id dolor conquirendae morbi brevi culpa iniuria antiquitate reprehensiones fecit debeo hominum huius exercitumque abducat expeteretur nasci pater eaque sapiens tractat pronuntiaret quosque expectata depravatum cognitione tradunt tale pariatur adoptionem effici expectamus privamur num scientia laudantium principes posset noster, firmissima senserit fortasse intus reliquaque accusamus\r\n\r\n Quamvis odio mutans scribendi reprehenderit inhaererent quantaque rebus sol voluptati quiddam fruentem eodem sapienti inprobitas progrediens factorum contineri brutus deditum scribendi meliore aliquando disserui erant solam exorsus explentur laetamur censet exquirere opes unam statuat, libris probant tradere modum expectamus primum parvos alienum perciperet iniuste omnem sentire, propriae chorusque arare erigimur\r\n\r\n Opinionum possunt timeam malum inportuno meminerit novum defatigatio nec, tenebo labefactetur aliquam plena pondere provocatus historiae, quamvis debemus iuberet modum, sibi possim sentiunt ultimum vituperatum debilitati domesticarum successerit, adest iis instructus carere consectetur natus vindicet virtute adquiescere magnis molita animadversionem afranius brevi turpius convicia afferrent hostem faciant, tradidisse silano delicata\r\n\r\n Frui fugiamus multam secundum mea maerores e magnosque habet nati mollis amarissimam habeatur cupiditatibus pertinacia, his num indoctis proposita, perpetua dolore, democritus quaeri, ferri vivere extremum cupiditatibusque solent sensum privatio commemorandis erroribus perspici scientiam isti incommoda tamen sapiens proficiscuntur ait, inflammat, inquam talem detrimenti fortitudo provident homine solemus odioque omnes\r\n\r\n Constituit locatus suscipere conectitur expetendas summam certissimam gloriosis utilitatibus illum graecis superstitio sensuum tollitur successerit audiebam perpessio deterritum acri, iactare saxum statuerunt gratiam locis primos num captet tam materia, audivi quasi nasci atilii, disserunt elaboraret o omni ullus, novi quantaque nostras militaris erunt iactant dicunt, sane infinitum fuit equidem dissentiunt','https://www.huleymantel.com/uploads/s1/49/39/29/fotos-variadas-luis-miguel-anon-13.webp','','Entrante',_binary '\0');
UNLOCK TABLES;

--
-- Table structure for table `recetas_guardadas`
--

DROP TABLE IF EXISTS `recetas_guardadas`;
CREATE TABLE `recetas_guardadas` (
  `usuario_id` bigint NOT NULL,
  `receta_id` bigint NOT NULL,
  KEY `FKavowwlcuo0lcax3tmda7r5eks` (`receta_id`),
  KEY `FKp92ehwhvyq7jc5s92p4syh0s8` (`usuario_id`),
  CONSTRAINT `FKavowwlcuo0lcax3tmda7r5eks` FOREIGN KEY (`receta_id`) REFERENCES `recetas` (`id`),
  CONSTRAINT `FKp92ehwhvyq7jc5s92p4syh0s8` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `comentarios`
--

DROP TABLE IF EXISTS `comentarios`;
CREATE TABLE `comentarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contenido` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `fecha_comentario` datetime(6) DEFAULT NULL,
  `fecha_creacion_comentario` datetime(6) DEFAULT NULL,
  `usuario_id` bigint NOT NULL,
  `receta_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjejir8kkix1vhxc98f1jr3fwj` (`usuario_id`),
  KEY `FKcdkymvayk1k94xjtqkcn9v12o` (`receta_id`),
  CONSTRAINT `FKcdkymvayk1k94xjtqkcn9v12o` FOREIGN KEY (`receta_id`) REFERENCES `recetas` (`id`),
  CONSTRAINT `FKjejir8kkix1vhxc98f1jr3fwj` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;