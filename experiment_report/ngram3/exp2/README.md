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
|        Truth no|            128|             72|
|       Truth yes|             43|            157|

P  0.785000 %, R  0.685590 %, F1  0.731935 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            157|             43|
|       Truth yes|             72|            128|

P  0.640000 %, R  0.748538 %, F1  0.690027 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            285|            115|
|       Truth yes|            115|            285|

P  0.712500 %, R  0.712500 %, F1  0.712500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            172|             28|
|       Truth yes|             32|            168|

P  0.840000 %, R  0.857143 %, F1  0.848485 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            168|             32|
|       Truth yes|             28|            172|

P  0.860000 %, R  0.843137 %, F1  0.851485 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            340|             60|
|       Truth yes|             60|            340|

P  0.850000 %, R  0.850000 %, F1  0.850000 %

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            167|             33|
|       Truth yes|             24|            176|

P  0.880000 %, R  0.842105 %, F1  0.860636 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            176|             24|
|       Truth yes|             33|            167|

P  0.835000 %, R  0.874346 %, F1  0.854220 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            343|             57|
|       Truth yes|             57|            343|

P  0.857500 %, R  0.857500 %, F1  0.857500 %

