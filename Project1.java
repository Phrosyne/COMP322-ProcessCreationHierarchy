import java.util.*;

class Project1 {
    private static final String NL = "\n";
    private static Scanner input;
    private static Process[] table;
    public static void main(String[] args) {
        input = new Scanner(System.in);
        System.out.println("Enter the table size:");

        if (!input.hasNextInt()) {
            System.out.println("Invalid input type.");
            return;
        }

        int n = input.nextInt();
        if (n <= 0) {
            System.out.println("Invalid table size.");
            return;
        }

        table = new Process[n]; 
        for (int i = 0; i < n; i++) {
            table[i] = new Process();
        }
        table[0].setParentIndex(0);
        
        int selection = -1;
        while (selection != 4) {
            printMenu();
            if (!input.hasNextInt()) {
                System.out.println("Invalid input type.");
                input.nextLine();
                continue;
            }

            selection = input.nextInt();
            input.nextLine();
            switch (selection) {
                case 1:
                    System.out.println("print hierarchy");
                    printHierarchy();
                    break;
                case 2:
                    System.out.println("add process");
                    addProcess();
                    break;
                case 3:
                    System.out.println("remove process");
                    removeProcess();
                    break;
                case 4:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    public static void printMenu() {
            System.out.println(NL + "1) Print the hierarchy from the table\r\n" + //
                            "2) Add a child process to the hierarchy\r\n" + //
                            "3) Remove a process's descendants from the hierarchy\r\n" + //
                            "4) Quit the program\r\n" + //
                            "Enter selection:");
    }

    /*
    1
Index   Parent  First   Younger
0       0       1       
1       0 

ONLY PRINT ACTIVE ROWS. NOT EVERY ROW ALL THE TIME
    */
    public static void printHierarchy() {
        System.out.println("Index\tParent\tFirst\tYounger");
        for (int i = 0; i < table.length; i++) {        
            int parent, first, younger;

            parent = table[i].getParentIndex();
            first = table[i].getFirstChildIndex();
            younger = table[i].getYoungerSiblingIndex();

            String p = parent == -1 ? "0" : Integer.toString(parent);
            String f = first == -1 ? "0" : Integer.toString(first);
            String y = younger == -1 ? "0" : Integer.toString(younger);

            System.out.println(i + "\t" + p + "\t" + f + "\t" + y);
        }
    }

    public static void addProcess() {
        System.out.println("Enter the parent process index for the child process:");
        if (!input.hasNextInt()) {
            System.out.println("Invalid input.");
            input.nextLine();
            return;
        }

        int parentIndex = input.nextInt();
        input.nextLine();
        if (parentIndex < 0 || parentIndex >= table.length) {
            System.out.println("Invalid process index.");
            return;
        }

        if (table[parentIndex].getParentIndex() == -1) {
            System.out.println("Process index is not active.");
            return;
        }

        int childIndex = 0;
        while (table[childIndex].getParentIndex() != -1) {
            childIndex++;
            if (childIndex == table.length) {
                System.out.println("Unable to assign an index for the child process.");
                return;
            }
        }

        table[childIndex].setParentIndex(parentIndex);
        if (table[parentIndex].getFirstChildIndex() == -1) {
            table[parentIndex].setFirstChildIndex(childIndex);
        } else {
            int youngestIndex = table[parentIndex].getFirstChildIndex();
            while(table[youngestIndex].getYoungerSiblingIndex() != -1) {
                youngestIndex = table[youngestIndex].getYoungerSiblingIndex();
            }
            table[youngestIndex].setYoungerSiblingIndex(childIndex);
        }

        System.out.println("Process " + childIndex + " was added as a child of process " + parentIndex + ".");
    }

    public static void removeProcess() {}

}