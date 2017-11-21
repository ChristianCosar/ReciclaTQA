package com.kodevian.reciclapp.util;

/**
 * Created by manu on 11/02/16.
 */
public class UtilString {
    public static String capitalizeFirstLetter(String original) {
        char[] chars = original.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
