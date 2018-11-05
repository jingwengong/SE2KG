package template;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TemplateHelper {

    private String inputFileDirectory;
    private String XMLFileDirectory;
    private String XMLString;
    private String matchThreshold;


    public TemplateHelper(String inputFileDirectory, String XMLFileDirectory, String matchThreshold) {
        this.inputFileDirectory = inputFileDirectory;
        this.XMLFileDirectory = XMLFileDirectory;
        this.matchThreshold = matchThreshold;
    }

    public void generateOutputFile() {
        this.XMLString = String.format(TemplateString, this.inputFileDirectory);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(XMLFileDirectory));
            writer.write(XMLString);
            writer.close();
        } catch (IOException ioe) {
            System.out.println("Cannot open the file with exception " + ioe.getMessage());
        }
    }

    private final String TemplateString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE LIMES SYSTEM \"limes.dtd\">\n" +
            "<LIMES>\n" +
            "\t<PREFIX>\n" +
            "\t\t<NAMESPACE>http://www.w3.org/1999/02/22-rdf-syntax-ns#</NAMESPACE>\n" +
            "\t\t<LABEL>rdf</LABEL>\n" +
            "\t</PREFIX>\n" +
            "\t<PREFIX>\n" +
            "\t\t<NAMESPACE>http://www.w3.org/2000/01/rdf-schema#</NAMESPACE>\n" +
            "\t\t<LABEL>rdfs</LABEL>\n" +
            "\t</PREFIX>\n" +
            "\t<PREFIX>\n" +
            "\t\t<NAMESPACE>http://www.w3.org/2002/07/owl#</NAMESPACE>\n" +
            "\t\t<LABEL>owl</LABEL>\n" +
            "\t</PREFIX>\n" +
            "\t<PREFIX>\n" +
            "\t\t<NAMESPACE>http://dbpedia.org/ontology/</NAMESPACE>\n" +
            "\t\t<LABEL>dbpedia-owl</LABEL>\n" +
            "\t</PREFIX>\n" +
            "\t<PREFIX>\n" +
            "\t\t<NAMESPACE>http://dbpedia.org/property/</NAMESPACE>\n" +
            "\t\t<LABEL>dbpedia-prop</LABEL>\n" +
            "\t</PREFIX>\n" +
            "\t<PREFIX>\n" +
            "\t\t<NAMESPACE>http://purl.org/dc/terms/</NAMESPACE>\n" +
            "\t\t<LABEL>dc</LABEL>\n" +
            "\t</PREFIX>\n" +
            "\t<PREFIX>\n" +
            "\t\t<NAMESPACE>http://xmlns.com/foaf/0.1/</NAMESPACE>\n" +
            "\t\t<LABEL>foaf</LABEL>\n" +
            "\t</PREFIX>\n" +
            "\t<SOURCE>\n" +
            "\t\t<ID>source</ID>\n" +
            "\t\t<ENDPOINT>%s</ENDPOINT>\n" +
            "\t\t<VAR>?x</VAR>\n" +
            "\t\t<PAGESIZE>-1</PAGESIZE>\n" +
            "\t\t<RESTRICTION></RESTRICTION>\n" +
            "\t\t<PROPERTY>label AS lowercase</PROPERTY>\n" +
            "\t\t<TYPE>CSV</TYPE>\n" +
            "\t</SOURCE>\n" +
            "\t<TARGET>\n" +
            "\t\t<ID>dbpedia</ID>\n" +
            "\t\t<ENDPOINT>http://dbpedia.org/sparql</ENDPOINT>\n" +
            "\t\t<VAR>?y</VAR>\n" +
            "\t\t<PAGESIZE>1000</PAGESIZE>\n" +
            "\t\t<MAXOFFSET>9000</MAXOFFSET>\n" +
            "\t\t<RESTRICTION></RESTRICTION>\n" +
            "\t\t<PROPERTY>rdfs:label AS nolang->lowercase</PROPERTY>\n" +
            "\t</TARGET>\n" +
            "\t\n" +
            "\t<METRIC>jarowinkler(x.label,y.rdfs:label)</METRIC>\n" +
            "\t<ACCEPTANCE>\n" +
            "\t\t<THRESHOLD>0.9</THRESHOLD>\n" +
            "\t\t<FILE>pub_accepted.txt</FILE>\n" +
            "\t\t<RELATION>owl:sameAs</RELATION>\n" +
            "\t</ACCEPTANCE>\n" +
            "\t\n" +
            "\t<REVIEW>\n" +
            "\t\t<THRESHOLD>0.9</THRESHOLD>\n" +
            "\t\t<FILE>pub_reviewme.txt</FILE>\n" +
            "\t\t<RELATION>owl:sameAs</RELATION>\n" +
            "\t</REVIEW>\n" +
            "\t\t<EXECUTION>\n" +
            "\t\t<REWRITER>default</REWRITER>\n" +
            "\t\t<PLANNER>default</PLANNER>\n" +
            "\t\t<ENGINE>default</ENGINE>\n" +
            "\t</EXECUTION>\n" +
            "\n" +
            "\t<OUTPUT>N3</OUTPUT>\n" +
            "</LIMES>\n";

    public String getInputFileDirectory() {
        return inputFileDirectory;
    }

    public void setInputFileDirectory(String inputFileDirectory) {
        this.inputFileDirectory = inputFileDirectory;
    }

    public String getXMLFileDirectory() {
        return XMLFileDirectory;
    }

    public void setXMLFileDirectory(String XMLFileDirectory) {
        this.XMLFileDirectory = XMLFileDirectory;
    }

    public String getTemplateString() {
        return TemplateString;
    }

    public String getXMLString() {
        return XMLString;
    }

    public void setXMLString(String XMLString) {
        this.XMLString = XMLString;
    }

    public String getMatchThreshold() {
        return matchThreshold;
    }

    public void setMatchThreshold(String matchThreshold) {
        this.matchThreshold = matchThreshold;
    }

}
