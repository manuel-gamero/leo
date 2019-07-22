package com.mg.service.datamining;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.ProductDataDAO;
import com.mg.datamining.clustering.DataItem;
import com.mg.datamining.clustering.ItemCluster;
import com.mg.datamining.enums.Attribute;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.model.Product;
import com.mg.model.ProductData;
import com.mg.service.ServiceImpl;

public class DataminigServiceImpl extends ServiceImpl implements DataminigService {

	private static final Logger log = LogManager.getLogger(DataminigServiceImpl.class);

	public DataminigServiceImpl() {
		super();
	}

	@Override
	public void saveDataminingProduct(List<ItemCluster<Product, Attribute>> itemClusterList) throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					try {
						ProductDataDAO dao = DaoFactory.getDAO(ProductDataDAO.class, em);
						//Delete table before insert new data
						dao.deleteProductData();
						
						for (ItemCluster<Product, Attribute> itemCluster : itemClusterList) {
							for (DataItem<Product, Attribute> dataItem : itemCluster.getItems()) {
								ProductData productData = new ProductData(dataItem.getData(), String.valueOf(itemCluster.getClusterId()));
								dao.save(productData);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new DaoException(e);
					}
					return null;
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
	}

}
