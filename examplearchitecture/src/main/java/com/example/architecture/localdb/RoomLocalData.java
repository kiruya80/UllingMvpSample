
package com.example.architecture.localdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.architecture.enty.BookDao;
import com.example.architecture.enty.LoanDao;
import com.example.architecture.enty.User;
import com.example.architecture.enty.Book;
import com.example.architecture.enty.Loan;
import com.example.architecture.enty.UserDao;

@Database(entities = {User.class, Book.class, Loan.class}, version = 1)
public abstract class RoomLocalData extends RoomDatabase {
    private static RoomLocalData INSTANCE;
//    public abstract UserDao userDao();

    public abstract UserDao userModel();
    public abstract BookDao bookModel();
    public abstract LoanDao loanModel();

    public static RoomLocalData getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), RoomLocalData.class)
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}