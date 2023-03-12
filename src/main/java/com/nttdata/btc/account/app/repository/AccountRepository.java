package com.nttdata.btc.account.app.repository;

import com.nttdata.btc.account.app.model.entity.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Class repository AccountRepository.
 *
 * @author lrs
 */
@Repository
public interface AccountRepository extends ReactiveMongoRepository<Account, String> {
}