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
|        Truth no|            220|            380|
|       Truth yes|              6|            194|

P  0.970000 %, R  0.337979 %, F1  0.501292 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            194|              6|
|       Truth yes|            380|            220|

P  0.366667 %, R  0.973451 %, F1  0.532688 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            414|            386|
|       Truth yes|            386|            414|

P  0.517500 %, R  0.517500 %, F1  0.517500 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            137|             63|
|       Truth yes|             61|            139|

P  0.695000 %, R  0.688119 %, F1  0.691542 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            139|             61|
|       Truth yes|             63|            137|

P  0.685000 %, R  0.691919 %, F1  0.688442 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            276|            124|
|       Truth yes|            124|            276|

P  0.690000 %, R  0.690000 %, F1  0.690000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            172|             28|
|       Truth yes|             38|            162|

P  0.810000 %, R  0.852632 %, F1  0.830769 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            162|             38|
|       Truth yes|             28|            172|

P  0.860000 %, R  0.819048 %, F1  0.839024 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            334|             66|
|       Truth yes|             66|            334|

P  0.835000 %, R  0.835000 %, F1  0.835000 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            166|             34|
|       Truth yes|             30|            170|

P  0.850000 %, R  0.833333 %, F1  0.841584 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            170|             30|
|       Truth yes|             34|            166|

P  0.830000 %, R  0.846939 %, F1  0.838384 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            336|             64|
|       Truth yes|             64|            336|

P  0.840000 %, R  0.840000 %, F1  0.840000 %

