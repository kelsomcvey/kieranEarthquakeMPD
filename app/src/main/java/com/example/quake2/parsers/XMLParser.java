package com.example.quake2.parsers;

/*  Starter project for Mobile Platform Development in Semester B Session 2018/2019
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/


// Name                 Kieran McVey
// Student ID           200212626
// Programme of Study   BSc Computing

import com.example.quake2.models.EarthQuakeModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    public static EarthQuakeModel[] parseFeed(String content) {

        try {

            boolean inItemTag = false;
            String currentTagName = "";
            EarthQuakeModel currentItem = null;

            List<EarthQuakeModel> itemList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("item")) {
                            inItemTag = true;
                            currentItem = new EarthQuakeModel();
                            itemList.add(currentItem);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            inItemTag = false;
                        }
                        currentTagName = "";
                        break;

                    case XmlPullParser.TEXT:
                        String text = parser.getText();
                        if (inItemTag && currentItem != null) {
                            try {
                                switch (currentTagName) {
                                    case "title":
                                        currentItem.setTitle(text);
                                        break;
                                    case "description":
                                        currentItem.setDescription(text);
                                        break;
                                        case "link":
                                        currentItem.setLink(text);
                                        break;
                                    case "category":
                                        currentItem.setCategory(text);
                                        break;

                                    case "geo:lat":
                                        currentItem.setLatString(text);
                                        break;
                                    case "geo:long":
                                        currentItem.setLongString(text);
                                        break;
                                    case "pubDate":
                                        currentItem.setPublishedDate(text);
                                        break;

                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }

                eventType = parser.next();

            } // end while loop

            EarthQuakeModel[] dataItems = new EarthQuakeModel[itemList.size()];
            return itemList.toArray(dataItems);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
