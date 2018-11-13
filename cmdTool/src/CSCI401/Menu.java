package CSCI401;

import ChangeCSVFormat.ChangeCSVFormat;
import FileGeneration.FileGeneration;
import FileGeneration.JsonParser;
import template.SPARQLquery;
import template.TemplateHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Menu {
	public static boolean isStringInt(String s)
	{
	    try
	    {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException ex)
	    {
	        return false;
	    }
	}
	
	public static boolean isStringDouble(String s) {
		try {
			Double.valueOf(s);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
	//input: FRED_CLARKE  output:Fred Clarke
	public static String toProperCase(String s) {
	    return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
	}
	
	public static String stringCoverter(String inputString) {
		  String[] parts = inputString.split("_");
		   String camelCaseString = "";
		   for (String part : parts){
		      camelCaseString = camelCaseString +  toProperCase(part)+" ";
		   }
		   return camelCaseString;
	}
	
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
        else if(choice.equals("3")) {
        	String csvFileName = fileName;
            ChangeCSVFormat csvConverter = new ChangeCSVFormat();
            csvConverter.changeFormat(csvFileName);
            List<List <String>>output = csvConverter.getInstancesAsList();
            JsonParser jp = new JsonParser();
            for(List <String> l : output) {
            	if(!isStringInt(l.get(0)) && !isStringDouble(l.get(0))) {
            		for(int j=0; j<l.size(); j++) {
            			SPARQLquery query = new SPARQLquery();
            			String correctName = stringCoverter(l.get(j));
            			query.generateSPARQLQueryFile(correctName.trim());
            			String command = "sh out.sh";
            			cmdTool.executeCommand(command);
            			String outputstr = cmdTool.getOutputStr();
            			jp.writeToFile(outputstr, correctName.trim());
            		}
            	}
            }
            System.out.println("Finished.");
            
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
