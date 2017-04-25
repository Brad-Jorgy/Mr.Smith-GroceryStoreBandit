package cs3450.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Order {
	private Map<Product, Integer> mItemsList;
	private int orderNumber;
	private double mTotal;
	private boolean isPremium = false;

	public Order() {
		mItemsList = new HashMap<Product, Integer>();
		mTotal = 0;
	}
	public Order(Product product, Integer id) {
		mItemsList = new HashMap<Product, Integer>();
		mTotal = 0;
	}

	public int getOrderNumber() {	return this.orderNumber;	}
	public void setOrderNumber(int orderNumber) { this.orderNumber = orderNumber; }
	public int getOrderSize() {	return mItemsList.size(); }
	public int getItem(Product product) {	return mItemsList.get(product);	}
	public double getTotal() { return mTotal; }
	public void clearOrder() {
		mItemsList.clear();
		orderNumber = -1;
		mTotal = 0;
		isPremium = false;
	}
	public void addItem(Product product, int quantity) {
		Integer q = mItemsList.get(product);
		if (q == null) {
			q = quantity;
		} else {
			q = q + quantity;
		}
		mItemsList.put(product, q);
		if(isPremium)
			mTotal = mTotal + product.getDiscountPrice() * quantity;
		else
			mTotal = mTotal + product.getPrice() * quantity;
	}
	public void editItem(Product product, int quantity) {
		Integer q = mItemsList.get(product);
		if (q != null) {
			mItemsList.put(product, quantity);
			if(isPremium)
				mTotal = mTotal - product.getDiscountPrice() * (q - quantity);
			else
				mTotal = mTotal - product.getPrice() * (q - quantity);
		}
	}
	public void removeItem(Product product) {
		Integer q = mItemsList.get(product);
		if (q != null) {
			mItemsList.remove(product);
			if(isPremium)
				mTotal = mTotal - product.getDiscountPrice() * q;
			else
				mTotal = mTotal - product.getPrice() * q;
		}
	}

	public List<PurchaseItem> getOrderList() {
		List<PurchaseItem> itemList = new LinkedList<>();
		for (Entry<Product, Integer> entry : mItemsList.entrySet()) {
			Product p = entry.getKey();
			Integer q = entry.getValue();
			itemList.add(new PurchaseItem(p, q));
		}
		return itemList;
	}

	public void setPremium(boolean isPremium){ this.isPremium = isPremium; }
}
