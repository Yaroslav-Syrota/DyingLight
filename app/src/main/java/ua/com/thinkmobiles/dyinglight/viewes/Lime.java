package ua.com.thinkmobiles.dyinglight.viewes;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

import ua.com.thinkmobiles.dyinglight.R;
import ua.com.thinkmobiles.dyinglight.entities.Item;

/**
 * Created by CAT_Caterpiller on 04.10.2015.
 */

public class Lime extends ViewGroup {
    private int mXcenterCoord;
    private int mYcenterCoord;
    private int mRadius;
    private int mLimeSize;
    private int mLimeRotation;
    private RectF mLimeBounds;
    private Slice mSlice;
    private int mStrokeLime;
    private int mStrokeSlice;
    private int mColorSlice;
    private int mColorLine;
    private int mColorCenter;
    private int mColorActive;

    private List<Item> mItems;
    private final int ALL_SLICES_ANGLE = 360;
    public static final int FLING_VELOCITY_DOWNSCALE = 3;
    public static final int AUTO_CENTER_ANIM_DURATION = 250;

    private Scroller mScroller;
    private GestureDetector mDetector;
    private ValueAnimator mAnimator;
    private ObjectAnimator mObjectAnimator;


    public Lime(Context _context, AttributeSet _attrs) {
        super(_context, _attrs);
        initAttr(_context, _attrs);
        init();
    }


    private void initAttr(Context _context, AttributeSet _attrs) {
        TypedArray array = _context.obtainStyledAttributes(_attrs,
                R.styleable.Lime);
        mLimeSize = array.getInt(R.styleable.Lime_limeSize, 6);
        mRadius = array.getDimensionPixelSize(R.styleable.Lime_limeRadius, 0);
        mStrokeLime = array.getDimensionPixelSize(R.styleable.Lime_limeStroke, 0);
        mStrokeSlice = array.getDimensionPixelSize(R.styleable.Lime_sliceStroke, 0);
        mColorSlice = array.getColor(R.styleable.Lime_sliceColor, Color.BLUE);
        mColorLine = array.getColor(R.styleable.Lime_lineColor, Color.RED);
        mColorActive = array.getColor(R.styleable.Lime_activeColor, Color.GREEN);
        mColorCenter = array.getColor(R.styleable.Lime_centerColor, Color.GREEN);
    }

