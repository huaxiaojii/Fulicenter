package ucai.cn.fulicter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import ucai.cn.fulicter.FuLiCenterApplication;
import ucai.cn.fulicter.activity.MainActivity;
import ucai.cn.fulicter.bean.MessageBean;
import ucai.cn.fulicter.bean.Result;
import ucai.cn.fulicter.bean.User;
import ucai.cn.fulicter.dao.UserDao;
import ucai.cn.fulicter.net.NetDao;
import ucai.cn.fulicter.net.OkHttpUtils;
import ucai.cn.fulicter.utils.ImageLoader;
import ucai.cn.fulicter.utils.L;
import ucai.cn.fulicter.utils.MFGT;
import ucai.cn.fulicter.utils.ResultUtils;

/**
 * Created by User on 2016/10/24.
 */

public class PersonalCenterFragment extends BaseFragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();
    @BindView(R.id.iv_user_avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;

    MainActivity mContext;
    @BindView(R.id.center_user_order_lis)
    GridView mCenterUserOrderLis;
    User user = null;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {

        initOrderList();

    }

    @Override
    protected void initData() {
//        User user = FuLiCenterApplication.getUser();
        user = FuLiCenterApplication.getUser();
        L.e(TAG, "user=" + user);

        if (user == null){
            MFGT.gotoLogin(mContext);
        } else {
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext, mIvUserAvatar );
            mTvUserName.setText(user.getMuserNick());
        }
    }

    @Override
    protected void setListener() {

    }
    @Override
    public void onResume(){
        super.onResume();
        user = FuLiCenterApplication.getUser();
        L.e(TAG,"user="+user);

        if (user!=null){
            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user),mContext,mIvUserAvatar);
            mTvUserName.setText(user.getMuserNick());
            syncUserInfo();
            syncCollectsCount();
        }
    }

    private void syncCollectsCount() {
        NetDao.getCollectsCount(mContext, user.getMuserName(), new OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                                if (result != null && result.isSuccess()) {
                                        mTvCollectCount.setText(result.getMsg());
                                    }else{
                                        mTvCollectCount.setText(String.valueOf(0));
                                    }
                            }

                                @Override
                        public void onError(String error) {
                                mTvCollectCount.setText(String.valueOf(0));
                                L.e(TAG,"error="+error);
                            }
                    });
            }



    private void syncUserInfo() {
        NetDao.syncUserInfo(mContext,user.getMuserName(), new OkHttpUtils.OnCompleteListener<String>(){
            @Override
            public void onSuccess(String s) {
                Result result = ResultUtils.getResultFromJson(s, User.class);
                if(result!=null){
                    User u = (User) result.getRetData();
                    if(!user.equals(u)){
                        UserDao dao = new UserDao(mContext);
                        boolean b = dao.saveUser(u);
                        if(b){
                            FuLiCenterApplication.setUser(u);
                            user = u;
                            ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), mContext, mIvUserAvatar);
                            mTvUserName.setText(user.getMuserNick());
                        }
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    @OnClick(R.id.layout_center_collect)
    public void gotoCollectsList(){
        MFGT.gotoCollects(mContext);
    }



    private void initOrderList() {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> order1 = new HashMap<String, Object>();
            order1.put("order", R.drawable.order_list1);
        data.add(order1);
        HashMap<String, Object> order2 = new HashMap<String, Object>();
            order2.put("order", R.drawable.order_list2);
        data.add(order2);
        HashMap<String, Object> order3 = new HashMap<String, Object>();
            order3.put("order", R.drawable.order_list3);
        data.add(order3);
        HashMap<String, Object> order4 = new HashMap<String, Object>();
            order4.put("order", R.drawable.order_list4);
        data.add(order4);
        HashMap<String, Object> order5 = new HashMap<String, Object>();
            order5.put("order", R.drawable.order_list5);
        data.add(order5);
        SimpleAdapter adapter = new SimpleAdapter(mContext, data, R.layout.simple_adapter,
                new String[]{"order"}, new int[]{R.id.iv_order});
                mCenterUserOrderLis.setAdapter(adapter);
            }

    @OnClick({ R.id.tv_center_settings,R.id.center_user_info})
    public void gotoSettings(){
        MFGT.gotoSettings(mContext);
    }
}