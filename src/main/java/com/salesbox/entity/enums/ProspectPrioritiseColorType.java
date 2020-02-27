package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/27/14
 * Time: 5:43 PM
 */
public enum ProspectPrioritiseColorType
{
       GREEN,
    RED,
    YELLOW;

    public static Integer indexOf(ProspectPrioritiseColorType prospectPrioritiseColorType) {
        for (int i = 0; i < ProspectPrioritiseColorType.values().length; i++) {
            if (ProspectPrioritiseColorType.values()[i].equals(prospectPrioritiseColorType)) {
                return i;
            }
        }
        return null;
    }
}
