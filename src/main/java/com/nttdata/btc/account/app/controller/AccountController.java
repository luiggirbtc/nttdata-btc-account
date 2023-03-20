package com.nttdata.btc.account.app.controller;

import com.nttdata.btc.account.app.model.request.AccountRequest;
import com.nttdata.btc.account.app.model.request.UpdateAccountRequest;
import com.nttdata.btc.account.app.model.response.AccountResponse;
import com.nttdata.btc.account.app.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Class AccountController.
 *
 * @author lrs
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/account")
@Tag(name = "Account", description = "Service Account")
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
    @Operation(summary = "Find accounts by holder")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping(value = "holder/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @Operation(summary = "Get account by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content)})
    @GetMapping(value = "id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @Operation(summary = "Create a new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AccountResponse>> createAccount(@Valid @RequestBody final AccountRequest request) {
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
    @Operation(summary = "Update account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AccountResponse>> updateProduct(@Valid @RequestBody final UpdateAccountRequest request) {
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
    @Operation(summary = "Get all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @Operation(summary = "Delete account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Void.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @DeleteMapping(value = "/{id}")
    public Mono<ResponseEntity<Void>> deleteAccount(@PathVariable(value = "id") final String id) {
        return service.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}