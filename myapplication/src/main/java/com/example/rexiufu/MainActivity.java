package com.example.rexiufu;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import java.io.File;
import java.io.IOException;

import dalvik.system.PathClassLoader;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
       findViewById(R.id.trxtview).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this,Bug.class);
               startActivity(intent);

           }
       });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }
     findViewById(R.id.jiaview)
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fixBug();
            }
        });

    }
    private void fixBug() {
        //1 从服务器下载dex文件 比如v1.1修复包文件（classes2.dex）
        File sourceFile = new File(Environment.getExternalStorageDirectory(), "classes2.dex");
        // 目标路径：私有目录
        //getDir("odex", Context.MODE_PRIVATE) 比如data/user/0/包名/app_odex
        File targetFile = new File(getDir("odex",
                Context.MODE_PRIVATE),File.separator+sourceFile.getName());
        FileUtils.copyFile(sourceFile,targetFile);
    }

}
