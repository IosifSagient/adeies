package com.adeies.adeies.enterprise.dto.user;

import lombok.Data;

@Data
public class ChangePasswordRq {
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
