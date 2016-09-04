package com.example.santa.timeselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        textView = (TextView) findViewById(R.id.textview);

//
//        TimeSelectorView timeSelectorView = (TimeSelectorView) findViewById(R.id.timeview);
//        timeSelectorView.setListener(new TimeSelectorView.TimeChangeListener() {
//            @Override
//            public void scrollFinish(String time) {
//                textView.setText(time);
//            }
//        });


//        JustListView listView = (JustListView) findViewById(R.id.listview);
//        listView.setAdapter(new SelectorAdapter(this, SelectorContanst.getMonths()));
//        listView.setSelection(Integer.MAX_VALUE/2+1);

//        SelectView selectView = (SelectView) findViewById(R.id.select_view);
//        selectView.setData(null);
    }
}
