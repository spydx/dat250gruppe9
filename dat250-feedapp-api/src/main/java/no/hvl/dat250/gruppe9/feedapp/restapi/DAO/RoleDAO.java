package no.hvl.dat250.gruppe9.feedapp.restapi.DAO;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.RoleEnum;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDAO extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(RoleEnum roleName);

    @Override
    <S extends Roles> List<S> saveAll(Iterable<S> iterable);
}