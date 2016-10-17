package ucai.cn.fulicter.activity;

import android.os.Bundle;
import android.os.Handler;

import ucai.cn.fulicter.R;
import ucai.cn.fulicter.utils.MFGT;

public class SplashActivity extends AppCompatActivity {
    private static final long sleepTime=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.gotoMainActivity(SplashActivity.this);
                finish();
            }
        },sleepTime);
    }
}
