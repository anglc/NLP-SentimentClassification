# Polarity Analysis for Sentiment Classification #

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
`film` = `movie`
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

## How To Decide K-top ##

明顯地觀察到，K 越大時，Language Model 越穩定，平均效能穩定，但最好效能可能會下降，對於 Winnow algorithm 或者是 Passive-Aggressive algorithm 來說，feature vector 會非常大，造成在找到合適的參數會消耗更多的時間，並且難在有限的迭代次數中找到好的切平面。

當 K 很大時，部分的 n-grams 甚至只出現在某些的 training set 中，這造成訓練 Winnow, Passive-Aggressive 的結果並不好，過於分配無用的權重給只出現在 training set 的 feature。在實驗中，挑選 K = 30000 與 K = 50000 的差異並不大，挑選的比例約為 5% 以內。

### Improve ###

#### LM Classifier ####

關於論文中提到的 LM filter，試圖去替除掉一些主觀句子，根據每一個句子進行閥值的評測，實驗中測試到非常低的值作為閥值才具有較好的可能性，由於太高、太低都會使得準確度下降，估計這個閥值的調整不具有普遍性。

而其他的 Classifier 並沒有做這類的主客觀篩選，也許可以藉由主客觀句子的判斷做一套分類器，說不定可以改善另外兩個分類器的語意判斷能力。

#### Combine Classifier ####

發現到 Winnow, Passive-Aggressive (PA) 都是以閥值作為判斷標準，因此當得到靠近閥值的數據下，判斷能力是相當脆弱。雖然 PA 在平均表現最好，大約落在 85% 附近，遠比 LM 和 Winnow 多了 5% ~ 10% 的準確度，如何加強？ 

將四個 Classifier 串在一起，並且用八個 attribute 作為一個 feature vector，接著訓練這一個感知機。這八個 attribute 分別是每一個 Classifier 的判斷強度。

```
(LM_POS, LM_NEG, WINNOW_POS, WINNOW_NEG, PA_POS, PA_NEG, SIMPLE_POS, SIMPLE_NEG)
```

其中由於 Language Model (LM) 的機率不好量化，因此單純採用 0/1 的方式表示。嘗試過取 log 發現仍然並不好。

* LM_POS : if LM.classify(x) = POS, then LM_POS = 1. Otherwise LM_POS = 0
* LM_NEG : if LM.classify(x) = NEG, then LM_NEG = 1. Otherwise LM_POS = 0
* WINNOW_POS : if Winnow.classify(x) = POS, the WINNOW_POS = Winnow.strongClassify(x).
* WINNOW_NEG : if Winnow.classify(x) = NEG, the WINNOW_NEG = Winnow.strongClassify(x).

strongClassify(x) 從 training data 中得到 h(x) 的出現的最大值，然後根據將判斷的函數大小得到，`strongClassify(x) = h(x) / TRAINING_MAX_H_VALUE`，之所以不直接使用 `strongClassify(x) = h(x)` 是因為很容易造成 overflow 或者是過度的調整判斷。在實驗結果後，將後者公式調整為前者所使用的。

當然可以訓練多台，並且串在一起，但是這種串法必須盡可能有所歧異性，並不是串越多越好，可以藉較少次的迭代次數、洗牌後的訓練序列來達到歧異性。PA 具有良好的適應性，在訓練集與測資集大小、差異不大時，效能仍然可以保持著線性關係，相當具有魯棒性。 

在實驗觀察中可以明白感知器在越少 feature 下，可以在越少迭代次數中訓練完成，相對地適應能力就會隨差異嚴重波動，實驗中使用的幾個感知機模型，都能在 vector 得到後的幾秒內完成訓練，不用勞費 SVM 的數個小時。Language Model 則會因為 feature 越多，展現更加穩定的效能，即便如此，LM 在負面評論的辨識率仍然不高，這一點從論文中也可以看得出具有相同的現象。

