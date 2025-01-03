package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.SearchFilters;
import com.adeies.adeies.enterprise.dto.Transactions.TransactionsDTO;
import com.adeies.adeies.enterprise.dto.Transactions.TrxStatusUpdate;
import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.dto.daysOff.UpdateRequestRq;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.entities.User;
import com.adeies.adeies.enterprise.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionsService {
    void requestDaysOff(RequestDaysOffRq rq, OAuth2User user);

    void updateDayOffRequest(UpdateRequestRq rq, OAuth2User user);

    Page<Transactions> getUsersReq(Long id, Pageable pageable);

    Integer calculateDaysRequested(User user, Long definitionId);

    List<TransactionsDTO> getTrxByDepartment(User user, Pageable pageable, List<Status> statuses);

    void updateTrxStatus(TrxStatusUpdate trxStatusUpdate, User user);

}
