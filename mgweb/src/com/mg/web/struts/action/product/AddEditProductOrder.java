package com.mg.web.struts.action.product;

import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Product;
import com.mg.model.ProductOrder;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.DTOFactory;
import com.mg.service.dto.ProductDTO;
import com.mg.service.product.ProductManager;
import com.mg.service.product.ProductServiceImpl;
import com.mg.web.struts.action.BasicAction;
import com.opensymphony.xwork2.Preparable;

public class AddEditProductOrder extends BasicAction implements Preparable {

	private static final long serialVersionUID = 1407928761808064375L;
	private ProductOrder productOrder;
	private List<ProductOrder> productOrderList;

	@Override
	public String execute() {
		return SUCCESS;
	}

	@Override
	public void prepare() {
		try {

			List<ProductOrder> list = ServiceLocator.getService(ProductServiceImpl.class).getAllProductOrder();
			for (ProductOrder productOrder : list) {
				productOrder.setProduct(ServiceLocator.getService(ProductServiceImpl.class)
						.getProduct(productOrder.getProduct().getId(), true));
			}
			productOrderList = list;
		} catch (Exception e) {
			managerException(e);
		}
	}

	public String save() {
		try {
			ServiceLocator.getService(ProductServiceImpl.class).saveProductOrder(productOrder);
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	public ProductOrder getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(ProductOrder productOrder) {
		this.productOrder = productOrder;
	}

	public void setProductOrderList(List<ProductOrder> productOrderList) {
		this.productOrderList = productOrderList;
	}

	public List<ProductOrder> getProductOrderList() {
		return productOrderList;
	}

	public List<ProductDTO> getProductOrderListDTO() throws ServiceException, ServiceLocatorException {
		List<Product> list = ProductManager.getProductList(productOrderList);
		return DTOFactory.getProductDTOList(list, "en", "CAD", false);
	}
}
