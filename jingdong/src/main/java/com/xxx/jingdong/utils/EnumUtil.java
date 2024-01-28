package com.xxx.jingdong.utils;


import com.xxx.jingdong.enums.CodeEnum;

/**
 * Author；Aaron
 */
public class EnumUtil {

    /**
     * getEnumConstants 用于返回枚举常量数组，换句话说，可以说此方法用于返回此枚举类的元素，
     *该数组包含由此Class对象按其声明的顺序表示的枚举类的值。
     *如果此Class对象不表示枚举类型，则返回null，
     * @param code
     * @param enumClass
     * @param <T>
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each:enumClass.getEnumConstants()){
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
