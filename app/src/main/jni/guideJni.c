//
// Created by 曹亚民 on 15/11/10.
//
#include <string.h>
#include <stdio.h>

#include "com_material_travel_LoginActivity.h"
#include "log.h"


//

char *Jstring2CStr1(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
                                                            strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);        //new   char[alen+1]; "\0"
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);  //释放内存

    return rtn;
}
JNIEXPORT void JNICALL Java_com_material_travel_LoginActivity_invokeMthod
        (JNIEnv *env, jclass jobj){
    LOGD("-----Log from native!---------------------");

    jclass clz = (*env)->FindClass(env,"com/material/travel/LoginActivity");
    //jclass clz_1=(*env)->GetObjectClass(env,jobj);
    if(clz==0){
        LOGI("find class error");
    }
    LOGI("find class ");
    //2 寻找class里面的方法
    //   jmethodID   (*GetMethodID)(JNIEnv*, jclass, const char*, const char*);
    jmethodID method = (*env)->GetStaticMethodID(env,clz,"testRxJavaAndroid","()V");
    //jmethodID method = (*env)->GetMethodID(env,clz_1,"testRxJavaAndroid","()V");

    if(method==0){
        LOGI("find method1 error");

    }
    LOGI("find method ");
    (*env)->CallStaticVoidMethod(env,clz,method);
    //(*env)->CallVoidMethod(env,clz_1,method);

}

JNIEXPORT jstring JNICALL Java_com_material_travel_LoginActivity_getStrFromNative
        (JNIEnv *env, jobject jobj) {
    LOGD("-----Log from native!---------------------");

    return (*env)->NewStringUTF(env, "Hello From Native!");
}

JNIEXPORT jstring JNICALL Java_com_material_travel_LoginActivity_gennerateStrFromNative
        (JNIEnv *env, jclass jcls, jstring jstr) {
    char *p = Jstring2CStr1(env, jstr);
    LOGI("%s", p);
    char *newstr = "append string";
    LOGI("END");
    return (*env)->NewStringUTF(env, strcat(p, newstr));
}

