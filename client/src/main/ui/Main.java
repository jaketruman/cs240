package ui;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
            System.out.println("WELCOME TO KNOCKOFF CHESS.COM");
            Scanner scanner = new Scanner(System.in);
            ChessClient chessClient =new ChessClient();
            boolean b = true;
            while (b){
                String temp = scanner.nextLine();
                System.out.println(chessClient.command(temp));
                if(temp.equals("Quit")){
                    b = false;
                }
            }


    }
}
