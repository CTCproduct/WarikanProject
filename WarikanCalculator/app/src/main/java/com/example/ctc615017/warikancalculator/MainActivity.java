package com.example.ctc615017.warikancalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //リスト
    private ArrayList<WarikanGroup> list = null;
    //WarikanAdapter
    private WarikanAdapter adapter = null;

    private int key = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView acc_txt = (TextView)findViewById(R.id.accountingTxt);
        acc_txt.setText(Integer.toString(WarikanAdapter.getAccountingTotal()));
        final TextView unit_num = (TextView)findViewById(R.id.unitNum);
        unit_num.setText(Integer.toString(WarikanAdapter.getUnit()));
        final TextView collectMoney_txt = (TextView)findViewById(R.id.collectMoney);
        collectMoney_txt.setText(Integer.toString(WarikanAdapter.getCollectTotal()));
        final TextView people_num = (TextView)findViewById(R.id.peopleNum);
        people_num.setText(Integer.toString(WarikanAdapter.getNumOfPeople()));
        final TextView diff_num = (TextView)findViewById(R.id.differenceNumTxt);
        diff_num.setText(Integer.toString(WarikanAdapter.getDiffMoney()));


        Intent intent = getIntent();
        if (!(intent.getStringExtra("key") == null)) {
            key = Integer.valueOf(intent.getStringExtra("key"));
            WarikanAdapter.setAccountingTotal(key);
            acc_txt.setText(Integer.toString(WarikanAdapter.getAccountingTotal()));
            int diff = Integer.parseInt(collectMoney_txt.getText().toString()) - key;
            diff_num.setText(Integer.toString(diff));
        }

        //accountingBtnの取得
        Button account_btn = (Button)findViewById(R.id.accountingBtn);
        account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Calculator.class);
                startActivity(intent);
            }
        });

        //リストの取得
        createData();
        adapter = new WarikanAdapter(this, R.layout.activity_main, list);
        ListView listview = (ListView)findViewById(R.id.WarikanList);
        listview.setAdapter(adapter);
        adapter.setOnChangeSummaryListener(new WarikanAdapter.ChangeSummaryListener() {
            @Override
            public void onChengeSummary(int summary, int collectionTotal) {
                //人数表示
                people_num.setText(String.valueOf(summary));
                //集金総額表示
                collectMoney_txt.setText(String.valueOf(collectionTotal));
                //差額表示
                int diff = collectionTotal - key;
                diff_num.setText(String.valueOf(diff));
                adapter.notifyDataSetChanged();
            }
        });

        //resetBtnの取得
        Button reset_btn = (Button)findViewById(R.id.resetBtn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //WarikanAdapter.setDiffMoney(0);
                key = 0;
                WarikanAdapter.setAccountingTotal(0);
                acc_txt.setText(Integer.toString(WarikanAdapter.getAccountingTotal()));
                WarikanAdapter.setCollectTotal(0);
                //WarikanAdapter.setNumOfPeople(0);
                adapter.totalPaymentMoney();
                adapter.calcPaymentMoney();
                adapter.summaryCount();
                adapter.notifyDataSetChanged();
            }
        });

        //unit_minusBtnの取得
        Button uniMin_btn = (Button)findViewById(R.id.unit_minusBtn);
        uniMin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WarikanAdapter.getUnit() != 1) {
                    WarikanAdapter.setUnit(WarikanAdapter.getUnit() / 10);
                    unit_num.setText(Integer.toString(WarikanAdapter.getUnit()));
                    adapter.totalPaymentMoney();
                    adapter.calcPaymentMoney();
                    adapter.summaryCount();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //unit_plusBtnの取得
        Button uniPlu_btn = (Button)findViewById(R.id.unit_plusBtn);
        uniPlu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (WarikanAdapter.getUnit() != 1000) {
                    WarikanAdapter.setUnit(WarikanAdapter.getUnit() * 10);
                    unit_num.setText(Integer.toString(WarikanAdapter.getUnit()));
                    adapter.totalPaymentMoney();
                    adapter.calcPaymentMoney();
                    adapter.summaryCount();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //configBtnの取得
        Button config_btn = (Button)findViewById(R.id.configBtn);
        config_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WarikanPreference.class);
                startActivity(intent);
            }
        });

    }

    //リストのデータ
    private void createData() {
        this.list = new ArrayList();
        WarikanGroup item1 = new WarikanGroup();
        item1.setStatusName("社長");
        item1.setWeight(2);
        list.add(item1);

        WarikanGroup item2 = new WarikanGroup();
        item2.setStatusName("取締役");
        item2.setWeight(1.9);
        list.add(item2);

        WarikanGroup item3 = new WarikanGroup();
        item3.setStatusName("部長");
        item3.setWeight(1.8);
        list.add(item3);

        WarikanGroup item4 = new WarikanGroup();
        item4.setStatusName("部長代理");
        item4.setWeight(1.7);
        list.add(item4);

        WarikanGroup item5 = new WarikanGroup();
        item5.setStatusName("課長");
        item5.setWeight(1.6);
        list.add(item5);

        WarikanGroup item6 = new WarikanGroup();
        item6.setStatusName("課長代理");
        item6.setWeight(1.5);
        list.add(item6);

        WarikanGroup item7 = new WarikanGroup();
        item7.setStatusName("係長");
        item7.setWeight(1.4);
        list.add(item7);

        WarikanGroup item8 = new WarikanGroup();
        item8.setStatusName("主任");
        item8.setWeight(1.4);
        list.add(item8);

        WarikanGroup item9 = new WarikanGroup();
        item9.setStatusName("一般");
        item9.setWeight(1.2);
        list.add(item9);

        WarikanGroup item10 = new WarikanGroup();
        item10.setStatusName("若手");
        item10.setWeight(1);
        list.add(item10);
    }
}
