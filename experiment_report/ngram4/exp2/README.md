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
|        Truth no|            249|            351|
|       Truth yes|             17|            183|

P  0.915000 %, R  0.342697 %, F1  0.498638 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            183|             17|
|       Truth yes|            351|            249|

P  0.415000 %, R  0.936090 %, F1  0.575058 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            432|            368|
|       Truth yes|            368|            432|

P  0.540000 %, R  0.540000 %, F1  0.540000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            138|             62|
|       Truth yes|             56|            144|

P  0.720000 %, R  0.699029 %, F1  0.709360 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            144|             56|
|       Truth yes|             62|            138|

P  0.690000 %, R  0.711340 %, F1  0.700508 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            282|            118|
|       Truth yes|            118|            282|

P  0.705000 %, R  0.705000 %, F1  0.705000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            174|             26|
|       Truth yes|             24|            176|

P  0.880000 %, R  0.871287 %, F1  0.875622 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            176|             24|
|       Truth yes|             26|            174|

P  0.870000 %, R  0.878788 %, F1  0.874372 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            350|             50|
|       Truth yes|             50|            350|

P  0.875000 %, R  0.875000 %, F1  0.875000 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            171|             29|
|       Truth yes|             28|            172|

P  0.860000 %, R  0.855721 %, F1  0.857855 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            172|             28|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.859296 %, F1  0.857143 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            343|             57|
|       Truth yes|             57|            343|

P  0.857500 %, R  0.857500 %, F1  0.857500 %

