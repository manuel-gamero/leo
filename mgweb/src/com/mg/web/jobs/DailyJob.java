package com.mg.web.jobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigServiceImpl;

public class DailyJob implements Runnable {

	@Override
	public void run() {
		try {
			/*FileInputStream in = new FileInputStream(ServiceLocator.getService(
					ConfigServiceImpl.class).getRootPathWeb()
					+ "/WEB-INF/classes/dataminig.properties");
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(ServiceLocator
					.getService(ConfigServiceImpl.class).getRootPathWeb()
					+ "/WEB-INF/dataminig.properties");
			props.setProperty("dataminig.lastrun", (new Date()).toString());
			props.store(out, null);
			out.close();*/
			System.out.println("TEST");

			OutputStream out = null;
			try {

				Properties props = new Properties();

				File f = new File(ServiceLocator.getService(
						ConfigServiceImpl.class).getRootPathWeb()
						+ "/WEB-INF/classes/dataminig.properties");
				if (f.exists()) {
					FileReader file = new FileReader(f);
					props.load(file);
					// Change your values here
					props.setProperty("dataminig.lastrun", (new Date()).toString());
					file.close();
				} else {

					// Set default values?
					props.setProperty("dataminig.lastrun", (new Date()).toString());

					f.createNewFile();
				}

				out = new FileOutputStream(f);
				props.store(out, "This is an optional header comment string");
				System.out.println(props.get("ServerPort"));

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (Exception ex) {
						System.out.println("IOException: Could not close myApp.properties output stream; "
										+ ex.getMessage());
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}