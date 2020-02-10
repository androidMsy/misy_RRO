package com.misy.photopicker.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Administrator on 2020/1/6/006.
 */

public class PermissionsUtils {
    public static boolean checkReadStoragePermission(Activity activity) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            return true;
        }
        int readStoragePermissionState =
                ContextCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE);

        boolean readStoragePermissionGranted = readStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!readStoragePermissionGranted) {
            ActivityCompat.requestPermissions(activity,
                    PermissionsConstant.PERMISSIONS_EXTERNAL_READ,
                    PermissionsConstant.REQUEST_EXTERNAL_READ);
        }
        return readStoragePermissionGranted;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkWriteStoragePermission(Activity fragment) {

        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(fragment, WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!writeStoragePermissionGranted) {
            fragment.requestPermissions(PermissionsConstant.PERMISSIONS_EXTERNAL_WRITE,
                    PermissionsConstant.REQUEST_EXTERNAL_WRITE);
        }
        return writeStoragePermissionGranted;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkCameraPermission(Activity fragment) {
        int cameraPermissionState = ContextCompat.checkSelfPermission(fragment, CAMERA);

        boolean cameraPermissionGranted = cameraPermissionState == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermissionGranted) {
            fragment.requestPermissions(PermissionsConstant.PERMISSIONS_CAMERA,
                    PermissionsConstant.REQUEST_CAMERA);
        }
        return cameraPermissionGranted;
    }
}
