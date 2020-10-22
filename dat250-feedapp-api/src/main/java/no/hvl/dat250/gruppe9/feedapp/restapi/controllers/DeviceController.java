package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<IoT>> getAllDevices() {
        var res = deviceService.getAll();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/{deviceid}")
    public ResponseEntity<IoT> get(@PathVariable("deviceid") final Long deviceid) {
        var res = deviceService.getDevice(deviceid);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value ="/{deviceid}")
    public ResponseEntity<IoT> updateDevice(
            @PathVariable("deviceid") final Long deviceid,
            @RequestBody IoT device
    ) {
        var founddevice = deviceService.getDevice(deviceid);
        if (founddevice.isPresent()) {
            var res = deviceService.update(device);
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/")
    public ResponseEntity<IoT> createDevice(@RequestBody IoT newdevice) {
        var res = deviceService.add(newdevice);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value ="/{deviceid}")
    public ResponseEntity<IoT> deleteDevice(@PathVariable("deviceid") final Long deviceid) {
        var found = deviceService.getDevice(deviceid);
        if(found.isPresent()) {
            var res = deviceService.delete(found.get());
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(res.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

}
