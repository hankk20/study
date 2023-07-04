package kr.co.hankk20.study.domain.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.querydsl.core.annotations.QueryProjection;
import kr.co.hankk20.study.domain.account.code.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AccountDto implements Serializable {
    private Long id;
    private String accountId;
    private String nickname;
    private AccountType accountType;
    private String accountString;
    private boolean quit = false;

    @QueryProjection
    public AccountDto(Long id, String accountId, String nickname, AccountType accountType, String accountString, boolean quit) {
        this.id = id;
        this.accountId = accountId;
        this.nickname = nickname;
        this.accountType = accountType;
        this.accountString = accountString;
        this.quit = quit;
    }

    @JsonProperty
    private String accountTypeName(){
        return accountType.getDisplayName();
    }
}
