import java.io.File;
import java.util.Scanner;

public class Main {

    static final String setFileName = "shop.xml";

    public static void main(String[] args) {
        //create products and prices arrays
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};

        Settings settings = new Settings();
        File set = new File(setFileName);
        settings.readSettings(set);

        File fileLoad = new File(settings.loadFileName);
        File fileSave = new File(settings.saveFileName);

        Basket basket;

        if (settings.enableLoad && fileLoad.exists() && fileLoad.length() > 0) {
            if (settings.loadFileFormat.equals("json")) basket = Basket.loadFromJSON(fileLoad);
            else if (settings.loadFileFormat.equals("text")) basket = Basket.loadFromTxtFile(fileLoad);
            else {
                basket = new Basket(products, prices);
            }
        } else {
            basket = new Basket(products, prices);
        }

        File csvFile = new File(settings.logFileName);
        ClientLog clientLog = new ClientLog();

        //print out available list
        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i] + " " + prices[i] + " руб/шт");
        }

        //create variables
        Scanner scanner = new Scanner(System.in);

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
            if (settings.enableSave) {
                if (settings.saveFileFormat.equals("json")) basket.saveJSON(fileSave);
                else if (settings.saveFileFormat.equals("text")) basket.saveTxt(fileSave);
            }
            clientLog.log(number + 1, pieces);

        }

        //total prices
        System.out.println();

        basket.printCart();
        if (settings.enableLog) clientLog.exportAsCSV(csvFile);

    }

}
