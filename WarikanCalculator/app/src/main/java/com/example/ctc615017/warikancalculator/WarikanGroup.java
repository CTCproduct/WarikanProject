package com.example.ctc615017.warikancalculator;

/**
 * Created by ctc615017 on 2016/02/29.
 * 各itemのステータスクラス
 */
public class WarikanGroup {
    private String statusName;
    private int numOfPeople = 0;
    private int amountOfMoney;
    private double weight = 1;
    private boolean chkbox = false;

    /**
     * 役職名取得
     * @return 役職名
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 役職名セット
     * @param statusName 役職名
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 各役職の人数の取得
     * @return 各役職の人数
     */
    public int getNumOfPeople() {
        return numOfPeople;
    }

    /**
     * 各役職の人数のセット
     * @param numOfPeople 各役職の人数
     */
    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    /**
     * Listの人数の上ボタン処理
     * @param addNum 加算する数
     * @return 各役職の人数
     */
    public int addNumObPeople(int addNum) {
        return numOfPeople += addNum;
    }

    /**
     * Listの人数の下ボタン処理
     * @param subNum 減算する数
     * @return 各役職の人数
     */
    public int subNumObPeople(int subNum) {
        return numOfPeople -= subNum;
    }

    /**
     * List各項目の支払金額セット
     * @param amountOfMoney 支払金額
     */
    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    /**
     * List各項目の支払金額取得
     * @return 支払金額
     */
    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    /**
     * 各役職の重みセット
     * @param weight 重み
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * 各役職の重み取得
     * @return 重み
     */
    public double getWeight() {
        if (getSelected() == false && weight != 0) {
            weight = 0;
        } else if (getSelected() == true && weight == 0) {
            weight = 1;
        }
        return weight;
    }

    /**
     * チェックボックス値セット
     * @param chkbox チェックボックスの値
     */
    public void setSelected(boolean chkbox) {
        this.chkbox = chkbox;
    }

    /**
     * チェックボックス値取得
     * @return チェックボックスの値
     */
    public boolean getSelected() {
        return chkbox;
    }

    /**
     * 設定画面:重みの加算
     * @param addNum 加算する数
     * @return 重み
     */
    public double addWeight(double addNum) {
        return this.weight = weight * 10 + addNum;
    }

    /**
     * 設定画面:重みの減算
     * @param subNum 減算する数
     * @return 重み
     */
    public double subWeight(double subNum) {
        return this.weight = weight * 10 - subNum;
    }

}
