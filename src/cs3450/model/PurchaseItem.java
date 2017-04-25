package cs3450.model;

public class PurchaseItem {
	private Product mProduct;
	private int mQuantity;

	public PurchaseItem(Product product, int quantity) {
		this.mProduct = product;
		this.mQuantity = quantity;
	}
	public int getId() {  return this.mProduct.getId(); }
	public void setProduct(Product product) { this.mProduct = product; }
	public int getQuantity() { return this.mQuantity; }
	public Product getProduct() {	return this.mProduct; }
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
