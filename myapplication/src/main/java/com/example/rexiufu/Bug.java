package com.example.rexiufu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * @ProjectName: MyApplication3
 * @Package: com.anzhi.rexiufu
 * @ClassName: Bubb
 * @Description: java类作用描述
 * @Author: yangff
 */
public class Bug extends Activity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView=new TextView(this);
        textView.setText("点击");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bug();
            }
        });
        setContentView(textView);
    }
    public void Bug(){
        int i=1;
        int y=1;
        textView.setText("hello"+y/i);
    }

}
