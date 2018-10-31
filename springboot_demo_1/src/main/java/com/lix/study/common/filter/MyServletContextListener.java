package com.lix.study.common.filter;
  
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
/**
 * 使用@WebListener注解，实现ServletContextListener接口
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {
	
	private static final  Logger logger = LoggerFactory.getLogger(MyServletContextListener.class);
  
         @Override
         public void contextDestroyed(ServletContextEvent arg0) {
                   logger.info("------------ServletContex销毁------------");
         }
  
         @Override
         public void contextInitialized(ServletContextEvent arg0) {
                    logger.info("-----------------ServletContex初始化----------------");
         }
}