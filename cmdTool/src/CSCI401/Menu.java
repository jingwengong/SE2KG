package CSCI401;

import ChangeCSVFormat.ChangeCSVFormat;
import FileGeneration.FileGeneration;
import template.TemplateHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
    public String processOption(String choice, String fileName)
    {
        CmdProcessor cmdTool = new CmdProcessor();
        String ret = "stupid";
        String inputFileName = "";
        if(choice.equals("1"))
        {
            inputFileName = fileName;
        }
        else if(choice.equals("2"))
        {
            String csvFileName = fileName;
            ChangeCSVFormat csvConverter = new ChangeCSVFormat();
            csvConverter.changeFormat(csvFileName);
            String convertedCsvFileName = new File("headers.csv").getAbsolutePath();
            TemplateHelper templateHelper = new TemplateHelper(convertedCsvFileName, "outputLinkage.xml", "0.8");
            inputFileName = "outputLinkage.xml";
            templateHelper.generateOutputFile();
        }
        String command = "curl -F config_file=@" + inputFileName + "  http://localhost:8080/submit";
        cmdTool.executeCommand(command);
        String id = cmdTool.getOutputStr().replaceAll("\\D+","");
        System.out.println(id);

        command = "curl http://localhost:8080/status/" + id;
        cmdTool.executeCommand(command);
        String output = cmdTool.getOutputStr();
        while(output.indexOf("Request has been processed")==-1)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread cannot sleep");
                return ret;
            }
            cmdTool.executeCommand(command);
            output = cmdTool.getOutputStr();
        }
        command = "curl http://localhost:8080/results/" + id;
        cmdTool.executeCommand(command);
        output = cmdTool.getOutputStr();
        String outputFileName = "";
        if(output.indexOf(".nt\"")!=-1)
            outputFileName = output.substring(output.indexOf("[\"")+2,output.indexOf(".nt\"")+3);
        else
            return ret;
        outputFileName = "C:\\git\\lime\\LIMES\\limes-core\\target\\.server-storage\\" + id +"\\" + outputFileName;
        Scanner input = null;
        try {
            input = new Scanner(new File(outputFileName));
        } catch (FileNotFoundException e) {
            System.out.println("cannot find the file from LIME server");
            return ret;
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("output.txt");
        } catch (IOException e) {
            System.out.println("cannot open output.txt");
        }
        while (input.hasNextLine())
        {
            String line = input.nextLine();
//            System.out.println(line);
            try {
                writer.write(line+"\n");
            } catch (IOException e) {
                System.out.println("cannot write to output.txt");
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("cannot close output.txt");
        }
//        FileGeneration fg = new FileGeneration();
//        fg.GenerateFileOutput("output.txt");
        return new File("output.txt").getAbsolutePath();
    }
}
