package com.example.extras.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.extras.R;
import com.example.extras.images.test.ImageActivity;
import com.example.extras.map.test.MapActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * Created by saguilera on 10/26/17.
 */
public class MainActivity extends AppCompatActivity {

    private static final int REDIRECTION_IMAGE = 0;
    private static final int REDIRECTION_MAP = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.activity_main_imageButton)
                .setOnClickListener(new RedirectListener(this, REDIRECTION_IMAGE));

        findViewById(R.id.activity_main_mapButton)
                .setOnClickListener(new RedirectListener(this, REDIRECTION_MAP));
    }

    public static class RedirectListener implements View.OnClickListener {

        @Redirection
        private int redirection;

        @NonNull
        private WeakReference<Context> contextRef;

        public RedirectListener(@NonNull Context context, @Redirection int redirection) {
            this.redirection = redirection;
            this.contextRef = new WeakReference<>(context);
        }

        @Override
        public void onClick(View view) {
            Context context = contextRef.get();

            if (context == null) {
                return;
            }

            Class<? extends Activity> target = null;
            switch (redirection) {
                case REDIRECTION_IMAGE:
                    target = ImageActivity.class;
                    break;
                case REDIRECTION_MAP:
                    target = MapActivity.class;
            }

            if (target != null) {
                context.startActivity(new Intent(context, target));
            }
        }

    }

    @IntDef({ REDIRECTION_IMAGE, REDIRECTION_MAP })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Redirection {}

}
