package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl service;
    private StorageDao storageDao;
    private User userExpected;
    private User duplicate;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl();
        userExpected = new User();

        userExpected.setLogin("vlad1996@gmail.com");
        userExpected.setPassword("gggttt");
        userExpected.setAge(25);

        duplicate = new User();

        duplicate.setLogin("vlad19@gmail.com");
        duplicate.setPassword("gggttt");
        duplicate.setAge(25);

        Storage.people.clear();
    }

    @Test
    void register_isInList_notOk() {
        storageDao.add(userExpected);

        duplicate.setLogin("vlad1996@gmail.com");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        userExpected.setLogin(null);
        storageDao.add(userExpected);

        duplicate.setLogin(null);

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_loginLessThenSix_notOk() {
        userExpected.setLogin("vlad@gmail.com");
        storageDao.add(userExpected);

        duplicate.setLogin("vlad@gmail.com");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        userExpected.setPassword(null);
        storageDao.add(userExpected);

        duplicate.setPassword(null);

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_passwordLessThenSix_notOk() {
        userExpected.setPassword("gggtt");
        storageDao.add(userExpected);

        duplicate.setPassword("gggtt");

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_nullAge_notOk() {
        userExpected.setAge(null);
        storageDao.add(userExpected);

        duplicate.setAge(null);

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }

    @Test
    void register_ageLessThenEighteen_notOk() {
        userExpected.setAge(17);
        storageDao.add(userExpected);

        duplicate.setAge(17);

        assertThrows(RegistrationException.class, () -> {
            service.register(duplicate);
        });
    }
}
