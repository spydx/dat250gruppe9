package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.IoTDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.RoleDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.reponse.InternalServerError;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.DeviceDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.DeviceVoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.IoTDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.RoleEnum;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private IoTDAO deviceStorage;

    @Autowired
    private VoteService voteService;

    @Autowired
    private RoleDAO roleStorage;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<List<IoT>> getAll() {
        return deviceStorage.getAll();
    }

    public Optional<IoT> getDevice(String device) {
        return deviceStorage.get(device);
    }
    public Optional<IoT> getByName(String name) {
        return deviceStorage.getByName(name);
    }

    public Optional<IoT> add(DeviceDTO newdev) {
        var device = new IoT();
        var role = roleStorage.findByName(RoleEnum.ROLE_USER).orElseThrow(
                () -> new InternalServerError("User Role not set")
        );
        device.setRoles(Collections.singleton(role));
        device.setName(newdev.getName());
        device.setPassword(passwordEncoder.encode(newdev.getPassword()));
        return deviceStorage.save(device);
    }

    public Optional<IoT> update(IoT device) {
        return deviceStorage.update(device);
    }

    public Optional<IoT> delete(IoT device) {
        return deviceStorage.delete(device);
    }

    public Optional<String> connectedToPoll(String deviceid) {
        var iot = deviceStorage.get(deviceid);
        if(iot.isPresent())
            return Optional.ofNullable(iot.get().getConnectedPoll().getId());
        return Optional.empty();
    }

    public Optional<List<Vote>> vote(IoT iot, DeviceVoteDTO response) {
        return voteService.deviceVote(iot, response);

    }
}

