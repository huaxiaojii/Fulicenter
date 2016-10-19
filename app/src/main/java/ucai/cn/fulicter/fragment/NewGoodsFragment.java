package ucai.cn.fulicter.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import ucai.cn.fulicter.adapter.GoodsAdapter;
import ucai.cn.fulicter.bean.NewGoodsBean;
import ucai.cn.fulicter.net.NetDao;
import ucai.cn.fulicter.net.OkHttpUtils;
import ucai.cn.fulicter.utils.L;
import ucai.cn.fulicter.view.SpaceItemDecoration;



/**
 * Created by yanglei on 2016/10/17.
 */
public class NewGoodsFragment extends Fragment {

    @BindView(R.id.tv_refresh)
    TextView mtvRefresh;
    @BindView(R.id.rv)
    RecyclerView mrv;
    @BindView(R.id.srl)
    SwipeRefreshLayout msrl;

    MainActivity mcontext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mlist;
    int pageId=1;
    boolean isMore;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mlist = new ArrayList<>();
        mAdapter=new GoodsAdapter(mlist,mcontext);
        initView();
        initData();
        setlistener();
        return layout;

    }

    private void setlistener() {
        setPullUplistener();
        setPullDownlistener();
    }

    private void setPullUplistener() {
    }


    private void setPullDownlistener() {

    }


    private void initData() {
        NetDao.downloadNewGoods(mcontext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                msrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(true);
                L.e("result="+result);
                if (result!= null && result.length > 0) {

                }else {
                    mAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                msrl.setRefreshing(false);
                mtvRefresh.setVisibility(View.GONE);
                L.e("error"+error);
            }
        });
    }

    private void initView() {
        msrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        GridLayoutManager glm = new GridLayoutManager(mcontext, I.COLUM_NUM);
        mrv.setLayoutManager(glm);
        mrv.setHasFixedSize(true);
        mrv.setAdapter(mAdapter);
        mrv.addItemDecoration(new SpaceItemDecoration(12));
    }
}
