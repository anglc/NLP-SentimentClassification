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
|        Truth no|            257|            343|
|       Truth yes|             21|            179|

P  0.895000 %, R  0.342912 %, F1  0.495845 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            179|             21|
|       Truth yes|            343|            257|

P  0.428333 %, R  0.924460 %, F1  0.585421 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            436|            364|
|       Truth yes|            364|            436|

P  0.545000 %, R  0.545000 %, F1  0.545000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            157|             43|
|       Truth yes|             77|            123|

P  0.615000 %, R  0.740964 %, F1  0.672131 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            123|             77|
|       Truth yes|             43|            157|

P  0.785000 %, R  0.670940 %, F1  0.723502 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            280|            120|
|       Truth yes|            120|            280|

P  0.700000 %, R  0.700000 %, F1  0.700000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            170|             30|
|       Truth yes|             29|            171|

P  0.855000 %, R  0.850746 %, F1  0.852868 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            171|             29|
|       Truth yes|             30|            170|

P  0.850000 %, R  0.854271 %, F1  0.852130 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            341|             59|
|       Truth yes|             59|            341|

P  0.852500 %, R  0.852500 %, F1  0.852500 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            165|             35|
|       Truth yes|             22|            178|

P  0.890000 %, R  0.835681 %, F1  0.861985 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            178|             22|
|       Truth yes|             35|            165|

P  0.825000 %, R  0.882353 %, F1  0.852713 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            343|             57|
|       Truth yes|             57|            343|

P  0.857500 %, R  0.857500 %, F1  0.857500 %

