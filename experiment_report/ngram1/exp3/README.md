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
|        Truth no|            121|            479|
|       Truth yes|              1|            199|

P  0.995000 %, R  0.293510 %, F1  0.453303 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            199|              1|
|       Truth yes|            479|            121|

P  0.201667 %, R  0.991803 %, F1  0.335180 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            320|            480|
|       Truth yes|            480|            320|

P  0.400000 %, R  0.400000 %, F1  0.400000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            136|             64|
|       Truth yes|             58|            142|

P  0.710000 %, R  0.689320 %, F1  0.699507 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            142|             58|
|       Truth yes|             64|            136|

P  0.680000 %, R  0.701031 %, F1  0.690355 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            278|            122|
|       Truth yes|            122|            278|

P  0.695000 %, R  0.695000 %, F1  0.695000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            168|             32|
|       Truth yes|             33|            167|

P  0.835000 %, R  0.839196 %, F1  0.837093 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            167|             33|
|       Truth yes|             32|            168|

P  0.840000 %, R  0.835821 %, F1  0.837905 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            335|             65|
|       Truth yes|             65|            335|

P  0.837500 %, R  0.837500 %, F1  0.837500 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            160|             40|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.813953 %, F1  0.843373 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             40|            160|

P  0.800000 %, R  0.864865 %, F1  0.831169 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            335|             65|
|       Truth yes|             65|            335|

P  0.837500 %, R  0.837500 %, F1  0.837500 %

