import exceptoin.IncorrectArgumentException;
import exceptoin.TaskNotFoundException;
import model.*;
import service.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static final TaskService taskService = new TaskService();

    private static final Pattern DATE_TIME_PATTERN =
            Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}");
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            removeTask(scanner);
                            break;
                        case 3:
                            printTaskByDay(scanner);
                            break;
                        case 4:
                            taskService.printDeleteTask();
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");
                }
            }
        }
    }

    private static void inputTask(Scanner scanner) {
        scanner.useDelimiter("\n");

        String heading = inputTaskTitle(scanner);
        String description = inputTaskDescription(scanner);
        TaskType type = inputTaskType(scanner);
        LocalDateTime taskTime = inputTaskTime(scanner);
        int repeatability = inputRepeatability(scanner);

        createTask(heading, description, type, taskTime, repeatability);
    }

    private static void removeTask(Scanner scanner) {
        System.out.println("Введите id задачи для удаления");
        int id = scanner.nextInt();

        try {
            taskService.removeTask(id);
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printTaskByDay(Scanner scanner) {
        System.out.println("Введите дату  задачи в формате dd.MM.yyyy");

        if (scanner.hasNext(DATE_PATTERN)) {
            String dateTime = scanner.next(DATE_PATTERN);
            LocalDate inputDate = LocalDate.parse(dateTime, DATE_FORMATTER);

            Collection<Task> taskByDay = taskService.getAllByDate(inputDate);

            for (Task task : taskByDay) {
                System.out.println(task);
            }
        } else {
            System.out.println("Введите дату и время задачи в формате dd.MM.yyyy");
            scanner.close();
        }
    }

    private static String inputTaskTitle(Scanner scanner) {
        System.out.println("Введите название задачи ");
        String title = scanner.next();

        if (title.isBlank()) {
            System.out.println("Необходимо ввести название задачи!");
            scanner.close();
        }
        return title;
    }

    private static String inputTaskDescription(Scanner scanner) {
        System.out.println("Введите описание задачи ");
        String description = scanner.next();

        if (description.isBlank()) {
            System.out.println("Необходимо ввести описание задачи!");
            scanner.close();
        }
        return description;
    }

    private static TaskType inputTaskType(Scanner scanner) {
        System.out.println("Введите тип задачи: (1 - личная, 2 - рабочая):");
        TaskType type;

        int taskTypeChoice = scanner.nextInt();

        switch (taskTypeChoice) {
            case 1:
                type = TaskType.PERSONAL_TASK;
                break;
            case 2:
                type = TaskType.WORK_TASK;
                break;
            default:
                System.out.println("Данные введены некорректно, назначена личная задача по умолчанию");
                type = TaskType.PERSONAL_TASK;
                break;
        }
        return type;
    }

    private static LocalDateTime inputTaskTime(Scanner scanner) {
        System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm");


        if (scanner.hasNext(DATE_TIME_PATTERN)) {
            String dateTime = scanner.next(DATE_TIME_PATTERN);
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } else {
            System.out.println("Введите дату и время задачи в формате dd.MM.yyyy HH:mm");
            scanner.close();
            return null;
        }
    }

    private static int inputRepeatability(Scanner scanner) {
        System.out.println("Введите повторяемые задачи: (1 - однократно, 2 - каждый день, " +
                "3 - каждую неделю, 4 - каждый месяц, 5 каждый год):");

        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            System.out.println("Введите числом повторяемость задачи");
            scanner.close();
        }
        return -1;
    }

    private static void createTask(String heading, String description,
                                   TaskType type, LocalDateTime taskTime, int repeatability) {

        Task task = null;
        try {
            switch (repeatability) {
                case 1:
                    task = new OneTimeTask(heading, description, type, taskTime);
                    break;
                case 2:
                    task = new DailyTask(heading, description, type, taskTime);
                    break;
                case 3:
                    task = new WeekTask(heading, description, type, taskTime);
                    break;
                case 4:
                    task = new MonthlyTask(heading, description, type, taskTime);
                    break;
                case 5:
                    task = new YearlyTask(heading, description, type, taskTime);
                    break;
                default:
                    System.out.println("Повторяемость задачи введена некорректно");
            }
        } catch (IncorrectArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (task != null) {
            taskService.addTask(task);
            System.out.println("Задача добавлена - " + task.getHeading());
        } else {
            System.out.println("Введены некорректные данные по задаче");
        }
    }

    private static void printMenu() {
        System.out.println(
                "1. Добавить задачу \n" +
                        "2. Удалить задачу\n" +
                        "3. Показать задачи на указанный день\n" +
                        "4. Посмотреть удаленные задачи\n" +
                        "0. Выход\n"
        );
    }
}