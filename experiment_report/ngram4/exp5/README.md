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
|        Truth no|            264|            336|
|       Truth yes|             20|            180|

P  0.900000 %, R  0.348837 %, F1  0.502793 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            180|             20|
|       Truth yes|            336|            264|

P  0.440000 %, R  0.929577 %, F1  0.597285 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            444|            356|
|       Truth yes|            356|            444|

P  0.555000 %, R  0.555000 %, F1  0.555000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            142|             58|
|       Truth yes|             68|            132|

P  0.660000 %, R  0.694737 %, F1  0.676923 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            132|             68|
|       Truth yes|             58|            142|

P  0.710000 %, R  0.676190 %, F1  0.692683 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            274|            126|
|       Truth yes|            126|            274|

P  0.685000 %, R  0.685000 %, F1  0.685000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            181|             19|
|       Truth yes|             39|            161|

P  0.805000 %, R  0.894444 %, F1  0.847368 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            161|             39|
|       Truth yes|             19|            181|

P  0.905000 %, R  0.822727 %, F1  0.861905 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            342|             58|
|       Truth yes|             58|            342|

P  0.855000 %, R  0.855000 %, F1  0.855000 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             33|            167|

P  0.835000 %, R  0.869792 %, F1  0.852041 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            167|             33|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.841346 %, F1  0.857843 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            342|             58|
|       Truth yes|             58|            342|

P  0.855000 %, R  0.855000 %, F1  0.855000 %

