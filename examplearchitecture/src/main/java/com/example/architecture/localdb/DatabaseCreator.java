package com.example.architecture.localdb;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 9.
 * @description :
 * @since :
 */

public class DatabaseCreator {
    private static RoomLocalData INSTANCE;
    private static final Object LOCK = new Object();

    public synchronized static RoomLocalData getRoomLocalData(Context context){
        if(INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            RoomLocalData.class, "person db")
//                    // To simplify the codelab, allow queries on the main thread.
//                    // Don't do this on a real app! See PersistenceBasicSample for an example.
//                    .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
