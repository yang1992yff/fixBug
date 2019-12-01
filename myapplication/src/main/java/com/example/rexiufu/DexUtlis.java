package com.example.rexiufu;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * @ProjectName: MyApplication3
 * @Package: com.anzhi.rexiufu
 * @ClassName: DexUtlis
 * @Description: java类作用描述
 * @Author: yangff
 */
public class DexUtlis {
    //存放需要修复的dex集合
    private static HashSet<File> loadedDex = new HashSet<>();
    static {
        //修复前先清空
        loadedDex.clear();
    }

    public static void loadRepairDex(Context context){
        //dex文件目录
        File fileDir = context.getDir("odex", Context.MODE_PRIVATE);
        File[] files = fileDir.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".dex") && !"classes.dex".equals(file.getName())) {
                //找到要修复的dex文件
                loadedDex.add(file);
            }
        }
        createDexClassLoader(context,fileDir);
    }
    /**
     * 创建类加载器
     *
     * @param context
     * @param fileDir
     * DexClassLoader的构造方法有四个参数：
     * •dexPath：dex相关文件路径集合，多个路径用文件分隔符分隔，默认文件分隔符为‘：’
     * •optimizedDirectory：解压的dex文件存储路径，这个路径必须是一个内部存储路径。
     * •librarySearchPath：包含 C/C++ 库的路径集合，多个路径用文件分隔符分隔分割，可以为null。
     * •parent：父加载器
     */
    private static void createDexClassLoader(Context context, File fileDir) {
        DexClassLoader myDexClassLoader;
        String optimizedDirectory = fileDir.getAbsolutePath() + File.separator + "opt_dex";
        File fOpt = new File(optimizedDirectory);
        if (!fOpt.exists()) {
            fOpt.mkdirs();
        }
        for (File file:loadedDex){
            myDexClassLoader=new DexClassLoader(file.getAbsolutePath(),optimizedDirectory,null,context.getClassLoader());
            hotfix(context,myDexClassLoader);
        }
    }
    public static void hotfix(Context context,ClassLoader repairClassLoader){
        PathClassLoader classLoad=(PathClassLoader)context.getClassLoader();
        try {
            Object puthlist=getPuthList(classLoad);
           Object value= mergedArray(getDexElements(puthlist),getDexElements(getPuthList(repairClassLoader)));
          setDexElements(puthlist,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并数组主要是把插入的数组放已有数组之前
     *
     * @param arrayJoin 前数组（插队数组）
     * @param arraySource 后数组（已有数组）
     * @return 处理后的新数组
     */
    public static Object mergedArray(Object arraySource , Object arrayJoin ){
        // 获得一个数组的Class对象，通过Array.newInstance()可以反射生成数组对象
       Class arrayClass=arraySource.getClass().getComponentType();
       int sourceLength=Array.getLength(arraySource);
       int joinLength=Array.getLength(arrayJoin);
       int all=sourceLength+joinLength;
       Object arrayAll=Array.newInstance(arrayClass,all);
       for (int i=0;i<all;i++){
           if(i<joinLength){
             Array.set(arrayAll,i, Array.get(arrayJoin,i));
           }else{
               Array.set(arrayAll,i, Array.get(arraySource,i-joinLength));
           }
       }
        return arrayAll;
    }
    /**
     * 通过反射获取PathList对象中的dexElements对象
     *
     * @param object PathList对象
     * @return Elements对象
     */
    public static Object getDexElements(Object object) throws Exception {
        Field dexElementsField=object.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        return  dexElementsField.get(object);
    }
    /**
     * 通过反射获取PathList对象中的dexElements对象
     *
     * @param object PathList对象
     * @param value 合并后Elements值
     * @return Elements对象
     */
    public static void setDexElements(Object object,Object value) throws Exception {
        Field dexElementsField=object.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        dexElementsField.set(object,value);
    }


    /**
     * 通过反射获取BaseDexClassLoader对象中的PathList对象
     *
     * @param baseDexClassLoader BaseDexClassLoader对象
     * @return PathList对象
     */
    public static Object getPuthList(ClassLoader baseDexClassLoader) throws Exception {
        Field puth=Class.forName("dalvik.system.BaseDexClassLoader").getDeclaredField("pathList");
        puth.setAccessible(true);
       return  puth.get(baseDexClassLoader);
    }
}
