package no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO;

public class DeviceVoteDTO {
    private int yes;
    private int no;

    public int getYes() {
        return yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
