package com.nttdata.btc.account.app.model.response;

import com.nttdata.btc.account.app.model.request.BaseRequest;

import java.util.Date;

import lombok.*;

/**
 * Class response ProductResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AccountResponse extends BaseRequest {
    private String id_account;
    private Date register_date;
    private Boolean status = false;
}