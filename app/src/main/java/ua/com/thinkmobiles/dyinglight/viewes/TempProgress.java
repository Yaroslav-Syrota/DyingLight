package ua.com.thinkmobiles.dyinglight.viewes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
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
                    timer = (mLineRight.yEnd - mLineRight.yStart) / 6;
                    Log.d("TTT", String.valueOf(timer));
                    for (int i = timer; i > 0; i--) {
                        Thread.sleep(50);
                        moveEndLine(mLineRight, mLineRight.xEnd, --mLineRight.yEnd);
                        moveEndLine(mLineBottom, mLineBottom.xEnd, --mLineBottom.yEnd);
                        moveEndLine(mLineFirstDiagonal, mLineFirstDiagonal.xEnd, --mLineFirstDiagonal.yEnd);
                        moveRect(mRectBottomRight, mLineRight.xEnd, mLineRight.yEnd);
                        postInvalidate();
                    }
                    Log.d("TTT", String.valueOf(timer));
                    for (int i = timer; i > 0; i--) {
                        Thread.sleep(50);
                        moveEndLine(mLineRight, mLineRight.xEnd, --mLineRight.yEnd);
                        moveEndLine(mLineFirstDiagonal, mLineFirstDiagonal.xEnd, --mLineFirstDiagonal.yEnd);
                        moveRect(mRectBottomRight, mLineRight.xEnd, mLineRight.yEnd);
                        moveLine(mLineBottom, ++mLineBottom.xStart, mLineBottom.yStart, mLineBottom.xEnd, --mLineBottom.yEnd);
                        moveEndLine(mLineLeft, ++mLineLeft.xEnd, mLineLeft.yEnd);
                        moveStartLine(mLineSecondDiagonal, ++mLineSecondDiagonal.xStart, mLineSecondDiagonal.yStart);
                        moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                        postInvalidate();
                    }
                    for (int i = timer; i > 0; i--) {
                        Thread.sleep(50);
                        moveEndLine(mLineRight, mLineRight.xEnd, --mLineRight.yEnd);
                        moveEndLine(mLineFirstDiagonal, mLineFirstDiagonal.xEnd, --mLineFirstDiagonal.yEnd);
                        moveRect(mRectBottomRight, mLineRight.xEnd, mLineRight.yEnd);
                        moveLine(mLineBottom, ++mLineBottom.xStart, mLineBottom.yStart, mLineBottom.xEnd, --mLineBottom.yEnd);
                        moveEndLine(mLineLeft, ++mLineLeft.xEnd, mLineLeft.yEnd);
                        moveStartLine(mLineSecondDiagonal, ++mLineSecondDiagonal.xStart, mLineSecondDiagonal.yStart);
                        moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                        postInvalidate();
                    }
                    for (int i = timer; i > 0; i--) {
                        Thread.sleep(50);
                        moveStartLine(mLineBottom, ++mLineBottom.xStart, mLineBottom.yStart);
                        moveLine(mLineLeft, mLineLeft.xStart, ++mLineLeft.yStart, ++mLineLeft.xEnd, mLineLeft.yEnd);
                        moveStartLine(mLineTop, mLineTop.xStart, ++mLineTop.yStart);
                        moveStartLine(mLineFirstDiagonal, mLineFirstDiagonal.xStart, ++mLineFirstDiagonal.yStart);
                        moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                        moveStartLine(mLineSecondDiagonal, ++mLineSecondDiagonal.xStart, mLineSecondDiagonal.yStart);
                        moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                        postInvalidate();
                    }
                    for (int i = timer; i > 0; i--) {
                        Thread.sleep(50);
                        moveStartLine(mLineLeft, mLineLeft.xStart, ++mLineLeft.yStart);
//                        moveLine(mLineLeft, mLineLeft.xStart, ++mLineLeft.yStart, ++mLineLeft.xEnd, mLineLeft.yEnd);
//                        moveStartLine(mLineTop, mLineTop.xStart, ++mLineTop.yStart);
//                        moveStartLine(mLineFirstDiagonal, mLineFirstDiagonal.xStart, ++mLineFirstDiagonal.yStart);
                        moveRect(mRectTopLeft, mLineLeft.xStart, mLineLeft.yStart);
//                        moveStartLine(mLineSecondDiagonal, ++mLineSecondDiagonal.xStart, mLineSecondDiagonal.yStart);
//                        moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                        postInvalidate();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void moveStartLine(Line _line, int _x, int _y) {
        _line.xStart = _x;
        _line.yStart = _y;
    }

    private void moveEndLine(Line _line, int _x, int _y) {
        _line.xEnd = _x;
        _line.yEnd = _y;
    }

    private void moveLine(Line _line, int _xStart, int _yStart, int _xEnd, int _yEnd) {
        _line.xStart = _xStart;
        _line.yStart = _yStart;
        _line.xEnd = _xEnd;
        _line.yEnd = _yEnd;
    }

    private void moveRect(Rect _rect, int _x, int _y) {
        offset = (_rect.right - _rect.left) / 2;
        _rect.left = _x - offset;
        _rect.top = _y - offset;
        _rect.right = _x + offset;
        _rect.bottom = _y + offset;
    }
}


