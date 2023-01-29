package model;

import exceptoin.IncorrectArgumentException;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task implements Repeatable {
    private static Integer counter = 1;
    private final Integer id;
    private String heading;
    private String description;
    private TaskType taskType;
    private LocalDateTime taskTime;


    public Task(String heading, String description, TaskType taskType, LocalDateTime taskTime) throws IncorrectArgumentException {
        this.id = counter;
        setHeading(heading);
        setTaskTime(taskTime);
        setTaskType(taskType);
        setDescription(description);
        counter++;
    }


    public Integer getId() {
        return id;
    }

    public String getHeading() {

        return heading;
    }

    public void setHeading(String heading) throws IncorrectArgumentException {
        if (heading != null && !heading.isEmpty()) {
            this.heading = heading;
        } else {
            throw new IncorrectArgumentException("заголовок задачи");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IncorrectArgumentException {
        if (description != null && !description.isEmpty()) {
            this.description = description;
        } else {
            throw new IncorrectArgumentException("описание задачи");
        }
    }


    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) throws IncorrectArgumentException {
        if (taskType != null) {
            this.taskType = taskType;
        } else {
            throw new IncorrectArgumentException("тип задачи");
        }
    }

    public LocalDateTime getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(LocalDateTime taskTime) throws IncorrectArgumentException {
        if (taskTime != null) {
            this.taskTime = taskTime;
        } else {
            throw new IncorrectArgumentException("время задачи");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(heading, task.heading) && Objects.equals(description, task.description) && taskType == task.taskType && Objects.equals(taskTime, task.taskTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heading, description, taskType, taskTime);
    }

    @Override
    public String toString() {
        return "Задача:" + "id=" + id + ", заголовок='" + heading + '\'' + ", описание='" + description + '\'' + ", тип=" + taskType + ", время=" + taskTime;
    }
}
