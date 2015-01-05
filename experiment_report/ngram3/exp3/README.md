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
|        Truth no|            137|             63|
|       Truth yes|             51|            149|

P  0.745000 %, R  0.702830 %, F1  0.723301 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            149|             51|
|       Truth yes|             63|            137|

P  0.685000 %, R  0.728723 %, F1  0.706186 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            286|            114|
|       Truth yes|            114|            286|

P  0.715000 %, R  0.715000 %, F1  0.715000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.875000 %, F1  0.875000 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.875000 %, F1  0.875000 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            350|             50|
|       Truth yes|             50|            350|

P  0.875000 %, R  0.875000 %, F1  0.875000 %

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            169|             31|
|       Truth yes|             21|            179|

P  0.895000 %, R  0.852381 %, F1  0.873171 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            179|             21|
|       Truth yes|             31|            169|

P  0.845000 %, R  0.889474 %, F1  0.866667 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            348|             52|
|       Truth yes|             52|            348|

P  0.870000 %, R  0.870000 %, F1  0.870000 %

