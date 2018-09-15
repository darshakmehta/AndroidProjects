/*
Assignment 2
Gaurav Pareek
Darshak Mehta
*/
package com.darshak.areacalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void triangle(View v) {
        TextView shapeName = (TextView)findViewById(R.id.shape);
        shapeName.setText("Triangle");
        TextView t2 = (TextView)findViewById(R.id.length2);
        t2.setVisibility(View.VISIBLE);
        EditText length2 = (EditText)findViewById(R.id.l2);
        length2.setVisibility(View.VISIBLE);
    }

    public void square(View V) {
        EditText length2 = (EditText)findViewById(R.id.l2);
        length2.setVisibility(View.GONE);
        TextView shapeName = (TextView)findViewById(R.id.shape);
        shapeName.setText("Square");
        TextView t2 = (TextView)findViewById(R.id.length2);
        t2.setVisibility(View.GONE);
    }

    public void circle(View V) {
        TextView shapeName = (TextView)findViewById(R.id.shape);
        shapeName.setText("Circle");
        EditText length2 = (EditText)findViewById(R.id.l2);
        length2.setVisibility(View.GONE);
        TextView t2 = (TextView)findViewById(R.id.length2);
        t2.setVisibility(View.GONE);
    }

    public void calculate(View V) {
        TextView shapeName = (TextView)findViewById(R.id.shape);
        String s = (String) shapeName.getText();
        if(s == "Triangle") {
            EditText l1 = (EditText) findViewById(R.id.l1);
            String s1 =  l1.getText().toString();
            int first = Integer.parseInt(s1);
            EditText l2 = (EditText) findViewById(R.id.l2);
            String s2 = l2.getText().toString();
            int second = Integer.parseInt(s2);
            double answer = 0.5 * first * second;
            TextView ans = (TextView) findViewById(R.id.answer);
            String ans_s = String.valueOf(answer);
            ans.setText(ans_s);
        }
        else if(s == "Square") {
            EditText l1 = (EditText) findViewById(R.id.l1);
            String s1 =  l1.getText().toString();
            int first = Integer.parseInt(s1);
            double answer = first * first;
            TextView ans = (TextView) findViewById(R.id.answer);
            String ans_s = String.valueOf(answer);
            ans.setText(ans_s);
        }
        else if(s == "Circle") {
            EditText l1 = (EditText) findViewById(R.id.l1);
            String s1 =  l1.getText().toString();
            int first = Integer.parseInt(s1);
            double answer = 3.1416 * first * first;
            TextView ans = (TextView) findViewById(R.id.answer);
            String ans_s = String.valueOf(answer);
            ans.setText(ans_s);
        }
    }

    public void clear(View v){
        EditText e1 = (EditText) findViewById(R.id.l1);
        EditText e2 = (EditText) findViewById(R.id.l2);
        TextView e3 = (TextView) findViewById(R.id.answer);
        TextView e4 = (TextView) findViewById(R.id.shape);
        e1.setText("");
        e2.setText("");
        e3.setText("");
        e4.setText("Select a shape");
        TextView t2 = (TextView)findViewById(R.id.length2);
        t2.setVisibility(View.VISIBLE);
        EditText length2 = (EditText)findViewById(R.id.l2);
        length2.setVisibility(View.VISIBLE);
    }
}
