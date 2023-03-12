package com.nttdata.btc.account.app.service;

import com.nttdata.btc.account.app.model.request.AccountRequest;
import com.nttdata.btc.account.app.model.request.UpdateAccountRequest;
import com.nttdata.btc.account.app.model.response.AccountResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service AccountService.
 *
 * @author lrs.
 */
public interface AccountService {
    /**
     * Method findAll.
     */
    Flux<AccountResponse> findAll();

    /**
     * Method findById.
     */
    Mono<AccountResponse> findById(String id);

    /**
     * Method save.
     */
    Mono<AccountResponse> save(AccountRequest request);

    /**
     * Method Delete.
     */
    Mono<Void> delete(String id);

    /**
     * Method update account.
     */
    Mono<AccountResponse> update(UpdateAccountRequest request);
}