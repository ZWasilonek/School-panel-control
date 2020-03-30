package pl.codelearn.adminApp;

import pl.codelearn.dao.UserDao;
import pl.codelearn.model.Group;
import pl.codelearn.dao.GroupDao;
import pl.codelearn.model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserApp {
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
        System.out.println("Users list:");
        User[] users = UserDao.findAll();
        for (User user : users)
            System.out.println("["+user.getId()+"] " + user.getUserName() + " [" + user.getEmail() + "]");

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
        System.out.println("Provide user details:");
        System.out.println("Name:");
        String name = scan.nextLine();
        System.out.println("E-mail:");
        String email = scan.nextLine();
        System.out.println("Password:");
        String password = scan.nextLine();
        Integer id = null;
        do {
            System.out.println("Which group he should belong to:");
            for (Group g : GroupDao.findAll())
                System.out.println("["+g.getId()+"] "+g.getName());
            try {
                id = scan.nextInt();
                scan.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (id == null);
        Group group = GroupDao.read(id);
        UserDao.create(new User(name, email, password, group));
        System.out.println("User added!");
    }

    private static void printEdit() {
        Integer id = null;
        do {
            System.out.println("Which user you want to edit?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter name:");
                String name = scan.nextLine();
                System.out.println("Enter e-mail:");
                String email = scan.nextLine();
                System.out.println("Enter password:");
                String password = scan.nextLine();
                User u = UserDao.read(id);
                u.setUserName(name);
                u.setEmail(email);
                u.setPassword(password);
                UserDao.update(u);
                System.out.println("User data updated!");
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (id == null);
    }

    private static void printDelete() {
        Integer id = null;
        do {
            System.out.println("Which user you want to remove?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                UserDao.delete(id);
                System.out.println("User removed!");
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (id == null);
    }
}
