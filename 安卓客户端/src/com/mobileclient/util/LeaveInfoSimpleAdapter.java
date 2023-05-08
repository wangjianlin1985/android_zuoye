package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.LeaveClassService;
import com.mobileclient.service.StudentService;
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

public class LeaveInfoSimpleAdapter extends SimpleAdapter { 
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

    public LeaveInfoSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.leaveinfo_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_leaveId = (TextView)convertView.findViewById(R.id.tv_leaveId);
	  holder.tv_leaveClassObj = (TextView)convertView.findViewById(R.id.tv_leaveClassObj);
	  holder.tv_reason = (TextView)convertView.findViewById(R.id.tv_reason);
	  holder.tv_studentObj = (TextView)convertView.findViewById(R.id.tv_studentObj);
	  holder.tv_addTime = (TextView)convertView.findViewById(R.id.tv_addTime);
	  holder.tv_leaveState = (TextView)convertView.findViewById(R.id.tv_leaveState);
	  /*设置各个控件的展示内容*/
	  holder.tv_leaveId.setText("请假id：" + mData.get(position).get("leaveId").toString());
	  holder.tv_leaveClassObj.setText("请假类别：" + (new LeaveClassService()).GetLeaveClass(Integer.parseInt(mData.get(position).get("leaveClassObj").toString())).getLeaveClassName());
	  holder.tv_reason.setText("请假原因：" + mData.get(position).get("reason").toString());
	  holder.tv_studentObj.setText("请假学生：" + (new StudentService()).GetStudent(mData.get(position).get("studentObj").toString()).getName());
	  holder.tv_addTime.setText("请假时间：" + mData.get(position).get("addTime").toString());
	  holder.tv_leaveState.setText("审核状态：" + mData.get(position).get("leaveState").toString());
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_leaveId;
    	TextView tv_leaveClassObj;
    	TextView tv_reason;
    	TextView tv_studentObj;
    	TextView tv_addTime;
    	TextView tv_leaveState;
    }
} 
