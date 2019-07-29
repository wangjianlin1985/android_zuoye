package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.LeaveClass;
public class LeaveClassListHandler extends DefaultHandler {
	private List<LeaveClass> leaveClassList = null;
	private LeaveClass leaveClass;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (leaveClass != null) { 
            String valueString = new String(ch, start, length); 
            if ("leaveClassId".equals(tempString)) 
            	leaveClass.setLeaveClassId(new Integer(valueString).intValue());
            else if ("leaveClassName".equals(tempString)) 
            	leaveClass.setLeaveClassName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("LeaveClass".equals(localName)&&leaveClass!=null){
			leaveClassList.add(leaveClass);
			leaveClass = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		leaveClassList = new ArrayList<LeaveClass>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("LeaveClass".equals(localName)) {
            leaveClass = new LeaveClass(); 
        }
        tempString = localName; 
	}

	public List<LeaveClass> getLeaveClassList() {
		return this.leaveClassList;
	}
}
