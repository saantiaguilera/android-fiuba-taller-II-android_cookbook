package com.example.extras.images.fresco;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.OperationCanceledException;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.MediaVariations;
import com.facebook.imagepipeline.request.Postprocessor;
import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Immutable utility class to load an image with a desired callback.
 *
 * Simple usage example;
 FrescoImageController.withProvider()
 .load(uriToLoadImageFrom); //Supports uri/url/resId/file
 .listener(new FrescoImageController.Callback() {
 //Override the onSuccess and the onFailure and do what you want
 })
 //There are a lot more stuff, check it out
 .into(theView);
 *
 * Created by saguilera on 8/1/15.
 */
public class FrescoImageController {

    private @NonNull WeakReference<? extends DraweeView> view;

    private final @NonNull FrescoControllerListener frescoCallback;

    private @NonNull Uri uri;
    private @Nullable MediaVariations mediaVariations;
    private @Nullable Priority priority;

    private @Nullable ResizeOptions resizeOptions;
    private @Nullable RotationOptions rotationOptions;
    private @Nullable ImageDecodeOptions decodeOptions;
    private @Nullable Postprocessor postprocessor;

    private boolean tapToRetry;
    private boolean progressiveRendering;
    private boolean localThumbnailPreview;
    private boolean autoPlayAnimations;

    private boolean noCache;
    private boolean noDiskCache;
    private boolean noMemoryCache;
    private @Nullable ImageRequest.CacheChoice cacheChoice;

    /**
     * Static method to withProvider an empty builder. The same can be achieved by doing
     * new FrescoImageController.Builder();
     *
     * @return empty builder
     */
    public static @NonNull Builder create() {
        return new Builder();
    }

    /**
     * Package visibility constructor. Since its an immutable object, use builders.
     */
    FrescoImageController(@NonNull final Uri uri, @NonNull DraweeView view,
        @Nullable Callback callback,
        @Nullable ResizeOptions resizeOpt, @Nullable ImageDecodeOptions decodeOpt,
        @Nullable Postprocessor postprocessor,
        boolean ttr, boolean pr, boolean ltp,
        boolean noCache, boolean noDiskCache, boolean noMemoryCache,
        boolean autoPlayAnimations,
        @Nullable ImageRequest.CacheChoice cacheChoice, @Nullable Priority priority,
        @Nullable RotationOptions rotationOptions, @Nullable MediaVariations mediaVariations) {
        this.view = new WeakReference<>(view);
        this.uri = uri;
        this.mediaVariations = mediaVariations;
        this.priority = priority;

        this.resizeOptions = resizeOpt;
        this.rotationOptions = rotationOptions;
        this.decodeOptions = decodeOpt;
        this.postprocessor = postprocessor;

        this.tapToRetry = ttr;
        this.progressiveRendering = pr;
        this.localThumbnailPreview = ltp;
        this.autoPlayAnimations = autoPlayAnimations;

        this.noCache = noCache;
        this.noDiskCache = noDiskCache;
        this.noMemoryCache = noMemoryCache;
        this.cacheChoice = cacheChoice;

        frescoCallback = new FrescoControllerListener(callback);

        ImageRequestBuilder request = ImageRequestBuilder.newBuilderWithSource(uri)
            .setLocalThumbnailPreviewsEnabled(localThumbnailPreview)
            .setProgressiveRenderingEnabled(progressiveRendering);

        if (noCache || noDiskCache) {
            request.disableDiskCache();
        }

        if (cacheChoice != null) {
            request.setCacheChoice(cacheChoice);
        }

        if (priority != null) {
            request.setRequestPriority(priority);
        }

        if (postprocessor != null) {
            request.setPostprocessor(postprocessor);
        }

        if (rotationOptions != null) {
            request.setRotationOptions(rotationOptions);
        }

        if (mediaVariations != null) {
            request.setMediaVariations(mediaVariations);
        }

        if (decodeOptions != null) {
            request.setImageDecodeOptions(decodeOptions);
        }

        if (resizeOptions != null) {
            request.setResizeOptions(resizeOptions);
        }

        DraweeController controller = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setImageRequest(request.build())
            .setTapToRetryEnabled(tapToRetry)
            .setOldController(view.getController())
            .setControllerListener(frescoCallback)
            .setAutoPlayAnimations(autoPlayAnimations)
            .build();

        view.setController(controller);
    }

