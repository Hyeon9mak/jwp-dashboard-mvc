package com.techcourse.repository;

import com.techcourse.domain.User;

import com.techcourse.exception.DuplicateAccountException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import nextstep.web.annotation.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class InMemoryUserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<String, User> database;
    private final AtomicLong autoIncrementId;

    public InMemoryUserRepository() {
        this(new HashMap<>(), new AtomicLong(1));
    }

    public InMemoryUserRepository(Map<String, User> database, AtomicLong autoIncrementId) {
        this.database = database;
        this.autoIncrementId = autoIncrementId;
    }

    public void save(User user) {
        if (database.containsKey(user.getAccount())) {
            LOGGER.debug("Duplicate account already exist => {}", user.getAccount());
            throw new DuplicateAccountException();
        }

        User newUser = User.withId(autoIncrementId.getAndIncrement(), user);
        database.put(newUser.getAccount(), newUser);
    }

    public Optional<User> findByAccount(String account) {
        return Optional.ofNullable(database.get(account));
    }
}
