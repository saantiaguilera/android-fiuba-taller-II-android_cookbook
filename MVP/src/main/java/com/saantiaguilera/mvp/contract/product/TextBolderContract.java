package com.saantiaguilera.mvp.contract.product;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import com.saantiaguilera.mvp.presenter.BasePresenterContract;
import com.saantiaguilera.mvp.view.BaseViewContract;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.reactivex.Observable;

/**
 * Interface for the contracts of a text bolder. This presenter/view
 * allows us to know when text should be bold or not
 */
public interface TextBolderContract {

    interface Presenter extends BasePresenterContract {
        // Sets enabled or not the bolder
        void setEnabled(boolean enabled);
        // Lets us observe changes in the presenter
        @NonNull Observable<Boolean> observeState();
    }

    interface View extends BaseViewContract {
        // Lets us observe clicks in the view
        @NonNull
        Observable<Integer> observeClicks();

        void setState(@State int state);

        /**
         * From here downwards its a state of the view. The presenter will use it
         * in references so its here for visibility.
         *
         * It could also be defined outside of this scope.. Its up to you
         */

        int STATE_OFF = 0;
        int STATE_ON = 1;

        @IntDef({STATE_OFF, STATE_ON})
        @Retention(RetentionPolicy.SOURCE)
        @interface State {}
    }

}
