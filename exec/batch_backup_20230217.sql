-- MariaDB dump 10.19  Distrib 10.10.2-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: batch
-- ------------------------------------------------------
-- Server version	10.10.2-MariaDB-1:10.10.2+maria~ubu2204

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `BATCH_JOB_EXECUTION`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `CREATE_TIME` datetime(6) NOT NULL,
  `START_TIME` datetime(6) DEFAULT NULL,
  `END_TIME` datetime(6) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime(6) DEFAULT NULL,
  `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
  CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_JOB_EXECUTION`
--

LOCK TABLES `BATCH_JOB_EXECUTION` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION` VALUES
(1,2,1,'2023-02-11 09:43:50.714000','2023-02-11 09:43:50.790000','2023-02-11 09:43:54.237000','COMPLETED','COMPLETED','','2023-02-11 09:43:54.239000',NULL),
(2,2,2,'2023-02-11 09:43:54.272000','2023-02-11 09:43:54.287000','2023-02-11 09:43:55.320000','COMPLETED','COMPLETED','','2023-02-11 09:43:55.321000',NULL),
(3,2,3,'2023-02-12 04:00:00.011000','2023-02-12 04:00:00.022000','2023-02-12 04:00:00.551000','COMPLETED','COMPLETED','','2023-02-12 04:00:00.551000',NULL),
(4,2,4,'2023-02-12 05:55:34.664000','2023-02-12 05:55:34.731000','2023-02-12 05:55:37.278000','COMPLETED','COMPLETED','','2023-02-12 05:55:37.279000',NULL),
(5,2,5,'2023-02-12 05:55:37.301000','2023-02-12 05:55:37.310000','2023-02-12 05:55:38.255000','COMPLETED','COMPLETED','','2023-02-12 05:55:38.256000',NULL),
(6,2,6,'2023-02-12 06:35:26.043000','2023-02-12 06:35:26.111000','2023-02-12 06:35:28.645000','COMPLETED','COMPLETED','','2023-02-12 06:35:28.646000',NULL),
(7,2,7,'2023-02-12 06:35:28.674000','2023-02-12 06:35:28.684000','2023-02-12 06:35:29.618000','COMPLETED','COMPLETED','','2023-02-12 06:35:29.618000',NULL),
(8,2,8,'2023-02-12 07:21:34.164000','2023-02-12 07:21:34.257000','2023-02-12 07:21:38.684000','COMPLETED','COMPLETED','','2023-02-12 07:21:38.685000',NULL),
(9,2,9,'2023-02-12 07:21:38.721000','2023-02-12 07:21:38.729000','2023-02-12 07:21:40.663000','COMPLETED','COMPLETED','','2023-02-12 07:21:40.664000',NULL),
(10,2,10,'2023-02-12 17:27:50.952000','2023-02-12 17:27:51.107000','2023-02-12 17:27:55.660000','COMPLETED','COMPLETED','','2023-02-12 17:27:55.664000',NULL),
(11,2,11,'2023-02-12 17:27:55.693000','2023-02-12 17:27:55.708000','2023-02-12 17:27:57.489000','COMPLETED','COMPLETED','','2023-02-12 17:27:57.490000',NULL),
(12,2,12,'2023-02-12 18:00:38.277000','2023-02-12 18:00:38.412000','2023-02-12 18:00:42.748000','COMPLETED','COMPLETED','','2023-02-12 18:00:42.757000',NULL),
(13,2,13,'2023-02-12 18:00:42.810000','2023-02-12 18:00:42.835000','2023-02-12 18:00:44.239000','COMPLETED','COMPLETED','','2023-02-12 18:00:44.240000',NULL),
(14,2,14,'2023-02-12 18:54:19.868000','2023-02-12 18:54:19.980000','2023-02-12 18:54:25.405000','COMPLETED','COMPLETED','','2023-02-12 18:54:25.414000',NULL),
(15,2,15,'2023-02-12 18:54:25.461000','2023-02-12 18:54:25.479000','2023-02-12 18:54:26.529000','COMPLETED','COMPLETED','','2023-02-12 18:54:26.529000',NULL),
(16,2,16,'2023-02-12 10:46:50.860000','2023-02-12 10:46:50.988000','2023-02-12 10:46:55.420000','COMPLETED','COMPLETED','','2023-02-12 10:46:55.421000',NULL),
(17,2,17,'2023-02-12 10:46:55.440000','2023-02-12 10:46:55.448000','2023-02-12 10:46:56.366000','COMPLETED','COMPLETED','','2023-02-12 10:46:56.367000',NULL),
(18,2,18,'2023-02-12 19:48:31.971000','2023-02-12 19:48:32.044000','2023-02-12 19:48:36.610000','COMPLETED','COMPLETED','','2023-02-12 19:48:36.612000',NULL),
(19,2,19,'2023-02-12 19:48:36.665000','2023-02-12 19:48:36.675000','2023-02-12 19:48:37.779000','COMPLETED','COMPLETED','','2023-02-12 19:48:37.779000',NULL),
(20,2,20,'2023-02-13 00:04:26.375000','2023-02-13 00:04:26.496000','2023-02-13 00:04:31.457000','COMPLETED','COMPLETED','','2023-02-13 00:04:31.465000',NULL),
(21,2,21,'2023-02-13 00:04:31.528000','2023-02-13 00:04:31.557000','2023-02-13 00:04:33.283000','COMPLETED','COMPLETED','','2023-02-13 00:04:33.284000',NULL),
(22,2,22,'2023-02-13 04:00:00.010000','2023-02-13 04:00:00.017000','2023-02-13 04:00:00.219000','COMPLETED','COMPLETED','','2023-02-13 04:00:00.220000',NULL),
(23,2,23,'2023-02-13 09:19:58.699000','2023-02-13 09:19:58.828000','2023-02-13 09:20:03.074000','COMPLETED','COMPLETED','','2023-02-13 09:20:03.076000',NULL),
(24,2,24,'2023-02-13 09:20:03.104000','2023-02-13 09:20:03.116000','2023-02-13 09:20:04.540000','COMPLETED','COMPLETED','','2023-02-13 09:20:04.540000',NULL),
(25,2,25,'2023-02-13 09:29:59.153000','2023-02-13 09:29:59.218000','2023-02-13 09:30:03.567000','COMPLETED','COMPLETED','','2023-02-13 09:30:03.568000',NULL),
(26,2,26,'2023-02-13 09:30:03.640000','2023-02-13 09:30:03.658000','2023-02-13 09:30:04.619000','COMPLETED','COMPLETED','','2023-02-13 09:30:04.620000',NULL),
(27,2,27,'2023-02-13 10:16:03.367000','2023-02-13 10:16:03.443000','2023-02-13 10:16:07.805000','COMPLETED','COMPLETED','','2023-02-13 10:16:07.806000',NULL),
(28,2,28,'2023-02-13 10:16:07.828000','2023-02-13 10:16:07.837000','2023-02-13 10:16:08.754000','COMPLETED','COMPLETED','','2023-02-13 10:16:08.755000',NULL),
(29,2,29,'2023-02-13 10:21:32.322000','2023-02-13 10:21:32.437000','2023-02-13 10:21:36.819000','FAILED','FAILED','org.springframework.data.redis.RedisConnectionFailureException: Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to redis_boot:6379\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.translateException(LettuceConnectionFactory.java:1689)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1597)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getNativeConnection(LettuceConnectionFactory.java:1383)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getConnection(LettuceConnectionFactory.java:1366)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getSharedConnection(LettuceConnectionFactory.java:1093)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getConnection(LettuceConnectionFactory.java:421)\n	at org.springframework.data.redis.core.RedisConnectionUtils.fetchConnection(RedisConnectionUtils.java:193)\n	at org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection(RedisConnectionUtils.java:144)\n	at org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:105)\n	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:211)\n	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:191)\n	at org.springframework.data.redis.core.AbstractOperations.execute(AbstractOperations.java:97)\n	at org.springframework.data.redis.core.DefaultValueOperations.set(DefaultValueOperations.java:305)\n	at com.ssafy.campinity.core.repository.redis.RedisDao.setValues(RedisDao.java:21)\n	at com.ssafy.campinity.demo.batch.job.HottestCampsiteConfig$1.execute(HottestCampsiteConfig.java:80)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(R','2023-02-13 10:21:36.828000',NULL),
(30,2,30,'2023-02-13 10:21:36.880000','2023-02-13 10:21:36.895000','2023-02-13 10:21:38.286000','FAILED','FAILED','org.springframework.data.redis.RedisConnectionFailureException: Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to redis_boot:6379\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.translateException(LettuceConnectionFactory.java:1689)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1597)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getNativeConnection(LettuceConnectionFactory.java:1383)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getConnection(LettuceConnectionFactory.java:1366)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getSharedConnection(LettuceConnectionFactory.java:1093)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getConnection(LettuceConnectionFactory.java:421)\n	at org.springframework.data.redis.core.RedisConnectionUtils.fetchConnection(RedisConnectionUtils.java:193)\n	at org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection(RedisConnectionUtils.java:144)\n	at org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:105)\n	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:211)\n	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:191)\n	at org.springframework.data.redis.core.AbstractOperations.execute(AbstractOperations.java:97)\n	at org.springframework.data.redis.core.DefaultValueOperations.set(DefaultValueOperations.java:305)\n	at com.ssafy.campinity.core.repository.redis.RedisDao.setValues(RedisDao.java:21)\n	at com.ssafy.campinity.demo.batch.job.HighestScoredCampsiteConfig$1.execute(HighestScoredCampsiteConfig.java:70)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.invok','2023-02-13 10:21:38.288000',NULL),
(31,2,31,'2023-02-13 10:23:45.600000','2023-02-13 10:23:45.743000','2023-02-13 10:23:50.421000','COMPLETED','COMPLETED','','2023-02-13 10:23:50.423000',NULL),
(32,2,32,'2023-02-13 10:23:50.459000','2023-02-13 10:23:50.476000','2023-02-13 10:23:51.846000','COMPLETED','COMPLETED','','2023-02-13 10:23:51.847000',NULL),
(33,2,33,'2023-02-13 10:40:53.371000','2023-02-13 10:40:53.466000','2023-02-13 10:40:57.449000','COMPLETED','COMPLETED','','2023-02-13 10:40:57.450000',NULL),
(34,2,34,'2023-02-13 10:40:57.479000','2023-02-13 10:40:57.488000','2023-02-13 10:40:58.704000','COMPLETED','COMPLETED','','2023-02-13 10:40:58.704000',NULL),
(35,2,35,'2023-02-13 15:14:25.887000','2023-02-13 15:14:25.978000','2023-02-13 15:14:30.582000','COMPLETED','COMPLETED','','2023-02-13 15:14:30.585000',NULL),
(36,2,36,'2023-02-13 15:14:30.640000','2023-02-13 15:14:30.653000','2023-02-13 15:14:31.997000','COMPLETED','COMPLETED','','2023-02-13 15:14:31.998000',NULL),
(37,2,37,'2023-02-13 17:04:53.108000','2023-02-13 17:04:53.243000','2023-02-13 17:04:57.475000','COMPLETED','COMPLETED','','2023-02-13 17:04:57.484000',NULL),
(38,2,38,'2023-02-13 17:04:57.528000','2023-02-13 17:04:57.541000','2023-02-13 17:04:59.326000','COMPLETED','COMPLETED','','2023-02-13 17:04:59.327000',NULL),
(39,2,39,'2023-02-13 17:21:02.947000','2023-02-13 17:21:03.061000','2023-02-13 17:21:07.643000','COMPLETED','COMPLETED','','2023-02-13 17:21:07.648000',NULL),
(40,2,40,'2023-02-13 17:21:07.688000','2023-02-13 17:21:07.705000','2023-02-13 17:21:09.348000','COMPLETED','COMPLETED','','2023-02-13 17:21:09.349000',NULL),
(41,2,41,'2023-02-14 00:16:18.768000','2023-02-14 00:16:18.860000','2023-02-14 00:16:23.296000','COMPLETED','COMPLETED','','2023-02-14 00:16:23.302000',NULL),
(42,2,42,'2023-02-14 00:16:23.352000','2023-02-14 00:16:23.369000','2023-02-14 00:16:24.816000','COMPLETED','COMPLETED','','2023-02-14 00:16:24.817000',NULL),
(43,2,43,'2023-02-14 04:00:00.010000','2023-02-14 04:00:00.022000','2023-02-14 04:00:00.214000','COMPLETED','COMPLETED','','2023-02-14 04:00:00.214000',NULL),
(44,2,44,'2023-02-14 04:05:00.011000','2023-02-14 04:05:00.022000','2023-02-14 04:05:00.997000','COMPLETED','COMPLETED','','2023-02-14 04:05:00.998000',NULL),
(45,2,45,'2023-02-14 04:10:00.008000','2023-02-14 04:10:00.017000','2023-02-14 04:10:00.716000','COMPLETED','COMPLETED','','2023-02-14 04:10:00.716000',NULL),
(46,2,46,'2023-02-14 04:20:00.009000','2023-02-14 04:20:00.017000','2023-02-14 04:20:00.431000','COMPLETED','COMPLETED','','2023-02-14 04:20:00.432000',NULL),
(47,2,47,'2023-02-14 04:25:00.009000','2023-02-14 04:25:00.017000','2023-02-14 04:25:00.068000','COMPLETED','COMPLETED','','2023-02-14 04:25:00.068000',NULL),
(48,2,48,'2023-02-14 04:25:00.078000','2023-02-14 04:25:00.084000','2023-02-14 04:25:00.100000','FAILED','FAILED','org.springframework.beans.factory.support.ScopeNotActiveException: Error creating bean with name \'scopedTarget.FcmMeesageDeleteStep\': Scope \'step\' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton; nested exception is java.lang.IllegalStateException: No context holder available for step scope\n	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:383)\n	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)\n	at org.springframework.aop.target.SimpleBeanTargetSource.getTarget(SimpleBeanTargetSource.java:35)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:195)\n	at com.sun.proxy.$Proxy218.getName(Unknown Source)\n	at org.springframework.batch.core.job.SimpleStepHandler.handleStep(SimpleStepHandler.java:115)\n	at org.springframework.batch.core.job.AbstractJob.handleStep(AbstractJob.java:413)\n	at org.springframework.batch.core.job.SimpleJob.doExecute(SimpleJob.java:136)\n	at org.springframework.batch.core.job.AbstractJob.execute(AbstractJob.java:320)\n	at org.springframework.batch.core.launch.support.SimpleJobLauncher$1.run(SimpleJobLauncher.java:149)\n	at org.springframework.core.task.SyncTaskExecutor.execute(SyncTaskExecutor.java:50)\n	at org.springframework.batch.core.launch.support.SimpleJobLauncher.run(SimpleJobLauncher.java:140)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n	at org.springframework.batch.core.configuration.annotation.SimpleBatchConfiguration$PassthruAdvice.invoke(SimpleBatchConfiguration.java:128)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215)\n	at com.sun.proxy.$','2023-02-14 04:25:00.100000',NULL),
(49,2,49,'2023-02-14 09:59:02.096000','2023-02-14 09:59:02.193000','2023-02-14 09:59:06.642000','COMPLETED','COMPLETED','','2023-02-14 09:59:06.643000',NULL),
(50,2,50,'2023-02-14 09:59:06.686000','2023-02-14 09:59:06.700000','2023-02-14 09:59:07.902000','COMPLETED','COMPLETED','','2023-02-14 09:59:07.902000',NULL),
(51,2,51,'2023-02-14 10:52:05.444000','2023-02-14 10:52:05.565000','2023-02-14 10:52:09.792000','COMPLETED','COMPLETED','','2023-02-14 10:52:09.793000',NULL),
(52,2,52,'2023-02-14 10:52:09.843000','2023-02-14 10:52:09.862000','2023-02-14 10:52:11.000000','COMPLETED','COMPLETED','','2023-02-14 10:52:11.001000',NULL),
(53,2,53,'2023-02-14 13:38:46.567000','2023-02-14 13:38:46.671000','2023-02-14 13:38:50.949000','COMPLETED','COMPLETED','','2023-02-14 13:38:50.951000',NULL),
(54,2,54,'2023-02-14 13:38:50.987000','2023-02-14 13:38:50.998000','2023-02-14 13:38:52.626000','COMPLETED','COMPLETED','','2023-02-14 13:38:52.626000',NULL),
(55,2,55,'2023-02-14 21:16:09.053000','2023-02-14 21:16:09.131000','2023-02-14 21:16:11.437000','COMPLETED','COMPLETED','','2023-02-14 21:16:11.438000',NULL),
(56,2,56,'2023-02-14 21:16:11.456000','2023-02-14 21:16:11.463000','2023-02-14 21:16:12.427000','COMPLETED','COMPLETED','','2023-02-14 21:16:12.427000',NULL),
(57,2,57,'2023-02-14 21:56:30.072000','2023-02-14 21:56:30.170000','2023-02-14 21:56:33.182000','COMPLETED','COMPLETED','','2023-02-14 21:56:33.182000',NULL),
(58,2,58,'2023-02-14 21:56:33.200000','2023-02-14 21:56:33.208000','2023-02-14 21:56:34.177000','COMPLETED','COMPLETED','','2023-02-14 21:56:34.178000',NULL),
(59,2,59,'2023-02-14 21:58:10.408000','2023-02-14 21:58:10.489000','2023-02-14 21:58:14.672000','COMPLETED','COMPLETED','','2023-02-14 21:58:14.673000',NULL),
(60,2,60,'2023-02-14 21:58:14.704000','2023-02-14 21:58:14.715000','2023-02-14 21:58:16.062000','COMPLETED','COMPLETED','','2023-02-14 21:58:16.063000',NULL),
(61,2,61,'2023-02-14 22:55:46.154000','2023-02-14 22:55:46.263000','2023-02-14 22:55:51.530000','COMPLETED','COMPLETED','','2023-02-14 22:55:51.533000',NULL),
(62,2,62,'2023-02-14 22:55:51.610000','2023-02-14 22:55:51.629000','2023-02-14 22:55:53.209000','COMPLETED','COMPLETED','','2023-02-14 22:55:53.210000',NULL),
(63,2,63,'2023-02-15 03:38:10.919000','2023-02-15 03:38:11.012000','2023-02-15 03:38:15.661000','COMPLETED','COMPLETED','','2023-02-15 03:38:15.672000',NULL),
(64,2,64,'2023-02-15 03:38:15.726000','2023-02-15 03:38:15.736000','2023-02-15 03:38:18.041000','COMPLETED','COMPLETED','','2023-02-15 03:38:18.042000',NULL),
(65,2,65,'2023-02-15 09:10:12.365000','2023-02-15 09:10:12.427000','2023-02-15 09:10:14.934000','COMPLETED','COMPLETED','','2023-02-15 09:10:14.935000',NULL),
(66,2,66,'2023-02-15 09:10:14.958000','2023-02-15 09:10:14.967000','2023-02-15 09:10:15.812000','COMPLETED','COMPLETED','','2023-02-15 09:10:15.813000',NULL),
(67,2,67,'2023-02-15 09:25:42.291000','2023-02-15 09:25:42.380000','2023-02-15 09:25:47.627000','COMPLETED','COMPLETED','','2023-02-15 09:25:47.627000',NULL),
(68,2,68,'2023-02-15 09:25:47.673000','2023-02-15 09:25:47.690000','2023-02-15 09:25:48.978000','COMPLETED','COMPLETED','','2023-02-15 09:25:48.979000',NULL),
(69,2,69,'2023-02-15 11:19:10.598000','2023-02-15 11:19:10.739000','2023-02-15 11:19:16.484000','COMPLETED','COMPLETED','','2023-02-15 11:19:16.492000',NULL),
(70,2,70,'2023-02-15 11:19:16.530000','2023-02-15 11:19:16.540000','2023-02-15 11:19:17.762000','COMPLETED','COMPLETED','','2023-02-15 11:19:17.763000',NULL),
(71,2,71,'2023-02-15 11:33:41.691000','2023-02-15 11:33:41.830000','2023-02-15 11:33:46.330000','COMPLETED','COMPLETED','','2023-02-15 11:33:46.331000',NULL),
(72,2,72,'2023-02-15 11:33:46.378000','2023-02-15 11:33:46.389000','2023-02-15 11:33:48.037000','COMPLETED','COMPLETED','','2023-02-15 11:33:48.037000',NULL),
(73,2,73,'2023-02-15 15:10:13.623000','2023-02-15 15:10:13.703000','2023-02-15 15:10:18.626000','COMPLETED','COMPLETED','','2023-02-15 15:10:18.627000',NULL),
(74,2,74,'2023-02-15 15:10:18.715000','2023-02-15 15:10:18.745000','2023-02-15 15:10:20.935000','COMPLETED','COMPLETED','','2023-02-15 15:10:20.935000',NULL),
(75,2,75,'2023-02-15 15:37:06.572000','2023-02-15 15:37:06.724000','2023-02-15 15:37:12.727000','COMPLETED','COMPLETED','','2023-02-15 15:37:12.732000',NULL),
(76,2,76,'2023-02-15 15:37:12.808000','2023-02-15 15:37:12.832000','2023-02-15 15:37:15.520000','COMPLETED','COMPLETED','','2023-02-15 15:37:15.521000',NULL),
(77,2,77,'2023-02-15 15:38:01.018000','2023-02-15 15:38:01.124000','2023-02-15 15:38:05.475000','COMPLETED','COMPLETED','','2023-02-15 15:38:05.477000',NULL),
(78,2,78,'2023-02-15 15:38:05.537000','2023-02-15 15:38:05.565000','2023-02-15 15:38:07.585000','COMPLETED','COMPLETED','','2023-02-15 15:38:07.586000',NULL),
(79,2,79,'2023-02-15 16:00:00.024000','2023-02-15 16:00:00.087000','2023-02-15 16:00:02.326000','COMPLETED','COMPLETED','','2023-02-15 16:00:02.327000',NULL),
(80,2,80,'2023-02-15 16:00:02.348000','2023-02-15 16:00:02.358000','2023-02-15 16:00:03.324000','COMPLETED','COMPLETED','','2023-02-15 16:00:03.325000',NULL),
(81,2,81,'2023-02-15 16:08:56.979000','2023-02-15 16:08:57.052000','2023-02-15 16:08:59.443000','COMPLETED','COMPLETED','','2023-02-15 16:08:59.444000',NULL),
(82,2,82,'2023-02-15 16:08:59.464000','2023-02-15 16:08:59.474000','2023-02-15 16:09:00.473000','COMPLETED','COMPLETED','','2023-02-15 16:09:00.474000',NULL),
(83,2,83,'2023-02-15 16:33:27.885000','2023-02-15 16:33:27.946000','2023-02-15 16:33:30.488000','COMPLETED','COMPLETED','','2023-02-15 16:33:30.488000',NULL),
(84,2,84,'2023-02-15 16:33:30.508000','2023-02-15 16:33:30.515000','2023-02-15 16:33:31.403000','COMPLETED','COMPLETED','','2023-02-15 16:33:31.403000',NULL),
(85,2,85,'2023-02-15 16:45:41.151000','2023-02-15 16:45:41.223000','2023-02-15 16:45:43.590000','COMPLETED','COMPLETED','','2023-02-15 16:45:43.591000',NULL),
(86,2,86,'2023-02-15 16:45:43.609000','2023-02-15 16:45:43.618000','2023-02-15 16:45:44.490000','COMPLETED','COMPLETED','','2023-02-15 16:45:44.491000',NULL),
(87,2,87,'2023-02-15 17:22:05.819000','2023-02-15 17:22:05.918000','2023-02-15 17:22:10.526000','COMPLETED','COMPLETED','','2023-02-15 17:22:10.532000',NULL),
(88,2,88,'2023-02-15 17:22:10.578000','2023-02-15 17:22:10.600000','2023-02-15 17:22:12.614000','COMPLETED','COMPLETED','','2023-02-15 17:22:12.615000',NULL),
(89,2,89,'2023-02-15 17:23:33.416000','2023-02-15 17:23:33.565000','2023-02-15 17:23:37.526000','COMPLETED','COMPLETED','','2023-02-15 17:23:37.527000',NULL),
(90,2,90,'2023-02-15 17:23:37.565000','2023-02-15 17:23:37.574000','2023-02-15 17:23:39.453000','COMPLETED','COMPLETED','','2023-02-15 17:23:39.453000',NULL),
(91,2,91,'2023-02-16 02:53:22.894000','2023-02-16 02:53:23.023000','2023-02-16 02:53:28.344000','COMPLETED','COMPLETED','','2023-02-16 02:53:28.345000',NULL),
(92,2,92,'2023-02-16 02:53:28.368000','2023-02-16 02:53:28.390000','2023-02-16 02:53:30.141000','COMPLETED','COMPLETED','','2023-02-16 02:53:30.142000',NULL),
(93,2,93,'2023-02-16 03:35:47.742000','2023-02-16 03:35:47.862000','2023-02-16 03:35:52.484000','COMPLETED','COMPLETED','','2023-02-16 03:35:52.486000',NULL),
(94,2,94,'2023-02-16 03:35:52.522000','2023-02-16 03:35:52.546000','2023-02-16 03:35:54.067000','COMPLETED','COMPLETED','','2023-02-16 03:35:54.068000',NULL),
(95,2,95,'2023-02-16 03:50:46.033000','2023-02-16 03:50:46.154000','2023-02-16 03:50:50.862000','COMPLETED','COMPLETED','','2023-02-16 03:50:50.863000',NULL),
(96,2,96,'2023-02-16 03:50:50.903000','2023-02-16 03:50:50.914000','2023-02-16 03:50:52.473000','COMPLETED','COMPLETED','','2023-02-16 03:50:52.475000',NULL),
(97,2,97,'2023-02-16 04:00:00.013000','2023-02-16 04:00:00.027000','2023-02-16 04:00:00.394000','COMPLETED','COMPLETED','','2023-02-16 04:00:00.395000',NULL),
(98,2,98,'2023-02-16 04:03:51.310000','2023-02-16 04:03:51.442000','2023-02-16 04:03:55.685000','COMPLETED','COMPLETED','','2023-02-16 04:03:55.687000',NULL),
(99,2,99,'2023-02-16 04:03:55.732000','2023-02-16 04:03:55.744000','2023-02-16 04:03:57.973000','COMPLETED','COMPLETED','','2023-02-16 04:03:57.974000',NULL),
(100,2,100,'2023-02-16 04:25:00.010000','2023-02-16 04:25:00.025000','2023-02-16 04:25:00.427000','COMPLETED','COMPLETED','','2023-02-16 04:25:00.427000',NULL),
(101,2,101,'2023-02-16 15:51:48.410000','2023-02-16 15:51:48.493000','2023-02-16 15:51:52.866000','COMPLETED','COMPLETED','','2023-02-16 15:51:52.868000',NULL),
(102,2,102,'2023-02-16 15:51:52.906000','2023-02-16 15:51:52.916000','2023-02-16 15:51:54.862000','COMPLETED','COMPLETED','','2023-02-16 15:51:54.863000',NULL);
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_JOB_EXECUTION_CONTEXT`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_CONTEXT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_CONTEXT` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_JOB_EXECUTION_CONTEXT`
--

