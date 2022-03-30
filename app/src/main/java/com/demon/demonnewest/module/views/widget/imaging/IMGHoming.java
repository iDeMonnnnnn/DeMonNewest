package com.demon.demonnewest.module.views.widget.imaging;

import android.graphics.Matrix;
import android.graphics.RectF;

/**
 * Created by felix on 2017/11/28 下午4:14.
 */

public class IMGHoming {

    private static final Matrix M = new Matrix();

    public float x, y;

    public float scale;

    public float rotate;

    public IMGHoming(float x, float y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotate = 0;
    }

    public IMGHoming(float x, float y, float scale, float rotate) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotate = rotate;
    }

    public void set(float x, float y, float scale, float rotate) {
        this.x = x;
        this.y = y;
        this.scale = scale;
        this.rotate = rotate;
    }

    public void concat(IMGHoming homing) {
        this.scale *= homing.scale;
        this.x += homing.x;
        this.y += homing.y;
    }

    public void rConcat(IMGHoming homing) {
        this.scale *= homing.scale;
        this.x -= homing.x;
        this.y -= homing.y;
    }

    public static boolean isRotate(IMGHoming sHoming, IMGHoming eHoming) {
        return Float.compare(sHoming.rotate, eHoming.rotate) != 0;
    }

    @Override
    public String toString() {
        return "IMGHoming{" +
                "x=" + x +
                ", y=" + y +
                ", scale=" + scale +
                ", rotate=" + rotate +
                '}';
    }

    public static IMGHoming fitHoming(RectF win, RectF frame, boolean isJustInner) {
        IMGHoming dHoming = new IMGHoming(0, 0, 1, 0);

        if (frame.contains(win) && !isJustInner) {
            // 不需要Fit
            return dHoming;
        }

        // 宽高都小于Win，才有必要放大
        if (isJustInner || frame.width() < win.width() && frame.height() < win.height()) {
            dHoming.scale = Math.min(win.width() / frame.width(), win.height() / frame.height());
        }

        RectF rect = new RectF();
        M.setScale(dHoming.scale, dHoming.scale, frame.centerX(), frame.centerY());
        M.mapRect(rect, frame);

        if (rect.width() < win.width()) {
            dHoming.x += win.centerX() - rect.centerX();
        } else {
            if (rect.left > win.left) {
                dHoming.x += win.left - rect.left;
            } else if (rect.right < win.right) {
                dHoming.x += win.right - rect.right;
            }
        }

        if (rect.height() < win.height()) {
            dHoming.y += win.centerY() - rect.centerY();
        } else {
            if (rect.top > win.top) {
                dHoming.y += win.top - rect.top;
            } else if (rect.bottom < win.bottom) {
                dHoming.y += win.bottom - rect.bottom;
            }
        }

        return dHoming;
    }
}
