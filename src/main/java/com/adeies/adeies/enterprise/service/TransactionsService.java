package com.adeies.adeies.enterprise.service;

import com.adeies.adeies.enterprise.dto.daysOff.RequestDaysOffRq;
import com.adeies.adeies.enterprise.entities.Transactions;
import com.adeies.adeies.enterprise.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionsService {
    void requestDaysOff(RequestDaysOffRq rq, OAuth2User user);

    Page<Transactions> getUsersReq (Long id, Pageable pageable);

}
