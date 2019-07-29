package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Homework;
import com.mobileclient.util.HttpUtil;

/*作业管理业务逻辑层*/
public class HomeworkService {
	/* 添加作业 */
	public String AddHomework(Homework homework) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("homeworkId", homework.getHomeworkId() + "");
		params.put("courseObj", homework.getCourseObj());
		params.put("title", homework.getTitle());
		params.put("content", homework.getContent());
		params.put("hwRequire", homework.getHwRequire());
		params.put("publishDate", homework.getPublishDate().toString());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询作业 */
	public List<Homework> QueryHomework(Homework queryConditionHomework) throws Exception {
		String urlString = HttpUtil.BASE_URL + "HomeworkServlet?action=query";
		if(queryConditionHomework != null) {
			urlString += "&courseObj=" + URLEncoder.encode(queryConditionHomework.getCourseObj(), "UTF-8") + "";
			urlString += "&title=" + URLEncoder.encode(queryConditionHomework.getTitle(), "UTF-8") + "";
			if(queryConditionHomework.getPublishDate() != null) {
				urlString += "&publishDate=" + URLEncoder.encode(queryConditionHomework.getPublishDate().toString(), "UTF-8");
			}
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		HomeworkListHandler homeworkListHander = new HomeworkListHandler();
		xr.setContentHandler(homeworkListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Homework> homeworkList = homeworkListHander.getHomeworkList();
		return homeworkList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Homework> homeworkList = new ArrayList<Homework>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Homework homework = new Homework();
				homework.setHomeworkId(object.getInt("homeworkId"));
				homework.setCourseObj(object.getString("courseObj"));
				homework.setTitle(object.getString("title"));
				homework.setContent(object.getString("content"));
				homework.setHwRequire(object.getString("hwRequire"));
				homework.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				homeworkList.add(homework);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return homeworkList;
	}

	/* 更新作业 */
	public String UpdateHomework(Homework homework) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("homeworkId", homework.getHomeworkId() + "");
		params.put("courseObj", homework.getCourseObj());
		params.put("title", homework.getTitle());
		params.put("content", homework.getContent());
		params.put("hwRequire", homework.getHwRequire());
		params.put("publishDate", homework.getPublishDate().toString());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除作业 */
	public String DeleteHomework(int homeworkId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("homeworkId", homeworkId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "作业信息删除失败!";
		}
	}

	/* 根据作业id获取作业对象 */
	public Homework GetHomework(int homeworkId)  {
		List<Homework> homeworkList = new ArrayList<Homework>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("homeworkId", homeworkId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "HomeworkServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Homework homework = new Homework();
				homework.setHomeworkId(object.getInt("homeworkId"));
				homework.setCourseObj(object.getString("courseObj"));
				homework.setTitle(object.getString("title"));
				homework.setContent(object.getString("content"));
				homework.setHwRequire(object.getString("hwRequire"));
				homework.setPublishDate(Timestamp.valueOf(object.getString("publishDate")));
				homeworkList.add(homework);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = homeworkList.size();
		if(size>0) return homeworkList.get(0); 
		else return null; 
	}
}
