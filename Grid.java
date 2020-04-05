package sudoku;

import java.util.*;

public class Grid {
private int[][]	values;
	
	//
	// DON'T CHANGE THIS.
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	//
	// DON'T CHANGE THIS.
	//
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}


	//
	// DON'T CHANGE THIS.
	// Copy ctor. Duplicates its source. You’ll call this 9 times in next9Grids.
	//
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
	//
	//Finds an empty member of values[][]. Returns an array list of 9 grids that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the current grid is full. Don’t change
	// “this” grid. Build 9 new grids.
	// 
	//
	// Example: if this grid = 1........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//                         .........
	//
	// Then the returned array list would contain:
	//
	// 11.......          12.......          13.......          14.......    and so on     19.......
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	// .........          .........          .........          .........                  .........
	//
	public ArrayList<Grid> next9Grids()
	{		
		if(!this.isFull())
		{
			int xOfNextEmptyCell = 0;
			int yOfNextEmptyCell = 0;

			// Find x,y of an empty cell.
			for(int i = 0; i < 9; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					if(values[i][j] == 0)
					{
						xOfNextEmptyCell = i;
						yOfNextEmptyCell = j;
					}
				}
			}

			// Construct array list to contain 9 new grids.
			ArrayList<Grid> grids = new ArrayList<Grid>();

			// Create 9 new grids as described in the comments above. Add them to grids.
			for(int grid = 1; grid <= 9; grid++)
			{
				Grid newGrid = new Grid(this);
				newGrid.values[xOfNextEmptyCell][yOfNextEmptyCell] = grid;
				grids.add(newGrid);
			}
			return grids;
		}
		return null;
	}
	
	
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{
		return rowIsLegal() && columnIsLegal() && blockIsLegal();
	}
	
	// Check every row. If you find an illegal row that contains a repeated 
	// 1, 2, 3, 4, 5, 6, 7, 8, or 9, return false.
	private boolean rowIsLegal()
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				int test = values[i][j];
				for(int k = j + 1; k < 9; k++)
				{
					int value = values[i][k];
					if(value == test && value != 0)
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	// Check every column. If you find an illegal column that contains a repeated 
	// 1, 2, 3, 4, 5, 6, 7, 8, or 9, return false.
	private boolean columnIsLegal()
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				int test = values[j][i];
				for(int k = j + 1; k < 9; k++)
				{
					int value = values[k][i];
					if(value == test && value != 0)
						return false;
				}
			}
		}
		return true;
	}
	
	// Check every block. If you find an illegal block that contains a repeated 
	// 1, 2, 3, 4, 5, 6, 7, 8, or 9, return false.
	public boolean blockIsLegal()
	{
		for(int i = 0; i < 7; i+= 3)
		{
			for(int j = 0; j < 7; j+= 3)
			{
				ArrayList<Integer> list = new ArrayList<>();
				for(int k = i; k < i + 3; k++)
				{
					for(int l = j; l < j + 3; l++)
					{
						if(values[k][l] != 0 && list.contains(values[k][l]))
							return false;
						list.add(values[k][l]);
					}
				}
			}
		}
		return true;
	}

	
	//
	// Returns true if every cell member of values[][] is a digit from 1-9.
	//
	public boolean isFull()
	{
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(values[i][j] == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	

	//
	// Returns true if x is a Grid and, for every (i,j), 
	// x.values[i][j] == this.values[i][j].
	//
	public boolean equals(Object x)
	{
		
		if(x instanceof Grid)
		{
			Grid that = (Grid) x;
			for(int i = 0; i < 9; i++)
			{
				for(int j = 0; j < 9; j++)
				{
					if(that.values[i][j] != this.values[i][j])
						return false;
				}
			}
			return true;
		}
		return false;
	}
}
