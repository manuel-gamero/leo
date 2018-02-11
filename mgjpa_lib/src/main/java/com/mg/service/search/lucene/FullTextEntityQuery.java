package com.mg.service.search.lucene;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;

public class FullTextEntityQuery<E> implements QueryResults<E> {

	private final static Logger log = Logger.getLogger( FullTextEntityQuery.class );
	
	public enum Mode {
		DEFAULT,
		SIMPLE_STARTS_WITH
	}
	
	private static final long serialVersionUID = 6363827108271376591L;

	protected transient FullTextQuery query;

	private List<String> fields;
	protected Class<E> clazz;

	private int firstResult;
	private int maxResults;
		
	// if the expression above is evaluated the value returned is stored here for dirty checks
	protected String queryValue;
	
	private Mode mode;
	private boolean dirty;
	protected List<E> items;

	protected FullTextEntityQuery() {
	}

	public FullTextEntityQuery( Class<E> clazz ) {
		this.clazz = clazz;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public List<E> getItems(EntityManager em) {		
		if( items == null || isDirty(queryValue) ) {
			refresh();
			try {
				items = getQuery(em).getResultList();
			} catch (Exception e) {
				items = new ArrayList<E>();
			}
		}
		return items;
	}

	protected boolean isDirty(String searchText) {		
		return dirty || !searchText.equals( queryValue );		
	}

	protected FullTextQuery getQuery(EntityManager em) {
		if( query == null ) {
			MultiFieldQueryParser parser = new MultiFieldQueryParser( getFields().toArray( new String[getFields().size()] ), new StandardAnalyzer() );
			org.apache.lucene.search.Query luceneQuery;
			
			try {
				luceneQuery = parser.parse( applyQueryMode( queryValue ) );				
			} catch( Exception e ) {
				throw new RuntimeException( e );
			}

			query = Search.getFullTextEntityManager(em).createFullTextQuery( luceneQuery, clazz );
			
			
			query.setFirstResult( getFirstResult() );
			
			if( getMaxResults() == 0 ) {
				throw new RuntimeException( "max results must be > 0" );
			}
			
			query.setMaxResults( getMaxResults() );
			
		}
		return query;
	}
	
	

	protected String applyQueryMode( String query ) {
		
		switch( getMode() ) {
			case SIMPLE_STARTS_WITH :
				return simpleStartsWith( query );
			case DEFAULT :
				return query;
			default:
				throw new RuntimeException( "unhandled: " + getMode()  );
		}
	}

	private String simpleStartsWith( String query ) {
		String b = "";
		String reg = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
		String numReg = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
		Pattern p = Pattern.compile(reg);
		String a = query;
			
		if( query.startsWith( "::" ) ) {
			// Dev string
			b = a.substring( 2 );
		} else {
			a = a.replace( ")", " ) ");
			a = a.replace( "(", " ( ");
			
			Matcher m = p.matcher(a);
			boolean ignoreConj = false;

			while (m.find()) {
				if(	m.group().equals( "AND" ) || m.group().equals( "&&" ) ||
					m.group().equals( "OR" )  || m.group().equals( "||" ) ||
					m.group().equals( "NOT" ) || m.group().equals( "(" ) ||
					m.group().equals( ")" ) ) {
					
					b += " " + m.group() + " ";
					ignoreConj = true;
				} else {						
					if ( b.length() > 0 && !ignoreConj) {
						b += " && ";
					} else if ( ignoreConj ) {
						ignoreConj = false;
					}
					
					b += m.group();
					
					if ( !m.group().endsWith( "\"" ) &&
						 !m.group().endsWith( ")" ) &&
						 !m.group().endsWith( "(" ) &&
						 !m.group().endsWith( "~" ) &&
						 !m.group().matches( numReg ) ) {
						b += "*";
					}
				}
		    }  
			if ( b.matches(  numReg ) ) {
				// query is only one number
				b += "*";
			}
		}		
		log.debug( "Search [SIMPLE_STARTS_WITH]: (Original: " + query + "), (Modified: " + b + ")" );
		return b;
	}

	public void setFields( List<String> fields ) {
		this.fields = fields;
	}

	public List<String> getFields() {
		if( fields == null ) {
			fields = new ArrayList<String>();
		}
		return fields;
	}

	public void setSearchText( String searchText ) {
		this.queryValue = searchText;
	}

	@Override
	public void refresh() {
		query = null;
		items = null;
		dirty = false;
	}

	@Override
	public int getFirstResult() {
		return this.firstResult;
	}

	@Override
	public void setFirstResult( int firstResult ) {
		if( firstResult != this.firstResult ) {
			dirty = true;
		}
		this.firstResult = firstResult;
	}

	@Override
	public int getMaxResults() {
		return maxResults;
	}

	@Override
	public void setMaxResults( int maxResults ) {
		if( maxResults != this.maxResults ) {
			dirty = true;
		}
		this.maxResults = maxResults;
	}

	@Override
	public int getUnpagedCount(EntityManager em) {
		try { 
			return getQuery(em).getResultSize();
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public String getOrderColumn() {
		throw new NotImplementedException("getOrderColumn");
	}

	@Override
	public void setOrderColumn( String orderColumn ) {
		throw new NotImplementedException("setOrderColumn");
	}

	@Override
	public String getOrder() {
		throw new NotImplementedException("getOrder");
	}

	@Override
	public void setOrder( String order ) {
		throw new NotImplementedException("setOrder");
	}

	@Override
	public OrderDirection getOrderDirection() {
		throw new NotImplementedException("getOrderDirection");
	}

	@Override
	public void setOrderDirection( OrderDirection pDirection ) {
		throw new NotImplementedException("setOrderDirection");
	}

	public void setMode( Mode mode ) {
		if( this.mode != mode ) {
			this.mode = mode;
			dirty = true;
		}
	}

	public Mode getMode() {
		return mode;
	}
}
