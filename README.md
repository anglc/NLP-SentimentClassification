# NLP SentimentClassification #

[content](http://morris821028.github.io/2014/12/05/NLP-paper2/)

## To do ##

* add more features

## Complete ##

* Simple Passive-Aggressive Algorithm
* Simple Winnow algorithm
* Simple Language Modeling
* Passive-Aggressive Algorithm & Winnow algorithm adjust training

## Usage ##

* JavaSE-1.7
* Eclipse

## Sample Output ##

```
positive sieve 3-grams building ...
negative sieve 3-grams building ...
positive #ngram = 208029
negative #ngram = 193143
Language Model top-10000 prepare ...
Language Model top-10000 testing ...
Table `Language Model Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            254|            146|
|       Truth yes|             47|            353|

Table `Language Model Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            353|             47|
|       Truth yes|            146|            254|

Table `Language Model Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            607|            193|
|       Truth yes|            193|            607|

Winnow algorithm top-10000 prepare ...
complete |                                        |
         |----------------------------------------|
Winnow algorithm top-10000 testing ...
Table `Winnow Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            264|            136|
|       Truth yes|            164|            236|

Table `Winnow Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            236|            164|
|       Truth yes|            136|            264|

Table `Winnow Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            500|            300|
|       Truth yes|            300|            500|

Passive-Aggressive algorithm top-10000 prepare ...
complete |                                        |
         |----------------------------------------|
Passive-Aggressive algorithm top-10000 testing ...
Table `Passive-Aggressive Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            269|            131|
|       Truth yes|            105|            295|

Table `Passive-Aggressive Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            295|            105|
|       Truth yes|            131|            269|

Table `Passive-Aggressive Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            564|            236|
|       Truth yes|            236|            564|
```