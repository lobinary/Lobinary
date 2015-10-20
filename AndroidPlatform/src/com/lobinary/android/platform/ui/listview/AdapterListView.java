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
import com.lobinary.android.platform.pojo.bean.FeaturesQueryListBean;
import com.lobinary.android.platform.ui.listview.PinnedSectionListView.PinnedSectionListAdapter;
/*
 * 
 * This is company of adapter
 * 
 * @author YangWenlong
 */
public class AdapterListView extends BaseAdapter implements PinnedSectionListAdapter{
	private ArrayList<FeaturesQueryListBean> list;
	private Context context;
	public ArrayList<FeaturesQueryListBean> getList() {
		return list;
	}
	public void setList(ArrayList<FeaturesQueryListBean> list) {
		if(list!=null){
			this.list = list;
		}else{
			list=new ArrayList<FeaturesQueryListBean>();
		}
	}
	public AdapterListView(Context context,ArrayList<FeaturesQueryListBean> list){
		this.setList(list);
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public FeaturesQueryListBean getItem(int position) {
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
		FeaturesQueryListBean bean=getItem(position);
		vh.company_item.setText(bean.text);
		if (bean.type == FeaturesQueryListBean.SECTION) {
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
		return viewType == FeaturesQueryListBean.SECTION;//0�Ǳ��⣬1������
	}

	@Override
	public int getViewTypeCount() {
		return 2;//2��view������ baseAdapter�е÷���
	}
	@Override
	public int getItemViewType(int position) {
		return ((FeaturesQueryListBean)getItem(position)).type;
	}
	public void refresh(ArrayList<FeaturesQueryListBean> arr){
        setList(arr);
        notifyDataSetChanged();
    }

}
