package com.example.ctc615017.warikancalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected int unit = 0;
    //リスト
    private ArrayList<WarikanGroup> list = null;
    //WarikanAdapter
    private WarikanAdapter adapter = null;
    private int key = 0;

    private String[] statusName = null;
    private String[] weight = null;
    private SharedPreferences prefs;
    private static final String PREF_KEY = "Array";
    private WarikanGroup item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView acc_txt = (TextView)findViewById(R.id.accountingTxt);
        acc_txt.setText(Integer.toString(WarikanAdapter.getAccountingTotal()));
        final TextView collectMoney_txt = (TextView)findViewById(R.id.collectMoney);
        collectMoney_txt.setText(Integer.toString(WarikanAdapter.getCollectTotal()));
        final TextView people_num = (TextView)findViewById(R.id.peopleNum);
        people_num.setText(Integer.toString(WarikanAdapter.getNumOfPeople()));
        final TextView diff_num = (TextView)findViewById(R.id.differenceNumTxt);
        diff_num.setText(Integer.toString(WarikanAdapter.getDiffMoney()));
        final LinearLayout background = (LinearLayout)findViewById(R.id.background);
        background.setBackgroundResource(R.drawable.cashtray);//背景の変更
        final Spinner unitSpinner = (Spinner) findViewById(R.id.unitSpinner);

        Intent intent = getIntent();
        if (!(intent.getStringExtra("key") == null)) {
            key = Integer.valueOf(intent.getStringExtra("key"));
            if(1000 <= key && key < 5000){
                background.setBackgroundResource(R.drawable.noguti);//背景の変更
            }
            else if(5000<=key && key < 10000){
                background.setBackgroundResource(R.drawable.higuti);//背景の変更
            }
            else if(10000 <= key){
                background.setBackgroundResource(R.drawable.yukiti);//背景の変更
            }
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
                overridePendingTransition(R.anim.start, R.anim.end);
            }
        });

        //重み設定の取得
        prefs = getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);
        WarikanPreference warikanPref = new WarikanPreference();
        statusName = warikanPref.getArray("StringStatusItem", prefs);
        weight = warikanPref.getArray("StringWeightItem", prefs);

        this.list = new ArrayList();
        if (statusName != null) {
            //statusNameがnullでなければリストを追加
            for (int i = 0; i < statusName.length; i++) {
                item = new WarikanGroup();
                item.setStatusName(statusName[i]);
                item.setWeight(Double.valueOf(weight[i]));
                item.setSelected(true);
                list.add(item);
            }
        }
        //リストにアダプターを設定
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
                key = 0;
                WarikanAdapter.setAccountingTotal(0);
                acc_txt.setText(Integer.toString(WarikanAdapter.getAccountingTotal()));
                WarikanAdapter.setCollectTotal(0);
                WarikanAdapter.delNumOfPeople();
                adapter.totalPaymentMoney();
                adapter.calcPaymentMoney();
                adapter.summaryCount();
                adapter.notifyDataSetChanged();
            }
        });

        // 単位設定用Spinner
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,
                getResources().getStringArray(R.array.spinner_list));
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                unit = Integer.parseInt(spinner.getSelectedItem().toString());
                adapter.setUnit(unit);
                adapter.totalPaymentMoney();
                adapter.calcPaymentMoney();
                adapter.summaryCount();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //configBtnの取得
        Button config_btn = (Button)findViewById(R.id.configBtn);
        config_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WarikanPreference.class);
                intent.putExtra("acc", String.valueOf(key));
                startActivity(intent);
                overridePendingTransition(R.anim.in_bottom, R.anim.out_top);
            }
        });
    }
}
