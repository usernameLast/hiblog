package cc.lastone.hiblog.utils;

import java.util.Random;

public class MyRandomUtil {
    private final static String BASE_STR = "0123456789qwertyuiopasdfghjklzxcvbnm";

    public static String randomStr(int i) {
        StringBuilder sb = new StringBuilder();
        if (i > 0) {
            int length = BASE_STR.length();
            Random r = new Random();
            for (int j = 0; j < i; j++) {
//                System.out.println(r.nextInt(length));
                sb.append(BASE_STR.charAt(r.nextInt(length)));
            }
        }
        return sb.toString();
    }
}
