package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Access;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PollDAO {

    private final EntityManager entityManager;

    @Autowired
    public PollDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Poll> get(Long id) {
        return Optional.ofNullable(entityManager.find(Poll.class, id));
    }

    public Optional<Poll> save(Poll item) {
        entityManager.persist(item);
        return Optional.ofNullable(item);
    }

    public Optional<Poll> update(Poll item) {
        var current = Optional.ofNullable(entityManager.find(Poll.class, item.getId()));
        if(current.isPresent()) {
            entityManager.merge(item);
            return Optional.ofNullable(entityManager.find(Poll.class, item.getId()));
        }
        return current;
    }

    public Optional<Poll> delete(Poll item) {
        var todelete = entityManager.find(Poll.class, item.getId());
        entityManager.remove(todelete);
        return Optional.ofNullable(todelete);
    }

    public Optional<List<Poll>> getAll() {
        var q = Optional.ofNullable(entityManager.createQuery("SELECT p FROM Poll p"));
        if(q.isPresent()) {
            var res = q.get().getResultList();
            return Optional.ofNullable(res);
        }
        return Optional.empty();
    }

    public Optional<List<Poll>> getAllPublic() {
        var q = entityManager
                .createQuery("select p from Poll p where p.access = :domain", Poll.class)
                .setParameter("domain", Access.PUBLIC);
        var list = q.getResultList();
        return Optional.ofNullable(list);

    }
}
