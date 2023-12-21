package com.adeies.adeies.enterprise.entities;

import com.adeies.adeies.enterprise.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsStatus {

    private String statusCode;
    private ErrorCode errorCode;




}
