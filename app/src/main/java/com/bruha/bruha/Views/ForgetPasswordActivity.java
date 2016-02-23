package com.bruha.bruha.Views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruha.bruha.Processing.CredentialsPHP;
import com.bruha.bruha.R;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ForgetPasswordActivity extends AppCompatActivity {

    CredentialsPHP credentialsPHP = new CredentialsPHP();

    @InjectView(R.id.forgetPasswordUsernameTextView) TextView mForgetPasswordUsernameTextView;
    @InjectView(R.id.forgetPasswordUsernameEditText) EditText mForgetPasswordUsernameEditText;
    @InjectView(R.id.forgetPasswordEmailTextView) TextView mForgetPasswordEmailTextView;
    @InjectView(R.id.forgetPasswordEmailEditText) EditText mForgetPasswordEmailEditText;
    //Injevting the Buttons:
    @InjectView(R.id.forgetPasswordSkipButton) Button mForgetPasswordSkipButton;
    @InjectView(R.id.forgetPasswordBackButton) Button mForgetPasswordBackButton;
    @InjectView(R.id.forgetPasswordSubmitButton) Button mForgetPasswordSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ButterKnife.inject(this);

        //Setting the bruha face inside the Layout.
        ImageView im = (ImageView) findViewById(R.id.loginbruhaface);
        im.setImageDrawable(svgToBitmapDrawable(getResources(), R.raw.bruhapurpleface, 100));

        //Setting onCLickListeners for the buttons
        mForgetPasswordSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(mForgetPasswordSkipButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        mForgetPasswordSkipButton.setAlpha(1f);
                        proceedWithoutAccount(null);
                    }
                });
                animator.start();
            }
        });

        mForgetPasswordBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(mForgetPasswordBackButton, "alpha", 1f, 0.5f);
                animator.setDuration(100);
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        mForgetPasswordBackButton.setAlpha(1f);
                        backtoLoginActivity(null);
                    }
                });
                animator.start();
            }
        });

        mForgetPasswordSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                String username = mForgetPasswordUsernameEditText.getText().toString();
                String email = mForgetPasswordEmailEditText.getText().toString();

                String response = credentialsPHP.forgotPassword(email, username);

                Log.v("Passreset test", response);

            }
        });

        resize();  //Resizing the page and implementing the buttons.
    }

    private void resize() {
        // Android functions to determine the screen dimensions.
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Storing the screen height into an int variable..
        int height = size.y;

        Typeface opensansregfnt = Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf");

        mForgetPasswordUsernameTextView.setTypeface(opensansregfnt);
        mForgetPasswordUsernameEditText.setTypeface(opensansregfnt);
        mForgetPasswordEmailTextView.setTypeface(opensansregfnt);
        mForgetPasswordEmailEditText.setTypeface(opensansregfnt);

        int x= (int)Math.round(height * .018);
        int x1= (int)Math.round(height * .028);
        mForgetPasswordUsernameTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        mForgetPasswordUsernameEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x1);
        mForgetPasswordEmailTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        mForgetPasswordEmailEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);

        ViewGroup.LayoutParams mForgetPasswordUsernameEditTextLayoutParams = mForgetPasswordUsernameEditText.getLayoutParams();
        ViewGroup.LayoutParams mForgetPasswordEmailEditTextLayoutParams = mForgetPasswordEmailEditText.getLayoutParams();
        mForgetPasswordUsernameEditTextLayoutParams.height =  (int)Math.round(height*.07);
        mForgetPasswordEmailEditTextLayoutParams.height =  (int)Math.round(height*.07);
        mForgetPasswordUsernameEditTextLayoutParams.width  = (int) Math.round(height*.30);
        mForgetPasswordEmailEditTextLayoutParams.width  = (int) Math.round(height*.30);

        ViewGroup.LayoutParams SkipButtonLayoutParams = mForgetPasswordSkipButton.getLayoutParams();
        ViewGroup.LayoutParams BackButtonLayoutParams = mForgetPasswordBackButton.getLayoutParams();
        ViewGroup.LayoutParams SubmitButtonLayoutParams = mForgetPasswordSubmitButton.getLayoutParams();
        SkipButtonLayoutParams.height =  (int)Math.round(height*.07);
        BackButtonLayoutParams.height =  (int)Math.round(height*.07);
        SubmitButtonLayoutParams.height =  (int)Math.round(height*.07);
        SkipButtonLayoutParams.width  = (int) Math.round(height*.135);
        BackButtonLayoutParams.width  = (int) Math.round(height*.135);
        SubmitButtonLayoutParams.width  = (int) Math.round(height*.135);

        mForgetPasswordSkipButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        mForgetPasswordBackButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
        mForgetPasswordSubmitButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, x);
    }







    public void proceedWithoutAccount(View view){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    public void backtoLoginActivity(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        }

    public BitmapDrawable svgToBitmapDrawable(Resources res, int resource, int size){
        try {
            size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVG.getFromResource(getApplicationContext(), resource);

            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            svg.renderToCanvas(canvas);

            BitmapDrawable drawable = new BitmapDrawable(res, bmp);


            return drawable;
        }catch(SVGParseException e){
            e.printStackTrace();
        }
        return null;
    }
}