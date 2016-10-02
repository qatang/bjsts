package com.bjsts.core.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * author: sunshow.
 */
public class CoreStringUtilsTest {

    @Test
    public void testMask() throws Exception {
        String string ="1234567891";
        System.out.println(CoreStringUtils.mask(string, 3, 4, "*", 0));
    }

    @Test
    public void testMaskChinaMobilePhone() throws Exception {
        Assert.assertEquals("186****6725", CoreStringUtils.maskChinaMobilePhone("18610116725"));
    }

    @Test
    public void testMaskChineseName() throws Exception {
        System.out.println(CoreStringUtils.maskChineseName("卢测试"));
    }

    @Test
    public void testMaskIdCard() throws Exception {
        System.out.println(CoreStringUtils.maskIdCard("33072342234423423423"));
    }

    @Test
    public void testMaskBankCard() throws Exception {
        System.out.println(CoreStringUtils.maskBankCard("6234444377886889"));
    }

    @Test
    public void testMaskNickname() throws Exception {
        System.out.println(CoreStringUtils.maskNickname("sun"));
        System.out.println(CoreStringUtils.maskNickname("sunshow"));
        System.out.println(CoreStringUtils.maskNickname("sunshow2002"));
    }
}
