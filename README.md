# NLP SentimentClassification #

[blog content](http://morris821028.github.io/2014/12/05/NLP-paper2/)

## Reference ##

2006 Comparative Experiments on Sentiment Classification for Online Product Reviews.
Hang Cui, Vibhu Mittal, Mayur Datar[1]

[1]: (http://dl.acm.org/citation.cfm?id=1597389)

## data crawler ##

* [1000 positive and 1000 negative processed reviews](http://www.cs.cornell.edu/People/pabo/movie-review-data/)
* [25 Movies So Bad They're Unmissable](http://www.rottentomatoes.com/m/showgirls/news/1868670/1/25_movies_so_bad_theyre_unmissable/)
* [The 50 Worst Movies Ever](http://www.empireonline.com/features/50-worst-movies-ever/default.asp?film=1)
* [Rotten Tomatoes > Movie > On Dvd & Streaming > Browse All](http://www.rottentomatoes.com/)

## To do ##

* add more features

## Complete ##

* Simple Passive-Aggressive Algorithm[1]
* Simple Winnow algorithm[1]
* Simple Language Modeling[1]
* Passive-Aggressive Algorithm & Winnow algorithm adjust training[1]

## Usage ##

* JavaSE-1.7
* Eclipse

If you compiler error with `NOT FOUND MAIN CLASS` on eclipse, find menu bar `Project > Clean ...` and run it to rebuild `/bin`.


## Sample Output (stdout) ##

with markdown format, and classifier will output under `path/output/`. For example, `training_set/ouotput/neg/Adaboost.txt`.

Must give the file dictionary like this

```
.
├── extra		// support data, like dictionary, ban list, ...
├── neg 		// negative processed data 
├── output 	// program output
│   ├── neg
│   └── pos
├── pos 		// positive processed data
└── user_test
    ├── neg  // you think the data maybe negative data.
    └── pos
```

If you want to test unknown data, put them in `user_test/neg` or `user_test/pos` folder. Program will generate result of classifier to `output/neg` or `output/pos` folder. Good Luck.

There is a example output in runtime.

```
## Configuration ##

* Ngram 5
* topNgram 40000

## Work ##

* positive sieve 5-grams building ...
* negative sieve 5-grams building ...

* positive #ngram 728638
* negative #ngram 666344

## Simple Decision ##


complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|


## Language Model ##

* Language Model prepare ...
* Language Model self-testing ...

## Winnow Algorithm ##

* Winnow algorithm prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Winnow algorithm self-testing ...
## Passive-Aggressive Algorithm ##

* Passive-Aggressive algorithm top-40000 prepare ...

complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|

* Passive-Aggressive algorithm self-testing ...

## Adaboost ##

* Prepare Meeting Machine ...


complete |>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>|


# User Require #

Table `Language Model Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            119|             81|
|       Truth yes|              9|            191|

P  95.50 %, R  70.22 %, F1  80.93 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            191|              9|
|       Truth yes|             81|            119|

P  59.50 %, R  92.97 %, F1  72.56 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            310|             90|
|       Truth yes|             90|            310|

P  77.50 %, R  77.50 %, F1  77.50 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            151|             49|
|       Truth yes|             68|            132|

P  66.00 %, R  72.93 %, F1  69.29 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            132|             68|
|       Truth yes|             49|            151|

P  75.50 %, R  68.95 %, F1  72.08 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            283|            117|
|       Truth yes|            117|            283|

P  70.75 %, R  70.75 %, F1  70.75 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            167|             33|
|       Truth yes|             33|            167|

P  83.50 %, R  83.50 %, F1  83.50 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            167|             33|
|       Truth yes|             33|            167|

P  83.50 %, R  83.50 %, F1  83.50 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            334|             66|
|       Truth yes|             66|            334|

P  83.50 %, R  83.50 %, F1  83.50 %

Table `Simple Decision Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            135|             65|
|       Truth yes|             12|            188|

P  94.00 %, R  74.31 %, F1  83.00 %

Table `Simple Decision Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            188|             12|
|       Truth yes|             65|            135|

P  67.50 %, R  91.84 %, F1  77.81 %

Table `Simple Decision Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            323|             77|
|       Truth yes|             77|            323|

P  80.75 %, R  80.75 %, F1  80.75 %

* Meeting prepare ...
* Meeting top-40000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            174|             26|
|       Truth yes|             22|            178|

P  89.00 %, R  87.25 %, F1  88.12 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            178|             22|
|       Truth yes|             26|            174|

P  87.00 %, R  88.78 %, F1  87.88 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            352|             48|
|       Truth yes|             48|            352|

P  88.00 %, R  88.00 %, F1  88.00 %
```