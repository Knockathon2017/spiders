package connect.exzeo.com.connect.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by subodh on 5/8/16.
 */
public class ConnectServiceClient {

    private static volatile APIConnect apiConnect = null;
    private static Retrofit sRetrofit;

    private ConnectServiceClient() {

    }

    public static Retrofit getRetrofit() {
        initConnectAPI();
        return sRetrofit;
    }

    /**
     * Initializing Singleton for Api
     */
    public static void initConnectAPI() {
        if (null == apiConnect) {
            synchronized (ConnectServiceClient.class) {
                if (null == apiConnect) {
                    sRetrofit = new Retrofit.Builder().baseUrl("http://172.16.2.170:9000")
                            .client(getHttpClient())
                            .addConverterFactory(getConverterFactory())
                            .build();
                    apiConnect = sRetrofit.create(APIConnect.class);
                }
            }
        }
    }

    /**
     * Custom Http Client to define connect
     ion timeouts.
     */
    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder client =
                new OkHttpClient.Builder().readTimeout(3, TimeUnit.MINUTES)
                        .writeTimeout(3, TimeUnit.MINUTES)
                        .connectTimeout(3, TimeUnit.MINUTES);

        //TODO: try to pass via method params
    /*int cacheSize = 10 * 1024 * 1024; // 10 MiB
    Cache cache = new Cache(getApplication().getCacheDir(), cacheSize);
		OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();*/

        client.networkInterceptors().add(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();


                return chain.proceed(requestBuilder.build());
            }
        });

        //add logging as last interceptor
        client.networkInterceptors().add(getLoggingInterceptor());// <-- this is the important line!

        return client.build();
    }

    /**
     * Creates the Converter factory by setting custom HttpClient.
     */
    private static Converter.Factory getConverterFactory() {
        Gson gson = new GsonBuilder().create();
        return GsonConverterFactory.create(gson);
    }


    /**
     * Method to get APITypTap
     */
    public static APIConnect getConnectAPI() {
        initConnectAPI();
        return apiConnect;
    }

    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

}
