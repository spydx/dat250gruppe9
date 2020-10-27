package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.IoTDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    private final IoTDAO deviceStorage;

    @Autowired
    public DeviceService(IoTDAO deviceStorage) {
        this.deviceStorage = deviceStorage;
    }

    public Optional<List<IoT>> getAll() {
        return deviceStorage.getAll();
    }

    public Optional<IoT> getDevice(String device) {
        return deviceStorage.get(device);
    }

    public Optional<IoT> add(IoT device) {
        return deviceStorage.save(device);
    }

    public Optional<IoT> update(IoT device) {
        return deviceStorage.update(device);
    }

    public Optional<IoT> delete(IoT device) {
        return deviceStorage.delete(device);
    }
}

