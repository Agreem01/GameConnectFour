package com.agreem.connect4;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    // main function
    public static void main(String[] args) throws BusinessLogicException {
        Board board = createBoard(args);
        Player[] players = createPlayers();

        while (true) {
            play(board, players);
            System.out.print("Do you want to play again (0-no, 1-yes): ");
            if (!(scanner.nextInt() == 1)) {
                break;
            }
            board.initiateBoard();
        }
    }

    // changing of players and winner declaration
    private static void play(Board board, Player[] players) {
        int currentIndex = 0;
        while (true) {
            System.out.println(board);
            currentIndex = currentIndex % players.length;
            Player currentPlayer = players[currentIndex];

            System.out.print(currentPlayer + " what column do you want to put your piece: ");
            int position = scanner.nextInt();

            try {
                if (board.play(currentPlayer, position)) {
                    System.out.println(currentPlayer + " won the match");
                    System.out.println(board);
                    break;
                }
                currentIndex++;
            } catch (BusinessLogicException ble) {
                System.out.println(ble.getMessage());
                // exit if there is no available slot remaining in the grid
            }
        }
    }

    private static Board createBoard(String[] args) throws BusinessLogicException {
        // command line argument : -r 6 -c 7 -p 0
        int row = 0;
        int column = 0;
        int pieces = 0;
        if (args.length < 6) {
            throw new BusinessLogicException("Not all parameter are present");
        }
        for (int i = 0; i < args.length; i += 2) {
            if ("-r".equals(args[i])) {
                row = Integer.parseInt(args[i + 1]);
                if (row < 1) {
                    System.out.print("You can't have 0 rows\nPlease enter a positive non-zero integer for number of rows: ");
                    row = scanner.nextInt();
                } // TODO Not checking again so have to figure out through options
            } else if ("-c".equals(args[i])) {
                column = Integer.parseInt(args[i + 1]);
                if (column < 1) {
                    System.out.print("You can't have 0 column\nPlease enter a positive non-zero integer for number of column: ");
                    column = scanner.nextInt();
                } // TODO Not checking again so have to figure out through options
            } else if ("-p".equals(args[i])) {
                pieces = Integer.parseInt(args[i + 1]);
                if (pieces < 1) {
                    System.out.print("You can't have 0 pieces to connect\nPlease enter a positive non-zero integer for number of pieces to connect: ");
                    pieces = scanner.nextInt();
                } // TODO Not checking again so have to figure out through options
            }
        }

        return new Board(row, column, pieces);
    }

    private static Player[] createPlayers() throws BusinessLogicException {
        System.out.print("Player 1 do you want red or yellow (r or y): ");
        String player1Color = scanner.next();
        String player2Color;
        if ("y".equals(player1Color)) {
            player2Color = "r";
        } else if ("r".equals(player1Color)) {
            player2Color = "y";
        } else {
            throw new BusinessLogicException("Invalid color");
        }
        return new Player[]{new Player("Player 1", player1Color), new Player("Player 2", player2Color)};
    }
}