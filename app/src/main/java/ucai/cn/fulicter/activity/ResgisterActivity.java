package ucai.cn.fulicter.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicter.bean.Result;
import ucai.cn.fulicter.net.NetDao;
import ucai.cn.fulicter.net.OkHttpUtils;
import ucai.cn.fulicter.utils.CommonUtils;
import ucai.cn.fulicter.utils.L;
import ucai.cn.fulicter.utils.MFGT;
import ucai.cn.fulicter.view.DisplayUtils;

/**
 * Created by User on 2016/10/23.
 */

public class ResgisterActivity extends BaseActivity{
    private static final String TAG = ResgisterActivity.class.getSimpleName();

    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.nick)
    EditText mNick;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.btn_register)
    Button mBtnRegister;


    String username;
    String nickname;
    String password;
    RecyclerView mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        DisplayUtils.initBackWithTitle(this, "账户注册");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.btn_register)
    public void checkedInput() {
        username = mUsername.getText().toString().trim();
        nickname = mNick.getText().toString().trim();
        password = mPassword.getText().toString().trim();
        String confirmPwd = mConfirmPassword.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            CommonUtils.showShortToast(R.string.user_name_connot_be_empty);
            mUsername.requestFocus();
            return;
        }else if(!username.matches("[a-zA-Z]\\w{5,15}")){
            CommonUtils.showShortToast(R.string.illegal_user_name);
            mUsername.requestFocus();
            return;
        }else if(TextUtils.isEmpty(nickname)){
            CommonUtils.showShortToast(R.string.nick_name_connot_be_empty);
            mNick.requestFocus();
            return;
        }else if(TextUtils.isEmpty(password)){
            CommonUtils.showShortToast(R.string.password_connot_be_empty);
            mPassword.requestFocus();
            return;
        }else if(TextUtils.isEmpty(confirmPwd)){
            CommonUtils.showShortToast(R.string.confirm_password_connot_be_empty);
            mConfirmPassword.requestFocus();
            return;
        }else if(!password.equals(confirmPwd)){
            CommonUtils.showShortToast(R.string.two_input_password);
            mConfirmPassword.requestFocus();
            return;
        }
        register();
    }

    private void register() {
        final ProgressDialog pd = new ProgressDialog(mContext);
        pd.setMessage(getResources().getString(R.string.registering));
        pd.show();
        NetDao.register(mContext, username, nickname, password, new OkHttpUtils.OnCompleteListener<Result>() {
            @Override
            public void onSuccess(Result result) {
                pd.dismiss();
                if(result==null){
                    CommonUtils.showShortToast(R.string.register_fail);
                }else{
                    if(result.isRetMsg()){
                        CommonUtils.showLongToast(R.string.register_success);
                        MFGT.finish(mContext);
                    }else{
                        CommonUtils.showLongToast(R.string.register_fail_exists);
                        mUsername.requestFocus();
                    }
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                CommonUtils.showShortToast(error);
                L.e(TAG,"register error="+error);
            }
        });
    }
}
}
