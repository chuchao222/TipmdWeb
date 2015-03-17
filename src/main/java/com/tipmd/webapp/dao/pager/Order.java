package com.tipmd.webapp.dao.pager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author bowee2010
 *
 * 排序类
 */
public class Order {
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

	
	public Order(String property, Ordering ordering) {
		this.property = property;
		this.ordering = ordering;
	}
	
	public static Order buildOrder(String property, Ordering ordering) {
		return new Order(property, ordering);
	}
	
	public static List<Order> buildEmptyOrderList() {
		return new ArrayList<Order>(0);
	}
	
	//TODO: 通过 orderString 创建Order List
	public static List<Order> buildOrderList(String orderString) {
		return null;
	}
	
	
	private String property;
	private Ordering ordering;
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
}
