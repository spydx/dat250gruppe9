package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Account;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.IoTDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.logging.ErrorManager;


@Repository
@Transactional
public class IoTDAO {

    @Autowired
    private EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(IoTDTO.class);

    public Optional<IoT> get(String id) {
        return Optional.ofNullable(entityManager.find(IoT.class, id));
    }

    public Optional<IoT> save(IoT device) {
        entityManager.persist(device);
        return Optional.ofNullable(device);
    }

    public Optional<IoT> update(IoT device) {
        var current = Optional.ofNullable(entityManager.find(IoT.class, device.getId()));
        if(current.isPresent()) {
            entityManager.merge(device);
            return Optional.ofNullable(entityManager.find(IoT.class, device.getId()));
        }
        return current;
    }

    public Optional<IoT> delete(IoT device) {
        var todelete = entityManager.find(IoT.class, device.getId());
        try {
            entityManager.remove(todelete);
            return Optional.ofNullable(todelete);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<IoT>> getAll() {
        var q = Optional.ofNullable(entityManager.createQuery("SELECT device from IoT device"));
        if(q.isPresent()) {
            var res = q.get().getResultList();
            return Optional.ofNullable(res);
        }
        return Optional.empty();
    }


    public Optional<IoT> getByName(String username) {
        try {
            var query = entityManager
                    .createQuery("SELECT a FROM IoT a WHERE a.name = :id", IoT.class)
                    .setParameter("id", username);
            var res = Optional.ofNullable(query.getSingleResult());
            if (res.isPresent())
                return res;
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Failed to fetch user {}", e.toString() );
            return Optional.empty();
        }
    }
}
