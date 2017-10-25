package com.saantiaguilera.mvp.presenter.product;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.saantiaguilera.mvp.contract.product.TextBolderContract;
import com.saantiaguilera.mvp.presenter.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import static com.saantiaguilera.mvp.contract.product.TextBolderContract.View.STATE_OFF;
import static com.saantiaguilera.mvp.contract.product.TextBolderContract.View.STATE_ON;

/**
 * This is our presenter, it will get attached to the view automatically and when attached
 * it should observe its clicks and respond to it
 */
public class TextBolderPresenter extends BasePresenter<TextBolderContract.View>
        implements TextBolderContract.Presenter {

    /**
     * Our state subject is in charge of "emiting" the state to the people observing our state
     * (the ones that call observeState())
     */
    @Nullable
    private PublishSubject<Boolean> stateSubject;

    public TextBolderPresenter() {
        stateSubject = PublishSubject.create();
    }

    @Override
    protected void onViewAttached(@NonNull TextBolderContract.View view) {
        view.observeClicks()
                // We will run everything from now on in a separate thread for computations
                .observeOn(Schedulers.computation())
                // This makes us not leak memory, since this "observable of clicks" will live forever
                // and we want that as soon as the presenter gets the view detached, to destroy itself
                // You can also bind to a specific view instead of the presenter lifecycle.
                .compose(this.<Integer>bindToLifecycle())
                // We map the clicks to a boolean of bold "on" / "off"
                .map(new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer integer) throws Exception {
                        return integer == STATE_ON;
                    }
                })
                // We will 'subscribe' (run the code for starting this observable and stuff)
                // on the main thread
                .subscribeOn(AndroidSchedulers.mainThread())
                // We subscribe and for each click, if there is a state subject we emit the boolean
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (stateSubject != null) {
                            stateSubject.onNext(aBoolean);
                        }
                    }
                });
    }

    /**
     * Set the view to enabled / disabled
     * @param enabled
     */
    @Override
    public void setEnabled(boolean enabled) {
        TextBolderContract.View view = getView();
        if (view != null ) {
            view.setState(enabled ? STATE_ON : STATE_OFF);
        }
    }

    /**
     * @return our state subject that will emit booleans for each click
     */
    @NonNull
    @Override
    public Observable<Boolean> observeState() {
        return stateSubject;
    }

}
