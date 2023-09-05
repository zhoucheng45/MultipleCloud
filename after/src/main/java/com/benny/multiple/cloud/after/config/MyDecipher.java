package com.benny.multiple.cloud.after.config;

import com.benny.multiple.cloud.after.AES256Encrypt;
import com.huawei.devspore.mas.password.Decipher;
import org.apache.commons.lang3.StringUtils;

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
