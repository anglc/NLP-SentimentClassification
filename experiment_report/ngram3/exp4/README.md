## Configuration ##

* Ngram 3
* topNgram 20000

## Work ##

* positive sieve 3-grams building ...
* negative sieve 3-grams building ...

* positive #ngram 563897
* negative #ngram 506096

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

* Passive-Aggressive algorithm top-20000 prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Passive-Aggressive algorithm self-testing ...
* Passive-Aggressive algorithm testing ...


## Adaboost ##

* Prepare Meeting Machine ...


complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Meeting prepare ...
* Meeting top-20000 testing ...


# User Require #

Table `Language Model Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            236|            364|
|       Truth yes|             12|            188|

P  0.940000 %, R  0.340580 %, F1  0.500000 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            188|             12|
|       Truth yes|            364|            236|

P  0.393333 %, R  0.951613 %, F1  0.556604 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            424|            376|
|       Truth yes|            376|            424|

P  0.530000 %, R  0.530000 %, F1  0.530000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            140|             60|
|       Truth yes|             58|            142|

P  0.710000 %, R  0.702970 %, F1  0.706468 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            142|             58|
|       Truth yes|             60|            140|

P  0.700000 %, R  0.707071 %, F1  0.703518 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            282|            118|
|       Truth yes|            118|            282|

P  0.705000 %, R  0.705000 %, F1  0.705000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            177|             23|
|       Truth yes|             37|            163|

P  0.815000 %, R  0.876344 %, F1  0.844560 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            163|             37|
|       Truth yes|             23|            177|

P  0.885000 %, R  0.827103 %, F1  0.855072 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            340|             60|
|       Truth yes|             60|            340|

P  0.850000 %, R  0.850000 %, F1  0.850000 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.872449 %, F1  0.863636 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            171|             29|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.857843 %, F1  0.866337 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            346|             54|
|       Truth yes|             54|            346|

P  0.865000 %, R  0.865000 %, F1  0.865000 %

