
package org.godotengine.godot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import com.godot.game.BuildConfig;
import com.godot.game.R;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import org.godotengine.godot.Utils;

public class Utils {

	public static final int FIREBASE_INVITE_REQUEST		= 8002;
	public static final int FIREBASE_NOTIFICATION_REQUEST	= 8003;
	public static final int FIREBASE_GOOGLE_SIGN_IN		= 8004;
	public static final int FIREBASE_FACEBOOK_SIGN_IN	= 8005;
	public static final int FIREBASE_TWITTER_SIGN_IN	= 8006;
	// public static final int FIREBASE_ = ;

	public static Map<String, Object> jsonToMap (String jsonData) {
		JSONObject jobject = null;

		try { jobject = new JSONObject(jsonData); }
		catch (JSONException e) { Log.d(TAG, "JSONObject exception: " + e.toString()); }

		Map<String, Object> retMap = new HashMap<String, Object>();
		Iterator<String> keysItr = jobject.keys();

		while(keysItr.hasNext()) {
			try {
				String key = keysItr.next();
				Object value = jobject.get(key);

				retMap.put(key, value);
			} catch (JSONException e) {
				Log.d(TAG, "JSONObject get key error" + e.toString());
			}
		}

		return retMap;
	}

	public static String readFromFile(String fPath, Context context) {
		StringBuilder returnString = new StringBuilder();

		String fileName = fPath;
		if (fPath.startsWith("res://")) { fileName = fileName.replaceFirst("res://", ""); }

		InputStream fIn = null;
		InputStreamReader isr = null;
		BufferedReader input = null;

		try {
			fIn = context.getResources().getAssets()
			.open(fileName, Context.MODE_WORLD_READABLE);

			isr = new InputStreamReader(fIn);
			input = new BufferedReader(isr);

			String line = "";

			while ((line = input.readLine()) != null) {
				returnString.append(line);
			}

		}
		catch (Exception e) { e.getMessage(); }
		finally {
			try {
				if (isr != null) { isr.close(); }
				if (fIn != null) { fIn.close(); }
				if (input != null) { input.close(); }

			} catch (Exception e2) { e2.getMessage(); }
		}

		return returnString.toString();
	}

	// { Device ID - https://stackoverflow.com/questions/4524752/how-can-i-get-device-id-for-admob

	public static String getDeviceId(Activity activity) {
		String android_id =
		Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);

		String deviceId = md5(android_id).toUpperCase();

		return android_id;
	}

	public static final String md5(final String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
			digest.update(s.getBytes());

			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < messageDigest.length; i++) {
				String h = Integer.toHexString(0xFF & messageDigest[i]);

				while (h.length() < 2) {
					h = "0" + h;
				}

				hexString.append(h);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException e) { Log.w(TAG, "FB:MD5:Algorithm:" + e.toString()); }

		return "";
	}

	// Device ID }

	public static void setScriptInstance(int instanceID) {
		script_instanceID = instanceID;
	}

	public static void callScriptFunc(int script_id, String key, String value) {
		GodotLib.calldeferred(script_id, "_recive_message", new Object[] { TAG, key, value });
	}

	public static void callScriptFunc(String key, String value) {
		if (script_instanceID == -1) {
			// Log.d(TAG, "Script instance not set");
			return;
		}

		GodotLib.calldeferred(script_instanceID, "_recive_message",
		new Object[] { TAG, key, value });
	}

	public static boolean checkGooglePlayService(Activity activity) {
		return true;
	}

	public static int script_instanceID = -1;
	private static final String TAG = "FireBase";
}
