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
|        Truth no|            134|             66|
|       Truth yes|             47|            153|

P  0.765000 %, R  0.698630 %, F1  0.730310 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            153|             47|
|       Truth yes|             66|            134|

P  0.670000 %, R  0.740331 %, F1  0.703412 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            287|            113|
|       Truth yes|            113|            287|

P  0.717500 %, R  0.717500 %, F1  0.717500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            176|             24|
|       Truth yes|             41|            159|

P  0.795000 %, R  0.868852 %, F1  0.830287 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            159|             41|
|       Truth yes|             24|            176|

P  0.880000 %, R  0.811060 %, F1  0.844125 %

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
|        Truth no|            172|             28|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.859296 %, F1  0.857143 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            171|             29|
|       Truth yes|             28|            172|

P  0.860000 %, R  0.855721 %, F1  0.857855 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            343|             57|
|       Truth yes|             57|            343|

P  0.857500 %, R  0.857500 %, F1  0.857500 %

