## GodotFireBase
Godot_FireBase is a firebase integration for godot android;

# Depends on
> Godot game engine (2.1/2.2-legacy): `git clone https://github.com/godotengine/godot`

> GodotSQL: `git clone https://github.com/FrogLogics/GodotSQL`

# Available Features
> Analytics

> Authentication [W.I.P] Google, Facebook

> Firebase Notification

> RemoteConfig

> Invites (Email & SMS)

# Build/Compile module
copy your `google-services.json` file to `[GODOT-ROOT]/platform/android/java/` and edit file modules/FireBase/config.py at line 17
```
env.android_add_default_config("applicationId 'com.your.appid'")
```
replay `com.your.appid` with you android application id.

# Initialize FireBase
Edit engine.cfg and add
```
[android]
modules="org/godotengine/godot/FireBase"
```

RemoteConfigs default parameters `.xml` file is at `[GODOT-ROOT]/modules/FireBase/res/xml/remote_config_defaults.xml`

# GDScript - getting module singleton and initializing;
```
var firebase = Globals.get_singleton("FireBase");
```
For Analytics only `firebase.init("");` or to user RemoteConfig or Notifications (subscribing to topic)
```
var config = Dictionary()
config["Authentication"] = true // Firebase Authentication
config["Notification"] = true;  // Firebase Notification
config["Invites"] = true  // Firebase Invites
config["RemoteConfig"] = true;  // Firebase Remote Config

firebase.init(config.to_json(), get_instance_id());
```
# Using FireBase Analytics
```
firebase.sendCustom("TestKey", "SomeValue");
firebase.setScreenName("Screen_name");
firebase.sendAchievement("someAchievementId");
```

# AlertDialog aditional
```
firebase.alert("Message goes here..!");
```

# Authentication

For Facebook edit `res/values/ids.xml` and replace facebook_app_id with your Facebook App Id
```
firebase.authConfig("'Google':true,'Facebook':true") // Configure Auth service

firebase.google_sign_in() // Firebase connect to google.
firebase.facebook_sign_in() // Firebase connect to facebook.

firebase.google_sign_out() // Firebase disconnect from google.
firebase.facebook_sign_out() // Firebase disconnect from facebook.

var gUserDetails = firebase.get_google_user() // returns name, email_id, photo_uri
var fbUserDetails = firebase.get_facebook_user() // returns name, email_id, photo_uri

TODO:
	firebase.google_revoke_access();
	firebase.facebook_revoke_access();

```

# Firebase Notification API
```
firebase.subscribeToTopic("topic") // Subscribe to particular topic.
firebase.getToken() // Get current client TokenID
<<<<<<< HEAD

If recived notifiction has a payload, it will be saved inside SQL Database under key: "firebase_notification_data"
=======
firebase.notifyInMins("message", 60) // Shedule notification in 60 min
>>>>>>> 6555676241adc8eb2ca36668892fea941ca31c79
```

# RemoteConfig API
```
firebase.getRemoteValue("remote_key") // Return String value
```
# Settings RemoteConfig default values
```
var defs = Dictionary()
defs["some_remoteconfig_key1"] = "remote_config_value1"
defs["some_remoteconfig_key2"] = "remote_config_value2"

firebase.setRemoteDefaults(defs.to_json())
```
OR load from json file
```
firebase.setRemoteDefaultsFile("res://path/to/jsonfile.json")
```

# Firebase Invites
```
Invite Friends with Email & SMS, DeepLink example: https://play.google.com/store/apps/details?id=[package-id].

firebase.invite("message", "https://example.com/beed/link") // Send Firebase Invites.
firebase.invite("message", "");  // Fallback to use default android share eg: Whatsapp, Twitter and more.
```

# Log FireBase Events
```
adb shell setprop log.tag.FA VERBOSE
adb shell setprop log.tag.FA-SVC VERBOSE
adb shell setprop debug.firebase.analytics.app {org.example.appname}
adb logcat -v time -s FA FA-SV
```
