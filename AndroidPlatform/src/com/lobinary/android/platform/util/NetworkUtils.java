package com.lobinary.android.platform.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.TextView;

public class NetworkUtils {

	/**
	 * 判断是否有网络
	 * 
	 * @param context
	 * @return true 有网络
	 */
	public static boolean networkExist(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfoArray = connectivityManager.getAllNetworkInfo();
		if (networkInfoArray != null) {
			for (NetworkInfo networkInfo : networkInfoArray) {
				if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 设置网络
	 * @param context
	 */
	public static void setNetworkDialog(final Context context) {
		TextView message = new TextView(context);
		message.setText("当前网络不可用，请检查网络设置！");
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context).setTitle("网络设置").setView(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
		alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.create().show();
	}
	

	/**
	 * 设置网络,点击取消关闭系统
	 * @param context
	 */
	public static void setNetworkDialogExit(final Context context) {
		TextView message = new TextView(context);
		message.setText("当前网络不可用，请检查网络设置！");
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context).setTitle("网络设置").setView(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
					}
				});
		alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.exit(0);
			}
		});
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.create().show();
	}
}
