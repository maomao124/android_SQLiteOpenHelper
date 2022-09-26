package mao.android_sqliteopenhelper.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Project name(项目名称)：android_SQLiteOpenHelper
 * Package(包名): mao.android_sqliteopenhelper.dao
 * Class(类名): DBHelper
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/9/26
 * Time(创建时间)： 22:08
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class DBHelper extends SQLiteOpenHelper
{

    /**
     * 数据库名字
     */
    private static final String DB_NAME = "login.db";

    /**
     * 表名
     */
    private static final String TABLE_NAME = "login_info";

    /**
     * 数据库版本
     */
    private static final int DB_VERSION = 1;

    /**
     * 实例，单例模式，懒汉式，双重检查锁方式
     */
    private static volatile DBHelper dbHelper = null;

    /**
     * 读数据库
     */
    private SQLiteDatabase readDatabase;
    /**
     * 写数据库
     */
    private SQLiteDatabase writeDatabase;


    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public DBHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * 获得实例
     *
     * @param context 上下文
     * @return {@link DBHelper}
     */
    public static DBHelper getInstance(Context context)
    {
        if (dbHelper == null)
        {
            synchronized (DBHelper.class)
            {
                if (dbHelper == null)
                {
                    dbHelper = new DBHelper(context);
                }
            }
        }
        return dbHelper;
    }

    /**
     * 打开读连接
     *
     * @return {@link SQLiteDatabase}
     */
    public SQLiteDatabase openReadConnection()
    {
        if (readDatabase == null || !readDatabase.isOpen())
        {
            readDatabase = dbHelper.getReadableDatabase();
        }
        return readDatabase;
    }

    /**
     * 打开写连接
     *
     * @return {@link SQLiteDatabase}
     */
    public SQLiteDatabase openWriteConnection()
    {
        if (writeDatabase == null || !writeDatabase.isOpen())
        {
            writeDatabase = dbHelper.getWritableDatabase();
        }
        return readDatabase;
    }

    /**
     * 关闭数据库读连接和写连接
     */
    public void closeConnection()
    {
        if (readDatabase != null && readDatabase.isOpen())
        {
            readDatabase.close();
            readDatabase = null;
        }

        if (writeDatabase != null && writeDatabase.isOpen())
        {
            writeDatabase.close();
            writeDatabase = null;
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "id LONG PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " age INTEGER NOT NULL," +
                " weight FLOAT NOT NULL)";
        db.execSQL(sql);
    }

    /**
     * 数据库版本更新时触发回调
     *
     * @param db         SQLiteDatabase
     * @param oldVersion 旧版本
     * @param newVersion 新版本
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }



}
