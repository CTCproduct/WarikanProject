package com.example.ctc615017.warikancalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ctc615017 on 2016/02/25.
 */
public class Calculator extends AppCompatActivity {
    private int result = 0;
    private boolean isOperatorKeyPushed;
    private int recentOperator = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        final TextView acc_txt = (TextView)findViewById(R.id.accountingTxt);
        recentOperator = R.id.equalBtn;

        View.OnClickListener buttonOperatorListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button operatorButton = (Button) view;
                int value = Integer.parseInt(acc_txt.getText().toString());
                if (recentOperator == R.id.equalBtn) {
                    result = value;
                } else {
                    result = calc(recentOperator, result, value);
                    acc_txt.setText(String.valueOf(result));
                }

                recentOperator = operatorButton.getId();
                isOperatorKeyPushed = true;
            }
        };

        //backBtnの取得
        Button back_btn = (Button)findViewById(R.id.backBtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(acc_txt.getText().toString(). equals("0"))) {
                    acc_txt.setText(acc_txt.getText().toString().substring(0, acc_txt.length()-1));

                    if (acc_txt.length() ==0 ) {
                        acc_txt.setText("0");
                    }
                }
            }
        });

        //clearBtnの取得
        Button clear_btn = (Button)findViewById(R.id.clearBtn);
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = 0;
                if (!(acc_txt.getText().toString(). equals("0"))) {
                    acc_txt.setText("0");
                }
            }
        });
        //plusBtnの取得
        Button plus_btn = (Button)findViewById(R.id.plusBtn);
        plus_btn.setOnClickListener(buttonOperatorListener);

        //minusBtnの取得
        Button minus_btn = (Button)findViewById(R.id.minusBtn);
        minus_btn.setOnClickListener(buttonOperatorListener);

        //equalBtnの取得
        Button equalBtn = (Button)findViewById(R.id.equalBtn);
        equalBtn.setOnClickListener(buttonOperatorListener);

        //oneBtnの取得
        Button one_btn = (Button)findViewById(R.id.oneBtn);
        one_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("1");
                } else {
                    acc_txt.append("1");
                }
                isOperatorKeyPushed = false;
            }
        });
        //twoBtnの取得
        Button two_btn = (Button)findViewById(R.id.twoBtn);
        two_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("2");
                } else {
                    acc_txt.append("2");
                }
                isOperatorKeyPushed = false;
            }
        });
        //threeBtnの取得
        Button three_btn = (Button)findViewById(R.id.threeBtn);
        three_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("3");
                } else {
                    acc_txt.append("3");
                }
                isOperatorKeyPushed = false;
            }
        });
        //fourBtnの取得
        Button four_btn = (Button)findViewById(R.id.fourBtn);
        four_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("4");
                } else {
                    acc_txt.append("4");
                }
                isOperatorKeyPushed = false;
            }
        });
        //fiveBtnの取得
        Button five_btn = (Button)findViewById(R.id.fiveBtn);
        five_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("5");
                } else {
                    acc_txt.append("5");
                }
                isOperatorKeyPushed = false;
            }
        });

        //sixBtnの取得
        Button six_btn = (Button)findViewById(R.id.sixBtn);
        six_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("6");
                } else {
                    acc_txt.append("6");
                }
                isOperatorKeyPushed = false;
            }
        });
        //sevenBtnの取得
        Button seven_btn = (Button)findViewById(R.id.sevenBtn);
        seven_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("7");
                } else {
                    acc_txt.append("7");
                }
                isOperatorKeyPushed = false;
            }
        });
        //eightBtnの取得
        Button eight_btn = (Button)findViewById(R.id.eightBtn);
        eight_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("8");
                } else {
                    acc_txt.append("8");
                }
                isOperatorKeyPushed = false;
            }
        });
        //nineBtnの取得
        Button nine_btn = (Button)findViewById(R.id.nineBtn);
        nine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acc_txt.getText().toString(). equals("0") || isOperatorKeyPushed == true) {
                    acc_txt.setText("9");
                } else {
                    acc_txt.append("9");
                }
                isOperatorKeyPushed = false;
            }
        });
        //zeroBtnの取得
        Button zero_btn = (Button)findViewById(R.id.zeroBtn);
        zero_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOperatorKeyPushed == true) {
                    acc_txt.setText("0");
                }
                if (!(acc_txt.getText().toString(). equals("0"))) {
                    acc_txt.append("0");
                }
                isOperatorKeyPushed = false;
            }
        });
        //zero2Btnの取得
        Button zero2_btn = (Button)findViewById(R.id.zero2Btn);
        zero2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOperatorKeyPushed == true) {
                    acc_txt.setText("0");
                }
                if (!(acc_txt.getText().toString(). equals("0"))) {
                    acc_txt.append("00");
                }
                isOperatorKeyPushed = false;
            }
        });

        //okBtnの取得
        Button ok_btn = (Button)findViewById(R.id.okBtn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String someData = acc_txt.getText().toString();
                Intent intent = new Intent(Calculator.this, MainActivity.class);
                intent.putExtra("key", someData);
                startActivity(intent);
                finish();

            }
        });

    }

    public int calc(int operator, int value1, int value2) {
        switch (operator) {
            case R.id.plusBtn:
                return value1 + value2;
            case R.id.minusBtn:
                return value1 - value2;
            default:
                return value1;
        }
    }
}
