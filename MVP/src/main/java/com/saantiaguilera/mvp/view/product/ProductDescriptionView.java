package com.saantiaguilera.mvp.view.product;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.saantiaguilera.mvp.contract.product.ProductDescriptionContract;

/**
 * Created by saguilera on 10/25/17.
 */
public class ProductDescriptionView extends AppCompatTextView
        implements ProductDescriptionContract.View {

    public ProductDescriptionView(Context context) {
        this(context, null);
    }

    public ProductDescriptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProductDescriptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setDescription(@NonNull String description) {
        setText(description);
    }

    @Override
    public void setTextBold(boolean enabled) {
        setTypeface(null, enabled ? Typeface.BOLD : Typeface.NORMAL);
    }
}
