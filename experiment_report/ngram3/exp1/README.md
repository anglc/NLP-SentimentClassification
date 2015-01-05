## Configuration ##

* Ngram 3
* topNgram 10000

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
|        Truth no|            244|            356|
|       Truth yes|             12|            188|

P  0.940000 %, R  0.345588 %, F1  0.505376 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            188|             12|
|       Truth yes|            356|            244|

P  0.406667 %, R  0.953125 %, F1  0.570093 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            432|            368|
|       Truth yes|            368|            432|

P  0.540000 %, R  0.540000 %, F1  0.540000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            130|             70|
|       Truth yes|             51|            149|

P  0.745000 %, R  0.680365 %, F1  0.711217 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            149|             51|
|       Truth yes|             70|            130|

P  0.650000 %, R  0.718232 %, F1  0.682415 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            279|            121|
|       Truth yes|            121|            279|

P  0.697500 %, R  0.697500 %, F1  0.697500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            172|             28|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.862069 %, F1  0.868486 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             28|            172|

P  0.860000 %, R  0.873096 %, F1  0.866499 %

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
|        Truth no|            175|             25|
|       Truth yes|             26|            174|

P  0.870000 %, R  0.874372 %, F1  0.872180 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            174|             26|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.870647 %, F1  0.872818 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            349|             51|
|       Truth yes|             51|            349|

P  0.872500 %, R  0.872500 %, F1  0.872500 %

