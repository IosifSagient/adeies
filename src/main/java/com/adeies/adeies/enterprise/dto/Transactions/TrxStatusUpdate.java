package com.adeies.adeies.enterprise.dto.Transactions;

import com.adeies.adeies.enterprise.enums.Status;
import lombok.Data;

@Data
public class TrxStatusUpdate {
    private Long trxId;
    private Status status;
}
