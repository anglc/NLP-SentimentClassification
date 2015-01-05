## Configuration ##

* Ngram 1
* topNgram 20000

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
|        Truth no|            138|             62|
|       Truth yes|             51|            149|

P  0.745000 %, R  0.706161 %, F1  0.725061 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            149|             51|
|       Truth yes|             62|            138|

P  0.690000 %, R  0.730159 %, F1  0.709512 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            287|            113|
|       Truth yes|            113|            287|

P  0.717500 %, R  0.717500 %, F1  0.717500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             33|            167|

P  0.835000 %, R  0.869792 %, F1  0.852041 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            167|             33|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.841346 %, F1  0.857843 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            342|             58|
|       Truth yes|             58|            342|

P  0.855000 %, R  0.855000 %, F1  0.855000 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            167|             33|
|       Truth yes|             20|            180|

P  0.900000 %, R  0.845070 %, F1  0.871671 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            180|             20|
|       Truth yes|             33|            167|

P  0.835000 %, R  0.893048 %, F1  0.863049 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            347|             53|
|       Truth yes|             53|            347|

P  0.867500 %, R  0.867500 %, F1  0.867500 %

