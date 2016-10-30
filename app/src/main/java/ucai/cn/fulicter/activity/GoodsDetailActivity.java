package ucai.cn.fulicter.activity;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import ucai.cn.fulicter.FuLiCenterApplication;
import ucai.cn.fulicter.I;
import ucai.cn.fulicter.bean.AlbumsBean;
import ucai.cn.fulicter.bean.GoodsDetailsBean;
import ucai.cn.fulicter.bean.MessageBean;
import ucai.cn.fulicter.bean.User;
import ucai.cn.fulicter.net.NetDao;
import ucai.cn.fulicter.net.OkHttpUtils;
import ucai.cn.fulicter.utils.CommonUtils;
import ucai.cn.fulicter.utils.L;
import ucai.cn.fulicter.utils.MFGT;
import ucai.cn.fulicter.view.FlowIndicator;
import ucai.cn.fulicter.view.SlideAutoLoopView;

public class GoodsDetailActivity extends BaseActivity {

    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_good_name_english)
    TextView tvGoodNameEnglish;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_good_price_shop)
    TextView tvGoodPriceShop;
    @BindView(R.id.tv_good_price_current)
    TextView tvGoodPriceCurrent;
    @BindView(R.id.salv)
    SlideAutoLoopView salv;
    @BindView(R.id.indicator)
    FlowIndicator indicator;
    @BindView(R.id.wv_good_brief)
    WebView wvGoodBrief;
    int goodsId;
    GoodsDetailActivity mContext;


    boolean isCollected = false;
    @BindView(R.id.iv_good_collect)
    ImageView ivGoodCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        ButterKnife.bind(this);
        goodsId = getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID, 0);
        L.e("details", "goodsid" + goodsId);
        if (goodsId == 0) {
            finish();
        }
        mContext = this;
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData() {
        NetDao.downloadGoodsDetall(mContext,
                goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
                    @Override
                    public void onSuccess(GoodsDetailsBean result) {
                        L.i("details=" + result);
                        if (result != null) {
                            showGoodsDetails(result);

                        } else {
                            finish();
                        }
                    }


                    @Override
                    public void onError(String error) {
                        finish();
                        L.e("details,error=" + error);
                        CommonUtils.showShortToast(error);

                    }
                });

    }

    private void showGoodsDetails(GoodsDetailsBean detalis) {
        tvGoodNameEnglish.setText(detalis.getGoodsEnglishName());
        tvGoodName.setText(detalis.getGoodsName());
        tvGoodPriceCurrent.setText(detalis.getCurrencyPrice());
        tvGoodPriceShop.setText(detalis.getShopPrice());
        salv.startPlayLoop(indicator, getAlbumImgUrl(detalis), getAlbmImgConut(detalis));
        wvGoodBrief.loadDataWithBaseURL(null, detalis.getGoodsBrief(), I.TEXT_HTML, I.UTF_8, null);

    }

    private int getAlbmImgConut(GoodsDetailsBean details) {
        if (details.getPromotePrice() != null && details.getPromotePrice().length() > 0) {
            return details.getProperties()[0].getAlbums().length;
        }
        return 0;

    }

    private String[] getAlbumImgUrl(GoodsDetailsBean details) {
        String[] urls = new String[]{};
        if (details.getPromotePrice() != null && details.getProperties().length > 0) {
            AlbumsBean[] albums = details.getProperties()[0].getAlbums();
            urls = new String[albums.length];
            for (int i = 0; i < albums.length; i++) {
                urls[i] = albums[i].getImgUrl();
            }

        }
        return urls;
    }

    @Override
    protected void initView() {
    }
    @Override
    protected void onResume(){
        super.onResume();
        isCollected();
    }

    @OnClick(R.id.backClickArea)
    public void onBackClick() {

        MFGT.finish(this);
    }

    public void onback(View v) {
        MFGT.finish(this);}


    @OnClick(R.id.iv_good_collect)
    public void onCollectClick(){
        User user= FuLiCenterApplication.getUser();
        if (user==null){
            MFGT.gotoLogin(mContext);
        }else {
            if (isCollected){
                NetDao.deleteCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null&&result.isSuccess()){
                            isCollected=!isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }else {
                NetDao.addCollect(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result!=null&&result.isSuccess()){
                            isCollected=!isCollected;
                            updateGoodsCollectStatus();
                            CommonUtils.showLongToast(result.getMsg());
                        }

                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        }

    }


    public void isCollected() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            NetDao.isColected(mContext, user.getMuserName(), goodsId, new
                    OkHttpUtils.OnCompleteListener<MessageBean>() {
                        @Override
                        public void onSuccess(MessageBean result) {
                            if (result != null && result.isSuccess()) {
                                isCollected = true;


                            }else {
                                isCollected=false;
                            }
                            updateGoodsCollectStatus();
                        }

                        @Override
                        public void onError(String error) {
                            isCollected=false;
                            updateGoodsCollectStatus();
                        }
                    });
            updateGoodsCollectStatus();
        }
    }

    private void updateGoodsCollectStatus() {
        if (isCollected) {
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_out);
        } else {
            ivGoodCollect.setImageResource(R.mipmap.bg_collect_in);
        }
    }
    @OnClick(R.id.iv_good_cart)
    public void addCart(){
        User user = FuLiCenterApplication.getUser();
        if(user!=null){
            NetDao.addCart(mContext, user.getMuserName(), goodsId, new OkHttpUtils.OnCompleteListener<MessageBean>() {
                @Override
                public void onSuccess(MessageBean result) {
                    if(result!=null && result.isSuccess()){
                        CommonUtils.showLongToast(R.string.add_goods_success);
                    }else {
                        CommonUtils.showLongToast(R.string.add_goods_fail);
                    }
                }
                @Override
                public void onError(String error) {
                    CommonUtils.showLongToast(R.string.add_goods_fail);
                    L.e("error="+error);
                }
            });
        }else{
            MFGT.gotoLogin(mContext);
        }
    }


}