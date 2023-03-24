package com.nttdata.btc.account.app.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class BaseRequest.
 *
 * @author lrs
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class BaseRequest {

    @NotNull(message = "Is mandatory")
    @NotEmpty(message = "Not be empty")
    @Schema(required = true, description = "Code account", example = "193-1853946-0-00")
    private String code_account;

    /**
     * List account holders.
     */
    @Schema(required = false, description = "Holder account", example = "{640bf4a36bf23c1c772da9d6}", type = "array")
    private List<String> holder_account; // titular id_cliente

    /**
     * List authorized signers.
     */
    @Schema(required = false, description = "authorized signer account", example = "{640ff25de14dbb2e4076ef56}", type = "array")
    private List<String> authorized_signer; //firmante id_cliente

    @Schema(required = true, description = "Id Product", example = "640ff1744975f4147b986d45")
    private String product;

    /**
     * saldo account
     */
    @Schema(description = "Balance account", example = "250.0")
    private Double balance = BigDecimal.ZERO.doubleValue();

    @Schema(required = false, description = "list transactions", example = "{640ff25de14dbb2e4076ef56}", type = "array")
    private List<String> transactions;
}