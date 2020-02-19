import java.util.*;

/**
 * @author lyskevin
 */
public class Main {

    private static final int[][] grid = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

    public static void main(String[] args) {
        System.out.println("Welcome to tic-tac-toe!\n");
        Scanner sc = new Scanner(System.in);
        while (!terminalTest()) {
            Action action = minimaxDecision();
            grid[action.row][action.column] = 1;
            printGrid();
            if (terminalTest()) {
                break;
            }
            System.out.println();
            System.out.println("Enter your move (row column): ");
            int row = sc.nextInt();
            int column = sc.nextInt();
            grid[row][column] = -1;
            System.out.println();
            printGrid();
            System.out.println("\nMy turn: \n");
        }
        System.out.println();
        if (utility() > 0) {
            System.out.println("You lost :)");
        } else if (utility() < 0) {
            System.out.println("You won :(");
        } else {
            System.out.println("Draw :|");
        }
        sc.close();
    }

    private static void printGrid() {
        for (int i = 0; i < 3; i++) {
            if (i > 0) {
                System.out.format("-----\n");
            }
            System.out.format("%s|%s|%s\n", getMarking(grid[i][0]), getMarking(grid[i][1]),
                    getMarking(grid[i][2]));
        }
    }

    private static String getMarking(int marking) {
        if (marking == 0) {
            return " ";
        } else if (marking == 1) {
            return "X";
        } else {
            return "O";
        }
    }

    private static Action minimaxDecision() {
        // Generate actions
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) {
                    grid[i][j] = 1;
                    int value = minValue();
                    actions.add(new Action(i, j, 1, value));
                    grid[i][j] = 0;
                }
            }
        }
        // Sort and return action with highest utility
        Collections.sort(actions);
        return actions.get(0);
    }

    private static int maxValue() {
        if (terminalTest()) {
            return utility();
        }
        int value = 10;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) {
                    grid[i][j] = 1;
                    value = Math.max(value, minValue());
                    grid[i][j] = 0;
                }
            }
        }
        return value;
    }

    private static int minValue() {
        if (terminalTest()) {
            return utility();
        }
        int value = -10;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) {
                    grid[i][j] = -1;
                    value = Math.min(value, maxValue());
                    grid[i][j] = 0;
                }
            }
        }
        return value;
    }

    private static boolean terminalTest() {
        for (int i = 0; i < 3; i++) {
            // Same value across a row
            if (grid[i][0] != 0 && grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2]) {
                return true;
            }
            // Same value across a column
            if (grid[0][i] != 0 && grid[0][i] == grid[1][i] && grid[0][i] == grid[2][i]) {
                return true;
            }
        }
        // Same value across diagonal
        if (grid[0][0] != 0 && grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2]) {
            return true;
        }
        // Same value across anti-diagonal
        if (grid[0][2] != 0 && grid[0][2] == grid[1][1] && grid[0][2] == grid[2][0]) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int utility() {
        for (int i = 0; i < 3; i++) {
            // Same value across a row
            if (grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2]) {
                return grid[i][0];
            }
            // Same value across a column
            if (grid[0][i] == grid[1][i] && grid[0][i] == grid[2][i]) {
                return grid[0][i];
            }
        }
        // Same value across diagonal
        if (grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2]) {
            return grid[0][0];
        }
        // Same value across anti-diagonal
        if (grid[0][2] == grid[1][1] && grid[0][2] == grid[2][0]) {
            return grid[0][2];
        }
        return 0;
    }

}

class Action implements Comparable<Action> {

    int row;
    int column;
    int marking;
    int value;

    Action(int row, int column, int marking, int value) {
        this.row = row;
        this.column = column;
        this.marking = marking;
        this.value = value;
    }

    @Override
        public int compareTo(Action other) {
            return other.value - this.value;
        }

}

