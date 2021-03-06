package com.example.ctc615017.warikancalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ctc615017 on 2016/03/11.
 */
public class WarikanPreference extends Activity {
    private static final String PREF_KEY = "Array";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Button ok_btn;
    Button reset_btn;
    private String key;
    private ArrayList<WarikanGroup> list = new ArrayList();
    //ConfigAdapter
    private ConfigAdapter conf_adapter = null;
    private String[] arrayItem = null;
    private String[] arrayItem2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_baselayout);
        // SharedPrefernces の取得
        prefs = getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);

        arrayItem = getArray("StringStatusItem", prefs);
        arrayItem2 = getArray("StringWeightItem", prefs);

        Intent intent = getIntent();
        if ( !(intent.getStringExtra("acc") == null) ) {
            key = intent.getStringExtra("acc");
        }

        //conf_adapter設定
        createData();
        conf_adapter = new ConfigAdapter(this, R.layout.config_baselayout, list);
        ListView listview = (ListView)findViewById(R.id.configList);
        listview.setAdapter(conf_adapter);

        if (arrayItem != null) {
            for (int i = 0; i < arrayItem.length; i++) {
                conf_adapter.setting(arrayItem[i], Double.valueOf(arrayItem2[i]));
            }
        }

        //okBtnの取得
        ok_btn = (Button) findViewById(R.id.okBtn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arrayWeightItem = conf_adapter.setWeightArray();
                saveArray(arrayWeightItem, "StringWeightItem", prefs);
                String arrayStatusItem = conf_adapter.setStatusArray();
                saveArray(arrayStatusItem, "StringStatusItem", prefs);

                Intent intent = new Intent(WarikanPreference.this, MainActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);
                overridePendingTransition(R.anim.in_top, R.anim.out_bottom);
            }
        });

        //resetBtnの取得
        reset_btn = (Button) findViewById(R.id.resetBtn);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (WarikanGroup item : list) {
                    item.setSelected(false);
                }
                conf_adapter.notifyDataSetChanged();
            }
        });
    }

    //リストのデータ
    private void createData() {
        WarikanGroup item1 = new WarikanGroup();
        item1.setStatusName("社長");
        list.add(item1);

        WarikanGroup item2 = new WarikanGroup();
        item2.setStatusName("取締役");
        list.add(item2);

        WarikanGroup item3 = new WarikanGroup();
        item3.setStatusName("部長");
        list.add(item3);

        WarikanGroup item4 = new WarikanGroup();
        item4.setStatusName("部長代理");
        list.add(item4);

        WarikanGroup item5 = new WarikanGroup();
        item5.setStatusName("課長");
        list.add(item5);

        WarikanGroup item6 = new WarikanGroup();
        item6.setStatusName("課長代理");
        list.add(item6);

        WarikanGroup item7 = new WarikanGroup();
        item7.setStatusName("係長");
        list.add(item7);

        WarikanGroup item8 = new WarikanGroup();
        item8.setStatusName("主任");
        list.add(item8);

        WarikanGroup item9 = new WarikanGroup();
        item9.setStatusName("一般");
        list.add(item9);

        WarikanGroup item10 = new WarikanGroup();
        item10.setStatusName("若手");
        list.add(item10);
    }

    //設定をeditorに保存
    private void saveArray(String array, String PrefKey, SharedPreferences prefs) {
        String stringItem = null;
        if(!array.equals("")) {
            stringItem = array.substring(0, array.length() - 1);
            editor = prefs.edit();
            editor.putString(PrefKey, stringItem).commit();
        } else {
            editor = prefs.edit();
            editor.putString(PrefKey, "").commit();
        }
    }
    //設定の取得
    public String[] getArray(String PrefKey, SharedPreferences prefs) {
        String stringItem = prefs.getString(PrefKey,"");
        if (stringItem != null && stringItem.length() != 0) {
            return stringItem.split(",");
        } else {
            return null;
        }
    }
}
