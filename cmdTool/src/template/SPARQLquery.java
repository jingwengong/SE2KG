package template;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SPARQLquery {
	private FileWriter fw = null;
	private PrintWriter pw = null;
	
	public SPARQLquery() {
		
	}
	
	public void generateSPARQLQueryFile(String objectName) {
		try {
			//fw = new FileWriter(new File(".").getAbsolutePath()+"//queries//out"+objectName+".txt",true);
			fw = new FileWriter("out.sh");
			pw = new PrintWriter(fw);
			pw.println("curl --header \"Accept: application/sparql-results+json\"  -G 'https://query.wikidata.org/sparql' --data-urlencode query='\n" + 
					"SELECT ?resource {");
			pw.println("  ?resource rdfs:label \""+objectName+"\"@en");
			pw.println("}'");
			pw.flush();
		}catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}finally {
			if(pw!=null) {
				pw.close();
			}
			
		}

		
	}
	

}
