package com.example.uncc.hw1;

/*
Homework assignment 01
File Name: MainActivity.java
Gaurav Pareek, Darshak Mehta
*/

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private String first;
    private String second;
    private String operand;
    private int flag = 0;
    private int flag_stage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void clearAllResults(View view) {
        TextView resultTextView = (TextView) findViewById(R.id.resultText);
        resultTextView.setText("0");
        first = null;
        second = null;
        flag_stage = 1;
        flag = 0;
    }

    public void getInput(View view) {
        StringBuffer inputText = new StringBuffer("");
        int inputID = view.getId();
        TextView resultTextView = (TextView) findViewById(R.id.resultText);
        String text = resultTextView.getText().toString();

        if (flag == 1) {
            inputText.delete(0, inputText.length());
            resultTextView.setText("");
            text = "";
            flag = 0;
        }
        if ( (text.contains(".") && text.length() != 15) || (!text.contains(".") && text.length() != 14) ) {
            if (text.equals("0") && text.length() == 1) {
                inputText.append("");
            } else {
                inputText.append(text);
            }
            switch(inputID) {
                case R.id.one :
                    inputText.append("1");
                    break;
                case R.id.two :
                    inputText.append("2");
                    break;
                case R.id.three :
                    inputText.append("3");
                    break;
                case R.id.four :
                    inputText.append("4");
                    break;
                case R.id.five :
                    inputText.append("5");
                    break;
                case R.id.six :
                    inputText.append("6");
                    break;
                case R.id.seven :
                    inputText.append("7");
                    break;
                case R.id.eight :
                    inputText.append("8");
                    break;
                case R.id.nine :
                    inputText.append("9");
                    break;
                case R.id.zero :
                    inputText.append("0");
                    break;
                case R.id.dot :
                    if (!inputText.toString().contains(".")) {
                        if ((text.length() == 1 && flag_stage == 1) || (text.length() == 0 && flag_stage == 2))
                            inputText.append("0.");
                        else
                            inputText.append(".");
                    }
                    break;
            }
            resultTextView.setText(inputText);
            if (flag_stage == 1)
                first = inputText + "";
            if (flag_stage == 2)
                second = inputText + "";
        }
    }

    public void getOperation(View view)
    {
        StringBuffer inputText = new StringBuffer("");
        TextView resultTextView = (TextView) findViewById(R.id.resultText);
        flag = 1;
        flag_stage = 2;
        int inputID = view.getId();
        switch(inputID) {
            case R.id.addition:
                operand = "+";
                break;
            case R.id.subtraction:
                operand = "-";
                break;
            case R.id.multiplication:
                operand = "*";
                break;
            case R.id.division:
                operand = "/";
                break;
        }
    }

    public void calculate(View view){
        BigDecimal d1,d2;
        TextView t = (TextView) findViewById(R.id.resultText);
        String text = t.getText().toString();
        if(first != null)
            d1 = new BigDecimal(first.toString());
        else
            d1 = new BigDecimal("0.0");
        if(second != null)
            d2 = new BigDecimal(second.toString());
        else
            d2 = new BigDecimal("0.0");;
        BigDecimal answer = new BigDecimal("0.0");

        switch(operand) {
            case "+":
                answer = d1.add(d2);
                break;
            case "-":
                answer = d1.subtract(d2);
                break;
            case "*":
                answer = d1.multiply(d2);
                break;
            case "/":
                try {
                    answer = d1.divide(d2,14,RoundingMode.HALF_UP);
                }
                catch (ArithmeticException e)
                {
                    Log.d("Exception Occurred",e.getMessage());
                    t.setText(e.getMessage());
                    return;
                }
                break;
        }

        String finalResult = answer.toPlainString();
        if (finalResult.length() > 15 && finalResult.contains(".")) {
            finalResult = finalResult.substring(0, 15);
        } else if (finalResult.length() > 14) {
            finalResult = finalResult.substring(0, 14);
        }

        finalResult = finalResult.indexOf(".") < 0 ? finalResult : finalResult.replaceAll("0*$", "").replaceAll("\\.$", "");
        t.setText(finalResult);
        first = finalResult;
    }
}