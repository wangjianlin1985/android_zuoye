package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LeaveInfo;
public class LeaveInfoListHandler extends DefaultHandler {
	private List<LeaveInfo> leaveInfoList = null;
	private LeaveInfo leaveInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (leaveInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("leaveId".equals(tempString)) 
            	leaveInfo.setLeaveId(new Integer(valueString).intValue());
            else if ("leaveClassObj".equals(tempString)) 
            	leaveInfo.setLeaveClassObj(new Integer(valueString).intValue());
            else if ("reason".equals(tempString)) 
            	leaveInfo.setReason(valueString); 
            else if ("content".equals(tempString)) 
            	leaveInfo.setContent(valueString); 
            else if ("studentObj".equals(tempString)) 
            	leaveInfo.setStudentObj(valueString); 
            else if ("addTime".equals(tempString)) 
            	leaveInfo.setAddTime(valueString); 
            else if ("leaveState".equals(tempString)) 
            	leaveInfo.setLeaveState(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LeaveInfo".equals(localName)&&leaveInfo!=null){
			leaveInfoList.add(leaveInfo);
			leaveInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		leaveInfoList = new ArrayList<LeaveInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LeaveInfo".equals(localName)) {
            leaveInfo = new LeaveInfo(); 
        }
        tempString = localName; 
	}

	public List<LeaveInfo> getLeaveInfoList() {
		return this.leaveInfoList;
	}
}
