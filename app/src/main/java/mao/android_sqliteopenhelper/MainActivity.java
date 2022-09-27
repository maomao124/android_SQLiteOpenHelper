package mao.android_sqliteopenhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mao.android_sqliteopenhelper.dao.DBHelper;
import mao.android_sqliteopenhelper.entity.Student;

/**
 * Class(类名): MainActivity
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/9/26
 * Time(创建时间)： 22:38
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class MainActivity extends AppCompatActivity
{

    /**
     * save按钮
     */
    private Button saveButton;
    /**
     * load按钮
     */
    private Button loadButton;

    /**
     * 学号编辑文本
     */
    private EditText idEditText;

    /**
     * 名称编辑文本
     */
    private EditText nameEditText;

    /**
     * 年龄编辑文本
     */
    private EditText ageEditText;

    /**
     * 体重编辑文本
     */
    private EditText weightEditText;


    /**
     * 标签
     */
    private static final String TAG = "MainActivity";

    /**
     * db helper
     */
    private DBHelper dbHelper;

    /**
     * 结果文本视图
     */
    private TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        saveButton = findViewById(R.id.Button_save);
        loadButton = findViewById(R.id.Button_load);

        idEditText = findViewById(R.id.EditText_id);
        nameEditText = findViewById(R.id.EditText_name);
        ageEditText = findViewById(R.id.EditText_age);
        weightEditText = findViewById(R.id.EditText_weight);
        resultTextView = findViewById(R.id.TextView_result);

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                save();
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                load();
            }
        });

        //load();
    }


    /**
     * 保存
     */
    private void save()
    {
//        try
//        {
//            long id = Long.parseLong(idEditText.getText().toString());
//            String name = nameEditText.getText().toString();
//            int age = Integer.parseInt(ageEditText.getText().toString());
//            float weight = Float.parseFloat(weightEditText.getText().toString());
//
//            SharedPreferences.Editor editor = getSharedPreferences("text", MODE_PRIVATE).edit();
//
//            editor.putLong("id", id);
//            editor.putString("name", name);
//            editor.putInt("age", age);
//            editor.putFloat("weight", weight);
//
//            editor.commit();
//            //异步
//            //editor.apply();
//
//            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
//        }
//        catch (Exception e)
//        {
//            Log.e(TAG, "save: ", e);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("异常")
//                    .setMessage("出现异常，请检查输入\n异常内容为\n：" + e.getMessage())
//                    .setPositiveButton("确定", null)
//                    .create()
//                    .show();
//        }

        try
        {
            long id = Long.parseLong(idEditText.getText().toString());
            String name = nameEditText.getText().toString();
            int age = Integer.parseInt(ageEditText.getText().toString());
            float weight = Float.parseFloat(weightEditText.getText().toString());

            Student student = new Student(id, name, age, weight);
            boolean insert = dbHelper.insert(student);
            if (!insert)
            {
                //插入失败，存在主键，转换为更新
                boolean update = dbHelper.update(student);
                if (!update)
                {
                    Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "update: " + student);
                Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
            }
            Log.d(TAG, "save: " + student);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Log.e(TAG, "save: ", e);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("异常")
                    .setMessage("出现异常，请检查输入\n异常内容为\n：" + e.getMessage())
                    .setPositiveButton("确定", null)
                    .create()
                    .show();
        }
    }

    /**
     * 加载
     */
    @SuppressLint("SetTextI18n")
    private void load()
    {
//        SharedPreferences sharedPreferences = getSharedPreferences("text", MODE_PRIVATE);
//        long id = sharedPreferences.getLong("id", 0);
//        String name = sharedPreferences.getString("name", "");
//        int age = sharedPreferences.getInt("age", 0);
//        float weight = sharedPreferences.getFloat("weight", 0.0f);
//
//        idEditText.setText(String.valueOf(id));
//        nameEditText.setText(name);
//        ageEditText.setText(String.valueOf(age));
//        weightEditText.setText(String.valueOf(weight));
//
//
//        String str = "学号：" + id + "\n姓名：" + name + "\n年龄：" + age + "\n体重：" + weight;
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("读取到的内容")
//                .setMessage(str)
//                .setPositiveButton("确定", null)
//                .create()
//                .show();


        Log.d(TAG, "load: start");
        List<Student> list = dbHelper.queryAll();
        resultTextView.setText("");
        for (Student student : list)
        {
            Log.d(TAG, "load: " + student);
            Long id = student.getId();
            String name = student.getName();
            Integer age = student.getAge();
            Float weight = student.getWeight();

            String str = "\n\n\n学号：" + id + "\n姓名：" + name + "\n年龄：" + age + "\n体重：" + weight;
            resultTextView.setText(resultTextView.getText().toString() + str);
            if (idEditText.getText().toString().equals(id.toString()))
            {
                idEditText.setText(String.valueOf(id));
                nameEditText.setText(name);
                ageEditText.setText(String.valueOf(age));
                weightEditText.setText(String.valueOf(weight));
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        save();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        dbHelper = DBHelper.getInstance(this);
        dbHelper.openReadConnection();
        dbHelper.openWriteConnection();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        dbHelper.closeConnection();
    }
}