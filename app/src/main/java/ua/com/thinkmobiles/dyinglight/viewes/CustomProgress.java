package ua.com.thinkmobiles.dyinglight.viewes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by CAT_Caterpiller on 02.10.2015.
 */
public class CustomProgress extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread mDrawThread;

    public CustomProgress(Context _context) {
        super(_context);
        getHolder().addCallback(this);
    }

    public CustomProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mDrawThread = new DrawThread(getHolder());
        mDrawThread.setRunning(true);
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mDrawThread.setRunning(false);
        while (retry) {
            try {
                mDrawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    class DrawThread extends Thread {
        private boolean running = false;
        private SurfaceHolder mHolder;

        public DrawThread(SurfaceHolder _holder) {
            mHolder = _holder;
        }

        public void setRunning(boolean _running) {
            running = _running;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = null;
                try {
                    canvas = mHolder.lockCanvas(null);
                    if (canvas == null)
                        continue;
                    canvas.drawColor(Color.GREEN);
                } finally {
                    if (canvas != null) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
