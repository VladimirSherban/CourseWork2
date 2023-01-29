package model;

import exceptoin.IncorrectArgumentException;

import java.time.LocalDateTime;

public class OneTimeTask extends Task {
    public OneTimeTask(String heading, String description, TaskType taskType, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(heading, description, taskType, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime) {
        return null;
    }
}
