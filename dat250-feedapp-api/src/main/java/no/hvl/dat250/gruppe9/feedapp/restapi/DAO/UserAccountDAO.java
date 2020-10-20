package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.AccountData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class UserAccountDAO {

    @Autowired
    private final EntityManager entityManager;

    @Autowired
    public UserAccountDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<AccountData> get(Long id) {
        return Optional.ofNullable(entityManager.find(AccountData.class, id));
    }

    public Optional<AccountData> save(AccountData u) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(u);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(u);
        } catch (Exception e) {

            entityManager.getTransaction().rollback();
            return Optional.empty();
        }
    }

    public Optional<AccountData> update(AccountData item) {
        var current = Optional.ofNullable(entityManager.find(AccountData.class, item.getId()));
        entityManager.getTransaction().begin();
        if(current.isPresent()) {
            entityManager.merge(item);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(entityManager.find(AccountData.class, item.getId()));
        }
        entityManager.getTransaction().rollback();
        return current;
    }

    public Optional<AccountData> delete(AccountData accountData) {
        entityManager.getTransaction().begin();
        try {
            var delete = entityManager.find(AccountData.class, accountData.getId());
            entityManager.remove(delete);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(delete);
        } catch (Exception e ) {
            System.out.println("Unable to delete: " + accountData + " " + e.toString());
            entityManager.getTransaction().rollback();
            return Optional.ofNullable(accountData);
        }

    }

    public Optional<List<AccountData>> getAll() {
        var q = entityManager.createQuery("SELECT u from AccountData u");
        var res = q.getResultList();
        return Optional.ofNullable(res);
    }


}
