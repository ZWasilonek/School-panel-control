package pl.codelearn.adminApp;

import pl.codelearn.model.Group;
import pl.codelearn.dao.GroupDao;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GroupApp {
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
        System.out.println("Group list:");
        Group[] groups = GroupDao.findAll();
        for (Group group : groups)
            System.out.println("["+group.getId()+"] " + group.getName());

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
        System.out.println("Provide group details");
        System.out.println("Name:");
        String name = scan.nextLine();
        Group group = new Group();
        group.setName(name);
        GroupDao.create(group);
        System.out.println("Group added!");
    }

    private static void printEdit() {
        Integer id = null;
        do {
            System.out.println("Which group do you want to edit?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter the name:");
                String name = scan.nextLine();
                Group g = GroupDao.read(id);
                g.setName(name);
                GroupDao.update(g);
                System.out.println("Group data updated!");
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (id == null);
    }

    private static void printDelete() {
        Integer id = null;
        do {
            System.out.println("Which group do you want to remove?");
            try {
                id = scan.nextInt();
                scan.nextLine();
                GroupDao.delete(id);
                System.out.println("Group removed!");
            } catch (InputMismatchException e) {
                System.out.println("Incorrect number");
                scan.nextLine();
            }
        } while (id == null);
    }
}
