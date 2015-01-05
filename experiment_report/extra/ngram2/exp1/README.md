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
|        Truth no|            529|            595|
|       Truth yes|            197|            974|

P  0.831768 %, R  0.620778 %, F1  0.710949 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            974|            197|
|       Truth yes|            595|            529|

P  0.470641 %, R  0.728650 %, F1  0.571892 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1503|            792|
|       Truth yes|            792|           1503|

P  0.654902 %, R  0.654902 %, F1  0.654902 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1119|              5|
|       Truth yes|           1052|            119|

P  0.101623 %, R  0.959677 %, F1  0.183784 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            119|           1052|
|       Truth yes|              5|           1119|

P  0.995552 %, R  0.515431 %, F1  0.679211 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1238|           1057|
|       Truth yes|           1057|           1238|

P  0.539434 %, R  0.539434 %, F1  0.539434 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            808|            316|
|       Truth yes|            256|            915|

P  0.781383 %, R  0.743298 %, F1  0.761865 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            915|            256|
|       Truth yes|            316|            808|

P  0.718861 %, R  0.759398 %, F1  0.738574 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1723|            572|
|       Truth yes|            572|           1723|

P  0.750763 %, R  0.750763 %, F1  0.750763 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            846|            278|
|       Truth yes|            290|            881|

P  0.752348 %, R  0.760138 %, F1  0.756223 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            881|            290|
|       Truth yes|            278|            846|

P  0.752669 %, R  0.744718 %, F1  0.748673 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1727|            568|
|       Truth yes|            568|           1727|

P  0.752505 %, R  0.752505 %, F1  0.752505 %

