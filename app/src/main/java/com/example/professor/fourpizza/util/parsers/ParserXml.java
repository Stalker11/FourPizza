package com.example.professor.fourpizza.util.parsers;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ParserXml {
    private static Context cont;
    private final static String TAG = ParserXml.class.getSimpleName();
    private final String ns = null;
    private List<String> location;
    private InputStream in;

    public static void setContext(Context context) {
        cont = context;
    }

    public List<String> parseGeolocation() {
        try {
            Log.d(TAG, "onCreate: " + cont.getAssets().open("gpx.xml"));
            in = cont.getAssets().open("gpx.xml");
            location = new ArrayList<>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                Log.d(TAG, "onCreate: " + parser.getNamespace());
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("lat")) {
                    String latitude = readLatitude(parser);
                    Log.d(TAG, "onCreate: lat" + latitude);
                    location.add(latitude);
                } else if (name.equals("lon")) {
                    String longtitude = readLongtitude(parser);
                    Log.d(TAG, "onCreate: lon" + longtitude);
                    location.add(longtitude);
                } else if (name.equals("time")) {
                    String time = readTime(parser);
                    Log.d(TAG, "onCreate: time" + time);
                    location.add(time);
                } else {
                    //   skip(parser);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return location;
    }

    private String readLatitude(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "lat");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "lat");
        return title;
    }

    private String readLongtitude(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "lon");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "lon");
        return title;
    }

    private String readTime(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "time");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "time");
        return title;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
