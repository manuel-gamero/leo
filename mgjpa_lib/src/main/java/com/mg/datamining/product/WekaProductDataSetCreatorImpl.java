package com.mg.datamining.product;

public class WekaProductDataSetCreatorImpl extends ProductDataSetCreatorImpl{

	/*public Instances createLearningDataSet() throws Exception {
		//this.blogEntries = createLearningData();
		ArrayList<Attribute> allAttributes = createAttributes();
		List<Product> productList = ServiceLocator.getService(ProductServiceImpl.class).getAllProduct();
		Instances trainingDataSet = new Instances("productClustering", allAttributes, productList.size());
		int numAttributes = allAttributes.size();
		ProductAnalyzer productAnalyzer = new ProductAnalyzerImpl();
		for (Product product : productList) {
			Instance instance = createNewInstance(numAttributes, trainingDataSet, allTags, dataItem);
			trainingDataSet.add(instance);
		}
		return trainingDataSet;
	}

	private ArrayList<Attribute> createAttributes() {
		com.mg.datamining.enums.Attribute[] allTags = com.mg.datamining.enums.Attribute.values();
		ArrayList <Attribute> allAttributes = new ArrayList<Attribute>();
		for (com.mg.datamining.enums.Attribute tag : allTags) {
			Attribute tagAttribute = new Attribute(tag.getCode());
			allAttributes.add(tagAttribute);
		}
		return allAttributes;
	}

	private Instance createNewInstance(int numAttributes,
			Instances trainingDataSet, Collection<Tag> allTags,
			DataItem<> dataItem) {
		Instance instance = new DenseInstance(numAttributes);
		instance.setDataset(trainingDataSet);
		int index = 0;
		ProductAnalyzer productAnalyzer = new ProductAnalyzerImpl();
		GenericMagnitudeVector<com.mg.datamining.enums.Attribute> tmv = productAnalyzer.createMagnitudeVector(product.getId());
		Map<Tag, GenericMagnitude> tmvMap = tmv.getMagnitudeMap();
		for (Tag tag : allTags) {
			TagMagnitude tm = tmvMap.get(tag);
			if (tm != null) {
				instance.setValue(index++, tm.getMagnitude());
			} else {
				instance.setValue(index++, 0.);
			}
		}
		return instance;
	}

	private List<DataItem<Product, com.mg.datamining.enums.Attribute>> getProductMagnitudeVectors(
			DeviceProduct dp) throws ServiceException, ServiceLocatorException {
		List<DataItem<Product, com.mg.datamining.enums.Attribute>> result = new ArrayList<DataItem<Product, com.mg.datamining.enums.Attribute>>();
		List<DeviceProduct> dpList = ServiceLocator.getService(
				DeviceServiceImpl.class).getDeviceProductGroupByProduc();
		List<Product> productList = ServiceLocator.getService(
				ProductServiceImpl.class).getAllProduct();
		ProductAnalyzer productAnalyzer = new ProductAnalyzerImpl();
		for (Product product : productList) {
			GenericMagnitudeVector<com.mg.datamining.enums.Attribute> pmv = productAnalyzer
					.createMagnitudeVector(product.getId());
			result.add(new DataItemImpl<Product, com.mg.datamining.enums.Attribute>(product, pmv));
		}
		return result;
	}
*/
}
