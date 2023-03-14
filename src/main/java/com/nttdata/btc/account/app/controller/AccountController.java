package com.nttdata.btc.account.app.controller;

import com.nttdata.btc.account.app.model.request.AccountRequest;
import com.nttdata.btc.account.app.model.request.UpdateAccountRequest;
import com.nttdata.btc.account.app.model.response.AccountResponse;
import com.nttdata.btc.account.app.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class AccountController.
 *
 * @author lrs
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
    /**
     * Inject dependency AccountService.
     */
    @Autowired
    private AccountService service;

    /**
     * Service list all accounts by holder.
     *
     * @param id {@link String}
     * @return {@link AccountResponse}
     */
    @GetMapping("holder/{id}")
    public Flux<AccountResponse> findAllByHolder(@PathVariable final String id) {
        log.info("Start findAllByHolder Accounts.");
        return service.findAllByHolder(id).doOnNext(product -> log.info(product.toString()));
    }

    /**
     * Service find by id.
     *
     * @param id {@link String}
     * @return {@link AccountResponse}
     */
    @GetMapping("id/{id}")
    public Mono<ResponseEntity<AccountResponse>> findById(@PathVariable final String id) {
        return service.findById(id)
                .map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Service create account.
     *
     * @param request {@link AccountRequest}
     * @return {@link AccountResponse}
     */
    @PostMapping("/")
    public Mono<ResponseEntity<AccountResponse>> createProduct(@RequestBody final AccountRequest request) {
        log.info("Start CreateAccount.");
        return service.save(request)
                .map(p -> new ResponseEntity<>(p, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    /**
     * Service update account.
     *
     * @param request {@link UpdateAccountRequest}
     * @return {@link AccountResponse}
     */
    @PutMapping("/")
    public Mono<ResponseEntity<AccountResponse>> updateProduct(@RequestBody final UpdateAccountRequest request) {
        log.info("Start UpdateAccount.");
        return service.update(request)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    /**
     * Service list all accounts.
     *
     * @return {@link AccountResponse}
     */
    @GetMapping("/")
    public Flux<AccountResponse> findAll() {
        log.info("Start findAll Accounts.");
        return service.findAll()
                .doOnNext(product -> log.info(product.toString()));
    }

    /**
     * Service delete account.
     *
     * @param id {@link String}
     * @return {@link Void}
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable(value = "id") final String id) {
        return service.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}