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
|       Truth yes|             56|            144|

P  0.720000 %, R  0.705882 %, F1  0.712871 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            144|             56|
|       Truth yes|             60|            140|

P  0.700000 %, R  0.714286 %, F1  0.707071 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            284|            116|
|       Truth yes|            116|            284|

P  0.710000 %, R  0.710000 %, F1  0.710000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            168|             32|
|       Truth yes|             26|            174|

P  0.870000 %, R  0.844660 %, F1  0.857143 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            174|             26|
|       Truth yes|             32|            168|

P  0.840000 %, R  0.865979 %, F1  0.852792 %

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
|        Truth no|            176|             24|
|       Truth yes|             26|            174|

P  0.870000 %, R  0.878788 %, F1  0.874372 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            174|             26|
|       Truth yes|             24|            176|

P  0.880000 %, R  0.871287 %, F1  0.875622 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            350|             50|
|       Truth yes|             50|            350|

P  0.875000 %, R  0.875000 %, F1  0.875000 %

