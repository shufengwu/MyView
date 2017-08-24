package com.delta.myview.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.delta.myview.R;

public class EventTestActivity extends AppCompatActivity {

    public static final String TAG = EventTestActivity.class.getSimpleName();
    private MyButton myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_test);
        myButton = (MyButton) this.findViewById(R.id.test);
        myButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //Log.i(TAG, "onTouch: ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_UP:
                        //Log.i(TAG, "onTouch: ACTION_UP");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //Log.i(TAG, "onTouch: ACTION_MOVE");
                        break;
                }
                return false;
            }
        });
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
            }
        });

        myButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(TAG, "onLongClick: ");
                return false;
            }
        });
    }
}
