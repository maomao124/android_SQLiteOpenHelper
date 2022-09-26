package mao.android_sqliteopenhelper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import mao.android_sqliteopenhelper.entity.Student;

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

    private static final String TAG = "DBHelper";


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
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
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

    /**
     * 查询所有
     *
     * @return {@link List}<{@link Student}>
     */
    public List<Student> queryAll()
    {
        List<Student> list = new ArrayList<>();

        Cursor cursor = readDatabase.query(TABLE_NAME, null, "1=1", new String[]{}, null, null, null);

        while (cursor.moveToNext())
        {
            Student student = new Student.Builder()
                    .setId(cursor.getLong(0))
                    .setName(cursor.getString(1))
                    .setAge(cursor.getInt(2))
                    .setWeight(cursor.getFloat(3))
                    .build();
            list.add(student);
        }

        cursor.close();
        return list;
    }

    /**
     * 插入
     *
     * @param student 学生
     * @return boolean
     */
    public boolean insert(Student student)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.getId());
        contentValues.put("name", student.getName());
        contentValues.put("age", student.getAge());
        contentValues.put("weight", student.getWeight());
        long insert = writeDatabase.insert(TABLE_NAME, null, contentValues);
        return insert > 0;
    }

    /**
     * 插入多条数据
     *
     * @param list 列表
     * @return boolean
     */
    public boolean insert(List<Student> list)
    {
        try
        {
            writeDatabase.beginTransaction();
            for (Student student : list)
            {
                boolean insert = this.insert(student);
                if (!insert)
                {
                    throw new Exception();
                }
            }
            writeDatabase.setTransactionSuccessful();
            return true;
        }
        catch (Exception e)
        {
            writeDatabase.endTransaction();
            Log.e(TAG, "insert: ", e);
            return false;
        }
    }

    /**
     * 更新
     *
     * @param student 学生
     * @return boolean
     */
    public boolean update(Student student)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.getId());
        contentValues.put("name", student.getName());
        contentValues.put("age", student.getAge());
        contentValues.put("weight", student.getWeight());

        int update = writeDatabase.update(TABLE_NAME, contentValues, "id=?", new String[]{student.getId().toString()});
        return update > 0;
    }


    /**
     * 删除
     *
     * @param id id
     * @return boolean
     */
    public boolean delete(long id)
    {
        int delete = writeDatabase.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        return delete > 0;
    }


}
