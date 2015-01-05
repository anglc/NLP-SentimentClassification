## Configuration ##

* Ngram 6
* topNgram 40000

## Work ##

* positive sieve 6-grams building ...
* negative sieve 6-grams building ...

* positive #ngram 1311148
* negative #ngram 1180927

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
|        Truth no|            692|            432|
|       Truth yes|            355|            816|

P  0.696840 %, R  0.653846 %, F1  0.674659 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            816|            355|
|       Truth yes|            432|            692|

P  0.615658 %, R  0.660936 %, F1  0.637494 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1508|            787|
|       Truth yes|            787|           1508|

P  0.657081 %, R  0.657081 %, F1  0.657081 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1122|              2|
|       Truth yes|           1090|             81|

P  0.069172 %, R  0.975904 %, F1  0.129187 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             81|           1090|
|       Truth yes|              2|           1122|

P  0.998221 %, R  0.507233 %, F1  0.672662 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1203|           1092|
|       Truth yes|           1092|           1203|

P  0.524183 %, R  0.524183 %, F1  0.524183 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            828|            296|
|       Truth yes|            268|            903|

P  0.771136 %, R  0.753128 %, F1  0.762025 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            903|            268|
|       Truth yes|            296|            828|

P  0.736655 %, R  0.755474 %, F1  0.745946 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1731|            564|
|       Truth yes|            564|           1731|

P  0.754248 %, R  0.754248 %, F1  0.754248 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1057|             67|
|       Truth yes|            616|            555|

P  0.473954 %, R  0.892283 %, F1  0.619074 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            555|            616|
|       Truth yes|             67|           1057|

P  0.940391 %, R  0.631799 %, F1  0.755810 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1612|            683|
|       Truth yes|            683|           1612|

P  0.702397 %, R  0.702397 %, F1  0.702397 %

