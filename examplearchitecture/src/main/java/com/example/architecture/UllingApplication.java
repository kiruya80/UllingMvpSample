package com.example.architecture;

import android.app.Application;
import android.content.Context;

import com.ulling.lib.core.util.QcPreferences;

/**
 * Created by KILHO on 2016. 7. 4..
 */

public class UllingApplication extends Application {
    public String TAG = getClass().getSimpleName();

    private static UllingApplication mInstance;
    private Context qCtx;
    private String APP_NAME;

    @Override
    public void onCreate() {
        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
        }
        super.onCreate();

        init();
    }

    /**
     *
     * @MethodName   : init
     * @Date         : 2015. 3. 15.
     * @author       : KILHO
     *
     * @Method
     *  ㄴ 초기화
     *
     * @변경이력
     */
    private void init() {
        mInstance = this;
        qCtx =  getApplicationContext();
        APP_NAME = qCtx.getResources().getString(R.string.app_name);
//        MyVolley.init(this);
        QcPreferences.init(qCtx, APP_NAME);
//        QcToast.init(this);
    }

    public static synchronized UllingApplication getInstance() {
        return mInstance;
    }


}