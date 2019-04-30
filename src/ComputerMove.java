public class ComputerMove
{
	private int priority = 0;
	private int col = -1, row = -1;
	private int moveType = 0;
	
	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int col)
	{
		this.col = col;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int row)
	{
		this.row = row;
	}

	public int getMoveType()
	{
		return moveType;
	}

	public void setMoveType(int moveType)
	{
		this.moveType = moveType;
	}
}