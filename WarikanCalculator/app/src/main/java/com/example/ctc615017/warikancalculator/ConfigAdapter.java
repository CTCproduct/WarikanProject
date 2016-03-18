package com.example.ctc615017.warikancalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ctc615017 on 2016/02/29.
 */
public class ConfigAdapter extends ArrayAdapter {
    private ArrayList<WarikanGroup> items;
    private LayoutInflater inflater;
    private TextView weight_txt;
    private Button weightPlus_btn;
    private Button weightMinus_btn;
    private CheckBox president_chkbox;

    public ConfigAdapter(Context context, int textViewResourceId, ArrayList items) {
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
            view = inflater.inflate(R.layout.configadapter_layout, null);
        }

        if (item != null) {

            weight_txt = (TextView) view.findViewById(R.id.weight);
            weight_txt.setText(String.valueOf(item.getWeight()));
            //weightPlusBtnのセット
            weightPlus_btn = (Button) view.findViewById(R.id.weightPlusBtn);
            weightPlus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    double num = item.addWeight(1);
                    item.setWeight(Math.floor(num)/10);
                    notifyDataSetChanged();
                }
            });

            //weightMinusBtnのセット
            weightMinus_btn = (Button) view.findViewById(R.id.weightMinusBtn);
            weightMinus_btn.setEnabled(item.getSelected());
            weightMinus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    double num = item.getWeight();
                    if (num > 1) {
                        num = item.subWeight(1);
                        item.setWeight(Math.floor(num)/10);
                        notifyDataSetChanged();
                    }
                }
            });


            //presidentのセット
            president_chkbox = (CheckBox) view.findViewById(R.id.checkBox);
            president_chkbox.setText(item.getStatusName());
            if (president_chkbox != null) {
                president_chkbox.setText(item.getStatusName());
            }

            president_chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    item.setSelected(isChecked);
                    if (isChecked == false) {
                        item.setSelected(false);
                    } else {
                        item.setSelected(true);
                    }
                    notifyDataSetChanged();
                }

            });
            weight_txt.setEnabled(item.getSelected());
            president_chkbox.setChecked(item.getSelected());
            weightPlus_btn.setEnabled(item.getSelected());
        }
        return view;
    }

    public interface ConfChangeSummaryListener {
        void onConfChengeSummary(int summary);
    }
    private ConfChangeSummaryListener mConfChangeSummaryListener = null;
    public void setOnConfChangeSummaryListener(ConfChangeSummaryListener listener) {
        mConfChangeSummaryListener = listener;
    }

    protected void summaryCount() {
        int summary = 0;
        if (mConfChangeSummaryListener != null) {
            for (WarikanGroup item : items){
                summary += item.getNumOfPeople();
            }
            mConfChangeSummaryListener.onConfChengeSummary(summary);
        }
    }

    public String setWeightArray() {
        StringBuffer buff = new StringBuffer();
        for (WarikanGroup item : items) {
            if (item.getSelected() == true) {
                buff.append(item.getWeight() + ",");
            }
        }

        String arrayItem = buff.toString();
        return arrayItem;
    }

    public String setStatusArray() {
        StringBuffer buff2 = new StringBuffer();
        for (WarikanGroup item : items) {
            if (item.getSelected() == true) {
                buff2.append(item.getStatusName() + ",");
            }
        }

        String arrayItem = buff2.toString();
        return arrayItem;
    }

    public void setting(String name, double weight) {
        for (WarikanGroup item : items) {
            if (item.getStatusName().equals(name)) {
                item.setWeight(weight);
                item.setSelected(true);
                break;
            }
        }
    }

}
