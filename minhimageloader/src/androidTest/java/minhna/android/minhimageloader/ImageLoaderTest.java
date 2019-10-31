package minhna.android.minhimageloader;

import android.content.Context;
import android.widget.ImageView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ImageLoaderTest {
    @Test
    public void loadImage() {
        Context appContext = ApplicationProvider.getApplicationContext();

        String url = "https://justa.io/api/uploads/company/office_space_img/558/1004d539-bcb2-40ff-bc20-fc363a37b35d.png";
        ImageView img = new ImageView(appContext);
        ImageLoader.Companion.getInstance(6)
                .displayImage(url, img, R.drawable.ic_launcher_background);
    }
}
