package ucai.cn.fulicter.adapter;


/**
 * Created by User on 2016/10/26.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import ucai.cn.fulicter.view.FooterViewHolder;
import ucai.cn.fulicter.I;
import ucai.cn.fulicter.bean.CollectBean;
import ucai.cn.fulicter.utils.ImageLoader;
import ucai.cn.fulicter.utils.MFGT;



public class CollectsAdapter extends Adapter {
    Context mContext;
    List<CollectBean> mList;
    boolean isMore;
    int soryBy = I.SORT_BY_ADDTIME_DESC;

    public CollectsAdapter(Context context, List<CollectBean> list) {
        mContext = context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public void setSoryBy(int soryBy) {
        this.soryBy = soryBy;
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new ColelctsViewHolder(View.inflate(mContext, R.layout.item_collects, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.mtvFooter.setText(String.valueOf(getFootString()));
        }else{
            ColelctsViewHolder vh = (ColelctsViewHolder) holder;
            CollectBean goods = mList.get(position);
            ImageLoader.downloadImg(mContext,vh.mIvGoodsThumb,goods.getGoodsThumb());
            vh.mTvGoodsName.setText(goods.getGoodsName());
            vh.mLayoutGoods.setTag(goods.getGoodsId());
        }
    }

    private int getFootString() {
        return isMore?R.string.load_more:R.string.no_more;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<CollectBean> list) {
        if(mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<CollectBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class ColelctsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView mIvGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView mTvGoodsName;
        @BindView(R.id.iv_collect_del)
        ImageView mIvCollectDel;
        @BindView(R.id.layout_goods)
        RelativeLayout mLayoutGoods;

        ColelctsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick(){
            int goodsId = (int) mLayoutGoods.getTag();
            MFGT.gotoGoodsDetailsActivity(mContext,goodsId);
        }
    }

}