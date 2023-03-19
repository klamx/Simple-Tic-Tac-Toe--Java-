package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dimension = 3;

        String[] characters = new String[9];
        Arrays.fill(characters, " ");
        String[][] table2d = convertTo2D(characters, dimension);
        int[] newCoord;
        int move = 0;

        printTable(characters);

        while (true) {
            newCoord = getCoords(scanner, table2d);
            table2d = newMove(table2d, newCoord, move);
            move++;
            characters = flattenArray(table2d);
            printTable(characters);

            // mensaje de quien gano despues de validar el tablero
            if (!validateTable(table2d)) {
                System.out.println("Impossible");
                break;
            } else {
                String wins = whoWins(table2d);
                if (wins.equals("X")) {
                    System.out.println("X wins");
                    break;
                } else if (wins.equals("O")) {
                    System.out.println("O wins");
                    break;
                }
            }
        }
//        scanner.close();
    }

    public static String[] flattenArray(String[][] array2D) {
        String[] flatArray = new String[array2D.length * array2D[0].length];
        int index = 0;

        for (String[] strings : array2D) {
            for (String string : strings) {
                flatArray[index++] = string;
            }
        }

        return flatArray;
    }

    public static int[] getCoords(Scanner scanner, String[][] table) {
        while (true) {
            int[] coords = new int[2];
            int num;
            for (int i = 0; i < 2; i++) {
                try {
                    num = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("You should enter numbers!");
                    break;
                }
                if (num >= 1 && num <= 3) {
                    coords[i] = num;
                } else {
                    System.out.println("Coordinates should be from 1 to 3!");
                    scanner.nextLine();
                    break;
                }
            }
            if (coords[0] != 0 && coords[1] != 0) {
                coords[0] -= 1;
                coords[1] -= 1;
            } else {
                continue;
            }
            if (validateCoord(table,coords)) {
                return coords;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }

    public static String[][] newMove(String[][] table, int[] coord, int move) {
        if (move % 2 == 0) {
            table[coord[0]][coord[1]] = "X";
        } else {
            table[coord[0]][coord[1]] = "O";
        }

        return table;
    }

    public static boolean validateCoord(String[][] table, int[] coord) {
        return table[coord[0]][coord[1]].equals("_") || table[coord[0]][coord[1]].equals(" ");
    }
    public static void printTable(String[] characters) {
        String bars = "---------";
        String pipe = "|";
        System.out.println(bars);
        for (int i = 0; i < characters.length; i++) {
            if (characters[i].equals("_")) {
                characters[i] = " ";
            }
            if (i % 3 == 0) {
                System.out.print(pipe + ' ' + characters[i] + ' ');
                continue;
            }
            if ((i + 1) % 3 == 0) {
                System.out.print(characters[i] + ' ' + pipe + '\n');
                continue;
            }
            System.out.print(characters[i] + ' ');
        }
        System.out.println(bars);
    }

    public static String[][] convertTo2D(String[] characters, int dimension) {
        String[][] new2d = new String[dimension][dimension];
        int count = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                new2d[i][j] = characters[count];
                count++;
            }
        }
        return new2d;
    }

    public static String whoWins(String[][] table) {
        boolean lineX = false;
        boolean lineO = false;

        for (int i = 0; i < 3; i++) {
            if ((!table[i][0].equals("_") || !table[i][0].equals("")) && table[i][0].equals(table[i][1]) && table[i][1].equals(table[i][2])) {
                if (!lineX) {
                    lineX = table[i][0].equals("X");
                }
                if (!lineO) {
                    lineO = table[i][0].equals("O");
                }
            }
        }

        // Analizar columnas
        for (int j = 0; j < 3; j++)
            if ((!table[0][j].equals("_") || !table[0][j].equals("")) && table[0][j].equals(table[1][j]) && table[1][j].equals(table[2][j])) {
                if (!lineX) {
                    lineX = table[0][j].equals("X");
                }
                if (!lineO) {
                    lineO = table[0][j].equals("O");
                }
            }

        if (lineX && lineO) {
            return "Impossible";
        }else if (lineX) {
            return "X";
        }else if (lineO) {
            return "O";
        }

        // Analizar diagonales
        if (table[0][0].equals(table[1][1]) && table[1][1].equals(table[2][2])) {
            return table[0][0];
        }
        if (table[0][2].equals(table[1][1]) && table[1][1].equals(table[2][0])) {
            return table[0][2];
        }

        // Verificar si hay empate
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[i][j].equals("_")) {
                    return "Game not finished";
                }
            }
        }

        // No hay ganador y el tablero está lleno, es un empate
        return "Draw";
    }

    public static boolean validateTable(String[][] table) {

        int countX = 0, countO = 0;
        for (String[] strings : table) {
            for (String c : strings) {
                if (!c.equals("X") && !c.equals("O") && !c.equals("_") && !c.equals(" ")) {
                    return false;
                }
                if (c.equals("X")) {
                    countX++;
                } else if (c.equals("O")) {
                    countO++;
                }
            }
        }

        int difference = (Math.abs(countX - countO));
        return difference >= 0 && difference <= 1;
    }
}
