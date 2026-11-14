import java.util.Random;
import java.util.Scanner;

public class Main {

    static final int SIZE = 3;
    static final char EMPTY = ' ';
    static final char[] SYMBOLS = {'X', 'O'};
    static final String[] NAMES = {"Игрок 1 (X)", "Игрок 2 (O)"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== КРЕСТИКИ-НОЛИКИ ===");
        System.out.println("Вводите номер клетки от 1 до 9:");
        System.out.println("1 | 2 | 3\n4 | 5 | 6\n7 | 8 | 9\n");

        playGame(scanner);
        scanner.close();
    }

    static void playGame(Scanner scanner) {
        char[][] board = createBoard();
        int current = new Random().nextInt(2);
        int moves = 0;
        boolean gameOver = false;
        System.out.println("Первым ходит: " + NAMES[current]);

        while (!gameOver) {
            printBoard(board);
            int cell = askMove(scanner, board, current);
            board[getRow(cell)][getCol(cell)] = SYMBOLS[current];
            moves++;

            if (checkWin(board, SYMBOLS[current])) {
                printBoard(board);
                System.out.println(getResultText(0, current));
                gameOver = true;
            } else if (moves == SIZE * SIZE) {
                printBoard(board);
                System.out.println(getResultText(1, current));
                gameOver = true;
            } else {
                current = 1 - current;
            }
        }

        double filled = moves * 100.0 / (SIZE * SIZE);
        System.out.println("Сделано ходов: " + moves + " (заполнено поля: " + filled + "%)");
    }

    static char[][] createBoard() {
        char[][] board = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        return board;
    }

    static void printBoard(char[][] board) {
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < SIZE - 1) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }

    static int askMove(Scanner scanner, char[][] board, int player) {
        while (true) {
            System.out.print(NAMES[player] + ", ваш ход (1-9): ");
            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Нужно ввести число, а не текст!");
                continue;
            }
            int cell = scanner.nextInt();

            if (!(cell >= 1 && cell <= SIZE * SIZE)) {
                System.out.println("Число должно быть от 1 до 9!");
                continue;
            }

            if (board[getRow(cell)][getCol(cell)] != EMPTY) {
                System.out.println("Эта клетка уже занята!");
                continue;
            }
            return cell;
        }
    }

    static int getRow(int cell) {
        return (cell - 1) / SIZE;
    }

    static int getCol(int cell) {
        return (cell - 1) % SIZE;
    }

    static boolean checkWin(char[][] board, char symbol) {
        for (int i = 0; i < SIZE; i++) {
            boolean rowWin = true;
            boolean colWin = true;
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != symbol) {
                    rowWin = false;
                }
                if (board[j][i] != symbol) {
                    colWin = false;
                }
            }
            if (rowWin || colWin) {
                return true;
            }
        }

        boolean diag1 = true;
        boolean diag2 = true;
        for (int i = 0; i < SIZE; i++) {
            if (board[i][i] != symbol) {
                diag1 = false;
            }
            if (board[i][SIZE - 1 - i] != symbol) {
                diag2 = false;
            }
        }
        return diag1 || diag2;
    }

    static String getResultText(int result, int player) {
        String text;
        switch (result) {
            case 0:
                text = "Победил " + NAMES[player] + "! Поздравляем!";
                break;
            case 1:
                text = "Ничья! Свободных клеток не осталось.";
                break;
            default:
                text = "Игра окончена.";
        }
        return text;
    }
}
