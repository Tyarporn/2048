import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class _2048
{
	private final int rows = 4;//amt of rows
	private final int cols = 4;//amt of cols
	private int[][] board;//board
	private int[][] previousBoard;//prev board;
	private int score;//score
	private int previousScore;//prev score;
	
	/**
	 * Initializes board and previousBoard using rows and cols.
	 * Uses the generateTile method to add two random tiles to board.
	 */
	public _2048()
	{
		board = new int[rows][cols];//initializes new board with length row and height cols
		previousBoard = new int[rows][cols];//initializes new previous board with same dimensions as board

		generateTile();//generates a starting tile

		score = 0;//score is 0
		previousScore = 0;//prev score is 0
	}
	
	/**
	 * Initializes the board of this object using the specified board.
	 * Initializes previousBoard using rows and cols.
	 * 
	 * Precondition: the specified board is a 4x4 2D Array.
	 * 
	 * @param board
	 */
	public _2048(int[][] board)
	{
		this.board=board;//board is th board provided
		previousBoard = new int[rows][cols];//previous board is starting board

		generateTile();//generates tile

		score = 0;//score is 0
		previousScore = 0;//score is 0
	}
	
	/**
	 * Generates a tile and add it to an empty spot on the board.
	 * 80% chance to generate a 2
	 * 20% chance to generate a 4
	 * 
	 * Does nothing if the board is full.
	 */
	private void generateTile()
	{
		if(!full())//if board not full
		{
			int chance = (int) (Math.random() * 100);//chance out of 100
			int randrow = (int) (Math.random() * board.length);//random row
			int randcol = (int) (Math.random() * board[0].length);//random col

			while (board[randrow][randcol] != 0)//while loop keeps shuffling row and col until it finds an empty spot
			{
				randrow = (int) (Math.random() * board.length);
				randcol = (int) (Math.random() * board[0].length);
			}

			if (chance < 20)//if chance is below 20% then the tile will be a 4 else it will be a 2
				board[randrow][randcol] = 4;
			else
				board[randrow][randcol] = 2;
		}
	}
	
	/**
	 * Returns false if the board contains a 0, true otherwise.
	 * @return
	 */
	private boolean full()
	{
		for(int r = 0; r < board.length; r ++)//as long as no ints in board are 0 then the board is full
		{
			for (int c = 0; c < board[r].length; c++)
			{
				if(board[r][c] == 0)//checks if element is 0
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns the board.
	 * @return
	 */
	public int[][] getBoard()
	{
		return board;
	}//returns board
	
	/**
	 * Returns the score.
	 * @return
	 */
	public int getScore()
	{
		return score;
	}//returns score
	
	/**
	 * Saves board into previousBoard and score into previousScore
	 * then performs a move based on the specified direction:
	 * 
	 * Valid directions (not case sensitive):
	 *  up
	 *  down
	 *  left
	 *  right
	 *  
	 * Adds a new tile to the board using the generateTile method.
	 * 
	 * @param direction
	 */
	public void move(String direction)
	{
		for (int r = 0; r < board.length; r++)//saves the board into previous board
		{
			for (int c = 0; c <board[r].length ; c++)
			{
				previousBoard[r][c] = board[r][c];//previous board is saved
			}
		}
		previousScore=score;// score saved into previous score

		if(direction == "up")
		{
			moveUp();//if up is entered then it moves up
		}
		else if (direction == "down")
		{
			moveDown();//if down is entered then it moves down
		}
		else if(direction == "left")
		{
			moveLeft();//if left is entered then it moves left
		}

		else if (direction == "right")
		{
			moveRight();//if right is entered then it moves right
		}

		generateTile();//generates random tile
	}
	
	/**
	 * Shifts all the tiles up, combines like tiles that collide.
	 */
	private void moveUp()
	{
		for (int c = 0; c < board[0].length ; c++)
		{
			ArrayList<Integer> column = new ArrayList<>(board[0].length);//arraylist of the column

			for (int r = 0; r < board.length; r++)
			{
				if(board[r][c] != 0)//as long as the space isnt empty, it is added to column
					column.add(board[r][c]);
			}

			for (int i = 0; i < column.size()-1; i++)//loops through columns except last element
			{
				if(column.get(i).equals(column.get(i +1)))//checks if the element at i is equal to the next element
				{
					int sum = column.get(i) *2;//sum to add to score is the number at i added to itself (i*2)
					column.set(i, column.get(i) + column.get(i+1));//replaces i with the numbers added together
					column.remove(i +1);//removes copy of i that was added
					score += sum;//adds the sum to score
				}
			}
			while(column.size() < board.length)//adds zeros until the arraylist is as big as the column length
				column.add(0);

			for (int r = 0; r < board.length ; r++)//adds the elements back into the board
			{
				board[r][c] = column.get(r);
			}


		}


	}
	
	/**
	 * Shifts all the tiles down, combines like tiles that collide.
	 */
	private void moveDown()
	{
		for (int c = 0; c < board[0].length ; c++)
		{
			ArrayList<Integer> column = new ArrayList<>(board[0].length);//arraylist of the column

			for (int r = 0; r < board.length; r++)//as long as the space isnt empty, it is added to column
			{
				if(board[r][c] != 0)
					column.add(board[r][c]);
			}
			Collections.reverse(column);//reverses the arraylist

			for (int i = 0; i < column.size()-1; i++)//loops through columns except last element
			{
				if(column.get(i).equals(column.get(i +1)))//checks if the element at i is equal to the next element
				{
					int sum = column.get(i) *2;//sum to add to score is the number at i added to itself (i*2)
					column.set(i, column.get(i) + column.get(i+1));//replaces i with the numbers added together
					column.remove(i +1);//removes copy of i that was added
					score += sum;//adds the sum to score
				}
			}
			while(column.size() < board.length)//adds zeros until the arraylist is as big as the column length
				column.add(0);

			Collections.reverse(column);//reverses the arraylist

			for (int r = 0; r < board.length ; r++)//adds the elements back into the board
			{
				board[r][c] = column.get(r);
			}
		}
	}
	
	/**
	 * Shifts all the tiles left, combines like tiles that collide.
	 */
	private void moveLeft()
	{
		for (int r = 0; r < board.length ; r++)
		{
			ArrayList<Integer> row = new ArrayList<>(board.length);//creates an arraylist of rows

			for (int c = 0; c < board[r].length; c++)//adds the row as long as its not an empty space
			{
				if(board[r][c] != 0)//checks to see if element is an empty space
					row.add(board[r][c]);
			}


			for (int i = 0; i < row.size()-1; i++)//loops through row except last element
			{
				if(row.get(i).equals(row.get(i +1)))//checks if element is equal to next element
				{
					int sum = row.get(i) *2;//sum to add to score is the number at i added to itself (i*2)
					row.set(i, row.get(i) + row.get(i+1));//replaces i with the numbers added together
					row.remove(i +1);//removes copy of i that was added
					score += sum;//adds the sum to score
				}
			}
			while(row.size() < board.length)//adds zeros until the arraylist is as big as the row length
				row.add(0);

			for (int c = 0; c < cols ; c++)//adds the elements back into the board
			{
				board[r][c] = row.get(c);
			}
		}
	}
	
	/**
	 * Shifts all the tiles right, combines like tiles that collide.
	 */
	private void moveRight()
	{
		for (int r = 0; r < board.length ; r++)
		{
			ArrayList<Integer> row = new ArrayList<>(board.length);//creates an arraylist of rows

			for (int c = 0; c < board[r].length; c++)//adds the row as long as its not an empty space
			{
				if(board[r][c] != 0)//checks to see if element is an empty space
					row.add(board[r][c]);
			}

			Collections.reverse(row);//reverses the arraylist

			for (int i = 0; i < row.size()-1; i++)//loops through row except last element
			{
				if(row.get(i).equals(row.get(i +1)))//checks if element is equal to next element
				{
					int sum = row.get(i) *2;//sum to add to score is the number at i added to itself (i*2)
					row.set(i, row.get(i) + row.get(i+1));//replaces i with the numbers added together
					row.remove(i +1);//removes copy of i that was added
					score += sum;//adds the sum to score
				}
			}
			while(row.size() < board.length)//adds zeros until the arraylist is as big as the row length
				row.add(0);

			Collections.reverse(row);//reverses the arraylist


			for (int c = 0; c < cols ; c++)//adds the elements back into the board
			{
				board[r][c] = row.get(c);
			}
		}
	}
	
	/**
	 * Sets board to previousBoard and score to previousScore
	 */
	public void undo()
	{
		board=previousBoard;//reloads the previous board
		score = previousScore;//reloads the previous score
	}
	
	/**
	 * Returns true if the game is over, false otherwise.
	 * @return
	 */
	public boolean gameOver()
	{
		boolean x = false;//boolean to determine if the game is over
		if (full())//while the board isnt full
		{
			for (int r = 0; r < board.length-1; r++)//loops through all elements in row major order
			{
				for (int c = 0; c < board[r].length-1; c++)
				{
					if(board[r][c]==board[r+1][c] || board[r][c] == board[r][c+1])//checks if the element is the same as the next element in all directions
						return x;//if so the same is not over
				}

			}
			return !x;//the game is over
		}
		return x;//the game is not over
	}
	
	/**
	 * Returns a String representation of this object.
	 */
	public String toString()
	{
		String rtn = "";
		
		for(int[] row : board)
		{
			rtn += "|";
			for(int num : row)
				if(num != 0)
				{
					String str = num + "";
					while(str.length() < 4)
						str = " " + str;
					rtn += str;
					
				}
				else
					rtn += "    ";
			rtn += "|\n";
		}
		
		rtn += "Score: " + score + "\n";
		
		return rtn;
	}
}
