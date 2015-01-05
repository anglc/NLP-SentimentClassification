## Configuration ##

* Ngram 4
* topNgram 40000

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

* Passive-Aggressive algorithm top-40000 prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Passive-Aggressive algorithm self-testing ...
* Passive-Aggressive algorithm testing ...


## Adaboost ##

* Prepare Meeting Machine ...


complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Meeting prepare ...
* Meeting top-40000 testing ...


# User Require #

Table `Language Model Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            262|            338|
|       Truth yes|             20|            180|

P  0.900000 %, R  0.347490 %, F1  0.501393 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            180|             20|
|       Truth yes|            338|            262|

P  0.436667 %, R  0.929078 %, F1  0.594104 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            442|            358|
|       Truth yes|            358|            442|

P  0.552500 %, R  0.552500 %, F1  0.552500 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            147|             53|
|       Truth yes|             77|            123|

P  0.615000 %, R  0.698864 %, F1  0.654255 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            123|             77|
|       Truth yes|             53|            147|

P  0.735000 %, R  0.656250 %, F1  0.693396 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            270|            130|
|       Truth yes|            130|            270|

P  0.675000 %, R  0.675000 %, F1  0.675000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            180|             20|
|       Truth yes|             37|            163|

P  0.815000 %, R  0.890710 %, F1  0.851175 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            163|             37|
|       Truth yes|             20|            180|

P  0.900000 %, R  0.829493 %, F1  0.863309 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            343|             57|
|       Truth yes|             57|            343|

P  0.857500 %, R  0.857500 %, F1  0.857500 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            180|             20|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.895288 %, F1  0.874680 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            171|             29|
|       Truth yes|             20|            180|

P  0.900000 %, R  0.861244 %, F1  0.880196 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            351|             49|
|       Truth yes|             49|            351|

P  0.877500 %, R  0.877500 %, F1  0.877500 %

