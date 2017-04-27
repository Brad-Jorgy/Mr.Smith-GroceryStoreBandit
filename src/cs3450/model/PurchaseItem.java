package cs3450.model;

public class PurchaseItem {
	private int mCustomerId;
	private int mOrderId;
	private Product mProduct;
	private int mQuantity;
	private int quantityChange;

	public PurchaseItem(int customerId, int orderId, Product product, int quantity) {
		mCustomerId = customerId;
		mOrderId = orderId;
		mProduct = product;
		mQuantity = quantity;
	}
	public int getId() {  return this.mProduct.getId(); }
	public void setProduct(Product product) { this.mProduct = product; }
	public int getQuantity() { return this.mQuantity; }
	public Product getProduct() {	return this.mProduct; }

	public int getOrderId() {return mOrderId;}

	public int getCustomerId() {return mCustomerId;}

	public void setOrderId(int orderId) {mOrderId = orderId;}

	public void setCustomerId(int customerId) {mCustomerId = customerId;}


	public void setQuantityChange(int quantitySet){quantityChange = quantitySet;}

	public int getQuantityChange(){return quantityChange;}

	//public void setQuantity(int quantity) { mQuantity = quantity; }

	@Override
	public String toString() {
		return mProduct.getName() + "  price: $" + mProduct.getPrice() + ",  number ordered: " + mQuantity + ",  total price: $" + String.format("%.2f", mProduct.getPrice() * mQuantity);
	}
	public String toStringDiscount(){
		double diff = mProduct.getPrice() - mProduct.getDiscountPrice();
		if(diff > 0)
			return mProduct.getName() + "  price: $" + mProduct.getDiscountPrice() + " (discount: $" + String.format("%.2f", diff) + "),  number ordered: " + mQuantity + ",  total price: $" + String.format("%.2f", mProduct.getDiscountPrice() * mQuantity);
		return mProduct.getName() + "  price: $" + mProduct.getPrice() + ",  number ordered: " + mQuantity + ",  total price: $" + String.format("%.2f", mProduct.getPrice() * mQuantity);
	}
}
