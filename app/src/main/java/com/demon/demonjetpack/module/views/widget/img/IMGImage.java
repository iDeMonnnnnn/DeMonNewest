package com.demon.demonjetpack.module.views.widget.img;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felix on 2017/11/21 下午10:03.
 */

public class IMGImage {

    private static final String TAG = "IMGImage";

    private Bitmap mImage, mMosaicImage, mDoodleImage;

    /**
     * 完整图片边框
     */
    private RectF mFrame = new RectF();

    /**
     * 裁剪图片边框（显示的图片区域）
     */
    private RectF mClipFrame = new RectF();

    /**
     * 裁剪模式前状态备份
     */
    private RectF mBackupClipFrame = new RectF();

    private float mBackupClipRotate = 0;

    private float mRotate = 0, mTargetRotate = 0;

    private boolean isRequestToBaseFitting = false;

    private boolean isAnimCanceled = false;

    /**
     * 裁剪模式时当前触摸锚点
     */
    private IMGClip.Anchor mAnchor;

    private boolean isSteady = true;

    private Path mShade = new Path();

    /**
     * 裁剪窗口
     */
    private IMGClipWindow mClipWin = new IMGClipWindow();

    /**
     * 编辑模式
     */
    private IMGMode mMode = IMGMode.NONE;

    private boolean isFreezing = mMode == IMGMode.CLIP;

    /**
     * 可视区域，无Scroll 偏移区域
     */
    private RectF mWindow = new RectF();

    /**
     * 是否初始位置
     */
    private boolean isInitialHoming = false;

    private List<IMGPath> allPath = new ArrayList<>();
    /**
     * 涂鸦路径
     */
    private List<IMGPath> mDoodles = new ArrayList<>();

    /**
     * 马赛克路径
     */
    private List<IMGPath> mMosaics = new ArrayList<>();

    private static final int MIN_SIZE = 500;

    private static final int MAX_SIZE = 10000;

    private Paint mPaint, mDoodlePaint, mShadePaint;

    private Matrix M = new Matrix();

    private static final boolean DEBUG = false;

    private static final Bitmap DEFAULT_IMAGE;

    private static final int COLOR_SHADE = 0xCC000000;

    static {
        DEFAULT_IMAGE = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    }

    {
        mShade.setFillType(Path.FillType.WINDING);

        // Doodle&Mosaic 's paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(IMGPath.BASE_WIDTH);
        mPaint.setPathEffect(new CornerPathEffect(IMGPath.BASE_WIDTH));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

    }

    public IMGImage() {
        mImage = DEFAULT_IMAGE;

        if (mMode == IMGMode.CLIP) {
            initShadePaint();
        }
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }

        this.mImage = bitmap;

        // 清空马赛克图层
        if (mMosaicImage != null) {
            mMosaicImage.recycle();
        }
        this.mMosaicImage = null;
        // 清空模糊图层
        if (mDoodleImage != null) {
            mDoodleImage.recycle();
        }
        this.mDoodleImage = null;

        makeMosaicBitmap();

