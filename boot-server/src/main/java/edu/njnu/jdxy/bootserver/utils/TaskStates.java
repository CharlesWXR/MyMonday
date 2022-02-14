package edu.njnu.jdxy.bootserver.utils;

public enum TaskStates {
    CLOSED(0),
    INITED(1),
    UNHANDLED(2),
    SUSPENDING(3),
    HANDLING(4),
    WAITING_AUDIT(5),
    WAITING_MODIFY(6),
    FINISHED(7);

    private int code;
    private TaskStates(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
