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
|        Truth no|            137|             63|
|       Truth yes|             50|            150|

P  0.750000 %, R  0.704225 %, F1  0.726392 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            150|             50|
|       Truth yes|             63|            137|

P  0.685000 %, R  0.732620 %, F1  0.708010 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            287|            113|
|       Truth yes|            113|            287|

P  0.717500 %, R  0.717500 %, F1  0.717500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            173|             27|
|       Truth yes|             38|            162|

P  0.810000 %, R  0.857143 %, F1  0.832905 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            162|             38|
|       Truth yes|             27|            173|

P  0.865000 %, R  0.819905 %, F1  0.841849 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            335|             65|
|       Truth yes|             65|            335|

P  0.837500 %, R  0.837500 %, F1  0.837500 %

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            170|             30|
|       Truth yes|             34|            166|

P  0.830000 %, R  0.846939 %, F1  0.838384 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            166|             34|
|       Truth yes|             30|            170|

P  0.850000 %, R  0.833333 %, F1  0.841584 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            336|             64|
|       Truth yes|             64|            336|

P  0.840000 %, R  0.840000 %, F1  0.840000 %

