package net.luis.platform;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SystemInit implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext application = event.getServletContext();

		// 上下文路径
		String path = application.getContextPath();
		application.setAttribute("path", path);

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
