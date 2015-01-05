## Configuration ##

* Ngram 5
* topNgram 40000

## Work ##

* positive sieve 5-grams building ...
* negative sieve 5-grams building ...

* positive #ngram 1102214
* negative #ngram 990993

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
|        Truth no|            252|            348|
|       Truth yes|             20|            180|

P  0.900000 %, R  0.340909 %, F1  0.494505 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            180|             20|
|       Truth yes|            348|            252|

P  0.420000 %, R  0.926471 %, F1  0.577982 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            432|            368|
|       Truth yes|            368|            432|

P  0.540000 %, R  0.540000 %, F1  0.540000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            152|             48|
|       Truth yes|             77|            123|

P  0.615000 %, R  0.719298 %, F1  0.663073 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            123|             77|
|       Truth yes|             48|            152|

P  0.760000 %, R  0.663755 %, F1  0.708625 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            275|            125|
|       Truth yes|            125|            275|

P  0.687500 %, R  0.687500 %, F1  0.687500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             36|            164|

P  0.820000 %, R  0.867725 %, F1  0.843188 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            164|             36|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.829384 %, F1  0.851582 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            339|             61|
|       Truth yes|             61|            339|

P  0.847500 %, R  0.847500 %, F1  0.847500 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            172|             28|
|       Truth yes|             27|            173|

P  0.865000 %, R  0.860697 %, F1  0.862843 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            173|             27|
|       Truth yes|             28|            172|

P  0.860000 %, R  0.864322 %, F1  0.862155 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            345|             55|
|       Truth yes|             55|            345|

P  0.862500 %, R  0.862500 %, F1  0.862500 %

