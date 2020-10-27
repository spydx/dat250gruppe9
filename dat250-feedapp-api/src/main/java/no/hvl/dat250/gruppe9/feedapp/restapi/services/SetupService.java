package no.hvl.dat250.gruppe9.feedapp.restapi.services;

import no.hvl.dat250.gruppe9.feedapp.restapi.DAO.AccountDAO;
import no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SetupService {

    @Value("${app.username}")
    private String username;

    @Value("${app.password}")
    private String password;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountDAO accountStorage;

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
            logger.info("Create admin account {}", create.get());

            var admin = userService.getAccount(username);
            if (admin.isPresent()) {
                userService.promoteToAdmin(admin.get());
                logger.info("Promoted {} to ADMIN ", admin.get().getEmail());
            }
        } else {
            logger.info("Account with email: {} already exist", exist.get().getEmail());
        }
    }
}
