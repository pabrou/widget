package com.pabrou.widgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.Random;

/**
 * Created by pablo on 10/1/16.
 */
public class ExampleAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        // Perform this loop procedure for each App Widget that belongs to this provider
        final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            updateWidget(context, appWidgetManager, appWidgetIds[i], appWidgetIds);
        }

    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int[] appWidgetIds) {

        // Create an Intent to launch ExampleActivity
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Intent intentUpdate = new Intent(context, ExampleAppWidgetProvider.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        PendingIntent pendingUpdateIntent = PendingIntent.getBroadcast(context,
                0, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

        String number = String.format("%03d", (new Random().nextInt(900) + 100));

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.shared_preference_key), Context.MODE_PRIVATE);
        String readString = sharedPref.getString(context.getString(R.string.pref_value), "");

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);
        views.setOnClickPendingIntent(R.id.launch_app, pendingIntent);
        views.setTextViewText(R.id.text_input, "Texto leido:"+readString);
        views.setOnClickPendingIntent(R.id.update_widget, pendingUpdateIntent);

        // Tell the AppWidgetManager to perform an update on the current app widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /*
        Called when a change in size occurs.
        We can get the new sizes and change the layout accordingly.
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

        /*
        int maxHeight = appWidgetManager.getAppWidgetOptions(appWidgetId).getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        System.out.println("Max height:"+maxHeight);
        */
    }
}
