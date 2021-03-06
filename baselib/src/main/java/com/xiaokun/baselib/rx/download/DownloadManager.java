package com.xiaokun.baselib.rx.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import io.reactivex.disposables.Disposable;

/**
 * Created by 肖坤 on 2018/4/22.
 *
 * @author 肖坤
 * @date 2018/4/22
 */

public class DownloadManager {
    public static SharedPreferences dSp;

    /**
     * 初始化DownloadManager
     *
     * @param context
     */
    public static void init(Context context) {
        dSp = context.getSharedPreferences("download_file", Context.MODE_PRIVATE);
    }

    /**
     * 暂停下载
     *
     * @param disposable 控制rxjava的开关
     * @param file       下载的文件
     */
    public static void pauseDownload(Disposable disposable, File file) {
        if (disposable == null || !file.exists()) {
            return;
        }
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        if (dSp == null) {
            throw new NullPointerException("必须首先初始化DownloadManager");
        }
        if (file.exists() && dSp != null) {
            dSp.edit().putLong(file.getPath(), file.length()).apply();
        }
    }

    /**
     * 取消下载
     *
     * @param disposable 控制rxjava的开关
     * @param file       下载的文件
     */
    public static void cancelDownload(Disposable disposable, File file) {
        if (disposable == null || !file.exists()) {
            return;
        }
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        if (file.exists() && dSp != null) {
            dSp.edit().putLong(file.getPath(), 0).apply();
        }
        //取消下载，最后一步记得删除掉已经下载的文件
        if (file.exists()) {
            file.delete();
        }
    }

    public static File initFile(String fileName) {
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        File file = new File(directory + File.separator + fileName);
        return file;
    }
}
