public class ComputerMove implements Comparable<Object>
{
	private int priority = 0;
	private int col = -1, row = -1;
	
	public int getPriority()
	{
		return priority;
	}
	
	public Integer getPriorityInt()
	{
		return new Integer(priority);
	}

	public void setPriority(int p)
	{
		priority = p;
	}

	public int getCol()
	{
		return col;
	}

	public void setCol(int c)
	{
		col = c;
	}

	public int getRow()
	{
		return row;
	}

	public void setRow(int r)
	{
		row = r;
	}
	public String toString()
	{
		return "Move at: " + col + ", " + row + ", " + priority;
	}

	@Override
	public int compareTo(Object o)
	{
		int comparePriority = ((ComputerMove)o).getPriority();
		return comparePriority-this.priority;
	}
}