package com.example.extras.images.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.extras.test.MainApplication;
import com.example.extras.R;
import com.example.extras.images.fresco.FrescoImageController;
import com.example.extras.images.picasso.CircleTransform;
import com.facebook.drawee.view.DraweeView;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image);

        loadWithFresco();
        loadWithPicasso();
    }

    /**
     * The perks of Fresco over Picasso and others is that:
     * - In older android systems where they had the Dalvik VM,
     * memmory was handled way differently (for example, the garbage
     * collection paused twice all threads when running). With the newer VM (ART) changes
     * were made to the GC (1 pause instead of two, Paralellized processing during the pause, etc)
     *
     * The main problem of this is that, images consume way too much memory. If you have a list
     * and each item has an image, simply a scroll should trigger the GC. Fresco attacks this issue
     * by loading images in the ashmem (Anonymous SHared MEMory, this means native memory).
     *
     * So for api 14 onwards, you should definitely use Fresco over anything else
     *
     * - You can customize everything particularly of the bussiness (placeholder / error / actual /
     * image on top / etc) over the SimpleDraweeView (the Fresco "ImageView")
     * - You can customize everything non bussiness (timeout / url / resize / rotation / cache /
     * etc) in the Pipeline. I've created a FrescoImageController wrapping easily each of the
     * available features. I tried to make it an exact copy of Picasso, but I decoupled it from
     * the Context, so you can use it from wherever you like
     * - Supports WebP with transparency from 14 onwards (android supports it from api 17+)
     * - Api is way too rich.
     *
     * Cons:
     * - Its highly coupled to their DraweeViews, you cant use an ImageView that doesnt extend theirs
     * - You cant use level-list, layer-list, or xmls that contain UI conditions for drawables.
     * This is OK since you shouldnt need Fresco for this type of images, but bear in mind
     * - Since its not made for beginners, the api is a bit more tough than Picasso's
     * - WebP library is way too big, so although it has a huge advantage of support, its size
     * is still a problem
     * - Requires library initialization see {@link MainApplication#onCreate()}
     */
    private void loadWithFresco() {
        FrescoImageController.create()
                .load("http://frescolib.org/static/sample-images/fresco_logo_anim_full_frames_with_pause_l.gif")
                .resize(200, 200)
                .into((DraweeView) findViewById(R.id.activity_image_frescoImageView));
    }

    /**
     * Pros of Picasso over Fresco:
     * - Smaller library
     * - Not too rich api, but definitely fits most of needs
     * - Highly decoupled, you can run it on ImageView
     * - Way too understandable. Really easy to use
     *
     * Cons:
     * - Dalvik VMs will suffer from images in the heap, since Picasso doesnt use ashmem for loading
     * them as Fresco
     * - Needs Context for loading an Image (its not a big con, but its still one)
     * - Doesnt give support to other resources, such as WebP for example, or basic transformations
     * such as a rounded image
     */
    private void loadWithPicasso() {
        Picasso.with(this)
                .load("https://avatars2.githubusercontent.com/u/82592")
                .resize(200, 200)
                .transform(new CircleTransform())
                .into((ImageView) findViewById(R.id.activity_image_picassoImageView));
    }
}
