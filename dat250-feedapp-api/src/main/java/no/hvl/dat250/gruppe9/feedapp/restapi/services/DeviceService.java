package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.IoTDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.DeviceVoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.IoTDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private IoTDAO deviceStorage;

    @Autowired
    private VoteService voteService;

    public Optional<List<IoT>> getAll() {
        return deviceStorage.getAll();
    }

    public Optional<IoT> getDevice(String device) {
        return deviceStorage.get(device);
    }

    public Optional<IoT> add(IoTDTO newdev) {
        var device = new IoT();
        device.setName(newdev.getName());
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
        var pollid = iot.getConnectedPoll().getId();
        var deviceid = iot.getId();
        return voteService.deviceVote(iot ,response);

    }
}

