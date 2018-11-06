package FileGeneration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileGeneration {
	private FileReader fr = null;
	private BufferedReader br = null;
	private FileWriter fw = null;
	private PrintWriter pw = null;
	
	public FileGeneration() {
	
	}
	
	public void GenerateFileOutput(String FileName) {
		try {
			fr = new FileReader(FileName);
			br = new BufferedReader(fr);
			fw = new FileWriter("TestOutput.txt");
			pw = new PrintWriter(fw);
			String line = br.readLine();
			while(line!=null) {
				writeLinkingResultsToFile(line, pw);
				line=br.readLine();
			}
			pw.flush();
		}catch(FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}finally {
			if(br!=null) {
				try {
					br.close();
				}catch(IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			}
			if(fr!=null) {
				try {
					fr.close();
				}catch(IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			}

			if(fw!=null) {
				try {
					fw.close();
				}catch(IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			}
		}
		
	}
	public void writeLinkingResultsToFile(String line, PrintWriter pw) {
		String[] splited = line.split("<");
		String object = splited[1].substring(0, splited[1].length()-2);
		pw.print(object+" ");
		String[] getRidOfSymble = splited[2].split("#");
		String relationship = getRidOfSymble[1];
		pw.print(" is "+relationship.substring(0, relationship.length()-2));
		pw.println(" "+splited[3].substring(0, splited[3].length()-3));
	}
	
//	public static void main(String[] args) {
//		FileGeneration fg = new FileGeneration();
//		fg.GenerateFileOutput("test.txt");
//	}
}
