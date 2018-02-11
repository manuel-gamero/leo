package com.mg.service.search.lucene;

import java.io.File;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.store.FSDirectory;
import org.hibernate.HibernateException;
import org.hibernate.search.exception.AssertionFailure;
import org.hibernate.search.spi.BuildContext;
import org.hibernate.search.store.impl.DirectoryProviderHelper;
import org.hibernate.search.store.impl.FSDirectoryProvider;

import com.mg.exception.ServiceLocatorException;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigServiceImpl;

/* 
 * used to provide configuration for index directory from  
 * configuration file instead of persistence.xml to make per 
 * environment settings less of a pain
 * */

public class FSDirectoryProviderCustom extends FSDirectoryProvider {

	private FSDirectory directory;
	private static Log log = LogFactory.getLog(FSDirectoryProvider.class);


	@Override
	public void initialize(String directoryProviderName, Properties properties, BuildContext context)  {
		try {
			String indexBase = getIndexDir(); 
			File indexDir = new File(indexBase);
			log.info("Setting index dir to " + indexDir);
			File file = new File(indexDir, directoryProviderName);
		
			directory = DirectoryProviderHelper.createFSIndex( file.toPath(),properties, context.getServiceManager() );
		}
		catch (Exception e) {
			throw new HibernateException("Unable to initialize index: " + directoryProviderName, e);
		}
	}

	private String getIndexDir() throws ServiceLocatorException {
		return ServiceLocator.getService(ConfigServiceImpl.class).getLuceneIndexPath();
	}

	public FSDirectory getDirectory() {

		return directory;

	}

	@Override
	public boolean equals(Object obj) {
		throw new AssertionFailure( "this type can not be compared reliably" );
	}

	@Override
	public int hashCode() {
		throw new AssertionFailure( "this type can not be compared reliably" );
	}
}