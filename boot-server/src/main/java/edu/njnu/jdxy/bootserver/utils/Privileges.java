package edu.njnu.jdxy.bootserver.utils;

public enum Privileges {
    FORBIDDEN(0, "Visit Forbidden"),
    READ_TASK(1, "Read Tasks"),
    RECEIVE_TASK(2, "Receive Tasks"),
    PUBLISH_TASK(4, "Publish Tasks"),
    WITHDRAW_SELF_TASK(8, "Withdraw Self-signed Tasks"),
    WITHDRAW_ALL_TASK(16, "Withdraw All Tasks"),
    AUDIT_TASK(32, "Audit Tasks"),
    INVITE_MEMBER(64, "Invite Department Members"),
    MANAGE_MEMBER(128, "Manage Department Members"),
    MANAGE_WORKSPACE(256, "Manage Workspace"),
    MANAGE_TASK_STATES(512, "Manage Task States");

    private int code;
    private String message;
    private Privileges(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
