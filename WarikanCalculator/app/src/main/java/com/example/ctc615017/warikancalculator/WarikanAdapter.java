package com.example.ctc615017.warikancalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ctc615017 on 2016/02/29.
 */
public class WarikanAdapter extends ArrayAdapter {
    private ArrayList<WarikanGroup> items;
    private LayoutInflater inflater;
    private static int unit = 1;
    private static int accountingTotal = 0;
    private static int collectTotal = 0;
    private static int numOfPeople =0;
    private static int diffMoney = 0;

    public WarikanAdapter(Context context, int textViewResourceId, ArrayList items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // データの取得
        final WarikanGroup item = (WarikanGroup)items.get(position);

        View view = convertView;
        if (view == null) {
        // 受け取ったビューがnullなら新しくビューを生成
            view = inflater.inflate(R.layout.adapter_layout, null);
        }

        if (item != null) {
            //statusTxtのセット
            TextView status_txt = (TextView) view.findViewById(R.id.statusTxt);
            status_txt.setText(item.getStatusName());
            if (status_txt != null) {
                status_txt.setText(item.getStatusName());
            }

            final TextView numOfPeople_txt = (TextView) view.findViewById(R.id.numOfPeople);
            numOfPeople_txt.setText(String.valueOf(item.getNumOfPeople()));
            final TextView amountPayment_txt = (TextView) view.findViewById(R.id.amountPayment);
            amountPayment_txt.setText(String.valueOf(item.getAmountOfMoney()));

            //peoplePlusBtnのセット
            Button peoplePlus_btn = (Button) view.findViewById(R.id.peoplePlusBtn);
            peoplePlus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = item.addNumObPeople(1);
                    item.setNumOfPeople(num);
                    totalPaymentMoney();
                    calcPaymentMoney();
                    summaryCount();
                }
            });

            //peopleMinusBtnのセット
            Button peopleMinus_btn = (Button) view.findViewById(R.id.peopleMinusBtn);
            peopleMinus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = item.getNumOfPeople();
                    if (num > 0) {
                        num = item.subNumObPeople(1);
                        item.setNumOfPeople(num);
                        totalPaymentMoney();
                        calcPaymentMoney();
                        summaryCount();
                    }
                }
            });

            /*//moneyPlusBtnのセット
            Button moneyPlus_btn = (Button) view.findViewById(R.id.moneyPlusBtn);
            moneyPlus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = item.addAmountOfMoney(getUnit());
                    amountPayment_txt.setText(Integer.toString(num));
                    amountPayment_txt.setText(Integer.toString(num));
                    summaryCount();
                }
            });

            //moneyMinusBtnのセット
            Button moneyMinus_btn = (Button) view.findViewById(R.id.moneyMinusBtn);
            moneyMinus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = item.getAmountOfMoney();
                    if (num > 0) {
                        num = item.subAmountOfMoney(getUnit());
                        amountPayment_txt.setText(Integer.toString(num));
                        summaryCount();
                    }
                }
            });*/
        }
        return view;
    }

    public interface ChangeSummaryListener {
        void onChengeSummary(int summary, int collectionTotal);
    }
    private ChangeSummaryListener mChangeSummaryListener = null;
    public void setOnChangeSummaryListener(ChangeSummaryListener listener) {
        mChangeSummaryListener = listener;
    }

    protected void summaryCount() {
        int summary = 0;
        int collectionTotal = 0;
        if (mChangeSummaryListener != null) {
                for (WarikanGroup item : items){
                    summary += item.getNumOfPeople();
                    collectionTotal += item.getNumOfPeople() * item.getAmountOfMoney();
                }
            mChangeSummaryListener.onChengeSummary(summary, collectionTotal);
        }
    }

    //各役職者の支払金額(1人あたり)
    public void totalPaymentMoney() {
        for (WarikanGroup i : items) {
            double totalWeight = 0;
            double payment = 0;
            for (WarikanGroup item : items) {
                totalWeight += item.getWeight() * item.getNumOfPeople();
            }

            if (totalWeight != 0) {
                if (i.getNumOfPeople() != 0) {
                    payment = getAccountingTotal() * i.getWeight() / totalWeight;
                } else if (i.getNumOfPeople() == 0) {
                    payment = 0;
                }
            } else {
                payment = 0;
            }
            i.setAmountOfMoney((int) Math.floor(payment / WarikanAdapter.getUnit()) * WarikanAdapter.getUnit());
        }
    }

    //重み最大値の役職者の支払金額
    public void calcPaymentMoney() {
        double money = 0;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getNumOfPeople() != 0) {
                //重み最大値の役職者以外の支払金額
                for (int j = i + 1; j < items.size(); j++) {
                    money += items.get(j).getAmountOfMoney() * items.get(j).getNumOfPeople();
                }
                int pay = (int) Math.ceil((getAccountingTotal() - money) / WarikanAdapter.getUnit() / items.get(i).getNumOfPeople()) * WarikanAdapter.getUnit();
                items.get(i).setAmountOfMoney(pay);
                break;
            } else if (items.get(i).getNumOfPeople() == 0) {
                items.get(i).setAmountOfMoney(0);
            }
        }
    }

    //単位の取得
    public static int getUnit() {
        return unit;
    }

    //単位をセット
    public static void setUnit(int unit) {
        WarikanAdapter.unit = unit;
    }

    //会計総額の値セット
    public static void setAccountingTotal(int accountingTotal) {
        WarikanAdapter.accountingTotal = accountingTotal;
    }

    //会計総額の値取得
    public static int getAccountingTotal() {
        return accountingTotal;
    }

    //集金総額の値セット
    public static void setCollectTotal(int collectTotal) {
        WarikanAdapter.collectTotal = collectTotal;
    }

    //集金総額の値取得
    public static int getCollectTotal() {
        return collectTotal;
    }

    //合計人数の値セット
    public static void setNumOfPeople(int numOfPeople) {
        WarikanAdapter.numOfPeople = numOfPeople;
    }

    //合計人数の値取得
    public static int getNumOfPeople() {
        return numOfPeople;
    }

    //集金総額の値セット
    public static void setDiffMoney(int diffMoney) {
        WarikanAdapter.diffMoney = diffMoney;
    }

    //集金総額の値取得
    public static int getDiffMoney() {
        return diffMoney;
    }
}
