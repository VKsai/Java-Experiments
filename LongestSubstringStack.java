import java.util.*;

public class LongestSubstringStack
{
	public static int lengthOfLongestSubstring(String s)
	{	
		Stack<Character> stk = new Stack<Character>();
		Hashtable<Integer, Character> ht = new Hashtable<Integer, Character>();
		
		int start = 0;
		int end = s.length();
		int minus = 0;
		stk.push(s.charAt(start));
		start = start + 1;
		while(start <= end-1 && !stk.isEmpty())
		{	
			if(stk.contains(s.charAt(start)))
			{
				ht.put(start, s.charAt(start));
				minus++;
			}
			if(stk.peek() != s.charAt(start) && !stk.contains(s.charAt(start)))
			{
				
				stk.push(s.charAt(start));
				System.out.println(stk);
				start++;
			}
			else
			{
				start++;
			}
		}
		System.out.println(ht);
		return (stk.size());
	}
	public static void main(String[] args)
	{
		String str = "pwwkew";
		System.out.println("The string is: " +str);
		
		int length = lengthOfLongestSubstring(str);
		System.out.println("Max sub-string is of: " +length);
	}
}