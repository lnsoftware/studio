package com.imxiaomai.convenience.store.scan.zxing.view;

import java.util.Collection;
import java.util.HashSet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.zxing.camera.CameraManager;

public final class ViewfinderView extends View {
    /**
     * 界面刷新时间
     */
    private static final long ANIMATION_DELAY = 10L;
    private static final int OPAQUE = 0xFF;

    /**
     * 绘制四个边角的长度
     */
    private int ScreenRate;

    /**
     * 绘制四个边角的宽度
     */
    private static final int CORNER_WIDTH = 5;

    /**
     * 扫描线每次移动的距离
     */
    private static final int SPEEN_DISTANCE = 5;

    /**
     * 手机屏幕密度
     */
    private static float density;
    /**
     * 字体大小
     */
    private static final int TEXT_SIZE = 16;
    /**
     * 字体距离扫描框的距离
     */
    private static final int TEXT_PADDING_TOP = 30;

    /**
     * 绘制画笔
     */
    private Paint paint;

    /**
     * 中间线到扫描框最顶部的位置
     */
    private int slideTop;

    /**
     * 中间线到扫描框最底部的位置
     */
    @SuppressWarnings("unused")
    private int slideBottom;
    private Bitmap resultBitmap;
    private final int maskColor;
    private final int resultColor;
    private final int resultPointColor;
    private Collection<ResultPoint> possibleResultPoints;
    private Collection<ResultPoint> lastPossibleResultPoints;
    boolean isFirst;

    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        density = context.getResources().getDisplayMetrics().density;
        // 将像素转换成dp
        ScreenRate = (int) (15 * density);
        // ScreenRate = ViewUtil.px2dip(getContext(), 25);
        paint = new Paint();
        Resources resources = getResources();
        maskColor = resources.getColor(R.color.zxinglib_viewfinder_mask);
        resultColor = resources.getColor(R.color.zxinglib_result_view);
        resultPointColor = resources.getColor(R.color.zxinglib_possible_result_points);
        possibleResultPoints = new HashSet<ResultPoint>(5);
    }

    @Override
    public void onDraw(Canvas canvas) {
        /**
         * 设置可以在xml中预览
         */
        if (isInEditMode()) {
            return;
        }
        /**
         * 获取中间扫描框
         */
        Rect frame = CameraManager.get().getFramingRect();
        if (frame == null) {
            return;
        }

        /**
         * 扫描线的上下位置，边框顶部和底部
         */
        if (!isFirst) {
            isFirst = true;
            slideTop = frame.top;
            slideBottom = frame.bottom;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        // 控制画笔颜色
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        /**
         * 绘制扫描框外面的阴影部分
         */
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            paint.setAlpha(OPAQUE);
            canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
        } else {
            /**
             * 绘制四个角的边框
             */
            paint.setColor(Color.WHITE);
            // 左上角横向
            canvas.drawRect(frame.left, frame.top, frame.left + ScreenRate,
                    frame.top + CORNER_WIDTH, paint);
            // 左上角垂直方向
            canvas.drawRect(frame.left, frame.top, frame.left + CORNER_WIDTH,
                    frame.top + ScreenRate, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.top, frame.right,
                    frame.top + CORNER_WIDTH, paint);
            canvas.drawRect(frame.right - CORNER_WIDTH, frame.top, frame.right,
                    frame.top + ScreenRate, paint);
            canvas.drawRect(frame.left, frame.bottom - CORNER_WIDTH, frame.left
                    + ScreenRate, frame.bottom, paint);
            canvas.drawRect(frame.left, frame.bottom - ScreenRate, frame.left
                    + CORNER_WIDTH, frame.bottom, paint);
            canvas.drawRect(frame.right - ScreenRate, frame.bottom
                    - CORNER_WIDTH, frame.right, frame.bottom, paint);
            canvas.drawRect(frame.right - CORNER_WIDTH, frame.bottom
                    - ScreenRate, frame.right, frame.bottom, paint);

            slideTop += SPEEN_DISTANCE;
            if (slideTop >= frame.bottom) {
                slideTop = frame.top;
            }
            /**
             * 绘制扫描线
             */
            Rect lineRect = new Rect();
            lineRect.left = frame.left;
            lineRect.right = frame.right;
            lineRect.top = slideTop;
            lineRect.bottom = slideTop + 18;
            canvas.drawBitmap(((BitmapDrawable) (getResources()
                    .getDrawable(R.drawable.zxinglib_scanning_line))).getBitmap(), null,
                    lineRect, paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(TEXT_SIZE * density);
            paint.setAlpha(0x40);
            paint.setTypeface(Typeface.create("System", Typeface.BOLD));

            String text = getResources().getString(R.string.zxing_scan_text);
            String textCamera = getResources().getString(R.string.zxing_scan_text_camera);

            /**
             * 绘制文字背景
             */
//            Rect textBg = new Rect();
//            textBg.left = frame.left - 10;
//            textBg.right = frame.right + 10;
//            textBg.top = (int) (frame.bottom + TEXT_PADDING_TOP * density);
//            textBg.bottom = (int) (frame.bottom + TEXT_PADDING_TOP * density) + 70;
//            canvas.drawBitmap(((BitmapDrawable) (getResources()
//                    .getDrawable(R.drawable.zxinglib_scanning_note_bg))).getBitmap(),
//                    null, textBg, paint);

            paint.setColor(Color.WHITE);
            int textSize = dip2px(getContext(), 16);
            paint.setTextSize(textSize);
            float textWidth = paint.measureText(text);
            float textCameraWidth = paint.measureText(textCamera);

            int marginTopFrame = dip2px(getContext(), 30);
            int marginTopText = marginTopFrame + textSize + dip2px(getContext(), 4);

            canvas.drawText(
                    text,
                    (width - textWidth) / 2,
                    (float) (frame.bottom + (float) TEXT_PADDING_TOP * density + marginTopFrame),
                    paint);

            canvas.drawText(
                    textCamera,
                    (width - textCameraWidth) / 2,
                    (float) (frame.bottom + (float) TEXT_PADDING_TOP * density + marginTopText),
                    paint);

            Collection<ResultPoint> currentPossible = possibleResultPoints;
            Collection<ResultPoint> currentLast = lastPossibleResultPoints;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints = new HashSet<ResultPoint>(5);
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(OPAQUE);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 6.0f, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(OPAQUE / 2);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frame.left + point.getX(), frame.top
                            + point.getY(), 3.0f, paint);
                }
            }

            // 局部刷新界面
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);

        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void drawViewfinder() {
        resultBitmap = null;
        invalidate();
    }

    /**
     * Draw a bitmap with the result points highlighted instead of the live
     * scanning display.
     * 
     * @param barcode
     *            An image of the decoded barcode.
     */
    public void drawResultBitmap(Bitmap barcode) {
        resultBitmap = barcode;
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {
        possibleResultPoints.add(point);
    }

}
