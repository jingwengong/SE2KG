package ChangeCSVFormat;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeCSVFormat {

    private List<String> headers;
    private List<List<String>> instances;

    public ChangeCSVFormat() {
        headers = new ArrayList<>();
        instances = new ArrayList<>();
    }

    public void changeFormat(String csvFile) {
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            if ((line = br.readLine()) != null) {
                headers = Arrays.asList(line.split(","));
            }
            while ((line = br.readLine()) != null) {
                instances.add(Arrays.asList(line.split(",")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.writeToFile();
    }

    private void writeToFile() {
        String filename = "headers.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write("label\n");
            for (int i = 0; i < headers.size(); i++) {
                fileWriter.write(headers.get(i) + "\n");
            }
            fileWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }
    public List<List<String>> getInstancesAsList() {
        return instances;
    }



}
