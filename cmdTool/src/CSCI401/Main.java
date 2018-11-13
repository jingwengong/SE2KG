package CSCI401;

import FileGeneration.FileGeneration;
import template.TemplateHelper;
import ChangeCSVFormat.ChangeCSVFormat;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import template.TemplateHelper;
import template.SPARQLquery;
import FileGeneration.JsonParser;
public class Main {
	
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

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Please choose linkage file option:\n"+
                            "   1. input linkage filename\n" +
                            "   2. auto generate linkage file\n"+
                            "   3. use SPARQL query to Wikidata");
        Scanner reader = new Scanner(System.in);
        String inputFileName = "";
        String choice = reader.nextLine();
        CmdProcessor cmdTool = new CmdProcessor();

        if(choice.equals("1"))
        {
            System.out.println("Please enter you input xml filename");
            inputFileName = reader.nextLine();
        }
        else if(choice.equals("2"))
        {
            System.out.println("Please enter you input csv filename");
            String csvFileName = reader.nextLine();
            ChangeCSVFormat csvConverter = new ChangeCSVFormat();
            csvConverter.changeFormat(csvFileName);
            String convertedCsvFileName = new File("headers.csv").getAbsolutePath();
            TemplateHelper templateHelper = new TemplateHelper(convertedCsvFileName, "outputLinkage.xml", "0.8");
            inputFileName = "outputLinkage.xml";
            templateHelper.generateOutputFile();
        }
        else if(choice.equals("3")) {
        	System.out.println("Please enter you input csv filename");
            String csvFileName = reader.nextLine();
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
            Thread.sleep(1000);
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
            return;
        outputFileName = "C:\\git\\lime\\LIMES\\limes-core\\target\\.server-storage\\" + id +"\\" + outputFileName;
        Scanner input = new Scanner(new File(outputFileName));
        FileWriter writer = new FileWriter("output.txt");
        while (input.hasNextLine())
        {
            String line = input.nextLine();
//            System.out.println(line);
            writer.write(line+"\n");
        }
        writer.close();
        FileGeneration fg = new FileGeneration();
        fg.GenerateFileOutput("output.txt");

//        System.out.println(command);

//        try {
//            cmdTool.dumpOutput("output.txt");
//        } catch (IOException ie) {
//            System.out.println(ie.getMessage());
//        }
    }


}


