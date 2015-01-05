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
|        Truth no|            693|            431|
|       Truth yes|            348|            823|

P  0.702818 %, R  0.656300 %, F1  0.678763 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            823|            348|
|       Truth yes|            431|            693|

P  0.616548 %, R  0.665706 %, F1  0.640185 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1516|            779|
|       Truth yes|            779|           1516|

P  0.660566 %, R  0.660566 %, F1  0.660566 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1123|              1|
|       Truth yes|           1075|             96|

P  0.081981 %, R  0.989691 %, F1  0.151420 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             96|           1075|
|       Truth yes|              1|           1123|

P  0.999110 %, R  0.510919 %, F1  0.676099 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1219|           1076|
|       Truth yes|           1076|           1219|

P  0.531155 %, R  0.531155 %, F1  0.531155 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            835|            289|
|       Truth yes|            297|            874|

P  0.746371 %, R  0.751505 %, F1  0.748929 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            874|            297|
|       Truth yes|            289|            835|

P  0.742883 %, R  0.737633 %, F1  0.740248 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1709|            586|
|       Truth yes|            586|           1709|

P  0.744662 %, R  0.744662 %, F1  0.744662 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1058|             66|
|       Truth yes|            572|            599|

P  0.511529 %, R  0.900752 %, F1  0.652505 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            599|            572|
|       Truth yes|             66|           1058|

P  0.941281 %, R  0.649080 %, F1  0.768337 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|           1657|            638|
|       Truth yes|            638|           1657|

P  0.722004 %, R  0.722004 %, F1  0.722004 %

