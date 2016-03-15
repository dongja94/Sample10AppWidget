package com.example.dongja94.sampleappwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WidgetUpdateService extends Service {
    public WidgetUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler mHandler = new Handler();
    private static final int[] IMAGES = {R.drawable.sample_thumb_0,
            R.drawable.sample_thumb_1,
            R.drawable.sample_thumb_2,
            R.drawable.sample_thumb_3,
            R.drawable.sample_thumb_4,
            R.drawable.sample_thumb_5,
            R.drawable.sample_thumb_6,
            R.drawable.sample_thumb_7};

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.post(updateRunnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(updateRunnable);
    }

    Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            updateAppWidget();
            mHandler.postDelayed(this, 2000);
        }
    };

    int count = 0;
    private void updateAppWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName provider = new ComponentName(this, MyAppWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(provider);
        for (int appWidgetId : appWidgetIds){
            CharSequence widgetText = "sample" + count;
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.my_app_widget_provider);
            views.setImageViewResource(R.id.image_icon, IMAGES[count % IMAGES.length]);
            views.setTextViewText(R.id.text_title, widgetText);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        count++;
    }


}
