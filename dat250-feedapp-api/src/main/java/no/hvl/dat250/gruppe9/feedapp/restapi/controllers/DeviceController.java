package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.VoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Vote;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.DeviceService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.PollService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PollService pollService;
    @Autowired
    private VoteService voteService;

    @GetMapping(value = "/")
    public ResponseEntity<List<IoT>> getAllDevices() {
        var res = deviceService.getAll();
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/{deviceid}")
    public ResponseEntity<IoT> get(@PathVariable("deviceid") final String deviceid) {
        var res = deviceService.getDevice(deviceid);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value ="/{deviceid}")
    public ResponseEntity<IoT> updateDevice(
            @PathVariable("deviceid") final String deviceid,
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
    public ResponseEntity<IoT> deleteDevice(@PathVariable("deviceid") final String deviceid) {
        var found = deviceService.getDevice(deviceid);
        if(found.isPresent()) {
            var res = deviceService.delete(found.get());
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
            return new ResponseEntity<>(res.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value ="/{deviceId}/vote/{pollId}")
    public ResponseEntity<List<Vote>> voteOnPoll(
            @PathVariable("deviceId") String deviceId,
            @PathVariable("pollId") String pollId,
            @RequestBody List<VoteDTO> response) {
        var device = deviceService.getDevice(deviceId);
        var poll = pollService.getPoll(pollId);

        if (device.isPresent() && poll.isPresent()) {
            var res = voteService.deviceVote(device.get(), poll.get(), response);
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
