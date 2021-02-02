package org.godotengine.godot.storage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import org.json.JSONObject;
import org.json.JSONException;

import org.godotengine.godot.auth.Auth;
import org.godotengine.godot.FireBase;

import java.util.Locale;

// SharedPreferences

public class Storage {

	public static Storage getInstance (Activity p_activity) {
		if (mInstance == null) {
			mInstance = new Storage(p_activity);
		}

		return mInstance;
	}

	public Storage(Activity p_activity) {
		activity = p_activity;
	}

	public void init (FirebaseApp firebaseApp) {
		mFirebaseApp = firebaseApp;

//		if (!Auth.getInstance(activity).isInitialized()) {

//		}

		// onNewIntent(getIntent());


		// Local broadcast receiver
		mBroadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				Log.d(TAG, "SD:OnReceive:" + intent.toString());

				// hideProgressDialog(); // Hiding our progress dialog

				switch (intent.getAction()) {
				case DownloadService.DOWNLOAD_COMPLETED:
					// Get number of bytes downloaded
					long numBytes = intent.getLongExtra(
							DownloadService.EXTRA_BYTES_DOWNLOADED,0);

					// Alert success
					// activity.getString(R.string.success),

					/**
					showMessageDialog("Success",
					String.format(Locale.getDefault(),
					"%d bytes downloaded from %s", numBytes,
					intent.getStringExtra(DownloadService.EXTRA_DOWNLOAD_PATH)));
					**/
					break;
				case DownloadService.DOWNLOAD_ERROR:
					// Alert failure
					/**
					showMessageDialog("Error",
					String.format(Locale.getDefault(), "Failed to download from %s",
					intent.getStringExtra(DownloadService.EXTRA_DOWNLOAD_PATH)));
					**/
					break;
//				case MyUploadService.UPLOAD_COMPLETED:
//				case MyUploadService.UPLOAD_ERROR:
//					onUploadResultIntent(intent);
//					break;
				}
			}
		};

		onStart();

		Log.d(TAG, "Initilaized Storage");
	}

	public void download(String url, String path) {
		if (!isInitialized()) { return; }

		Log.d(TAG, "SD:Downloading:"+url);
		mDownloadUrl = Uri.parse(url);
		// String path = mFileUri.getLastPathSegment();

		// Kick off MyDownloadService to download the file
		Intent intent = new Intent(activity, DownloadService.class)
		.putExtra(DownloadService.EXTRA_DOWNLOAD_PATH, url)
		.putExtra(DownloadService.EXTRA_DOWNLOAD_TO, path)
		.setAction(DownloadService.ACTION_DOWNLOAD);

		Log.d(TAG, "SD:Starting:Service:Download");
		activity.startService(intent);
	}

	public void upload(final String filePath) {

		Log.d(TAG, "SD:Uploading:"+filePath);
		Uri fileUri = Uri.parse(filePath);
		// String path = mFileUri.getLastPathSegment();

		// Kick off MyDownloadService to download the file
		Intent intent = new Intent(activity, DownloadService.class)
		.putExtra(UploadService.EXTRA_FILE_URI, fileUri)
		.setAction(UploadService.ACTION_UPLOAD);

		Log.d(TAG, "SD:Starting:Service:Upload");
		activity.startService(intent);
	}

	public void onStart() {
		LocalBroadcastManager.getInstance(activity)
		.registerReceiver(mBroadcastReceiver, DownloadService.getIntentFilter());
	}

	public void onStop() {
		if (!isInitialized()) { return; }

		LocalBroadcastManager.getInstance(activity).unregisterReceiver(mBroadcastReceiver);
	}

	private boolean isInitialized() {
		if (mFirebaseApp == null) {
			Log.d(TAG, "FireBase Storage, not initialized");
			return false;
		}

		return true;
	}

	private static Activity activity = null;
	private static Storage mInstance = null;

	private FirebaseApp mFirebaseApp = null;

	private BroadcastReceiver mBroadcastReceiver = null;
	private Uri mDownloadUrl = null;
	private Uri mFileUrl = null;

	private static final String TAG = "FireBase";

}
