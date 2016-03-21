package com.material.travel.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * Created by caoyamin on 15/10/21.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance = null;
    //private SparseArray<T> sArrays=new SparseArray<T>();
    private static final String APATCH_PATH = "/out.apatch";
    /**
     * patch manager
     */
    private PatchManager mPatchManager;
    public static MyApplication getInstance() {
        return mInstance;
    }
    public static String storagePath=null;
    /*public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }*/

    //private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        //refWatcher= LeakCanary.install(this);
        mInstance=this;
        storagePath= Environment.getExternalStorageDirectory().getAbsolutePath();
        //intHotPatch();
    }
    void intHotPatch(){
        // initialize
        mPatchManager = new PatchManager(this);
        mPatchManager.init("1.0");
        // load patch
        mPatchManager.loadPatch();
        Log.e("YM","patch loaded");
        // add patch at runtime
        try {
            // .apatch file path
            String patchFileString = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + APATCH_PATH;
            mPatchManager.addPatch(patchFileString);
            Log.d("YM", "apatch:" + patchFileString + " added.");
        } catch (IOException e) {
            Log.d("YM", "apatch:" + e.getMessage());
            mPatchManager.removeAllPatch();
        }

    }
    public static String  getStoragePath(){
        return storagePath;
    }
   /* private void saveObject(Object obj){
        sArrays.append(1, (T) obj);
    }*/

}
