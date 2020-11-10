package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Access;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.AccountDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.DeviceDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.PollDTO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.IoT;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.Poll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SetupService {

    @Value("${app.username}")
    private String username;

    @Value("${app.password}")
    private String password;

    @Value("${app.anonymous.account}")
    private String anonymousUser;

    @Value("${app.anonymous.password}")
    private String anonymousPassword;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountDAO accountStorage;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private PollService pollService;

    private final Logger logger = LoggerFactory.getLogger(SetupService.class);

    public void init() {

        var res = accountStorage.initRoles();
        logger.info("Created ROLES: {}", res);

        var exist = userService.getAccount(username);
        if(exist.isEmpty()) {
            var newadmin = new AccountDTO(
                    "PollHub",
                    "Admin",
                    username,
                    password
            );
            var create = userService.add(newadmin);
            if(create.isPresent())
                logger.info("Create admin account {}", create.get());
            else
                logger.error("Failed to create admin account");

            var admin = userService.getAccount(username);
            if (admin.isPresent()) {
                userService.promoteToAdmin(admin.get());
                logger.info("Promoted {} to ADMIN ", admin.get().getEmail());
            }
        } else {
            logger.info("Account with email: {} already exist", exist.get().getEmail());
        }
        var anonaccount = userService.getAccount(anonymousUser);
        if(anonaccount.isEmpty()) {
            var newAccount = new AccountDTO(
                    "Anonymous",
                    "Pollhub",
                    anonymousUser,
                    anonymousPassword
            );
            var createres = userService.add(newAccount);
            if(createres.isPresent())
                logger.info("Create anonymous account {}", createres.get());
            else
                logger.error("Cannot create anonymouse account");

        }

        var anonowner = userService.getAccount(anonymousUser);
        Optional<Poll> poll = pollService.getPollByName("Biden vs Trump");
        if(anonowner.isPresent() && poll.isEmpty()) {
            PollDTO p = new PollDTO();
            p.setName("Biden vs Trump");
            p.setQuestion("Who do you wanna vote for?");
            p.setAnsweryes("Biden");
            p.setAnswerno("Trump");
            p.setAccess(Access.PUBLIC);
            p.setTimeend(new Date());
            poll = pollService.addPoll(p,anonowner.get().getId());

        }

        var device = deviceService.getByName("iot-test-device");
        if(device.isEmpty()) {
            var d = new DeviceDTO();
            d.setName("iot-test-device");
            d.setPassword("1234");
            deviceService.add(d);
            if(poll.isPresent()) {
                var dev = deviceService.getByName("iot-test-device");
                if(dev.isPresent()) {
                    dev.get().setConnectedPoll(poll.get());
                    deviceService.update(dev.get());
                }
            }
        }

    }
}
