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
|        Truth no|            134|             66|
|       Truth yes|             49|            151|

P  0.755000 %, R  0.695853 %, F1  0.724221 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            151|             49|
|       Truth yes|             66|            134|

P  0.670000 %, R  0.732240 %, F1  0.699739 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            285|            115|
|       Truth yes|            115|            285|

P  0.712500 %, R  0.712500 %, F1  0.712500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            173|             27|
|       Truth yes|             30|            170|

P  0.850000 %, R  0.862944 %, F1  0.856423 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            170|             30|
|       Truth yes|             27|            173|

P  0.865000 %, R  0.852217 %, F1  0.858561 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            343|             57|
|       Truth yes|             57|            343|

P  0.857500 %, R  0.857500 %, F1  0.857500 %

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            165|             35|
|       Truth yes|             19|            181|

P  0.905000 %, R  0.837963 %, F1  0.870192 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            181|             19|
|       Truth yes|             35|            165|

P  0.825000 %, R  0.896739 %, F1  0.859375 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            346|             54|
|       Truth yes|             54|            346|

P  0.865000 %, R  0.865000 %, F1  0.865000 %

