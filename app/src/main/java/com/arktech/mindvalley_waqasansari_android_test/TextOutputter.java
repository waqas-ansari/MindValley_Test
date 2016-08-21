package com.arktech.mindvalley_waqasansari_android_test;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class TextOutputter {

    public static JSONObject loadAsJSON(String text) throws JSONException {
        return new JSONObject(text);
    }

    public static XmlPullParser loadAsXML(String text) throws XmlPullParserException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser pullParser = factory.newPullParser();
        pullParser.setInput(null, text);
        return pullParser;
    }

}
