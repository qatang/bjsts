package com.bjsts.core.util;

import com.bjsts.core.enums.GenderType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunshow
 */
public final class IdCardUtils {
    protected static final transient Logger logger = LoggerFactory.getLogger(IdCardUtils.class);

    /**
     * 18位身份证号校验(不再支持15位身份证号)
     * @param number 身份证号
     */
    public static IdCard validate(String number) {
        return new IdCard(number);
    }

    public static class IdCard {
        // 身份证号
        private String idCard;

        // 性别
        private GenderType genderType;

        // 地区编码
        private String regionCode;

        // 生日: yyyy-MM-dd
        private String birthday;

        // 年龄
        private Integer age;

        // 随机码
        private String randomCode;

        private String checkCharacter;

        private static int[] weight = new int[] {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};

        public IdCard(String number) {
            if (StringUtils.length(number) != 18) {
                throw new RuntimeException("身份证号码位数不正确");
            }
            if (!CoreStringUtils.isNumeric(number)) {
                throw new RuntimeException("身份证号码不全为数字");
            }
            this.idCard = number;

            this.checkCharacter = StringUtils.substring(idCard, 17);
            this.regionCode = StringUtils.substring(idCard, 0, 6);
            this.birthday = StringUtils.substring(idCard, 6, 14);
            this.randomCode = StringUtils.substring(idCard, 14, 17);

            int sum = 0;

            for (int i = 1; i < number.length(); i++) {
                int n = Integer.parseInt(StringUtils.substring(number, number.length() - 1 - i, number.length() - i));

                sum += n * weight[number.length() - 1 - i];
            }

            int checkValue = (12 - (sum % 11)) % 11;

            if (!String.valueOf(checkValue).equals(checkCharacter)) {
                throw new RuntimeException("身份证校验码不正确");
            }

            int genderValue = Integer.parseInt(StringUtils.substring(randomCode, 2));
            if (genderValue % 2 == 0) {
                this.genderType = GenderType.Female;
            } else {
                this.genderType = GenderType.Male;
            }

            this.age = CoreDateUtils.calculateAge(CoreDateUtils.parseDate(birthday, "yyyyMMdd"));
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public GenderType getGenderType() {
            return genderType;
        }

        public void setGenderType(GenderType genderType) {
            this.genderType = genderType;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getRandomCode() {
            return randomCode;
        }

        public void setRandomCode(String randomCode) {
            this.randomCode = randomCode;
        }

        public String getCheckCharacter() {
            return checkCharacter;
        }

        public void setCheckCharacter(String checkCharacter) {
            this.checkCharacter = checkCharacter;
        }
    }
}
