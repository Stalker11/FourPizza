package com.example.professor.fourpizza.util;

import android.app.Application;

public class ProjectApplication extends Application {
    @Override
    public void onCreate() {
        ParserXml.setContext(getApplicationContext());
        super.onCreate();
    }
}
