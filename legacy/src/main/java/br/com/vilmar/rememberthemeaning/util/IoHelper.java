package br.com.vilmar.rememberthemeaning.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by vilmar on 2/19/15.
 */
public class IoHelper {

    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static String getPath(String name){
        File f = new File(Environment.getExternalStorageDirectory().toString());

        for (File temp : f.listFiles()) {
            if (temp.getName().equals(name)) {
                return temp.getPath();
            }
        }

        return null;
    }

    public static Bitmap getBitmap(String path) {
        Bitmap bitmap = null;

        try {
            bitmap = BitmapFactory.decodeFile(path);
        } catch (Exception e) {
            bitmap = null;
            e.printStackTrace();
        }

        return bitmap;
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);

        if (file != null && file.exists()) {
            return file.delete();
        }

        return false;
    }

    public static boolean deleteDir(String dirName) {
        File dir = new File(dirName);

        if (dir != null && dir.exists() && dir.isDirectory()) {
            String deleteCmd = "rm -r " + dir.getAbsolutePath();
            Runtime runtime = Runtime.getRuntime();

            try {
                runtime.exec(deleteCmd);

                return true;
            } catch (IOException e) { }
        }

        return false;
    }

    public static boolean existsFile(String fileName) {
        File file = new File(fileName);
        return file != null && file.exists();
    }

    public static boolean existsDir(String dirName) {
        File dir = new File(dirName);
        return dir != null && dir.isDirectory() && dir.exists();
    }

    public static File createDir(String dirName) {
        File dir = new File (dirName);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        return dir;
    }

    public static boolean copyFile(String fromFile, String toFile) {
        boolean sucess = false;

        try {
            File from = new File(fromFile);
            File to = new File(toFile);

            InputStream in = new FileInputStream(from);
            OutputStream out = new FileOutputStream(to);

            int len;
            byte[] buf = new byte[1024];

            while ((len = in.read(buf)) > 0) {

                out.write(buf, 0, len);
            }

            in.close();
            out.close();

        } catch (Exception e) {
            sucess = false;
        }

        return sucess;
    }

    public static final byte[] readAll(InputStream is) throws Exception {

        if (is == null) {

            return null;
        }

        ByteArrayOutputStream baos = null;

        try {

            baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];

            int size;

            while ((size = is.read(buffer, 0, buffer.length)) > 0) {

                baos.write(buffer, 0, size);
            }

            buffer = null;

            byte[] data = baos.toByteArray();

            if (data == null || data.length == 0) {

                return null;

            } else {

                return data;
            }

        } finally {

            try {

                if (baos != null) {

                    baos.close();
                }

            } finally {

                baos = null;
            }
        }
    }

    public final static byte[] readFile(Context context, String fileName) {

        InputStream is = null;

        try {

            is = context.getApplicationContext().openFileInput(fileName);

            return IoHelper.readAll(is);

        } catch (FileNotFoundException e) {

            return null;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        } finally {

            if (is != null) {

                try {

                    is.close();

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    is = null;
                }
            }
        }
    }

    public final static byte[] readFile(File file) {

        InputStream is = null;

        try {

            is = new FileInputStream(file);

            return IoHelper.readAll(is);

        } catch (Exception e) {

            return null;

        } finally {

            if (is != null) {

                try {

                    is.close();

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    is = null;
                }
            }
        }
    }

    public static final String readTextFileFromAssets(Context context,
                                                      String assetName) {
        InputStream is = null;
        try {
            is = context.getApplicationContext().getAssets().open(assetName);
            return new String(IoHelper.readAll(is));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
        }
    }

    public static final String readTextFileFromRawResource(Context context,
                                                           int resId) {
        InputStream is = null;
        try {
            is = context.getApplicationContext().getResources()
                    .openRawResource(resId);
            return new String(IoHelper.readAll(is));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    is = null;
                }
            }
        }
    }

    public static final boolean writeFile(File file, byte[] data) {

        boolean error = false;

        boolean result = false;

        try {

            FileOutputStream fos = null;

            try {

                fos = new FileOutputStream(file);

                fos.write(data);

            } catch (Exception e) {

                error = true;

                throw e;

            } finally {

                if (fos != null) {

                    try {

                        fos.close();

                        result = !error;

                    } catch (IOException e) {

                        e.printStackTrace();

                    } finally {

                        fos = null;
                    }
                }
            }

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public final static boolean writePrivateFile(Context context,
                                                 String fileName, byte[] data) {

        boolean error = false;

        boolean result = false;

        try {

            FileOutputStream fos = null;

            try {

                fos = context.getApplicationContext().openFileOutput(fileName,
                        Context.MODE_PRIVATE);

                fos.write(data);

            } catch (Exception e) {

                error = true;

                throw e;

            } finally {

                if (fos != null) {

                    try {

                        fos.close();

                        result = !error;

                    } catch (IOException e) {

                        e.printStackTrace();

                    } finally {

                        fos = null;
                    }
                }
            }

            return result;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    public final static File getFile(String path){
        return new File(path);
    }



}
