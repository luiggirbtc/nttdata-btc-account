package com.nttdata.btc.account.app.controller;

import com.nttdata.btc.account.app.model.request.AccountRequest;
import com.nttdata.btc.account.app.model.request.UpdateAccountRequest;
import com.nttdata.btc.account.app.model.response.AccountResponse;
import com.nttdata.btc.account.app.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @InjectMocks
    AccountController controller;

    @Mock
    AccountService service;

    List<AccountResponse> listAccount = new ArrayList<>();

    static final String CODE_HOLDER = "640bf4a36bf23c1c772da9d6";
    static final String ID_ACCOUNT_01 = "640cc29c60650d1637e040a9";

    @BeforeEach
    private void setUp() {
        AccountResponse beanResponse = new AccountResponse();
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
    @DisplayName("Return all accounts by holder")
    void testFindAllByHolder() {
        when(service.findAllByHolder(anyString())).thenReturn(Flux.just(listAccount.get(0)));

        Flux<AccountResponse> result = controller.findAllByHolder(CODE_HOLDER);

        assertEquals(listAccount.get(0).getCode_account(), result.blockFirst().getCode_account());
    }

    @Test
    @DisplayName("Return account by id")
    void testFindById() {
        when(service.findById(anyString())).thenReturn(Mono.just(listAccount.get(0)));

        Mono<AccountResponse> result = controller.findById(ID_ACCOUNT_01);

        assertEquals(listAccount.get(0).getCode_account(), result.block().getCode_account());
    }

    @Test
    @DisplayName("Create a new account")
    void testCreateAccount() {
        AccountResponse response = listAccount.get(0);

        AccountRequest request = new AccountRequest();
        request.setCode_account(response.getCode_account());
        request.setHolder_account(response.getHolder_account());
        request.setAuthorized_signer(response.getAuthorized_signer());
        request.setProduct(response.getProduct());
        request.setBalance(response.getBalance());
        request.setTransactions(response.getTransactions());

        when(service.save(request)).thenReturn(Mono.just(response));

        Mono<AccountResponse> result = controller.createAccount(request);

        assertEquals(response.getCode_account(), result.block().getCode_account());
    }

    @Test
    @DisplayName("Update a account")
    void testUpdateAccount() {
        UpdateAccountRequest updateRequest = new UpdateAccountRequest();
        updateRequest.setId_account(ID_ACCOUNT_01);
        updateRequest.setCode_account("193-1853946-0-77");
        updateRequest.setHolder_account(Collections.singletonList("640bf4a36bf23c1c772da9d6"));
        updateRequest.setAuthorized_signer(Collections.singletonList("640cab9692b061681e7d4d86"));
        updateRequest.setProduct("640c24cd3b905b25cfa2f25a");
        updateRequest.setBalance(350.0);
        updateRequest.setTransactions(new ArrayList<>());

        AccountResponse responseUpdated = listAccount.get(0);
        responseUpdated.setCode_account(updateRequest.getCode_account());

        when(service.update(updateRequest)).thenReturn(Mono.just(responseUpdated));

        Mono<AccountResponse> result = controller.updateAccount(updateRequest);

        assertEquals(responseUpdated.getCode_account(), result.block().getCode_account());
    }

    @Test
    @DisplayName("Return all accounts")
    void testFindAllAccounts() {
        when(service.findAll()).thenReturn(Flux.fromIterable(listAccount));

        Flux<AccountResponse> result = controller.findAll();

        assertEquals(result.blockFirst().getId_account(), listAccount.get(0).getId_account());
    }

    @Test
    @DisplayName("Delete account")
    void testDeleteAccount() {
        when(service.delete(anyString())).thenReturn(Mono.empty());

        Mono<Boolean> result = controller.deleteAccount(ID_ACCOUNT_01).thenReturn(Boolean.TRUE);

        assertTrue(result.block());
    }
}