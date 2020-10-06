package no.hvl.dat250.gruppe9;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedPollResultDAO;
import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
import no.hvl.dat250.gruppe9.DAO.FeedVotesDAO;
import no.hvl.dat250.gruppe9.entities.*;
import no.hvl.dat250.gruppe9.services.PollService;
import no.hvl.dat250.gruppe9.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PopulateDBService {


    public static final FeedUserDAO udao = new FeedUserDAO();
    public static final FeedPollDAO pdao = new FeedPollDAO();
    public static final FeedVotesDAO vdao = new FeedVotesDAO();
    public static final FeedPollResultDAO rdao = new FeedPollResultDAO();
    public static final UserService userService = new UserService(udao);
    public static final PollService pollService = new PollService(pdao,rdao,vdao);

    private static List<FeedUser> userList = new ArrayList<>();
    private static List<FeedPoll> pollList = new ArrayList<>();
    final static int LIMIT = 10;

    public static void main(String[] args) {

        Random random = new Random();

        for (int i = 1; i <= LIMIT; i++) {

            var u = new FeedUser();
            u.setFirstname("user " + i);
            u.setLastname("i have a " + i + "lastname");
            u.setEmail("user" + i + "@mail.com");
            u.setRole(FeedRoles.USER);
            u.setPassword("supaSecre"+i);

            if(!userExist(u)) {
                userList.add(u);
                userService.addUser(u);
                System.out.println("[ Added ] " + u);
            } else {
                System.out.println("[ Already Exist ]" + u);
            }
        }

        if(userList.isEmpty())
            userList = userService.getAll();

        for (int i = 1; i <= LIMIT; i++) {
            FeedPoll f = new FeedPoll();
            f.setName("Super poll " + i);
            f.setQuestion("random thjingy "+ i);
            f.setAnswerno("don't agree");
            f.setAnsweryes("AgGREE!");
            f.setFeedaccess(FeedAccess.PUBLIC);
            Date d = new Date();
            f.setTimestart(d);
            f.setOwner(userList.get(random.nextInt(LIMIT)));
            pollList.add(f);
            if(!pollExist(f)) {
                pollService.addPoll(f);
                System.out.println("[ Added ] " + f);
            } else {
                System.out.println("[ Already Exist ]" + f);
            }
        }

        if(pollList.isEmpty()) {
            pollList = pollService.getAll();
        }

        FeedPoll poll = new FeedPoll();
        var owner = userList.get(random.nextInt(LIMIT));

        poll.setAnsweryes("ACcepted");
        poll.setAnswerno("UnaceTAble");
        poll.setName("Is it...");
        poll.setQuestion("Accetable?");
        var date = new Date();
        poll.setTimestart(date);
        poll.setFeedaccess(FeedAccess.PUBLIC);
        poll.setOwner(owner);


        var votingpoll = pollService.addPoll(poll);
        var list = owner.getPollsList();
        list.add(votingpoll);
        owner.setPollsList(list);

        userService.updateUser(owner);

        System.out.println(votingpoll);
        List<FeedVotes> votesList = new ArrayList<>();
        if(votingpoll != null) {
            for (int i = 0; i < LIMIT; i++) {
                FeedVotes v = new FeedVotes();
                FeedUser u = userList.get(i);
                if (u.getId() == poll.getOwner()) continue;
                Date d = new Date();
                v.setVotetime(d);
                if(i % 2 == 0)
                    v.setAnswer(Boolean.TRUE);
                else
                    v.setAnswer(Boolean.FALSE);

                v.setVoterid(u.getId());
                List<FeedVotes> vl = new ArrayList<>();
                vl.add(v);
                u.setVotedOn(vl);
                System.out.println("[ Adding ]" + v);
                userService.updateUser(u);
                pollService.addVote(v,poll.getId());
                System.out.println("[ User OK ]");
                votesList.add(v);
            }
            System.out.println("Done voting");
        } else {
            System.out.println("Voting null");
        }

        if(votingpoll != null) {
            var pollres = pollService.getResult(votingpoll.getId());
            if (pollres == null) {
                var res = pollService.generateResult(poll);
                System.out.println("[ Addded ] " + res);
            } else {
                System.out.println("[ Already exist] " + pollres);
            }
        }


    }

    public static boolean pollExist(FeedPoll p) {
        var list = pollService.getAll();
        if(list.contains(p))
            return true;
        return false;
    }
    public static boolean userExist(FeedUser user) {
        var list = userService.getAll();
        if(list.contains(user)) {
            return true;
        }
        return false;
    }
}

