## Configuration ##

* Ngram 2
* topNgram 20000

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
|        Truth no|            526|            598|
|       Truth yes|            174|            997|

P  0.851409 %, R  0.625078 %, F1  0.720897 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            997|            174|
|       Truth yes|            598|            526|

P  0.467972 %, R  0.751429 %, F1  0.576754 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1523|            772|
|       Truth yes|            772|           1523|

P  0.663617 %, R  0.663617 %, F1  0.663617 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1114|             10|
|       Truth yes|           1021|            150|

P  0.128096 %, R  0.937500 %, F1  0.225394 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            150|           1021|
|       Truth yes|             10|           1114|

P  0.991103 %, R  0.521780 %, F1  0.683645 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1264|           1031|
|       Truth yes|           1031|           1264|

P  0.550763 %, R  0.550763 %, F1  0.550763 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            826|            298|
|       Truth yes|            259|            912|

P  0.778822 %, R  0.753719 %, F1  0.766065 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            912|            259|
|       Truth yes|            298|            826|

P  0.734875 %, R  0.761290 %, F1  0.747850 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1738|            557|
|       Truth yes|            557|           1738|

P  0.757298 %, R  0.757298 %, F1  0.757298 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1052|             72|
|       Truth yes|            570|            601|

P  0.513237 %, R  0.893016 %, F1  0.651844 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            601|            570|
|       Truth yes|             72|           1052|

P  0.935943 %, R  0.648582 %, F1  0.766205 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1653|            642|
|       Truth yes|            642|           1653|

P  0.720261 %, R  0.720261 %, F1  0.720261 %

