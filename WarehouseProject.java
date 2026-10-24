import java.util.Scanner;

public class WarehouseProject {
    public static void main(String[] args){

        // DECLARE VARIABLES

        Scanner scanner = new Scanner(System.in);

        int choice;
        boolean isRunning = true;

        // ARRAYS

        String[] product = new String[9];
        int[] quantity = new int[9];
        int[] shelf = new int[9];

        // MENU

        while(isRunning){

            System.out.println(" ");
            System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ●\n");
            System.out.println("Welcome to Ewa's Warehouse!");
            System.out.println("What do you want to do? \n");
            System.out.println("  1. Receive goods.");
            System.out.println("  2. Remove products.");
            System.out.println("  3. Check inventory.");
            System.out.println("  4. Check the shelf.");
            System.out.println("  5. Exit.\n");
            System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ●");
            System.out.println(" ");

            // GET AND PROCESS THE USER'S CHOICE

            System.out.print("Enter your choice (1-5): ");

            while(!scanner.hasNextInt()){
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next();
            }

            choice = scanner.nextInt();
            while(choice <= 0 || choice > 5){
                System.out.print("Invalid number. Enter your choice (1-5): ");
                choice = scanner.nextInt();
            }

            switch(choice){
                case 1 -> addProduct(product, quantity, shelf, scanner);
                case 2 -> removeProduct(product, quantity, shelf, scanner);
                case 3 -> checkInventory(product, quantity, shelf, scanner);
                case 4 -> checkShelf(product, shelf, scanner);
                case 5 -> {
                    System.out.println(" ");
                    System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ●\n");
                    System.out.println("Thank you for using Ewa's Warehouse!");
                    System.out.println("See you next time!\n");
                    System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ●");
                    System.out.println(" ");
                    isRunning = false;
                }
                default -> System.out.println("Sorry... Your choice is invalid!");
            }
        }
        scanner.close();
    }

    // 1. RECEIVE GOODS
    static void addProduct(String[] product, int[] quantity, int[] shelf, Scanner scanner) {

        String addAnotherProduct = "Y";

        while(addAnotherProduct.equals("Y")) {

            boolean found = false;
            boolean added = false;
            int foundIndex = -1;

            System.out.print("Enter product name: ");
            String productName = scanner.next();

            for (int i = 0; i < product.length; i++) {
                if (product[i] != null && product[i].equalsIgnoreCase(productName)) {
                    found = true;
                    foundIndex = i;
                }
            }
            if (found) {
                System.out.print("This product already exists. Enter the quantity to add: ");
                int amountToAdd = scanner.nextInt();

                while (amountToAdd <= 0) {
                    System.out.println("Quantity must be greater than 0. Enter again: ");
                    amountToAdd = scanner.nextInt();
                }

                quantity[foundIndex] += amountToAdd;

                System.out.println("The new quantity is: " + quantity[foundIndex] + " pieces.");

                added = true;
            }
            else {
                for (int i = 0; i < product.length; i++) {
                    if (product[i] == null) {

                        product[i] = productName;

                        System.out.print("Enter the quantity: ");
                        quantity[i] = scanner.nextInt();

                        while (quantity[i] <= 0) {
                            System.out.print("Quantity must be greater than 0. Enter again: ");
                            quantity[i] = scanner.nextInt();
                        }

                        System.out.print("Enter the shelf number (1, 2, 3): ");
                        shelf[i] = scanner.nextInt();
                        while (shelf[i] <= 0 || shelf[i] > 3) {
                            System.out.print("You can choose shelf 1, 2 or 3: ");
                            shelf[i] = scanner.nextInt();
                        }

                        added = true;
                        break;

                    }
                }
            }
            if (!added) {
                System.out.println("The warehouse is full. You cannot add a new product.");
            }

            scanner.nextLine();

            System.out.print("Do you want to add another product? (Y/N): ");
            addAnotherProduct = scanner.nextLine().toUpperCase();

            while (!addAnotherProduct.equals("Y") && !addAnotherProduct.equals("N")) {
                System.out.print("Invalid choice. Please enter Y or N: ");
                addAnotherProduct = scanner.nextLine().toUpperCase();
            }
        }
    }

