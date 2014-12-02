package com.lobinary.platform.db;

import com.lobinary.platform.constants.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *	SQLite数据库总连接
 *
 *	当调用SQLiteOpenHelper的getWritableDatabase()或者getReadableDatabase()方法获取用于操作数据库的SQLiteDatabase实例的时候
 *	如果数据库不存在，Android系统会自动生成一个数据库，接着调用onCreate()方法。
 *	onCreate()方法在初次生成数据库时才会被调用，在onCreate()方法里可以生成数据库表结构及添加一些应用使用到的初始化数据。
 *	onUpgrade()方法在数据库的版本发生变化时会被调用，一般在软件升级时才需改变版本号，而数据库的版本是由程序员控制的。
 *	假设数据库现在的版本是1，由于业务的变更，修改了数据库表结构，这时候就需要升级软件，升级软件时希望更新用户手机里的数据库表结构，为了实现这一目的，可以把原来的数据库版本设置为2，并且在onUpgrade()方法里面实现表结构的更新。
 *	当软件的版本升级次数比较多，这时在onUpgrade()方法里面可以根据原版号和目标版本号进行判断，然后做出相应的表结构及数据更新。
 *
 * @author Lobinary
 *
 */
public class DBHelper extends SQLiteOpenHelper {
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	/**
	 * 数据库实际被创建是在getWritableDatabase()或getReadableDatabase()方法调用时<br/>
	 * CursorFactory设置为null,使用系统默认的工厂类
	 * @param context
	 */
	public DBHelper(Context context){
		super(context,DBInfo.DB.DATEBASE_NAME,null,DBInfo.DB.DATEBASE_VERSION);
	}
	
	/**
	 * 每次打开数据的时候执行
	 */
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		Log.d(Constants.LOG.LOG_TAG, "数据库连接成功！");
	}

	/**
	 *	继承SQLiteOpenHelper类,必须要覆写的三个方法：onCreate(),onUpgrade(),onOpen()
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
        // 执行创建表的SQL语句
        db.execSQL(DBInfo.TABLE.INTERACTION_MESSAGE.CREATE_TABLE_INTERACTION_MESSAGE);
        // 即便程序修改重新运行，只要数据库已经创建过，就不会再进入这个onCreate方法
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 调用时间：如果DATABASE_VERSION值被改为别的数,系统发现现有数据库版本不同,即会调用onUpgrade

        // onUpgrade方法的三个参数，一个 SQLiteDatabase对象，一个旧的版本号和一个新的版本号
        // 这样就可以把一个数据库从旧的模型转变到新的模型
        // 这个方法中主要完成更改数据库版本的操作
        Log.d(Constants.LOG.LOG_TAG, "数据库准备升级！");
        db.execSQL(DBInfo.TABLE.INTERACTION_MESSAGE.DROP_TABLE_INTERACION_MESSAGE);
        onCreate(db);
        // 上述做法简单来说就是，通过检查常量值来决定如何，升级时删除旧表，然后调用onCreate来创建新表
        // 一般在实际项目中是不能这么做的，正确的做法是在更新数据表结构时，还要考虑用户存放于数据库中的数据不丢失

	}

}
