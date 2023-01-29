package model;

import exceptoin.IncorrectArgumentException;

import java.time.LocalDateTime;

public class YearlyTask extends Task {

    public YearlyTask(String heading, String description, TaskType taskType, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(heading, description, taskType, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime) {
        return dateTime.plusYears(1);
    }
}
