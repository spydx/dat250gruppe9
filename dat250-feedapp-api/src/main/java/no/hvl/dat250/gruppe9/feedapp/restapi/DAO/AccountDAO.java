package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.RoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.util.AbstractAnnotationValueVisitor6;
import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.Optional;

@Repository
@Transactional
public class AccountDAO {

    @Autowired
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(AccountDAO.class);

    public Optional<Account> get(String id) {
        var res = entityManager.find(Account.class, id);
        return Optional.ofNullable(res);
    }

    public Optional<Account> getByEmail(String email) {
        try {
            var query = entityManager
                    .createQuery("SELECT a FROM Account a WHERE a.email = :emailid", Account.class)
                    .setParameter("emailid", email);
            var res = Optional.ofNullable(query.getSingleResult());
            if (res.isPresent())
                return res;
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Failed to fetch user {}", e.toString() );
            return Optional.empty();
        }
    }

    public Optional<Account> save(Account account) {
        entityManager.persist(account);
        return Optional.ofNullable(account);
    }

    public Optional<Account> update(Account account) {
        try {
            entityManager.merge(account);
            var res = entityManager.find(Account.class, account.getId());
            return Optional.ofNullable(res);
        } catch (Exception e) {
            logger.error("Unable to update user");
            return Optional.empty();
        }
    }

    public Optional<Account> delete(Account account) {
        var found = Optional.ofNullable(entityManager.find(Account.class, account.getId()));
        if (found.isPresent()) {
            entityManager.remove(found.get());
            return Optional.empty();
        }
        logger.error("Unable to delete user");
        return Optional.ofNullable(account);
    }

    public Boolean initRoles() {
        var res = entityManager.createNativeQuery("SELECT COUNT(*) FROM Roles");
        BigInteger count = (BigInteger)res.getSingleResult();
        if (count==BigInteger.ZERO) {
            entityManager
                    .createNativeQuery("INSERT INTO Roles(name) VALUES('ROLE_USER')")
                    .executeUpdate();
            entityManager
                    .createNativeQuery("INSERT INTO Roles(name) VALUES('ROLE_ADMIN')")
                    .executeUpdate();
            return true;
        }
        logger.info("Roles already CREATED Count is {}", count);
        return false;
    }
}
