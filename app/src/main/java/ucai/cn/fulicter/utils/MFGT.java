package ucai.cn.fulicter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import ucai.cn.fulicter.I;
import ucai.cn.fulicter.R;
import ucai.cn.fulicter.activity.GoodsDetailActivity;
import ucai.cn.fulicter.activity.MainActivity;
import ucai.cn.fulicter.activity.SplashActivity;


/**
 * Created by Winston on 2016/10/14.
 */
    public class MFGT {
        public static void finish(Activity activity){
            activity.finish();
            activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        public static void gotoMainActivity(SplashActivity context){
            startActivity(context, MainActivity.class);
        }
        public static void startActivity(Activity context,Class<?> cls){
            Intent intent = new Intent();
            intent.setClass(context,cls);
//            context.startActivity(intent);
            context.startActivity(intent);
            context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
        }
    public static void gotoGoodsDetailsActivity(Activity context,int goodsId){
        Intent intent=new Intent();
        intent.setClass(context,GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    public static void startActivity(Activity context,Intent intent){
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }


    public static void gotoGoodsDetailActivity(Context mContext, int goodsId) {

    }

    public static void gotoCategoryChildAvtivity(Context context, int catId){
        Intent intent=new Intent();
        intent, intent.setClass();
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
        startActivity((Activity) context,intent);
    }
        Intent intent=new Intent();
}

