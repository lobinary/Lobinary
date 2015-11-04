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
import com.lobinary.android.platform.pojo.bean.PinnerListBean;
import com.lobinary.android.platform.ui.listview.PinnedSectionListView.PinnedSectionListAdapter;

/**
 * 
 * @author Lobinary
 *
 */
public class AdapterListView extends BaseAdapter implements PinnedSectionListAdapter{
	private ArrayList<PinnerListBean> list;
	private Context context;
	public ArrayList<PinnerListBean> getList() {
		return list;
	}
	public void setList(ArrayList<PinnerListBean> list) {
		if(list!=null){
			this.list = list;
		}else{
			list=new ArrayList<PinnerListBean>();
		}
	}
	public AdapterListView(Context context,ArrayList<PinnerListBean> list){
		this.setList(list);
		this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public PinnerListBean getItem(int position) {
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
		PinnerListBean bean=getItem(position);
		vh.company_item.setText(bean.text);
		vh.image.setImageDrawable(converView.getResources().getDrawable(bean.imgId));
		if (bean.type == PinnerListBean.SECTION) {
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
		return viewType == PinnerListBean.SECTION;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public int getItemViewType(int position) {
		return ((PinnerListBean)getItem(position)).type;
	}
	public void refresh(ArrayList<PinnerListBean> arr){
        setList(arr);
        notifyDataSetChanged();
    }

}
