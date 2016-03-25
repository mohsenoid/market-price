package com.mirhoseini.marketprice.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.mirhoseini.marketprice.R;
import com.mirhoseini.marketprice.database.model.PriceValue;

import java.util.List;

/**
 * Created by Mohsen on 25/03/16.
 */
public class MyGraph extends View {
    private final Rect rect = new Rect();
    private final Paint linePaint;
    private final Paint backgroundPaint = new Paint();
    int xIndex, xScale;
    //    private Point[] mPoints;
    private float[] mPoints;
    private List<PriceValue> mPriceValues;
    private long maxX, minX;
    private float maxY, minY;

    public MyGraph(final Context context, final AttributeSet aSet) {
        super(context, aSet);

        linePaint = new Paint();
        linePaint.setColor(getResources().getColor(R.color.colorPrimary));
        linePaint.setStrokeWidth(2);
    }

    public List<PriceValue> getPriceValues() {
        return mPriceValues;
    }

    public void setPriceValues(List<PriceValue> priceValues) {
        this.mPriceValues = priceValues;

        calcMaxMin();

        invalidate();
    }

    private void calcMaxMin() {
        if (mPriceValues != null && mPriceValues.size() > 0) {
            mPoints = new float[mPriceValues.size() * 2];

            maxX = minX = mPriceValues.get(0).getX();
            maxY = minY = mPriceValues.get(0).getY();
            mPoints[0] = mPriceValues.get(0).getX();
            mPoints[1] = mPriceValues.get(0).getY();

            for (int i = 1; i < mPriceValues.size(); i++) {
                maxX = Math.max(maxX, mPriceValues.get(i).getX());
                minX = Math.min(minX, mPriceValues.get(i).getX());

                maxY = Math.max(maxY, mPriceValues.get(i).getY());
                minY = Math.min(minY, mPriceValues.get(i).getY());

                mPoints[i * 2] = mPriceValues.get(i).getX();
                mPoints[i * 2 + 1] = mPriceValues.get(i).getY();
            }
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (mPriceValues == null) {
            return;
        }
        canvas.drawLines(mPoints, linePaint);
//        rect.set((int) (xIndex * xScale), 0, (int) (xIndex * xScale + 5), getHeight());
//        canvas.drawRect(rect, backgroundPaint);
    }

}