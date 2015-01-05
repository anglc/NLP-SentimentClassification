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
|        Truth no|            222|            378|
|       Truth yes|              6|            194|

P  0.970000 %, R  0.339161 %, F1  0.502591 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            194|              6|
|       Truth yes|            378|            222|

P  0.370000 %, R  0.973684 %, F1  0.536232 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            416|            384|
|       Truth yes|            384|            416|

P  0.520000 %, R  0.520000 %, F1  0.520000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            138|             62|
|       Truth yes|             57|            143|

P  0.715000 %, R  0.697561 %, F1  0.706173 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            143|             57|
|       Truth yes|             62|            138|

P  0.690000 %, R  0.707692 %, F1  0.698734 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            281|            119|
|       Truth yes|            119|            281|

P  0.702500 %, R  0.702500 %, F1  0.702500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            181|             19|
|       Truth yes|             35|            165|

P  0.825000 %, R  0.896739 %, F1  0.859375 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            165|             35|
|       Truth yes|             19|            181|

P  0.905000 %, R  0.837963 %, F1  0.870192 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            346|             54|
|       Truth yes|             54|            346|

P  0.865000 %, R  0.865000 %, F1  0.865000 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            172|             28|
|       Truth yes|             22|            178|

P  0.890000 %, R  0.864078 %, F1  0.876847 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            178|             22|
|       Truth yes|             28|            172|

P  0.860000 %, R  0.886598 %, F1  0.873096 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            350|             50|
|       Truth yes|             50|            350|

P  0.875000 %, R  0.875000 %, F1  0.875000 %

