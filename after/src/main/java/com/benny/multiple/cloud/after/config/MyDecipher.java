package com.benny.multiple.cloud.after.config;

import com.benny.multiple.cloud.after.utils.AES256Encrypt;
import com.huawei.devspore.mas.password.Decipher;
import org.apache.commons.lang3.StringUtils;

/**
 * 解密接口。配置文件中的密文即通过这个接口解密
 * com.huawei.devspore.mas.password.Decipher
 */
public class MyDecipher implements Decipher {

    @Override
    public String decode(String s) {
        if(StringUtils.isBlank(s)){
            return "";
        }
        String decrypt = AES256Encrypt.decrypt(s);
        return decrypt;
    }

    public String encode(String s){
        String encrypt = AES256Encrypt.encrypt(s);
        return encrypt;
    }
}
