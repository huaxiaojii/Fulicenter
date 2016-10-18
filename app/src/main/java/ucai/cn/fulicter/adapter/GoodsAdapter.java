package ucai.cn.fulicter.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.xm_fulicenter.I;
import cn.ucai.xm_fulicenter.R;
import cn.ucai.xm_fulicenter.bean.NewGoodsBean;
import cn.ucai.xm_fulicenter.utils.ImageLoader;
import ucai.cn.fulicter.utils.MFGT;

/**
 * Created by yanglei on 2016/10/17.
 */
public class GoodsAdapter extends Adapter {
    List<NewGoodsBean> mList;
    Context mcontext;
    boolean isMore;

    public GoodsAdapter(List<NewGoodsBean> list, Context context) {
        mList = list;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public boolean isMore(){
        return isMore;
    }
    public void setMore(boolean more){
        isMore=more;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mcontext, R.layout.item_footer, null));
        } else {
            holder = new GoodsViewHolder(View.inflate(mcontext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
                FooterViewHolder vh= (FooterViewHolder) holder;
            vh.mTvFooter.setText(getFootString());
        } else {
            GoodsViewHolder vh= (GoodsViewHolder) holder;
            NewGoodsBean goods = mList.get(position);
            ImageLoader.downloadImg(mcontext, vh.mivGoodsThumd, goods.getGoodsThumb());
            vh.mtvGoodsName.setText(goods.getGoodsName());
            vh.mtvGoodsPrice.setText(goods.getCurrencyPrice());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<NewGoodsBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFootString() {

        return isMore?R.String.load_more.String;
    }


    static class GoodsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumd)
        ImageView mivGoodsThumd;
        @BindView(R.id.tvGoodsName)
        TextView mtvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView mtvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout mlayoutGoods;
        private Activity mContext;

        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick(){
            int goodsId= (int) mlayoutGoods.getTag();
            
            MFGT.gotoGoodsDetailsActivity(mContext,goodsId);
        }
    }

    static class FooterViewHolder extends ViewHolder{
        @BindView(R.id.tvFooter)
        TextView tvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
