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
            List<List<String>> temp = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                temp.add(Arrays.asList(line.split(",")));
            }
            if (temp.size() != 0) {
                for (int j = 0; j < temp.get(0).size(); j++) {
                    List<String> curr = new ArrayList<>();
                    for (int i = 0; i < temp.size(); i++) {
                        curr.add(temp.get(i).get(j));
                    }
                    instances.add(curr);
                }
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

    public List<String> getHeaders() {
        return headers;
    }

    public List<List<String>> getInstancesAsList() {
        return instances;
    }



}
