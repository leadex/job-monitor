package ru.leadexsystems.startup.jobmonitor.common.model;

/**
 * Created by ekabardinsky on 11/21/16.
 */
public enum NotificationType {
    slack;

    public static NotificationType createByName(String name){
        switch (name){
            case "slack" : return NotificationType.slack;
        }
        throw new IllegalStateException(String.format("Unknow type : %s", name));
    }

}
