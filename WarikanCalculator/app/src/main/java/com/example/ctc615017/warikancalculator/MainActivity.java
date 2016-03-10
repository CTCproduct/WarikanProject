package com.example.ctc615017.warikancalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
        acc_txt.setText("0");
        final TextView unit_num = (TextView)findViewById(R.id.unitNum);
        final TextView money_num = (TextView)findViewById(R.id.moneyNum);
        final TextView people_num = (TextView)findViewById(R.id.peopleNum);
        final TextView dif_num = (TextView)findViewById(R.id.differenceNumTxt);


        Intent intent = getIntent();
        if (!(intent.getStringExtra("key") == null)) {
            acc_txt.setText(intent.getStringExtra("key"));
            key = Integer.valueOf(intent.getStringExtra("key"));
            int diff = Integer.parseInt(money_num.getText().toString()) - key;
            dif_num.setText(Integer.toString(diff));
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
                money_num.setText(String.valueOf(collectionTotal));
                //差額表示
                int diff = collectionTotal - key;
                dif_num.setText(String.valueOf(diff));
                adapter.notifyDataSetChanged();
            }
        });

        //resetBtnの取得
        Button reset_btn = (Button)findViewById(R.id.resetBtn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                people_num.setText("0");
                money_num.setText("0");
                dif_num.setText("0");
                acc_txt.setText("0");
            }
        });

        //unit_minusBtnの取得
        Button uniMin_btn = (Button)findViewById(R.id.unit_minusBtn);
        uniMin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(unit_num.getText().toString().equals("1"))) {
                    unit_num.setText(unit_num.getText().toString().substring(0, unit_num.length()-1));
                }
            }
        });

        //unit_plusBtnの取得
        Button uniPlu_btn = (Button)findViewById(R.id.unit_plusBtn);
        uniPlu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(unit_num.getText().toString().equals("1000"))) {
                    unit_num.append("0");
                }
            }
        });

    }

    //リストのデータ
    private void createData() {
        this.list = new ArrayList();
        WarikanGroup item1 = new WarikanGroup();
        item1.setStatusName("偉い人");
        item1.setWeight(2);
        item1.setAccountingTotal(key);
        list.add(item1);

        WarikanGroup item2 = new WarikanGroup();
        item2.setStatusName("普通の人");
        item2.setWeight(1.5);
        item2.setAccountingTotal(key);
        list.add(item2);

        WarikanGroup item3 = new WarikanGroup();
        item3.setStatusName("若手");
        item3.setWeight(1);
        item3.setAccountingTotal(key);
        list.add(item3);

        WarikanGroup item4 = new WarikanGroup();
        item4.setStatusName("その他");
        item4.setWeight(1.2);
        item4.setAccountingTotal(key);
        list.add(item4);
    }
}
