package com.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;


/**
 * @Description: 自定义Logger 工具类
 * @Author: junping.chi@luckincoffee.com
 * @Date: 2019/11/27 13:36
 */
public class CustomLoggerUtils {
    private static final Logger LOGGER = Logger.getLogger(CustomLoggerUtils.class);

    private static final String SLASH = "/";
    /**
     * 血缘日志文件名
     */
    private static final String LINEAGE_FILE_NAME = "stdLineageInfo";
    /**
     * 血缘解析Logger 缓存
     */
    private static Logger LINEAGE_LOGGER_CACHE = null;

    /**
     * 获取特定Logger，该Logger将日志打印到指定文件
     *
     * @param filePath 该logger的日志将打印在此路劲对应的文件中
     * @return
     * @throws Exception
     */
    public static Logger getLogger(String filePath, Level level) throws Exception {
        Logger logger;
        try {
            //PatternLayout
            PatternLayout patternLayout = new PatternLayout();
            patternLayout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %p [%C{3}:%M:%L] %m%n");
            ////RollingFileAppender
            RollingFileAppender rollingFileAppender = new RollingFileAppender(patternLayout, filePath);
            rollingFileAppender.setMaxFileSize("200MB");
            rollingFileAppender.setMaxBackupIndex(10);
            rollingFileAppender.setEncoding("UTF-8");
            //Logger
            logger = Logger.getLogger(CustomLoggerUtils.class.getName() + "==" + filePath);
            logger.setLevel(level);
            //防止同一个appender设置多次，导致同一处log日志打印多次
            if (logger.getAppender(rollingFileAppender.getName()) == null) {
                logger.addAppender(rollingFileAppender);
            } else {
                throw new RuntimeException("The appender'name is already exist,please change it to another one.");
            }
        } catch (Exception e) {
            LOGGER.error("Get Logger with the filePath failed", e);
            throw new Exception(e);
        }
        //返回自定义logger
        return logger;
    }


    /**
     * 获取血缘解析日志打印Logger
     *
     * @return
     * @throws Exception
     */
    public static Logger getLineageLogger() throws Exception {
        if (LINEAGE_LOGGER_CACHE == null) {
            String yarnAppContainer = System.getProperty("spark.yarn.app.container.log.dir");
            String javaTmp = System.getProperty("java.io.tmpdir");
            String logFilePathPrefix = StringUtils.isBlank(yarnAppContainer) ? javaTmp : yarnAppContainer;
            Logger logger = getLogger(logFilePathPrefix + SLASH + LINEAGE_FILE_NAME, Level.DEBUG);
            LINEAGE_LOGGER_CACHE = logger;
            return LINEAGE_LOGGER_CACHE;
        } else {
            return LINEAGE_LOGGER_CACHE;
        }
    }
}