package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Homework;
import com.mobileclient.service.HomeworkService;
import com.mobileclient.domain.Course;
import com.mobileclient.service.CourseService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class HomeworkAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明课程下拉框
	private Spinner spinner_courseObj;
	private ArrayAdapter<String> courseObj_adapter;
	private static  String[] courseObj_ShowText  = null;
	private List<Course> courseList = null;
	/*课程管理业务逻辑层*/
	private CourseService courseService = new CourseService();
	// 声明作业标题输入框
	private EditText ET_title;
	// 声明作业内容输入框
	private EditText ET_content;
	// 声明作业要求输入框
	private EditText ET_hwRequire;
	// 出版发布日期控件
	private DatePicker dp_publishDate;
	protected String carmera_path;
	/*要保存的作业信息*/
	Homework homework = new Homework();
	/*作业管理业务逻辑层*/
	private HomeworkService homeworkService = new HomeworkService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.homework_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加作业");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		spinner_courseObj = (Spinner) findViewById(R.id.Spinner_courseObj);
		// 获取所有的课程
		try {
			courseList = courseService.QueryCourse(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int courseCount = courseList.size();
		courseObj_ShowText = new String[courseCount];
		for(int i=0;i<courseCount;i++) { 
			courseObj_ShowText[i] = courseList.get(i).getCourseName();
		}
		// 将可选内容与ArrayAdapter连接起来
		courseObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, courseObj_ShowText);
		// 设置下拉列表的风格
		courseObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_courseObj.setAdapter(courseObj_adapter);
		// 添加事件Spinner事件监听
		spinner_courseObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				homework.setCourseObj(courseList.get(arg2).getCourseNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_courseObj.setVisibility(View.VISIBLE);
		ET_title = (EditText) findViewById(R.id.ET_title);
		ET_content = (EditText) findViewById(R.id.ET_content);
		ET_hwRequire = (EditText) findViewById(R.id.ET_hwRequire);
		dp_publishDate = (DatePicker)this.findViewById(R.id.dp_publishDate);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加作业按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取作业标题*/ 
					if(ET_title.getText().toString().equals("")) {
						Toast.makeText(HomeworkAddActivity.this, "作业标题输入不能为空!", Toast.LENGTH_LONG).show();
						ET_title.setFocusable(true);
						ET_title.requestFocus();
						return;	
					}
					homework.setTitle(ET_title.getText().toString());
					/*验证获取作业内容*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(HomeworkAddActivity.this, "作业内容输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					homework.setContent(ET_content.getText().toString());
					/*验证获取作业要求*/ 
					if(ET_hwRequire.getText().toString().equals("")) {
						Toast.makeText(HomeworkAddActivity.this, "作业要求输入不能为空!", Toast.LENGTH_LONG).show();
						ET_hwRequire.setFocusable(true);
						ET_hwRequire.requestFocus();
						return;	
					}
					homework.setHwRequire(ET_hwRequire.getText().toString());
					/*获取发布日期*/
					Date publishDate = new Date(dp_publishDate.getYear()-1900,dp_publishDate.getMonth(),dp_publishDate.getDayOfMonth());
					homework.setPublishDate(new Timestamp(publishDate.getTime()));
					/*调用业务逻辑层上传作业信息*/
					HomeworkAddActivity.this.setTitle("正在上传作业信息，稍等...");
					String result = homeworkService.AddHomework(homework);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
