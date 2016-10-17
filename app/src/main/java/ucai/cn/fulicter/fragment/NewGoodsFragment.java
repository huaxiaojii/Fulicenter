package ucai.cn.fulicter.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import cn.ucai.xm_fulicenter.Activity.MainActivity;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.adapter.GoodsAdapter;
import cn.ucai.xm_fulicenter.bean.NewGoodsBean;
import cn.ucai.xm_fulicenter.net.NetDao;
import cn.ucai.xm_fulicenter.net.OkHttpUtils;
import cn.ucai.xm_fulicenter.utils.ConvertUtils;
import cn.ucai.xm_fulicenter.utils.L;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_newgoods, container, false);
        ButterKnife.bind(this, layout);
        mcontext= (MainActivity) getContext();
        mlist = new ArrayList<>();
        mAdapter=new GoodsAdapter(mlist,mcontext);
        initView();
        initData();
        return layout;
    }

    private void initData() {
        NetDao.downloadNewGoods(mcontext, pageId, new OkHttpUtils.OnCompleteListener<NewGoodsBean[]>() {
            @Override
            public void onSuccess(NewGoodsBean[] result) {
                if (result!= null && result.length > 0) {
                    ArrayList<NewGoodsBean> List = ConvertUtils.array2List(result);
                    mAdapter.initData(List);
                }
            }

            @Override
            public void onError(String error) {
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
    }
}
