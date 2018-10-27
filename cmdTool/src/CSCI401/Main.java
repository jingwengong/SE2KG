package CSCI401;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please input linkage name");
        String inputFileName = reader.nextLine();
        String command = "curl -F config_file=@" + inputFileName + "  http://localhost:8080/submit";
        CmdProcessor cmdTool = new CmdProcessor();
        cmdTool.executeCommand(command);
        String id = cmdTool.getOutputStr().replaceAll("\\D+","");
        System.out.println(id);
        Thread.sleep(1000);
        command = "curl http://localhost:8080/results/" + id;
        cmdTool.executeCommand(command);
        String output = cmdTool.getOutputStr();
        String outputFileName = output.substring(output.indexOf("[\"")+2,output.indexOf(".nt\"")+3);
        outputFileName = "C:\\git\\lime\\LIMES\\limes-core\\target\\.server-storage\\" + id +"\\" + outputFileName;
        Scanner input = new Scanner(new File(outputFileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        while (input.hasNextLine())
        {
            writer.write(input.nextLine());
            writer.newLine();
        }

        System.out.println(command);

//        try {
//            cmdTool.dumpOutput("output.txt");
//        } catch (IOException ie) {
//            System.out.println(ie.getMessage());
//        }
    }


}

