package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.util.AbstractAnnotationValueVisitor6;
import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Transactional
public class AccountDAO {

    private final EntityManager entityManager;

    @Autowired
    public AccountDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Account> get(Long id) {
        var res = entityManager.find(Account.class, id);
        return Optional.ofNullable(res);
    }

    //TODO: This does not work :P
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
            //TODO: Logger
            return Optional.empty();
        }
    }

    public Optional<Account> delete(Account account) {
        var found = Optional.ofNullable(entityManager.find(Account.class, account.getId()));
        if (found.isPresent()) {
            entityManager.remove(found.get());
            return Optional.empty();
        }
        //TODO: Logger
        return Optional.ofNullable(account);

    }

}
