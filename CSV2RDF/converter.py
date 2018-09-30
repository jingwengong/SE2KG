import csv
import sys
with open(sys.argv[1], "r") as f:
    reader = csv.reader(f)
    output = open(sys.argv[2],'w')
    output.write("@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>.\n")
    output.write("@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>.\n")
    output.write("@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n")
    output.write("@prefix : <http://example.org/data/"+sys.argv[1]+"#>.\n\n")
    i = 1;
    for label in next(reader):
        output.write(":c" + str(i) + " a :Column;\n")
        output.write("  rdfs:label \""+label+"\".\n\n")
        i += 1



