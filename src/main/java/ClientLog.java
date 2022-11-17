import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {

    private List<String[]> log = new ArrayList<String[]>();

    public void log(int productNum, int amount) {

        String[] line = (String.valueOf(productNum) + "," + String.valueOf(amount)).split(",");
        this.log.add(line);

    }

    public void exportAsCSV(File txtFile) {

        String[] firstString = "productNum,amount".split(",");
        boolean logFileNew = false;

        if (!txtFile.exists()) logFileNew = true;

        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {

            if (logFileNew) writer.writeNext(firstString);
            writer.writeAll(log);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
