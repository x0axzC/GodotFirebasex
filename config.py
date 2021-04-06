
def can_build(plat):
	return (plat == "android")

def configure(env):
	if env["platform"] == "android":
		env.android_add_maven_repository("url 'https://maven.fabric.io/public'")
		env.android_add_maven_repository(\
		"url 'https://oss.sonatype.org/content/repositories/snapshots'")

		env.android_add_gradle_classpath("com.google.gms:google-services:3.0.0")
		env.android_add_gradle_plugin("com.google.gms.google-services")

		env.android_add_dependency("compile 'com.android.support:support-annotations:25.0.1'")
                env.android_add_dependency("compile 'com.google.firebase:firebase-core:10.0.1'")

		##Auth++
		env.android_add_dependency("compile 'com.google.android.gms:play-services-auth:10.0.1'")
		env.android_add_dependency("compile 'com.facebook.android:facebook-android-sdk:4.18.0'")
		env.android_add_dependency("compile 'com.google.firebase:firebase-auth:10.0.1'")
		env.android_add_dependency(\
		"compile('com.twitter.sdk.android:twitter-core:1.6.6@aar') { transitive = true }")
		env.android_add_dependency(\
		"compile('com.twitter.sdk.android:twitter:1.13.1@aar') { transitive = true }")
		##Auth--

		##AdMob++
		env.android_add_dependency("compile 'com.google.firebase:firebase-ads:10.0.1'")
		##AdMob--

		##RemoteConfig++
		env.android_add_dependency("compile 'com.google.firebase:firebase-config:10.0.1'")
		##RemoteConfig--

		##Notification++
                env.android_add_dependency("compile 'com.google.firebase:firebase-messaging:10.0.1'")
		env.android_add_dependency("compile 'com.firebase:firebase-jobdispatcher:0.5.2'")
		##Notification--

		##Invites++
		env.android_add_dependency("compile 'com.google.firebase:firebase-invites:10.0.1'")
		##Invites--

		##Storage++
		env.android_add_dependency("compile 'com.google.firebase:firebase-storage:10.0.1'")
		##Storage--

		env.android_add_dependency("compile 'commons-codec:commons-codec:1.10'")

		env.android_add_java_dir("android");
		env.android_add_res_dir("res");
		env.android_add_to_manifest("android/AndroidManifestChunk.xml");
		env.android_add_to_permissions("android/AndroidPermissionsChunk.xml");
		env.android_add_default_config("minSdkVersion 15")
		env.android_add_default_config("applicationId 'com.froglogics.dotsndots'")
		#env.disable_module()
