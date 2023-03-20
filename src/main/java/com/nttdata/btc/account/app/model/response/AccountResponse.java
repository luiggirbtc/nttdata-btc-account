package com.nttdata.btc.account.app.model.response;

import com.nttdata.btc.account.app.model.request.BaseRequest;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(required = true, description = "Id account", example = "640cc29c60650d1637e040a9")
    private String id_account;

    @Schema(description = "Register date", example = "2023-03-11T21:58:49.101+00:00")
    private Date register_date;

    @Schema(description = "Status account", example = "true")
    private Boolean status = false;
}