#include <jni.h>
#include <gtk/gtk.h>
#include "tomboykeybinder.h"
#include "edu_tsinghua_lumaqq_hotkey_KeyBinder.h"

static JNIEnv *java_env;
static jobject java_obj;

static void handler(char *keystring, gpointer data) {
	jclass cls = (*java_env)->GetObjectClass(java_env, java_obj);
	jmethodID mid = (*java_env)->GetMethodID(java_env, cls, "callback", "(Ljava/lang/String;)V");
	if (mid == 0) {
		return;
	}
	
	jstring str = (*java_env)->NewStringUTF(java_env, keystring);
	(*java_env)->CallVoidMethod(java_env, java_obj, mid, str);
}

/*
 * Class:     edu_tsinghua_lumaqq_hotkey_KeyBinder
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_edu_tsinghua_lumaqq_hotkey_KeyBinder_init(JNIEnv *env, jobject obj) {
	java_env = env;
	java_obj = (*env)->NewGlobalRef(env, obj);
	gtk_init_check(NULL, NULL);
	tomboy_keybinder_init();
}

/*
 * Class:     edu_tsinghua_lumaqq_hotkey_KeyBinder
 * Method:    bind
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_tsinghua_lumaqq_hotkey_KeyBinder_bind(JNIEnv *env, jobject obj, jstring keystring) { 
	const char *str = (*env)->GetStringUTFChars(env, keystring, 0);
	gboolean success = tomboy_keybinder_bind(str, handler, NULL);
	(*env)->ReleaseStringUTFChars(env, keystring, str);
	return (success == TRUE) ? JNI_TRUE : JNI_FALSE;
}

/*
 * Class:     edu_tsinghua_lumaqq_hotkey_KeyBinder
 * Method:    unbind
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_edu_tsinghua_lumaqq_hotkey_KeyBinder_unbind(JNIEnv *env, jobject obj, jstring keystring) {
	const char *str = (*env)->GetStringUTFChars(env, keystring, 0);
	tomboy_keybinder_unbind(str, handler);
	(*env)->ReleaseStringUTFChars(env, keystring, str);
}

