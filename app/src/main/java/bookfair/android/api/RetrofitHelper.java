package bookfair.android.api;

import android.app.Application;

import com.github.simonpercic.oklog3.OkLogInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import bookfair.android.BookFairApp;
import bookfair.android.BuildConfig;
import bookfair.android.core.PreferenceManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static final String BASE_URL = "https://dev-bookfair.000webhostapp.com/";

    private Application context;

    @Inject
    PreferenceManager preferenceManager;

    public RetrofitHelper(Application context) {
        this.context = context;

        BookFairApp.get(this.context).getComponent().inject(this);

    }

    public BookFairApiService bookFairApiService() {

        final GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpBuilder.addInterceptor(logging);

            OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
            okHttpBuilder.addInterceptor(okLogInterceptor);

        }

        OkHttpClient okHttpClient = okHttpBuilder.build();

        Retrofit retrofit;

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .baseUrl(BASE_URL)
                .build();

        return retrofit.create(BookFairApiService.class);
    }

}
