package no.hvl.dat250.gruppe9;

import no.hvl.dat250.gruppe9.DAO.FeedPollDAO;
import no.hvl.dat250.gruppe9.DAO.FeedUserDAO;
import no.hvl.dat250.gruppe9.DAO.FeedVotesDAO;
import no.hvl.dat250.gruppe9.entities.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PopulateDatabase {

    private static final FeedUserDAO udao = new FeedUserDAO();
    private static final FeedPollDAO pdao = new FeedPollDAO();
    private static final FeedVotesDAO vdao = new FeedVotesDAO();

    private static final List<FeedUser> userList = new ArrayList<>();
    private static final List<FeedPoll> pollList = new ArrayList<>();


    public static void main(String[] args) {

        final int LIMIT = 10;

        Random random = new Random();

        for (int i = 0; i < LIMIT; i++) {
            FeedUser u = new FeedUser();
            u.setFirstname("user " + i);
            u.setEmail("user" + i + "@mail.com");
            u.setRole(FeedRoles.USER);
            u.setPassword("supaSecre"+i);
            userList.add(u);
            udao.addUser(u);
        }

        for (int i = 0; i < LIMIT; i++) {
            FeedPoll f = new FeedPoll();
            f.setName("Super poll " + i);
            f.setQuestion("random thjingy "+ i);
            f.setAnswerno("don't agree");
            f.setAnsweryes("AgGREE!");
            f.setFeedaccess(FeedAccess.PUBLIC);
            Date d = new Date();
            f.setTimestart(d);
            f.setOwner(userList.get(random.nextInt(LIMIT)));
            pdao.addPoll(f);
            pollList.add(f);
        }

        FeedPoll voting = new FeedPoll();
        voting.setOwner(userList.get(random.nextInt(LIMIT)));
        voting.setAnsweryes("ACcepted");
        voting.setAnswerno("UnaceTAble");
        voting.setName("Is it...");
        voting.setQuestion("Accetable?");
        List<FeedVotes> votesList = new ArrayList<>();
        for (int i = 0; i < LIMIT; i++) {
            FeedVotes v = new FeedVotes();
            FeedUser u = userList.get(i);
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
            votesList.add(v);
        }
        voting.setVotes(votesList);
        pdao.addPoll(voting);
        pollList.add(voting);

    }
}
