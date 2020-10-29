package no.hvl.dat250.gruppe9.feedapp.restapi.controllers;

import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtAutheticationResponse;
import no.hvl.dat250.gruppe9.feedapp.restapi.config.security.JwtTokenProvider;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.DeviceDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.DeviceVoteDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.IoTDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.LoginDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.DeviceService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.PollService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.UserService;
import no.hvl.dat250.gruppe9.feedapp.restapi.services.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    //TODO: Remove Prototyåing only
    @GetMapping(value = "/")
    public ResponseEntity<List<IoT>> getAllDevices(
            @RequestHeader("Authorization") final String token) {
        var accountid = tokenProvider.parseHeader(token);
        if(accountid.isPresent()) {
            var res = deviceService.getAll();
            if(res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/{deviceid}")
    public ResponseEntity<IoT> get(@PathVariable("deviceid") final String deviceid) {
        var res = deviceService.getDevice(deviceid);
        if(res.isPresent())
            return new ResponseEntity<>(res.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /* Not Needed
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
    }*/

    @PostMapping(value = "/connect")
    public ResponseEntity<IoT> createDevice(@RequestBody DeviceDTO newdevice) {
        var exists = deviceService.getDevice(newdevice.getName());
        if(exists.isEmpty()) {
            var res = deviceService.add(newdevice);
            if (res.isPresent())
                return new ResponseEntity<>(res.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }
    @PostMapping(value ="/authenticate")
    public ResponseEntity<?> authenticate(@NotNull @Valid @RequestBody DeviceDTO login) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getName(),
                        login.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = tokenProvider.generateToken(auth);
        var device = deviceService.getByName(login.getName());
        if(device.isPresent())
            return ResponseEntity.ok(new JwtAutheticationResponse(token,device.get().getId()));

        logger.error("Loggin error failed for {}", login.getName());
        return ResponseEntity.ok("failed to find device");
    }

    @DeleteMapping(value ="/{deviceid}")
    public ResponseEntity<IoT> deleteDevice(
            @RequestHeader("Authorization") final String token,
            @PathVariable("deviceid") final String deviceid) {

        var access = tokenProvider.validateToken(token);
        var accountid = tokenProvider.parseHeader(token);
        if(accountid.isPresent() && access) {
            if(userService.validateAdmin(accountid.get())) {
                var found = deviceService.getDevice(deviceid);
                if(found.isPresent()) {
                    var res = deviceService.delete(found.get());
                    if(res.isPresent())
                        return new ResponseEntity<>(res.get(), HttpStatus.OK);
                    return new ResponseEntity<>(res.get(), HttpStatus.NO_CONTENT);
                }
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @PostMapping(value ="/{deviceId}/vote/")
    public ResponseEntity<Boolean> voteOnPoll(
            @RequestHeader("Authorization") final String token,
            @PathVariable("deviceId") String deviceId,
            @RequestBody DeviceVoteDTO response) {

        var device = deviceService.getDevice(deviceId);
        var isConnected = deviceService.connectedToPoll(deviceId);

        if (device.isPresent() && isConnected.isPresent()) {
            var res = deviceService.vote(device.get(), response);
            if(res.isPresent())
                return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
    }

}
