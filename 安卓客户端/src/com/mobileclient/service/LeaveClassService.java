package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LeaveClass;
import com.mobileclient.util.HttpUtil;

/*请假类别管理业务逻辑层*/
public class LeaveClassService {
	/* 添加请假类别 */
	public String AddLeaveClass(LeaveClass leaveClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveClassId", leaveClass.getLeaveClassId() + "");
		params.put("leaveClassName", leaveClass.getLeaveClassName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询请假类别 */
	public List<LeaveClass> QueryLeaveClass(LeaveClass queryConditionLeaveClass) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LeaveClassServlet?action=query";
		if(queryConditionLeaveClass != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LeaveClassListHandler leaveClassListHander = new LeaveClassListHandler();
		xr.setContentHandler(leaveClassListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LeaveClass> leaveClassList = leaveClassListHander.getLeaveClassList();
		return leaveClassList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<LeaveClass> leaveClassList = new ArrayList<LeaveClass>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveClass leaveClass = new LeaveClass();
				leaveClass.setLeaveClassId(object.getInt("leaveClassId"));
				leaveClass.setLeaveClassName(object.getString("leaveClassName"));
				leaveClassList.add(leaveClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveClassList;
	}

	/* 更新请假类别 */
	public String UpdateLeaveClass(LeaveClass leaveClass) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveClassId", leaveClass.getLeaveClassId() + "");
		params.put("leaveClassName", leaveClass.getLeaveClassName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除请假类别 */
	public String DeleteLeaveClass(int leaveClassId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveClassId", leaveClassId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "请假类别信息删除失败!";
		}
	}

	/* 根据请假类别id获取请假类别对象 */
	public LeaveClass GetLeaveClass(int leaveClassId)  {
		List<LeaveClass> leaveClassList = new ArrayList<LeaveClass>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveClassId", leaveClassId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveClassServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveClass leaveClass = new LeaveClass();
				leaveClass.setLeaveClassId(object.getInt("leaveClassId"));
				leaveClass.setLeaveClassName(object.getString("leaveClassName"));
				leaveClassList.add(leaveClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = leaveClassList.size();
		if(size>0) return leaveClassList.get(0); 
		else return null; 
	}
}