藉此把 LM 對於負面評論辨識率很差的特性，才將其判斷與其他的感知機串在一起使用。這一類的串許多的分類器的算法，可以參照 Adaboost (Adaptive Boosting) 的想法。

#### N-grams Score ####

特別小心公式的計算，雖然有很多乘除法，可以使用 `Math.log` 降下來，防止 overflow 的可能，但同時也會造成嚴重的浮點數誤差。所以使用恰當的 `double` 運算即可，即使遇到 `NaN` 也沒有關係。

論文中提及的公式，額外增加變成

```
\chi^{2}(t, c) = \frac{N \times (AD - CB)^{2} }{(A+C)\times (B + D) \times (A + B) \times (C + D)} \times Weight[t.getSize()] \times Score(t)
```

其中，`Weight[t.getSize()]` 正如上述的 `N-grams Bonus`，當最大上為 `n = 5` 時，部分 unigram、bigram 仍然有用。而 `Score(t)` 則是取額外資料中的 `AFINN-111.txt` 單詞正反面的絕對值權重和，有可能正負兩個詞合併成 bigram 來描述更強烈的負面，因此必須取絕對值。

#### Vector ####

選擇 K-top feature n-grams 後，感知機的 Vector 如何放置權重仍然是個困難，從實驗中，單純拿 `n-grams appear times` 作為一個 attribute weight 效果並不好，於是嘗試拿 `Math.log(n-grams appear times)`，但是效果並不好，有可能是浮點數誤差造成的差異並不大，而 `Math.log` 本身就很小，尤其是 `n-grams appear times = 1` 的時候會變成 0，額外加上一個基底 base 來補足也拿以取捨。

最後取用

```
vector[i] = Score(ngrams(i)) + Math.sqrt(n-grams(i) appear times)
```

這部分仍然要做實驗，`Score(ngrams(i))` 大約落在 `1 ~ 10` 之間。

#### Process ####

經過幾次的 cross validation 後，每一次會挑到不同的 feature n-grams，藉由交叉驗證得到不同的精準度 P，同時也將挑選的 n-grams 增加 P 的權重，在實驗中總共做了 5 次 cross validation，針對同一組 800 資料，進行 `1 : 1` 的劃分。原本預期挑選 40K 個不同的 n-grams 作為 feature，但是經過 5 次實驗，總共得到 50K 個不同的 n-grams，根據累加的 P 值進行由大排到小，挑選 1/5 的 n-grams 出來，最後挑了少於 10K 個做為 feature。

針對已知的 1600 筆資料進行 `3 : 1` 的劃分，先對數量較多的資料重訓練，隨後才將數量較少放在一起做第二次訓練。防止過度的訓練，導致感知器針對訓練集的已知資訊分配過多的權重，反而針對未知的元素不足以判斷。

## extra data support ##

* AFINN-111.txt
* positive word list (ignore) 毫無幫助
* negative word list (ignore) 毫無幫助
* negation not (ignore) 目前發現只會更糟糕

# 程式撰寫 #

## N-grams Storing ##

`string` 轉 `integer` 標記。

## How to improve experiment ##

先用同一份資料訓練、測試，查看是否接近 `P = R = 100%`，接著放入未知的資料，找到挑選 feature n-grams 之間的差異。

列出幾個可能的差異後，從訓練的感知機中得到每一項的權重，由於是線性分類器，權重的大小即可作為是否具有特色，通常差距會達到 10 ~ 100 倍之間。即使從 N-grams score 得到較高的分數，從感知機中會發現到未必是較大的權重，有可能是某幾篇相關的電影所造成的一面倒。

# To Do #

增加兩個不在 top feature 中的 attribute，但是在 pos/neg word weight 中的 n-grams 所評分的結果。在量化這些 n-grams 的分數時，不管正反面的強度，一律取絕對值進行加總，有可能一個正面單詞跟一個負面單詞合併在一起來表示一個更強烈的正面或反面資訊。
