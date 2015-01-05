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
|        Truth no|            630|            494|
|       Truth yes|            307|            864|

P  0.737831 %, R  0.636230 %, F1  0.683274 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            864|            307|
|       Truth yes|            494|            630|

P  0.560498 %, R  0.672359 %, F1  0.611354 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1494|            801|
|       Truth yes|            801|           1494|

P  0.650980 %, R  0.650980 %, F1  0.650980 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1120|              4|
|       Truth yes|           1054|            117|

P  0.099915 %, R  0.966942 %, F1  0.181115 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            117|           1054|
|       Truth yes|              4|           1120|

P  0.996441 %, R  0.515179 %, F1  0.679200 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1237|           1058|
|       Truth yes|           1058|           1237|

P  0.538998 %, R  0.538998 %, F1  0.538998 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            746|            378|
|       Truth yes|            220|            951|

P  0.812126 %, R  0.715576 %, F1  0.760800 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            951|            220|
|       Truth yes|            378|            746|

P  0.663701 %, R  0.772257 %, F1  0.713876 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1697|            598|
|       Truth yes|            598|           1697|

P  0.739434 %, R  0.739434 %, F1  0.739434 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1062|             62|
|       Truth yes|            600|            571|

P  0.487617 %, R  0.902054 %, F1  0.633038 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            571|            600|
|       Truth yes|             62|           1062|

P  0.944840 %, R  0.638989 %, F1  0.762383 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1633|            662|
|       Truth yes|            662|           1633|

P  0.711547 %, R  0.711547 %, F1  0.711547 %

