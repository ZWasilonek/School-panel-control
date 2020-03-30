package pl.codelearn.admin;

import pl.codelearn.model.Exercise;
import pl.codelearn.dao.ExerciseDao;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExerciseApp {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String action = null;
        do {
            action = printInvitation();
            switch (action) {
                case "add":
                    printAdd();
                    break;
                case "edit":
                    printEdit();
                    break;
                case "delete":
                    printDelete();
                    break;
            }
        } while (!("quit").equals(action));
    }

    private static String printInvitation() {
        String action = null;
        System.out.println("Exercises list:");
        Exercise[] exercises = ExerciseDao.findAll();
        for (Exercise exercise : exercises)
            System.out.println("["+exercise.getId()+"] " + exercise.getTitle());

        do {
            System.out.println("Choose one of the options: [a]dd, [e]dit, [d]elete, [q]uit :");
            String response = scan.nextLine();
            switch (response.charAt(0)) {
                case 'a':
                    action = "add";
                    break;
                case 'e':
                    action = "edit";
                    break;
                case 'd':
                    action = "delete";
                    break;
                case 'q':
                    action = "quit";
                    break;
                default:
                    System.out.println("There is no such option: " + response);
            }
        } while (action == null);
        return action;
    }

    private static void printAdd() {
        System.out.println("Provide the exercise details:");
        System.out.println("Title:");
        String title = scan.nextLine();
        System.out.println("Description:");
        String description = scan.nextLine();
        ExerciseDao.create(new Exercise(title, description));
        System.out.println("Exercise added!");
    }

    private static void printEdit() {
        Integer id = null;
        do {
            System.out.println("Which exercise do you want to edit?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the title:");
                String title = scan.nextLine();
                System.out.println("Enter the description:");
                String description = scan.nextLine();
                Exercise e = ExerciseDao.read(id);
                e.setTitle(title);
                e.setDescription(description);
                ExerciseDao.update(e);
                System.out.println("Exercise data updated!");
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (id == null);
    }

    private static void printDelete() {
        Integer id = null;
        do {
            System.out.println("Which exercise do you want to remove?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                ExerciseDao.delete(id);
                System.out.println("Exercise removed");
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (id == null);
    }
}
