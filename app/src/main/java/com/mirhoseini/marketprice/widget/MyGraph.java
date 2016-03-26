package com.mirhoseini.marketprice.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.View;

import com.mirhoseini.marketprice.R;
import com.mirhoseini.marketprice.database.model.PriceValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mohsen on 25/03/16.
 */
public class MyGraph extends View implements SurfaceHolder.Callback {
    static final int LINE_WIDTH = 4;

    int width, height;
    int parentWidth, parentHeight;

    int paddingBottom = 50;

//    int xPart, yPart;

    int left, top, bottom, right;

    float startX, startY, deltaX, deltaY;
    float previousTranslateX = 0f;
    float previousTranslateY = 0f;
    boolean isScrolling = false;


    private final Paint linePaint;
    private final Paint axisPaint;
    private final Paint borderPaint;
    private final Paint textPaint;


    private float[] mPoints;
    private List<PriceValue> mPriceValues;
    private long maxX, minX;
    private float maxY, minY;

    private float xFactor, yFactor;

    public MyGraph(final Context context, final AttributeSet aSet) {
        super(context, aSet);

        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.colorPrimary));
        linePaint.setStrokeWidth(LINE_WIDTH);

        borderPaint = new Paint();
        borderPaint.setColor(getResources().getColor(R.color.colorAccent));
        borderPaint.setStrokeWidth(5);

        axisPaint = new Paint();
        axisPaint.setColor(getResources().getColor(R.color.colorAccent));
        axisPaint.setStrokeWidth(1);

        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.colorAccent));
        textPaint.setTextSize(30);

    }

    public List<PriceValue> getPriceValues() {
        return mPriceValues;
    }

    public void setPriceValues(List<PriceValue> priceValues) {
        this.mPriceValues = priceValues;


        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {

        calcPoints();

        drawGraphBorders(canvas);

        drawGraphLines(canvas);

    }

    private void calcPoints() {
        if (mPriceValues != null && mPriceValues.size() > 0) {
            //finding Max and Min
            maxX = minX = mPriceValues.get(0).getX();
            maxY = minY = mPriceValues.get(0).getY();

            for (int i = 1; i < mPriceValues.size(); i++) {
                maxX = Math.max(maxX, mPriceValues.get(i).getX());
                minX = Math.min(minX, mPriceValues.get(i).getX());

                maxY = Math.max(maxY, mPriceValues.get(i).getY());
                minY = Math.min(minY, mPriceValues.get(i).getY());
            }

            maxY = (float) Math.ceil(maxY);
            minY = (float) Math.floor(minY);

            xFactor = (float) width / (float) mPriceValues.size();
            yFactor = (float) height / (maxY - minY);

            mPoints = new float[(mPriceValues.size() - 1) * 4];

            //calculating Lines Points
            for (int i = 0; i < mPriceValues.size() - 1; i++) {
                mPoints[i * 4] = i * xFactor;
                mPoints[i * 4 + 1] = height - (mPriceValues.get(i).getY() - minY) * yFactor;

                mPoints[i * 4 + 2] = (i + 1) * xFactor;
                mPoints[i * 4 + 3] = height - (mPriceValues.get(i + 1).getY() - minY) * yFactor;
            }
        }

    }

    private void drawGraphLines(Canvas canvas) {

        if (mPoints == null) {
            return;
        }

        canvas.drawLines(mPoints, linePaint);

    }

    private void drawGraphBorders(Canvas canvas) {

        //draw borders
        canvas.drawLine(left, top, left, bottom, borderPaint);
        canvas.drawLine(left, bottom, right, bottom, borderPaint);

        //draw axis
        float axisY = minY;
        int stepY = (int) (maxY - minY) / 5;

        while (axisY < maxY) {
            float y = height - (axisY - minY) * yFactor;
            canvas.drawLine(left, y, right, y, axisPaint);

            canvas.drawText(axisY + " USD", left + 5, y - 5, textPaint);

            axisY += stepY;
        }

        int axisX = 0;
        int stepX = mPriceValues.size() / 3;

        while (axisX < mPriceValues.size()) {
            float x = axisX * xFactor;
            canvas.drawLine(x, top, x, bottom, axisPaint);

            SimpleDateFormat sdf = new SimpleDateFormat("MMM d, ''yy");

            canvas.drawText(sdf.format(new Date(mPriceValues.get(axisX).getX() * 1000)), x, bottom + paddingBottom / 2 + 5, textPaint);
            axisX += stepX;
        }

    }

    private void initView() {
        width = getWidth();
        height = getHeight() - paddingBottom;

        left = 0;
        top = 0;
        bottom = height;
        right = width;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        this.setMeasuredDimension(parentWidth, parentHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        initView();
        invalidate();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false); // Allows us to use invalidate() to call
        // onDraw()
        postInvalidate();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                startX = event.getX() - previousTranslateX;
//                startY = event.getY() - previousTranslateY;
//
//                // Log.d(TAG, "Touch X : " + x + " Touch Y : " + y);
//                break;
//            case MotionEvent.ACTION_UP:
//                if (!isScrolling) {
//                    isScrolling = false;
//                    return true;
//                }
//            case MotionEvent.ACTION_CANCEL:
//                // Release the scroll.
//                isScrolling = false;
//                return false;
//            case MotionEvent.ACTION_MOVE:
//                deltaX = event.getX() - startX;
//                deltaY = event.getY() - startY;
//
//                startX = event.getX();
//                startY = event.getY();
//                // Log.d(TAG, "Move deltaX : " + deltaX + " Move deltaY : " +
//                // deltaY);
//
//                int yScroll = getScrollY();
//
//                if (Math.abs(deltaY) > 5 || isScrolling) {
//                    isScrolling = true;
//                    yScroll -= deltaY;
//
//                    if (yScroll <= 0)
//                        yScroll = 0;
//
//                    else if (yScroll >= height - parentHeight)
//                        yScroll = (height - parentHeight);
//
//                    scrollTo(0, yScroll);
//                }
//                break;
//        }
//        return true;
//    }

}