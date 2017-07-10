package com.ulling.lib.core.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.ulling.lib.core.R;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;

/**
 * Created by P100651 on 2017-07-10.
 */
public class QbaseApplication extends Application {
    //    public String TAG = getClass().getSimpleName();
    private static QbaseApplication qAppInstance;
    private Context qCon;
    public String APP_NAME;
    public QcPreferences qcPreferences;

    /**
     * 애플리케이션이 생성될 때 호출된다. 모든 상태변수와 리소스 초기화한다
     */
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
     * @MethodName : init
     * @Date : 2015. 3. 15.
     * @author : KILHO
     * @Method ㄴ 초기화
     * @변경이력
     */
    private void init() {
        QcLog.i("QbaseApplication init !");
        qAppInstance = this;
        qCon = getApplicationContext();
        APP_NAME = qCon.getResources().getString(R.string.app_name);
        QcPreferences.init(qCon);
        qcPreferences = QcPreferences.getInstance(qCon);
//        QcToast.init(this);
    }

    public static synchronized QbaseApplication getInstance() {
        return qAppInstance;
    }

    /**
     * 애플리케이션 객체가 종료될 때 호출되는데 항상 보증하지 않는다.
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        QcLog.i("QbaseApplication onTerminate !");
    }

    /**
     * 애플리케이션은 구성변경을 위해 재시작하지 않는다.
     * 변경이 필요하다면 이곳에서 핸들러를 재정의 하면 된다.
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        QcLog.i("QbaseApplication onConfigurationChanged !");
    }

    /**
     * 시스템이 리소스가 부족할 때 발생한다.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        QcLog.e("QbaseApplication onLowMemory !");
    }
}