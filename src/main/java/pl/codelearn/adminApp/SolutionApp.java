package pl.codelearn.adminApp;

import pl.codelearn.dao.UserDao;
import pl.codelearn.model.Exercise;
import pl.codelearn.dao.ExerciseDao;
import pl.codelearn.dao.SolutionDao;
import pl.codelearn.model.Solution;
import pl.codelearn.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SolutionApp {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        String action = null;
        do {
            action = printInvitation();
            switch (action) {
                case "add":
                    printAdd();
                    break;
                case "view":
                    printView();
                    break;
            }
        } while (!("quit").equals(action));
    }

    private static String printInvitation() {
        String action = null;
        do {
            System.out.println("Choose one of the options: [a]dd, [v]iew, [q]uit :");
            String response = scan.nextLine();
            switch (response.charAt(0)) {
                case 'a':
                    action = "add";
                    break;
                case 'v':
                    action = "view";
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
        System.out.println("Users list:");
        User[] users = UserDao.findAll();
        for (User user : users)
            System.out.println("[" + user.getId() + "]" + user.getUserName());

        Integer userId = null;
        do {
            System.out.println("Which user you want to add an exercise to?");
            try {
                userId = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (userId == null);
        System.out.println("Exercises list:");
        Exercise[] exercises = ExerciseDao.findAll();
        for (Exercise exercise : exercises)
            System.out.println("[" + exercise.getId() + "]" + exercise.getTitle());
        Integer exerciseId = null;
        do {
            System.out.println("Which exercise do you want to add?");
            try {
                exerciseId = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (userId == null);
        SolutionDao.create(new Solution(UserDao.read(userId), ExerciseDao.read(exerciseId)));
        System.out.println("Assigned exercise!");
    }

    private static void printView() {
        System.out.println("Users list:");
        User[] users = UserDao.findAll();
        for (User user : users)
            System.out.println("[" + user.getId() + "]" + user.getUserName());
        Integer id = null;
        do {
            System.out.println("Which user exercises you want to see?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                Solution[] solutions = SolutionDao.findAllByUserId(id);
                for (Solution solution : solutions)
                    System.out.println("["+solution.getId()+"]" + ExerciseDao.read(solution.getExercise().getId()).getTitle() + ": " + solution.getDescription());
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (id == null);
    }
}
