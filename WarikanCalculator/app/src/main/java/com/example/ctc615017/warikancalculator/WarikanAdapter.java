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
            view = inflater.inflate(R.layout.adapter, null);
            //view.setTag(item);
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
                    //WarikanGroup itemA = (WarikanGroup)v.getTag();
                    int num = item.addNumObPeople(1);
                    item.setNumOfPeople(num);
                    for (WarikanGroup item : items) {
                        totalPaymentMoney(item);
                        //double payment = totalPaymentMoney(item);
                        //item.setAmountOfMoney((int)Math.ceil(payment/100)*100);
                    }
                    if (items.get(0).getNumOfPeople() != 0) {
                        double otherMoney = otherTotalMoney();
                        int pay = (int)Math.ceil((items.get(0).getAccountingTotal() - otherMoney)/100/ items.get(0).getNumOfPeople()) * 100;
                        items.get(0).setAmountOfMoney(pay);
                    } else {
                        items.get(0).setAmountOfMoney(0);
                    }
                    summaryCount();
                }
            });

            //peopleMinusBtnのセット
            Button peopleMinus_btn = (Button) view.findViewById(R.id.peopleMinusBtn);
            peopleMinus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //int num = Integer.parseInt(numOfPeople_txt.getText().toString());
                    int num = item.getNumOfPeople();
                    if (num > 0) {
                        num = item.subNumObPeople(1);
                        item.setNumOfPeople(num);
                        for (WarikanGroup item : items) {
                            totalPaymentMoney(item);
                            //totalPaymentMoney(item);
                            //double payment = totalPaymentMoney(item);
                            //item.setAmountOfMoney((int)Math.ceil(payment/100)*100);
                        }
                        if (items.get(0).getNumOfPeople() != 0) {
                            double otherMoney = otherTotalMoney();
                            int pay = (int)Math.ceil((items.get(0).getAccountingTotal() - otherMoney)/100/ items.get(0).getNumOfPeople()) * 100;
                            items.get(0).setAmountOfMoney(pay);
                        } else {
                            items.get(0).setAmountOfMoney(0);
                        }
                        summaryCount();
                    }
                }
            });

            //moneyPlusBtnのセット
            Button moneyPlus_btn = (Button) view.findViewById(R.id.moneyPlusBtn);
            moneyPlus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = item.addAmountOfMoney(100);
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
                        num = item.subAmountOfMoney(100);
                        amountPayment_txt.setText(Integer.toString(num));
                        summaryCount();
                    }
                }
            });
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

    private void summaryCount() {
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



    /*public void totalPaymentMoney() {
        double payment = 0;
        double max = 0;
        int ans = 0;
        int totalMenber = 0;
        int member = 0;
        for (WarikanGroup item : items) {
            if( item.getWeight() > max ){
                max = item.getWeight();
            }
        }

        for (WarikanGroup item : items) {
            totalMenber += item.getWeight() * item.getNumOfPeople();

        }

        for (WarikanGroup item : items) {
            if (item.getWeight() != max) {
                if (item.getNumOfPeople() != 0) {
                    payment = Math.round(item.getAccountingTotal() / 100 / item.getNumOfPeople() * item.getWeight()) * 100;
                    item.setAmountOfMoney((int) Math.ceil(payment));
                    member += item.getNumOfPeople();
                    ans += payment;
                } else {
                    item.setAmountOfMoney(0);
                }
            }
            if (item.getWeight() == max){
                if (item.getNumOfPeople() != 0) {
                    payment = item.getAccountingTotal() - ans * (totalMenber - member) / item.getNumOfPeople();
                    item.setAmountOfMoney((int) Math.ceil(payment));
                } else {
                    item.setAmountOfMoney(0);
                }
            }
        }

    }*/

    /*public void minTotalPaymentMoney(WarikanGroup i) {
        double payment = 0;
        double min = 10;
        int ans = 0;
        for (WarikanGroup item : items) {
            if( item.getWeight() < min ){
                min = item.getWeight();
            }
        }
        if (i.getWeight() == min) {
            i.setAmountOfMoney(0);
            double amoManey =0;
            for (WarikanGroup item : items) {
                if (item.getNumOfPeople() != 0) {
                    amoManey += item.getAmountOfMoney() * item.getNumOfPeople();
                }
            }
            if (i.getNumOfPeople() != 0) {
                payment = (i.getAccountingTotal() - amoManey) / i.getNumOfPeople();
            } else {
                payment = 0;
            }
            ans = (int)Math.ceil(payment/100)*100;
            i.setAmountOfMoney(ans);
        }
    }*/

    public void totalPaymentMoney(WarikanGroup i) {
        double totalWeight = 0;
        double payment = 0;
        for (WarikanGroup item : items) {
            totalWeight += item.getWeight() * item.getNumOfPeople();
        }

        if (totalWeight != 0) {
            if (i.getNumOfPeople() != 0) {
                payment = i.getAccountingTotal() * i.getWeight()  / totalWeight;
            } else if (i.getNumOfPeople() == 0) {
                payment = 0;
            }
        } else {
            payment = 0;
        }
        i.setAmountOfMoney((int)Math.floor(payment / 100)*100);

    }

    public double otherTotalMoney() {
        double money = 0;
        for (int i = 1; i < items.size(); i++) {
            money += items.get(i).getAmountOfMoney() * items.get(i).getNumOfPeople();
        }
        return money;
    }

    /*public double totalPaymentMoney(WarikanGroup i) {
        double totalWeight = 0;
        double payment = 0;
        for (WarikanGroup item : items) {
            totalWeight += item.getWeight() * item.getNumOfPeople();
        }

        if (totalWeight != 0) {
            if (i.getNumOfPeople() != 0) {
                //for (WarikanGroup item : items) {
                    payment = i.getWeight() * i.getAccountingTotal() / totalWeight;
                //}
            } else if (i.getNumOfPeople() == 0) {
                payment = 0;
            }
        } else {
            payment = 0;
        }
        return payment;
    }*/
}