LOCK TABLES `BATCH_JOB_EXECUTION_CONTEXT` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION_CONTEXT` VALUES
(1,'{\"@class\":\"java.util.HashMap\"}',NULL),
(2,'{\"@class\":\"java.util.HashMap\"}',NULL),
(3,'{\"@class\":\"java.util.HashMap\"}',NULL),
(4,'{\"@class\":\"java.util.HashMap\"}',NULL),
(5,'{\"@class\":\"java.util.HashMap\"}',NULL),
(6,'{\"@class\":\"java.util.HashMap\"}',NULL),
(7,'{\"@class\":\"java.util.HashMap\"}',NULL),
(8,'{\"@class\":\"java.util.HashMap\"}',NULL),
(9,'{\"@class\":\"java.util.HashMap\"}',NULL),
(10,'{\"@class\":\"java.util.HashMap\"}',NULL),
(11,'{\"@class\":\"java.util.HashMap\"}',NULL),
(12,'{\"@class\":\"java.util.HashMap\"}',NULL),
(13,'{\"@class\":\"java.util.HashMap\"}',NULL),
(14,'{\"@class\":\"java.util.HashMap\"}',NULL),
(15,'{\"@class\":\"java.util.HashMap\"}',NULL),
(16,'{\"@class\":\"java.util.HashMap\"}',NULL),
(17,'{\"@class\":\"java.util.HashMap\"}',NULL),
(18,'{\"@class\":\"java.util.HashMap\"}',NULL),
(19,'{\"@class\":\"java.util.HashMap\"}',NULL),
(20,'{\"@class\":\"java.util.HashMap\"}',NULL),
(21,'{\"@class\":\"java.util.HashMap\"}',NULL),
(22,'{\"@class\":\"java.util.HashMap\"}',NULL),
(23,'{\"@class\":\"java.util.HashMap\"}',NULL),
(24,'{\"@class\":\"java.util.HashMap\"}',NULL),
(25,'{\"@class\":\"java.util.HashMap\"}',NULL),
(26,'{\"@class\":\"java.util.HashMap\"}',NULL),
(27,'{\"@class\":\"java.util.HashMap\"}',NULL),
(28,'{\"@class\":\"java.util.HashMap\"}',NULL),
(29,'{\"@class\":\"java.util.HashMap\"}',NULL),
(30,'{\"@class\":\"java.util.HashMap\"}',NULL),
(31,'{\"@class\":\"java.util.HashMap\"}',NULL),
(32,'{\"@class\":\"java.util.HashMap\"}',NULL),
(33,'{\"@class\":\"java.util.HashMap\"}',NULL),
(34,'{\"@class\":\"java.util.HashMap\"}',NULL),
(35,'{\"@class\":\"java.util.HashMap\"}',NULL),
(36,'{\"@class\":\"java.util.HashMap\"}',NULL),
(37,'{\"@class\":\"java.util.HashMap\"}',NULL),
(38,'{\"@class\":\"java.util.HashMap\"}',NULL),
(39,'{\"@class\":\"java.util.HashMap\"}',NULL),
(40,'{\"@class\":\"java.util.HashMap\"}',NULL),
(41,'{\"@class\":\"java.util.HashMap\"}',NULL),
(42,'{\"@class\":\"java.util.HashMap\"}',NULL),
(43,'{\"@class\":\"java.util.HashMap\"}',NULL),
(44,'{\"@class\":\"java.util.HashMap\"}',NULL),
(45,'{\"@class\":\"java.util.HashMap\"}',NULL),
(46,'{\"@class\":\"java.util.HashMap\"}',NULL),
(47,'{\"@class\":\"java.util.HashMap\"}',NULL),
(48,'{\"@class\":\"java.util.HashMap\"}',NULL),
(49,'{\"@class\":\"java.util.HashMap\"}',NULL),
(50,'{\"@class\":\"java.util.HashMap\"}',NULL),
(51,'{\"@class\":\"java.util.HashMap\"}',NULL),
(52,'{\"@class\":\"java.util.HashMap\"}',NULL),
(53,'{\"@class\":\"java.util.HashMap\"}',NULL),
(54,'{\"@class\":\"java.util.HashMap\"}',NULL),
(55,'{\"@class\":\"java.util.HashMap\"}',NULL),
(56,'{\"@class\":\"java.util.HashMap\"}',NULL),
(57,'{\"@class\":\"java.util.HashMap\"}',NULL),
(58,'{\"@class\":\"java.util.HashMap\"}',NULL),
(59,'{\"@class\":\"java.util.HashMap\"}',NULL),
(60,'{\"@class\":\"java.util.HashMap\"}',NULL),
(61,'{\"@class\":\"java.util.HashMap\"}',NULL),
(62,'{\"@class\":\"java.util.HashMap\"}',NULL),
(63,'{\"@class\":\"java.util.HashMap\"}',NULL),
(64,'{\"@class\":\"java.util.HashMap\"}',NULL),
(65,'{\"@class\":\"java.util.HashMap\"}',NULL),
(66,'{\"@class\":\"java.util.HashMap\"}',NULL),
(67,'{\"@class\":\"java.util.HashMap\"}',NULL),
(68,'{\"@class\":\"java.util.HashMap\"}',NULL),
(69,'{\"@class\":\"java.util.HashMap\"}',NULL),
(70,'{\"@class\":\"java.util.HashMap\"}',NULL),
(71,'{\"@class\":\"java.util.HashMap\"}',NULL),
(72,'{\"@class\":\"java.util.HashMap\"}',NULL),
(73,'{\"@class\":\"java.util.HashMap\"}',NULL),
(74,'{\"@class\":\"java.util.HashMap\"}',NULL),
(75,'{\"@class\":\"java.util.HashMap\"}',NULL),
(76,'{\"@class\":\"java.util.HashMap\"}',NULL),
(77,'{\"@class\":\"java.util.HashMap\"}',NULL),
(78,'{\"@class\":\"java.util.HashMap\"}',NULL),
(79,'{\"@class\":\"java.util.HashMap\"}',NULL),
(80,'{\"@class\":\"java.util.HashMap\"}',NULL),
(81,'{\"@class\":\"java.util.HashMap\"}',NULL),
(82,'{\"@class\":\"java.util.HashMap\"}',NULL),
(83,'{\"@class\":\"java.util.HashMap\"}',NULL),
(84,'{\"@class\":\"java.util.HashMap\"}',NULL),
(85,'{\"@class\":\"java.util.HashMap\"}',NULL),
(86,'{\"@class\":\"java.util.HashMap\"}',NULL),
(87,'{\"@class\":\"java.util.HashMap\"}',NULL),
(88,'{\"@class\":\"java.util.HashMap\"}',NULL),
(89,'{\"@class\":\"java.util.HashMap\"}',NULL),
(90,'{\"@class\":\"java.util.HashMap\"}',NULL),
(91,'{\"@class\":\"java.util.HashMap\"}',NULL),
(92,'{\"@class\":\"java.util.HashMap\"}',NULL),
(93,'{\"@class\":\"java.util.HashMap\"}',NULL),
(94,'{\"@class\":\"java.util.HashMap\"}',NULL),
(95,'{\"@class\":\"java.util.HashMap\"}',NULL),
(96,'{\"@class\":\"java.util.HashMap\"}',NULL),
(97,'{\"@class\":\"java.util.HashMap\"}',NULL),
(98,'{\"@class\":\"java.util.HashMap\"}',NULL),
(99,'{\"@class\":\"java.util.HashMap\"}',NULL),
(100,'{\"@class\":\"java.util.HashMap\"}',NULL),
(101,'{\"@class\":\"java.util.HashMap\"}',NULL),
(102,'{\"@class\":\"java.util.HashMap\"}',NULL);
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_CONTEXT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_JOB_EXECUTION_PARAMS`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_PARAMS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_PARAMS` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `TYPE_CD` varchar(6) NOT NULL,
  `KEY_NAME` varchar(100) NOT NULL,
  `STRING_VAL` varchar(250) DEFAULT NULL,
  `DATE_VAL` datetime(6) DEFAULT NULL,
  `LONG_VAL` bigint(20) DEFAULT NULL,
  `DOUBLE_VAL` double DEFAULT NULL,
  `IDENTIFYING` char(1) NOT NULL,
  KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_JOB_EXECUTION_PARAMS`
