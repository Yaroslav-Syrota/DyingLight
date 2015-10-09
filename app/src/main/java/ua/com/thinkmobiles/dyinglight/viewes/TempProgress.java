package ua.com.thinkmobiles.dyinglight.viewes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by CAT_Caterpiller on 08.10.2015.
 */
public class TempProgress extends View {
    private Paint mPaint;
    private Rect mRectTopLeft, mRectTopRight, mRectBottomLeft, mRectBottomRight;
    private Line mLineFirstDiagonal, mLineSecondDiagonal, mLineTop, mLineLeft, mLineRight, mLineBottom;
    private int offset;
    private int timer;
    private int alpha = 225;

    public TempProgress(Context context) {
        super(context);
        initFigure();
    }

    public TempProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFigure();
    }

    private void initFigure() {
        mPaint = new Paint();

        mRectTopLeft = new Rect(50, 50, 80, 80);
        mRectTopRight = new Rect(300, 50, 330, 80);
        mRectBottomLeft = new Rect(50, 300, 80, 330);
        mRectBottomRight = new Rect(300, 300, 330, 330);

        mLineFirstDiagonal = new Line(65, 65, 315, 315);
        mLineSecondDiagonal = new Line(65, 315, 315, 65);

        mLineTop = new Line(65, 65, 315, 65);
        mLineLeft = new Line(65, 65, 65, 315);
        mLineRight = new Line(315, 65, 315, 315);
        mLineBottom = new Line(65, 315, 315, 315);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.GREEN);

        canvas.drawRect(mRectTopLeft, mPaint);
        canvas.drawRect(mRectTopRight, mPaint);
        canvas.drawRect(mRectBottomLeft, mPaint);
        canvas.drawRect(mRectBottomRight, mPaint);

        myDrawLine(canvas, mLineFirstDiagonal, mPaint);
        myDrawLine(canvas, mLineSecondDiagonal, mPaint);
        myDrawLine(canvas, mLineTop, mPaint);
        myDrawLine(canvas, mLineLeft, mPaint);
        myDrawLine(canvas, mLineRight, mPaint);
        myDrawLine(canvas, mLineBottom, mPaint);
//        canvas.drawRect(130, 130, 250, 250, mPaint);

    }

    private void myDrawLine(Canvas _canvas, Line _line, Paint _paint) {
        _canvas.drawLine(_line.xStart, _line.yStart, _line.xEnd, _line.yEnd, _paint);
    }

    public void showAnim() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    for (int a = 50; a < 250; a++) {
                        if(alpha == 0) {
                            alpha = 225;
                        }
                        mPaint.setAlpha((int) alpha);
                        Thread.sleep(100);
                        mRectTopLeft.left = a;
                        mRectTopLeft.top = a;
                        mRectTopLeft.bottom = a + 30;
                        mRectTopLeft.right = a + 30;
                        postInvalidate();
                        alpha--;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void mooveLine(Line _line, int _x, int _y) {
        _line.xStart = _x;
        _line.yStart = _y;
    }

    private void revertMooveLine(Line _line, int _x, int _y) {
        _line.xEnd = _x;
        _line.yEnd = _y;
    }

    private void mooveRect(Rect _rect, int _x, int _y) {
        offset = _rect.right - _rect.left;
        _rect.left = _x;
        _rect.top = _y;
        _rect.right = _x + offset;
        _rect.bottom = _y = offset;
    }
}


