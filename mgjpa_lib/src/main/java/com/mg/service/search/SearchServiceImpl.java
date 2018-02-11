package com.mg.service.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.mg.dao.core.DaoCommand;
import com.mg.exception.DaoException;
import com.mg.exception.InitializationException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Product;
import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.init.ConfigService;
import com.mg.service.search.autocompleter.Autocompleter;
import com.mg.service.search.autocompleter.AutocompleterImpl;
import com.mg.service.search.autocompleter.core.AutocompleterConfigurator;
import com.mg.service.search.autocompleter.core.AutocompleterElement;
import com.mg.service.search.autocompleter.core.AutocompleterUnit;
import com.mg.service.search.autocompleter.task.SearchAutocompleterTask;
import com.mg.service.search.lucene.FullTextEntityQuery.Mode;
import com.mg.service.search.lucene.ProductFullTextEntityQuery;

/**
 * Provides all search related logic in the system.
 * 
 * @author MJGP
 *
 */
public class SearchServiceImpl extends ServiceImpl implements SearchService {

	private static final Logger log = Logger.getLogger(SearchServiceImpl.class);
	
	// Java timers work with milliseconds
	private static final long TIME_UNIT_ONE_SECOND = 1000;
	private static final long TIME_UNIT_ONE_MINUTE = 60 * TIME_UNIT_ONE_SECOND;	
	private static final String TITLE_EDGE_NGRAM_INDEX = "edgeNGramTitle";
	 private static final String TITLE_NGRAM_INDEX = "nGramTitle";
	
	public SearchServiceImpl() {
		super();
	}

	private Autocompleter<Long, AutocompleterUnit> seriesSearchAutocompleter;
	
	
	public Autocompleter<Long, AutocompleterUnit> getSeriesSearchAutocompleter() {
		return seriesSearchAutocompleter;
	}


	Timer mainSearchTimer;
	
	@Override
	public void initialize() throws InitializationException {
		AutocompleterConfigurator mainSearchConfigurator = AutocompleterConfigurator.DEFAULT;
		try {
			mainSearchConfigurator = ServiceLocator.getService(ConfigService.class).getSearchConfigurator();
		} catch (Exception e) {
			throw new InitializationException(
					"Something wrong with configuration service", e);
		} finally {
			// get sure we start up with functional autocompleters
			//mainSearchAutocompleter = new AutocompleterImpl<Long, AutocompleterUnit>(mainSearchConfigurator);
			seriesSearchAutocompleter = new AutocompleterImpl<Long, AutocompleterUnit>(mainSearchConfigurator);
			loadAutocompleterData();	
			scheduleTimers();
		}
	}
	

	@Override
	public void shutdown() {
		if (mainSearchTimer != null)
			mainSearchTimer.cancel();	
		
		//getMainSearchAutocompleter().printReport();
		getSeriesSearchAutocompleter().printReport();
	}

	private void scheduleTimers() {
		int refreshInterval = getSeriesSearchAutocompleter().getConfigurator()
				.getRefreshInterval();

		// Is configured to refresh?
		if (refreshInterval > 0) {
			mainSearchTimer = new Timer();
			mainSearchTimer.scheduleAtFixedRate(
					new SearchAutocompleterTask(), refreshInterval
							* TIME_UNIT_ONE_MINUTE, refreshInterval
							* TIME_UNIT_ONE_MINUTE);
		}
	}
	
	@Override
	public void loadAutocompleterData(){			
		
		try{
			/*
			 *  TODO This is only a test to make the autocompleter work
			 *  on game names.
			 *  You have to fill it up with the correct search terms
			 */
			SearchService searchService = ServiceLocator.getService(SearchService.class);
			
			Collection<AutocompleterElement<Long, AutocompleterUnit>> elements = new
			  ArrayList<AutocompleterElement<Long, AutocompleterUnit>>();
			AutocompleterUnit unit;
/*			Map<Long, String> map = searchService.getAllGamesMap();
			log.debug("autocompleter initializing:"+map.size());

			for (Map.Entry<Long, String>  entry : map.entrySet()) {
				unit = new AutocompleterUnit(entry.getKey(), entry.getValue());
				AutocompleterElement<Long, AutocompleterUnit> element = 
					new	AutocompleterElement<Long, AutocompleterUnit>(entry.getKey(), unit);
				elements.add(element);				
			} 

			searchService.getMainSearchAutocompleter().build(elements);
			
			// fill autocompleter for game series
			elements.clear();*/
			
			Map<Long, String> mapSeries = searchService.getAllSeriesMap(); 

			for (Map.Entry<Long, String>  entry : mapSeries.entrySet()) {
				unit = new AutocompleterUnit(entry.getKey(), entry.getValue());
				AutocompleterElement<Long, AutocompleterUnit> element = 
					new	AutocompleterElement<Long, AutocompleterUnit>(entry.getKey(), unit);
				elements.add(element);				
			} 

			searchService.getSeriesSearchAutocompleter().build(elements);
			
			
			
			
			if(log.isInfoEnabled()){
				log.debug("Search autocompleter has been successfully initialized.");
			}					
			
		}catch (ServiceLocatorException sle) {
			// TODO: handle exception
		}catch (ServiceException se) {
			// TODO: handle exception
		}		
	}

	@Override
	public Map<Long, String> getAllSeriesMap() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restart() throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					try {
						FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
						fullTextEntityManager.createIndexer().startAndWait();
						return null;
					} catch (Exception e) {
						e.printStackTrace();
						throw new DaoException(e);
					}
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> searchProduct(final String searchText) throws ServiceException {
		List<Product> list = null;
		try {
			daoManager.setCommitTransaction(true);
			list = (List<Product>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)	throws DaoException {
							FullTextEntityManager fullTextEntityManager =
								    org.hibernate.search.jpa.Search.getFullTextEntityManager(em);

								// create native Lucene query unsing the query DSL
								// alternatively you can write the Lucene query using the Lucene query parser
								// or the Lucene programmatic API. The Hibernate Search DSL is recommended though
								QueryBuilder qb = (QueryBuilder) fullTextEntityManager.getSearchFactory()
								    .buildQueryBuilder().forEntity(Product.class).get();
								/*org.apache.lucene.search.Query luceneQuery = ((org.hibernate.search.query.dsl.QueryBuilder) qb)
								  .keyword()
								  .onFields("translationByNameTransId.translationEntries.text")
								  .matching(query)
								  .createQuery();*/
								Query luceneQuery = qb.phrase().withSlop(2).onField(TITLE_NGRAM_INDEX)
										   .andField(TITLE_EDGE_NGRAM_INDEX).boostedTo(5)
										   .sentence(searchText.toLowerCase()).createQuery();

								// wrap Lucene query in a javax.persistence.Query
								javax.persistence.Query jpaQuery =
								    fullTextEntityManager.createFullTextQuery(luceneQuery, Product.class);

								// execute search
								List<Product> result = jpaQuery.getResultList();
								
								ProductFullTextEntityQuery q = new ProductFullTextEntityQuery();
								List<String> fields = new ArrayList<String>();
								fields.add("translationByNameTransId.translationEntries.text");
								q.setFields(fields);
								q.setMode(Mode.SIMPLE_STARTS_WITH);
								q.setMaxResults(25);
								q.setSearchText(searchText);
								
								return q.getItems(em);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}
	
}
