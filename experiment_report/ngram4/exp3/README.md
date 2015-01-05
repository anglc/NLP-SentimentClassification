## Configuration ##

* Ngram 4
* topNgram 20000

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
|        Truth no|            247|            353|
|       Truth yes|             17|            183|

P  0.915000 %, R  0.341418 %, F1  0.497283 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            183|             17|
|       Truth yes|            353|            247|

P  0.411667 %, R  0.935606 %, F1  0.571759 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            430|            370|
|       Truth yes|            370|            430|

P  0.537500 %, R  0.537500 %, F1  0.537500 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            137|             63|
|       Truth yes|             58|            142|

P  0.710000 %, R  0.692683 %, F1  0.701235 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            142|             58|
|       Truth yes|             63|            137|

P  0.685000 %, R  0.702564 %, F1  0.693671 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            279|            121|
|       Truth yes|            121|            279|

P  0.697500 %, R  0.697500 %, F1  0.697500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            178|             22|
|       Truth yes|             36|            164|

P  0.820000 %, R  0.881720 %, F1  0.849741 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            164|             36|
|       Truth yes|             22|            178|

P  0.890000 %, R  0.831776 %, F1  0.859903 %

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
|        Truth no|            171|             29|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.857843 %, F1  0.866337 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.872449 %, F1  0.863636 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            346|             54|
|       Truth yes|             54|            346|

P  0.865000 %, R  0.865000 %, F1  0.865000 %