    // 2. REMOVE PRODUCTS
    static void removeProduct(String[] product, int[] quantity, int[] shelf, Scanner scanner){

        String removeAnotherProduct = "Y";

        while(removeAnotherProduct.equals("Y")){

            boolean found = false;
            int foundIndex = -1;
            boolean empty = true;

            for (int i = 0; i < product.length; i++) {
                if (product[i] != null) {
                    empty = false;
                }
            }
            if(empty){
                System.out.println("The warehouse is empty. Nothing to remove.");
                return;
            }

            while(!found){
                System.out.print("Enter product name: ");
                String productName = scanner.next();
                for (int i = 0; i < product.length; i++){
                    if(product[i] != null && product[i].equalsIgnoreCase(productName)){
                        found = true;
                        foundIndex = i;
                    }
                }
                if(!found){
                    System.out.println("The product is not in stock. Try again.");
                }
            }
            if (found){
                System.out.println("In stock: " + quantity[foundIndex] + " pieces.");
                System.out.print("How many units do you want to remove? ");
                int amountToRemove = scanner.nextInt();

                while(amountToRemove <= 0 || amountToRemove > quantity[foundIndex]){
                    System.out.println("Quantity must be greater than 0 and cannot exceed the available stock. Please enter the amount again: ");
                    amountToRemove = scanner.nextInt();
                }

                quantity[foundIndex] -= amountToRemove;

                if(quantity[foundIndex] == 0){
                    product[foundIndex] = null;
                    shelf[foundIndex] = 0;

                    System.out.println("The product is out of stock and has been removed.");
                }
                else{
                    System.out.println("Remaining stock: " + quantity[foundIndex] + " pieces.");
                }
            }

            System.out.print("Do you want to remove another product? (Y/N): ");
            scanner.nextLine();
            removeAnotherProduct = scanner.nextLine().toUpperCase();

            while (!removeAnotherProduct.equals("Y") && !removeAnotherProduct.equals("N")) {
                System.out.print("Invalid choice. Please enter Y or N: ");
                removeAnotherProduct = scanner.nextLine().toUpperCase();
            }
        }
    }

    // 3. CHECK INVENTORY
    static void checkInventory(String[] product, int[] quantity, int[] shelf, Scanner scanner) {

        boolean empty = true;

        for (int i = 0; i < product.length; i++) {
            if (product[i] != null) {
                empty = false;
            }
        }
        if (empty) {
            System.out.println("The warehouse is empty.");
            System.out.println("Press Enter to return to the menu.");
            scanner.nextLine();
            scanner.nextLine();
            return;
        }

        System.out.println(" ");
        System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ●\n");
        System.out.println("Warehouse inventory:\n");

        for (int i = 0; i < product.length; i++) {
            if (product[i] != null) {
                System.out.println(
                        "Product: " + product[i] +
                                " | Quantity: " + quantity[i] +
                                " | Shelf: " + shelf[i] +
                                " "
                );
            }
        }

        System.out.println(" ");
        System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ●");
        System.out.println(" ");

        System.out.println("Press Enter to return to the menu.");
        scanner.nextLine();
        scanner.nextLine();
    }

    // 4. CHECK THE SHELF
    static void checkShelf (String[] product, int[] shelf, Scanner scanner){

        int checkShelf;
        String checkAnotherShelf = "Y";

        while (checkAnotherShelf.equals("Y")) {

            boolean empty = true;

            System.out.print("Which shelf do you want to check? (1, 2, 3): ");
            checkShelf = scanner.nextInt();

            while(checkShelf < 1 || checkShelf > 3){
                System.out.println("Invalid shelf. Please choose shelf 1, 2 or 3: ");
                checkShelf = scanner.nextInt();
            }

            for (int i = 0; i < product.length; i++) {
                if (product[i] != null && shelf[i] == checkShelf) {
                    empty = false;
                }
            }
            if (empty) {
                System.out.println("This shelf is empty.");
                System.out.println("Press Enter to return to the menu.");
                scanner.nextLine();
                scanner.nextLine();
                return;
            }

            System.out.println(" ");
            System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ●\n");
            System.out.println("Products on shelf "  + checkShelf + ":\n");

            for (int i = 0; i < product.length; i++) {
                if (product[i] != null && shelf[i] == checkShelf) {
                    System.out.println(product[i]);
                }
            }

            System.out.println(" ");
            System.out.println("● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ● ●");
            System.out.println(" ");

            scanner.nextLine();

            System.out.print("Do you want to check another shelf? (Y/N): ");
            checkAnotherShelf = scanner.nextLine().toUpperCase();

            while (!checkAnotherShelf.equals("Y") && !checkAnotherShelf.equals("N")) {
                System.out.print("Invalid choice. Please enter Y or N: ");
                checkAnotherShelf = scanner.nextLine().toUpperCase();
            }
        }
    }
}
