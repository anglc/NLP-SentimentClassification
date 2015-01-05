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
|        Truth no|            208|            392|
|       Truth yes|              6|            194|

P  0.970000 %, R  0.331058 %, F1  0.493639 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            194|              6|
|       Truth yes|            392|            208|

P  0.346667 %, R  0.971963 %, F1  0.511057 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            402|            398|
|       Truth yes|            398|            402|

P  0.502500 %, R  0.502500 %, F1  0.502500 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            132|             68|
|       Truth yes|             47|            153|

P  0.765000 %, R  0.692308 %, F1  0.726841 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            153|             47|
|       Truth yes|             68|            132|

P  0.660000 %, R  0.737430 %, F1  0.696570 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            285|            115|
|       Truth yes|            115|            285|

P  0.712500 %, R  0.712500 %, F1  0.712500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            171|             29|
|       Truth yes|             24|            176|

P  0.880000 %, R  0.858537 %, F1  0.869136 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            176|             24|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.876923 %, F1  0.865823 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            347|             53|
|       Truth yes|             53|            347|

P  0.867500 %, R  0.867500 %, F1  0.867500 %

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            166|             34|
|       Truth yes|             20|            180|

P  0.900000 %, R  0.841121 %, F1  0.869565 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            180|             20|
|       Truth yes|             34|            166|

P  0.830000 %, R  0.892473 %, F1  0.860104 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            346|             54|
|       Truth yes|             54|            346|

P  0.865000 %, R  0.865000 %, F1  0.865000 %

