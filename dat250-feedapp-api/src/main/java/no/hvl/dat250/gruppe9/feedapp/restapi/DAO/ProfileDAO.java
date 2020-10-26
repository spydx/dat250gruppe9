package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ProfileDAO {

    @Autowired
    private EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(ProfileDAO.class);

    public Optional<Profile> get(String id) {
        return Optional.ofNullable(entityManager.find(Profile.class, id));
    }

    public Optional<Profile> save(Profile profile) {
        entityManager.merge(profile);
        return Optional.ofNullable(profile);
    }

    public Optional<Profile> update(Profile item) {
        var current = Optional.ofNullable(entityManager.find(Profile.class, item.getId()));
        if(current.isPresent()) {
            entityManager.merge(item);
            return Optional.ofNullable(entityManager.find(Profile.class, item.getId()));
        }
        logger.error("Update error");
        return current;
    }

    public Optional<Profile> delete(Profile profile) {
        try {
            var delete = entityManager.find(Profile.class, profile.getId());
            entityManager.remove(delete);
            return Optional.ofNullable(delete);
        } catch (Exception e ) {
            logger.error("Unable to delete: {}",profile);
            return Optional.ofNullable(profile);
        }
    }

    public Optional<List<Profile>> getAll() {
        var q = entityManager.createQuery("SELECT u from Profile u");
        var res = q.getResultList();
        return Optional.ofNullable(res);
    }


}
