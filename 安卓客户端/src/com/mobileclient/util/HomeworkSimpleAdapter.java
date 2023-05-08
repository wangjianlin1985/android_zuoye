package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.CourseService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class HomeworkSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public HomeworkSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.homework_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_homeworkId = (TextView)convertView.findViewById(R.id.tv_homeworkId);
	  holder.tv_courseObj = (TextView)convertView.findViewById(R.id.tv_courseObj);
	  holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
	  holder.tv_hwRequire = (TextView)convertView.findViewById(R.id.tv_hwRequire);
	  holder.tv_publishDate = (TextView)convertView.findViewById(R.id.tv_publishDate);
	  /*设置各个控件的展示内容*/
	  holder.tv_homeworkId.setText("作业id：" + mData.get(position).get("homeworkId").toString());
	  holder.tv_courseObj.setText("课程：" + (new CourseService()).GetCourse(mData.get(position).get("courseObj").toString()).getCourseName());
	  holder.tv_title.setText("作业标题：" + mData.get(position).get("title").toString());
	  holder.tv_hwRequire.setText("作业要求：" + mData.get(position).get("hwRequire").toString());
	  try {holder.tv_publishDate.setText("发布日期：" + mData.get(position).get("publishDate").toString().substring(0, 10));} catch(Exception ex){}
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_homeworkId;
    	TextView tv_courseObj;
    	TextView tv_title;
    	TextView tv_hwRequire;
    	TextView tv_publishDate;
    }
} 
