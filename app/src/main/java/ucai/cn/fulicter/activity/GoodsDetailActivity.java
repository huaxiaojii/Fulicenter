package ucai.cn.fulicter.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ucai.cn.fulicter.R;
import ucai.cn.fulicter.bean.GoodsDetailsBean;
import ucai.cn.fulicter.net.NetDao;
import ucai.cn.fulicter.net.OkHttpUtils;
import ucai.cn.fulicter.utils.Commonutils;
import ucai.cn.fulicter.utils.L;

public class GoodsDetailActivity extends AppCompatActivity {
    int goodsId;
    GoodsDetailActivity mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        int goodsId=getIntent().getIntExtra(I.GoodsDetails.KEY_GOODS_ID,0);
        L.e("details","goodsid"+goodsId);
        if (goodsId==0){
            finish();
        }
        mContext=this;
        initView();
        initData();
        setListener();

    }

    private void initData() {
        NetDao.downloadGoodsDetail(mContext,goodsId, new OkHttpUtils.OnCompleteListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean result) {
                L.i("details="+result);
                if (result!=null){
                    showGoodDetails(result);
                }else {
                    finish();
                }

            }

            @Override
            public void onError(String error) {
                finish();
                L.e("details,error="+error);
                Commonutils.showShortToast(error);
            }
        });
    }

    private void showGoodDetails(GoodsDetailsBean result) {
        mTvGoodNameEnglish.setText(detail.getGoodsEnglishNmae());
        mTvGoodName.setText(details.getGoodsName());
        mTvGooodPriceCurrnet.setText(details.getCurrencyPrice());
        mTvGoodPriceShop.setText(details.getShopPrice());
    }

    private void initView() {

    }

    private void setListener() {
    }


}
