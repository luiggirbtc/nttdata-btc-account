package com.nttdata.btc.account.app.model.response;

import com.nttdata.btc.account.app.model.request.BaseRequest;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class response ProductResponse.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountResponse extends BaseRequest {
    private String id_account;
    private Date register_date;
    private Boolean status = false;
}