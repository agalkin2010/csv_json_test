import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.List;

public class Basket {

    private int[] amounts;
    private String[] products;
    private int[] prices;

    private int totalPrice;

    public Basket(String[] products, int[] prices) {

        this.products = products;
        this.prices = prices;
        this.amounts = new int[products.length];

    }


    public void addToCart(int productNum, int amount) {

        this.amounts[productNum] += amount;

    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        this.totalPrice = 0;
        for (int i = 0; i < this.products.length; i++) {
            if (this.amounts[i] != 0) {
                System.out.println(this.products[i] + " " + this.amounts[i] + " шт " + this.amounts[i] * this.prices[i] + " руб");
                this.totalPrice += this.amounts[i] * this.prices[i];
            }

        }
        System.out.println("Итого: " + this.totalPrice + " руб");
    }


    public void saveTxt(File textFile) {

        try (FileWriter writer = new FileWriter(textFile, false)) {
            String sProducts = this.products[0];
            String sPrices = String.valueOf(this.prices[0]);
            String sAmounts = String.valueOf(this.amounts[0]);
            if (this.products.length > 1) {
                for (int i = 1; i < this.products.length; i++) {
                    sProducts += " " + this.products[i];
                    sPrices += " " + String.valueOf(this.prices[i]);
                    sAmounts += " " + String.valueOf(this.amounts[i]);
                }
            }
            writer.write(sProducts);
            writer.append("\n");
            writer.write(sPrices);
            writer.append("\n");
            writer.write(sAmounts);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void saveJSON(File jsonfile) {

        CurBasket curBasket = new CurBasket();
        curBasket.productsName = this.products;
        curBasket.productPrice = this.prices;
        curBasket.productAmount = this.amounts;

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try (FileWriter writer = new FileWriter(jsonfile)) {
            writer.write(gson.toJson(curBasket));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromTxtFile(File textFile) {

        try (FileReader reader = new FileReader(textFile)) {

            String s = "";
            int c;
            while ((c = reader.read()) != -1) s += (char) c;
            String[] arr = s.split("\n");
            String[] sProducts = arr[0].split(" ");
            String[] sPrices = arr[1].split(" ");
            String[] sAmount = arr[2].split(" ");

            int[] amounts = new int[sProducts.length];
            int[] prices = new int[sProducts.length];
            for (int i = 0; i < sProducts.length; i++) {
                prices[i] = Integer.valueOf(sPrices[i]);
                amounts[i] = Integer.valueOf(sAmount[i]);
            }

            Basket basket = new Basket(sProducts, prices);
            basket.setAmounts(amounts);

            return basket;

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return null;

    }

    public static Basket loadFromJSON(File jsonfile) {

        try (FileReader reader = new FileReader(jsonfile)) {

            String s = "";
            int c;
            while ((c = reader.read()) != -1) s += (char) c;

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            CurBasket curBasket = gson.fromJson(s, CurBasket.class);

            Basket basket = new Basket(curBasket.productsName, curBasket.productPrice);
            basket.setAmounts(curBasket.productAmount);

            return basket;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void setAmounts(int[] amounts) {
        this.amounts = amounts;
    }


}

class CurBasket {
    public String[] productsName;
    public int[] productPrice;
    public int[] productAmount;
}