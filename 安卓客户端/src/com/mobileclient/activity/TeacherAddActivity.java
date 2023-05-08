package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Teacher;
import com.mobileclient.service.TeacherService;
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
public class TeacherAddActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明教师编号输入框
	private EditText ET_teacherNo;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 声明年龄输入框
	private EditText ET_age;
	// 出版入职日期控件
	private DatePicker dp_comeDate;
	// 声明职称输入框
	private EditText ET_postName;
	// 声明联系电话输入框
	private EditText ET_telephone;
	// 声明教师简介输入框
	private EditText ET_teacherDesc;
	protected String carmera_path;
	/*要保存的老师信息*/
	Teacher teacher = new Teacher();
	/*老师管理业务逻辑层*/
	private TeacherService teacherService = new TeacherService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.teacher_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加老师");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_teacherNo = (EditText) findViewById(R.id.ET_teacherNo);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		ET_age = (EditText) findViewById(R.id.ET_age);
		dp_comeDate = (DatePicker)this.findViewById(R.id.dp_comeDate);
		ET_postName = (EditText) findViewById(R.id.ET_postName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		ET_teacherDesc = (EditText) findViewById(R.id.ET_teacherDesc);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加老师按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取教师编号*/
					if(ET_teacherNo.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "教师编号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherNo.setFocusable(true);
						ET_teacherNo.requestFocus();
						return;
					}
					teacher.setTeacherNo(ET_teacherNo.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					teacher.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					teacher.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					teacher.setSex(ET_sex.getText().toString());
					/*验证获取年龄*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "年龄输入不能为空!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					teacher.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*获取入职日期*/
					Date comeDate = new Date(dp_comeDate.getYear()-1900,dp_comeDate.getMonth(),dp_comeDate.getDayOfMonth());
					teacher.setComeDate(new Timestamp(comeDate.getTime()));
					/*验证获取职称*/ 
					if(ET_postName.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "职称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_postName.setFocusable(true);
						ET_postName.requestFocus();
						return;	
					}
					teacher.setPostName(ET_postName.getText().toString());
					/*验证获取联系电话*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "联系电话输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					teacher.setTelephone(ET_telephone.getText().toString());
					/*验证获取教师简介*/ 
					if(ET_teacherDesc.getText().toString().equals("")) {
						Toast.makeText(TeacherAddActivity.this, "教师简介输入不能为空!", Toast.LENGTH_LONG).show();
						ET_teacherDesc.setFocusable(true);
						ET_teacherDesc.requestFocus();
						return;	
					}
					teacher.setTeacherDesc(ET_teacherDesc.getText().toString());
					/*调用业务逻辑层上传老师信息*/
					TeacherAddActivity.this.setTitle("正在上传老师信息，稍等...");
					String result = teacherService.AddTeacher(teacher);
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
