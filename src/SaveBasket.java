import java.io.Serializable;

public class SaveBasket implements Serializable {

    private static final long serialVersionUID = 1L;
    private String[] products;
    private int[] prices;
    private int[] amount;

    public SaveBasket(String[] products, int[] prices, int[] amounts) {
        this.products = products;
        this.prices = prices;
        this.amount = amounts;
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

    public int[] getAmount() {
        return amount;
    }
}
