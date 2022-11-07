import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static final String fileName = "basket.txt";

    public static void main(String[] args) {
        //creat products a nd prices arrays
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        Basket basket;
        File file = new File(fileName);
        if(file.exists() && file.length() > 0){
            basket = Basket.loadFromTxtFile(file);
        }else{
            basket = new Basket(products, prices);
        }

        //print out available list
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        //create variables
        Scanner scanner = new Scanner(System.in);
        int[] order = new int[products.length];
        boolean[] selected = new boolean[products.length];
        Arrays.fill(order, 0);
        int totalPrice = 0;

        //infinite cycle input data from user
        while (true) {
            System.out.println();
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();

            //exit from loop
            if (input.equals("end")) {
                break;
            }

            //parse data from user
            String[] inputData = input.split(" ");
            int number = Integer.parseInt(inputData[0]) - 1;
            int pieces = Integer.parseInt(inputData[1]);

            basket.addToCart(number, pieces);
            basket.saveTxt(file);

        }

        //total prices
        System.out.println();

        basket.printCart();

    }

}
