package com.example.studystudymorestudyforever.encode;

import android.util.Log;

import com.example.vankhanhpr.vidu2.encode.DataCryption;

import java.util.Random;

/**
 * Created by VANKHANHPR on 7/7/2017.
 */

public class Encode {
    String TAG = DataCryption.class.getSimpleName();
    private final int MAX_SEC = 20;
    private final int MAX_ITEM = 20;
    private final String MARSK_DATA = "A@m";
    private final int BASIC_NUM = 40;

    private int[][] secret = {{191, 71, 77, 46, 58, 49, 44, 43, 55, 101, 122, 45, 124, 137, 237, 117, 71, 76, 129, 120},
            {114, 41, 45, 212, 49, 59, 14, 58, 46, 57, 133, 225, 219, 116, 222, 211, 119, 95, 128, 146},
            {81, 71, 44, 50, 218, 89, 44, 113, 115, 111, 122, 95, 41, 137, 137, 67, 112, 76, 129, 90},
            {40, 117, 120, 126, 78, 79, 54, 133, 110, 81, 91, 95, 94, 47, 57, 66, 82, 60, 122, 70},
            {80, 71, 122, 122, 128, 91, 94, 73, 45, 51, 213, 75, 214, 57, 77, 60, 232, 116, 120, 130},
            {114, 72, 54, 46, 78, 79, 212, 112, 115, 118, 122, 52, 54, 77, 117, 67, 222, 127, 91, 40},
            {51, 70, 128, 99, 121, 89, 74, 113, 155, 111, 124, 45, 90, 87, 77, 47, 82, 76, 129, 120},
            {111, 77, 47, 57, 78, 59, 94, 113, 115, 121, 128, 51, 44, 47, 77, 67, 52, 66, 69, 80},
            {117, 74, 71, 35, 48, 49, 41, 43, 65, 70, 129, 115, 124, 71, 57, 70, 82, 76, 119, 110},
            {79, 57, 137, 136, 75, 49, 141, 239, 31, 116, 98, 57, 40, 117, 93, 84, 81, 170, 190, 121},
            {121, 51, 71, 40, 54, 149, 74, 42, 57, 104, 112, 145, 134, 127, 167, 127, 76, 41, 122, 132},
            {84, 42, 42, 212, 46, 59, 11, 41, 46, 57, 133, 225, 219, 116, 222, 231, 119, 95, 128, 146},
            {83, 78, 47, 51, 211, 89, 43, 113, 115, 111, 122, 95, 141, 137, 137, 67, 122, 76, 129, 92},
            {42, 127, 220, 122, 178, 79, 54, 133, 110, 81, 91, 95, 94, 47, 57, 66, 81, 60, 122, 71},
            {81, 70, 142, 121, 138, 91, 94, 73, 45, 51, 213, 175, 214, 57, 77, 60, 212, 116, 120, 120},
            {54, 78, 44, 43, 77, 79, 112, 142, 115, 118, 122, 52, 54, 77, 147, 67, 222, 137, 111, 70},
            {61, 74, 118, 89, 122, 83, 74, 113, 55, 111, 124, 45, 90, 87, 77, 47, 82, 76, 129, 220},
            {121, 87, 87, 67, 784, 52, 94, 113, 95, 121, 128, 51, 44, 47, 77, 67, 52, 66, 69, 55},
            {97, 71, 70, 45, 481, 42, 41, 43, 65, 70, 129, 115, 114, 71, 57, 70, 82, 76, 119, 44},
            {69, 52, 90, 46, 70, 41, 141, 239, 31, 116, 98, 57, 140, 117, 93, 84, 81, 170, 190, 77}
    };

    private int GetKeySecret() {
        Random random = new Random();
        return random.nextInt(MAX_SEC - 1);
    }

    public String encryptString(String originalData) throws Exception {
        byte[] textToEncrypt = originalData.getBytes("UTF-8");
        if (originalData.contains("\"") ||
                originalData.contains("\'") ||
                originalData.contains("\\"))
            return new String("".getBytes(), "UTF-8");
        return new String(encrypt(textToEncrypt), "UTF-8");
    }

    public String decryptString(String originalData) throws Exception {
        byte[] textToEncrypt = originalData.getBytes("UTF-8");
        return new String(decrypt(textToEncrypt), "UTF-8");
    }

    public byte[] encrypt(byte[] InStr) {
        byte[] OutEncryptStr = new byte[2 * InStr.length + 1];
        int StrLen = InStr.length;
        int i = 0, j = 0, k = 0, key = 0;
        int X = 0, Num = 0;

        key = GetKeySecret();
        Log.d(TAG, "Key: " + key);

        OutEncryptStr[k++] = (byte) (key + BASIC_NUM);
        for (i = 0; i < StrLen; i++) {
            X = InStr[i] + secret[key][j++];

            if (j == MAX_ITEM)
                j = 0;
            Num = 0;
            if (X > BASIC_NUM) {
                Num = (int) X / BASIC_NUM;
                X = X % BASIC_NUM + BASIC_NUM;
            }

            OutEncryptStr[k++] = (byte) (Num + BASIC_NUM);
            OutEncryptStr[k++] = (byte) X;
        }
        return OutEncryptStr;
    }

    public byte[] decrypt(byte[] InStr) {
        byte[] OutDecryptStr = new byte[InStr.length / 2];
        int StrLen = InStr.length;
        int i = 0, j = 0, k = 0, key = 0;
        int X = 0, Num = 0;

        if (StrLen % 2 == 0)
            return (null);

        //Log.d(TAG, "InStr[0]: " + InStr[0]);
        key = InStr[0] - BASIC_NUM;
        Log.d(TAG, "Key: " + key);

        for (i = 1; i < StrLen; i++) {
            if (i % 2 != 0) {
                Num = InStr[i] - BASIC_NUM;
                continue;
            }
            if (Num > 0)
                X = Num * BASIC_NUM + InStr[i] - BASIC_NUM - secret[key][j];
            else
                X = InStr[i] - secret[key][j];
            j++;
            if (j == MAX_ITEM)
                j = 0;

            if (X <= 0) {
                //printf("MyDecrypt >> Data incorrect format. Strlen: %d, Source string: %s At position: %d, KEY: %d\n", StrLen, InStr, i, key);
                return (null);
            }
            OutDecryptStr[k++] = (byte) X;
        }

        return OutDecryptStr;
    }

}
