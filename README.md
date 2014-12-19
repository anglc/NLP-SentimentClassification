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
positive sieve 1-grams building ...
negative sieve 1-grams building ...
positive #ngram = 22523
negative #ngram = 21100
Language Model top-10000 prepare ...
Language Model top-10000 testing ...
Table `Language Model Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            283|            117|
|       Truth yes|             30|            370|

Table `Language Model Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            370|             30|
|       Truth yes|            117|            283|

Table `Language Model Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            653|            147|
|       Truth yes|            147|            653|

Winnow algorithm top-10000 prepare ...
complete |                                        |
         |----------------------------------------|
Winnow algorithm top-10000 testing ...
Table `Winnow Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            253|            147|
|       Truth yes|            126|            274|

Table `Winnow Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            274|            126|
|       Truth yes|            147|            253|

Table `Winnow Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            527|            273|
|       Truth yes|            273|            527|

Passive-Aggressive algorithm top-10000 prepare ...
complete |                                        |
         |----------------------------------------|
Passive-Aggressive algorithm top-10000 testing ...
Table `Passive-Aggressive Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            304|             96|
|       Truth yes|             52|            348|

Table `Passive-Aggressive Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            348|             52|
|       Truth yes|             96|            304|

Table `Passive-Aggressive Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            652|            148|
|       Truth yes|            148|            652|

Three-Ways Online top-10000 prepare ...
Three-Ways Online top-10000 testing ...
663 / 137 (AC/WA)
1-oponin 413 / 44 (AC/WA)
n-oponin 250 / 93 (AC/WA)
positive sieve 1-grams building ...
negative sieve 1-grams building ...
positive #ngram = 23624
negative #ngram = 22074
Language Model top-10000 prepare ...
Language Model top-10000 testing ...
Table `Language Model Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|             40|            360|
|       Truth yes|              0|            400|

Table `Language Model Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            400|              0|
|       Truth yes|            360|             40|

Table `Language Model Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            440|            360|
|       Truth yes|            360|            440|

Winnow algorithm top-10000 prepare ...
complete |                                        |
         |----------------------------------------|
Winnow algorithm top-10000 testing ...
Table `Winnow Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            272|            128|
|       Truth yes|            164|            236|

Table `Winnow Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            236|            164|
|       Truth yes|            128|            272|

Table `Winnow Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            508|            292|
|       Truth yes|            292|            508|

Passive-Aggressive algorithm top-10000 prepare ...
complete |                                        |
         |----------------------------------------|
Passive-Aggressive algorithm top-10000 testing ...
Table `Passive-Aggressive Class Positive`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            329|             71|
|       Truth yes|             90|            310|

Table `Passive-Aggressive Class Negative`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            310|             90|
|       Truth yes|             71|            329|

Table `Passive-Aggressive Final`
|Truth\Classifier|  Classifier no| Classifier yes|
|          ------|         ------|         ------|
|        Truth no|            639|            161|
|       Truth yes|            161|            639|

Three-Ways Online top-10000 prepare ...
Three-Ways Online top-10000 testing ...
576 / 224 (AC/WA)
1-oponin 237 / 26 (AC/WA)
n-oponin 339 / 198 (AC/WA)
```