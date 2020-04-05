import config.MainConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        String configFile;
        if (args.length == 0) {
            configFile = "config.yaml";
        } else {
            configFile = args[0];
        }
        try {
            MainConfig config = main.getConfig(configFile);
            System.out.println("got config");
            DataSource dataSource = new DataSource(config);
            System.out.println("got dataSource");
            DatabaseDao databaseDao = new DatabaseDao(config, dataSource);
            System.out.println("got databaseDao");
            databaseDao.processTable();
        } catch (FileNotFoundException ex) {
            System.out.println("��������� ������ ��������� � ����� ����������, " +
                    "������� �������� ���� ������������ ������ ���������� � ��������� ����� �������");
        }

    }

    private MainConfig getConfig(String pathToConfig) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        FileInputStream inputStream;
        inputStream = new FileInputStream(new File(pathToConfig));
        return yaml.loadAs(inputStream, MainConfig.class);
    }
}
