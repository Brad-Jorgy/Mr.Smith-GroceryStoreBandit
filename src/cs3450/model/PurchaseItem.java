package cs3450.model;

public class PurchaseItem {
	private Product mProduct;
	private int mQuantity;

	public PurchaseItem(Product product, int quantity) {
		mProduct = product;
		mQuantity = quantity;
	}

	public int getId() {
		return mProduct.getId();
	}

	public void setProduct(Product product) { mProduct = product; }

	public int getQuantity() { return mQuantity; }

	public Product getProduct() {
		return mProduct;
	}

	@Override
	public String toString() {
		return mProduct.getName() + "  price:" + mProduct.getPrice() + "  number ordered:" + mQuantity + "  total price:" + String.format("%.2f", mProduct.getPrice() * mQuantity);
	}
}