from SPARQLWrapper import SPARQLWrapper, JSON
import pandas as pd
sparql = SPARQLWrapper("https://query.wikidata.org/sparql")

# From https://www.wikidata.org/wiki/Wikidata:SPARQL_query_service/queries/examples#Cats
sparql.setQuery("""
	SELECT ?item ?itemLabel
	WHERE
	{
	    ?item wdt:P31 wd:Q146 .
	    SERVICE wikibase:label { bd:serviceParam wikibase:language "en" }
	}
	""")
sparql.setReturnFormat(JSON)
results = sparql.query().convert()

results_df = pd.io.json.json_normalize(results['results']['bindings'])
results_df[['item.value', 'itemLabel.value']].head()
print (results)
