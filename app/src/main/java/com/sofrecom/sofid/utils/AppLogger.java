package com.sofrecom.sofid.utils;

import android.content.Context;
import android.os.Environment;

import com.bosphere.filelogger.FL;
import com.bosphere.filelogger.FLConfig;
import com.bosphere.filelogger.FLConst;
import com.sofrecom.sofid.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by oibnchahdia on 15/04/2019
 */
public final class AppLogger extends FL{

    static final int DEFAULT_MAX_FILE_COUNT = 7;

    public static void init(Context context) {
        File logDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+context.getString(R.string.app_name), "Log");
        if (!logDirectory.exists())
            logDirectory.mkdirs();

        init(new FLConfig.Builder(context)
                .defaultTag(context.getString(R.string.app_name)+"_Default")   // customise default tag
                .formatter(new Formatter())
                .minLevel(FLConst.Level.V)   // customise minimum logging level
                .logToFile(true)   // enable logging to file
                .dir(logDirectory)    // customise directory to hold log files
                .retentionPolicy(FLConst.RetentionPolicy.FILE_COUNT) // customise retention strategy
                .maxFileCount(DEFAULT_MAX_FILE_COUNT)    // customise how many log files to keep if retention by file count
                .build());

        setEnabled(true);
    }

    private static class Formatter extends FLConfig.DefaultFormatter {

        private final ThreadLocal<SimpleDateFormat> mFileNameFmt = new ThreadLocal<SimpleDateFormat>() {
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("dd_MM_yyyy", Locale.ENGLISH);
            }
        };

        private final ThreadLocal<Date> mDate = new ThreadLocal<Date>() {
            @Override
            protected Date initialValue() {
                return new Date();
            }
        };

        @Override
        public String formatFileName(long timeInMillis) {
            mDate.get().setTime(timeInMillis);
            return mFileNameFmt.get().format(mDate.get()) + "_log.txt";
        }
    }
}
