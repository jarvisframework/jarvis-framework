package com.jarvis.framework.webmvc.util;

public abstract class DesensitizationUtil {

    public static String desensitize(String origin, int skipPreLength, int skipSuffixLength, String symbol) {
        if (origin == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            int i = 0;

            for(int n = origin.length(); i < n; ++i) {
                if (i < skipPreLength) {
                    sb.append(origin.charAt(i));
                } else if (i > n - skipSuffixLength - 1) {
                    sb.append(origin.charAt(i));
                } else {
                    sb.append(symbol);
                }
            }

            return sb.toString();
        }
    }

    public static String chineseName(String fullName) {
        return fullName == null ? null : desensitize(fullName, 0, 1, "*");
    }

    public static String idCardNum(String id) {
        return desensitize(id, 6, 4, "*");
    }

    public static String fixedPhone(String num) {
        return desensitize(num, 0, 4, "*");
    }

    public static String mobilePhone(String num) {
        return desensitize(num, 3, 4, "*");
    }

    public static String address(String address) {
        return desensitize(address, 6, 0, "*");
    }

    public static String email(String email) {
        if (email == null) {
            return null;
        } else {
            int index = email.indexOf("@");
            if (index <= 1) {
                return email;
            } else {
                String preEmail = desensitize(email.substring(0, index), 1, 0, "*");
                return preEmail + email.substring(index);
            }
        }
    }

    public static String bankCard(String cardNum) {
        return desensitize(cardNum, 6, 4, "*");
    }

    public static String password(String password) {
        return password == null ? null : "******";
    }

    public static String carNumber(String carNumber) {
        return null == carNumber ? null : desensitize(carNumber, 2, 1, "*");
    }

    public static String key(String key) {
        if (key == null) {
            return null;
        } else {
            StringBuilder tmpKey = new StringBuilder(desensitize(key, 0, 3, "*"));
            if (tmpKey.length() > 6) {
                return tmpKey.substring(tmpKey.length() - 6);
            } else if (tmpKey.length() >= 6) {
                return tmpKey.toString();
            } else {
                int buffLength = 6 - tmpKey.length();

                for(int i = 0; i < buffLength; ++i) {
                    tmpKey.insert(0, "*");
                }

                return tmpKey.toString();
            }
        }
    }
}
