package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public class IoTDAO {

    private final EntityManager entityManager;

    @Autowired
    public IoTDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<IoT> get(Long id) {

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





}
