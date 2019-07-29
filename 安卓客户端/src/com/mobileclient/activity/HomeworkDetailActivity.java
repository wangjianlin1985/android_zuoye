package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Homework;
import com.mobileclient.service.HomeworkService;
import com.mobileclient.domain.Course;
import com.mobileclient.service.CourseService;
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
public class HomeworkDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明作业id控件
	private TextView TV_homeworkId;
	// 声明课程控件
	private TextView TV_courseObj;
	// 声明作业标题控件
	private TextView TV_title;
	// 声明作业内容控件
	private TextView TV_content;
	// 声明作业要求控件
	private TextView TV_hwRequire;
	// 声明发布日期控件
	private TextView TV_publishDate;
	/* 要保存的作业信息 */
	Homework homework = new Homework(); 
	/* 作业管理业务逻辑层 */
	private HomeworkService homeworkService = new HomeworkService();
	private CourseService courseService = new CourseService();
	private int homeworkId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.homework_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看作业详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_homeworkId = (TextView) findViewById(R.id.TV_homeworkId);
		TV_courseObj = (TextView) findViewById(R.id.TV_courseObj);
		TV_title = (TextView) findViewById(R.id.TV_title);
		TV_content = (TextView) findViewById(R.id.TV_content);
		TV_hwRequire = (TextView) findViewById(R.id.TV_hwRequire);
		TV_publishDate = (TextView) findViewById(R.id.TV_publishDate);
		Bundle extras = this.getIntent().getExtras();
		homeworkId = extras.getInt("homeworkId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HomeworkDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    homework = homeworkService.GetHomework(homeworkId); 
		this.TV_homeworkId.setText(homework.getHomeworkId() + "");
		Course courseObj = courseService.GetCourse(homework.getCourseObj());
		this.TV_courseObj.setText(courseObj.getCourseName());
		this.TV_title.setText(homework.getTitle());
		this.TV_content.setText(homework.getContent());
		this.TV_hwRequire.setText(homework.getHwRequire());
		Date publishDate = new Date(homework.getPublishDate().getTime());
		String publishDateStr = (publishDate.getYear() + 1900) + "-" + (publishDate.getMonth()+1) + "-" + publishDate.getDate();
		this.TV_publishDate.setText(publishDateStr);
	} 
}
