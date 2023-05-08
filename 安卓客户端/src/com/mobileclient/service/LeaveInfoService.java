package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.LeaveInfo;
import com.mobileclient.util.HttpUtil;

/*请假管理业务逻辑层*/
public class LeaveInfoService {
	/* 添加请假 */
	public String AddLeaveInfo(LeaveInfo leaveInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveId", leaveInfo.getLeaveId() + "");
		params.put("leaveClassObj", leaveInfo.getLeaveClassObj() + "");
		params.put("reason", leaveInfo.getReason());
		params.put("content", leaveInfo.getContent());
		params.put("studentObj", leaveInfo.getStudentObj());
		params.put("addTime", leaveInfo.getAddTime());
		params.put("leaveState", leaveInfo.getLeaveState());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询请假 */
	public List<LeaveInfo> QueryLeaveInfo(LeaveInfo queryConditionLeaveInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "LeaveInfoServlet?action=query";
		if(queryConditionLeaveInfo != null) {
			urlString += "&leaveClassObj=" + queryConditionLeaveInfo.getLeaveClassObj();
			urlString += "&reason=" + URLEncoder.encode(queryConditionLeaveInfo.getReason(), "UTF-8") + "";
			urlString += "&studentObj=" + URLEncoder.encode(queryConditionLeaveInfo.getStudentObj(), "UTF-8") + "";
			urlString += "&addTime=" + URLEncoder.encode(queryConditionLeaveInfo.getAddTime(), "UTF-8") + "";
			urlString += "&leaveState=" + URLEncoder.encode(queryConditionLeaveInfo.getLeaveState(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		LeaveInfoListHandler leaveInfoListHander = new LeaveInfoListHandler();
		xr.setContentHandler(leaveInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<LeaveInfo> leaveInfoList = leaveInfoListHander.getLeaveInfoList();
		return leaveInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<LeaveInfo> leaveInfoList = new ArrayList<LeaveInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveInfo leaveInfo = new LeaveInfo();
				leaveInfo.setLeaveId(object.getInt("leaveId"));
				leaveInfo.setLeaveClassObj(object.getInt("leaveClassObj"));
				leaveInfo.setReason(object.getString("reason"));
				leaveInfo.setContent(object.getString("content"));
				leaveInfo.setStudentObj(object.getString("studentObj"));
				leaveInfo.setAddTime(object.getString("addTime"));
				leaveInfo.setLeaveState(object.getString("leaveState"));
				leaveInfoList.add(leaveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return leaveInfoList;
	}

	/* 更新请假 */
	public String UpdateLeaveInfo(LeaveInfo leaveInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveId", leaveInfo.getLeaveId() + "");
		params.put("leaveClassObj", leaveInfo.getLeaveClassObj() + "");
		params.put("reason", leaveInfo.getReason());
		params.put("content", leaveInfo.getContent());
		params.put("studentObj", leaveInfo.getStudentObj());
		params.put("addTime", leaveInfo.getAddTime());
		params.put("leaveState", leaveInfo.getLeaveState());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除请假 */
	public String DeleteLeaveInfo(int leaveId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveId", leaveId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "请假信息删除失败!";
		}
	}

	/* 根据请假id获取请假对象 */
	public LeaveInfo GetLeaveInfo(int leaveId)  {
		List<LeaveInfo> leaveInfoList = new ArrayList<LeaveInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("leaveId", leaveId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "LeaveInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				LeaveInfo leaveInfo = new LeaveInfo();
				leaveInfo.setLeaveId(object.getInt("leaveId"));
				leaveInfo.setLeaveClassObj(object.getInt("leaveClassObj"));
				leaveInfo.setReason(object.getString("reason"));
				leaveInfo.setContent(object.getString("content"));
				leaveInfo.setStudentObj(object.getString("studentObj"));
				leaveInfo.setAddTime(object.getString("addTime"));
				leaveInfo.setLeaveState(object.getString("leaveState"));
				leaveInfoList.add(leaveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = leaveInfoList.size();
		if(size>0) return leaveInfoList.get(0); 
		else return null; 
	}
}
