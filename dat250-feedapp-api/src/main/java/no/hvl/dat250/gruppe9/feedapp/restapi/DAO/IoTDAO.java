package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@Repository
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
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(device);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(device);
        } catch (Exception e) {
            //TODO: Error LOgger
            entityManager.getTransaction().rollback();
            return Optional.ofNullable(device);
        }
    }

    public Optional<IoT> update(IoT device) {
        var current = Optional.ofNullable(entityManager.find(IoT.class, device.getId()));
        if(current.isPresent()) {
            entityManager.getTransaction().begin();
            entityManager.merge(device);
            entityManager.getTransaction().commit();
            return Optional.ofNullable(entityManager.find(IoT.class, device.getId()));
        }
        return current;
    }

    public Optional<IoT> delete(IoT device) {
        var todelete = entityManager.find(IoT.class, device.getId());
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(todelete);
            entityManager.getTransaction().commit();
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
