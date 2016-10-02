package com.bjsts.core.util;

import org.junit.Test;

/**
 * @author sunshow
 */
public final class IdCardUtilsTest {

    @Test
    public void testValidate() throws Exception {
        IdCardUtils.IdCard idCard = IdCardUtils.validate("XXXXX");

        System.out.println(idCard.getRegionCode());
        System.out.println(idCard.getBirthday());
        System.out.println(idCard.getRandomCode());
        System.out.println(idCard.getCheckCharacter());
        System.out.println(idCard.getGenderType());
        System.out.println(idCard.getAge());
    }
}
