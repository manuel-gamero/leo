package com.mg.datamining.interfaces;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Audit;
import com.mg.model.Device;

public interface ICreation {
	
	void apply(Device device, Audit audit) throws NumberFormatException, ServiceException, ServiceLocatorException;

}
