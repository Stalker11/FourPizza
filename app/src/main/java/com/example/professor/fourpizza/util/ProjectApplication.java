package com.example.professor.fourpizza.util;

import android.app.Application;

import com.example.professor.fourpizza.util.parsers.ParserXml;

public class ProjectApplication extends Application {
    @Override
    public void onCreate() {
        ParserXml.setContext(getApplicationContext());
        super.onCreate();
    }
}
