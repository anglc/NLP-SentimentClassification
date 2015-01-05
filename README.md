# NLP SentimentClassification #

[blog content](http://morris821028.github.io/2014/12/05/NLP-paper2/)

## Reference ##

2006 Comparative Experiments on Sentiment Classification for Online Product Reviews.
Hang Cui, Vibhu Mittal, Mayur Datar [link](http://dl.acm.org/citation.cfm?id=1597389)

## To do ##

* add more features

## Complete ##

* Simple Passive-Aggressive Algorithm
* Simple Winnow algorithm
* Simple Language Modeling
* Passive-Aggressive Algorithm & Winnow algorithm adjust training

## Usage ##

* JavaSE-1.7
* Eclipse

## Sample Output (stdout) ##

with markdown format, and classifier will output under `path/output/`. For example, `training_set/ouotput/neg/Adaboost.txt`.

Must give the file dictionary like this

```
.
├── extra
├── neg
├── output
│   ├── neg
│   ├── pos
│   └── unknown
├── pos
└── user_test
    ├── neg
    ├── pos
    └── unknown
```

Good Luck.

```
## Configuration ##

* Ngram 4
* topNgram 10000

## Work ##

* positive sieve 4-grams building ...
* negative sieve 4-grams building ...

* positive #ngram 810482
* negative #ngram 725426

## Language Model ##

* Language Model prepare ...
* Language Model self-testing ...
* Language Model testing ...

Table `Language Model Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            122|            128|
|       Truth yes|             16|            234|

P  0.936000 %, R  0.646409 %, F1  0.764706 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            234|             16|
|       Truth yes|            128|            122|

P  0.488000 %, R  0.884058 %, F1  0.628866 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            356|            144|
|       Truth yes|            144|            356|

P  0.712000 %, R  0.712000 %, F1  0.712000 %


## Winnow Algorithm ##

* Winnow algorithm prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Winnow algorithm self-testing ...
* Winnow algorithm testing ...

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            160|             90|
|       Truth yes|             66|            184|

P  0.736000 %, R  0.671533 %, F1  0.702290 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            184|             66|
|       Truth yes|             90|            160|

P  0.640000 %, R  0.707965 %, F1  0.672269 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            344|            156|
|       Truth yes|            156|            344|

P  0.688000 %, R  0.688000 %, F1  0.688000 %

## Passive-Aggressive Algorithm ##

* Passive-Aggressive algorithm top-10000 prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Passive-Aggressive algorithm self-testing ...
* Passive-Aggressive algorithm testing ...

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            196|             54|
|       Truth yes|             28|            222|

P  0.888000 %, R  0.804348 %, F1  0.844106 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            222|             28|
|       Truth yes|             54|            196|

P  0.784000 %, R  0.875000 %, F1  0.827004 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            418|             82|
|       Truth yes|             82|            418|

P  0.836000 %, R  0.836000 %, F1  0.836000 %


## Adaboost ##

* Prepare Meeting Machine ...


complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            199|             51|
|       Truth yes|             21|            229|

P  0.916000 %, R  0.817857 %, F1  0.864151 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            229|             21|
|       Truth yes|             51|            199|

P  0.796000 %, R  0.904545 %, F1  0.846809 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            428|             72|
|       Truth yes|             72|            428|

P  0.856000 %, R  0.856000 %, F1  0.856000 %


# User Require #

Table `Language Model Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             15|             11|
|       Truth yes|              0|              1|

P  1.000000 %, R  0.083333 %, F1  0.153846 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|              1|              0|
|       Truth yes|             11|             15|

P  0.576923 %, R  1.000000 %, F1  0.731707 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             16|             11|
|       Truth yes|             11|             16|

P  0.592593 %, R  0.592593 %, F1  0.592593 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             25|              0|
|       Truth yes|              1|              0|

P  0.000000 %, R  NaN %, F1  NaN %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|              0|              1|
|       Truth yes|              0|             25|

P  1.000000 %, R  0.961538 %, F1  0.980392 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             25|              1|
|       Truth yes|              1|             25|

P  0.961538 %, R  0.961538 %, F1  0.961538 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             13|             12|
|       Truth yes|              1|              0|

P  0.000000 %, R  0.000000 %, F1  NaN %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|              0|              1|
|       Truth yes|             12|             13|

P  0.520000 %, R  0.928571 %, F1  0.666667 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             13|             13|
|       Truth yes|             13|             13|

P  0.500000 %, R  0.500000 %, F1  0.500000 %

* Meeting prepare ...
* Meeting top-10000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             24|              1|
|       Truth yes|              1|              0|

P  0.000000 %, R  0.000000 %, F1  NaN %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|              0|              1|
|       Truth yes|              1|             24|

P  0.960000 %, R  0.960000 %, F1  0.960000 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|             24|              2|
|       Truth yes|              2|             24|

P  0.923077 %, R  0.923077 %, F1  0.923077 %

```