package com.company.mulitchoice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.company.mulitchoice.dashboardPage.NewDashboardActivity;
import com.company.mulitchoice.logInPage.LogInActivity;
import com.company.mulitchoice.signUpPage.SignUpActivity;
import com.github.ybq.android.spinkit.SpinKitView;

public abstract class BaseBindingActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB myBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResources());
        executeBinding();
        inItComponent();
        getViewBinding();
    }

    public VB getViewBinding() {
        return myBinding;
    }

    protected abstract void inItComponent();

    private void executeBinding() {
        myBinding = layoutInflater();
        setContentView(myBinding.getRoot());
    }

    protected abstract VB layoutInflater();

    protected abstract int layoutResources();

    public void failureDialog(String message){
        final Dialog myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.setContentView(R.layout.failure_dialog_layout);

        TextView ok = myDialog.findViewById(R.id.tv_ok);
        TextView msgView = myDialog.findViewById(R.id.tv_error_message);
        msgView.setText(message);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            myDialog.dismiss();

                Intent logInIntent = new Intent(BaseBindingActivity.this, LogInActivity.class);
                startActivity(logInIntent);

                Intent myIntent = new Intent(BaseBindingActivity.this, NewDashboardActivity.class);
                startActivity(myIntent);

            }
        });


        myDialog.show();
        myDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window myWindow = myDialog.getWindow();
        myWindow.setGravity(Gravity.BOTTOM);
    }

    public void dashBoardDialog(){
        final Dialog myBottomSheetDialog = new Dialog(this);
        myBottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myBottomSheetDialog.setContentView(R.layout.account_upgrade_bottom_sheet_dialog_layout);

        View whiteCircle = myBottomSheetDialog.findViewById(R.id.circle_pass);
        CardView yellowPassMark = myBottomSheetDialog.findViewById(R.id.pass_mark);
        Button continueButton = myBottomSheetDialog.findViewById(R.id.btn_continue);
        TextView cancel = myBottomSheetDialog.findViewById(R.id.tv_cancel);


        whiteCircle.setOnClickListener(view1 -> {
            whiteCircle.setVisibility(View.INVISIBLE);
            assert yellowPassMark != null;
            yellowPassMark.setVisibility(View.VISIBLE);
        });

        yellowPassMark.setOnClickListener(view1 -> {
            yellowPassMark.setVisibility(View.INVISIBLE);
            whiteCircle.setVisibility(View.VISIBLE);
        });

        myBottomSheetDialog.show();
        myBottomSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        myBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myBottomSheetDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Window myWindow = myBottomSheetDialog.getWindow();
        myWindow.setGravity(Gravity.BOTTOM);
    }


}