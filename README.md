# NLP SentimentClassification #

[content](http://morris821028.github.io/2014/12/05/NLP-paper2/)

## To do ##

* `Language Modeling` build
* Optimal Winnow algorithm and Passive-Aggressive Algorithm

## Complete ##

* Simple Passive-Aggressive Algorithm
* Simple Winnow algorithm
* Passive-Aggressive Algorithm & Winnow algorithm adjust training

## Usage ##

* JavaSE-1.7
* Eclipse

## Sample Output ##

```
positive sieve 5-grams building ...
negative sieve 5-grams building ...
positive #ngram = 234413
negative #ngram = 217863
Winnow algorithm top-10000 prepare ...
complete |                                        |
         |----------------------------------------|
Winnow algorithm top-10000 testing ...
Table `Winnow Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            400|              0|
|       Truth yes|              0|            400|

Table `Winnow Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            400|              0|
|       Truth yes|              0|            400|

Table `Winnow Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            800|              0|
|       Truth yes|              0|            800|

Passive-Aggressive algorithm top-10000 prepare ...
complete |                                        |
         |----------------------------------------|
Passive-Aggressive algorithm top-10000 testing ...
Table `Passive-Aggressive Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            399|              1|
|       Truth yes|              0|            400|

Table `Passive-Aggressive Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            400|              0|
|       Truth yes|              1|            399|

Table `Passive-Aggressive Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            799|              1|
|       Truth yes|              1|            799|
```