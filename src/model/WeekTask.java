package model;

import exceptoin.IncorrectArgumentException;

import java.time.LocalDateTime;

public class WeekTask extends Task {
    public WeekTask(String heading, String description, TaskType taskType, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(heading, description, taskType, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime) {
        return dateTime.plusWeeks(1);
    }
}
