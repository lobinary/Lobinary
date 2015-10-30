package com.lobinary.android.platform.ui.listview;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lobinary.android.platform.R;
import com.lobinary.android.platform.pojo.bean.FeaturesListBaseBean;
import com.lobinary.android.platform.pojo.bean.FeaturesListBaseBean;
import com.lobinary.android.platform.ui.listview.PinnedSectionListView.PinnedSectionListAdapter;

/**
 * 
 * @author Lobinary
 *
 */
public class AdapterListView extends BaseAdapter implements PinnedSectionListAdapter{
	private ArrayList<FeaturesListBaseBean> list;
	private Context context;
	public ArrayList<FeaturesListBaseBean> getList() {
		return list;
	}
	public void setList(ArrayList<FeaturesListBaseBean> list) {
		if(list!=null){
			this.list = list;
		}else{
			list=new ArrayList<FeaturesListBaseBean>();
		}
	}
	public AdapterListView(Context context,ArrayList<FeaturesListBaseBean> list){
		this.setList(list);
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public FeaturesListBaseBean getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View converView, ViewGroup viewGrop) {
		// TODO Auto-generated method stub
		ViewHolder vh=null;
		if(converView==null){
			vh=new ViewHolder();
			converView=LayoutInflater.from(context).inflate(R.layout.features_item, null);
			vh.company_item=(TextView)converView.findViewById(R.id.title);
			vh.image=(ImageView)converView.findViewById(R.id.imageView1);
			converView.setTag(vh);
		}else{
			vh=(ViewHolder) converView.getTag();
		}
		FeaturesListBaseBean bean=getItem(position);
		vh.company_item.setText(bean.text);
		if (bean.type == FeaturesListBaseBean.SECTION) {
			vh.company_item.setBackgroundResource(R.drawable.features_list_title_bg);
			vh.image.setVisibility(View.GONE);
			
		}else{
			vh.company_item.setBackgroundResource(R.drawable.features_list_item_bg);
			vh.image.setVisibility(View.VISIBLE);
		}
		return converView;
	}
	@Override
	public boolean isItemViewTypePinned(int viewType) {
		// TODO Auto-generated method stub
		return viewType == FeaturesListBaseBean.SECTION;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public int getItemViewType(int position) {
		return ((FeaturesListBaseBean)getItem(position)).type;
	}
	public void refresh(ArrayList<FeaturesListBaseBean> arr){
        setList(arr);
        notifyDataSetChanged();
    }

}
