package com.lobinary.android.platform.ui.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lobinary.android.common.service.communication.CommunicationServiceInterface;
import com.lobinary.android.common.util.PropertiesUtil;
import com.lobinary.android.common.util.factory.CommonFactory;
import com.lobinary.android.platform.R;
import com.lobinary.android.platform.pojo.bean.ButtonListenerBean;
import com.lobinary.android.platform.pojo.bean.PinnerListBean;
import com.lobinary.android.platform.ui.activity.LogActivity;
import com.lobinary.android.platform.ui.activity.MusicActivity;
import com.lobinary.android.platform.util.AndroidNetUtil;

@SuppressLint("UseSparseArrays")
public class MainContentHomeFragment extends Fragment {
	
	Logger logger = LoggerFactory.getLogger(MainContentHomeFragment.class);
	
	View maintContentHomeBtn1;
	View maintContentHomeBtn2;
	View maintContentHomeBtn3;
	View maintContentHomeBtn4;

	private TextView homeBtnText1;
	private TextView homeBtnText2;
	private TextView homeBtnText3;
	private TextView homeBtnText4;

	private ImageView homeBtnImg1;
	private ImageView homeBtnImg2;
	private ImageView homeBtnImg3;
	private ImageView homeBtnImg4;
	
	public static List<View> btnList = new ArrayList<View>();
	public static List<ImageView> btnImgList = new ArrayList<ImageView>();
	public static List<TextView> btnTextList = new ArrayList<TextView>();
	public static List<PinnerListBean> homeBtnList;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_content_home_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initialView();
		addListener();
	}

	@SuppressWarnings("unchecked")
	private void initialView() {
		maintContentHomeBtn1 = getActivity().findViewById(R.id.maintContentHomeBtn1);
		maintContentHomeBtn2 = getActivity().findViewById(R.id.maintContentHomeBtn2);
		maintContentHomeBtn3 = getActivity().findViewById(R.id.maintContentHomeBtn3);
		maintContentHomeBtn4 = getActivity().findViewById(R.id.maintContentHomeBtn4);

		homeBtnText1 = (TextView) getActivity().findViewById(R.id.homeBtnText1);
		homeBtnText2 = (TextView) getActivity().findViewById(R.id.homeBtnText2);
		homeBtnText3 = (TextView) getActivity().findViewById(R.id.homeBtnText3);
		homeBtnText4 = (TextView) getActivity().findViewById(R.id.homeBtnText4);

		homeBtnImg1 = (ImageView) getActivity().findViewById(R.id.homeBtnImg1);
		homeBtnImg2 = (ImageView) getActivity().findViewById(R.id.homeBtnImg2);
		homeBtnImg3 = (ImageView) getActivity().findViewById(R.id.homeBtnImg3);
		homeBtnImg4 = (ImageView) getActivity().findViewById(R.id.homeBtnImg4);

		btnList.add(maintContentHomeBtn1);
		btnList.add(maintContentHomeBtn2);
		btnList.add(maintContentHomeBtn3);
		btnList.add(maintContentHomeBtn4);

		btnImgList.add(homeBtnImg1);
		btnImgList.add(homeBtnImg2);
		btnImgList.add(homeBtnImg3);
		btnImgList.add(homeBtnImg4);

		btnTextList.add(homeBtnText1);
		btnTextList.add(homeBtnText2);
		btnTextList.add(homeBtnText3);
		btnTextList.add(homeBtnText4);
		
		homeBtnList =  (List<PinnerListBean>) PropertiesUtil.getFileValue("homeBtnList");
		if(homeBtnList==null){
			initialHomeBtnListInFile();
		}else {
			for (int i = 0; i < homeBtnList.size(); i++) {
				btnTextList.get(i).setText(homeBtnList.get(i).text);
				btnImgList.get(i).setImageDrawable(getResources().getDrawable(homeBtnList.get(i).imgId));
			}
		}
	}

	private void initialHomeBtnListInFile() {
		homeBtnList = new ArrayList<PinnerListBean>();
		homeBtnList.add(new PinnerListBean(PinnerListBean.ITEM, "音乐",R.drawable.music,PinnerListBean.ACTIVITY_INTENT_NEED_CLIENT,null,MusicActivity.class));
		homeBtnList.add(new PinnerListBean(PinnerListBean.ITEM, "关机",R.drawable.unknow,PinnerListBean.BASE_REMOTE_METHOD,"shutDown",null));
		homeBtnList.add(new PinnerListBean(PinnerListBean.ITEM, "取消关机",R.drawable.unknow,PinnerListBean.BASE_REMOTE_METHOD,"cancelShutDown",null));
		homeBtnList.add(new PinnerListBean(PinnerListBean.ITEM, "日志页面",R.drawable.unknow,PinnerListBean.ACTIVITY_INTENT_NOT_NEED_CLIENT,null,LogActivity.class));
		PropertiesUtil.saveFileValue("homeBtnList", homeBtnList);
	}

	private void addListener() {
		setLisener(maintContentHomeBtn1);
		setLisener(maintContentHomeBtn2);
		setLisener(maintContentHomeBtn3);
		setLisener(maintContentHomeBtn4);
	}

	/**
	 * 
	 * @param view
	 */
	public void setLisener(final View view){
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = v.getId();
				PinnerListBean pinnerListBean = null;
				if(v.getId()==R.id.maintContentHomeBtn1){
					pinnerListBean = homeBtnList.get(0);
				}else if(v.getId()==R.id.maintContentHomeBtn2){
					pinnerListBean = homeBtnList.get(1);
				}else if(v.getId()==R.id.maintContentHomeBtn3){
					pinnerListBean = homeBtnList.get(2);
				}else if(v.getId()==R.id.maintContentHomeBtn4){
					pinnerListBean = homeBtnList.get(3);
				}
				Message msg = new Message();
				List<Object> paramList = new ArrayList<Object>();
				paramList.add(getActivity());
				paramList.add(pinnerListBean);
				msg.obj = paramList;
				FeaturesRightFragment.invokeMethodHandler.sendMessage(msg);
			}
		});
	}
	
	public static List<PinnerListBean> getHomeBtnsList(){
		return homeBtnList;
	}

	/**
	 * 替换主页按钮
	 * @param img 
	 * @param charSequence
	 * @param text
	 */
	public static void replaceHomeBtn(PinnerListBean oldBtn, PinnerListBean newBtn, Drawable img) {
		for (int i = 0; i < homeBtnList.size(); i++) {
			if(homeBtnList.get(i).text.equals(oldBtn.text)){
				btnTextList.get(i).setText(newBtn.text);
				btnImgList.get(i).setImageDrawable(img);
				homeBtnList.set(i, newBtn);
				PropertiesUtil.saveFileValue("homeBtnList", homeBtnList);
				break;
			}
		}
		
	}
	
	
	
}
