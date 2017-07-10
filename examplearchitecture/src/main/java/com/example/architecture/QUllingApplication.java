package com.example.architecture;

import android.content.Context;

import com.ulling.lib.core.base.QbaseApplication;
import com.ulling.lib.core.util.QcPreferences;

/**
 * Created by KILHO on 2016. 7. 4..
 */

public class QUllingApplication extends QbaseApplication {
    private static QUllingApplication mInstance;
    private Context qCon;
    private String APP_NAME;
    public QcPreferences appQcPreferences;

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
        qCon =  getApplicationContext();
        APP_NAME = qCon.getResources().getString(R.string.app_name);
//        MyVolley.init(this);
        QcPreferences.init(qCon);
        appQcPreferences = QcPreferences.getInstance(qCon);
//        QcPreferences.init(qCon, APP_NAME);
//        QcToast.init(this);
    }

    public static synchronized QUllingApplication getInstance() {
        return mInstance;
    }


}