package com.tencent.mars.xlog;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import com.tencent.mars.BuildConfig;

import java.io.File;

/**
 * @author zhaoyuan orehzhang
 */
public class Log {
    private static final String TAG = "mars.xlog.log";

    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int LEVEL_NONE = 6;

    // defaults to LEVEL_NONE
    private static int level = LEVEL_NONE;
    public static Context toastSupportContext = null;

    public interface LogImp {

        void logV(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logI(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logD(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logW(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logE(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        void logF(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log);

        int getLogLevel();

        void appenderClose();

        void appenderFlush(boolean isSync);

    }

    private static LogImp debugLog = new LogImp() {
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void logV(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (level <= LEVEL_VERBOSE) {
                android.util.Log.v(tag, log);
            }
        }

        @Override
        public void logI(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (level <= LEVEL_INFO) {
                android.util.Log.i(tag, log);
            }
        }

        @Override
        public void logD(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (level <= LEVEL_DEBUG) {
                android.util.Log.d(tag, log);
            }

        }

        @Override
        public void logW(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (level <= LEVEL_WARNING) {
                android.util.Log.w(tag, log);
            }

        }

        @Override
        public void logE(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, String log) {
            if (level <= LEVEL_ERROR) {
                android.util.Log.e(tag, log);
            }
        }

        @Override
        public void logF(String tag, String filename, String funcname, int line, int pid, long tid, long maintid, final String log) {
            if (level > LEVEL_FATAL) {
                return;
            }
            android.util.Log.e(tag, log);

            if (toastSupportContext != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(toastSupportContext, log, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        @Override
        public int getLogLevel() {
            return level;
        }

        @Override
        public void appenderClose() {

        }

        @Override
        public void appenderFlush(boolean isSync) {
        }

    };

    private static LogImp logImp = debugLog;

    public static void setLogImp(LogImp imp) {
        logImp = imp;
    }

    public static LogImp getImpl() {
        return logImp;
    }

    /**
     * 回去异常堆栈信息
     *
     * @param throwable 异常
     * @return
     */
    public static String getStackTraceString(Throwable throwable) {
        return android.util.Log.getStackTraceString(throwable);
    }

    /**
     * 获取方法调用栈
     *
     * @return 方法调用栈
     */
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    public static void appenderClose() {
        if (logImp != null) {
            logImp.appenderClose();
        }
    }

    public static void appenderFlush(boolean isSync) {
        if (logImp != null) {
            logImp.appenderFlush(isSync);
        }
    }

    public static int getLogLevel() {
        if (logImp != null) {
            return logImp.getLogLevel();
        }
        return LEVEL_NONE;
    }

    public static void setLevel(final int level, final boolean jni) {
        Log.level = level;
        android.util.Log.w(TAG, "new log level: " + level);

        if (jni) {
            Xlog.setLogLevel(level);
            //android.util.Log.e(TAG, "no jni log level support");
        }
    }

    /**
     * use f(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void f(final String tag, final String msg) {
        f(tag, msg, getCallerStackTraceElement());
    }

    /**
     * use e(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void e(final String tag, final String msg) {
        e(tag, msg, getCallerStackTraceElement());
    }

    /**
     * use w(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void w(final String tag, final String msg) {
        w(tag, msg, getCallerStackTraceElement());
    }

    /**
     * use i(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void i(final String tag, final String msg) {
        i(tag, msg, getCallerStackTraceElement());
    }

    /**
     * use d(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void d(final String tag, final String msg) {
        d(tag, msg, getCallerStackTraceElement());
    }

    /**
     * use v(tag, format, obj) instead
     *
     * @param tag
     * @param msg
     */
    public static void v(final String tag, final String msg) {
        v(tag, msg, getCallerStackTraceElement());
    }

    public static void f(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            if (obj != null && obj[0] instanceof StackTraceElement) {
                StackTraceElement caller = (StackTraceElement) obj[0];
                logImp.logF(tag, caller.getFileName(), caller.getMethodName(), caller.getLineNumber(), Process.myPid(), Thread.currentThread().getId(), Looper.getMainLooper().getThread().getId(), format);
            } else {
                final String log = obj == null ? format : String.format(format, obj);
                logImp.logF(tag, "", "", 0, Process.myPid(), Process.myTid(), Looper.getMainLooper().getThread().getId(), log);
            }
        }
    }

    public static void e(final String tag, final String format, Throwable throwable) {
        e(tag, format + "" + getStackTraceString(throwable));
    }

    public static void e(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            if (obj != null && obj[0] instanceof StackTraceElement) {
                StackTraceElement caller = (StackTraceElement) obj[0];
                logImp.logE(tag, caller.getFileName(), caller.getMethodName(), caller.getLineNumber(), Process.myPid(), Thread.currentThread().getId(),
                        Looper.getMainLooper().getThread().getId(), format);
            } else {
                String log = obj == null ? format : String.format(format, obj);
                if (log == null) {
                    log = "";
                }
                logImp.logE(tag, "", "", 0, Process.myPid(), Process.myTid(), Looper.getMainLooper().getThread().getId(), log);
            }
        }
    }

    public static void w(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            if (obj != null && obj[0] instanceof StackTraceElement) {
                StackTraceElement caller = (StackTraceElement) obj[0];
                logImp.logW(tag, caller.getFileName(), caller.getMethodName(), caller.getLineNumber(), Process.myPid(), Thread.currentThread().getId(),
                        Looper.getMainLooper().getThread().getId(), format);
            } else {
                String log = obj == null ? format : String.format(format, obj);
                if (log == null) {
                    log = "";
                }
                logImp.logW(tag, "", "", 0, Process.myPid(), Process.myTid(), Looper.getMainLooper().getThread().getId(), log);
            }
        }
    }

    public static void i(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            if (obj != null && obj[0] instanceof StackTraceElement) {
                StackTraceElement caller = (StackTraceElement) obj[0];
                logImp.logI(tag, caller.getFileName(), caller.getMethodName(), caller.getLineNumber(), Process.myPid(), Thread.currentThread().getId(),
                        Looper.getMainLooper().getThread().getId(), format);
            } else {
                String log = obj == null ? format : String.format(format, obj);
                if (log == null) {
                    log = "";
                }
                logImp.logI(tag, "", "", 0, Process.myPid(), Process.myTid(), Looper.getMainLooper().getThread().getId(), log);
            }
        }
    }

    public static void d(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            if (obj != null && obj[0] instanceof StackTraceElement) {
                StackTraceElement caller = (StackTraceElement) obj[0];
                logImp.logD(tag, caller.getFileName(), caller.getMethodName(), caller.getLineNumber(), Process.myPid(), Thread.currentThread().getId(),
                        Looper.getMainLooper().getThread().getId(), format);
            } else {
                String log = obj == null ? format : String.format(format, obj);
                if (log == null) {
                    log = "";
                }
                logImp.logD(tag, "", "", 0, Process.myPid(), Process.myTid(), Looper.getMainLooper().getThread().getId(), log);
            }
        }
    }

    public static void v(String tag, final String format, final Object... obj) {
        if (logImp != null) {
            if (obj != null && obj[0] instanceof StackTraceElement) {
                StackTraceElement caller = (StackTraceElement) obj[0];
                logImp.logV(tag, caller.getFileName(), caller.getMethodName(), caller.getLineNumber(), Process.myPid(), Thread.currentThread().getId(),
                        Looper.getMainLooper().getThread().getId(), format);
            } else {
                String log = obj == null ? format : String.format(format, obj);
                if (log == null) {
                    log = "";
                }
                logImp.logV(tag, "", "", 0, Process.myPid(), Process.myTid(), Looper.getMainLooper().getThread().getId(), log);
            }
        }
    }

    public static void printErrStackTrace(String tag, Throwable tr, final String format, final Object... obj) {
        if (logImp != null) {
            String log = obj == null ? format : String.format(format, obj);
            if (log == null) {
                log = "";
            }
            log += "  " + android.util.Log.getStackTraceString(tr);
            logImp.logE(tag, "", "", 0, Process.myPid(), Process.myTid(), Looper.getMainLooper().getThread().getId(), log);
        }
    }

    private static final String SYS_INFO;

    static {
        final StringBuilder sb = new StringBuilder();
        try {
            sb.append("VERSION.RELEASE:[" + android.os.Build.VERSION.RELEASE);
            sb.append("] VERSION.CODENAME:[" + android.os.Build.VERSION.CODENAME);
            sb.append("] VERSION.INCREMENTAL:[" + android.os.Build.VERSION.INCREMENTAL);
            sb.append("] BOARD:[" + android.os.Build.BOARD);
            sb.append("] DEVICE:[" + android.os.Build.DEVICE);
            sb.append("] DISPLAY:[" + android.os.Build.DISPLAY);
            sb.append("] FINGERPRINT:[" + android.os.Build.FINGERPRINT);
            sb.append("] HOST:[" + android.os.Build.HOST);
            sb.append("] MANUFACTURER:[" + android.os.Build.MANUFACTURER);
            sb.append("] MODEL:[" + android.os.Build.MODEL);
            sb.append("] PRODUCT:[" + android.os.Build.PRODUCT);
            sb.append("] TAGS:[" + android.os.Build.TAGS);
            sb.append("] TYPE:[" + android.os.Build.TYPE);
            sb.append("] USER:[" + android.os.Build.USER + "]");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        SYS_INFO = sb.toString();
    }

    public static String getSysInfo() {
        return SYS_INFO;
    }

    /**
     * 初始化Xlog
     *
     * @param context    上下文
     * @param nameprefix 文件前缀
     * @param pubkey     加密key
     */
    public static void initXlog(Context context, String nameprefix, String pubkey) {
        File logRootFile = context.getExternalFilesDir(null);
        if (logRootFile == null) {
            logRootFile = context.getFilesDir();
        }
        String logPath = logRootFile.getAbsolutePath() + "/xlog";
        Xlog.appenderOpen(Xlog.LEVEL_ALL, Xlog.AppednerModeAsync, "", logPath, nameprefix, 0, "");
        Xlog.setConsoleLogOpen(BuildConfig.DEBUG);
        Log.setLogImp(new Xlog());
    }
}
