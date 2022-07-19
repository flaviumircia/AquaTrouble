package com.flaviumircia.aquatrouble;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;

public class PermissionCheck {
    public void askPermissions(ActivityResultLauncher<String[]> multiplePermissionLauncher, String[] PERMISSIONS, Context getAppContext) {
        //did the user granted the permissions?
        if (!hasPermissions(PERMISSIONS,getAppContext)) {
            //if not then launch dialog box
            Log.d("PERMISSIONS", "Launching multiple contract permission launcher for ALL required permissions");
            multiplePermissionLauncher.launch(PERMISSIONS);
        } else {
            //if the permissions are granted then log the message
            Log.d("PERMISSIONS", "All permissions are already granted");
        }
    }
    //user method for granting permissions
    public boolean hasPermissions(String[] permissions, Context appContext) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(appContext, permission) != PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISSIONS", "Permission is not granted: " + permission);
                    return false;
                }
                Log.d("PERMISSIONS", "Permission already granted: " + permission);
            }
            return true;
        }
        return false;
    }
}
