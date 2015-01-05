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
|        Truth no|            255|            345|
|       Truth yes|             21|            179|

P  0.895000 %, R  0.341603 %, F1  0.494475 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            179|             21|
|       Truth yes|            345|            255|

P  0.425000 %, R  0.923913 %, F1  0.582192 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            434|            366|
|       Truth yes|            366|            434|

P  0.542500 %, R  0.542500 %, F1  0.542500 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            147|             53|
|       Truth yes|             72|            128|

P  0.640000 %, R  0.707182 %, F1  0.671916 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            128|             72|
|       Truth yes|             53|            147|

P  0.735000 %, R  0.671233 %, F1  0.701671 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            275|            125|
|       Truth yes|            125|            275|

P  0.687500 %, R  0.687500 %, F1  0.687500 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            177|             23|
|       Truth yes|             41|            159|

P  0.795000 %, R  0.873626 %, F1  0.832461 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            159|             41|
|       Truth yes|             23|            177|

P  0.885000 %, R  0.811927 %, F1  0.846890 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            336|             64|
|       Truth yes|             64|            336|

P  0.840000 %, R  0.840000 %, F1  0.840000 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            178|             22|
|       Truth yes|             32|            168|

P  0.840000 %, R  0.884211 %, F1  0.861538 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            168|             32|
|       Truth yes|             22|            178|

P  0.890000 %, R  0.847619 %, F1  0.868293 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            346|             54|
|       Truth yes|             54|            346|

P  0.865000 %, R  0.865000 %, F1  0.865000 %

