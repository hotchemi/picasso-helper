package helper.picasso.picasso_helper;

import android.app.Application;
import android.test.ApplicationTestCase;

import helper.picasso.PicassoHelper;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        PicassoHelper.getInstance()
                .setMaxAge(0)
                .setMaxSize(0)
                .with(getContext())
                .load(null)
                .fit()
                .into(null);

        super(Application.class);
    }
}