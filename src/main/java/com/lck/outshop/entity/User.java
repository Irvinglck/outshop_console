package com.lck.outshop.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private String name;
    private String pwd;
    private String captcha;
    private String phone;
    private String code;
}