    private void init() {
        mItems = new ArrayList<>();
        setItems(mItems);
        mSlice = new Slice(getContext());
        addView(mSlice);
        mSlice.rotateTo(mLimeRotation);
        mScroller = new Scroller(getContext(), null, true);
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                tickScrollAnimation();
            }
        });
        mObjectAnimator = ObjectAnimator.ofInt(Lime.this, "LimeRotation", 0);
        mDetector = new GestureDetector(Lime.this.getContext(), new GestureListener());
        mDetector.setIsLongpressEnabled(false);
    }

    public void setItems(List<Item> _list) {
        int[] icons = setIcons();

        int sliceAngle = ALL_SLICES_ANGLE / icons.length;
        int startAngle = 0;

        for (int i = 0; i < mLimeSize; i++) {
            Item item = new Item();
            item.setId(i);
            item.setImage(icons[i]);
            item.setAngleStart(startAngle);
            item.setAngleStop(startAngle + sliceAngle);
            startAngle += sliceAngle;
            item.setColor(mColorSlice);
            _list.add(item);
        }
    }

    //TODO remake filling through XML
    private int[] setIcons() {
        int[] icons = {R.drawable.ic_search,
                R.drawable.ic_save,
                R.drawable.ic_list,
                R.drawable.ic_edit,
                R.drawable.ic_delete};
        return icons;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mSlice.layout(l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        float fX = (float) Math.pow(event.getX() - mXcenterCoord, 2);
        float fY = (float) Math.pow(event.getY() - mYcenterCoord, 2);
        return (fX + fY) < Math.pow(mRadius, 2);
    }

    private void tickScrollAnimation() {
        if (!mScroller.isFinished()) {
            mScroller.computeScrollOffset();
            setLimeRotation(mScroller.getCurrY());
        } else {
            mAnimator.cancel();
        }
    }

    private void moveItemUp(int startAngle, int endAngle) {
        int itemCenterAngle = ((startAngle + endAngle) / 2) +
                getLimeRotation();
        itemCenterAngle %= 360;

        if (itemCenterAngle != 270) {
            int rotateAngle;
            if (itemCenterAngle > 270 || itemCenterAngle < 90) {
                rotateAngle = (270 - 360 - itemCenterAngle) % 360;
            } else {
                rotateAngle = (270 + 360 - itemCenterAngle) % 360;
            }
            mObjectAnimator.setIntValues(getLimeRotation(), rotateAngle + getLimeRotation());
            mObjectAnimator.setDuration(AUTO_CENTER_ANIM_DURATION).start();
        }
        invalidate();
    }

    public int getLimeRotation() {
        return mLimeRotation;
    }

    public void setLimeRotation(int rotation) {
        rotation = (rotation % ALL_SLICES_ANGLE + ALL_SLICES_ANGLE) % ALL_SLICES_ANGLE;
        mLimeRotation = rotation;
        mSlice.rotateTo(rotation);
    }

    public float vectorToScalarScroll(float dx, float dy, float x, float y) {
        float l = (float) Math.sqrt(dx * dx + dy * dy);
        float crossX = -y;
        float crossY = x;
        float dot = (crossX * dx + crossY * dy);
        float sign = Math.signum(dot);
        return l * sign;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        if (mRadius == 0) {
            mRadius = width / 2;
        }
        int height = measureHeight(heightMeasureSpec);
        if (height < width)
            mRadius = height / 2;
        mRadius -= mStrokeLime;
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {
            result = mRadius * 2;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float xPad = (float) (getPaddingLeft() + getPaddingRight());
        float yPad = (float) (getPaddingTop() + getPaddingBottom());

        float ww = (float) w - xPad;
        float hh = (float) h - yPad;
        float diameter = Math.min(ww, hh);

        mLimeBounds = new RectF(0, 0, diameter, diameter);
        mLimeBounds.offsetTo(getPaddingLeft(), getPaddingTop());
    }

    private class Slice extends View{
        private Paint mPaintLime;
        private Paint mPaintCenter;
        private Paint mPaintSlice;

        public Slice(Context context) {
            super(context);
            init();
        }

        private void init() {
            mPaintLime = new Paint();
            mPaintLime.setColor(mColorLine);
            mPaintLime.setAntiAlias(true);
            mPaintCenter = new Paint();
            mPaintCenter.setAntiAlias(true);
            mPaintSlice = new Paint();
            mPaintSlice.setAntiAlias(true);
        }

        @Override
        protected void onDraw(Canvas _canvas) {
            super.onDraw(_canvas);
            mXcenterCoord = getWidth() / 2;
            mYcenterCoord = getHeight() / 2;
            drawLime(_canvas);
            drawSlice(_canvas);
            drawCenter(_canvas);
        }

        private void drawLime(Canvas _canvas) {
            _canvas.drawCircle(mXcenterCoord, mYcenterCoord, mRadius + mStrokeLime, mPaintLime);
        }

        private void drawCenter(Canvas _canvas) {
            mPaintCenter.setColor(mColorActive);
            _canvas.drawCircle(mXcenterCoord, mYcenterCoord, mRadius / 9, mPaintCenter);
            mPaintCenter.setColor(mColorCenter);
            _canvas.drawCircle(mXcenterCoord, mYcenterCoord, mRadius / 12, mPaintCenter);
        }

        private void drawSlice(Canvas _canvas) {
            mLimeBounds.set(mXcenterCoord - mRadius, mYcenterCoord - mRadius,
                    mXcenterCoord + mRadius, mYcenterCoord + mRadius);

            float sliceAngle = (float) ALL_SLICES_ANGLE / mItems.size();
            float startAngle = -90 - sliceAngle / 2;

            //TODO check rotation
            _canvas.rotate(90 + sliceAngle / 2, mXcenterCoord, mYcenterCoord);


            for (int i = 0; i < mItems.size(); i++) {
                Item item = mItems.get(i);

                mPaintSlice.setColor(item.getColor());
                _canvas.drawArc(mLimeBounds, startAngle, sliceAngle, true, mPaintSlice);

                // TODO drawable vs bitmap
//                Drawable drawable = getResources().getDrawable(item.getImage());
//                drawable.setBounds();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), item.getImage());
                _canvas.drawBitmap(bitmap, mXcenterCoord - bitmap.getWidth() / 2, mYcenterCoord -
                        (mRadius * 2 / 3) - bitmap.getHeight() / 2, mPaintSlice);
                _canvas.rotate(sliceAngle, mXcenterCoord, mYcenterCoord);
            }

            double y = mYcenterCoord + (Math.sin(Math.toRadians(270 - sliceAngle / 2)) * mRadius);
            double x = mXcenterCoord + (Math.cos(Math.toRadians(270 - sliceAngle / 2)) * mRadius);
            for (int i = 0; i < mItems.size(); i++) {
                mPaintSlice.setColor(mColorLine);
                mPaintSlice.setStrokeWidth(mStrokeSlice);
                _canvas.drawLine(mXcenterCoord, mYcenterCoord, (int) x, (int) y, mPaintSlice);
                _canvas.rotate(sliceAngle, mXcenterCoord, mYcenterCoord);
            }
        }

        public void rotateTo(float pieRotation) {
            setRotation(pieRotation);
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float scrollTheta = vectorToScalarScroll(
                    distanceX,
                    distanceY,
                    e2.getX() - mLimeBounds.centerX(),
                    e2.getY() - mLimeBounds.centerY());
            setLimeRotation(getLimeRotation() - (int) scrollTheta / FLING_VELOCITY_DOWNSCALE);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float scrollTheta = vectorToScalarScroll(
                    velocityX,
                    velocityY,
                    e2.getX() - mLimeBounds.centerX(),
                    e2.getY() - mLimeBounds.centerY());
            mScroller.fling(
                    0,
                    getLimeRotation(),
                    0,
                    (int) scrollTheta / FLING_VELOCITY_DOWNSCALE,
                    0,
                    0,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE);

            mAnimator.setDuration(mScroller.getDuration());
            mAnimator.start();
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            float xPosition = event.getX();
            float yPosition = event.getY();

            float y = mYcenterCoord - yPosition;
            float x = mXcenterCoord - xPosition;

            double angle = Math.toDegrees(Math.atan2(y, x)) - 180;
            if (angle < 0) {
                angle += ALL_SLICES_ANGLE;
            }

            angle = (angle + ALL_SLICES_ANGLE - getLimeRotation()) % ALL_SLICES_ANGLE;
            for (int i = 0; i < mItems.size(); i++) {
                Item item = mItems.get(i);
                item.setColor(mColorSlice);
                if (angle > item.getAngleStart() && angle < item.getAngleStop()) {
                    item.setColor(getResources().getColor(R.color.activeSliceColor));
                    moveItemUp(item.getAngleStart(), item.getAngleStop());
                }
            }
            mSlice.invalidate();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mObjectAnimator.cancel();
            return true;
        }
    }
}
