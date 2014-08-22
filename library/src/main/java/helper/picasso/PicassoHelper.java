package helper.picasso;

import android.content.Context;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloaderWithSetting;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloaderWithSetting;

public class PicassoHelper {

    /**
     * Three days.
     */
    private static final int MAX_AGE = 86400 * 3;

    /**
     * 50MB.
     */
    private static final long MAX_SIZE = 50 * 1024 * 1024;

    private static Picasso picasso;

    /**
     * No instances.
     */
    private PicassoHelper() {
    }

    public static Picasso with(Context context) {
        if (picasso == null) {
            synchronized (Picasso.class) {
                if (picasso == null) {
                    context = context.getApplicationContext();
                    picasso = new Picasso.Builder(context)
                            .downloader(createDownloader(context)).build();
                    return picasso;
                }
            }
        }
        return picasso;
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

    private static UrlConnectionDownloaderWithSetting createUrlConnectionDownloader(Context context) {
        UrlConnectionDownloaderWithSetting downloader = new UrlConnectionDownloaderWithSetting(context);
        downloader.setMaxAge(MAX_AGE);
        downloader.setMaxSize(MAX_SIZE);
        return downloader;
    }

    private static OkHttpDownloaderWithSetting createOkHttpDownloader(Context context) {
        OkHttpDownloaderWithSetting downloader = new OkHttpDownloaderWithSetting(context, MAX_SIZE);
        downloader.setMaxAge(MAX_AGE);
        return downloader;
    }

}