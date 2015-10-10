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
public class CustomViewProgress extends View {
    private Paint mPaint, mPaintRect;
    private Rect mRectTopLeft, mRectTopRight, mRectBottomLeft, mRectBottomRight, mRectCenter;
    private Line mLineFirstDiagonal, mLineSecondDiagonal, mLineTop, mLineLeft, mLineRight, mLineBottom;
    private int offset;
    private int timer;
    private int alpha = 225;

    public CustomViewProgress(Context context) {
        super(context);
        initFigure();
    }

    public CustomViewProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFigure();
    }

    private void initFigure() {
        mPaint = new Paint();
        mPaintRect = new Paint();
        mPaintRect.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.GREEN);

        mRectTopLeft = new Rect(50, 50, 80, 80);
        mRectTopRight = new Rect(290, 50, 320, 80);
        mRectBottomLeft = new Rect(50, 290, 80, 320);
        mRectBottomRight = new Rect(290, 290, 320, 320);
        mRectCenter = new Rect(130, 130, 245, 245);

        mLineFirstDiagonal = new Line(65, 65, 305, 305);
        mLineSecondDiagonal = new Line(65, 305, 305, 65);

        mLineTop = new Line(65, 65, 305, 65);
        mLineLeft = new Line(65, 65, 65, 305);
        mLineRight = new Line(305, 65, 305, 305);
        mLineBottom = new Line(65, 305, 305, 305);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(mRectTopLeft, mPaint);
        canvas.drawRect(mRectTopRight, mPaint);
        canvas.drawRect(mRectBottomLeft, mPaint);
        canvas.drawRect(mRectBottomRight, mPaint);
        canvas.drawRect(mRectCenter, mPaintRect);

        myDrawLine(canvas, mLineFirstDiagonal, mPaint);
        myDrawLine(canvas, mLineSecondDiagonal, mPaint);
        myDrawLine(canvas, mLineTop, mPaint);
        myDrawLine(canvas, mLineLeft, mPaint);
        myDrawLine(canvas, mLineRight, mPaint);
        myDrawLine(canvas, mLineBottom, mPaint);
    }


    private void myDrawLine(Canvas _canvas, Line _line, Paint _paint) {
        _canvas.drawLine(_line.xStart, _line.yStart, _line.xEnd, _line.yEnd, _paint);
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


    public void showAnim() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int repeat = 3; repeat > 0; repeat--) {
                        for (int j = 3; j > 0; j--) {
                            for (int i = 0; i <= 225; i++) {
                                mPaintRect.setAlpha(i);
                                Thread.sleep(2);
                                postInvalidate();
                            }
                            Thread.sleep(10);
                            for (int i = 225; i > 0; i--) {
                                mPaintRect.setAlpha(i);
                                Thread.sleep(2);
                                postInvalidate();
                            }
                            Thread.sleep(10);
                        }
                        Thread.sleep(300);

                        timer = (mLineRight.yEnd - mLineRight.yStart) / 6;
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveEndLine(mLineRight, mLineRight.xEnd, --mLineRight.yEnd);
                            moveEndLine(mLineBottom, mLineBottom.xEnd, --mLineBottom.yEnd);
                            moveEndLine(mLineFirstDiagonal, mLineFirstDiagonal.xEnd, --mLineFirstDiagonal.yEnd);
                            moveRect(mRectBottomRight, mLineBottom.xEnd, mLineBottom.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
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
                            Thread.sleep(5);
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
                            Thread.sleep(5);
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
                            Thread.sleep(5);
                            moveStartLine(mLineLeft, mLineLeft.xStart, ++mLineLeft.yStart);
                            moveLine(mLineTop, mLineTop.xStart, ++mLineTop.yStart, --mLineTop.xEnd, mLineTop.yEnd);
                            moveStartLine(mLineRight, --mLineRight.xStart, mLineRight.yStart);
                            moveStartLine(mLineFirstDiagonal, mLineFirstDiagonal.xStart, ++mLineFirstDiagonal.yStart);
                            moveRect(mRectTopLeft, mLineLeft.xStart, mLineLeft.yStart);
                            moveEndLine(mLineSecondDiagonal, --mLineSecondDiagonal.xEnd, mLineSecondDiagonal.yEnd);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveLine(mLineTop, mLineTop.xStart, ++mLineTop.yStart, --mLineTop.xEnd, mLineTop.yEnd);
                            moveLine(mLineFirstDiagonal, mLineFirstDiagonal.xStart, ++mLineFirstDiagonal.yStart, --mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            moveStartLine(mLineLeft, mLineLeft.xStart, ++mLineLeft.yStart);
                            moveLine(mLineRight, --mLineRight.xStart, mLineRight.yStart, --mLineRight.xEnd, mLineRight.yEnd);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            moveEndLine(mLineSecondDiagonal, --mLineSecondDiagonal.xEnd, mLineSecondDiagonal.yEnd);
                            moveEndLine(mLineBottom, --mLineBottom.xEnd, mLineBottom.yEnd);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            moveRect(mRectBottomRight, mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveEndLine(mLineTop, --mLineTop.xEnd, mLineTop.yEnd);
                            moveEndLine(mLineFirstDiagonal, --mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            moveEndLine(mLineLeft, mLineLeft.xEnd, --mLineLeft.yEnd);
                            moveLine(mLineRight, --mLineRight.xStart, mLineRight.yStart, --mLineRight.xEnd, mLineRight.yEnd);
                            moveRect(mRectBottomLeft, mLineLeft.xEnd, mLineLeft.yEnd);
                            moveLine(mLineSecondDiagonal, mLineSecondDiagonal.xStart, --mLineSecondDiagonal.yStart, --mLineSecondDiagonal.xEnd, mLineSecondDiagonal.yEnd);
                            moveLine(mLineBottom, mLineBottom.xStart, --mLineBottom.yStart, --mLineBottom.xEnd, mLineBottom.yEnd);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            moveRect(mRectBottomRight, mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveStartLine(mLineTop, ++mLineTop.xStart, mLineTop.yStart);
                            moveLine(mLineFirstDiagonal, ++mLineFirstDiagonal.xStart, mLineFirstDiagonal.yStart, --mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            moveLine(mLineLeft, ++mLineLeft.xStart, mLineLeft.yStart, mLineLeft.xEnd, --mLineLeft.yEnd);
                            moveEndLine(mLineRight, --mLineRight.xEnd, mLineRight.yEnd);
                            moveRect(mRectBottomLeft, mLineLeft.xEnd, mLineLeft.yEnd);
                            moveStartLine(mLineSecondDiagonal, mLineSecondDiagonal.xStart, --mLineSecondDiagonal.yStart);
                            moveLine(mLineBottom, mLineBottom.xStart, --mLineBottom.yStart, --mLineBottom.xEnd, mLineBottom.yEnd);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            moveRect(mRectBottomRight, mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveLine(mLineTop, ++mLineTop.xStart, mLineTop.yStart, mLineTop.xEnd, ++mLineTop.yEnd);
                            moveStartLine(mLineFirstDiagonal, ++mLineFirstDiagonal.xStart, mLineFirstDiagonal.yStart);
                            moveLine(mLineLeft, ++mLineLeft.xStart, mLineLeft.yStart, mLineLeft.xEnd, --mLineLeft.yEnd);
                            moveStartLine(mLineRight, mLineRight.xStart, ++mLineRight.yStart);
                            moveRect(mRectBottomLeft, mLineLeft.xEnd, mLineLeft.yEnd);
                            moveLine(mLineSecondDiagonal, mLineSecondDiagonal.xStart, --mLineSecondDiagonal.yStart, mLineSecondDiagonal.xEnd, ++mLineSecondDiagonal.yEnd);
                            moveStartLine(mLineBottom, mLineBottom.xStart, --mLineBottom.yStart);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveLine(mLineTop, ++mLineTop.xStart, mLineTop.yStart, mLineTop.xEnd, ++mLineTop.yEnd);
                            moveStartLine(mLineLeft, ++mLineLeft.xStart, mLineLeft.yStart);
                            moveStartLine(mLineFirstDiagonal, ++mLineFirstDiagonal.xStart, mLineFirstDiagonal.yStart);
                            moveStartLine(mLineRight, mLineRight.xStart, ++mLineRight.yStart);
                            moveEndLine(mLineSecondDiagonal, mLineSecondDiagonal.xEnd, ++mLineSecondDiagonal.yEnd);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveEndLine(mLineTop, mLineTop.xEnd, ++mLineTop.yEnd);
                            moveEndLine(mLineSecondDiagonal, mLineSecondDiagonal.xEnd, ++mLineSecondDiagonal.yEnd);
                            moveStartLine(mLineRight, mLineRight.xStart, ++mLineRight.yStart);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            postInvalidate();
                        }


                        for (int i = 225; i > 0; i--) {
                            mPaint.setAlpha(i);
                            Thread.sleep(3);
                            postInvalidate();
                        }
                        Thread.sleep(15);
                        for (int j = 3; j > 0; j--) {
                            for (int i = 0; i <= 225; i++) {
                                mPaint.setAlpha(i);
                                Thread.sleep(3);
                                postInvalidate();
                            }
                            Thread.sleep(15);
                        }
                        Thread.sleep(50);

                        // reverse motion
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveEndLine(mLineFirstDiagonal, ++mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            moveEndLine(mLineRight, ++mLineRight.xEnd, mLineRight.yEnd);
                            moveEndLine(mLineBottom, ++mLineBottom.xEnd, mLineBottom.yEnd);
                            moveRect(mRectBottomRight, mLineBottom.xEnd, mLineBottom.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveEndLine(mLineFirstDiagonal, ++mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            moveEndLine(mLineRight, ++mLineRight.xEnd, mLineRight.yEnd);
                            moveLine(mLineBottom, mLineBottom.xStart, ++mLineBottom.yStart, ++mLineBottom.xEnd, mLineBottom.yEnd);
                            moveRect(mRectBottomRight, mLineBottom.xEnd, mLineBottom.yEnd);
                            moveStartLine(mLineSecondDiagonal, mLineSecondDiagonal.xStart, ++mLineSecondDiagonal.yStart);
                            moveEndLine(mLineLeft, mLineLeft.xEnd, ++mLineLeft.yEnd);
                            moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveEndLine(mLineFirstDiagonal, ++mLineFirstDiagonal.xEnd, mLineFirstDiagonal.yEnd);
                            moveEndLine(mLineRight, ++mLineRight.xEnd, mLineRight.yEnd);
                            moveLine(mLineBottom, mLineBottom.xStart, ++mLineBottom.yStart, ++mLineBottom.xEnd, mLineBottom.yEnd);
                            moveRect(mRectBottomRight, mLineBottom.xEnd, mLineBottom.yEnd);
                            moveStartLine(mLineSecondDiagonal, mLineSecondDiagonal.xStart, ++mLineSecondDiagonal.yStart);
                            moveLine(mLineLeft, --mLineLeft.xStart, mLineLeft.yStart, mLineLeft.xEnd, ++mLineLeft.yEnd);
                            moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                            moveStartLine(mLineFirstDiagonal, --mLineFirstDiagonal.xStart, mLineFirstDiagonal.yStart);
                            moveStartLine(mLineTop, --mLineTop.xStart, mLineTop.yStart);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveStartLine(mLineRight, mLineRight.xStart, --mLineRight.yStart);
                            moveStartLine(mLineBottom, mLineBottom.xStart, ++mLineBottom.yStart);
                            moveLine(mLineSecondDiagonal, mLineSecondDiagonal.xStart, ++mLineSecondDiagonal.yStart, mLineSecondDiagonal.xEnd, --mLineSecondDiagonal.yEnd);
                            moveLine(mLineLeft, --mLineLeft.xStart, mLineLeft.yStart, mLineLeft.xEnd, ++mLineLeft.yEnd);
                            moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                            moveStartLine(mLineFirstDiagonal, --mLineFirstDiagonal.xStart, mLineFirstDiagonal.yStart);
                            moveLine(mLineTop, --mLineTop.xStart, mLineTop.yStart, mLineTop.xEnd, --mLineTop.yEnd);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveLine(mLineRight, mLineRight.xStart, --mLineRight.yStart, mLineRight.xEnd, ++mLineRight.yEnd);
                            moveEndLine(mLineBottom, mLineBottom.xEnd, ++mLineBottom.yEnd);
                            moveEndLine(mLineSecondDiagonal, mLineSecondDiagonal.xEnd, --mLineSecondDiagonal.yEnd);
                            moveStartLine(mLineLeft, --mLineLeft.xStart, mLineLeft.yStart);
                            moveRect(mRectBottomRight, mLineBottom.xEnd, mLineBottom.yEnd);
                            moveLine(mLineFirstDiagonal, --mLineFirstDiagonal.xStart, mLineFirstDiagonal.yStart, mLineFirstDiagonal.xEnd, ++mLineFirstDiagonal.yEnd);
                            moveLine(mLineTop, --mLineTop.xStart, mLineTop.yStart, mLineTop.xEnd, --mLineTop.yEnd);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveLine(mLineRight, mLineRight.xStart, --mLineRight.yStart, mLineRight.xEnd, ++mLineRight.yEnd);
                            moveLine(mLineBottom, --mLineBottom.xStart, mLineBottom.yStart, mLineBottom.xEnd, ++mLineBottom.yEnd);
                            moveLine(mLineSecondDiagonal, --mLineSecondDiagonal.xStart, mLineSecondDiagonal.yStart, mLineSecondDiagonal.xEnd, --mLineSecondDiagonal.yEnd);
                            moveEndLine(mLineLeft, --mLineLeft.xEnd, mLineLeft.yEnd);
                            moveRect(mRectBottomRight, mLineBottom.xEnd, mLineBottom.yEnd);
                            moveEndLine(mLineFirstDiagonal, mLineFirstDiagonal.xEnd, ++mLineFirstDiagonal.yEnd);
                            moveEndLine(mLineTop, mLineTop.xEnd, --mLineTop.yEnd);
                            moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveEndLine(mLineRight, mLineRight.xEnd, ++mLineRight.yEnd);
                            moveLine(mLineBottom, --mLineBottom.xStart, mLineBottom.yStart, mLineBottom.xEnd, ++mLineBottom.yEnd);
                            moveStartLine(mLineSecondDiagonal, --mLineSecondDiagonal.xStart, mLineSecondDiagonal.yStart);
                            moveLine(mLineLeft, mLineLeft.xStart, --mLineLeft.yStart, --mLineLeft.xEnd, mLineLeft.yEnd);
                            moveRect(mRectBottomRight, mLineBottom.xEnd, mLineBottom.yEnd);
                            moveLine(mLineFirstDiagonal, mLineFirstDiagonal.xStart, --mLineFirstDiagonal.yStart, mLineFirstDiagonal.xEnd, ++mLineFirstDiagonal.yEnd);
                            moveStartLine(mLineTop, mLineTop.xStart, --mLineTop.yStart);
                            moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveStartLine(mLineRight, ++mLineRight.xStart, mLineRight.yStart);
                            moveStartLine(mLineBottom, --mLineBottom.xStart, mLineBottom.yStart);
                            moveLine(mLineSecondDiagonal, --mLineSecondDiagonal.xStart, mLineSecondDiagonal.yStart, ++mLineSecondDiagonal.xEnd, mLineSecondDiagonal.yEnd);
                            moveLine(mLineLeft, mLineLeft.xStart, --mLineLeft.yStart, --mLineLeft.xEnd, mLineLeft.yEnd);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            moveStartLine(mLineFirstDiagonal, mLineFirstDiagonal.xStart, --mLineFirstDiagonal.yStart);
                            moveLine(mLineTop, mLineTop.xStart, --mLineTop.yStart, ++mLineTop.xEnd, mLineTop.yEnd);
                            moveRect(mRectBottomLeft, mLineBottom.xStart, mLineBottom.yStart);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveStartLine(mLineRight, ++mLineRight.xStart, mLineRight.yStart);
                            moveEndLine(mLineSecondDiagonal, ++mLineSecondDiagonal.xEnd, mLineSecondDiagonal.yEnd);
                            moveStartLine(mLineLeft, mLineLeft.xStart, --mLineLeft.yStart);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            moveStartLine(mLineFirstDiagonal, mLineFirstDiagonal.xStart, --mLineFirstDiagonal.yStart);
                            moveLine(mLineTop, mLineTop.xStart, --mLineTop.yStart, ++mLineTop.xEnd, mLineTop.yEnd);
                            moveRect(mRectTopLeft, mLineTop.xStart, mLineTop.yStart);
                            postInvalidate();
                        }
                        for (int i = timer; i > 0; i--) {
                            Thread.sleep(5);
                            moveStartLine(mLineRight, ++mLineRight.xStart, mLineRight.yStart);
                            moveEndLine(mLineSecondDiagonal, ++mLineSecondDiagonal.xEnd, mLineSecondDiagonal.yEnd);
                            moveRect(mRectTopRight, mLineTop.xEnd, mLineTop.yEnd);
                            moveEndLine(mLineTop, ++mLineTop.xEnd, mLineTop.yEnd);
                            postInvalidate();
                        }
                        Thread.sleep(20);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}


