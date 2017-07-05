/*
 * Copyright (c) 2016. iUlling Corp.
 * Created By Kil-Ho Choi
 */
package com.ulling.lib.core.util;

import android.content.Context;

/**
 *
 */
public class QcBackPressClose {
    private long backKeyPressedTime = 0;
    private static final int BACK_KEY_TIMEOUT = 2000;
    private static QcBackPressClose SINGLE_U;

    private QcBackPressClose() {
    }

    public static WQcBackPressClose with(Context qCtx) {
        if (SINGLE_U == null) {
            SINGLE_U = new QcBackPressClose();
        }
        return SINGLE_U.get(qCtx);
    }

    private WQcBackPressClose get(Context qCtx) {
        return new WQcBackPressClose(qCtx);
    }

    public class WQcBackPressClose {
        private final Context qCtx;

        public WQcBackPressClose(Context qCtx) {
            this.qCtx = qCtx;
        }

        public boolean isBackPress(String backKeyMsg) {
            if (System.currentTimeMillis() > backKeyPressedTime + BACK_KEY_TIMEOUT) {
                backKeyPressedTime = System.currentTimeMillis();
                if (qCtx != null) {
                    QcToast.with(qCtx, backKeyMsg, false);
                }
                return false;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + BACK_KEY_TIMEOUT) {
                return true;
            }
            return false;
        }

        public boolean isBackPress(int backKeyMsgId) {
            if (System.currentTimeMillis() > backKeyPressedTime + BACK_KEY_TIMEOUT) {
                backKeyPressedTime = System.currentTimeMillis();
                if (qCtx != null) {
                    QcToast.with(qCtx, qCtx.getResources().getString(backKeyMsgId), false);
                }
                return false;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + BACK_KEY_TIMEOUT) {
                return true;
            }
            return false;
        }
    }
}