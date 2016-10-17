package ucai.cn.fulicter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;



import butterknife.BindView;
import butterknife.ButterKnife;
import ucai.cn.fulicter.R;
import ucai.cn.fulicter.utils.L;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.layout_new_good)
    RadioButton mlayoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton mlayoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton mlayoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton mlayoutCart;
    @BindView(R.id.tvCartHint)
    TextView mtvCartHint;
    @BindView(R.id.layout_personal_center)
    RadioButton mlayoutPersonalCenter;

    int index;
    RadioButton [] rbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActiviry onCreate");
        initView();
    }

    private void initView() {
        rbs = new RadioButton[5];
        rbs[0]=mlayoutNewGood;
        rbs[1]=mlayoutBoutique;
        rbs[2]=mlayoutCategory;
        rbs[3]=mlayoutCart;
        rbs[4]=mlayoutPersonalCenter;
    }


    public void onCheckedChange(View v) {
        switch (v.getId()) {
            case R.id.layout_new_good:
                index=0;
                break;
            case R.id.layout_boutique:
                index=1;
                break;
            case R.id.layout_category:
                index=2;
                break;
            case R.id.layout_cart:
                index=3;
                break;
            case R.id.layout_personal_center:
                index=4;
                break;
        }
        setRadioButtonSataus();
    }

    private void setRadioButtonSataus() {
        for (int i=0;i<rbs.length;i++) {
            if (i == index) {
                rbs[i].setChecked(true);
            }else{
                rbs[i].setChecked(false);
            }
        }
    }
}
