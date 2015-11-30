package com.hadlink.library.adapter.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hadlink.library.adapter.utils.ViewEventListener;


public abstract class BindableLayout<T> extends FrameLayout {

    protected ViewEventListener<T> viewEventListener;
    protected T item;
    protected int position;
    protected Context mContext;

    public BindableLayout(Context context) {
        super(context);
        initView(context);
    }

    public BindableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BindableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BindableLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void initView(Context context) {
        mContext = context;
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            inflate(context, layoutId, this);
        }
        // This fix is needed because FrameLayout is fucked up with the RecyclerView (ignores width)
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        onViewInflated();
    }

    public void onViewInflated() {

    }

    @LayoutRes
    public abstract int getLayoutId();

    public void bind(T item, int position) {
        this.item = item;
        this.position = position;
        bind(item);
    }

    public abstract void bind(T item);

    @Nullable
    public ViewEventListener<T> getViewEventListener() {
        return viewEventListener;
    }

    public void setViewEventListener(ViewEventListener<T> viewEventListener) {
        this.viewEventListener = viewEventListener;
    }


    public void notifyItemAction(int actionId, T theItem, View view) {
        if (viewEventListener != null) {
            viewEventListener.onViewEvent(actionId, theItem, position, view);
        }
    }

    public void notifyItemAction(int actionId, View view) {
        notifyItemAction(actionId, item, view);
    }

    public void notifyItemAction(int actionId) {
        notifyItemAction(actionId, item, this);
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
