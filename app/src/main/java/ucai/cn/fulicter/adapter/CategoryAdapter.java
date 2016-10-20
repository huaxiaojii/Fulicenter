package ucai.cn.fulicter.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.acl.Group;
import java.text.BreakIterator;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ucai.cn.fulicter.R;
import ucai.cn.fulicter.bean.CategoryChildBean;
import ucai.cn.fulicter.bean.CategoryGroupBean;
import ucai.cn.fulicter.utils.ImageLoader;

/**
 * Created by User on 2016/10/20.
 */

public class CategoryAdapter extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<CategoryGroupBean> mGroupList;
    ArrayList<ArrayList<CategoryChildBean>> mChildList;

    public CategoryAdapter(Context mContext, ArrayList<CategoryGroupBean> mGroupList, ArrayList<ArrayList<CategoryChildBean>> mChildList) {
        mContext = mContext;
        mGroupList = new ArrayList<>();
        mGroupList.addAll(mGroupList);
        mChildList = new ArrayList<>();
        mChildList.addAll(mChildList);
    }

    @Override
    public int getGroupCount() {
        return mGroupList != null ? mGroupList.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).size() : 0;
    }

    @Override
    public CategoryGroupBean getGroup(int groupPosition) {
        return mGroupList != null ? mGroupList.get(groupPosition) : null;
    }

    @Override
    public CategoryChildBean getChild(int groupPosition, int childPosition) {
        return mChildList != null && mChildList.get(groupPosition) != null ?
                mChildList.get(groupPosition).get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getGroupView(int groupPosition, int childPosition,boolean isExpand, View view, ViewGroup parent) {
        ChildViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_child, null);
            holder=new ChildViewHolder(view);
            view.setTag(holder);
        }else {
            view.getTag();
            holder= (ChildViewHolder) view.getTag();
        }
        CategoryChildBean child =getChild(groupPosition,childPosition);
        if (child!=null){
            ImageLoader.downloadImg(mContext,holder.ivGroupThumb,child.getImageUrl());
            holder.mtvCategoryChildName.setText(child.getName());
            holder.ivIndicator.setImageResource(isExpand?R.mipmap.expand_off:R.mipmap.expand_on);
        }
        return view;
    }


//    public View getGroupView(int groupPosition, int childPosition,boolean isExpand, View view, ViewGroup parent) {
//        ChildViewHolder holder;
//        if (view == null) {
//            view = View.inflate(mContext, R.layout.item_category_child, null);
//            holder=new ChildViewHolder(view);
//            view.setTag(holder);
//        }else {
//            view.getTag();
//            holder= (ChildViewHolder) view.getTag();
//        }
//       CategoryChildBean child =getChild(groupPosition,childPosition);
//        if (child!=null){
//            ImageLoader.downloadImg(mContext,holder.ivGroupThumb,child.getImageUrl());
//            holder.mtvCategoryChildName.setText(child.getName());
//            holder.ivIndicator.setImageResource(isExpand?R.mipmap.expand_off:R.mipmap.expand_on);
//        }
//        return view;
//    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpand, View view, ViewGroup parent) {
        GroupViewHolder holder;
        if (view == null) {
            view = View.inflate(mContext, R.layout.item_category_group, null);
            holder=new GroupViewHolder(view);
            view.setTag(holder);
        }else {
            view.getTag();
            holder= (GroupViewHolder) view.getTag();
        }
        CategoryGroupBean group =getGroup(groupPosition);
        if (group!=null){
            ImageLoader.downloadImg(mContext,holder.ivGroupThumb,group.getImageUrl());
            holder.tvGroupName.setText(group.getName());
            holder.ivIndicator.setImageResource(isExpand?R.mipmap.arrow2_up:R.mipmap.arrow2_down);
        }
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

     class GroupViewHolder {
        @BindView(R.id.iv_group_thumb)
        ImageView ivGroupThumb;
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;
        @BindView(R.id.iv_indicator)
        ImageView ivIndicator;

         GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
        class ChildViewHolder {
            @BindView(R.id.iv_group_thumb)
            ImageView ivGroupThumb;
            @BindView(R.id.tv_group_name)
            TextView tvGroupName;
            @BindView(R.id.iv_indicator)
            ImageView ivIndicator;
            public BreakIterator mtvCategoryChildName;

            ChildViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
}
