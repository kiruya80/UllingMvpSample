package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.common.ApiUrl;
import com.example.architecture.databinding.ActivityDetailBinding;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.viewmodel.DetailRetrofitLiveViewModel;
import com.ulling.lib.core.base.QcBaseLifeActivity;
import com.ulling.lib.core.util.QcLog;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 8. 2.
 * @description :
 * @since :
 */

public class DetailActivity extends QcBaseLifeActivity {
    private QUllingApplication qApp;
    private ActivityDetailBinding viewBinding;
    private DetailRetrofitLiveViewModel viewModel;
    private Answer item;
    private int answerId;
    private String TransitionName_id;
    private String TransitionName_name;
    private String TransitionName_profile;

    @Override
    protected void needInitToOnCreate() {
        Intent intent = getIntent();
//        ProfileImage = intent.getStringExtra("ProfileImage");
        TransitionName_id = intent.getStringExtra("TransitionName_id");
        TransitionName_name = intent.getStringExtra("TransitionName_name");
        TransitionName_profile = intent.getStringExtra("TransitionName_profile");
        answerId = intent.getIntExtra("answerId", 0);
        item = (Answer) intent.getSerializableExtra("item");
        if (item != null)
            QcLog.e("item == " + item.toString());

        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(DetailRetrofitLiveViewModel.class);
            viewModel.needInitViewModel(this);
            viewModel.needDatabaseModel(DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }

    }

    @Override
    protected void needResetData() {

    }

    @Override
    protected void needInitViewModel() {

    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (ActivityDetailBinding) getViewDataBinding();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            viewBinding.tvUserId.setTransitionName(TransitionName_id);
//            viewBinding.tvUserName.setTransitionName(TransitionName_name);
            viewBinding.rlProfile.setTransitionName(TransitionName_profile);
//            viewBinding.ivProfile.setTransitionName(TransitionName_profile);
        }

    }

    @Override
    protected void needUIEventListener() {

    }

    @Override
    protected void needSubscribeUiFromViewModel() {
        observerAnswer(viewModel.getAnswerFromRoom(answerId));
    }


    private void observerAnswer(LiveData<Answer> answer) {
        QcLog.e("observerAllAnswer == ");
        //observer LiveData
        answer.observe(this, new Observer<Answer>() {
            @Override
            public void onChanged(@Nullable Answer answer) {
                QcLog.e("allanswers observe == ");
                if (answer != null) {
                    viewBinding.tvUserId.setText("" + answer.getOwner().getUserId());
                    viewBinding.tvUserName.setText(answer.getOwner().getDisplayName());

                Glide.with(qCon)
                        .load(answer.getOwner().getProfileImage())
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher)
//                        .crossFade(R.anim.fade_in, 300)
                        .bitmapTransform(new CropCircleTransformation(qCon))
                        .into(viewBinding.ivProfile);

//                    Picasso.with(qCon)
//                            .load(answer.getOwner().getProfileImage())
//                            .error(R.mipmap.ic_launcher)
//                            .placeholder(R.mipmap.ic_launcher)
//                            .into(viewBinding.ivProfile, new Callback() {
//                                @Override
//                                public void onSuccess() {
//                                    supportStartPostponedEnterTransition();
//                                }
//
//                                @Override
//                                public void onError() {
//                                    supportStartPostponedEnterTransition();
//                                }
//                            });


                } else {

                }
            }
        });
    }

}
