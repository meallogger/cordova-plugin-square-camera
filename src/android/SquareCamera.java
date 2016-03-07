package com.meallogger.squarecamera;

import android.app.Activity;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.desmond.squarecamera.CameraActivity;

public class SquareCamera extends CordovaPlugin {
  private static final int REQUEST_CAMERA = 0;

  private CallbackContext callbackContext;
  private JSONArray args;

  private static final String PICTURE_MIN_WIDTH = "pictureMinWidth";
  private static final String STORAGE_DIRECTORY = "storageDirectory";

  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    this.args = args;
    this.callbackContext = callbackContext;

    if (action.equals("getPicture")) {
      if(cordova.hasPermission(Manifest.permission.CAMERA)) {
        getPicture();
      }
      else {
        cordova.requestPermission(this, REQUEST_CAMERA, Manifest.permission.CAMERA);
      }
      return true;
    }

    return false;
  }


  public void getPicture() throws JSONException {
    JSONObject opts = args.getJSONObject(0);
    Intent intent = new Intent(this.cordova.getActivity(), CameraActivity.class);
    if(!opts.isNull(PICTURE_MIN_WIDTH)) {
      intent.putExtra(CameraActivity.EXTRA_PICTURE_MIN_WIDTH, opts.getInt(PICTURE_MIN_WIDTH));
    }
    if(!opts.isNull(STORAGE_DIRECTORY)) {
      intent.putExtra(CameraActivity.EXTRA_STORAGE_DIRECTORY, opts.getString(STORAGE_DIRECTORY));
    }
    this.cordova.startActivityForResult(this, intent, REQUEST_CAMERA);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if(resultCode == Activity.RESULT_CANCELED) {
      callbackContext.error("Cancelled.");
      return;
    }
    else if(resultCode != Activity.RESULT_OK) {
      callbackContext.error("Error while taking photo. resultCode=" + resultCode);
      return;
    }

    if(requestCode == REQUEST_CAMERA) {
      Uri imageUri = intent.getData();

      callbackContext.success(imageUri.toString());
    }
  }

  public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults) throws JSONException {
    for(int result : grantResults) {
      if(result == PackageManager.PERMISSION_DENIED) {
        callbackContext.error("Permission to camera was denied.");
        return;
      }
    }

    if(requestCode == REQUEST_CAMERA) {
      getPicture();
    }
  }
}
