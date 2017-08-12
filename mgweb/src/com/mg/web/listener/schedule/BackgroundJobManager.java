package com.mg.web.listener.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.mg.web.jobs.DailyJob;

@WebListener
public class BackgroundJobManager /*implements ServletContextListener*/ {

	private ScheduledExecutorService scheduler;

	//@Override
	public void contextInitialized(ServletContextEvent event) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new DailyJob(), 0, 1, TimeUnit.MINUTES);
		// scheduler.scheduleAtFixedRate(new SomeHourlyJob(), 0, 1,
		// TimeUnit.HOURS);
		// scheduler.scheduleAtFixedRate(new SomeQuarterlyJob(), 0, 15,
		// TimeUnit.MINUTES);
	}

	//@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
	}

}