    /**
     * Gets the attached view
     *
     * @return attached view or null if its already gced by the os
     */
    public @Nullable DraweeView getView() {
        return view.get();
    }

    /**
     * Getter
     */
    @Nullable
    public ResizeOptions getResizeOptions() {
        return resizeOptions;
    }

    /**
     * Getter
     */
    @Nullable
    public Postprocessor getPostprocessor() {
        return postprocessor;
    }

    /**
     * Getter
     */
    @Nullable
    public MediaVariations getMediaVariations() {
        return mediaVariations;
    }

    /**
     * Getter
     */
    @Nullable
    public Priority getPriority() {
        return priority;
    }

    /**
     * Getter
     */
    @Nullable
    public ImageRequest.CacheChoice getCacheChoice() {
        return cacheChoice;
    }

    /**
     * Getter
     */
    @Nullable
    public RotationOptions getRotationOptions() {
        return rotationOptions;
    }

    /**
     * Getter
     */
    @Nullable
    public ImageDecodeOptions getDecodeOptions() {
        return decodeOptions;
    }

    /**
     * Getter
     */
    @NonNull
    public Uri getUri() {
        return uri;
    }

    /**
     * Getter
     */
    public boolean isCacheEnabled() {
        return !noCache;
    }

    /**
     * Getter
     */
    public boolean isAutoPlayAnimations() {
        return autoPlayAnimations;
    }

    /**
     * Getter
     */
    public boolean isMemoryCacheEnabled() {
        return !noMemoryCache;
    }

    /**
     * Getter
     */
    public boolean isDiskCacheEnabled() {
        return !noDiskCache;
    }

    /**
     * Getter
     */
    public boolean isLocalThumbnailPreviewEnabled() {
        return localThumbnailPreview;
    }

    /**
     * Getter
     */
    public boolean isProgressiveRenderingEnabled() {
        return progressiveRendering;
    }

    /**
     * Getter
     */
    public boolean isTapToRetryEnabled() {
        return tapToRetry;
    }

    /**
     * Perform an explicit success callback
     */
    public void success() {
        frescoCallback.success(null, null);
    }

    /**
     * Perform an explicit failure callback
     */
    public void failure() {
        frescoCallback.failure(new OperationCanceledException("Called failure explicitly from " + getClass().getSimpleName()));
    }

    /**
     * Create builder from state.
     *
     * Note this wont set the current view.
     *
     * @return new builder with current state
     */
    public @NonNull Builder newBuilder() {
        Builder builder = new Builder()
            .load(getUri())
            .tapToRetry(isTapToRetryEnabled())
            .autoPlayAnimations(isAutoPlayAnimations())
            .progressiveRendering(isProgressiveRenderingEnabled())
            .localThumbnailPreview(isLocalThumbnailPreviewEnabled());

        if (!isCacheEnabled()) {
            builder.noCache();
        }

        if (!isDiskCacheEnabled()) {
            builder.noDiskCache();
        }

        if (!isMemoryCacheEnabled()) {
            builder.noMemoryCache();
        }

        if (getMediaVariations() != null) {
            builder.mediaVariations(getMediaVariations());
        }

        if (getRotationOptions() != null) {
            builder.rotationOptions(getRotationOptions());
        }

        if (getCacheChoice() != null) {
            builder.cacheChoice(getCacheChoice());
        }

        if (getPriority() != null) {
            builder.priority(getPriority());
        }

        if (getDecodeOptions() != null) {
            builder.decodeOptions(getDecodeOptions());
        }

        if (getResizeOptions() != null) {
            builder.resize(getResizeOptions().width, getResizeOptions().height);
        }

        if (getPostprocessor() != null) {
            builder.postprocessor(getPostprocessor());
        }

        if (frescoCallback.getCallback() != null) {
            builder.listener(frescoCallback.getCallback());
        }

        return builder;
    }

    /**
     * Builder class to withProvider an immutable FrescoController
     */
    public static class Builder {

        private @Nullable Uri mUri = null;
        private @Nullable Callback listener = null;
        private @Nullable ResizeOptions resizeOptions = null;
        private @Nullable RotationOptions rotationOptions = RotationOptions.autoRotate();
        private @Nullable ImageRequest.CacheChoice cacheChoice = null;
        private @Nullable Priority priority = null;
        private @Nullable MediaVariations mediaVariations = null;
        private boolean tapToRetry = false;
        private boolean progressiveRendering = false;
        private boolean localThumbnailPreview = false;
        private boolean noCache = false;
        private boolean noDiskCache = false;
        private boolean noMemoryCache = false;
        private boolean autoPlayAnimations = false;
        private @Nullable ImageDecodeOptions decodeOptions = null;
        private @Nullable Postprocessor postprocessor = null;

