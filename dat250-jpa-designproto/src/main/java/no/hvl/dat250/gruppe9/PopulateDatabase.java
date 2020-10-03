package no.hvl.dat250.gruppe9;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
import no.hvl.dat250.gruppe9.entities.*;

import java.util.List;

public class PopulateDatabase {
    static FeedUserDAO userDAO = new FeedUserDAO();
    static FeedPollDAO pollDAO = new FeedPollDAO();
    public static void main(String[] args) {
        List<FeedUser> userList = createUsers("firstName", "LastName", 10);
        CreatePolls(userList, "pollName", "question");
    }

    private static void CreatePolls(List<FeedUser> userList, String pollName, String question) {
        for (int i = 0; i < userList.size(); i++) {
            pollDAO.addPoll(pollName+i, question+i, FeedAccess.PUBLIC, "yes", "no",
                    userList.get(i), new FeedPollResult());
        }
    }

    private static List<FeedUser> createUsers(String firstName, String lastName, int n) {
        for (int i = 0; i < n; i++) {
            userDAO.addUser(firstName+i, lastName+i, FeedRoles.USER,
                    firstName+i+'@'+lastName+i, i+"");
        }
        List<FeedUser> userList = userDAO.getAll();
        return userList;
    }
}
