package com.nttdata.btc.account.app.model.request;

import lombok.*;

import java.util.List;

/**
 * Class BaseRequest.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BaseRequest {

    private String code_account;

    /**
     * List account holders.
     */
    private List<String> holder_account; // titular id_cliente

    /**
     * List authorized signers.
     */
    private List<String> authorized_signer; //firmante id_cliente

    private String product; // id_producto

    /**
     * saldo account
     */
    private Double balance;

    private List<String> transactions;
}