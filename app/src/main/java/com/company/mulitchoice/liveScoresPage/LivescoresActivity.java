package com.company.mulitchoice.liveScoresPage;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.view.View;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.EventFragment;
import com.company.mulitchoice.R;
import com.company.mulitchoice.databinding.ActivityLivescoresBinding;
import com.company.mulitchoice.fragments.NewsFragment;
import com.company.mulitchoice.fragments.ScoresFragment;

public class LivescoresActivity extends BaseBindingActivity<ActivityLivescoresBinding> {


    @Override
    protected ActivityLivescoresBinding layoutInflater() {
        return ActivityLivescoresBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_livescores;
    }
    @Override
    protected void inItComponent() {
        initView();
        initListener();
    }

    private void initView() {
        startUpFragment();
    }

    private void startUpFragment() {
        FragmentManager myFragmentManager = getSupportFragmentManager();
        FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
        ScoresFragment myScoreFragment = new ScoresFragment();
        myFragmentTransaction.add(R.id.fragment_container, myScoreFragment).commit();

    }

    private void initListener() {
        sportEvents();
        bottomNavigationComponent();
    }

    private void bottomNavigationComponent() {
        scores();
        events();
        news();
    }

    private void news() {
        getViewBinding().layoutNews.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getViewBinding().viewNewsPurple.setVisibility(View.VISIBLE);
                getViewBinding().tvNews.setTextColor(getColor(R.color.orange));

                getViewBinding().viewScoresPurple.setVisibility(View.INVISIBLE);
                getViewBinding().tvScores.setTextColor(getColor(R.color.text_gray));

                getViewBinding().viewFavouritePurple.setVisibility(View.INVISIBLE);
                getViewBinding().tvFavourite.setTextColor(getColor(R.color.text_gray));

                FragmentManager myFragmentManager = getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                NewsFragment myNewsFragment = new NewsFragment();
                myFragmentTransaction.replace(R.id.fragment_container, myNewsFragment).commit();

            }
        });
    }

    private void events() {
        getViewBinding().layoutFavourite.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getViewBinding().viewFavouritePurple.setVisibility(View.VISIBLE);
                getViewBinding().tvFavourite.setTextColor(getColor(R.color.orange));

                getViewBinding().viewScoresPurple.setVisibility(View.INVISIBLE);
                getViewBinding().tvScores.setTextColor(getColor(R.color.text_gray));

                getViewBinding().viewNewsPurple.setVisibility(View.INVISIBLE);
                getViewBinding().tvNews.setTextColor(getColor(R.color.text_gray));

                FragmentManager myFragmentManager = getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                EventFragment myEventFragment = new EventFragment();
                myFragmentTransaction.replace(R.id.fragment_container, myEventFragment).commit();

            }
        });
    }

    private void scores() {
        getViewBinding().layoutScores.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getViewBinding().viewScoresPurple.setVisibility(View.VISIBLE);
                getViewBinding().tvScores.setTextColor(getColor(R.color.orange));

                getViewBinding().viewFavouritePurple.setVisibility(View.INVISIBLE);
                getViewBinding().tvFavourite.setTextColor(getColor(R.color.text_gray));

                getViewBinding().viewNewsPurple.setVisibility(View.INVISIBLE);
                getViewBinding().tvNews.setTextColor(getColor(R.color.text_gray));

                FragmentManager myFragmentManager = getSupportFragmentManager();
                FragmentTransaction myFragmentTransaction = myFragmentManager.beginTransaction();
                ScoresFragment myScoreFragment = new ScoresFragment();
                myFragmentTransaction.replace(R.id.fragment_container, myScoreFragment).commit();

            }
        });
    }

    private void sportEvents() {
        football();
        hockey();
        basketBall();
        tennis();
        cricket();
    }

    private void cricket() {
        getViewBinding().layoutCricket.setOnClickListener(view -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getViewBinding().viewCricket.setVisibility(View.VISIBLE);
                getViewBinding().tvCricket.setTextColor(getColor(R.color.black));

                getViewBinding().viewFootball.setVisibility(View.INVISIBLE);
                getViewBinding().tvFootball.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewHockey.setVisibility(View.INVISIBLE);
                getViewBinding().tvHockey.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewTennis.setVisibility(View.INVISIBLE);
                getViewBinding().tvTennis.setTextColor(getColor(R.color.text_gray));

                getViewBinding().viewBasketball.setVisibility(View.INVISIBLE);
                getViewBinding().tvBasketball.setTextColor(getColor(R.color.text_gray));
            }
        });
    }

    private void tennis() {
        getViewBinding().layoutTennis.setOnClickListener(view -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getViewBinding().viewTennis.setVisibility(View.VISIBLE);
                getViewBinding().tvTennis.setTextColor(getColor(R.color.black));

                getViewBinding().viewFootball.setVisibility(View.INVISIBLE);
                getViewBinding().tvFootball.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewHockey.setVisibility(View.INVISIBLE);
                getViewBinding().tvHockey.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewBasketball.setVisibility(View.INVISIBLE);
                getViewBinding().tvBasketball.setTextColor(getColor(R.color.text_gray));

                getViewBinding().viewCricket.setVisibility(View.INVISIBLE);
                getViewBinding().tvCricket.setTextColor(getColor(R.color.text_gray));
            }
        });

    }

    private void basketBall() {
        getViewBinding().layoutBasketball.setOnClickListener(view -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getViewBinding().viewBasketball.setVisibility(View.VISIBLE);
                getViewBinding().tvBasketball.setTextColor(getColor(R.color.black));

                getViewBinding().viewFootball.setVisibility(View.INVISIBLE);
                getViewBinding().tvFootball.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewHockey.setVisibility(View.INVISIBLE);
                getViewBinding().tvHockey.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewTennis.setVisibility(View.INVISIBLE);
                getViewBinding().tvTennis.setTextColor(getColor(R.color.text_gray));

                getViewBinding().viewCricket.setVisibility(View.INVISIBLE);
                getViewBinding().tvCricket.setTextColor(getColor(R.color.text_gray));
            }
        });
    }

    private void hockey() {
        getViewBinding().layoutHockey.setOnClickListener(view -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getViewBinding().viewHockey.setVisibility(View.VISIBLE);
                getViewBinding().tvHockey.setTextColor(getColor(R.color.black));

                getViewBinding().viewFootball.setVisibility(View.INVISIBLE);
                getViewBinding().tvFootball.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewBasketball.setVisibility(View.INVISIBLE);
                getViewBinding().tvBasketball.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewTennis.setVisibility(View.INVISIBLE);
                getViewBinding().tvTennis.setTextColor(getColor(R.color.text_gray));

                getViewBinding().viewCricket.setVisibility(View.INVISIBLE);
                getViewBinding().tvCricket.setTextColor(getColor(R.color.text_gray));
            }
        });
    }
    private void football() {
        getViewBinding().layoutFootball.setOnClickListener(view -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getViewBinding().viewFootball.setVisibility(View.VISIBLE);
                getViewBinding().tvFootball.setTextColor(getColor(R.color.black));

                getViewBinding().viewHockey.setVisibility(View.INVISIBLE);
                getViewBinding().tvHockey.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewBasketball.setVisibility(View.INVISIBLE);
                getViewBinding().tvBasketball.setTextColor(getColor(R.color.text_gray));


                getViewBinding().viewTennis.setVisibility(View.INVISIBLE);
                getViewBinding().tvTennis.setTextColor(getColor(R.color.text_gray));

                getViewBinding().viewCricket.setVisibility(View.INVISIBLE);
                getViewBinding().tvCricket.setTextColor(getColor(R.color.text_gray));
            }
        });
    }


}