package com.meallogger.squarecamera;


import android.content.Intent;
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

  private static final String PICTURE_MIN_WIDTH = "pictureMinWidth";
  private static final String STORAGE_DIRECTORY = "storageDirectory";

  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    this.callbackContext = callbackContext;

    if (action.equals("getPicture")) {
      getPicture(args.getJSONObject(0));
      return true;
    }

    return false;
  }

  public void getPicture(JSONObject opts) throws JSONException {
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
    if(requestCode == REQUEST_CAMERA) {
      Uri imageUri = intent.getData();

      callbackContext.success(imageUri.toString());
    }
  }

}

