package com.codemcd.lotto;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final int MIN_LOTTO_PRICE = 1000;
    private static final int MAX_RANK_NUMBER = 5;

    private List<Lotto> lottoList;
    private List<Integer> countOfRank = new ArrayList<>(MAX_RANK_NUMBER + 1);
    private int totalPrizeMoney = 0;

    public void start() {
        int money = Input.inputMoneyAndReturn();
        buyLotto(money);
        Output.printLottoNumbers(lottoList);
        WinningLotto winningLotto = Input.inputWinningAndBonusNumber();
        matchingLotto(winningLotto);
        double earningsRate = calculateEarningsRate(money);
        Output.printLottoResult(countOfRank, earningsRate);
    }

    private void buyLotto(int money) {
        int numberOfLotto = money / MIN_LOTTO_PRICE;
        lottoList = new ArrayList<>(numberOfLotto);
        for (int i = 0; i < numberOfLotto; ++i) {
            lottoList.add(new Lotto(RandomNumber.makeLottoNumber()));
        }
    }

    private void matchingLotto(WinningLotto winningLotto) {
        initializeCountOfRankList();
        for (int i = 0; i < lottoList.size(); ++i) {
            Rank currentRank = winningLotto.match(lottoList.get(i));
            countOfRank.set(currentRank.getNumberOfRank(),
                    countOfRank.get(currentRank.getNumberOfRank()) + 1);
            totalPrizeMoney += currentRank.getWinningMoney();
        }
    }

    private void initializeCountOfRankList() {
        for (int i = 0; i < MAX_RANK_NUMBER + 1; ++i)
            countOfRank.add(0);
    }

    private double calculateEarningsRate(int money) {
        return totalPrizeMoney / money;
    }
}
