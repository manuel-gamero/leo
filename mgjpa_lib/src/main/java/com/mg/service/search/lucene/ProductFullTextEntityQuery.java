package com.mg.service.search.lucene;

import javax.persistence.EntityManager;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

import com.mg.model.Product;


public class ProductFullTextEntityQuery extends FullTextEntityQuery<Product> {
	
	// if the expression above is evaluated the value returned is stored here for dirty checks
	
	public ProductFullTextEntityQuery(){
		super( Product.class );
	}

	private static final long serialVersionUID = 7215682440269353986L;
	
	@Override
	protected FullTextQuery getQuery(EntityManager em) {
		if( query == null ) {
			MultiFieldQueryParser parser = new MultiFieldQueryParser( getFields()
					.toArray( new String[getFields().size()] ), new StandardAnalyzer() );

			Query luceneQuery;
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

			//QueryBuilder qb = (QueryBuilder) fullTextEntityManager.getSearchFactory().buildQueryBuilder();
			// keep a reference to the result of the el that is used 
			// to create the lucene query for dirty checks
			
			//QueryBuilder b = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Product.class).get();
			/*Query luceneQuery1 = qb.bool()
			    .should(qb.keyword().onField("translationByNameTransId.translationEntries.text").matching(super.applyQueryMode( queryValue )).createQuery())
			    .createQuery();
			*/
			
			try {
				
				luceneQuery = parser.parse( super.applyQueryMode( queryValue ) );
				
				/*luceneQuery = ((QueryBuilder) qb)
						  .keyword()
						  .onFields("translationByNameTransId.translationEntries.text")
						  .matching(super.applyQueryMode( queryValue ))
						  .createQuery();
						  */
				//bQuery.add( parser.parse( super.applyQueryMode( queryValue ) ), BooleanClause.Occur.MUST );
				//bQuery.add( MultiFieldQueryParser.parse( applications.toArray( new String[applications.size()] ), fields.toArray( new String[fields.size()] ), new StandardAnalyzer() ),  BooleanClause.Occur.MUST );
				//luceneQuery = bQuery;
		
			} catch( ParseException e ) {
				throw new RuntimeException( e );
			}

			query = fullTextEntityManager.createFullTextQuery( luceneQuery, clazz );
			
			
			query.setFirstResult( getFirstResult() );
			
			if( getMaxResults() == 0 ) {
				throw new RuntimeException( "max results must be > 0" );
			}
			
			query.setMaxResults( getMaxResults() );
			
		}
		return query;
	}
	
}
