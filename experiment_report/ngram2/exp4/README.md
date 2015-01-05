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
|        Truth no|            222|            378|
|       Truth yes|              6|            194|

P  0.970000 %, R  0.339161 %, F1  0.502591 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            194|              6|
|       Truth yes|            378|            222|

P  0.370000 %, R  0.973684 %, F1  0.536232 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            416|            384|
|       Truth yes|            384|            416|

P  0.520000 %, R  0.520000 %, F1  0.520000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            129|             71|
|       Truth yes|             58|            142|

P  0.710000 %, R  0.666667 %, F1  0.687651 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            142|             58|
|       Truth yes|             71|            129|

P  0.645000 %, R  0.689840 %, F1  0.666667 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            271|            129|
|       Truth yes|            129|            271|

P  0.677500 %, R  0.677500 %, F1  0.677500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            170|             30|
|       Truth yes|             23|            177|

P  0.885000 %, R  0.855072 %, F1  0.869779 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            177|             23|
|       Truth yes|             30|            170|

P  0.850000 %, R  0.880829 %, F1  0.865140 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            347|             53|
|       Truth yes|             53|            347|

P  0.867500 %, R  0.867500 %, F1  0.867500 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            161|             39|
|       Truth yes|             14|            186|

P  0.930000 %, R  0.826667 %, F1  0.875294 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            186|             14|
|       Truth yes|             39|            161|

P  0.805000 %, R  0.920000 %, F1  0.858667 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            347|             53|
|       Truth yes|             53|            347|

P  0.867500 %, R  0.867500 %, F1  0.867500 %