--

LOCK TABLES `BATCH_JOB_EXECUTION_PARAMS` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION_PARAMS` VALUES
(1,'LONG','time','','1970-01-01 00:00:00.000000',1676108630514,0,'Y'),
(2,'LONG','time','','1970-01-01 00:00:00.000000',1676108634246,0,'Y'),
(3,'LONG','time','','1970-01-01 00:00:00.000000',1676174400000,0,'Y'),
(4,'LONG','time','','1970-01-01 00:00:00.000000',1676181334518,0,'Y'),
(5,'LONG','time','','1970-01-01 00:00:00.000000',1676181337284,0,'Y'),
(6,'LONG','time','','1970-01-01 00:00:00.000000',1676183725901,0,'Y'),
(7,'LONG','time','','1970-01-01 00:00:00.000000',1676183728652,0,'Y'),
(8,'LONG','time','','1970-01-01 00:00:00.000000',1676186493933,0,'Y'),
(9,'LONG','time','','1970-01-01 00:00:00.000000',1676186498690,0,'Y'),
(10,'LONG','time','','1970-01-01 09:00:00.000000',1676190470587,0,'Y'),
(11,'LONG','time','','1970-01-01 09:00:00.000000',1676190475672,0,'Y'),
(12,'LONG','time','','1970-01-01 09:00:00.000000',1676192438018,0,'Y'),
(13,'LONG','time','','1970-01-01 09:00:00.000000',1676192442771,0,'Y'),
(14,'LONG','time','','1970-01-01 09:00:00.000000',1676195659560,0,'Y'),
(15,'LONG','time','','1970-01-01 09:00:00.000000',1676195665424,0,'Y'),
(16,'LONG','time','','1970-01-01 00:00:00.000000',1676198810649,0,'Y'),
(17,'LONG','time','','1970-01-01 00:00:00.000000',1676198815425,0,'Y'),
(18,'LONG','time','','1970-01-01 09:00:00.000000',1676198911722,0,'Y'),
(19,'LONG','time','','1970-01-01 09:00:00.000000',1676198916626,0,'Y'),
(20,'LONG','time','','1970-01-01 09:00:00.000000',1676214266112,0,'Y'),
(21,'LONG','time','','1970-01-01 09:00:00.000000',1676214271476,0,'Y'),
(22,'LONG','time','','1970-01-01 09:00:00.000000',1676228400001,0,'Y'),
(23,'LONG','time','','1970-01-01 09:00:00.000000',1676247598393,0,'Y'),
(24,'LONG','time','','1970-01-01 09:00:00.000000',1676247603083,0,'Y'),
(25,'LONG','time','','1970-01-01 09:00:00.000000',1676248198877,0,'Y'),
(26,'LONG','time','','1970-01-01 09:00:00.000000',1676248203593,0,'Y'),
(27,'LONG','time','','1970-01-01 09:00:00.000000',1676250962987,0,'Y'),
(28,'LONG','time','','1970-01-01 09:00:00.000000',1676250967810,0,'Y'),
(29,'LONG','time','','1970-01-01 09:00:00.000000',1676251292029,0,'Y'),
(30,'LONG','time','','1970-01-01 09:00:00.000000',1676251296843,0,'Y'),
(31,'LONG','time','','1970-01-01 09:00:00.000000',1676251425308,0,'Y'),
(32,'LONG','time','','1970-01-01 09:00:00.000000',1676251430432,0,'Y'),
(33,'LONG','time','','1970-01-01 09:00:00.000000',1676252453089,0,'Y'),
(34,'LONG','time','','1970-01-01 09:00:00.000000',1676252457453,0,'Y'),
(35,'LONG','time','','1970-01-01 09:00:00.000000',1676268865632,0,'Y'),
(36,'LONG','time','','1970-01-01 09:00:00.000000',1676268870595,0,'Y'),
(37,'LONG','time','','1970-01-01 09:00:00.000000',1676275492852,0,'Y'),
(38,'LONG','time','','1970-01-01 09:00:00.000000',1676275497489,0,'Y'),
(39,'LONG','time','','1970-01-01 09:00:00.000000',1676276462755,0,'Y'),
(40,'LONG','time','','1970-01-01 09:00:00.000000',1676276467653,0,'Y'),
(41,'LONG','time','','1970-01-01 09:00:00.000000',1676301378493,0,'Y'),
(42,'LONG','time','','1970-01-01 09:00:00.000000',1676301383313,0,'Y'),
(43,'LONG','time','','1970-01-01 09:00:00.000000',1676314800001,0,'Y'),
(44,'LONG','time','','1970-01-01 09:00:00.000000',1676315100001,0,'Y'),
(45,'LONG','time','','1970-01-01 09:00:00.000000',1676315400000,0,'Y'),
(46,'LONG','time','','1970-01-01 09:00:00.000000',1676316000000,0,'Y'),
(47,'LONG','time','','1970-01-01 09:00:00.000000',1676316300000,0,'Y'),
(48,'LONG','time','','1970-01-01 09:00:00.000000',1676316300072,0,'Y'),
(49,'LONG','time','','1970-01-01 09:00:00.000000',1676336341816,0,'Y'),
(50,'LONG','time','','1970-01-01 09:00:00.000000',1676336346656,0,'Y'),
(51,'LONG','time','','1970-01-01 09:00:00.000000',1676339525129,0,'Y'),
(52,'LONG','time','','1970-01-01 09:00:00.000000',1676339529807,0,'Y'),
(53,'LONG','time','','1970-01-01 09:00:00.000000',1676349526227,0,'Y'),
(54,'LONG','time','','1970-01-01 09:00:00.000000',1676349530959,0,'Y'),
(55,'LONG','time','','1970-01-01 09:00:00.000000',1676376968851,0,'Y'),
(56,'LONG','time','','1970-01-01 09:00:00.000000',1676376971442,0,'Y'),
(57,'LONG','time','','1970-01-01 09:00:00.000000',1676379389846,0,'Y'),
(58,'LONG','time','','1970-01-01 09:00:00.000000',1676379393186,0,'Y'),
(59,'LONG','time','','1970-01-01 09:00:00.000000',1676379490208,0,'Y'),
(60,'LONG','time','','1970-01-01 09:00:00.000000',1676379494682,0,'Y'),
(61,'LONG','time','','1970-01-01 09:00:00.000000',1676382945971,0,'Y'),
(62,'LONG','time','','1970-01-01 09:00:00.000000',1676382951553,0,'Y'),
(63,'LONG','time','','1970-01-01 09:00:00.000000',1676399890697,0,'Y'),
(64,'LONG','time','','1970-01-01 09:00:00.000000',1676399895686,0,'Y'),
(65,'LONG','time','','1970-01-01 09:00:00.000000',1676419812227,0,'Y'),
(66,'LONG','time','','1970-01-01 09:00:00.000000',1676419814942,0,'Y'),
(67,'LONG','time','','1970-01-01 09:00:00.000000',1676420742007,0,'Y'),
(68,'LONG','time','','1970-01-01 09:00:00.000000',1676420747641,0,'Y'),
(69,'LONG','time','','1970-01-01 09:00:00.000000',1676427550328,0,'Y'),
(70,'LONG','time','','1970-01-01 09:00:00.000000',1676427556500,0,'Y'),
(71,'LONG','time','','1970-01-01 09:00:00.000000',1676428421435,0,'Y'),
(72,'LONG','time','','1970-01-01 09:00:00.000000',1676428426341,0,'Y'),
(73,'LONG','time','','1970-01-01 09:00:00.000000',1676441413431,0,'Y'),
(74,'LONG','time','','1970-01-01 09:00:00.000000',1676441418641,0,'Y'),
(75,'LONG','time','','1970-01-01 09:00:00.000000',1676443026214,0,'Y'),
(76,'LONG','time','','1970-01-01 09:00:00.000000',1676443032753,0,'Y'),
(77,'LONG','time','','1970-01-01 09:00:00.000000',1676443080809,0,'Y'),
(78,'LONG','time','','1970-01-01 09:00:00.000000',1676443085491,0,'Y'),
(79,'LONG','time','','1970-01-01 09:00:00.000000',1676444399878,0,'Y'),
(80,'LONG','time','','1970-01-01 09:00:00.000000',1676444402332,0,'Y'),
(81,'LONG','time','','1970-01-01 09:00:00.000000',1676444936825,0,'Y'),
(82,'LONG','time','','1970-01-01 09:00:00.000000',1676444939448,0,'Y'),
(83,'LONG','time','','1970-01-01 09:00:00.000000',1676446407745,0,'Y'),
(84,'LONG','time','','1970-01-01 09:00:00.000000',1676446410492,0,'Y'),
(85,'LONG','time','','1970-01-01 09:00:00.000000',1676447140998,0,'Y'),
(86,'LONG','time','','1970-01-01 09:00:00.000000',1676447143595,0,'Y'),
(87,'LONG','time','','1970-01-01 09:00:00.000000',1676449325602,0,'Y'),
(88,'LONG','time','','1970-01-01 09:00:00.000000',1676449330544,0,'Y'),
(89,'LONG','time','','1970-01-01 09:00:00.000000',1676449413146,0,'Y'),
(90,'LONG','time','','1970-01-01 09:00:00.000000',1676449417538,0,'Y'),
(91,'LONG','time','','1970-01-01 09:00:00.000000',1676483602646,0,'Y'),
(92,'LONG','time','','1970-01-01 09:00:00.000000',1676483608349,0,'Y'),
(93,'LONG','time','','1970-01-01 09:00:00.000000',1676486147503,0,'Y'),
(94,'LONG','time','','1970-01-01 09:00:00.000000',1676486152496,0,'Y'),
(95,'LONG','time','','1970-01-01 09:00:00.000000',1676487045784,0,'Y'),
(96,'LONG','time','','1970-01-01 09:00:00.000000',1676487050876,0,'Y'),
(97,'LONG','time','','1970-01-01 09:00:00.000000',1676487600001,0,'Y'),
(98,'LONG','time','','1970-01-01 09:00:00.000000',1676487831019,0,'Y'),
(99,'LONG','time','','1970-01-01 09:00:00.000000',1676487835694,0,'Y'),
(100,'LONG','time','','1970-01-01 09:00:00.000000',1676489100000,0,'Y'),
(101,'LONG','time','','1970-01-01 09:00:00.000000',1676530308176,0,'Y'),
(102,'LONG','time','','1970-01-01 09:00:00.000000',1676530312874,0,'Y');
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_PARAMS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_JOB_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_JOB_EXECUTION_SEQ`
--

