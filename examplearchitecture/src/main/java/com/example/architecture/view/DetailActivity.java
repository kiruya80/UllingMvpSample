package com.example.architecture.view;

import android.content.Intent;
import android.os.Build;

import com.example.architecture.R;
import com.example.architecture.databinding.ActivityDetailBinding;
import com.example.architecture.entities.room.Answer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ulling.lib.core.base.QcBaseLifeActivity;
import com.ulling.lib.core.util.QcLog;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 8. 2.
 * @description :
 * @since :
 */

public class DetailActivity extends QcBaseLifeActivity {
    ActivityDetailBinding viewBinding;
    Answer item;
    String TransitionName;

    @Override
    protected void needInitToOnCreate() {
        Intent intent = getIntent();
//        ProfileImage = intent.getStringExtra("ProfileImage");
        TransitionName = intent.getStringExtra("TransitionName");
        item = (Answer) intent.getSerializableExtra("item");
        if (item != null)
            QcLog.e("item == " + item.toString());
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

    }

    @Override
    protected void needUIEventListener() {

    }

    @Override
    protected void needSubscribeUiFromViewModel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewBinding.ivProfile.setTransitionName(TransitionName);
        }

        viewBinding.tvUserId.setText("" + item.getOwner().getUserId());
        viewBinding.tvUserName.setText(item.getOwner().getDisplayName());

//        if (item.getOwner().getProfileImage() != null)
        Picasso.with(qCon)
                .load(item.getOwner().getProfileImage())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
//                .crossFade(R.anim.fade_in, 300)
//                    .bitmapTransform(new BlurTransformation(qCon, 3), new CropCircleTransformation(qCon))
//                .into(viewBinding.ivProfile);
         .into(viewBinding.ivProfile, new Callback() {
            @Override
            public void onSuccess() {
                supportStartPostponedEnterTransition();
            }

            @Override
            public void onError() {
                supportStartPostponedEnterTransition();
            }
        });


    }
}
