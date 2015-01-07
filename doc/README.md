# 極性分析 #

## Stop Word List ##

This stopword list is probably the most widely used stopword list. It covers a wide number of stopwords without getting too aggressive and including too many words which a user might search upon. This wordlist contains only 11 words.

```
the
i
it
he
she
they
we
you
-
a
an
```

比起其他網路上的 stop word list 少了很多介系詞，原因是這樣的，例如 `who, whom, where, ...` 這些詞作為描述對象的主體，這些主體通常不可省略。例如在描述『演員很不錯，但是電影劇本很糟糕。』這段話時，根據實驗的幾種模型，如果忽略主體的存在，很容易造成描述的需求面向錯誤。必然地，無法將 `movie, film` 過濾掉，主體是相當重要的，即使他在單一詞的極性上不夠明確。

## Parsing Sentence ##

首先，必須把所有縮寫單位展開，當然有可能把錯誤的 `Bob's` 展開成錯誤的意思，實驗上不構成多大的影響，但以下的操作會更明確地把潛在的 feature 更清楚地劃分，而不會挑到已經重複的。

```
`can't` = `can not`
`n't` = ` not`
`'re` = ` are`
`'m` = ` am`
`'s` = ` is`
`'ve` = ` have`
`'ll` = ` will`
... maybe more
```

再來，必須針對符號轉英詞，有可能是在描述電影名稱會用到 `&`，一律將其轉換成 `and` 會更有效。

```
`&` = ` and`
```

最後，語法上的變換統一。這可能會遭遇到描述『過去的版本很好，現在這個版本很糟。』也許規則應該對時間做點細部劃分。

```
`am, are, is, was, were` = `be`
`no` = `not`
```

更多的口語描述，例如 `uuuuuuuugggggggggglllllllllllyyyyyyy = ugly` 的可能，特別針對 long duplicate word 特別處理，查看濃縮之後，是否會在字典中。狀聲詞 `oh, wow, ah, ...` 可能是一個極性指標，在此就不濾掉這幾個單詞。

當使用 stop word 過濾某一個句子時，剩餘的詞應該串在一起成為新的句子，而不以 stop word 分開成新的句子。

## Filter N-grams Feature ##

在挑選 n-grams 時，根據給定的公式，從 800 正向評論、800 反向評論中，大約會得到 50M ~ 100M 不同的 n-grams。當我們篩選 n = 3 時，`bad` 將可能被儲存為 `(bad, null, null)`。挑選時，必須保障 high order n-grams 佔有一定的數量，大約落在 `n-grams : (n+1)-grams = 7 : 3`。

評分時，額外增加 high order 的評分權重，以下是程式中使用的分配。

```
N-grams Bonus = {1, 1.02, 1.03, 1.04, 1.05, 1.06, 1.07};
```

這麼做確保實驗上有保留之前的優良，同時也增加新的元素進去。

當挑選 K-top feature 時，必須將正反兩方的 feature N-grams 分別佔有約 50%，並且去掉同時挑到的情況。

更多的策略，例如 `(bad, too, null)` 可以視為 `(too, bad, null)`，使用 dag 的方式紀錄 N-grams。

## How to decide K-top ##

明顯地觀察到，K 越大時，Language Model 越穩定，平均效能穩定，但最好效能可能會下降，對於 Winnow algorithm 或者是 Passive-Aggressive algorithm 來說，feature vector 會非常大，造成在找到合適的參數會消耗更多的時間，並且難在有限的迭代次數中找到好的切平面。

當 K 很大時，部分的 n-grams 甚至只出現在某些的 training set 中，這造成訓練 Winnow, Passive-Aggressive 的結果並不好，過於分配無用的權重給只出現在 training set 的 feature。在實驗中，挑選 K = 30000 與 K = 50000 的差異並不大，挑選的比例約為 5% 以內。

### Improve ##

發現到 Winnow, Passive-Aggressive (PA) 都是以閥值作為判斷標準，因此當得到靠近閥值的數據下，判斷能力是相當脆弱。雖然 PA 在平均表現最好，大約落在 85% 附近，遠比 LM 和 Winnow 多了 5% ~ 10% 的準確度，如何加強？ 

# 程式撰寫 #

## N-grams Storing ##
