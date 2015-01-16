# NLP SentimentClassification #

[blog content](http://morris821028.github.io/2014/12/05/NLP-paper2/)

## Reference ##

2006 Comparative Experiments on Sentiment Classification for Online Product Reviews.
Hang Cui, Vibhu Mittal, Mayur Datar [here][1]

[1]: http://dl.acm.org/citation.cfm?id=1597389

## Data Crawler ##

* [1000 positive and 1000 negative processed reviews](http://www.cs.cornell.edu/People/pabo/movie-review-data/)
* [25 Movies So Bad They're Unmissable](http://www.rottentomatoes.com/m/showgirls/news/1868670/1/25_movies_so_bad_theyre_unmissable/)
* [The 50 Worst Movies Ever](http://www.empireonline.com/features/50-worst-movies-ever/default.asp?film=1)
* [Rotten Tomatoes > Movie > On Dvd & Streaming > Browse All](http://www.rottentomatoes.com/)

## To Do ##

* add more features

## Complete ##

* Simple Passive-Aggressive Algorithm [here][1]
* Simple Winnow algorithm [here][1]
* Simple Language Modeling [here][1]
* Passive-Aggressive Algorithm & Winnow algorithm adjust training [here][1]

[1]: http://dl.acm.org/citation.cfm?id=1597389

## Usage ##

* JavaSE-1.7
* Eclipse

If you compiler error with `NOT FOUND MAIN CLASS` on eclipse, find menu bar `Project > Clean ...` and run it to rebuild `/bin`.

## Options ##

```
prompt> java -jar NLP-SentimentClassification.jar

Usage: java -jar NLP-SentimentClassification.jar [options]

Input Options

	-path <TRAINING_PATH>	training set folder path. default <TRAINING_PATH>="training_set".

	-tpath <TEST_PATH>		user test folder path. default <TEST_PATH>="user_test".

Processing Options	
	
	-n <NGRAM_MAX>			n-grams size. default <NGRAM_MAX>="3".

	-top <FEATURE_MAX>		pick the number of feature n-grams. default <FEATURE_MAX>="40000".

	-cross <CROSS_TIMES>	default <CROSS_TIMES>="5".

	-crosspart <RATIO>		pick <RATIO> : 5 = TRAINING : OTHER, default <RATIO>="5".

	-ittime <ITCOUNT> 		training model, default <ITCOUNT>="20"

	-oittime <ITCOUNT>		online training model, default <ITCOUNT>="20"

	-ui 					call UI interface.
```

## Sample ##

### Sample 1 ###

```
java -jar NLP-SentimentClassification.jar -n 3 -top 40000 -path training_set -tpath user_test
```

### Sample 2 ###

```
java -jar NLP-SentimentClassification.jar -n 3 -top 10000 -path training_set2 -tpath user_test
```

output with markdown format, and classifier will output under `path/output/`. For example, `ouotput/neg/Adaboost.txt`.

`<TRAINING_PATH>` folder must give the file dictionary like this

```
.
├── extra		// support data, like dictionary, ban list, ...
├── neg 		// negative processed data 
└── pos 		// positive processed data
```

`<TEST_PATH>` folder must give the file dictionary like this

```
.
├── neg 		// test data which expected negative
└── pos 		// test data which expected positive
```

If you want to test unknown data, put them in `user_test/neg` or `user_test/pos` folder. Program will generate result of classifier to `output/neg` or `output/pos` folder. 

Good Luck.