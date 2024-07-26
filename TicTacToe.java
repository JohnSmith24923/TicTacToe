package tictactoe;

// TicTacToe: Rishi Shah 

/* Your task is to finish this program.
 * 	TODO: check for win
	TODO: check for tie
	DO NOT let people continue clicking if the game is won.
	Play again?  -- optional addition (or else just end the program nicely)
 */

import java.awt.Color;
import hsa2.GraphicsConsole;

public class TicTacToe {

	public static void main(String[] args) {
		new TicTacToe();
	}

	int winW = 600;
	int winH = 600;
	GraphicsConsole gc = new GraphicsConsole(winW,winH);

	static final int XX = 1;
	static final int OO = -1;
	static final int BLANK = 0;  //or call it EMPTY

	static final int SIZE = 3;	 //or call it GRID
	int[][] board = new int[SIZE][SIZE];
	int turn = XX;	

	TicTacToe() {
		setup();

		while(true) {			
			if (gc.getMouseClick() > 0) handleMouseClick();			
			//Play again?  -- optional addition
			drawGraphics();
			gc.sleep(10);

			// Check for win
			int winner = checkWin();

			if (winner != BLANK) {
				String winnerStr;
				if (winner == XX) {
					winnerStr = "1";
				} else {
					winnerStr = "2";
				}
				
				gc.showDialog("Thanks for playing! Player " + winnerStr + " wins!", "Game Over");
				gc.close();
				return;
			}

			// Check for tie
			if (checkTie()) {
				System.out.println("It is a tie!");
				gc.showDialog("It is a tie!", "Game Over");
				gc.close();
				return;
			}
		}
	}	

	void setup() {
		gc.setTitle("TicTacToe");
		gc.setLocationRelativeTo(null);
		gc.setAntiAlias(true);
		gc.setStroke(2);
		gc.setBackgroundColor(Color.decode("#CCDDFF"));
		gc.clear();
		gc.setResizable(true);
		gc.enableMouse(); //enables mouse clicking
	}

	void handleMouseClick() {
		//1. find which square the player clicked in
		//NOTE: I'm dividing by "boxW" from drawGraphics()
		//		actually, invert and multiply
		int col = gc.getMouseX() * SIZE/winW; 
		int row = gc.getMouseY() * SIZE/winH; 

		//2. if this square is NOT empty, then return
		if (board[row][col] != BLANK) return;

		//3. update board data
		board[row][col] = turn;  //phenomenally awesome!!!!

		//4. change the turn
		turn *= -1; //cool

		printBoard();
	}

	void drawGraphics() {
		winW = gc.getDrawWidth();
		winH = gc.getDrawHeight();		
		int boxW = winW/SIZE; //this is how big each square is
		int boxH = winH/SIZE;

		synchronized(gc){ 
			gc.clear();

			//Draw grid
			gc.setColor(Color.GRAY);			
			for (int i = 1; i < SIZE; i++) {
				gc.drawLine(0, i * winH/SIZE, winW, i * winH/SIZE);
				gc.drawLine(i * winW/SIZE, 0, i * winW/SIZE, winH);
			}

			//Draw X or O
			gc.setColor(Color.BLUE.darker());
			for(int row = 0; row < SIZE; row++) {
				for (int col = 0; col < SIZE; col++) {
					if(board[row][col] == OO) {
						gc.drawOval(col*boxW, row*boxH, boxW, boxH);
					}
					if(board[row][col] == XX) {
						gc.drawLine(col*boxW, row*boxH, (col+1)*boxW, (row+1)*boxH);
						gc.drawLine((col+1)*boxW, row*boxH, col*boxW, (row+1)*boxH);
					}
				}
			}
		}		
	}

	void printBoard() {
		for(int row=0; row < SIZE; row++) {
			for(int col=0; col < SIZE; col++){
				System.out.printf("%3d", board[row][col]);
			}
			System.out.println();
		}
		//System.out.println("=========="); //print out dividing line of the correct length
		for (int i = 0; i < SIZE*3 +2; i++) System.out.print("=");
		System.out.println();		
	}
	
	int checkWin() {
		// Check rows
		for (int row = 0; row < SIZE; row++) {
			int sum = 0;
			for (int col = 0; col < SIZE; col++) {
				sum += board[row][col];
			}
			if (Math.abs(sum) == SIZE) {
				return (sum > 0) ? XX : OO;
			}
		}

		// Check columns
		for (int col = 0; col < SIZE; col++) {
			int sum = 0;
			for (int row = 0; row < SIZE; row++) {
				sum += board[row][col];
			}
			if (Math.abs(sum) == SIZE) {
				return (sum > 0) ? XX : OO;
			}
		}

		// Check diagonals
		int sum1 = 0;
		int sum2 = 0;
		for (int i = 0; i < SIZE; i++) {
			sum1 += board[i][i];
			sum2 += board[i][SIZE-i-1];
		}
		if (Math.abs(sum1) == SIZE) {
			return (sum1 > 0) ? XX : OO;
		}
		if (Math.abs(sum2) == SIZE) {
			return (sum2 > 0) ? XX : OO;
		}

		return BLANK;
	}

	boolean checkTie() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] == BLANK) {
					return false;
				}
			}
		}
		return true;
	}
}
