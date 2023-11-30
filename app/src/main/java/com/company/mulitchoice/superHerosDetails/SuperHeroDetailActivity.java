package com.company.mulitchoice.superHerosDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.billersList.BillerListActivity;
import com.company.mulitchoice.dashboardPage.DashboardActivity;
import com.company.mulitchoice.dashboardPage.SuperHerosModel;
import com.company.mulitchoice.databinding.ActivitySuperHeroDetailBinding;
import com.company.mulitchoice.employeeDatabasePage.SaveUsersActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

public class SuperHeroDetailActivity extends BaseBindingActivity<ActivitySuperHeroDetailBinding> {

    private SuperHerosModel myModel;
    private BottomSheetDialog myBottomSheetDialog;
    @Override
    protected ActivitySuperHeroDetailBinding layoutInflater() {
        return ActivitySuperHeroDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected int layoutResources() {
        return R.layout.activity_super_hero_detail;
    }

    @Override
    protected void inItComponent() {

        getHerosData();
        initListener();

    }
    private void getHerosData() {

        Intent myIntent = getIntent();
        String heroImage = myIntent.getStringExtra("hero_image");
        String heroName = myIntent.getStringExtra("hero_name");
        String heroRealName = myIntent.getStringExtra("real_name");
        String heroCreator = myIntent.getStringExtra("hero_creator");
        String heroBio = myIntent.getStringExtra("hero_bio");
        String heroFirstAppearance = myIntent.getStringExtra("hero_first_appearance");
        String heroPublisher = myIntent.getStringExtra("hero_publisher");
        String heroTeam = myIntent.getStringExtra("hero_team");


        Picasso.get().load(heroImage).into(getViewBinding().imgHeroPicture);
        getViewBinding().tvSuperHeroBio.setText(heroBio);
        getViewBinding().tvSuperHeroTeam.setText(heroTeam);
        getViewBinding().tvSuperHeroFirstAppearance.setText(heroFirstAppearance);
        getViewBinding().tvCreatedBy.setText(heroCreator);
        getViewBinding().tvRealName.setText(heroRealName);
        getViewBinding().tvName.setText(heroName);
        getViewBinding().tvSuperPublisher.setText(heroPublisher);

    }

    private void initListener() {
       arrowBack();
       nameClick();
    }

    private void nameClick() {
        getViewBinding().pendingMore.setOnClickListener(view -> {
             show();
        });
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void show() {
         myBottomSheetDialog = new BottomSheetDialog(this);
         myBottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

         LinearLayout editLayout = myBottomSheetDialog.findViewById(R.id.layout_edit);
         LinearLayout shareLayout = myBottomSheetDialog.findViewById(R.id.layout_share);
         LinearLayout printLayout = myBottomSheetDialog.findViewById(R.id.layout_print);
         LinearLayout updateLayout = myBottomSheetDialog.findViewById(R.id.layout_update);
         LinearLayout dismissDialog = myBottomSheetDialog.findViewById(R.id.layout_dismiss);
        TextView heroName = myBottomSheetDialog.findViewById(R.id.tv_super_hero_name);

        Intent myIntent = getIntent();
        String superHeroName = myIntent.getStringExtra("hero_name");

        assert heroName != null;
        heroName.setText(superHeroName);


        assert dismissDialog != null;
        dismissDialog.setOnClickListener(view -> {
             myBottomSheetDialog.dismiss();
         });

        assert editLayout != null;
        editLayout.setOnClickListener(view -> {
            myBottomSheetDialog.dismiss();

            Intent editIntent = new Intent(SuperHeroDetailActivity.this, SaveUsersActivity.class);
            startActivity(editIntent);

         });

        assert shareLayout != null;
        shareLayout.setOnClickListener(view -> {
            myBottomSheetDialog.dismiss();

//            String app_url = "https://simplifiedcoding.net/demos/";
//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            //          set image uri
//            Uri imageUri = Uri.fromFile(new File(getCacheDir(),"images"));
////            set the subject and text (message with link)
//            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check it out" + app_url);
////            shareIntent.putExtra(Intent.EXTRA_STREAM, myModel.getImageurl());
//            shareIntent.putExtra(Intent.EXTRA_TEXT, app_url);
//            shareIntent.setDataAndType(imageUri,"image/*");
//            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//            shareIntent.setType("text/plain");
//
//            if(shareIntent.resolveActivity(getPackageManager()) != null){
//                startActivity(Intent.createChooser(shareIntent, "Share using"));
//            }
        });

        assert printLayout != null;
        printLayout.setOnClickListener(view -> {
            myBottomSheetDialog.dismiss();

        });

        assert updateLayout != null;
        updateLayout.setOnClickListener(view -> {
            myBottomSheetDialog.dismiss();

        });
        myBottomSheetDialog.show();
    }

    private void arrowBack() {
        getViewBinding().arrowBack.setOnClickListener(view -> {
            SuperHeroDetailActivity.super.onBackPressed();
        });
    }


}