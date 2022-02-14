package edu.njnu.jdxy.bootserver.utils;

public enum TaskTypes {
    NO_OUTPUT(0, "No Output Task"),
    REQUIRE_OUTPUT(1, "Require Output Task"),
    REQUIRE_AUDIT(2, "Require Audio Task"),
    REQUIRE_DEPENDENCIES(4, "Require Dependencies");

    private int type;
    private String message;

    private TaskTypes(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
