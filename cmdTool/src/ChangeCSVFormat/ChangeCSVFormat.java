package ChangeCSVFormat;

import java.io.*;

public class ChangeCSVFormat {

    private String[] headers;

    public ChangeCSVFormat() {

    }

    public void changeFormat(String csvFile) {
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            if ((line = br.readLine()) != null) {
                String[] headers = line.split(",");
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
        String filename = "output/headers.csv";
        try {
            FileWriter fileWriter = new FileWriter(filename);

            for (int i = 0; i < headers.length; i++) {
                fileWriter.write(headers[i] + "\n");
            }
            fileWriter.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }



}
