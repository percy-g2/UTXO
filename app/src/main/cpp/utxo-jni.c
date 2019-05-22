#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getChangellyApiKey(JNIEnv *env, jclass type) {

    char * apiKey = "";
    return (*env)->NewStringUTF(env, apiKey);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getChangellySecretKey(JNIEnv *env, jclass type) {

    char * secretKey = "";
    return (*env)->NewStringUTF(env, secretKey);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getBitfinexBaseUrl(JNIEnv *env, jclass type) {

    char * baseUrl ="https://api.bitfinex.com/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getBitmexBaseUrl(JNIEnv *env, jclass type) {

    char *baseUrl = "https://www.bitmex.com/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getChangellyBaseUrl(JNIEnv *env, jclass type) {

    char * baseUrl = "https://api.changelly.com";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getBinanceBaseUrl(JNIEnv *env, jclass type) {

    char *baseUrl = "https://www.binance.com/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getOkexBaseUrl(JNIEnv *env, jclass type) {

    char *baseUrl = "https://www.okex.com/";
    return (*env)->NewStringUTF(env, baseUrl);
}


JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getBlocktrailBaseUrl(JNIEnv *env,
                                                                          jclass type) {
    char * baseUrl = "https://api.blocktrail.com/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getBlocktrailApiKey(JNIEnv *env, jclass type) {

    char * apiKey = "";
    return (*env)->NewStringUTF(env, apiKey);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getBitStampBaseUrl(JNIEnv *env, jclass type) {

    char * baseUrl = "https://www.bitstamp.net/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getPocketbitsBaseUrl(JNIEnv *env,
                                                                          jclass type) {
    char * baseUrl = "https://pocketbits.in/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getWazirxBaseUrl(JNIEnv *env,
                                                                      jclass type) {
    char *baseUrl = "https://api.wazirx.com/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getCoinMarketCapBaseUrl(JNIEnv *env,
                                                                          jclass type) {
    char * baseUrl = "https://api.coinmarketcap.com/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getGdaxBaseUrl(JNIEnv *env,
                                                                             jclass type) {
    char * baseUrl = "https://api.gdax.com/products/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getNewsApiBaseUrl(JNIEnv *env,
                                                                       jclass type) {
    char *baseUrl = "https://newsapi.org/";
    return (*env)->NewStringUTF(env, baseUrl);
}

JNIEXPORT jstring JNICALL
Java_com_androidevlinux_percy_UTXO_utils_NativeUtils_getNewsApiKey(JNIEnv *env, jclass type) {
    char *returnValue = "";
    return (*env)->NewStringUTF(env, returnValue);
}