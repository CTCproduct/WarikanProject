package com.example.ctc615017.warikancalculator;

import android.app.Activity;
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
    private static final String PREF_KEY = "preference";
    private static final String KEY_TEXT = "text";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button ok_btn;
    private ArrayList<WarikanGroup> list = new ArrayList();
    //ConfigAdapter
    private ConfigAdapter conf_adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_baselayout);
        // SharedPrefernces の取得
        pref = getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);


        createData();
        conf_adapter = new ConfigAdapter(this, R.layout.config_baselayout, list);
        ListView listview = (ListView)findViewById(R.id.configList);
        listview.setAdapter(conf_adapter);
        conf_adapter.setOnConfChangeSummaryListener(new ConfigAdapter.ConfChangeSummaryListener() {
            @Override
            public void onConfChengeSummary(int summary) {

            }
        });

        ok_btn = (Button) findViewById(R.id.okBtn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*editor = pref.edit();
                for ( int i = 0; i < list.size(); i++){
                    if (list.get(i).getSelected() == false) {
                        editor.putString(
                                KEY_TEXT,
                                list.getText.toString()
                        );
                    }
                    list.get(i).getWeight();
                }

                editor.commit();*/
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

}
