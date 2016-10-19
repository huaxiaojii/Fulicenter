package ucai.cn.fulicter.fragment;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ucai.cn.fulicter.I;
import ucai.cn.fulicter.R;
import ucai.cn.fulicter.activity.MainActivity;
import ucai.cn.fulicter.adapter.BoutiqueAdapter;
import ucai.cn.fulicter.bean.BoutiqueBean;
import ucai.cn.fulicter.net.NetDao;
import ucai.cn.fulicter.net.OkHttpUtils;
import ucai.cn.fulicter.utils.CommonUtils;
import ucai.cn.fulicter.utils.ConvertUtils;
import ucai.cn.fulicter.utils.L;
import ucai.cn.fulicter.view.SpaceItemDecoration;

/**
 * Created by User on 2016/10/19.
 */

public class BotiqueFragment extends Fragment {
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    LinearLayoutManager llm;
    MainActivity mContext;
    BoutiqueAdapter mAdapter;
    ArrayList<BoutiqueBean> mList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext = (MainActivity) getContext();
        mAdapter = new BoutiqueAdapter(mContext,mList);
        initView();
        initData();
        return layout;
    }

    private void initData() {
        downloadBoutique(I.ACTION_DOWNLOAD);
    }

    private void downloadBoutique(final int action) {
        NetDao.downloadBuotique(mContext, new OkHttpUtils.OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                L.e("result="+result);
                if(result!=null && result.length>0){
                    ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                    if(action== I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
                        mAdapter.initData(list);
                    }else{
                        mAdapter.addData(list);
                    }
                    if(list.size()<I.PAGE_SIZE_DEFAULT){
                        mAdapter.setMore(false);
                    }
                }else{
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(false);
                CommonUtils.showShortToast(error);
                L.e("error:"+error);
            }
        });
    }

    private void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        llm = new LinearLayoutManager(mContext);
        mRv.setLayoutManager(llm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }
}