LOCK TABLES `BATCH_JOB_EXECUTION_SEQ` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_EXECUTION_SEQ` VALUES
(102,'0');
/*!40000 ALTER TABLE `BATCH_JOB_EXECUTION_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_JOB_INSTANCE`
--

DROP TABLE IF EXISTS `BATCH_JOB_INSTANCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_INSTANCE` (
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_KEY` varchar(32) NOT NULL,
  PRIMARY KEY (`JOB_INSTANCE_ID`),
  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_JOB_INSTANCE`
--

LOCK TABLES `BATCH_JOB_INSTANCE` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_INSTANCE` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_INSTANCE` VALUES
(1,0,'HottestCampsiteJob','a1c89881707a5206702c23971b4071ed'),
(2,0,'HighestScoredCampsiteJob','fce8f919ae2615db681be5a7fb1881de'),
(3,0,'softDeleteEtcMessageJob','9d55bd9a5ac959e29779fed510342ca2'),
(4,0,'HottestCampsiteJob','15163f7873c94c901180cb276bf46592'),
(5,0,'HighestScoredCampsiteJob','d02ff55c0b9f0af1d2e9b3b59d339e2d'),
(6,0,'HottestCampsiteJob','a5eeb7b2edd899c2c6c6a40a85ab2cba'),
(7,0,'HighestScoredCampsiteJob','99199f165b8fb6f353597e3d28bf10df'),
(8,0,'HottestCampsiteJob','28ae49eba250a0927b17492a1cbc1ffa'),
(9,0,'HighestScoredCampsiteJob','f6ee7d1545103bb462f919240d35ee14'),
(10,0,'HottestCampsiteJob','6a8d0ca4fe00113b9a94008171d3b21a'),
(11,0,'HighestScoredCampsiteJob','ffc86df6dca6e4820ecebe47c3401a70'),
(12,0,'HottestCampsiteJob','fb6454829b6aee7f5e2e2f9aade07e2a'),
(13,0,'HighestScoredCampsiteJob','c62cef3294f5a838489cfa270e75664e'),
(14,0,'HottestCampsiteJob','f2a2e0cad303eac8e77f0923972dbb9f'),
(15,0,'HighestScoredCampsiteJob','3c0eccb79f91a52d5749450455d9e4be'),
(16,0,'HottestCampsiteJob','a70e9d4d30700667e4210e68663affa4'),
(17,0,'HighestScoredCampsiteJob','94956e740776119d6f4b06354209dc3c'),
(18,0,'HottestCampsiteJob','6e897f4147821c6643c279ba5c363f84'),
(19,0,'HighestScoredCampsiteJob','cbab179219c37dae3bda64b3bed8b2ff'),
(20,0,'HottestCampsiteJob','52b59ac66ebc20e5db59e297f8c8c67d'),
(21,0,'HighestScoredCampsiteJob','6ca48e0a1d63312a612e2f693aea9921'),
(22,0,'softDeleteEtcMessageJob','2aafe8c5e1c5069e846435c91bcf67a3'),
(23,0,'HottestCampsiteJob','3fb704e0ce816cec679d4068bb59d43b'),
(24,0,'HighestScoredCampsiteJob','ced6227927c1aec5f89e9de92ce321c1'),
(25,0,'HottestCampsiteJob','e55ebf511a41afbe79fa70a4ccda1e37'),
(26,0,'HighestScoredCampsiteJob','c6d75a5ec26cd85b7afaba71c2f11bad'),
(27,0,'HottestCampsiteJob','402e16c0a689af69244f426f60129bdb'),
(28,0,'HighestScoredCampsiteJob','e62c9b06a97bca07e4e6918e4b2b63bc'),
(29,0,'HottestCampsiteJob','efa19a79fa3cb3caf0eb27c96a6f7f55'),
(30,0,'HighestScoredCampsiteJob','40432a0461a8fbad2b621fda662d47d8'),
(31,0,'HottestCampsiteJob','c0e86d8c06124e98944ed14c8f8aa6e7'),
(32,0,'HighestScoredCampsiteJob','8db02b1b97e4de2cda65bcf00ce15090'),
(33,0,'HottestCampsiteJob','5097d3ae6afa619e9cdb15c6e5594a2f'),
(34,0,'HighestScoredCampsiteJob','d1ee422dc2111c25a1a127e83d805c05'),
(35,0,'HottestCampsiteJob','535a7d9ab97d2964adda7d4fb4c7ebf5'),
(36,0,'HighestScoredCampsiteJob','dfd738c656d364956c3a0d300c8b8343'),
(37,0,'HottestCampsiteJob','e01a2955c27d20310ea375b84bf5f19c'),
(38,0,'HighestScoredCampsiteJob','e665c975adbf5edff2a635bf12aa0629'),
(39,0,'HottestCampsiteJob','8b684034a2c20c1bdc3e2a99fb66cf84'),
(40,0,'HighestScoredCampsiteJob','3c60dea5dfcfb6938c39a170d5296f79'),
(41,0,'HottestCampsiteJob','10a93789a64aafc36e41cc8fada52f83'),
(42,0,'HighestScoredCampsiteJob','161e3e665559100dc88820461a666386'),
(43,0,'softDeleteEtcMessageJob','7a6b66bf72535d86f7d09ddd0f67aa39'),
(44,0,'HottestCampsiteJob','40059d2bf37236e98f3cb6612909ff83'),
(45,0,'HighestScoredCampsiteJob','00e9d58ecd3fe619711ef1e50d1ee98c'),
(46,0,'hardDeleteJob','2a324aedee776c9255832ea7681779fd'),
(47,0,'fcmTokenDeleteJob','45909a6a850dfc4478ac6cef180fff68'),
(48,0,'FcmMessageDeleteJob','1883fab457278ea9dcc55e936b32412d'),
(49,0,'HottestCampsiteJob','af2e111360588e7f0279dcd8988c4094'),
(50,0,'HighestScoredCampsiteJob','b5f0d47fe892cfb3595ed3b7bef247e0'),
(51,0,'HottestCampsiteJob','f9ca7b0e4d82437570b5b24594ccf6b7'),
(52,0,'HighestScoredCampsiteJob','0d11cda0b75c4ef8622bedbf65dc717b'),
(53,0,'HottestCampsiteJob','b1854604927b5d89fbd46d008d8c1d88'),
(54,0,'HighestScoredCampsiteJob','3c8502b2d8d79130160e5b0be729e4cd'),
(55,0,'HottestCampsiteJob','d92705f7fcaa325f22b74054dafdb8c2'),
(56,0,'HighestScoredCampsiteJob','f6924a331eb836fb27aa88401be87923'),
(57,0,'HottestCampsiteJob','65bd6f922489113e9ec059a799980208'),
(58,0,'HighestScoredCampsiteJob','a8044b8765d6444965dee90ecba3f45e'),
(59,0,'HottestCampsiteJob','0966c11e6766f889f5c19cd9c3ebd031'),
(60,0,'HighestScoredCampsiteJob','d02649fea36095203df5a1f49d4d90e4'),
(61,0,'HottestCampsiteJob','97d1f93cd529b35dc1ce04eac7386b21'),
(62,0,'HighestScoredCampsiteJob','4a608cd95790cc00188ac7287ad0a87b'),
(63,0,'HottestCampsiteJob','2e5a3b4612d618f434bedb6007edf2cb'),
(64,0,'HighestScoredCampsiteJob','2877422cd0e54d111f6fe9ac4483dcec'),
(65,0,'HottestCampsiteJob','d495e5291277d84fd2a39969bc057f2f'),
(66,0,'HighestScoredCampsiteJob','fa62cf47f78223fa9722754d7d2b5d5d'),
(67,0,'HottestCampsiteJob','e37d9f048b2fd2307b69c0489bec439e'),
(68,0,'HighestScoredCampsiteJob','df1eaf01fc986f933d4f1d52a2b42c99'),
(69,0,'HottestCampsiteJob','38647060bd79a69627bb544aa7cbddd5'),
(70,0,'HighestScoredCampsiteJob','6beaba3279c8f67299da319ca34d3616'),
(71,0,'HottestCampsiteJob','72ed1cb44b36f7abbf25c84dc6ebe5ea'),
(72,0,'HighestScoredCampsiteJob','6a7fe3dd45ea3b55944badc3973f695e'),
(73,0,'HottestCampsiteJob','7d1883d4d0f2bc9a6da13f68f8d65309'),
(74,0,'HighestScoredCampsiteJob','b384b54cbbb967c46bfc906a161e8506'),
(75,0,'HottestCampsiteJob','2c0bf8ba59a21fe9126e745b1dc9186c'),
(76,0,'HighestScoredCampsiteJob','eea4608726491cee088a99798e6b769b'),
(77,0,'HottestCampsiteJob','e992d747bb8c1bf04a28baa1563bd007'),
(78,0,'HighestScoredCampsiteJob','45e914632617fb08c111f0fd0ef6b29e'),
(79,0,'HottestCampsiteJob','8a1e28254cfbbe539b7f08a9c6e52b14'),
(80,0,'HighestScoredCampsiteJob','8cc6dab57c2fd88572b4667eb3ab7456'),
(81,0,'HottestCampsiteJob','696c72042eee8472cb12967c149369b8'),
(82,0,'HighestScoredCampsiteJob','b63c0feaf7dbad6bf1014953ddad4d5a'),
(83,0,'HottestCampsiteJob','36d0e4ee931bcd027564c58e8d7dc99b'),
(84,0,'HighestScoredCampsiteJob','53a2117cb6e50c7572856b2b90a0402c'),
(85,0,'HottestCampsiteJob','adc845ebf6b6c65332b9bb3fbd7c389e'),
(86,0,'HighestScoredCampsiteJob','b850d17d57debe4235bef9b75f5d984f'),
(87,0,'HottestCampsiteJob','226bcbeac799fc6ddd29d6889feedd4e'),
(88,0,'HighestScoredCampsiteJob','e7afa1a795f7583a05b9789d70d24c58'),
(89,0,'HottestCampsiteJob','fd0851a0e8a16fde534c3d83af2fb4a1'),
(90,0,'HighestScoredCampsiteJob','f5d51bc1fe876b84a4c177b42d3eb59d'),
(91,0,'HottestCampsiteJob','673db35a5c692f87b20a6b36cfdc1141'),
(92,0,'HighestScoredCampsiteJob','226956da239232f74ff8ca0097f137ff'),
(93,0,'HottestCampsiteJob','6071422648d2d42c46a1d9ae47d3b04d'),
(94,0,'HighestScoredCampsiteJob','f6fd6450ca4024fb652e3bc45a7e32e4'),
(95,0,'HottestCampsiteJob','089f7e63f0ecd12da7f43fda599b5618'),
(96,0,'HighestScoredCampsiteJob','5a98ccaa956868c1ea382bb600957296'),
(97,0,'softDeleteEtcMessageJob','46b81e50acd476732082aaeb5aca7bc7'),
(98,0,'HottestCampsiteJob','806e3acd3f0c1cb6ff6890a6e91b5202'),
(99,0,'HighestScoredCampsiteJob','2c2d6358e5cc2ae2919c5178fb7b7a34'),
(100,0,'fcmTokenDeleteJob','c2a0fa8d29f56e643f1385329517665a'),
(101,0,'HottestCampsiteJob','5d0f8ebdf6a365c8719b3957e863b0ce'),
(102,0,'HighestScoredCampsiteJob','e9a0c939922b0686500a70256ce38173');
/*!40000 ALTER TABLE `BATCH_JOB_INSTANCE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_JOB_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_JOB_SEQ`
--

LOCK TABLES `BATCH_JOB_SEQ` WRITE;
/*!40000 ALTER TABLE `BATCH_JOB_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_JOB_SEQ` VALUES
(102,'0');
/*!40000 ALTER TABLE `BATCH_JOB_SEQ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_STEP_EXECUTION`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) NOT NULL,
  `STEP_NAME` varchar(100) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime(6) NOT NULL,
  `END_TIME` datetime(6) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `COMMIT_COUNT` bigint(20) DEFAULT NULL,
  `READ_COUNT` bigint(20) DEFAULT NULL,
  `FILTER_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_COUNT` bigint(20) DEFAULT NULL,
  `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_STEP_EXECUTION`
--

LOCK TABLES `BATCH_STEP_EXECUTION` WRITE;
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION` DISABLE KEYS */;
INSERT INTO `BATCH_STEP_EXECUTION` VALUES
(1,3,'HottestCampsiteStep',1,'2023-02-11 09:43:50.825000','2023-02-11 09:43:54.214000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-11 09:43:54.217000'),
(2,3,'HighestScoredCampsiteStep',2,'2023-02-11 09:43:54.325000','2023-02-11 09:43:55.305000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-11 09:43:55.308000'),
(3,3,'softDeleteEtcMessageStep',3,'2023-02-12 04:00:00.039000','2023-02-12 04:00:00.544000','COMPLETED',1,8,0,8,0,0,0,0,'COMPLETED','','2023-02-12 04:00:00.545000'),
(4,3,'HottestCampsiteStep',4,'2023-02-12 05:55:34.758000','2023-02-12 05:55:37.269000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 05:55:37.270000'),
(5,3,'HighestScoredCampsiteStep',5,'2023-02-12 05:55:37.330000','2023-02-12 05:55:38.249000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 05:55:38.249000'),
(6,3,'HottestCampsiteStep',6,'2023-02-12 06:35:26.135000','2023-02-12 06:35:28.629000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 06:35:28.631000'),
(7,3,'HighestScoredCampsiteStep',7,'2023-02-12 06:35:28.710000','2023-02-12 06:35:29.611000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 06:35:29.612000'),
(8,3,'HottestCampsiteStep',8,'2023-02-12 07:21:34.295000','2023-02-12 07:21:38.657000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 07:21:38.665000'),
(9,3,'HighestScoredCampsiteStep',9,'2023-02-12 07:21:38.765000','2023-02-12 07:21:40.648000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 07:21:40.649000'),
(10,3,'HottestCampsiteStep',10,'2023-02-12 17:27:51.168000','2023-02-12 17:27:55.642000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 17:27:55.643000'),
(11,3,'HighestScoredCampsiteStep',11,'2023-02-12 17:27:55.726000','2023-02-12 17:27:57.481000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 17:27:57.482000'),
(12,3,'HottestCampsiteStep',12,'2023-02-12 18:00:38.462000','2023-02-12 18:00:42.716000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 18:00:42.726000'),
(13,3,'HighestScoredCampsiteStep',13,'2023-02-12 18:00:42.879000','2023-02-12 18:00:44.230000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 18:00:44.231000'),
(14,3,'HottestCampsiteStep',14,'2023-02-12 18:54:20.032000','2023-02-12 18:54:25.381000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 18:54:25.385000'),
(15,3,'HighestScoredCampsiteStep',15,'2023-02-12 18:54:25.520000','2023-02-12 18:54:26.522000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 18:54:26.523000'),
(16,3,'HottestCampsiteStep',16,'2023-02-12 10:46:51.027000','2023-02-12 10:46:55.410000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 10:46:55.411000'),
(17,3,'HighestScoredCampsiteStep',17,'2023-02-12 10:46:55.471000','2023-02-12 10:46:56.359000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 10:46:56.360000'),
(18,3,'HottestCampsiteStep',18,'2023-02-12 19:48:32.108000','2023-02-12 19:48:36.580000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 19:48:36.583000'),
(19,3,'HighestScoredCampsiteStep',19,'2023-02-12 19:48:36.708000','2023-02-12 19:48:37.772000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-12 19:48:37.773000'),
(20,3,'HottestCampsiteStep',20,'2023-02-13 00:04:26.538000','2023-02-13 00:04:31.439000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 00:04:31.444000'),
(21,3,'HighestScoredCampsiteStep',21,'2023-02-13 00:04:31.596000','2023-02-13 00:04:33.277000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 00:04:33.278000'),
(22,3,'softDeleteEtcMessageStep',22,'2023-02-13 04:00:00.034000','2023-02-13 04:00:00.213000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 04:00:00.213000'),
(23,3,'HottestCampsiteStep',23,'2023-02-13 09:19:58.880000','2023-02-13 09:20:03.053000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 09:20:03.055000'),
(24,3,'HighestScoredCampsiteStep',24,'2023-02-13 09:20:03.146000','2023-02-13 09:20:04.534000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 09:20:04.534000'),
(25,3,'HottestCampsiteStep',25,'2023-02-13 09:29:59.260000','2023-02-13 09:30:03.545000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 09:30:03.557000'),
(26,3,'HighestScoredCampsiteStep',26,'2023-02-13 09:30:03.717000','2023-02-13 09:30:04.611000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 09:30:04.612000'),
(27,3,'HottestCampsiteStep',27,'2023-02-13 10:16:03.494000','2023-02-13 10:16:07.794000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 10:16:07.795000'),
(28,3,'HighestScoredCampsiteStep',28,'2023-02-13 10:16:07.861000','2023-02-13 10:16:08.747000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 10:16:08.747000'),
(29,2,'HottestCampsiteStep',29,'2023-02-13 10:21:32.509000','2023-02-13 10:21:36.791000','FAILED',0,0,0,0,0,0,0,1,'FAILED','org.springframework.data.redis.RedisConnectionFailureException: Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to redis_boot:6379\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.translateException(LettuceConnectionFactory.java:1689)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1597)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getNativeConnection(LettuceConnectionFactory.java:1383)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getConnection(LettuceConnectionFactory.java:1366)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getSharedConnection(LettuceConnectionFactory.java:1093)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getConnection(LettuceConnectionFactory.java:421)\n	at org.springframework.data.redis.core.RedisConnectionUtils.fetchConnection(RedisConnectionUtils.java:193)\n	at org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection(RedisConnectionUtils.java:144)\n	at org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:105)\n	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:211)\n	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:191)\n	at org.springframework.data.redis.core.AbstractOperations.execute(AbstractOperations.java:97)\n	at org.springframework.data.redis.core.DefaultValueOperations.set(DefaultValueOperations.java:305)\n	at com.ssafy.campinity.core.repository.redis.RedisDao.setValues(RedisDao.java:21)\n	at com.ssafy.campinity.demo.batch.job.HottestCampsiteConfig$1.execute(HottestCampsiteConfig.java:80)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(R','2023-02-13 10:21:36.797000'),
(30,2,'HighestScoredCampsiteStep',30,'2023-02-13 10:21:36.931000','2023-02-13 10:21:38.274000','FAILED',0,0,0,0,0,0,0,1,'FAILED','org.springframework.data.redis.RedisConnectionFailureException: Unable to connect to Redis; nested exception is io.lettuce.core.RedisConnectionException: Unable to connect to redis_boot:6379\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.translateException(LettuceConnectionFactory.java:1689)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$ExceptionTranslatingConnectionProvider.getConnection(LettuceConnectionFactory.java:1597)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getNativeConnection(LettuceConnectionFactory.java:1383)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory$SharedConnection.getConnection(LettuceConnectionFactory.java:1366)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getSharedConnection(LettuceConnectionFactory.java:1093)\n	at org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory.getConnection(LettuceConnectionFactory.java:421)\n	at org.springframework.data.redis.core.RedisConnectionUtils.fetchConnection(RedisConnectionUtils.java:193)\n	at org.springframework.data.redis.core.RedisConnectionUtils.doGetConnection(RedisConnectionUtils.java:144)\n	at org.springframework.data.redis.core.RedisConnectionUtils.getConnection(RedisConnectionUtils.java:105)\n	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:211)\n	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:191)\n	at org.springframework.data.redis.core.AbstractOperations.execute(AbstractOperations.java:97)\n	at org.springframework.data.redis.core.DefaultValueOperations.set(DefaultValueOperations.java:305)\n	at com.ssafy.campinity.core.repository.redis.RedisDao.setValues(RedisDao.java:21)\n	at com.ssafy.campinity.demo.batch.job.HighestScoredCampsiteConfig$1.execute(HighestScoredCampsiteConfig.java:70)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n	at java.base/java.lang.reflect.Method.invoke(Method.java:566)\n	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)\n	at org.springframework.aop.framework.ReflectiveMethodInvocation.invok','2023-02-13 10:21:38.275000'),
(31,3,'HottestCampsiteStep',31,'2023-02-13 10:23:45.799000','2023-02-13 10:23:50.401000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 10:23:50.408000'),
(32,3,'HighestScoredCampsiteStep',32,'2023-02-13 10:23:50.509000','2023-02-13 10:23:51.840000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 10:23:51.841000'),
(33,3,'HottestCampsiteStep',33,'2023-02-13 10:40:53.536000','2023-02-13 10:40:57.427000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 10:40:57.440000'),
(34,3,'HighestScoredCampsiteStep',34,'2023-02-13 10:40:57.520000','2023-02-13 10:40:58.695000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 10:40:58.696000'),
(35,3,'HottestCampsiteStep',35,'2023-02-13 15:14:26.026000','2023-02-13 15:14:30.550000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 15:14:30.560000'),
(36,3,'HighestScoredCampsiteStep',36,'2023-02-13 15:14:30.690000','2023-02-13 15:14:31.991000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 15:14:31.991000'),
(37,3,'HottestCampsiteStep',37,'2023-02-13 17:04:53.287000','2023-02-13 17:04:57.447000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 17:04:57.455000'),
(38,3,'HighestScoredCampsiteStep',38,'2023-02-13 17:04:57.571000','2023-02-13 17:04:59.320000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 17:04:59.320000'),
(39,3,'HottestCampsiteStep',39,'2023-02-13 17:21:03.110000','2023-02-13 17:21:07.626000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 17:21:07.632000'),
(40,3,'HighestScoredCampsiteStep',40,'2023-02-13 17:21:07.780000','2023-02-13 17:21:09.330000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-13 17:21:09.332000'),
(41,3,'HottestCampsiteStep',41,'2023-02-14 00:16:18.912000','2023-02-14 00:16:23.275000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 00:16:23.284000'),
(42,3,'HighestScoredCampsiteStep',42,'2023-02-14 00:16:23.413000','2023-02-14 00:16:24.810000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 00:16:24.811000'),
(43,3,'softDeleteEtcMessageStep',43,'2023-02-14 04:00:00.037000','2023-02-14 04:00:00.207000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 04:00:00.207000'),
(44,3,'HottestCampsiteStep',44,'2023-02-14 04:05:00.036000','2023-02-14 04:05:00.991000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 04:05:00.992000'),
(45,3,'HighestScoredCampsiteStep',45,'2023-02-14 04:10:00.032000','2023-02-14 04:10:00.710000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 04:10:00.710000'),
(46,3,'MessagehardDeleteStep',46,'2023-02-14 04:20:00.031000','2023-02-14 04:20:00.191000','COMPLETED',1,1,0,1,0,0,0,0,'COMPLETED','','2023-02-14 04:20:00.192000'),
(47,3,'MyCollectionHardDeleteStep',46,'2023-02-14 04:20:00.210000','2023-02-14 04:20:00.422000','COMPLETED',1,7,0,7,0,0,0,0,'COMPLETED','','2023-02-14 04:20:00.423000'),
(48,3,'fcmTokenDeleteStep',47,'2023-02-14 04:25:00.034000','2023-02-14 04:25:00.062000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 04:25:00.062000'),
(49,3,'HottestCampsiteStep',49,'2023-02-14 09:59:02.225000','2023-02-14 09:59:06.620000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 09:59:06.624000'),
(50,3,'HighestScoredCampsiteStep',50,'2023-02-14 09:59:06.737000','2023-02-14 09:59:07.894000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 09:59:07.895000'),
(51,3,'HottestCampsiteStep',51,'2023-02-14 10:52:05.628000','2023-02-14 10:52:09.766000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 10:52:09.774000'),
(52,3,'HighestScoredCampsiteStep',52,'2023-02-14 10:52:09.903000','2023-02-14 10:52:10.990000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 10:52:10.991000'),
(53,3,'HottestCampsiteStep',53,'2023-02-14 13:38:46.715000','2023-02-14 13:38:50.925000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 13:38:50.929000'),
(54,3,'HighestScoredCampsiteStep',54,'2023-02-14 13:38:51.034000','2023-02-14 13:38:52.610000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 13:38:52.616000'),
(55,3,'HottestCampsiteStep',55,'2023-02-14 21:16:09.166000','2023-02-14 21:16:11.426000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 21:16:11.427000'),
(56,3,'HighestScoredCampsiteStep',56,'2023-02-14 21:16:11.482000','2023-02-14 21:16:12.419000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 21:16:12.420000'),
(57,3,'HottestCampsiteStep',57,'2023-02-14 21:56:30.211000','2023-02-14 21:56:33.174000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 21:56:33.175000'),
(58,3,'HighestScoredCampsiteStep',58,'2023-02-14 21:56:33.236000','2023-02-14 21:56:34.171000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 21:56:34.172000'),
(59,3,'HottestCampsiteStep',59,'2023-02-14 21:58:10.538000','2023-02-14 21:58:14.639000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 21:58:14.641000'),
(60,3,'HighestScoredCampsiteStep',60,'2023-02-14 21:58:14.754000','2023-02-14 21:58:16.056000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 21:58:16.057000'),
(61,3,'HottestCampsiteStep',61,'2023-02-14 22:55:46.312000','2023-02-14 22:55:51.483000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 22:55:51.486000'),
(62,3,'HighestScoredCampsiteStep',62,'2023-02-14 22:55:51.699000','2023-02-14 22:55:53.194000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-14 22:55:53.195000'),
(63,3,'HottestCampsiteStep',63,'2023-02-15 03:38:11.077000','2023-02-15 03:38:15.636000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 03:38:15.648000'),
(64,3,'HighestScoredCampsiteStep',64,'2023-02-15 03:38:15.785000','2023-02-15 03:38:18.029000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 03:38:18.031000'),
(65,3,'HottestCampsiteStep',65,'2023-02-15 09:10:12.455000','2023-02-15 09:10:14.924000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 09:10:14.925000'),
(66,3,'HighestScoredCampsiteStep',66,'2023-02-15 09:10:14.994000','2023-02-15 09:10:15.804000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 09:10:15.805000'),
(67,3,'HottestCampsiteStep',67,'2023-02-15 09:25:42.451000','2023-02-15 09:25:47.580000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 09:25:47.607000'),
(68,3,'HighestScoredCampsiteStep',68,'2023-02-15 09:25:47.722000','2023-02-15 09:25:48.963000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 09:25:48.964000'),
(69,3,'HottestCampsiteStep',69,'2023-02-15 11:19:10.800000','2023-02-15 11:19:16.457000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 11:19:16.470000'),
(70,3,'HighestScoredCampsiteStep',70,'2023-02-15 11:19:16.566000','2023-02-15 11:19:17.751000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 11:19:17.753000'),
(71,3,'HottestCampsiteStep',71,'2023-02-15 11:33:41.893000','2023-02-15 11:33:46.308000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 11:33:46.310000'),
(72,3,'HighestScoredCampsiteStep',72,'2023-02-15 11:33:46.432000','2023-02-15 11:33:48.016000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 11:33:48.017000'),
(73,3,'HottestCampsiteStep',73,'2023-02-15 15:10:13.740000','2023-02-15 15:10:18.588000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 15:10:18.601000'),
(74,3,'HighestScoredCampsiteStep',74,'2023-02-15 15:10:18.801000','2023-02-15 15:10:20.924000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 15:10:20.925000'),
(75,3,'HottestCampsiteStep',75,'2023-02-15 15:37:06.825000','2023-02-15 15:37:12.685000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 15:37:12.688000'),
(76,3,'HighestScoredCampsiteStep',76,'2023-02-15 15:37:12.937000','2023-02-15 15:37:15.505000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 15:37:15.506000'),
(77,3,'HottestCampsiteStep',77,'2023-02-15 15:38:01.185000','2023-02-15 15:38:05.456000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 15:38:05.459000'),
(78,3,'HighestScoredCampsiteStep',78,'2023-02-15 15:38:05.621000','2023-02-15 15:38:07.567000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 15:38:07.568000'),
(79,3,'HottestCampsiteStep',79,'2023-02-15 16:00:00.110000','2023-02-15 16:00:02.318000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 16:00:02.319000'),
(80,3,'HighestScoredCampsiteStep',80,'2023-02-15 16:00:02.381000','2023-02-15 16:00:03.316000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 16:00:03.317000'),
(81,3,'HottestCampsiteStep',81,'2023-02-15 16:08:57.080000','2023-02-15 16:08:59.431000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 16:08:59.433000'),
(82,3,'HighestScoredCampsiteStep',82,'2023-02-15 16:08:59.496000','2023-02-15 16:09:00.466000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 16:09:00.467000'),
(83,3,'HottestCampsiteStep',83,'2023-02-15 16:33:27.980000','2023-02-15 16:33:30.480000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 16:33:30.481000'),
(84,3,'HighestScoredCampsiteStep',84,'2023-02-15 16:33:30.535000','2023-02-15 16:33:31.397000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 16:33:31.398000'),
(85,3,'HottestCampsiteStep',85,'2023-02-15 16:45:41.250000','2023-02-15 16:45:43.581000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 16:45:43.582000'),
(86,3,'HighestScoredCampsiteStep',86,'2023-02-15 16:45:43.640000','2023-02-15 16:45:44.483000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 16:45:44.484000'),
(87,3,'HottestCampsiteStep',87,'2023-02-15 17:22:05.977000','2023-02-15 17:22:10.505000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 17:22:10.512000'),
(88,3,'HighestScoredCampsiteStep',88,'2023-02-15 17:22:10.684000','2023-02-15 17:22:12.605000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 17:22:12.607000'),
(89,3,'HottestCampsiteStep',89,'2023-02-15 17:23:33.603000','2023-02-15 17:23:37.505000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 17:23:37.508000'),
(90,3,'HighestScoredCampsiteStep',90,'2023-02-15 17:23:37.612000','2023-02-15 17:23:39.446000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-15 17:23:39.447000'),
(91,3,'HottestCampsiteStep',91,'2023-02-16 02:53:23.084000','2023-02-16 02:53:28.317000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 02:53:28.325000'),
(92,3,'HighestScoredCampsiteStep',92,'2023-02-16 02:53:28.425000','2023-02-16 02:53:30.131000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 02:53:30.132000'),
(93,3,'HottestCampsiteStep',93,'2023-02-16 03:35:47.951000','2023-02-16 03:35:52.449000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 03:35:52.470000'),
(94,3,'HighestScoredCampsiteStep',94,'2023-02-16 03:35:52.575000','2023-02-16 03:35:54.057000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 03:35:54.058000'),
(95,3,'HottestCampsiteStep',95,'2023-02-16 03:50:46.220000','2023-02-16 03:50:50.828000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 03:50:50.840000'),
(96,3,'HighestScoredCampsiteStep',96,'2023-02-16 03:50:50.945000','2023-02-16 03:50:52.451000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 03:50:52.452000'),
(97,3,'softDeleteEtcMessageStep',97,'2023-02-16 04:00:00.054000','2023-02-16 04:00:00.378000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 04:00:00.380000'),
(98,3,'HottestCampsiteStep',98,'2023-02-16 04:03:51.481000','2023-02-16 04:03:55.655000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 04:03:55.657000'),
(99,3,'HighestScoredCampsiteStep',99,'2023-02-16 04:03:55.786000','2023-02-16 04:03:57.960000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 04:03:57.961000'),
(100,3,'fcmTokenDeleteStep',100,'2023-02-16 04:25:00.049000','2023-02-16 04:25:00.390000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 04:25:00.394000'),
(101,3,'HottestCampsiteStep',101,'2023-02-16 15:51:48.547000','2023-02-16 15:51:52.852000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 15:51:52.854000'),
(102,3,'HighestScoredCampsiteStep',102,'2023-02-16 15:51:52.959000','2023-02-16 15:51:54.853000','COMPLETED',1,0,0,0,0,0,0,0,'COMPLETED','','2023-02-16 15:51:54.855000');
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_STEP_EXECUTION_CONTEXT`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_CONTEXT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION_CONTEXT` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_STEP_EXECUTION_CONTEXT`
--

LOCK TABLES `BATCH_STEP_EXECUTION_CONTEXT` WRITE;
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT` DISABLE KEYS */;
INSERT INTO `BATCH_STEP_EXECUTION_CONTEXT` VALUES
(1,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(2,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(3,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\",\"softDeleteEtcMessageReader.read.count\":9,\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(4,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(5,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(6,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(7,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(8,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(9,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(10,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(11,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(12,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(13,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(14,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(15,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(16,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(17,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(18,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(19,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(20,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(21,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(22,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\",\"softDeleteEtcMessageReader.read.count\":1,\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(23,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(24,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(25,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(26,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(27,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(28,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(29,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(30,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(31,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(32,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(33,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(34,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(35,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(36,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(37,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(38,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(39,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(40,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(41,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(42,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(43,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\",\"softDeleteEtcMessageReader.read.count\":1,\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(44,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(45,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(46,'{\"@class\":\"java.util.HashMap\",\"MessageHardDeleteReader.read.count\":2,\"batch.taskletType\":\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(47,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\",\"MyCollectionHardDeleteReader.read.count\":8,\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(48,'{\"@class\":\"java.util.HashMap\",\"FcmTokenDeleteReader.read.count\":1,\"batch.taskletType\":\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(49,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy161\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(50,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy161\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(51,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy161\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(52,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy161\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(53,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy161\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(54,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy161\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(55,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(56,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(57,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(58,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(59,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(60,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy163\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(61,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(62,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(63,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(64,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(65,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(66,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy166\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(67,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(68,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(69,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(70,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(71,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(72,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(73,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(74,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(75,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(76,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(77,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(78,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(79,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(80,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(81,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(82,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(83,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(84,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(85,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(86,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(87,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(88,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(89,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(90,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(91,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(92,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(93,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(94,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(95,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(96,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(97,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\",\"softDeleteEtcMessageReader.read.count\":1,\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(98,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(99,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(100,'{\"@class\":\"java.util.HashMap\",\"FcmTokenDeleteReader.read.count\":1,\"batch.taskletType\":\"org.springframework.batch.core.step.item.ChunkOrientedTasklet\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(101,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL),
(102,'{\"@class\":\"java.util.HashMap\",\"batch.taskletType\":\"com.sun.proxy.$Proxy165\",\"batch.stepType\":\"org.springframework.batch.core.step.tasklet.TaskletStep\"}',NULL);
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_CONTEXT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BATCH_STEP_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BATCH_STEP_EXECUTION_SEQ`
--

LOCK TABLES `BATCH_STEP_EXECUTION_SEQ` WRITE;
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_SEQ` DISABLE KEYS */;
INSERT INTO `BATCH_STEP_EXECUTION_SEQ` VALUES
(102,'0');
/*!40000 ALTER TABLE `BATCH_STEP_EXECUTION_SEQ` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-16 18:41:26
