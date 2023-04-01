package com.nttdata.btc.account.app.service;

import com.nttdata.btc.account.app.model.entity.Account;
import com.nttdata.btc.account.app.model.request.AccountRequest;
import com.nttdata.btc.account.app.model.request.UpdateAccountRequest;
import com.nttdata.btc.account.app.model.response.AccountResponse;
import com.nttdata.btc.account.app.repository.AccountRepository;
import com.nttdata.btc.account.app.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountServiceImpl service;

    List<Account> listAccount = new ArrayList<>();

    static final String CODE_HOLDER = "640bf4a36bf23c1c772da9d6";
    static final String ID_ACCOUNT_01 = "640cc29c60650d1637e040a9";

    @BeforeEach
    private void setUp() {
        Account beanResponse = new Account();
        beanResponse.setId_account(ID_ACCOUNT_01);
        beanResponse.setCode_account("193-1853946-0-26");
        beanResponse.setHolder_account(Collections.singletonList(CODE_HOLDER));
        beanResponse.setAuthorized_signer(Collections.singletonList("640cab9692b061681e7d4d86"));
        beanResponse.setProduct("640c24cd3b905b25cfa2f25a");
        beanResponse.setBalance(350.0);
        beanResponse.setTransactions(new ArrayList<>());
        beanResponse.setRegister_date(new Date());
        beanResponse.setStatus(true);

        listAccount.add(beanResponse);
    }

    @Test
    @DisplayName("Return all accounts")
    void testFindAllAccounts() {
        when(repository.findAll()).thenReturn(Flux.fromIterable(listAccount));

        Flux<AccountResponse> result = service.findAll();

        assertEquals(result.blockFirst().getId_account(), listAccount.get(0).getId_account());
    }

    @Test
    @DisplayName("Return all accounts by holder")
    void testFindAllByHolder() {
        when(repository.findAll()).thenReturn(Flux.fromIterable(listAccount));

        Flux<AccountResponse> result = service.findAllByHolder(CODE_HOLDER);

        assertEquals(listAccount.get(0).getCode_account(), result.blockFirst().getCode_account());
    }

    @Test
    @DisplayName("Return account by id")
    void testFindById() {
        when(repository.findById(anyString())).thenReturn(Mono.just(listAccount.get(0)));

        Mono<AccountResponse> result = service.findById(ID_ACCOUNT_01);

        assertEquals(listAccount.get(0).getCode_account(), result.block().getCode_account());
    }

    @Test
    @DisplayName("Save account")
    void testSave() {
        Account entity = listAccount.get(0);
        when(repository.save(any())).thenReturn(Mono.just(entity));

        AccountRequest request = new AccountRequest();
        request.setCode_account(entity.getCode_account());
        request.setHolder_account(entity.getHolder_account());
        request.setAuthorized_signer(entity.getAuthorized_signer());
        request.setProduct(entity.getProduct());
        request.setBalance(entity.getBalance());
        request.setTransactions(entity.getTransactions());

        Mono<AccountResponse> result = service.save(request);

        assertEquals(entity.getCode_account(), result.block().getCode_account());
    }

    @Test
    @DisplayName("Delete account")
    void testDelete() {
        Account entity = listAccount.get(0);
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(any())).thenReturn(Mono.just(entity));

        Mono<Boolean> result = service.delete(ID_ACCOUNT_01).thenReturn(Boolean.TRUE);

        assertTrue(result.block());
    }

    @Test
    @DisplayName("Return error while delete account")
    void testDeleteError() {
        Account entity = listAccount.get(0);
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(any())).thenReturn(null);

        Mono<Boolean> result = service.delete(ID_ACCOUNT_01).thenReturn(Boolean.TRUE);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, result::block);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    @DisplayName("Update account")
    void testUpdate() {
        UpdateAccountRequest updateRequest = new UpdateAccountRequest();
        updateRequest.setId_account(ID_ACCOUNT_01);
        updateRequest.setCode_account("193-1853946-0-77");
        updateRequest.setHolder_account(Collections.singletonList("640bf4a36bf23c1c772da9d6"));
        updateRequest.setAuthorized_signer(Collections.singletonList("640cab9692b061681e7d4d86"));
        updateRequest.setProduct("640c24cd3b905b25cfa2f25a");
        updateRequest.setBalance(350.0);
        updateRequest.setTransactions(new ArrayList<>());

        Account entity = listAccount.get(0);
        entity.setCode_account(updateRequest.getCode_account());
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(any())).thenReturn(Mono.just(entity));

        Mono<AccountResponse> result = service.update(updateRequest);

        assertEquals(entity.getCode_account(), result.block().getCode_account());
    }

    @Test
    @DisplayName("Return error while update account")
    void testUpdateError() {
        UpdateAccountRequest updateRequest = new UpdateAccountRequest();
        updateRequest.setId_account(ID_ACCOUNT_01);
        updateRequest.setCode_account("193-1853946-0-77");
        updateRequest.setHolder_account(Collections.singletonList("640bf4a36bf23c1c772da9d6"));
        updateRequest.setAuthorized_signer(Collections.singletonList("640cab9692b061681e7d4d86"));
        updateRequest.setProduct("640c24cd3b905b25cfa2f25a");
        updateRequest.setBalance(350.0);
        updateRequest.setTransactions(new ArrayList<>());

        Account entity = listAccount.get(0);
        entity.setCode_account(updateRequest.getCode_account());
        when(repository.findById(anyString())).thenReturn(Mono.just(entity));
        when(repository.save(any())).thenReturn(null);

        Mono<AccountResponse> result = service.update(updateRequest);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, result::block);

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }
}