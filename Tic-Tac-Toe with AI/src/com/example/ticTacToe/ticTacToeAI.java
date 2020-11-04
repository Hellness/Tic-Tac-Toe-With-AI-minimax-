//Another loop for continues playing. Add player option(first-second / player-AI)
package com.example.ticTacToe;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ticTacToeAI {
    enum condition{
        NOT,DRAW,X,Y
    }
    public static void main(String[] args){
        condition fin = condition.NOT;
        char[] board = {' ',' ',' ',' ',' ',' ',' ',' ',' '};
        int counter = 0;
        printResult(fin,board);
        while(fin == condition.NOT){
            if(counter%2 == 0){
                playersTurn(board);
            }
            else{
                AIsTurn(board);
            }
            fin = control(board);
            printResult(fin,board);
            counter++;
        }
    }
    public static void playersTurn(char[] board){
        Scanner aScanner = new Scanner(System.in);
        String input;
        int posX;
        int posY;
        do {
            do {
                System.out.print("Enter the coordinates you are willing to choose: ");
                input = aScanner.nextLine();
            }
            while (!controlInputForm(input));
            posX = Character.getNumericValue(input.charAt(0));
            posY = Character.getNumericValue(input.charAt(2));
        }while (!controlXY(posX,posY,board));
        board[posX*3+posY] = 'X';
    }
    public static void AIsTurn(char[] board){
        int bestScore = -2;
        int move = 0;
        for(int i=0;i<9;i++){
            if(board[i] == ' '){
                board[i] = 'Y';
                int score = minimax(board,0,false);
                board[i] = ' ';
                if(score>bestScore){
                    bestScore = score;
                    move = i;
                }
            }
        }
        board[move] = 'Y';
    }
    public static condition control(char[] board){
        if((board[0] == board[4]&&board[4]==board[8])||(board[2] == board[4]&&board[4]==board[6])){
            if(board[4] == 'X'){
                return condition.X;
            }
            else if(board[4] == 'Y'){
                return condition.Y;
            }
            else{
                return condition.NOT;
            }
        }
        for(int i=0;i<3;i++){
            int whichH = i*3;
            if(board[whichH]==board[whichH+1]&&board[whichH+1]==board[whichH+2]){
                if(board[whichH]=='X'){
                    return condition.X;
                }
                else if(board[whichH]=='Y'){
                    return condition.Y;
                }
            }
            else if(board[i]==board[i+3]&&board[i+3]==board[i+6]){
                if(board[i]=='X'){
                    return condition.X;
                }
                else if(board[i]=='Y'){
                    return condition.Y;
                }
            }
        }
        for(int i=0;i<9;i++){
            if(board[i]==' '){
                return condition.NOT;
            }
        }
        return condition.DRAW;
    }
    public static boolean controlInputForm(String input){
        String pattern = "^\\d\\s\\d$";
        return Pattern.matches(pattern, input);
    }
    public static boolean controlXY(int x,int y,char[] board){
        return board[x * 3 + y] != 'X' && board[x * 3 + y] != 'Y';
    }
    public static void printResult(condition fin,char[] board){
        System.out.print("-------------\n|");
        for(int i=0;i<9;i++){
            System.out.printf("%3c",board[i]);
            if((i+1)%3 ==0&&i!=8){
                System.out.print("  |\n|");
            }
            else if(i==8){
                System.out.println("  |");
            }
        }
        System.out.println("-------------");
        if(fin==condition.DRAW){
            System.out.print("TIE");
        }
        else if(fin==condition.X){
            System.out.print("X WINS");
        }
        else if(fin == condition.Y){
            System.out.print("Y WINS");
        }
    }
    public static int minimax(char[] board, int depth,boolean turn) {
        condition fin = control(board);
        int bestScore;
        if (fin != condition.NOT) {
            if (fin == condition.Y) {
                return 1;
            } else if (fin == condition.X) {
                return -1;
            } else {
                return 0;
            }
        }
        if (turn) {
            bestScore = -2;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'Y';
                    int score = minimax(board, depth + 1, false);
                    board[i] = ' ';
                    bestScore = Math.max(bestScore, score);
                }
            }
        } else {
            bestScore = 2;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    int score = minimax(board, depth + 1, true);
                    board[i] = ' ';
                    bestScore = Math.min(bestScore, score);
                }
            }
        }
        return bestScore;
    }
}
