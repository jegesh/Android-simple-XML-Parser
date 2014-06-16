package com.example.learningsqlite;

import java.io.IOException;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.Xml;

public class XMLLayoutParser {
	Context activity;
	/*
	 * Each constant represents an Android View type
	 * Add in additional constants as needed; those here
	 * are just some that seem would be most commonly needed
	 */
	public static final String EDIT_TEXT = "EditText";
	public static final String TEXT_VIEW = "TextView";
	public static final String CHECKBOX = "CheckBox";
	
	
	public XMLLayoutParser(Context act){
		activity = act;
	}
	

	
	public int[] getElementIds(int layoutId, String viewType) throws XmlPullParserException, IOException{
		XmlResourceParser parser = activity.getResources().getLayout(layoutId);
		LinkedList<Integer> idList = new LinkedList<Integer>();
		while(parser.getEventType()!=XmlResourceParser.END_DOCUMENT){
			parser.next();
			if(parser.getEventType()==XmlResourceParser.START_TAG){
				if(parser.getName().equals(viewType)){
			/*		long viewId = parser.getIdAttributeResourceValue(0); // this is what's supposed to work
					if(viewId!=0)
						idList.add((int) viewId);
					*/
					// this is the workaround!
					if(parser.getAttributeName(0).equals("id")){
						long viewId = parser.getAttributeResourceValue(0, 0);
						idList.add((int)viewId);
					}
				}
			}
		}
		parser.close();
		int[] inputIds = new int[idList.size()];
		for(int j = 0;j<inputIds.length;j++)
			
			inputIds[j] = idList.pollFirst();
			
		return inputIds;
	}

}
