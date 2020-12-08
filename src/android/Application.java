package com.spoon.backgroundfileupload;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import net.gotev.uploadservice.UploadServiceConfig;

public class Application extends com.orm.SugarApp {
    private static final String defaultChannelId = "com.spoon.backgroundfileupload.channel";
    private static final String defaultChannelName = "upload channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        UploadServiceConfig.initialize(
                this,
                getMetaData("CHANNEL_ID", defaultChannelId),
                false
        );
    }

    private String getMetaData(String key, String def) {
        try {
            ApplicationInfo app = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);

            return app.metaData.getString(key, def);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return def;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    getMetaData("CHANNEL_ID", defaultChannelId),
                    getMetaData("CHANNEL_NAME", defaultChannelName),
                    NotificationManager.IMPORTANCE_LOW
            );

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
    }
}
