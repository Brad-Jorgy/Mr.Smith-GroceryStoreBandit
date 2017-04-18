package cs3450.model;

public class PurchaseItem {
	private int mCustomerId;
	private int mOrderId;
	private Product mProduct;
	private int mQuantity;

	public PurchaseItem(int customerId, int orderId, Product product, int quantity) {
		mCustomerId = customerId;
		mOrderId = orderId;
		mProduct = product;
		mQuantity = quantity;
	}

	public int getOrderId() {return mOrderId;}

	public int getCustomerId() {return mCustomerId;}

	public void setOrderId(int orderId) {mOrderId = orderId;}

	public void setCustomerId(int customerId) {mCustomerId = customerId;}

	public int getId() {
		return mProduct.getId();
	}

	public void setProduct(Product product) { mProduct = product; }

	public int getQuantity() { return mQuantity; }

	public Product getProduct() {
		return mProduct;
	}

	public void setQuantity(int quantity) { mQuantity = quantity; }

	@Override
	public String toString() {
		return mProduct.getName() + "  price:" + mProduct.getPrice() + "  number ordered:" + mQuantity + "  total price:" + String.format("%.2f", mProduct.getPrice() * mQuantity);
	}
}