        onImageChanged();
    }

    public IMGMode getMode() {
        return mMode;
    }

    public void setMode(IMGMode mode) {

        if (this.mMode == mode) return;

        if (mode == IMGMode.CLIP) {
            setFreezing(true);
        }

        this.mMode = mode;

        if (mMode == IMGMode.CLIP) {

            // 初始化Shade 画刷
            initShadePaint();

            // 备份裁剪前Clip 区域
            mBackupClipRotate = getRotate();
            mBackupClipFrame.set(mClipFrame);

            float scale = 1 / getScale();
            M.setTranslate(-mFrame.left, -mFrame.top);
            M.postScale(scale, scale);
            M.mapRect(mBackupClipFrame);

            // 重置裁剪区域
            mClipWin.reset(mClipFrame, getTargetRotate());
        } else {

            if (mMode == IMGMode.MOSAIC || mMode == IMGMode.DOODLE) {
                makeMosaicBitmap();
            }

            mClipWin.setClipping(false);
        }
    }

    private void initShadePaint() {
        if (mShadePaint == null) {
            mShadePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mShadePaint.setColor(COLOR_SHADE);
            mShadePaint.setStyle(Paint.Style.FILL);
        }
    }

    public boolean isMosaicEmpty() {
        return mMosaics.isEmpty();
    }

    public boolean isDoodleEmpty() {
        return mDoodles.isEmpty();
    }

    public void reset() {
        allPath.clear();
        mDoodles.clear();
        mMosaics.clear();
    }

    public void undo() {
        if (!allPath.isEmpty()) {
            IMGPath path = allPath.get(allPath.size() - 1);

            for (int i = mDoodles.size() - 1; i >= 0; i--) {
                IMGPath mDoodlePath = mDoodles.get(i);
                if (mDoodlePath.getId() == path.getId()) {
                    mDoodles.remove(i);
                }
            }
            for (int i = mMosaics.size() - 1; i >= 0; i--) {
                IMGPath mMosaicsPath = mMosaics.get(i);
                if (mMosaicsPath.getId() == path.getId()) {
                    mMosaics.remove(i);
                }
            }
            allPath.remove(allPath.size() - 1);
        }
    }


    public RectF getClipFrame() {
        return mClipFrame;
    }

    /**
     * 裁剪区域旋转回原始角度后形成新的裁剪区域，旋转中心发生变化，
     * 因此需要将视图窗口平移到新的旋转中心位置。
     */
    public IMGHoming clip(float scrollX, float scrollY) {
        RectF frame = mClipWin.getOffsetFrame(scrollX, scrollY);

        M.setRotate(-getRotate(), mClipFrame.centerX(), mClipFrame.centerY());
        M.mapRect(mClipFrame, frame);

        return new IMGHoming(
                scrollX + (mClipFrame.centerX() - frame.centerX()),
                scrollY + (mClipFrame.centerY() - frame.centerY()),
                getScale(), getRotate()
        );
    }

    public void toBackupClip() {
        M.setScale(getScale(), getScale());
        M.postTranslate(mFrame.left, mFrame.top);
        M.mapRect(mClipFrame, mBackupClipFrame);
        setTargetRotate(mBackupClipRotate);
        isRequestToBaseFitting = true;
    }

    public void resetClip() {
        // TODO 就近旋转
        setTargetRotate(getRotate() - getRotate() % 360);
        mClipFrame.set(mFrame);
        mClipWin.reset(mClipFrame, getTargetRotate());
    }

    private void makeMosaicBitmap() {
        if ((mMosaicImage != null && mDoodleImage != null) || mImage == null) {
            return;
        }

        if (mDoodlePaint == null) {
            mDoodlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDoodlePaint.setFilterBitmap(false);
            mDoodlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        }

        if (mMode == IMGMode.MOSAIC) {
            int w = Math.round(mImage.getWidth() / 64f);
            int h = Math.round(mImage.getHeight() / 64f);
            w = Math.max(w, 8);
            h = Math.max(h, 8);
            //生成马赛克图片
            mMosaicImage = Bitmap.createScaledBitmap(mImage, w, h, false);
        } else if (mMode == IMGMode.DOODLE) {
            int width = mImage.getWidth();
            float scale = 20.0f / width;
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            mDoodleImage = Bitmap.createBitmap(mImage, 0, 0, width, mImage.getHeight(), matrix, true);

            Matrix mosaicMatrix = new Matrix();
            mosaicMatrix.setTranslate(mFrame.left, mFrame.top);
            float scaleX = (mFrame.right - mFrame.left) / mDoodleImage.getWidth();
            float scaleY = (mFrame.bottom - mFrame.top) / mDoodleImage.getHeight();
            mosaicMatrix.postScale(scaleX, scaleY);
            // 生成模糊图片
            mDoodleImage = Bitmap.createBitmap(mDoodleImage, 0, 0, mDoodleImage.getWidth(), mDoodleImage.getHeight(), mosaicMatrix, true);
        }
    }

    private void onImageChanged() {
        isInitialHoming = false;
        onWindowChanged(mWindow.width(), mWindow.height());

        if (mMode == IMGMode.CLIP) {
            mClipWin.reset(mClipFrame, getTargetRotate());
        }
    }

    public RectF getFrame() {
        return mFrame;
    }

    public boolean onClipHoming() {
        return mClipWin.homing();
    }

    public IMGHoming getStartHoming(float scrollX, float scrollY) {
        return new IMGHoming(scrollX, scrollY, getScale(), getRotate());
    }

    public IMGHoming getEndHoming(float scrollX, float scrollY) {
        IMGHoming homing = new IMGHoming(scrollX, scrollY, getScale(), getTargetRotate());

        if (mMode == IMGMode.CLIP) {
            RectF frame = new RectF(mClipWin.getTargetFrame());
            frame.offset(scrollX, scrollY);
            if (mClipWin.isResetting()) {

                RectF clipFrame = new RectF();
                M.setRotate(getTargetRotate(), mClipFrame.centerX(), mClipFrame.centerY());
                M.mapRect(clipFrame, mClipFrame);

                homing.rConcat(IMGUtils.fill(frame, clipFrame));
            } else {
                RectF cFrame = new RectF();

                // cFrame要是一个暂时clipFrame
                if (mClipWin.isHoming()) {
//
//                    M.mapRect(cFrame, mClipFrame);

//                    mClipWin
                    // TODO 偏移中心

                    M.setRotate(getTargetRotate() - getRotate(), mClipFrame.centerX(), mClipFrame.centerY());
                    M.mapRect(cFrame, mClipWin.getOffsetFrame(scrollX, scrollY));

                    homing.rConcat(IMGUtils.fitHoming(frame, cFrame, mClipFrame.centerX(), mClipFrame.centerY()));


                } else {
                    M.setRotate(getTargetRotate(), mClipFrame.centerX(), mClipFrame.centerY());
                    M.mapRect(cFrame, mFrame);
                    homing.rConcat(IMGUtils.fillHoming(frame, cFrame, mClipFrame.centerX(), mClipFrame.centerY()));
                }

            }
        } else {
            RectF clipFrame = new RectF();
            M.setRotate(getTargetRotate(), mClipFrame.centerX(), mClipFrame.centerY());
            M.mapRect(clipFrame, mClipFrame);

            RectF win = new RectF(mWindow);
            win.offset(scrollX, scrollY);
            homing.rConcat(IMGUtils.fitHoming(win, clipFrame, isRequestToBaseFitting));
            isRequestToBaseFitting = false;
        }

        return homing;
    }


    public void addPath(IMGPath path, float sx, float sy) {
        if (path == null) return;

        float scale = 1f / getScale();

        M.setTranslate(sx, sy);
        M.postRotate(-getRotate(), mClipFrame.centerX(), mClipFrame.centerY());
        M.postTranslate(-mFrame.left, -mFrame.top);
        M.postScale(scale, scale);
        path.transform(M);
        path.setId(System.currentTimeMillis());
        path.setWidth(path.getWidth() * scale);
        switch (path.getMode()) {
            case DOODLE:
                mDoodles.add(path);
                break;
            case MOSAIC:
                mMosaics.add(path);
                break;
        }
        allPath.add(path);
    }

    public void onWindowChanged(float width, float height) {
        if (width == 0 || height == 0) {
            return;
        }

        mWindow.set(0, 0, width, height);

        if (!isInitialHoming) {
            onInitialHoming(width, height);
        } else {

            // Pivot to fit window.
            M.setTranslate(mWindow.centerX() - mClipFrame.centerX(), mWindow.centerY() - mClipFrame.centerY());
            M.mapRect(mFrame);
            M.mapRect(mClipFrame);
        }

        mClipWin.setClipWinSize(width, height);
    }

    private void onInitialHoming(float width, float height) {
        mFrame.set(0, 0, mImage.getWidth(), mImage.getHeight());
        mClipFrame.set(mFrame);
        mClipWin.setClipWinSize(width, height);

        if (mClipFrame.isEmpty()) {
            return;
        }

        toBaseHoming();

        isInitialHoming = true;
        onInitialHomingDone();
    }

    private void toBaseHoming() {
        if (mClipFrame.isEmpty()) {
            // Bitmap invalidate.
            return;
        }

        float scale = Math.min(
                mWindow.width() / mClipFrame.width(),
                mWindow.height() / mClipFrame.height()
        );

        // Scale to fit window.
        M.setScale(scale, scale, mClipFrame.centerX(), mClipFrame.centerY());
        M.postTranslate(mWindow.centerX() - mClipFrame.centerX(), mWindow.centerY() - mClipFrame.centerY());
        M.mapRect(mFrame);
        M.mapRect(mClipFrame);
    }

    private void onInitialHomingDone() {
        if (mMode == IMGMode.CLIP) {
            mClipWin.reset(mClipFrame, getTargetRotate());
        }
    }

    public void onDrawImage(Canvas canvas) {

        // 裁剪区域
        canvas.clipRect(mClipWin.isClipping() ? mFrame : mClipFrame);

        // 绘制图片
        canvas.drawBitmap(mImage, null, mFrame, null);

        if (DEBUG) {
            // Clip 区域
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(6);
            canvas.drawRect(mFrame, mPaint);
            canvas.drawRect(mClipFrame, mPaint);
        }
    }

    public int onDrawMosaicsPath(Canvas canvas) {
        int layerCount = canvas.saveLayer(mFrame, null, Canvas.ALL_SAVE_FLAG);

        if (!isMosaicEmpty()) {
            canvas.save();
            float scale = getScale();
            canvas.translate(mFrame.left, mFrame.top);
            canvas.scale(scale, scale);
            for (IMGPath path : mMosaics) {
                path.onDrawPath(canvas, mPaint);
            }
            canvas.restore();
        }

        return layerCount;
    }

    public void onDrawMosaic(Canvas canvas, int layerCount) {
        canvas.drawBitmap(mMosaicImage, null, mFrame, mDoodlePaint);
        canvas.restoreToCount(layerCount);
    }

    public int onDrawDoodlesPath(Canvas canvas) {
        int layerCount = canvas.saveLayer(mFrame, null, Canvas.ALL_SAVE_FLAG);
        if (!isDoodleEmpty()) {
            canvas.save();
            float scale = getScale();
            canvas.translate(mFrame.left, mFrame.top);
            canvas.scale(scale, scale);
            for (IMGPath path : mDoodles) {
                path.onDrawPath(canvas, mPaint);
            }
            canvas.restore();
        }
        return layerCount;
    }


    public void onDrawDoodle(Canvas canvas, int layerCount) {
        canvas.drawBitmap(mDoodleImage, null, mFrame, mDoodlePaint);
        canvas.restoreToCount(layerCount);
    }

    public void onDrawShade(Canvas canvas) {
        if (mMode == IMGMode.CLIP && isSteady) {
            mShade.reset();
            mShade.addRect(mFrame.left - 2, mFrame.top - 2, mFrame.right + 2, mFrame.bottom + 2, Path.Direction.CW);
            mShade.addRect(mClipFrame, Path.Direction.CCW);
            canvas.drawPath(mShade, mShadePaint);
        }
    }

    public void onDrawClip(Canvas canvas, float scrollX, float scrollY) {
        if (mMode == IMGMode.CLIP) {
            mClipWin.onDraw(canvas);
        }
    }

    public void onTouchDown(float x, float y) {
        isSteady = false;
        if (mMode == IMGMode.CLIP) {
            mAnchor = mClipWin.getAnchor(x, y);
        }
    }

    public void onTouchUp(float scrollX, float scrollY) {
        if (mAnchor != null) {
            mAnchor = null;
        }
    }

    public void onSteady(float scrollX, float scrollY) {
        isSteady = true;
        onClipHoming();
        mClipWin.setShowShade(true);
    }

    public void onScaleBegin() {

    }

    public IMGHoming onScroll(float scrollX, float scrollY, float dx, float dy) {
        if (mMode == IMGMode.CLIP) {
            mClipWin.setShowShade(false);
            if (mAnchor != null) {
                mClipWin.onScroll(mAnchor, dx, dy);

                RectF clipFrame = new RectF();
                M.setRotate(getRotate(), mClipFrame.centerX(), mClipFrame.centerY());
                M.mapRect(clipFrame, mFrame);

                RectF frame = mClipWin.getOffsetFrame(scrollX, scrollY);
                IMGHoming homing = new IMGHoming(scrollX, scrollY, getScale(), getTargetRotate());
                homing.rConcat(IMGUtils.fillHoming(frame, clipFrame, mClipFrame.centerX(), mClipFrame.centerY()));
                return homing;
            }
        }
        return null;
    }

    public float getTargetRotate() {
        return mTargetRotate;
    }

    public void setTargetRotate(float targetRotate) {
        this.mTargetRotate = targetRotate;
    }

    /**
     * 在当前基础上旋转
     */
    public void rotate(int rotate) {
        mTargetRotate = Math.round((mRotate + rotate) / 90f) * 90;
        mClipWin.reset(mClipFrame, getTargetRotate());
    }

    public float getRotate() {
        return mRotate;
    }

    public void setRotate(float rotate) {
        mRotate = rotate;
    }

    public float getScale() {
        return 1f * mFrame.width() / mImage.getWidth();
    }

    public void setScale(float scale) {
        setScale(scale, mClipFrame.centerX(), mClipFrame.centerY());
    }

    public void setScale(float scale, float focusX, float focusY) {
        onScale(scale / getScale(), focusX, focusY);
    }

    public void onScale(float factor, float focusX, float focusY) {

        if (factor == 1f) return;

        if (Math.max(mClipFrame.width(), mClipFrame.height()) >= MAX_SIZE
                || Math.min(mClipFrame.width(), mClipFrame.height()) <= MIN_SIZE) {
            factor += (1 - factor) / 2;
        }

        M.setScale(factor, factor, focusX, focusY);
        M.mapRect(mFrame);
        M.mapRect(mClipFrame);

        // 修正clip 窗口
        if (!mFrame.contains(mClipFrame)) {
            // TODO
//            mClipFrame.intersect(mFrame);
        }

    }

    public void onScaleEnd() {

    }

    public void onHomingStart(boolean isRotate) {
        isAnimCanceled = false;
    }

    public void onHoming(float fraction) {
        mClipWin.homing(fraction);
    }

    public boolean onHomingEnd(float scrollX, float scrollY, boolean isRotate) {
        if (mMode == IMGMode.CLIP) {
            // 开启裁剪模式

            boolean clip = !isAnimCanceled;

            mClipWin.setHoming(false);
            mClipWin.setClipping(true);
            mClipWin.setResetting(false);

            return clip;
        } else {
            if (isFreezing && !isAnimCanceled) {
                setFreezing(false);
            }
        }
        return false;
    }

    public boolean isFreezing() {
        return isFreezing;
    }

    private void setFreezing(boolean freezing) {
        if (freezing != isFreezing) {
            isFreezing = freezing;
        }
    }

    public void onHomingCancel(boolean isRotate) {
        isAnimCanceled = true;
        Log.d(TAG, "Homing cancel");
    }

    public void release() {
        if (mImage != null && !mImage.isRecycled()) {
            mImage.recycle();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (DEFAULT_IMAGE != null) {
            DEFAULT_IMAGE.recycle();
        }
    }
}
