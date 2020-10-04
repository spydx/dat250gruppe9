package no.hvl.dat250.gruppe9.services;

import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
import no.hvl.dat250.gruppe9.entities.FeedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final FeedUserDAO feedUserDAO;

    @Autowired
    public UserService(FeedUserDAO feedUserDAO) {
        this.feedUserDAO = feedUserDAO;
    }

    public List<FeedUser> getAll()  {
        return feedUserDAO.getAll();
    }

    public void addUser(FeedUser user){
        feedUserDAO.addUser(user);
    }




}
