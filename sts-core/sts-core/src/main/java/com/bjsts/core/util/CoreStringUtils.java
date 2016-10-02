package com.bjsts.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author qatang
 * @since 2015-07-20 11:05
 */
public class CoreStringUtils {
    protected static final transient Logger logger = LoggerFactory.getLogger(CoreStringUtils.class);

    public static String generateRandomStr(int len) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i ++) {
            Random random = new Random();
            result.append(chars.charAt(random.nextInt(chars.length() - 1)));
        }
        return result.toString();
    }

    public static String generateRandomCode(int len) {
        String chars = "0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i ++) {
            Random random = new Random();
            result.append(chars.charAt(random.nextInt(chars.length() - 1)));
        }
        return result.toString();
    }

    /**
     * 检验中国的手机号格式
     * @param phone 不含+86/8086等前缀的11位手机号
     * @return 检测通过则返回true
     */
    public static boolean checkChinaMobilePhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            logger.error("手机号不能为空, phone={}", phone);
            return false;
        }

        if (phone.length() != 11) {
            logger.error("手机号位数不正确, phone=\"{}\"", phone);
            return false;
        }

        if (!isNumeric(phone)) {
            logger.error("手机号必须全为数字, phone={}", phone);
            return false;
        }

        if (!StringUtils.startsWithAny(phone, "13", "14", "15", "17", "18")) {
            logger.error("手机号前两位非法, phone={}", phone);
            return false;
        }

        return true;
    }

    /**
     * 检查手机号并返回错误信息
     * @param phone
     * @return
     */
    public static String checkChinaMobilePhoneWithMessage(String phone) {
        if (StringUtils.isBlank(phone)) {
            return "手机号不能为空";
        }

        if (phone.length() != 11) {
            return "手机号位数不正确";
        }

        if (!isNumeric(phone)) {
            return "手机号必须全为数字";
        }

        if (!StringUtils.startsWithAny(phone, "13", "14", "15", "17", "18")) {
            return "手机号前两位非法";
        }

        return null;
    }

    /**
     * 检测字符串是否仅包含数字
     * @param s 待测试字符串
     * @return 检测通过返回true
     */
    public static boolean isNumeric(String s) {
        return StringUtils.containsOnly(s, "0123456789");
    }

    public static String uniqueId(String prefix) {
        SecureRandom sec = new SecureRandom();
        byte[] sbuf = sec.generateSeed(8);
        ByteBuffer bb = ByteBuffer.wrap(sbuf);

        long time = System.currentTimeMillis();
        String uniqid = String.format("%s%08x%05x", prefix, time / 1000, time);
        uniqid += "." + String.format("%.8s", "" + bb.getLong() * -1);
        return uniqid;
    }

    /**
     * 字符串打码
     * @param s 待打码字符串
     * @param leftRest 左边预留
     * @param rightRest 右边预留
     * @param mosaic 替换字符
     * @param mosaicCount 如果大于零则按指定数量显示替换字符个数, 否则保持原始长度
     * @return 打码后的字符串
     */
    public static String mask(String s, int leftRest, int rightRest, String mosaic, int mosaicCount) {
        int length = StringUtils.length(s);
        if (leftRest + rightRest >= length) {
            // 超过长度不做打码原样返回
            return s;
        }

        String left = StringUtils.left(s, leftRest);
        String right = StringUtils.right(s, rightRest);

        String middle;
        if (mosaicCount <= 0) {
            middle = StringUtils.repeat(mosaic, length - leftRest - rightRest);
        } else {
            middle = StringUtils.repeat(mosaic, mosaicCount);
        }

        return left + middle +right;
    }

    /**
     * 手机号打码
     * @param phone 11位长度的中国手机号
     * @return 打码后的手机号
     */
    public static String maskChinaMobilePhone(String phone) {
        return mask(phone, 3, 4, "*", 0);
    }

    /**
     * 中文姓名打码
     * @param name 中文姓名
     * @return 打码后的中文姓名
     */
    public static String maskChineseName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return mask(name, 0, name.length() - 1, "*", 0);
        }
        return "*";
    }

    /**
     * 身份证打码
     * @param idCard 身份证号
     * @return 打码后的身份证号
     */
    public static String maskIdCard(String idCard) {
        return mask(idCard, 3, 4, "*", 0);
    }

    /**
     * 银行卡号打码
     * @param bankCard 银行卡号
     * @return 打码后的银行卡号
     */
    public static String maskBankCard(String bankCard) {
        return mask(bankCard, 4, 4, "*", 10);
    }

    /**
     * 昵称或用户名打码
     * @param nickname 昵称
     * @return 打码后的昵称
     */
    public static String maskNickname(String nickname) {
        int length = StringUtils.length(nickname);

        // 4个字符以内展示一半并填充马赛克
        if (length <= 4) {
            return mask(nickname, length / 2, 0, "*", 6);
        } else  {
            // 否则展示前2后2并填充马赛克
            return mask(nickname, 2, 2, "*", 4);
        }
    }

    /**
     * 校验用户名是否合法
     * @param username 用户名
     * @return 是否合法
     */
    public static boolean checkUsername(String username) {
        // 长度4到20位
        if (StringUtils.isBlank(username) || username.length() > 20) {
            return false;
        }

        // 纯中文名-长度至少2
        if (Pattern.matches("^[\\u4e00-\\u9fa5]+$", username) && username.length() >= 2) {
            return true;
        }

        // 非纯中文长度至少4
        if (username.length() < 4) {
            return false;
        }

        // 不能为11位纯数字-手机号
        String phoneFmt = "^\\d{11}$";
        if (Pattern.matches(phoneFmt, username)) {
            return false;
        }

        // 只能包含小写字母、大写字母、数字、-、_、中文
        String reg = "^[a-zA-Z0-9_\\-\\u4e00-\\u9fa5]+$";
        return Pattern.matches(reg, username);
    }

    /**
     * 检查用户名并返回错误信息
     * @param username
     * @return
     */
    public static String checkUsernameWithMessage(String username) {
        // 长度4到20位
        if (StringUtils.isBlank(username) || username.length() > 20) {
            return "用户名长度不能超过20位";
        }

        // 纯中文名-长度至少2
        if (Pattern.matches("^[\\u4e00-\\u9fa5]+$", username) && username.length() >= 2) {
            return "中文用户名长度至少为2位";
        }

        // 非纯中文长度至少4
        if (username.length() < 4) {
            return "非中文用户名长度至少为4位";
        }

        // 不能为11位纯数字-手机号
        String phoneFmt = "^\\d{11}$";
        if (Pattern.matches(phoneFmt, username)) {
            return "用户名不能为11位纯数字";
        }

        // 只能包含小写字母、大写字母、数字、-、_、中文
        String reg = "^[a-zA-Z0-9_\\-\\u4e00-\\u9fa5]+$";
        if (!Pattern.matches(reg, username)) {
            return "用户名只能包含大小写字母、数字、中文、或-、_";
        }
        return null;
    }

    /**
     * 查找第一个非空的字符串
     * @param strings 要查找的字符串列表
     * @return 第一个非空的字符串, 如果未找到则返回null
     */
    public static String firstNotBlank(String... strings) {
        for (String string : strings) {
            if (StringUtils.isNotBlank(string)) {
                return string;
            }
        }
        return null;
    }
}
