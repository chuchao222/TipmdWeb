package com.tipmd.webapp.dao.pager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

/**
 * @author bowee2010
 *
 * 排序类
 */
public class Orders {
	private List<Order> orderList;
	private final static String COMMA = ",";
	private final static String BLANK = " ";
	private final static int INIT_ORDER_LIST_SIZE = 3;
	private final static Order.Ordering DEFAULT_ORDERING = Order.Ordering.ASC;
	
	public static Orders buildOrders(String property, Order.Ordering ordering) {
		if(StringUtils.isEmpty(property) || property.trim().length() == 0)
			throw new IllegalArgumentException("Build orders failed: You cannot specify a null property.");
		if(ordering == null)
			ordering = DEFAULT_ORDERING;
		
		Order order =  new Order(property, ordering);
		Orders orders = new Orders(INIT_ORDER_LIST_SIZE);
		orders.getOrderList().add(order);
		return orders;
	}
	
	public void addOrder(String property, Order.Ordering ordering) {
		getOrderList().add(new Order(property, ordering));
	}
	
	public void addOrder(Order order) {
		getOrderList().add(order);
	}
	
	
	public static Orders buildEmptyOrders() {
		return new Orders(0);
	}

	
	List<Order> getOrderList() {
		return orderList;
	}

	/**
	 * 将Orders对象转化成sql语句
	 * @return order by sql语句
	 */
	public String convertToSQL() {
		if(orderList == null || orderList.size() == 0) return "";
		
		StringBuilder s = new StringBuilder(" ORDER BY ");
		for(Order order:orderList) {
			s.append(order.getProperty());
			s.append(BLANK);
			s.append(order.getOrdering());
			s.append(COMMA);
		}
		s.deleteCharAt(s.length()-1);
		return s.toString();
	}
	
	private Orders(int size) { 
		orderList = new ArrayList<Order>(size);
	}
	
	public static class Order {
		private String property;
		private Ordering ordering;
		
		public Order(String property, Ordering ordering) {
			this.property = property;
			this.ordering = ordering;
		}
		
		public String getProperty() {
			return property;
		}
	
		public void setProperty(String property) {
			this.property = property;
		}
	
		public Ordering getOrdering() {
			return ordering;
		}
	
		public void setOrdering(Ordering ordering) {
			this.ordering = ordering;
		}
		
		@Override
		public String toString() {
			return "Order [property=" + property + ", ordering=" + ordering + "]";
		}
		
		public static enum Ordering {
			ASC, DESC;

			public static Ordering from(String value) {
				try {
					return Ordering.valueOf(value.toUpperCase(Locale.US));
				} catch (Exception e) {
					return ASC;
				}
			}
		}
	}
}
