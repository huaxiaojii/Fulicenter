package ucai.cn.fulicter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import ucai.cn.fulicter.I;
import ucai.cn.fulicter.R;
import ucai.cn.fulicter.bean.NewGoodsBean;
import ucai.cn.fulicter.utils.ImageLoader;
import ucai.cn.fulicter.utils.MFGT;


public class GoodsAdapter extends Adapter {
    Context mContext;
    ArrayList<NewGoodsBean> mList;
    boolean isMore;


    public GoodsAdapter(ArrayList<NewGoodsBean> list, Context context) {
        mList=new ArrayList<>();
        mList.addAll(list);
        mContext = context;
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
            holder = new GoodsViewHolder(View.inflate(mContext, R.layout.item_goods, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(getItemViewType(position)==I.TYPE_FOOTER){
            FooterViewHolder mGoodsViewHolder= (FooterViewHolder) holder;
            mGoodsViewHolder.tvFooter.setText(String.valueOf(getFootString()));
        }else {
            GoodsViewHolder mGoodsViewHolder= (GoodsViewHolder) holder;
            NewGoodsBean mNewGoodsBean=mList.get(position);
            ImageLoader.downloadImg(mContext,mGoodsViewHolder.ivGoodsThumb,mNewGoodsBean.getGoodsThumb());
            mGoodsViewHolder.tvGoodsName.setText(mNewGoodsBean.getGoodsName());
            mGoodsViewHolder.tvGoodsPrice.setText(mNewGoodsBean.getCurrencyPrice());
            mGoodsViewHolder.layoutGoods.setTag(mNewGoodsBean.getGoodsId());

        }
    }
    public int getFootString() {

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

    public void initData(ArrayList<NewGoodsBean> list) {
        if(mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(ArrayList<NewGoodsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class GoodsViewHolder extends ViewHolder{
        @BindView(R.id.ivGoodsThumb)
        ImageView ivGoodsThumb;
        @BindView(R.id.tvGoodsName)
        TextView tvGoodsName;
        @BindView(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @BindView(R.id.layout_goods)
        LinearLayout layoutGoods;


        GoodsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.layout_goods)
        public void onGoodsItemClick(){
            int goodsId=(int)layoutGoods.getTag();
            MFGT.gotoGoodsDetailActivity(mContext,goodsId);

        }
    }

    static class FooterViewHolder extends ViewHolder{
        @BindView(R.id.tvFooter)
        TextView mtvFooter;
        public BreakIterator tvFooter;

        FooterViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
        }
    }





}
