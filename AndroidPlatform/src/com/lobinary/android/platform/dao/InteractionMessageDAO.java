package com.lobinary.android.platform.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lobinary.android.platform.constants.Constants;
import com.lobinary.android.platform.db.DBHelper;
import com.lobinary.android.platform.db.DBInfo;
import com.lobinary.android.platform.dto.InteractionMessage;

/**
 * 交互信息DAO
 * 
 * @author Lobinary
 * 
 */
public class InteractionMessageDAO {

	private DBHelper helper;
	private SQLiteDatabase db;

	public InteractionMessageDAO(Context context) {
		Log.d(Constants.LOG.LOG_TAG, "数据库管理器：数据库初始化中......");
		helper = new DBHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}

	/**
	 * add InteractionMessages
	 * 
	 * @param InteractionMessages
	 */
	public void add(List<InteractionMessage> interactionMessages) {
		Log.d(Constants.LOG.LOG_TAG, "数据库管理器：正在添加交互信息");
		// 采用事务处理，确保数据完整性
		db.beginTransaction(); // 开始事务
		try {
			for (InteractionMessage interactionMessage : interactionMessages) {
				db.execSQL(
						"INSERT INTO "
								+ DBInfo.TABLE.INTERACTION_MESSAGE.TABLE_INTERACTION_MESSAGE
								+ " VALUES(null, ?, ?, ?, ?, ?, ?, ?)",
						new Object[] {interactionMessage.getSenderId(),
								interactionMessage.getReceiverId(),
								interactionMessage.getMessageType(),
								interactionMessage.getMessageInfo(),
								interactionMessage.getSendDate(),
								interactionMessage.getReceiveDate(),
								interactionMessage.getSendStatus()});
				// 带两个参数的execSQL()方法，采用占位符参数？，把参数值放在后面，顺序对应
				// 一个参数的execSQL()方法中，用户输入特殊字符时需要转义
				// 使用占位符有效区分了这种情况
			}
			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	/**
	 * update InteractionMessage's age
	 * 
	 * @param InteractionMessage
	 */
	public void updateSendStatus(InteractionMessage interactionMessage) {
		Log.d(Constants.LOG.LOG_TAG, "数据库管理器：更新发送状态");
		ContentValues cv = new ContentValues();
		cv.put("sendStatus", interactionMessage.getSendStatus());
		db.update(DBInfo.TABLE.INTERACTION_MESSAGE.TABLE_INTERACTION_MESSAGE, cv, DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID + " = ?",
				new String[] { String.valueOf(interactionMessage.getId()) });
	}

	/**
	 * delete old InteractionMessage
	 * 
	 * @param InteractionMessage
	 */
	public void deleteInteractionMessageById(InteractionMessage interactionMessage) {
		Log.d(Constants.LOG.LOG_TAG, "数据库管理器：正在删除交互信息对象(" + interactionMessage.getId() + ")");
		db.delete(DBInfo.TABLE.INTERACTION_MESSAGE.TABLE_INTERACTION_MESSAGE, DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID + " >= ?",
				new String[] { String.valueOf(interactionMessage.getId()) });
	}

	/**
	 * query all InteractionMessages, return list
	 * 
	 * @return List<InteractionMessage>
	 */
	public List<InteractionMessage> query() {
//		Log.d(Constants.LOG.LOG_TAG, "数据库管理器：正在查询交互信息列表");
		ArrayList<InteractionMessage> interactionMessages = new ArrayList<InteractionMessage>();
		Cursor c = queryTheCursor();
		while (c.moveToNext()) {
			InteractionMessage interactionMessage = new InteractionMessage();
			interactionMessage.setId(c.getInt(c.getColumnIndex(DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID)));
			interactionMessage.setSenderId(c.getInt(c.getColumnIndex(DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID)));
			interactionMessage.setReceiverId(c.getInt(c.getColumnIndex(DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID)));
			interactionMessage.setMessageType(c.getInt(c.getColumnIndex(DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID)));
			interactionMessage.setMessageInfo(c.getString(c.getColumnIndex(DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID)));
			String sendDateStr = c.getString(c.getColumnIndex(DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID));
			interactionMessage.setSendDate(new Date());
			String receiveDateStr = c.getString(c.getColumnIndex(DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID));
//			Log.d(Constants.LOG.LOG_TAG, "sendDateStr：" + sendDateStr);
			interactionMessage.setReceiveDate(new Date());
			interactionMessage.setSendStatus(c.getInt(c.getColumnIndex(DBInfo.TABLE.INTERACTION_MESSAGE.COLUMN_INTERACIONT_MESSAGE_ID)));
			interactionMessages.add(interactionMessage);
		}
		c.close();
		return interactionMessages;
	}

	/**
	 * query all InteractionMessages, return cursor
	 * 
	 * @return Cursor
	 */
	public Cursor queryTheCursor() {
		Log.d(Constants.LOG.LOG_TAG, "数据库管理器：查询cursor指针");
		Cursor c = db.rawQuery("SELECT * FROM " + DBInfo.TABLE.INTERACTION_MESSAGE.TABLE_INTERACTION_MESSAGE,
				null);
		return c;
	}

	/**
	 * close database
	 */
	public void closeDB() {
		Log.d(Constants.LOG.LOG_TAG, "数据库管理器：关闭数据库");
		// 释放数据库资源
		db.close();
	}

}
