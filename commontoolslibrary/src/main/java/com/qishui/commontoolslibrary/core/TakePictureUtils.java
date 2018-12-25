package com.qishui.commontoolslibrary.core;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * author： qishui
 * date: 2018/12/25  15:27
 * email: qihsuichixi@163.com
 * qq: 798150439
 * blog: http://zhouwenxi.top
 * desc: 拍照、选择照片工具类
 */
public class TakePictureUtils {

    private static final String TAG = "TAKEPICTUREUTILS";
    private TakePictureFragment takePictureFragment;

    private TakePictureUtils(Fragment fragment) {
        this(fragment.getActivity());
    }

    private TakePictureUtils(FragmentActivity activity) {
        takePictureFragment = getFragment(activity.getSupportFragmentManager());
    }

    /**
     * 构建对象
     *
     * @param activity
     * @return
     */
    public static TakePictureUtils with(FragmentActivity activity) {
        return new TakePictureUtils(activity);
    }

    public static TakePictureUtils with(Fragment fragment) {
        return new TakePictureUtils(fragment);
    }


    /**
     * 拍照片
     *
     * @param takePictureCallback
     * @return
     */
    public TakePictureUtils takePicture(TakePictureCallback takePictureCallback) {
        takePictureFragment.takePicture(takePictureCallback);
        return this;
    }

    public TakePictureUtils takePicture(String picPath, String picName, TakePictureCallback takePictureCallback) {
        takePictureFragment.takePicture(picPath, picName, takePictureCallback);
        return this;
    }

    /**
     * 拍照
     */
    public interface TakePictureCallback {
        void show(String path);
    }

    /**
     * 选取照片
     */
    public interface SelectPictureCallBack {
        void show(String path);
    }

    public TakePictureUtils selectPicture(SelectPictureCallBack selectPictureCallBack) {
        takePictureFragment.selectPicture(selectPictureCallBack);
        return this;
    }

    /**
     * 获取fragment
     *
     * @param fragmentManager
     * @return
     */
    private TakePictureFragment getFragment(@NonNull final FragmentManager fragmentManager) {
        TakePictureFragment fragment = (TakePictureFragment) fragmentManager.findFragmentByTag(TAG);
        boolean isNewInstance = fragment == null;
        if (isNewInstance) {
            fragment = new TakePictureFragment();
            fragmentManager.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    /**
     * 辅助
     */
    public static class TakePictureFragment extends Fragment {

        private static final int TAKEPICTURE = 0;
        private static final int SELECT_IMAGE_REQUEST_CODE = 1;
        private SelectPictureCallBack selectPictureCallBack;
        private TakePictureCallback takePictureCallback;
        private String picUrl;

        public TakePictureFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }


        /**
         * 拍照设置文件夹及名字
         *
         * @param picPath
         * @param picName
         * @param takePictureCallback
         */
        public void takePicture(String picPath, String picName, TakePictureCallback takePictureCallback) {

            try {
                picUrl = FileUtils.createFile(picPath, picName);
                this.takePictureCallback = takePictureCallback;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(getActivity(), new File(picUrl)));
                startActivityForResult(intent, TAKEPICTURE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 默认设置
         *
         * @param takePictureCallback
         */
        public void takePicture(TakePictureCallback takePictureCallback) {

            try {
                picUrl = FileUtils.createNewPngFile();
                this.takePictureCallback = takePictureCallback;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider7.getUriForFile(getActivity(), new File(picUrl)));
                startActivityForResult(intent, TAKEPICTURE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * 选取相册图片
         */
        public void selectPicture(TakePictureUtils.SelectPictureCallBack selectPictureCallBack) {
            try {
                this.selectPictureCallBack = selectPictureCallBack;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_IMAGE_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == TAKEPICTURE) {
                    if (takePictureCallback != null) {
                        takePictureCallback.show(picUrl);
                    }
                }

                if (requestCode == SELECT_IMAGE_REQUEST_CODE) {
                    handleSelectPicture(data);
                }
            }


        }

        private void handleSelectPicture(Intent data) {
            String imagePath = null;
            Uri uri = data.getData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //如果是document类型的Uri,则通过document id处理
                if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                        String id = docId.split(":")[1];//解析出数字格式的id
                        String selection = MediaStore.Images.Media._ID + "=" + id;
                        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                    } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                        imagePath = getImagePath(contentUri, null);
                    }
                } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                    //如果是content类型的Uri，则使用普通方式处理
                    imagePath = getImagePath(uri, null);
                } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                    //如果是file类型的Uri，直接获取图片路径即可
                    imagePath = uri.getPath();
                }
            } else {
                imagePath = getImagePath(uri, null);
            }

            //根据图片路径显示图片
            if (selectPictureCallBack != null) {
                selectPictureCallBack.show(imagePath);
            }
        }


        private String getImagePath(Uri uri, String selection) {
            String path = null;
            //通过Uri和selection来获取真实的图片路径
            Cursor cursor = getActivity().getContentResolver().query(uri, null, selection, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
                cursor.close();
            }
            return path;
        }

    }

}
