package ucai.cn.fulicter.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.BreakIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import ucai.cn.fulicter.R;

/**
 * Created by User on 2016/10/19.
 */
public class FooterViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.tvFooter)
    public TextView mtvFooter;
    public BreakIterator mTvFooter;

    public FooterViewHolder(View view){
        super(view);
        ButterKnife.bind(this,view);
    }

}
