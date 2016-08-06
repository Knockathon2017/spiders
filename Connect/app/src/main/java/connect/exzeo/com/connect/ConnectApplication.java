package connect.exzeo.com.connect;

import android.app.Application;

import connect.exzeo.com.connect.data.remote.ConnectServiceClient;

/**
 * Created by subodh on 5/8/16.
 */
public class ConnectApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeData();
    }

    private void initializeData() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                ConnectServiceClient.initConnectAPI();
            }
        }).start();
    }
}
