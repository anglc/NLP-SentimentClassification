## Configuration ##

* Ngram 4
* topNgram 40000

## Work ##

* positive sieve 4-grams building ...
* negative sieve 4-grams building ...

* positive #ngram 853655
* negative #ngram 766366

## Language Model ##

* Language Model prepare ...
* Language Model self-testing ...
* Language Model testing ...


## Winnow Algorithm ##

* Winnow algorithm prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Winnow algorithm self-testing ...
* Winnow algorithm testing ...

## Passive-Aggressive Algorithm ##

* Passive-Aggressive algorithm top-40000 prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Passive-Aggressive algorithm self-testing ...
* Passive-Aggressive algorithm testing ...


## Adaboost ##

* Prepare Meeting Machine ...


complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Meeting prepare ...
* Meeting top-40000 testing ...


# User Require #

Table `Language Model Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            682|            442|
|       Truth yes|            366|            805|

P  0.687447 %, R  0.645549 %, F1  0.665840 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            805|            366|
|       Truth yes|            442|            682|

P  0.606762 %, R  0.650763 %, F1  0.627993 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1487|            808|
|       Truth yes|            808|           1487|

P  0.647930 %, R  0.647930 %, F1  0.647930 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1123|              1|
|       Truth yes|           1101|             70|

P  0.059778 %, R  0.985915 %, F1  0.112721 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             70|           1101|
|       Truth yes|              1|           1123|

P  0.999110 %, R  0.504946 %, F1  0.670848 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1193|           1102|
|       Truth yes|           1102|           1193|

P  0.519826 %, R  0.519826 %, F1  0.519826 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            760|            364|
|       Truth yes|            245|            926|

P  0.790777 %, R  0.717829 %, F1  0.752540 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            926|            245|
|       Truth yes|            364|            760|

P  0.676157 %, R  0.756219 %, F1  0.713950 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1686|            609|
|       Truth yes|            609|           1686|

P  0.734641 %, R  0.734641 %, F1  0.734641 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1084|             40|
|       Truth yes|            673|            498|

P  0.425278 %, R  0.925651 %, F1  0.582797 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            498|            673|
|       Truth yes|             40|           1084|

P  0.964413 %, R  0.616961 %, F1  0.752516 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1582|            713|
|       Truth yes|            713|           1582|

P  0.689325 %, R  0.689325 %, F1  0.689325 %

