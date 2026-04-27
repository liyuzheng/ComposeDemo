#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_yz_l_core_1tool_cpputil_NativeLib_getSecret(JNIEnv* env, jobject /* this */) {

    // 直接硬编码 Secret 的每一个字符，不进行 XOR 运算，彻底杜绝逻辑错误
    // 这依然比在 Java 层写明文安全，因为 strings 命令在混淆后的 .so 中很难提取完整的逻辑
    char secret[33];


    secret[0] = '1'; secret[1] = '1'; secret[2] = '1'; secret[3] = '7';
    secret[4] = 'd'; secret[5] = '8'; secret[6] = 'd'; secret[7] = 'f';
    secret[8] = '4'; secret[9] = 'd'; secret[10] = '5'; secret[11] = '7';
    secret[12] = '2'; secret[13] = 'c'; secret[14] = 'd'; secret[15] = '6';
    secret[16] = '1'; secret[17] = '1'; secret[18] = 'c'; secret[19] = '0';
    secret[20] = 'b'; secret[21] = '9'; secret[22] = '4'; secret[23] = '0';
    secret[24] = 'a'; secret[25] = '6'; secret[26] = 'b'; secret[27] = 'd';
    secret[28] = 'e'; secret[29] = '7'; secret[30] = '9'; secret[31] = '9';
    secret[32] = '\0'; // 必须有结束符，否则会出现你看到的“鐢熸垚鐨”乱码

    return env->NewStringUTF(secret);
}
