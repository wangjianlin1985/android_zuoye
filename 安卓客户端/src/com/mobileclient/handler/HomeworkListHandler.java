package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Homework;
public class HomeworkListHandler extends DefaultHandler {
	private List<Homework> homeworkList = null;
	private Homework homework;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (homework != null) { 
            String valueString = new String(ch, start, length); 
            if ("homeworkId".equals(tempString)) 
            	homework.setHomeworkId(new Integer(valueString).intValue());
            else if ("courseObj".equals(tempString)) 
            	homework.setCourseObj(valueString); 
            else if ("title".equals(tempString)) 
            	homework.setTitle(valueString); 
            else if ("content".equals(tempString)) 
            	homework.setContent(valueString); 
            else if ("hwRequire".equals(tempString)) 
            	homework.setHwRequire(valueString); 
            else if ("publishDate".equals(tempString)) 
            	homework.setPublishDate(Timestamp.valueOf(valueString));
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Homework".equals(localName)&&homework!=null){
			homeworkList.add(homework);
			homework = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		homeworkList = new ArrayList<Homework>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Homework".equals(localName)) {
            homework = new Homework(); 
        }
        tempString = localName; 
	}

	public List<Homework> getHomeworkList() {
		return this.homeworkList;
	}
}
