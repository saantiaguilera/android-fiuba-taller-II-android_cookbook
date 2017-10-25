package com.saantiaguilera.mvp.view.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.jakewharton.rxbinding2.view.RxView;
import com.saantiaguilera.mvp.contract.product.TextBolderContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * View that implements the particular view contract of a text bolder
 */
public class TextBolderView extends AppCompatTextView implements TextBolderContract.View {

    @State
    private int state;

    public TextBolderView(Context context) {
        this(context, null);
    }

    public TextBolderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextBolderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // Start with the state off
        setState(STATE_OFF);
    }

    @NonNull
    @Override
    public Observable<Integer> observeClicks() {
        return RxView.clicks(this)
                // Do the map in the main thread
                .observeOn(AndroidSchedulers.mainThread())
                // Change the state to the opposite
                .map(new Function<Object, Integer>() {
                    @Override
                    public Integer apply(Object o) throws Exception {
                        state = state == STATE_OFF ? STATE_ON : STATE_OFF;
                        return state;
                    }
                })
                // Set the state whenever it is emmited a click
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        setState(state);
                    }
                });
    }

    /**
     * Set the state and change the text to the corresponding
     * @param state to set
     */
    @Override
    public void setState(int state) {
        this.state = state;
        setText(state == STATE_OFF ? "OFF" : "ON");
    }

}
