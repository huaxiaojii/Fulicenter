package ucai.cn.fulicter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import ucai.cn.fulicter.FuLiCenterApplication;
import ucai.cn.fulicter.I;
import ucai.cn.fulicter.fragment.BotiqueFragment;
import ucai.cn.fulicter.fragment.CartFragment;
import ucai.cn.fulicter.fragment.CategoryFragment;
import ucai.cn.fulicter.fragment.NewGoodsFragment;
import ucai.cn.fulicter.fragment.PersonalCenterFragment;
import ucai.cn.fulicter.utils.L;
import ucai.cn.fulicter.utils.MFGT;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.layout_new_good)
    RadioButton mLayoutNewGood;
    @BindView(R.id.layout_boutique)
    RadioButton mLayoutBoutique;
    @BindView(R.id.layout_category)
    RadioButton mLayoutCategory;
    @BindView(R.id.layout_cart)
    RadioButton mLayoutCart;
    @BindView(R.id.tvCartHint)
    TextView mTvCartHint;
    @BindView(R.id.layout_personal_center)
    RadioButton mLayoutPersonalCenter;

    int index;
    int currentIndex;
    RadioButton[] rbs;
    Fragment[] mFragments;
    NewGoodsFragment mNewGoodsFragment;
    BotiqueFragment mBoutiqueFragment;
    CategoryFragment mCategoryFragment;
    PersonalCenterFragment mPersonalCenterFragment;
    CartFragment mCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        L.i("MainActivity onCreate");
        super.onCreate(savedInstanceState);
    }

    private void initFragment() {
        mFragments = new Fragment[5];
        mNewGoodsFragment = new NewGoodsFragment();
        mBoutiqueFragment = new BotiqueFragment();
        mCategoryFragment = new CategoryFragment();
        mCartFragment = new CartFragment();
        mPersonalCenterFragment = new PersonalCenterFragment();
//        mFragments[0] = mNewGoodsFragment;
//        mFragments[1] = mBoutiqueFragment;
//        mFragments[2] = mCategoryFragment;
//        mFragments[4] = mPersonalCenterFragment;

//        mFragments[3] = mCartFragment;
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.fragment_container,mBoutiqueFragment)
        //      .add(R.id.fragment_container,mCategoryFragment)
        //.hide(mBoutiqueFragment)
        //      .hide(mCategoryFragment)
//                .add(R.id.fragment_container,mNewGoodsFragment)
//                .add(R.id.fragment_container,mBoutiqueFragment)
//                .add(R.id.fragment_container,mCategoryFragment)
//                .hide(mBoutiqueFragment)
//                .hide(mCategoryFragment)
//                .show(mNewGoodsFragment)
//                .commit();
    }

    @Override
    protected void initView() {
        rbs = new RadioButton[5];
        rbs[0] = mLayoutNewGood;
        rbs[1] = mLayoutBoutique;
        rbs[2] = mLayoutCategory;
        rbs[3] = mLayoutCart;
        rbs[4] = mLayoutPersonalCenter;
    }

    @Override
    protected void initData() {
        initFragment();
    }

    @Override
    protected void setListener() {

    }

    public void onCheckedChange(View v) {
        switch (v.getId()){
            case R.id.layout_new_good:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_category:
                index = 2;
                break;
            case R.id.layout_cart:
//                index = 3;
                if(FuLiCenterApplication.getUser()==null){
                                        MFGT.gotoLoginFromCart(this);
                                    }else {
                                        index = 3;
                                    }
                break;
            case R.id.layout_personal_center:
                if(FuLiCenterApplication.getUser()==null){
                    MFGT.gotoLogin(this);
                }else {
                    index = 4;
                }
                break;
        }
        setFragment();
    }

    private void setFragment() {
        if(index!=currentIndex) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.hide(mFragments[currentIndex]);
            if(!mFragments[index].isAdded()){
                ft.add(R.id.fragment_container,mFragments[index]);
            }
            ft.show(mFragments[index]).commit();
        }
        setRadioButtonStatus();
        currentIndex = index;
    }

    private void setRadioButtonStatus() {
        L.e("index="+index);
        for (int i=0;i<rbs.length;i++){
            if(i==index){
                rbs[i].setChecked(true);
            }else{
                rbs[i].setChecked(false);
            }
        }
    }
    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.e(TAG,"onResume...");
        if (index == 4&&FuLiCenterApplication.getUser()== null){
            index = 0;
        }
        setFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e(TAG,"onActivityResult,requestCode="+requestCode);
//        if(requestCode == I.REQUEST_CODE_LOGIN && FuLiCenterApplication.getUser()!=null){
//            index = 4;
        if(FuLiCenterApplication.getUser()!=null){
            if(requestCode == I.REQUEST_CODE_LOGIN) {
                index = 4;
            }
            if(requestCode == I.REQUEST_CODE_LOGIN_FROM_CART){
                index = 3;
            }
        }
    }
}