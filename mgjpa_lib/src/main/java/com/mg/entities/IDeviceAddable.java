package com.mg.entities;

import java.util.Date;

public interface IDeviceAddable extends IDevice{
	 void add(IDeviceAddable item);
	 public Date getCreationDate();
	 public Date getLastModification();
}
