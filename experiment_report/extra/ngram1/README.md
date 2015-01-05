## Configuration ##

* Ngram 1
* topNgram 20000

## Work ##

* positive sieve 1-grams building ...
* negative sieve 1-grams building ...

* positive #ngram 32878
* negative #ngram 30920

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
|        Truth no|            254|            870|
|       Truth yes|             45|           1126|

P  0.961571 %, R  0.564128 %, F1  0.711083 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1126|             45|
|       Truth yes|            870|            254|

P  0.225979 %, R  0.849498 %, F1  0.356992 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1380|            915|
|       Truth yes|            915|           1380|

P  0.601307 %, R  0.601307 %, F1  0.601307 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1120|              4|
|       Truth yes|           1076|             95|

P  0.081127 %, R  0.959596 %, F1  0.149606 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             95|           1076|
|       Truth yes|              4|           1120|

P  0.996441 %, R  0.510018 %, F1  0.674699 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1215|           1080|
|       Truth yes|           1080|           1215|

P  0.529412 %, R  0.529412 %, F1  0.529412 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            797|            327|
|       Truth yes|            308|            863|

P  0.736977 %, R  0.725210 %, F1  0.731046 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            863|            308|
|       Truth yes|            327|            797|

P  0.709075 %, R  0.721267 %, F1  0.715119 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1660|            635|
|       Truth yes|            635|           1660|

P  0.723312 %, R  0.723312 %, F1  0.723312 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1001|            123|
|       Truth yes|            519|            652|

P  0.556789 %, R  0.841290 %, F1  0.670092 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            652|            519|
|       Truth yes|            123|           1001|

P  0.890569 %, R  0.658553 %, F1  0.757186 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1653|            642|
|       Truth yes|            642|           1653|

P  0.720261 %, R  0.720261 %, F1  0.720261 %

