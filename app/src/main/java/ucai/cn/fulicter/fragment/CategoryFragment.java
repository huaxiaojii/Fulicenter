package ucai.cn.fulicter.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ucai.cn.fulicter.R;
import ucai.cn.fulicter.activity.MainActivity;
import ucai.cn.fulicter.adapter.CategoryAdapter;
import ucai.cn.fulicter.bean.CategoryChildBean;
import ucai.cn.fulicter.bean.CategoryGroupBean;
import ucai.cn.fulicter.net.NetDao;
import ucai.cn.fulicter.net.OkHttpUtils;
import ucai.cn.fulicter.utils.ConvertUtils;
import ucai.cn.fulicter.utils.L;

/**
 * Created by User on 2016/10/20.
 */

public class CategoryFragment extends BaseFragment {
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;

    CategoryAdapter mAdapter;
    MainActivity mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;


    int groupCount;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, layout);
        mContext= (MainActivity) getContext();
        mChildList=new ArrayList<>();
        mGroupList=new ArrayList<>();
        mAdapter=new CategoryAdapter(mContext,mGroupList,mChildList);
        return layout;
    }

    @Override
    protected void initView() {
        elvCategory.setGroupIndicator(null);
        elvCategory.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        downloadGroup();

    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(mContext, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                L.e("downloadGroup,result="+result);
                if (result!=null && result.length>0){
                    ArrayList<CategoryGroupBean> grouppList = ConvertUtils.array2List(result);
                    L.e("groupList="+grouppList.size());
                    mGroupList.addAll(grouppList);
                   for (int i=0;i<grouppList.size();i++){
                       mChildList.add(new ArrayList<CategoryChildBean>());
                       CategoryGroupBean g=grouppList.get(i);
                       downloadChild(g.getId());
//                    for (CategoryGroupBean g:grouppList){
//                        downloadChild(g.getId());
                    }
                }
            }

            @Override
            public void onError(String error) {
                L.e("error=" +error);

            }
        });
    }

    private void downloadChild(int id) {
        NetDao.downloadCategoryChild(mContext, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                L.e("downloadChild,result="+result);
                if (result!= null && result.length>0){
                    ArrayList<CategoryChildBean> childList =ConvertUtils.array2List(result);
                    L.e("childList="+childList.size());
                    int index = 0;
                    mChildList.addAll(index,childList);
                }
                if (groupCount==mGroupList.size()){
                    mAdapter.initData(mGroupList,mChildList);
                }
            }

            @Override
            public void onError(String error) {
                L.e("error="+error);
            }
        });
    }

    @Override
    protected void setListener() {

    }
}
