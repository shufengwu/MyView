package com.delta.textutilstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

public class MainActivity extends AppCompatActivity {

    String str1;
    String str2 = "";
    String str3 = "hehehe";
    String str4 = " hehehe fjlkalk jkkllfksa djlkakfjs";
    String str5;
    String str6 = "hehehe";
    StringBuffer sb = new StringBuffer("hehehe");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //isEmpty
        System.out.println(TextUtils.isEmpty(str1));
        System.out.println(TextUtils.isEmpty(str2));
        System.out.println(TextUtils.isEmpty(str3));

        //getTrimmedLength 减去字符串首尾空格后的长度
        System.out.println(TextUtils.getTrimmedLength(str4));

        //equals
        //Returns true if CharSequence a and CharSequence b are equal, including if they are both null.
        System.out.println(TextUtils.equals(str1, str5));
        System.out.println(TextUtils.equals(str3, str6));
        System.out.println(TextUtils.equals(sb, str6));
        System.out.println(str6.equals(sb));

        //getReverse
        //截取字符串中一段，并逆序输出
        System.out.println(TextUtils.getReverse(str4, 0, str4.length()));

    }
}
