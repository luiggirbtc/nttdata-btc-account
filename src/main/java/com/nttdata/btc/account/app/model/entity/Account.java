package com.nttdata.btc.account.app.model.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity Account.
 *
 * @author lrs
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "account")
public class Account {
    @Id
    private String id_account;

    private String code_account;

    /*
     * List account holders.
     */
    private List<String> holder_account; // titular id_cliente

    /*
     * List authorized signers.
     */
    private List<String> authorized_signer; //firmante id_cliente

    private String product; // id_producto

    /*
     * saldo account
     */
    private Double balance;

    private List<String> transactions; //id_transaction

    private Date register_date = new Date();

    private boolean status = true;

    /**
     * Constructor create a new account.
     *
     * @param code_account      {@link String}
     * @param holder_account    {@link List<String>}
     * @param authorized_signer {@link List<String>}
     * @param product           {@link String}
     * @param balance           {@link Double}
     * @param transactions      {@link List<String>}
     */
    public Account(String code_account, List<String> holder_account,
                   List<String> authorized_signer, String product,
                   Double balance, List<String> transactions) {
        this.code_account = code_account;
        this.holder_account = holder_account;
        this.authorized_signer = authorized_signer;
        this.product = product;
        this.balance = balance;
        this.transactions = transactions;
    }
}