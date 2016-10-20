package ucai.cn.fulicter.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ucai.cn.fulicter.I;
import ucai.cn.fulicter.R;
import ucai.cn.fulicter.adapter.GoodsAdapter;
import ucai.cn.fulicter.bean.NewGoodsBean;
import ucai.cn.fulicter.view.SpaceItemDecoration;

public class CategoryChildActivity extends BaseActivity {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;

    CategoryChildActivity mContext;
    GoodsAdapter mAdapter;
    ArrayList<NewGoodsBean> mList;
    int pageId=1;
    GridLayoutManager glm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_child);
        ButterKnife.bind(this);
        mList=new ArrayList<>();
        mAdapter=new GoodsAdapter(mContext,mList);
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
        mTvCommonTitle.setText(boutique.getTitle());

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @OnClick(R.id.backClickArea)
    public void onClick() {
    }
}
