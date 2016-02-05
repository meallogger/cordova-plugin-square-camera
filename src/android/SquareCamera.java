package com.meallogger.squarecamera;


import android.content.Intent;
import android.net.Uri;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONException;

import com.desmond.squarecamera.CameraActivity;

public class SquareCamera extends CordovaPlugin {
  private static final int REQUEST_CAMERA = 0;

  private CallbackContext callbackContext;

  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    this.callbackContext = callbackContext;

    if (action.equals("getPicture")) {
      getPicture();
      return true;
    }

    return false;
  }

  public void getPicture() {
    Intent startCustomCameraIntent = new Intent(this.cordova.getActivity(), CameraActivity.class);
    this.cordova.startActivityForResult(this, startCustomCameraIntent, REQUEST_CAMERA);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    if(requestCode == REQUEST_CAMERA) {
      Uri imageUri = intent.getData();

      callbackContext.success(imageUri.toString());
    }
  }

}

