package com.example.ctc615017.warikancalculator;

import android.content.Context;
import android.util.Log;
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
    private WarikanGroup underItem;
    private WarikanGroup upperItem;
    private double underWeight = 1.0;
    private double upperWeight = 1.0;

    public ConfigAdapter(Context context, int textViewResourceId, ArrayList items) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int itemPosition = position;
        // データの取得
        final WarikanGroup item = (WarikanGroup)items.get(position);
        Log.d("ConfigAdapter","position= "+position+", item= "+item.getStatusName()+item.getWeight());
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
                    //itemPositionより下にcheckboxがtrueの項目がいくつあるかカウント
                    int count = 0;
                    for (int i = itemPosition; i < 9; i++) {
                        underItem = (WarikanGroup) items.get(i + 1);
                        if (underItem.getSelected() == true) {
                            count += 1;
                        }
                    }
                    //itemPositionの上にあるcheckboxがtrueの項目のWeightを取得
                    double num = item.getWeight();
                    for (int i = itemPosition; i > 0; i--) {
                        upperItem = (WarikanGroup) items.get(i - 1);
                        if (upperItem.getSelected() == true) {
                            upperWeight = upperItem.getWeight();
                            break;
                        }
                    }
                    //itemPositionのWeightを加算できるか判定、上の項目のWeightより重くならないよう加算
                    if (0 < count && num < upperWeight || 0 < count && itemPosition == 0) {
                        double sub_num = item.addWeight(1);
                        item.setWeight(Math.floor(sub_num) / 10);
                    }
                    notifyDataSetChanged();
                }
            });

            //weightMinusBtnのセット
            weightMinus_btn = (Button) view.findViewById(R.id.weightMinusBtn);
            weightMinus_btn.setEnabled(item.getSelected());
            weightMinus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //itemPositionより下にある項目のWeightを取得
                    double num = item.getWeight();
                    for (int i = itemPosition; i < 9; i++) {
                        underItem = (WarikanGroup) items.get(i + 1);
                        if (underItem.getSelected() == true) {
                            underWeight = underItem.getWeight();
                            break;
                        }
                    }
                    //下の項目のWeightより小さくならないよう減算
                    if (num > underWeight) {
                        num = item.subWeight(1);
                        item.setWeight(Math.floor(num) / 10);
                        notifyDataSetChanged();
                    }
                }
            });


            //president_chkboxのセット
            president_chkbox = (CheckBox) view.findViewById(R.id.checkBox);
            president_chkbox.setText(item.getStatusName());
            if (president_chkbox != null) {
                president_chkbox.setText(item.getStatusName());
            }

            //チェック状態が変更された時
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
            //チェックボックスがクリックされた時
            president_chkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getSelected() == true) {
                        for (int i = itemPosition; i < 9; i++) {
                            underItem = (WarikanGroup) items.get(i + 1);
                            if (underItem.getSelected() == true) {
                                item.setWeight(underItem.getWeight());
                                break;
                            }
                        }
                    } else if (item.getSelected() == false) {
                        for (int i = itemPosition; i > 0; i--) {
                            upperItem = (WarikanGroup) items.get(i - 1);
                            if (upperItem.getSelected() == true) {
                                item.setWeight(1.0);
                                break;
                            }
                        }
                    }
                }
            });
            weight_txt.setEnabled(item.getSelected());
            president_chkbox.setChecked(item.getSelected());
            weightPlus_btn.setEnabled(item.getSelected());
            for (int i = 9; i >= 0; i--) {
                underItem = (WarikanGroup) items.get(i);
                if (underItem.getSelected() == true) {
                    underItem.setWeight(1.0);
                    break;
                }
            }
        }
        return view;
    }

    /*public interface ConfChangeSummaryListener {
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
    }*/

    //checkboxがtrueである各項目のWeightをBufferに溜める
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

    //checkboxがtrueである各項目のStatusNameをBufferに溜める
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

    //同じStatusNameの項目にWeightとcheckboxの状態(true,false)をセット
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
