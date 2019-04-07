package com.mg.datamining.interfaces;

import java.util.Date;

public interface IDeviceAddable extends IDevice{
	 void add(IDeviceAddable item);
	 public Date getCreationDate();
	 public Date getLastModification();
}
