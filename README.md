# Project_Regex

**Project Description --**
Regex algorithm identifying the keywords in a document, such as the abbreviations of technical terms and short forms of phrases, and replacing them with appropriate given keyword/ phrases. To do this efficiently we need to first identify the keyword by their features, such as words consisting with capital letters, or with special characters (such as “ ‘ “ in “won’t”), and search it in the dataset (abbreviation list). If detected, recover the phrases from abbreviations. If not, skip and move on to the next. For instance, our algorithm would recognize the keyword ‘ASAP’ as the abbreviation of ‘As Soon As Possible’ and then replace it with its full spelling.

**Edge Cases --**
The method is design for detecting and shifting standardized terms and phrases. Therefore, miss-spealled terms cannot be replaced regularly. Rather, they will be counted as spelling errors. At the same time, a keyword missing special characters will also be considered as an error so that it won’t be detected by the algorithm. For instance, the keyword ‘wont’ will be considered as a typographical error and will not be recognized as abbreviation.

**Expected complexities** 
- worst case time complexity: O(n^2)
- average time complicity: O(nlog(n))

**Dataset Collection --**
Abbreviation list- Cited from “The Complete List of 1697 Common Text Abbreviations & Acronyms”. Converted toward Excel Text.
Link - https://www.webopedia.com/reference/text-abbreviations/

**Language --**
JAVA
