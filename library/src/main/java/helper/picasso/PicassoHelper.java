package helper.picasso;

import android.content.Context;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

public class PicassoHelper {

    private static Picasso PICASSO;

    /**
     * Three days.
     */
    private static final int MAX_AGE = 86400 * 3;

    /**
     * 50MB.
     */
    private static final long MAX_SIZE = 50 * 1024 * 1024;

    /**
     * No instances
     */
    private PicassoHelper() {
    }

    public static synchronized Picasso getInstance(Context context) {
        if (PICASSO != null) {
            return PICASSO;
        }
        context = context.getApplicationContext();
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(createDownloader(context));
        return builder.build();
    }

    private static Downloader createDownloader(Context context) {
        boolean okUrlFactory = false;
        try {
            Class.forName("com.squareup.okhttp.OkUrlFactory");
            okUrlFactory = true;
        } catch (ClassNotFoundException ignored) {
        }

        boolean okHttpClient = false;
        try {
            Class.forName("com.squareup.okhttp.OkHttpClient");
            okHttpClient = true;
        } catch (ClassNotFoundException ignored) {
        }

        if (okHttpClient != okUrlFactory) {
            throw new RuntimeException(""
                    + "Picasso detected an unsupported OkHttp on the classpath.\n"
                    + "To use OkHttp with this version of Picasso, you'll need:\n"
                    + "1. com.squareup.okhttp:okhttp:1.6.0 (or newer)\n"
                    + "2. com.squareup.okhttp:okhttp-urlconnection:1.6.0 (or newer)\n"
                    + "Note that OkHttp 2.0.0+ is supported!");
        }

        return okHttpClient
                ? createOkHttpDownloader(context)
                : createUrlConnectionDownloader(context);
    }

    private static UrlConnectionDownloader createUrlConnectionDownloader(Context context) {
        UrlConnectionDownloader downloader = new UrlConnectionDownloader(context);
        downloader.setMaxAge(MAX_AGE);
        downloader.setMaxSize(MAX_SIZE);
        return downloader;
    }

    private static OkHttpDownloader createOkHttpDownloader(Context context) {
        OkHttpDownloader downloader = new OkHttpDownloader(context, MAX_SIZE);
        downloader.setMaxAge(MAX_AGE);
        return downloader;
    }

}