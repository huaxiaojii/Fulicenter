package ucai.cn.fulicter.fragment;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import ucai.cn.fulicter.adapter.BaseFragment;
import ucai.cn.fulicter.adapter.GoodsAdapter;
import ucai.cn.fulicter.bean.NewGoodsBean;
import ucai.cn.fulicter.net.NetDao;;
import ucai.cn.fulicter.net.OkHttpUtils;
import ucai.cn.fulicter.utils.ConvertUtils;
import ucai.cn.fulicter.utils.L;
import ucai.cn.fulicter.view.SpaceItemDecoration;



/**
 * Created by yanglei on 2016/10/17.
 */

public class NewGoodsFragment extends BaseFragment {
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    @BindView(R.id.rvNewGoods)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;

    MainActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId = 1;
    GridLayoutManager glm;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        L.e("NewGoodsFragment.onCreateView");
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mContext= (MainActivity) getContext();
//        mContext = (MainActivity) getContext();
        mList = new ArrayList<>();
        mAdapter=new GoodsAdapter(mContext,mList);
//        mAdapter = new GoodsAdapter(mContext,mList);
        super.onCreateView(inflater,container,savedInstanceState);
        return layout;
    }

    @Override
    protected void setListener() {
        setPullUpListener();
        setPullDownListener();
    }

    private void setPullDownListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSrl.setRefreshing(true);
                mTvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadNewGoods(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void downloadNewGoods(final int action) {
        NetDao.downloadNewGoods(mContext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                mSrl.setRefreshing(false);
                mTvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                L.e("result="+result);
                if(result!=null && result.length>0){
                    ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                    if(action==I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN) {
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

    private void setPullUpListener() {
        mRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if(newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == mAdapter.getItemCount()-1
                        && mAdapter.isMore()){
                    pageId++;
                    downloadNewGoods(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                mSrl.setEnabled(firstPosition==0);
            }
        });
    }

    @Override
    protected void initData() {
        downloadNewGoods(I.ACTION_DOWNLOAD);
    }

    @Override
    protected void initView() {
        mSrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        mRv.setLayoutManager(glm);
        mRv.setHasFixedSize(true);
        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(new SpaceItemDecoration(12));
    }
}
//public class NewGoodsFragment extends Fragment {
////    public class NewGoodsFragment extends Fragment {
//
//    @BindView(R.id.tv_refresh)
//    TextView mtvRefresh;
//    @BindView(R.id.rv)
//    RecyclerView mrv;
//    @BindView(R.id.srl)
//    SwipeRefreshLayout msrl;
//
//    MainActivity mcontext;
//    GoodsAdapter mAdapter;
//    ArrayList<NewGoodsBean> mlist;
//    int pageId=1;
//    boolean isMore;
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
//        ButterKnife.bind(this, layout);
//        mlist = new ArrayList<>();
//        mAdapter=new GoodsAdapter(mlist,mcontext);
//        initView();
//        initData();
//        setlistener();
//        return layout;
//
//    }
//
//    private void setlistener() {
//        setPullUplistener();
//        setPullDownlistener();
//    }
//
//    private void setPullUplistener() {
//    }
//
//
//    private void setPullDownlistener() {
//
//    }
//
//
//    private void initData() {
//        NetDao.downloadNewGoods(mcontext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
//            @Override
//            public void onSuccess(NewGoodsBean[] result) {
//                msrl.setRefreshing(false);
//                mtvRefresh.setVisibility(View.GONE);
//                mAdapter.setMore(true);
//                L.e("result="+result);
//                if (result!= null && result.length > 0) {
//
//                }else {
//                    mAdapter.setMore(false);
//                }
//            }
//
//            @Override
//            public void onError(String error) {
//                msrl.setRefreshing(false);
//                mtvRefresh.setVisibility(View.GONE);
//                L.e("error"+error);
//            }
//        });
//    }
//
//    private void initView() {
//        msrl.setColorSchemeColors(
//                getResources().getColor(R.color.google_blue),
//                getResources().getColor(R.color.google_green),
//                getResources().getColor(R.color.google_red),
//                getResources().getColor(R.color.google_yellow)
//        );
//        GridLayoutManager glm = new GridLayoutManager(mcontext, I.COLUM_NUM);
//        mrv.setLayoutManager(glm);
//        mrv.setHasFixedSize(true);
//        mrv.setAdapter(mAdapter);
//        mrv.addItemDecoration(new SpaceItemDecoration(12));
//    }
//}
