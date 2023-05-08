package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.service.LeaveInfoService;
import com.mobileclient.domain.LeaveClass;
import com.mobileclient.service.LeaveClassService;
import com.mobileclient.domain.Student;
import com.mobileclient.service.StudentService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class LeaveInfoDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明请假id控件
	private TextView TV_leaveId;
	// 声明请假类别控件
	private TextView TV_leaveClassObj;
	// 声明请假原因控件
	private TextView TV_reason;
	// 声明请假详情控件
	private TextView TV_content;
	// 声明请假学生控件
	private TextView TV_studentObj;
	// 声明请假时间控件
	private TextView TV_addTime;
	// 声明审核状态控件
	private TextView TV_leaveState;
	/* 要保存的请假信息 */
	LeaveInfo leaveInfo = new LeaveInfo(); 
	/* 请假管理业务逻辑层 */
	private LeaveInfoService leaveInfoService = new LeaveInfoService();
	private LeaveClassService leaveClassService = new LeaveClassService();
	private StudentService studentService = new StudentService();
	private int leaveId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveinfo_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看请假详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_leaveId = (TextView) findViewById(R.id.TV_leaveId);
		TV_leaveClassObj = (TextView) findViewById(R.id.TV_leaveClassObj);
		TV_reason = (TextView) findViewById(R.id.TV_reason);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_studentObj = (TextView) findViewById(R.id.TV_studentObj);
		TV_addTime = (TextView) findViewById(R.id.TV_addTime);
		TV_leaveState = (TextView) findViewById(R.id.TV_leaveState);
		Bundle extras = this.getIntent().getExtras();
		leaveId = extras.getInt("leaveId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LeaveInfoDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    leaveInfo = leaveInfoService.GetLeaveInfo(leaveId); 
		this.TV_leaveId.setText(leaveInfo.getLeaveId() + "");
		LeaveClass leaveClassObj = leaveClassService.GetLeaveClass(leaveInfo.getLeaveClassObj());
		this.TV_leaveClassObj.setText(leaveClassObj.getLeaveClassName());
		this.TV_reason.setText(leaveInfo.getReason());
		this.TV_content.setText(leaveInfo.getContent());
		Student studentObj = studentService.GetStudent(leaveInfo.getStudentObj());
		this.TV_studentObj.setText(studentObj.getName());
		this.TV_addTime.setText(leaveInfo.getAddTime());
		this.TV_leaveState.setText(leaveInfo.getLeaveState());
	} 
}
