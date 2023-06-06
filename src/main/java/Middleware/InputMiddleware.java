package Middleware;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputMiddleware {
    private static final String passwordPatternStr = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-[{}]:;',?/*~$^+=<>]).{8,12}$";
    private static final String digitPatternStr = "^\\d+$";
    private static final String negativeDigitPatternStr = "^-[1-9]\\d*|0$";
    public static final String doubleNegativeStr = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
    public static final String doublePositiveStr = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
    private static final Pattern negativeDoublePattern = Pattern.compile(doubleNegativeStr);
    private static final Pattern doublePositivePattern = Pattern.compile(doublePositiveStr);
    private static final Pattern negativeDigitPattern = Pattern.compile(negativeDigitPatternStr);
    private static final String whiteSpacePatternStr = "\\s+";
    private static final Pattern passPattern = Pattern.compile(passwordPatternStr);
    private static final Pattern digitPattern = Pattern.compile(digitPatternStr);
    private static final Pattern whiteSpacePattern = Pattern.compile(whiteSpacePatternStr);
    public static boolean isValidPassword(String password){
        Matcher matcher = passPattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidPhoneNum(String phoneNum){
        Matcher matcher = digitPattern.matcher(phoneNum);
        if(matcher.matches()){
            return phoneNum.length() == 10;
        }else{
            return false;
        }
    }

    public static boolean isValidUsername(String username){
        Matcher matcher = whiteSpacePattern.matcher(username);
        if(matcher.find()){
            System.out.println("hehe");
            return false;
        }else {
            return username.length() >= 12;
        }
    }
    public static boolean isPositive(String num) {
        if(num.isEmpty()){
            return false;
        }
        Matcher matcher = digitPattern.matcher(num);
        Matcher matcher1 = doublePositivePattern.matcher(num);
        if(!matcher.matches() && !matcher1.matches()){
            return false;
        }

        return Double.parseDouble(num) >= 0;
    }
    public static boolean isValidCopy(String num){
        Matcher matcher = digitPattern.matcher(num);
        Matcher matcher1 = negativeDigitPattern.matcher(num);
        return matcher.matches() || matcher1.matches();
    }
    public static boolean isValidNumber(String num){
        Matcher matcher = negativeDoublePattern.matcher(num);
        Matcher matcher2 = doublePositivePattern.matcher(num);
        Matcher matcher1 = digitPattern.matcher(num);
        return matcher.matches() || matcher1.matches() || matcher2.matches();
    }

    public static boolean isValidIString(int length, String inputI4){
        return inputI4.length() <= length;
    }

    public static boolean isValidLowerBound(int length, String inputI4){
        return inputI4.length() >= length;
    }

}
