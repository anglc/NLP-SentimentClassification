## Configuration ##

* Ngram 6
* topNgram 20000

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
|        Truth no|            251|            349|
|       Truth yes|             19|            181|

P  0.905000 %, R  0.341509 %, F1  0.495890 %

Table `Language Model Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            181|             19|
|       Truth yes|            349|            251|

P  0.418333 %, R  0.929630 %, F1  0.577011 %

Table `Language Model Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            432|            368|
|       Truth yes|            368|            432|

P  0.540000 %, R  0.540000 %, F1  0.540000 %

Table `Winnow Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            139|             61|
|       Truth yes|             61|            139|

P  0.695000 %, R  0.695000 %, F1  0.695000 %

Table `Winnow Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            139|             61|
|       Truth yes|             61|            139|

P  0.695000 %, R  0.695000 %, F1  0.695000 %

Table `Winnow Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            278|            122|
|       Truth yes|            122|            278|

P  0.695000 %, R  0.695000 %, F1  0.695000 %

Table `Passive-Aggressive Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            178|             22|
|       Truth yes|             34|            166|

P  0.830000 %, R  0.882979 %, F1  0.855670 %

Table `Passive-Aggressive Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            166|             34|
|       Truth yes|             22|            178|

P  0.890000 %, R  0.839623 %, F1  0.864078 %

Table `Passive-Aggressive Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            344|             56|
|       Truth yes|             56|            344|

P  0.860000 %, R  0.860000 %, F1  0.860000 %

* Meeting prepare ...
* Meeting top-20000 testing ...

Table `Adaboost Class Positive`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            173|             27|
|       Truth yes|             28|            172|

P  0.860000 %, R  0.864322 %, F1  0.862155 %

Table `Adaboost Class Negative`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            172|             28|
|       Truth yes|             27|            173|

P  0.865000 %, R  0.860697 %, F1  0.862843 %

Table `Adaboost Final`

|Truth\Classifier|  Classifier no| Classifier yes|
|----------------|---------------|---------------|
|        Truth no|            345|             55|
|       Truth yes|             55|            345|

P  0.862500 %, R  0.862500 %, F1  0.862500 %

