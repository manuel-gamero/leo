package com.mg.service.search.lucene;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

public interface QueryResults<I> extends Serializable {

	public static enum OrderDirection {
		ASC( "asc" ),
		DESC( "desc" );

		String _code;

		private OrderDirection( String pCode ) {
			_code = pCode;
		}

		public String getCode() {
			return _code;
		}

		public static OrderDirection fromCode( String pCode ) {
			for( OrderDirection value : OrderDirection.values() ) {
				if( value.getCode().equals( pCode ) ) {
					return value;
				}
			}
			throw new IllegalArgumentException( "No value has code: " + pCode );
		}
	}

	public List<I> getItems(EntityManager em);

	public int getUnpagedCount(EntityManager em);

	public int getFirstResult();

	public void setFirstResult( int firstResult );

	public int getMaxResults();

	public void setMaxResults( int maxResults );

	public String getOrder();

	public void setOrder( String order );

	public String getOrderColumn();

	public void setOrderColumn( String orderColumn );

	public OrderDirection getOrderDirection();

	public void setOrderDirection( OrderDirection pDirection );

	public void refresh();

}
