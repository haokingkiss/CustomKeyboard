package com.hao.customkeyboard.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

/**
 * 手机屏幕相关工具类
 */
public class ScreenUtils {

    /**
     * 获取屏幕高度px值
     */
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取屏幕宽度px值
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;// 手机的屏幕密度（px像素与dp密度无关像素的比值）;
        return (int) (px / density + 0.5f * (px >= 0 ? 1 : -1));
//    return (int) ((px - 0.5f) / density);
    }

    /**
     * px转sp
     */
    public static int px2sp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity + 0.5f);
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
//    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, float spVal) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spVal * scaledDensity + 0.5f);
//    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取尚未绘制出来的控件的宽
     */
    public static int getMeasuredWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    /**
     * 获取尚未绘制出来的控件的高
     */
    public static int getMeasuredHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 设置透明状态栏(api >= 19方可使用)
     * <p>可在Activity的onCreat()中调用
     * <p>需在顶部控件布局中加入以下属性让内容出现在状态栏之下
     * <p>android:clipToPadding="true"
     * <p>android:fitsSystemWindows="true"
     */
    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 隐藏状态栏
     * <p>也就是设置全屏，一定要在setContentView之前调用，否则报错
     * <p>此方法Activity可以继承AppCompatActivity
     * <p>启动的时候状态栏会显示一下再隐藏，比如QQ的欢迎界面
     * <p>在配置文件中Activity加属性android:theme="@android:style/Theme.NoTitleBar.Fullscreen"
     * <p>如加了以上配置Activity不能继承AppCompatActivity，会报错
     */
    public static void hideStatusBar(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 全屏
     */
    public static void setFullScreen(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        // 首先使 ChildView 不预留空间
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }

        int statusBarHeight = ScreenUtils.getStatusBarHeight(activity);
        // 需要设置这个 flag 才能设置状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 避免多次调用该方法时,多次移除了 View
        if (mChildView != null && mChildView.getLayoutParams() != null
                && mChildView.getLayoutParams().height == statusBarHeight) {
            // 移除假的 View.
            mContentView.removeView(mChildView);
            mChildView = mContentView.getChildAt(0);
        }
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView
                    .getLayoutParams();
            // 清除 ChildView 的 marginTop 属性
            if (lp != null && lp.topMargin >= statusBarHeight) {
                lp.topMargin -= statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }
    }
}