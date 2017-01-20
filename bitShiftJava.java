import java.util.*;

class bitShiftJava
{
	public static int rangeBitwiseAnd(int m, int n)
	{
        		int First, Second;
		Stack<Integer> resultStack = new Stack<>();
		public int resultEven, resultOdd;
		
		if(m <= n)
		{
			rangeBitwiseAnd(m+2, n);
		
			First = Integer.parseInt(Integer.toBinaryString(m)); 
			Second = Integer.parseInt(Integer.toBinaryString(m+1));
			if(m != n)
			{
				resultStack.push(First & Second);
			}
			else
			{
				resultStack.push(First);		//Body for ODD range
			}		
		}
		
		while(!resultStack.isEmpty())
		{
			int top = resultStack.pop();

			if(resultStack.peek() != null)
			{
				int next = resultStack.pop();	
				resultEven = top & next;
			}
			else
			{
				resultOdd = resultEven & top;
				 
			}
		}
		
		if((resultStack.size()) % 2  == 0)
		{
			return resultEven;
		}
		else
		{
			return resultOdd;
		}
    	}	

	public static void main(String[] args)
	{
		int i, j;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Start: ");
		i = sc.nextInt();
		System.out.println("Enter End: ");
		j = sc.nextInt();
		if(i >=0 && j <= Integer.MAX_VALUE && i < j)
		{
			int result = rangeBitwiseAnd(i, j);
			System.out.println(result);
		}
		else
		{
			System.out.println("Range not correct");
		}
	}
}