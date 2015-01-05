## Configuration ##

* Ngram 2
* topNgram 10000

## Work ##

* positive sieve 2-grams building ...
* negative sieve 2-grams building ...

* positive #ngram 253627
* negative #ngram 229175

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
|        Truth no|            518|            606|
|       Truth yes|            173|            998|

P  0.852263 %, R  0.622195 %, F1  0.719279 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            998|            173|
|       Truth yes|            606|            518|

P  0.460854 %, R  0.749638 %, F1  0.570799 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1516|            779|
|       Truth yes|            779|           1516|

P  0.660566 %, R  0.660566 %, F1  0.660566 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1103|             21|
|       Truth yes|            989|            182|

P  0.155423 %, R  0.896552 %, F1  0.264920 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            182|            989|
|       Truth yes|             21|           1103|

P  0.981317 %, R  0.527247 %, F1  0.685945 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1285|           1010|
|       Truth yes|           1010|           1285|

P  0.559913 %, R  0.559913 %, F1  0.559913 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            773|            351|
|       Truth yes|            231|            940|

P  0.802733 %, R  0.728118 %, F1  0.763607 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            940|            231|
|       Truth yes|            351|            773|

P  0.687722 %, R  0.769920 %, F1  0.726504 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1713|            582|
|       Truth yes|            582|           1713|

P  0.746405 %, R  0.746405 %, F1  0.746405 %

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            944|            180|
|       Truth yes|            399|            772|

P  0.659266 %, R  0.810924 %, F1  0.727273 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            772|            399|
|       Truth yes|            180|            944|

P  0.839858 %, R  0.702904 %, F1  0.765302 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1716|            579|
|       Truth yes|            579|           1716|

P  0.747712 %, R  0.747712 %, F1  0.747712 %

