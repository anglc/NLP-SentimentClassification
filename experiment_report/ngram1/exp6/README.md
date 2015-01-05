## Configuration ##

* Ngram 1
* topNgram 10000

## Work ##

* positive sieve 1-grams building ...
* negative sieve 1-grams building ...

* positive #ngram 32878
* negative #ngram 30920

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

* Passive-Aggressive algorithm top-10000 prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Passive-Aggressive algorithm self-testing ...
* Passive-Aggressive algorithm testing ...


## Adaboost ##

* Prepare Meeting Machine ...


complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Meeting prepare ...
* Meeting top-10000 testing ...


# User Require #

Table `Language Model Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            125|            475|
|       Truth yes|              1|            199|

P  0.995000 %, R  0.295252 %, F1  0.455378 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            199|              1|
|       Truth yes|            475|            125|

P  0.208333 %, R  0.992063 %, F1  0.344353 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            324|            476|
|       Truth yes|            476|            324|

P  0.405000 %, R  0.405000 %, F1  0.405000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            134|             66|
|       Truth yes|             50|            150|

P  0.750000 %, R  0.694444 %, F1  0.721154 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            150|             50|
|       Truth yes|             66|            134|

P  0.670000 %, R  0.728261 %, F1  0.697917 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            284|            116|
|       Truth yes|            116|            284|

P  0.710000 %, R  0.710000 %, F1  0.710000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            171|             29|
|       Truth yes|             35|            165|

P  0.825000 %, R  0.850515 %, F1  0.837563 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            165|             35|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.830097 %, F1  0.842365 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            336|             64|
|       Truth yes|             64|            336|

P  0.840000 %, R  0.840000 %, F1  0.840000 %

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            160|             40|
|       Truth yes|             24|            176|

P  0.880000 %, R  0.814815 %, F1  0.846154 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            176|             24|
|       Truth yes|             40|            160|

P  0.800000 %, R  0.869565 %, F1  0.833333 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            336|             64|
|       Truth yes|             64|            336|

P  0.840000 %, R  0.840000 %, F1  0.840000 %

