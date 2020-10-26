package no.hvl.dat250.gruppe9.feedapp.restapi.config.security;

public class FeedAPIResponse {
    private boolean success;
    private String msg;

    public FeedAPIResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
