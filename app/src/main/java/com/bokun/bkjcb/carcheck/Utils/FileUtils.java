package com.bokun.bkjcb.carcheck.Utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public static String SDPATH = Environment.getExternalStorageDirectory()
            + "/CheckApp/formats/";
    public static String SDPATH1 = Environment.getExternalStorageDirectory()
            + "/CheckApp/tempImage/";

    public static String getSDPATH() {
        File fileParent = new File(SDPATH);
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        return SDPATH;
    }

    public static void saveBitmap(Bitmap bm, String picName) {
        Log.e("", "保存图片");
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("", "已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createSDDir(String fileName) throws IOException {
        File dir = new File(fileName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File parent = new File(SDPATH);
            if (!parent.exists()) {
                parent.mkdirs();
            }
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static void delFile(File file) {
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static void deleteDir(String path) {
        File dir = new File(path);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDir(path); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

}
