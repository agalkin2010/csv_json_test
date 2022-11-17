import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Settings {

    public boolean enableLoad;
    public boolean enableSave;
    public boolean enableLog;
    public String loadFileName;
    public String saveFileName;
    public String logFileName;
    public String loadFileFormat;
    public String saveFileFormat;


    public void readSettings(File file) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            Node root = doc.getDocumentElement();

            NodeList list = root.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node chieldNode = list.item(i);

                NodeList listChield = chieldNode.getChildNodes();

                for (int j = 0; j < listChield.getLength(); j++) {
                    String setName = listChield.item(j).getNodeName();
                    String setValue = listChield.item(j).getTextContent();

                    if (chieldNode.getNodeName() == "load") {
                        if (setName == "enabled") enableLoad = Boolean.valueOf(setValue);
                        if (setName == "fileName") loadFileName = setValue;
                        if (setName == "format") loadFileFormat = setValue;
                    }

                    if (chieldNode.getNodeName() == "save") {
                        if (setName == "enabled") enableSave = Boolean.valueOf(setValue);
                        if (setName == "fileName") saveFileName = setValue;
                        if (setName == "format") saveFileFormat = setValue;
                    }

                    if (chieldNode.getNodeName() == "log") {
                        if (setName == "enabled") enableLog = Boolean.valueOf(setValue);
                        if (setName == "fileName") logFileName = setValue;
                    }
                }
            }


        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }
}
