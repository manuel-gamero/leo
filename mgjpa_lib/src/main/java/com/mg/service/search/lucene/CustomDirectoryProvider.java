package com.mg.service.search.lucene;

import java.util.Properties;

import org.apache.lucene.store.RAMDirectory;
import org.hibernate.search.engine.service.classloading.spi.ClassLoaderService;
import org.hibernate.search.engine.service.spi.ServiceManager;
import org.hibernate.search.indexes.spi.DirectoryBasedIndexManager;
import org.hibernate.search.spi.BuildContext;
import org.hibernate.search.store.DirectoryProvider;


public class CustomDirectoryProvider implements DirectoryProvider<RAMDirectory> {
    private ServiceManager serviceManager;
    private ClassLoaderService classLoaderService;

    public void initialize(
        String directoryProviderName,
        Properties properties,
        BuildContext context) {
        //get a reference to the ServiceManager
        this.serviceManager = context.getServiceManager();
    }

    @Override
    public void start(DirectoryBasedIndexManager arg0) {
        //get the current ClassLoaderService
        classLoaderService = serviceManager.requestService(ClassLoaderService.class);
    }

    public RAMDirectory getDirectory() {
        return null;
    }

    @Override
    public void stop() {
        //make sure to release all services
        serviceManager.releaseService(ClassLoaderService.class);
    }
	
}
