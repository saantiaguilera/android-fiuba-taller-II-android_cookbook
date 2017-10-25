package com.saantiaguilera.mvp.presenter;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.saantiaguilera.mvp.view.BaseViewContract;
import com.squareup.coordinators.Coordinator;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.OutsideLifecycleException;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.lang.ref.WeakReference;

import io.reactivex.functions.Function;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Base presenter, all presenters should extend from this class
 */
public abstract class BasePresenter<View extends BaseViewContract> extends Coordinator {

    @Nullable
    private WeakReference<View> view;

    @NonNull
    private final BehaviorSubject<PresenterEvent> lifecycleSubject;

    /**
     * Constructor that will bind a lifecycle subject for compositions
     */
    public BasePresenter() {
        lifecycleSubject = BehaviorSubject.createDefault(PresenterEvent.ATTACH);
    }

    /**
     * Attach a view to us
     * @param view attached
     */
    @Override
    public final void attach(android.view.View view) {
        super.attach(view);
        lifecycleSubject.onNext(PresenterEvent.ATTACH);
        this.view = new WeakReference<>((View) view);
        onViewAttached(this.view.get());
    }

    /**
     * Detach the view from us
     * @param view detached
     */
    @Override
    public final void detach(android.view.View view) {
        super.detach(view);
        lifecycleSubject.onNext(PresenterEvent.DETACH);
        this.view = null;
        onViewDetached((View) view);
    }

    /**
     * Get the attached view
     * @return the view if attached, null otherwise
     */
    protected @Nullable View getView() {
        return view != null ? view.get() : null;
    }


    /**
     * Bind to a view, the observable will be unsuscribed when the view is detached from its root
     */
    @NonNull
    @CheckResult
    protected final <T> LifecycleTransformer<T> bindToView(@NonNull View view) {
        return RxLifecycleAndroid.bindView((android.view.View) view);
    }

    /**
     * Useful when using views that are reusable and dont detach (eg in a recyclerview)
     * Or for binding stuff that are agnostic to the view (eg an interactor)
     */
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycle.bind(lifecycleSubject, CONTROLLER_LIFECYCLE);
    }

    /**
     * Overrideable method that will be called whenever the view is attached
     * @param view attached
     */
    protected abstract void onViewAttached(@NonNull View view);

    /**
     * Overrideable method that will be called whenever the view is detached
     * @param view detached
     */
    protected void onViewDetached(@NonNull View view) {}

    /**
     * Controller lifecycle used in Rx compositions
     */
    private static final Function<PresenterEvent, PresenterEvent> CONTROLLER_LIFECYCLE =
            new Function<PresenterEvent, PresenterEvent>() {
                @Override
                public PresenterEvent apply(PresenterEvent lastEvent) {
                    switch (lastEvent) {
                        case ATTACH:
                            return PresenterEvent.DETACH;
                        default:
                            throw new OutsideLifecycleException("Cannot bind to lifecycle when outside of it.");
                    }
                }
            };

    /**
     * Enum for the presenter composition events
     */
    private enum PresenterEvent {
        ATTACH,
        DETACH
    }

}
