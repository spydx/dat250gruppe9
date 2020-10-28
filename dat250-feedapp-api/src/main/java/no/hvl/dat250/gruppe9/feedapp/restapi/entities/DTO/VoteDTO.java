package no.hvl.dat250.gruppe9.feedapp.restapi.entities.DTO;

public class VoteDTO {
    private Boolean answer;

    VoteDTO() {

    }
    
    public VoteDTO(Boolean answer) {
        this.answer = answer;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }
}
