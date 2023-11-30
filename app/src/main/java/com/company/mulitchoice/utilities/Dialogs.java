package com.company.mulitchoice.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.company.mulitchoice.BaseBindingActivity;
import com.company.mulitchoice.R;
import com.company.mulitchoice.logInPage.LogInActivity;
import com.company.mulitchoice.superHerosDetails.SuperHeroDetailActivity;
import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Objects;

public class Dialogs {

    private Dialogs() {
    }

    public static void SuccessDialog(Context myContext, String message){
        final Dialog myDialog = new Dialog(myContext);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.success_dialog_layout);

        TextView ok = myDialog.findViewById(R.id.tv_ok);
        TextView msgView = myDialog.findViewById(R.id.tv_error_message);
        msgView.setText(message);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialog.dismiss();

            }
        });


        myDialog.show();
        Objects.requireNonNull(myDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window myWindow = myDialog.getWindow();
        myWindow.setGravity(Gravity.BOTTOM);
    }

    public static void loadingDialog(Context myContext){
        final Dialog myDialog = new Dialog(myContext);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.loading_dialog);

        SpinKitView progressDialog = myDialog.findViewById(R.id.spinKit2);

        progressDialog.setVisibility(View.VISIBLE);

        myDialog.show();
        Objects.requireNonNull(myDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        myBottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window myWindow = myDialog.getWindow();
        myWindow.setGravity(Gravity.TOP);
    }



}
