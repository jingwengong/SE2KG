curl --header "Accept: application/sparql-results+json"  -G 'https://query.wikidata.org/sparql' --data-urlencode query='
SELECT ?resource {
  ?resource rdfs:label "Third Base"@en
}'