        /**
         * Constructor
         */
        public Builder() {}

        /**
         * Set a resId from where the image will be loaded
         * @param resId with the id of the drawable to load
         * @return Builder
         */
        public @NonNull Builder load(int resId) {
            this.mUri = ImageRequestBuilder.newBuilderWithResourceId(resId).build().getSourceUri();
            return this;
        }

        /**
         * Set Uri from where the image will be loaded
         * @param uri with the address to download the image from
         * @return Builder
         */
        public @NonNull Builder load(@NonNull Uri uri) {
            this.mUri = uri;
            return this;
        }

        /**
         * Set Url from where the image will be loaded
         * @param url with the address to download the image from
         * @return Builder
         */
        public @NonNull Builder load(@NonNull String url) {
            this.mUri = Uri.parse(url);
            return this;
        }

        /**
         * Set file from where the image will be loaded
         * @param file with the image
         * @return Builder
         */
        public @NonNull Builder load(@NonNull File file) {
            this.mUri = Uri.fromFile(file);
            return this;
        }

        /**
         * Set if you want to receive callbacks from the loading.
         *
         * @param listener from where callbacks will be observed
         * @return Builder
         */
        public @NonNull Builder listener(@NonNull Callback listener) {
            this.listener = listener;
            return this;
        }

        /**
         * Resize the image before showing it.
         * Of course resize != scale.
         * For scaling just use the layout_width / layout_height of the view. If you need a particular mode
         * you can use the hierarchy.
         *
         * Note: Currently fresco doesnt support png resizing. So take into account that if you will
         * implement this in a place with possible .png images (like local user files) you wont have
         * all images resized. This can lead to problems, eg if you have a jpg and a png image of
         * 16:9 and you resize to 1:1, you will only resize the squared one.
         * A possible solution for this, but please use it carefully is to use a postprocessor that
         * resizes the bitmap if its Uri is a .png, but be careful it can make the cache useless
         *
         * @param width dest to resize
         * @param height dest to resize
         * @return Builder
         */
        public @NonNull Builder resize(int width, int height) {
            this.resizeOptions = new ResizeOptions(width, height);
            return this;
        }

        /**
         * Dont cache the image.
         * By default all images are cached
         *
         * @return Builder
         */
        public @NonNull Builder noCache() {
            this.noCache = true;
            return this;
        }

        /**
         * Dont cache the image in disk.
         * By default all images are cached
         *
         * @return Builder
         */
        public @NonNull Builder noDiskCache() {
            this.noDiskCache = true;
            return this;
        }

        /**
         * Dont cache the image in memory
         * By default all images are cached
         *
         * @return Builder
         */
        public @NonNull Builder noMemoryCache() {
            this.noMemoryCache = true;
            return this;
        }

        /**
         * Set the choice of cache. By default Fresco can store images in a
         * lowres cache and a default. If you know your image is tiny enough, you can
         * set the tiny cache to make fresco more performant.
         * @return Builder
         */
        public @NonNull Builder cacheChoice(@NonNull ImageRequest.CacheChoice cacheChoice) {
            this.cacheChoice = cacheChoice;
            return this;
        }

        /**
         * When having multiple uris that reference a single image but with different dimensions,
         * you can set a media variations for all the variants that if you have already cached
         * a different sized image (of the same variant), when loading a new one instead of downloading
         * it, it will just retrieve the other and onRender it for the new size.
         *
         * @return Builder
         */
        public @NonNull Builder mediaVariations(@NonNull MediaVariations mediaVariations) {
            this.mediaVariations = mediaVariations;
            return this;
        }

        /**
         * Priority for the rendering of the image (for higher priorities, it will be picked faster from the
         * queue for rendering)
         *
         * @return Builder
         */
        public @NonNull Builder priority(@NonNull Priority priority) {
            this.priority = priority;
            return this;
        }

        /**
         * Tap on the image to retry loading it
         * Default: False
         *
         * @param should enable tap to retry
         * @return Builder
         */
        public @NonNull Builder tapToRetry(boolean should) {
            this.tapToRetry = should;
            return this;
        }

