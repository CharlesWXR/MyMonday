package edu.njnu.jdxy.bootserver.utils;

public class TaskTypeUtil {
    static public boolean hasOutput(int taskType) {
        return (taskType & TaskTypes.REQUIRE_OUTPUT.getType()) == 1;
    }

    static public boolean needAudit(int taskType) {
        return (taskType & TaskTypes.REQUIRE_AUDIT.getType()) == 1;
    }

    static public boolean hasDependencies(int taskType) {
        return (taskType & TaskTypes.REQUIRE_DEPENDENCIES.getType()) == 1;
    }
}
