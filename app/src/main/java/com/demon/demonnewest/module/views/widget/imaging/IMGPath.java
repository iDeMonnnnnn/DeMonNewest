package com.demon.demonnewest.module.views.widget.imaging;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by felix on 2017/11/22 下午6:13.
 */

public class IMGPath {

    protected Path path;

    private long id;

    private float width;

    private IMGMode mode;


    public static final float BASE_WIDTH = 72f;


    public IMGPath() {
        this(new Path());
    }

    public IMGPath(Path path) {
        this(path, IMGMode.DOODLE);
    }

    public IMGPath(Path path, IMGMode mode) {
        this(path, mode, BASE_WIDTH);
    }


    public IMGPath(Path path, IMGMode mode, float width) {
        this.path = path;
        this.mode = mode;
        this.width = width;
        if (mode == IMGMode.MOSAIC || mode == IMGMode.DOODLE) {
            path.setFillType(Path.FillType.EVEN_ODD);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public IMGMode getMode() {
        return mode;
    }

    public void setMode(IMGMode mode) {
        this.mode = mode;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return width;
    }

    public void onDrawPath(Canvas canvas, Paint paint) {
        if (mode == IMGMode.DOODLE || mode == IMGMode.MOSAIC) {
            paint.setStrokeWidth(width);
            // rewind
            canvas.drawPath(path, paint);
        }
    }

    public void transform(Matrix matrix) {
        path.transform(matrix);
    }
}
