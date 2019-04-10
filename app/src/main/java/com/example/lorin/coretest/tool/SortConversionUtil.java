package com.example.lorin.coretest.tool;

import android.content.Context;

import java.util.Locale;

/**
 * Created by lorin on 16/6/30.
 */
public class SortConversionUtil {

    /**
     * 将数字转换次序 例如1转化为1st，支持英语展示，中文状态下默认不变
     *
     * @param context
     * @param number
     * @return
     */
    public static String numberToENSort(Context context, int number) {

        Locale curLocale = context.getResources().getConfiguration().locale;

        if (curLocale.getLanguage().equals("en")) {
            return number + "";
        }

        String suffix = "th";

        if ((number % 100) != 11 && String.valueOf(number).endsWith("1")) {
            suffix = "st";
        } else if ((number % 100) != 12 && String.valueOf(number).endsWith("2")) {
            suffix = "nd";
        } else if ((number % 100) != 12 && String.valueOf(number).endsWith("3")) {
            suffix = "nd";
        }

        return number + suffix;
    }


}
