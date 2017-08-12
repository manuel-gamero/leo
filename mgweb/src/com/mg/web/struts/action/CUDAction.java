/**
 * 
 */
package com.mg.web.struts.action;

import com.mg.exception.ServiceException;

/**
 * @author MJGP
 *
 */
public interface CUDAction {
	String create() throws ServiceException;
	String update();
	String delete();
}
