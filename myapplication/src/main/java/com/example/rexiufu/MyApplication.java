package com.example.rexiufu;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.rexiufu.DexUtlis;


/**
 * @ProjectName: MyApplication3
 * @Package: com.anzhi.rexiufu
 * @ClassName: MyApplication
 * @Description: java类作用描述
 * @Author: yangff
 */
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //分包
        MultiDex.install(this);
        DexUtlis.loadRepairDex(this);
    }
}
