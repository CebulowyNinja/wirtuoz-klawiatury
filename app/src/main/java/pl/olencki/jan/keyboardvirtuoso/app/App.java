package pl.olencki.jan.keyboardvirtuoso.app;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;

    public static Context getAppContext() {
        return App.context;
    }

    public void onCreate() {
        super.onCreate();

        App.context = getApplicationContext();
    }
}