        /**
         * Start automatically the animations.
         *
         * This is the same as attaching a listener, checking nullability of animatable and doing
         * animatable.start();
         *
         * @return Builder
         */
        public @NonNull Builder autoPlayAnimations(boolean autoPlayAnimations) {
            this.autoPlayAnimations = autoPlayAnimations;
            return this;
        }

        /**
         * Load the image while its rendering, this is useful if you want to show previews while its
         * rendering
         * Default: false
         *
         * @param should be enabled
         * @return Builder
         */
        public @NonNull Builder progressiveRendering(boolean should) {
            this.progressiveRendering = should;
            return this;
        }

        /**
         * Show local thumbnail if present in the exif data
         * Default: false
         *
         * Fresco limitation:
         * This option is supported only for local URIs, and only for images in the JPEG format.
         *
         * @param should show it
         * @return builder
         */
        public @NonNull Builder localThumbnailPreview(boolean should) {
            this.localThumbnailPreview = should;
            return this;
        }

        /**
         * Use a custom decode options. Create it with ImageDecodeOptionsBuilder.
         * Beware since this handles internal state information. Use at your own risk if needed.
         *
         * @param options for image decoding
         * @return Builder
         */
        public @NonNull Builder decodeOptions(@NonNull ImageDecodeOptions options) {
            this.decodeOptions = options;
            return this;
        }

        /**
         * Describes how the image should be rotated, this happens pre decoding!
         *
         * Works for JPEG only.
         *
         * @return Builder
         */
        public @NonNull Builder rotationOptions(@NonNull RotationOptions rotationOptions) {
            this.rotationOptions = rotationOptions;
            return this;
        }

        /**
         * Since there are more than one postprocessor and processing methods (see
         * BasePostprocessor and BaseRepeatedPostprocessor) and there are three different
         * processing methods, you should feed the builder with the postprocessor instance already created
         * (instead of us defining a particular method and class for you to process the data)
         *
         * Note: DO NOT override more than one of the bitmap processing methods, this WILL lead to
         * undesired behaviours and is prone to errors
         *
         * Note: Fresco may (in a future, but currently it doesnt) support postprocessing
         * on animations.
         *
         * @param postprocessor instance for images
         * @return Builder
         */
        public @NonNull Builder postprocessor(@NonNull Postprocessor postprocessor) {
            this.postprocessor = postprocessor;
            return this;
        }

        /**
         * Attach to a view.
         *
         * Note this will handle the loading of the uri. There MUST be (Mandatory) an existent Uri
         * from where to load the image.
         *
         * You can save the returned instance to retreive the data you have used or to explicitly
         * call the callbacks
         *
         * @param view to attach the desired args
         * @return Controller
         */
        public @NonNull FrescoImageController into(@NonNull DraweeView view) {
            if (mUri == null) {
                throw new IllegalStateException(
                    "Creating controller for drawee with no address to retrieve image from. Forgot to call setUri/setUrl ??");
            }

            return new FrescoImageController(mUri, view,
                listener,
                resizeOptions, decodeOptions,
                postprocessor,
                tapToRetry, progressiveRendering, localThumbnailPreview,
                noCache, noDiskCache, noMemoryCache,
                autoPlayAnimations,
                cacheChoice, priority,
                rotationOptions, mediaVariations);
        }

    }

    /**
     * Interface from where callbacks will be dispatched
     */
    public interface Callback {
        void onSuccess(@Nullable ImageInfo imageInfo, @Nullable Animatable animatable);
        void onFailure(@NonNull Throwable t);
    }

    private class FrescoControllerListener extends BaseControllerListener<ImageInfo> {

        private @Nullable Callback callback;

        public FrescoControllerListener(@Nullable Callback callback) {
            this.callback = callback;
        }

        @Nullable
        public Callback getCallback() {
            return callback;
        }

        public void success(@Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
            if (callback != null) {
                callback.onSuccess(imageInfo, animatable);
            }

            if (!isCacheEnabled() || !isMemoryCacheEnabled()) {
                Fresco.getImagePipeline().evictFromCache(getUri());
            }
        }

        public void failure(Throwable throwable) {
            if (callback != null) {
                callback.onFailure(throwable);
            }
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            success(imageInfo, animatable);
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
            failure(throwable);
        }
    }

}