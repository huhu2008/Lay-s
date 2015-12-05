package com.hadlink.library.net;

import android.content.Context;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by lyao on 2015/8/27.
 */
public class RetrofitUtils {
    /**
     * baseUrl end add "/"
     * path end no add "/"
     */

    public static Retrofit singleton;


    public static <T> T createApi(Context context, Class<T> clazz, String host, boolean debug) {
        if (singleton == null) {
            synchronized (RetrofitUtils.class) {
                if (singleton == null) {
                    singleton = new Retrofit.Builder()
                            .baseUrl(host)
                            .addConverterFactory(GsonConverterFactory.create(GsonUtils.INSTANCE.get()))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(OkHttpUtils.getInstance(context, debug))
                            .build();
                }
            }

        }
        return singleton.create(clazz);
    }

}
