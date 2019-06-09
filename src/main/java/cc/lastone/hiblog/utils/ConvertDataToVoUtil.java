package cc.lastone.hiblog.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ConvertDataToVoUtil {

    /**
     * 将原生查询数据转换成对象
     *
     * @param objects
     * @param classT
     * @param <T>
     * @return
     */
    public static <T> List<T> convert(List<Object[]> objects, Class<T> classT) {
        List<T> list = new ArrayList<T>();
        if (objects.size() > 0) {
            // 获得成员变量
            Field[] fields = classT.getDeclaredFields();
            for (Object[] object : objects) {
                int length = object.length;
                T tempObj = null;
                try {
                    tempObj = classT.newInstance();
                    for (int i = 0; i < length; i++) {
//                        System.out.println("------------------");
//                        System.out.println(object[i]);
//                        System.out.println(object[i].getClass().toString());
//                        System.out.println("------------------");
                        setter(tempObj, fields[i].getName(), object[i], fields[i].getType());
                    }
                    //System.out.println("---------------------------------------------");
                    //System.out.println(tempObj.toString());
                    //System.out.println("---------------------------------------------");
                    list.add(tempObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    /**
     * @param obj   要操作的对象
     * @param attr  要操作的属性
     * @param value 要设置的属性数据
     * @param type  要设置的属性的类型
     */
    public static void setter(Object obj, String attr, Object value, Class<?> type) {
        try {
            Method met = obj.getClass().getMethod("set" + initStr(attr), type);
            met.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将单词首字母大写
     *
     * @param old
     * @return
     */
    public static String initStr(String old) {
        return old.substring(0, 1).toUpperCase() + old.substring(1);
    }


}
