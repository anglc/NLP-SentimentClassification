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
|        Truth no|            132|             68|
|       Truth yes|             53|            147|

P  0.735000 %, R  0.683721 %, F1  0.708434 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            147|             53|
|       Truth yes|             68|            132|

P  0.660000 %, R  0.713514 %, F1  0.685714 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            279|            121|
|       Truth yes|            121|            279|

P  0.697500 %, R  0.697500 %, F1  0.697500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            173|             27|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.863636 %, F1  0.859296 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            171|             29|
|       Truth yes|             27|            173|

P  0.865000 %, R  0.856436 %, F1  0.860697 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            344|             56|
|       Truth yes|             56|            344|

P  0.860000 %, R  0.860000 %, F1  0.860000 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            153|             47|
|       Truth yes|             17|            183|

P  0.915000 %, R  0.795652 %, F1  0.851163 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            183|             17|
|       Truth yes|             47|            153|

P  0.765000 %, R  0.900000 %, F1  0.827027 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            336|             64|
|       Truth yes|             64|            336|

P  0.840000 %, R  0.840000 %, F1  0.840000 %

