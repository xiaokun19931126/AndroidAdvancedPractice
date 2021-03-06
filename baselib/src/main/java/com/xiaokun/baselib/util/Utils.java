package com.xiaokun.baselib.util;

import android.content.Context;
import android.util.TypedValue;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

/**
 * <pre>
 *     作者   : 肖坤
 *     时间   : 2018/05/09
 *     描述   :
 *     版本   : 1.0
 * </pre>
 */
public class Utils {
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取actionbar高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    //生成随机数
    public static int testRandom1() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            System.out.println("random.nextInt()=" + random.nextInt());
        }
        System.out.println("/////以上为testRandom1()的测试///////");
        return random.nextInt();
    }


    //在一定范围内生成随机数.
    //比如此处要求在[0 - n)内生成随机数.
    //注意:包含0不包含n
    public static void testRandom2() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println("random.nextInt()=" + random.nextInt(20));
        }
        System.out.println("/////以上为testRandom2()的测试///////");
    }


    //在一定范围内生成不重复的随机数
    //在testRandom2中生成的随机数可能会重复.
    //在此处避免该问题
    public static void testRandom3() {
        HashSet<Integer> integerHashSet = new HashSet<Integer>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randomInt = random.nextInt(20);
            System.out.println("生成的randomInt=" + randomInt);
            if (!integerHashSet.contains(randomInt)) {
                integerHashSet.add(randomInt);
                System.out.println("添加进HashSet的randomInt=" + randomInt);
            } else {
                System.out.println("该数字已经被添加,不能重复添加");
            }
        }
        System.out.println("/////以上为testRandom3()的测试///////");
    }

    /**
     * 随机生成字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 返回手机号码
     */
    private static String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");

    /**
     * 随机生成电话号码
     *
     * @return
     */
    public static String getTel() {
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    public static int getNum(int start, int end) {
        return (int) (Math.random() * (end - start + 1) + start);
    }

}
