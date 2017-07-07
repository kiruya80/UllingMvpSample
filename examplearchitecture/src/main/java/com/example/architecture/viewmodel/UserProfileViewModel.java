package com.example.architecture.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.example.architecture.enty.LoanWithUserAndBook;
import com.example.architecture.enty.User;
import com.example.architecture.model.DatabaseModel;
import com.example.architecture.model.utils.DatabaseInitializer;
import com.ulling.lib.core.util.QcLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.DB_TYPE_REMOTE_RETROFIT;

/**
 * Created by P100651 on 2017-07-04.
 *
 * UI의 데이터를 준비하는 클래스
 * livedata 1,2,3,....
 */
public class UserProfileViewModel extends AndroidViewModel {
    private DatabaseModel mDatabaseModel;
    private LiveData<User> user;
//    private LiveData<List<User>> userList;

    private LiveData<String> mLoansResult;
    public LiveData<String> getLoansResult() {
        return mLoansResult;
    }
    public LiveData<String> getRemoteDBLoansResult() {
        return mLoansResult;
    }
    public LiveData<User> getUser() {
        return this.user;
    }

    public UserProfileViewModel(Application application ) {
        super(application);
        // db model 초기화
        mDatabaseModel = new DatabaseModel(getApplication());
        mDatabaseModel.initLocalDb(DB_TYPE_LOCAL_ROOM);
        mDatabaseModel.initRemoteDb(DB_TYPE_REMOTE_RETROFIT);

    }

    /**
     * DB 초기 생성
     * 데이터에 옵져버 연결
     */
    public void createDb() {
        DatabaseInitializer.populateAsync(mDatabaseModel.getlocalData());
        subscribeToDbChanges();
    }
    public void getUserInfo(String userId) {
        user = mDatabaseModel.getUserInfo(false, "");

//        if (user != null) {
//            // local
//            userList = mDb.userDao().findLoansByNameAfter("Mike", getYesterdayDate());
//        } else {
//            // remote data
//            user = userRepo.getUser(userId);
//        }
    }


    private void subscribeToDbChanges() {
        QcLog.e("subscribeToDbChanges == ");
        LiveData<List<LoanWithUserAndBook>> loans = mDatabaseModel.loanModel().findLoansByNameAfter("Mike", getYesterdayDate());
        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
        mLoansResult = Transformations.map(loans,
                new Function<List<LoanWithUserAndBook>, String>() {
                    @Override
                    public String apply(List<LoanWithUserAndBook> loansWithUserAndBook) {
                        QcLog.e("apply == ");
                        StringBuilder sb = new StringBuilder();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                                Locale.US);
                        for (LoanWithUserAndBook loan : loansWithUserAndBook) {
                            sb.append(String.format("%s\n  (Returned: %s)\n",
                                    loan.bookTitle,
                                    simpleDateFormat.format(loan.endTime)));
                        }
                        return sb.toString();
                    }
                });

    }

    private Date getYesterdayDate() {
        QcLog.e("getYesterdayDate == ");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -1);
        return calendar.getTime();
    }
    /**
     * 이 메서드는 ViewModel이 더이상 사용되지 않고 파괴될 때 호출됩니다.
     * Realm 인스턴스같이 ViewModel이 어떤 데이터를 관찰하고 ViewModel이 새는 것을 막기 위해
     * 구독을 취소할 필요가 있는 경우 유용합니다.
     */
    @Override
    protected void onCleared() {
        QcLog.e("onCleared == ");
        if (mDatabaseModel != null)
            mDatabaseModel.onCleared();
        super.onCleared();
    }

}