package by.com.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    final static Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.trace("Start trace");
        LOGGER.info("Entering application.");

        Run run = new Run();
        Properties properties = run.asProperties("configuration.properties");
        LOGGER.info("Properties have been gotten");
        String path = properties.getProperty("path");
        String suffix = properties.getProperty("suffix");
        String[] strings = properties.getProperty("fileNames").split(",");

        List<String> extended = new ArrayList<>();
        List<String> namesWithoutExtend = new ArrayList<>();
        List<File> renamedFiles = new ArrayList<>();

        for (String s : strings) {
            namesWithoutExtend.add(s.split("\\.")[0]);
            extended.add("." + s.split("\\.")[1]);
        }
        try {
            File file;
            int count = 0;
            for (String nameFile : strings) {
                file = new File(path + nameFile);
                if (file.isFile()) {
                    File newFile = new File(path + suffix + "_" + namesWithoutExtend.get(count) + extended.get(count));
                    if (file.renameTo(newFile)) {
                        renamedFiles.add(newFile);
                        LOGGER.info(nameFile + " have been renamed" + "\n");
                        count++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Exception happen!", e);
        }
        System.out.println(renamedFiles);
        LOGGER.info("Application closed.");
        LOGGER.trace("End trace");
    }
}