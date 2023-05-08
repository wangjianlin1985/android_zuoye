package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.LeaveClass;
import com.mobileclient.service.LeaveClassService;
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
public class LeaveClassDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明请假类别id控件
	private TextView TV_leaveClassId;
	// 声明请假类别名称控件
	private TextView TV_leaveClassName;
	/* 要保存的请假类别信息 */
	LeaveClass leaveClass = new LeaveClass(); 
	/* 请假类别管理业务逻辑层 */
	private LeaveClassService leaveClassService = new LeaveClassService();
	private int leaveClassId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.leaveclass_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看请假类别详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_leaveClassId = (TextView) findViewById(R.id.TV_leaveClassId);
		TV_leaveClassName = (TextView) findViewById(R.id.TV_leaveClassName);
		Bundle extras = this.getIntent().getExtras();
		leaveClassId = extras.getInt("leaveClassId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				LeaveClassDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    leaveClass = leaveClassService.GetLeaveClass(leaveClassId); 
		this.TV_leaveClassId.setText(leaveClass.getLeaveClassId() + "");
		this.TV_leaveClassName.setText(leaveClass.getLeaveClassName());
	} 
}
