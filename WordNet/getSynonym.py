import nltk 
from nltk.corpus import wordnet 
synonyms = [] 
antonyms = [] 

currWord = "address"
def getSynonym ( word ):
	for syn in wordnet.synsets(word): 
	    for l in syn.lemmas(): 
	        synonyms.append(l.name()) 
	        if l.antonyms(): 
	            antonyms.append(l.antonyms()[0].name())

	# print(set(synonyms)) 
	return set(synonyms)

synonyms = getSynonym(currWord)
print(synonyms)
