package com.mg.web.struts.action;




public class BasicListActionSupport<T> extends BasicAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
}