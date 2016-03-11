package com.example.ctc615017.warikancalculator;

import java.util.ArrayList;

/**
 * Created by ctc615017 on 2016/02/29.
 */
public class WarikanGroup {
    private String statusName;
    private int numOfPeople = 0;
    private int amountOfMoney;
    private double weight;

    //役職名取得
    public String getStatusName() {
        return statusName;
    }

    //役職名セット
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    //各役職の人数の取得
    public int getNumOfPeople() {
        return numOfPeople;
    }

    //各役職の人数のセット
    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
    }

    //Listの人数の上ボタン処理
    public int addNumObPeople(int addNum) {
        return numOfPeople += addNum;
    }

    //Listの人数の下ボタン処理
    public int subNumObPeople(int subNum) {
        return numOfPeople -= subNum;
    }

    //List各項目の支払金額セット
    public void setAmountOfMoney(int amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    //List各項目の支払金額取得
    public int getAmountOfMoney() {
        return amountOfMoney;
    }

    //各役職の重みセット
    public void setWeight(double weight) {
        this.weight = weight;
    }

    //各役職の重み取得
    public double getWeight() {
        return weight;
    }

    //Listの各支払額の下ボタン処理
    public int addAmountOfMoney(int addAmount) {
        return amountOfMoney += addAmount;
    }

    //Listの各支払額の上ボタン処理
    public int subAmountOfMoney(int subAmount) {
        return amountOfMoney -= subAmount;
    }

}
