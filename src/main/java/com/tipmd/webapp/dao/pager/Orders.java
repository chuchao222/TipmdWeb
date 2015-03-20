package com.tipmd.webapp.dao.pager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bowee2010
 *
 * 排序类
 */
class Orders {
	private List<Order> orderList;
	private final static String COMMA = ",";
	private final static String BLANK = " ";
	
	public Orders addOrder(String property, String ordering) {
		getOrderList().add(new Order(property, ordering));
		return this;
	}
	
	public void addOrder(Order order) {
		getOrderList().add(order);
	}
	
	
	public static Orders buildEmptyOrders() {
		return new Orders(0);
	}

	
	public List<Order> getOrderList() {
		return orderList;
	}
	
	public boolean isContainAnyOrder() {
		return orderList.size() > 0;
	}

	/**
	 * 将Orders对象转化成sql语句
	 * @return order by sql语句
	 */
	public String convertToSQL() {
		if(orderList == null || orderList.size() == 0) return "";
		
		StringBuilder s = new StringBuilder("ORDER BY");
		for(Order order:orderList) {
			s.append(BLANK);
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
		private String ordering;
		
		public Order(String property, String ordering) {
			this.property = property;
			this.ordering = ordering;
		}
		
		public String getProperty() {
			return property;
		}
	

		public String getOrdering() {
			return ordering;
		}
		
		@Override
		public String toString() {
			return "Order [property=" + property + ", ordering=" + ordering + "]";
		}
		
	}
}
