package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.service.LeaveInfoService;
import com.mobileclient.domain.LeaveClass;
import com.mobileclient.service.LeaveClassService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class LeaveInfoEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明请假idTextView
	private TextView TV_leaveId;
	// 声明请假类别下拉框
	private Spinner spinner_leaveClassObj;
	private ArrayAdapter<String> leaveClassObj_adapter;
	private static  String[] leaveClassObj_ShowText  = null;
	private List<LeaveClass> leaveClassList = null;
	/*请假类别管理业务逻辑层*/
	private LeaveClassService leaveClassService = new LeaveClassService();
	// 声明请假原因输入框
	private EditText ET_reason;
	// 声明请假详情输入框
	private EditText ET_content;
	// 声明请假学生下拉框
	private Spinner spinner_studentObj;
	private ArrayAdapter<String> studentObj_adapter;
	private static  String[] studentObj_ShowText  = null;
	private List<Student> studentList = null;
	/*请假学生管理业务逻辑层*/
	private StudentService studentService = new StudentService();
	// 声明请假时间输入框
	private EditText ET_addTime;
	// 声明审核状态输入框
	private EditText ET_leaveState;
	protected String carmera_path;
	/*要保存的请假信息*/
	LeaveInfo leaveInfo = new LeaveInfo();
	/*请假管理业务逻辑层*/
	private LeaveInfoService leaveInfoService = new LeaveInfoService();

	private int leaveId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveinfo_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑请假信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_leaveId = (TextView) findViewById(R.id.TV_leaveId);
		spinner_leaveClassObj = (Spinner) findViewById(R.id.Spinner_leaveClassObj);
		// 获取所有的请假类别
		try {
			leaveClassList = leaveClassService.QueryLeaveClass(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int leaveClassCount = leaveClassList.size();
		leaveClassObj_ShowText = new String[leaveClassCount];
		for(int i=0;i<leaveClassCount;i++) { 
			leaveClassObj_ShowText[i] = leaveClassList.get(i).getLeaveClassName();
		}
		// 将可选内容与ArrayAdapter连接起来
		leaveClassObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leaveClassObj_ShowText);
		// 设置图书类别下拉列表的风格
		leaveClassObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_leaveClassObj.setAdapter(leaveClassObj_adapter);
		// 添加事件Spinner事件监听
		spinner_leaveClassObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setLeaveClassObj(leaveClassList.get(arg2).getLeaveClassId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_leaveClassObj.setVisibility(View.VISIBLE);
		ET_reason = (EditText) findViewById(R.id.ET_reason);
		ET_content = (EditText) findViewById(R.id.ET_content);
		spinner_studentObj = (Spinner) findViewById(R.id.Spinner_studentObj);
		// 获取所有的请假学生
		try {
			studentList = studentService.QueryStudent(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int studentCount = studentList.size();
		studentObj_ShowText = new String[studentCount];
		for(int i=0;i<studentCount;i++) { 
			studentObj_ShowText[i] = studentList.get(i).getName();
		}
		// 将可选内容与ArrayAdapter连接起来
		studentObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, studentObj_ShowText);
		// 设置图书类别下拉列表的风格
		studentObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_studentObj.setAdapter(studentObj_adapter);
		// 添加事件Spinner事件监听
		spinner_studentObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				leaveInfo.setStudentObj(studentList.get(arg2).getStudentNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_studentObj.setVisibility(View.VISIBLE);
		ET_addTime = (EditText) findViewById(R.id.ET_addTime);
		ET_leaveState = (EditText) findViewById(R.id.ET_leaveState);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		leaveId = extras.getInt("leaveId");
		/*单击修改请假按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取请假原因*/ 
					if(ET_reason.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoEditActivity.this, "请假原因输入不能为空!", Toast.LENGTH_LONG).show();
						ET_reason.setFocusable(true);
						ET_reason.requestFocus();
						return;	
					}
					leaveInfo.setReason(ET_reason.getText().toString());
					/*验证获取请假详情*/ 
					if(ET_content.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoEditActivity.this, "请假详情输入不能为空!", Toast.LENGTH_LONG).show();
						ET_content.setFocusable(true);
						ET_content.requestFocus();
						return;	
					}
					leaveInfo.setContent(ET_content.getText().toString());
					/*验证获取请假时间*/ 
					if(ET_addTime.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoEditActivity.this, "请假时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_addTime.setFocusable(true);
						ET_addTime.requestFocus();
						return;	
					}
					leaveInfo.setAddTime(ET_addTime.getText().toString());
					/*验证获取审核状态*/ 
					if(ET_leaveState.getText().toString().equals("")) {
						Toast.makeText(LeaveInfoEditActivity.this, "审核状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_leaveState.setFocusable(true);
						ET_leaveState.requestFocus();
						return;	
					}
					leaveInfo.setLeaveState(ET_leaveState.getText().toString());
					/*调用业务逻辑层上传请假信息*/
					LeaveInfoEditActivity.this.setTitle("正在更新请假信息，稍等...");
					String result = leaveInfoService.UpdateLeaveInfo(leaveInfo);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    leaveInfo = leaveInfoService.GetLeaveInfo(leaveId);
		this.TV_leaveId.setText(leaveId+"");
		for (int i = 0; i < leaveClassList.size(); i++) {
			if (leaveInfo.getLeaveClassObj() == leaveClassList.get(i).getLeaveClassId()) {
				this.spinner_leaveClassObj.setSelection(i);
				break;
			}
		}
		this.ET_reason.setText(leaveInfo.getReason());
		this.ET_content.setText(leaveInfo.getContent());
		for (int i = 0; i < studentList.size(); i++) {
			if (leaveInfo.getStudentObj().equals(studentList.get(i).getStudentNo())) {
				this.spinner_studentObj.setSelection(i);
				break;
			}
		}
		this.ET_addTime.setText(leaveInfo.getAddTime());
		this.ET_leaveState.setText(leaveInfo.getLeaveState());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
