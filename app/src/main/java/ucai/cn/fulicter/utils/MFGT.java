package ucai.cn.fulicter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import ucai.cn.fulicter.I;
import ucai.cn.fulicter.activity.BoutiqueChildActivity;
import ucai.cn.fulicter.activity.CategoryChildActivity;
import ucai.cn.fulicter.activity.GoodsDetailActivity;
import ucai.cn.fulicter.activity.LoginActivity;
import ucai.cn.fulicter.activity.MainActivity;
import ucai.cn.fulicter.activity.ResgisterActivity;
import ucai.cn.fulicter.bean.BoutiqueBean;
import ucai.cn.fulicter.bean.CategoryChildBean;


/**
 * Created by Winston on 2016/10/14.
 */
public class MFGT {
    public static void finish(Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void gotoMainActivity(Activity context){
        startActivity(context, MainActivity.class);
    }
    public static void startActivity(Activity context,Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(context,cls);
        startActivity(context,intent);
    }

    public static void gotoGoodsDetailsActivity(Context context, int goodsId){
        Intent intent = new Intent();
        intent.setClass(context, GoodsDetailActivity.class);
        intent.putExtra(I.GoodsDetails.KEY_GOODS_ID,goodsId);
        startActivity(context,intent);
    }

    public static void startActivity(Context context,Intent intent){
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }


    public static void gotoBoutiqueChildActivity(Context context, BoutiqueBean bean){
        Intent intent = new Intent();
        intent.setClass(context, BoutiqueChildActivity.class);
        intent.putExtra(I.Boutique.CAT_ID,bean);
        startActivity(context,intent);
    }


    public static void gotoCategoryChildActivity(Context context, int catId, String groupName, ArrayList<CategoryChildBean> list){
        Intent intent = new Intent();
        intent.setClass(context, CategoryChildActivity.class);
        intent.putExtra(I.CategoryChild.CAT_ID,catId);
        intent.putExtra(I.CategoryGroup.NAME,groupName);
        intent.putExtra(I.CategoryChild.ID,list);
        startActivity(context,intent);
    }

    public static void gotoLogin(Activity context){
        Intent intent = new Intent();
        intent.setClass(context,LoginActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_LOGIN);
        startActivity(context, LoginActivity.class);
    }
    public static void gotoRegister(Activity context){
        Intent intent= new Intent();
        intent.setClass(context,ResgisterActivity.class);
        startActivityForResult(context,intent,I.REQUEST_CODE_REGISTER);
        startActivity(context, ResgisterActivity.class);
    }
    public static void startActivityForResult(Activity context,Intent intent,int requestCode){
        context.startActivityForResult(intent,requestCode);
        context.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    public static void gotoSettings(MainActivity context) {
        startActivity(context, ResgisterActivity.class);
    }
}




