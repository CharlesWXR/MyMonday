package edu.njnu.jdxy.bootserver.utils;

public class TranslatePrivilegeUtil {
    static public boolean testRead(int privilege) {
        return (privilege & Privileges.READ_TASK.getCode()) == 1;
    }

    static public boolean testReceive(int privilege) {
        return (privilege & Privileges.RECEIVE_TASK.getCode()) == 1;
    }

    static public boolean testPublish(int privilege) {
        return (privilege & Privileges.PUBLISH_TASK.getCode()) == 1;
    }

    static public boolean testWithdrawSelf(int privilege) {
        return (privilege & Privileges.WITHDRAW_SELF_TASK.getCode()) == 1;
    }

    static public boolean testWithdrawAll(int privilege) {
        return (privilege & Privileges.WITHDRAW_ALL_TASK.getCode()) == 1;
    }

    static public boolean testAudit(int privilege) {
        return (privilege & Privileges.AUDIT_TASK.getCode()) == 1;
    }

    static public boolean testInviteMember(int privilege) {
        return (privilege & Privileges.INVITE_MEMBER.getCode()) == 1;
    }

    static public boolean testManageMember(int privilege) {
        return (privilege & Privileges.MANAGE_MEMBER.getCode()) == 1;
    }

    static public boolean testManageWorkspace(int privilege) {
        return (privilege & Privileges.MANAGE_WORKSPACE.getCode()) == 1;
    }

    static public boolean testManageTaskState(int privilege) {
        return (privilege & Privileges.MANAGE_TASK_STATES.getCode()) == 1;
    }
}
