package com.nttdata.btc.account.app.service.impl;

import com.nttdata.btc.account.app.model.entity.Account;
import com.nttdata.btc.account.app.model.request.AccountRequest;
import com.nttdata.btc.account.app.model.request.UpdateAccountRequest;
import com.nttdata.btc.account.app.model.response.AccountResponse;
import com.nttdata.btc.account.app.repository.AccountRepository;
import com.nttdata.btc.account.app.service.AccountService;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.nttdata.btc.account.app.util.constant.Constants.DEFAULT_FALSE;

/**
 * Class implement methods from AccountService.
 *
 * @author lrs
 */
@Service
public class AccountServiceImpl implements AccountService {

    /**
     * Inject dependency {@link AccountRepository}
     */
    @Autowired
    private AccountRepository repository;

    /**
     * This method return all accounts.
     *
     * @return {@link List<AccountResponse>}
     */
    @Override
    public Flux<AccountResponse> findAll() {
        return repository.findAll().filter(Account::isStatus)
                .map(c -> buildAccountR.apply(c))
                .onErrorReturn(new AccountResponse());
    }

    /**
     * This method return all accounts by holder.
     *
     * @return {@link List<AccountResponse>}
     */
    @Override
    public Flux<AccountResponse> findAllByHolder(String id) {
        return repository.findAll().filter(entity -> entity.getHolder_account().stream()
                .anyMatch(s -> s.equalsIgnoreCase(id)))
                .map(filtered -> buildAccountR.apply(filtered))
                .onErrorReturn(new AccountResponse());
    }

    /**
     * This method find a account by id.
     *
     * @param id {@link String}
     * @return {@link AccountResponse}
     */
    @Override
    public Mono<AccountResponse> findById(String id) {
        return repository.findById(id)
                .filter(Account::isStatus)
                .map(e -> buildAccountR.apply(e))
                .onErrorReturn(new AccountResponse());
    }

    /**
     * This method save a account.
     *
     * @param request {@link AccountRequest}
     * @return {@link AccountResponse}
     */
    @Override
    public Mono<AccountResponse> save(AccountRequest request) {
        return repository.save(buildAccount.apply(request))
                .flatMap(entity -> Mono.just(buildAccountR.apply(entity)))
                .onErrorReturn(new AccountResponse());
    }

    /**
     * This method update status from account.
     *
     * @param id {@link String}
     * @return {@link Void}
     */
    @Override
    public Mono<Void> delete(String id) {
        return repository.findById(id).filter(Account::isStatus)
                .map(e -> updateStatus.apply(e, DEFAULT_FALSE))
                .flatMap(e -> repository.delete(e));
    }

    /**
     * This method update a account.
     *
     * @param request {@link UpdateAccountRequest}
     * @return {@link AccountResponse}
     */
    @Override
    public Mono<AccountResponse> update(UpdateAccountRequest request) {
        return repository.findById(request.getId_account())
                .map(entity -> updateAccount.apply(request, entity))
                .flatMap(account -> repository.save(account))
                .flatMap(aupdated -> Mono.just(buildAccountR.apply(aupdated)))
                .onErrorReturn(new AccountResponse());
    }

    /**
     * BiFunction update Account.
     */
    BiFunction<UpdateAccountRequest, Account, Account> updateAccount = (request, bean) -> {
        bean.setCode_account(request.getCode_account());
        bean.setHolder_account(request.getHolder_account());
        bean.setAuthorized_signer(request.getAuthorized_signer());
        bean.setProduct(request.getProduct());
        bean.setBalance(request.getBalance());
        bean.setTransactions(request.getTransactions());
        return bean;
    };

    /**
     * BiFunction updateStatus from Account.
     */
    BiFunction<Account, Boolean, Account> updateStatus = (product, status) -> {
        product.setStatus(status);
        return product;
    };

    /**
     * Function build new Account.
     */
    Function<AccountRequest, Account> buildAccount = request -> new Account(request.getCode_account(),
            request.getHolder_account(), request.getAuthorized_signer(), request.getProduct(),
            request.getBalance(), request.getTransactions());

    /**
     * Function build new AccountResponse.
     */
    Function<Account, AccountResponse> buildAccountR = entity -> {
        AccountResponse response = new AccountResponse();
        response.setId_account(entity.getId_account());
        response.setCode_account(entity.getCode_account());
        response.setHolder_account(entity.getHolder_account());
        response.setAuthorized_signer(entity.getAuthorized_signer());
        response.setProduct(entity.getProduct());
        response.setBalance(entity.getBalance());
        response.setTransactions(entity.getTransactions());
        response.setRegister_date(entity.getRegister_date());
        response.setStatus(entity.isStatus());
        return response;
    };
}