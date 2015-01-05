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
|        Truth no|            244|            356|
|       Truth yes|             20|            180|

P  0.900000 %, R  0.335821 %, F1  0.489130 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            180|             20|
|       Truth yes|            356|            244|

P  0.406667 %, R  0.924242 %, F1  0.564815 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            424|            376|
|       Truth yes|            376|            424|

P  0.530000 %, R  0.530000 %, F1  0.530000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            150|             50|
|       Truth yes|             66|            134|

P  0.670000 %, R  0.728261 %, F1  0.697917 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            134|             66|
|       Truth yes|             50|            150|

P  0.750000 %, R  0.694444 %, F1  0.721154 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            284|            116|
|       Truth yes|            116|            284|

P  0.710000 %, R  0.710000 %, F1  0.710000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             31|            169|

P  0.845000 %, R  0.871134 %, F1  0.857868 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            169|             31|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.849515 %, F1  0.862069 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            344|             56|
|       Truth yes|             56|            344|

P  0.860000 %, R  0.860000 %, F1  0.860000 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            178|             22|
|       Truth yes|             25|            175|

P  0.875000 %, R  0.888325 %, F1  0.881612 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            175|             25|
|       Truth yes|             22|            178|

P  0.890000 %, R  0.876847 %, F1  0.883375 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            353|             47|
|       Truth yes|             47|            353|

P  0.882500 %, R  0.882500 %, F1  0.882500 %

