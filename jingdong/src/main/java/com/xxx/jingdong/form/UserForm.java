package com.xxx.jingdong.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class UserForm {
    @NotEmpty(message = "请填写用户名")
    private String username;
    @NotEmpty(message = "请填写密码")
    private String password;
    @NotEmpty(message = "请确认填写密码")
    private String repassword;
}
