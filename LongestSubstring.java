import java.util.*;
import java.lang.*;

public class LongestSubstring
{
	static int minus = 0;
    	public static int lengthOfLongestSubstring(String s)
	{
		if(s.length() == 0) return 0;
		
		Hashtable<Integer, Character> ht = new Hashtable<Integer, Character>();
		
		int start = 0;
		int result = subStringCheck(ht, s, start);
		return result;			
    	}

	public static int subStringCheck(Hashtable<Integer, Character> t, String s1, int track)
	{
		System.out.println(s1.length());
		int end = s1.length();
		//while(track <= s1.length())
		//{
			if(t.containsValue(s1.charAt(track)) == false && (track < end-1))
			{
				t.put(track, s1.charAt(track));
				track +=1;
				subStringCheck(t, s1.substring(track), track); 		//Recursive Call
				System.out.println("Track1:" +track);		
			}
			else if(t.containsValue(s1.charAt(track)) == true && (track < end - 1))
			{
				track += 1;
				subStringCheck(t, s1.substring(track), track);
				System.out.println("Track2::" +track);
				minus++;
			}
		//}
		System.out.println("Elements of HashTable are: " +t);
		return (t.size()-minus);
	}

	public static void main(String[] args)
	{
		String str = "abcabcbb";
		System.out.println("The string is: " +str);
		
		int length = lengthOfLongestSubstring(str);
		System.out.println("Max sub-string is of: " +length);
	}
}