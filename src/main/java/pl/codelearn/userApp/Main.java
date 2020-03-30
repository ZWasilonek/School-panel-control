package pl.codelearn.UserApp;

import pl.codelearn.dao.UserDao;
import pl.codelearn.dao.SolutionDao;
import pl.codelearn.model.Solution;
import pl.codelearn.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: Main [id]");
            System.out.println(" id - user id");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println(args[1] + " - id is not correct");
            System.out.println("Usage: Main [id]");
            System.out.println(" id - user id");
            return;
        }
        User user = UserDao.read(id);
        if (user == null) {
            System.out.println(args[0] + " - id is not correct");
            System.out.println("Usage: Main [id]");
            System.out.println(" id - user id");
            return;
        }

        //We have proper User
        String action = null;
        do {
            action = printInvitation();
            switch (action) {
                case "add":
                    printAdd(user);
                    break;
                case "view":
                    printView(user);
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

    private static void printAdd(User user) {
        System.out.println("Exercise list without solutions:");
        Solution[] solutions = SolutionDao.findAllByUserId(user.getId());
        for (Solution solution : solutions)
            if (solution.getDescription() == null || solution.getDescription().isEmpty())
            System.out.println("[" + solution.getId() + "]" + solution.getExercise().getTitle());
        Integer solutionId = null;
        do {
            System.out.println("Which task do you want to solve?");
            try {
                solutionId = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number.");
                scan.nextLine();
            }
        } while (solutionId == null);
        Solution solution = SolutionDao.read(solutionId);
        System.out.println("Provide a solution for the exercise: " + solution.getId() + " " + solution.getExercise().getTitle());
        System.out.println(solution.getExercise().getDescription());
        String description = scan.nextLine();
        solution.setDescription(description);
        SolutionDao.update(solution);
        System.out.println("Solution saved!");
    }

    private static void printView(User user) {
        Solution[] solutions = SolutionDao.findAllByUserId(user.getId());
        for(Solution solution : solutions) {
            System.out.println("Exercise: " + "[" + solution.getExercise().getId() + "]" + solution.getExercise().getTitle() + ": " + solution.getExercise().getDescription());
            System.out.println("Solution: " + solution.getDescription());
        }
    }
}
