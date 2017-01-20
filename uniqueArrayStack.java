import java.util.*;

class uniqueArrayStack
{
	public static int removeDuplicates(int[] nums)
	{		

		Stack<Integer> st = new Stack<>();
        
        
        		for(int i = 0; i < nums.length; i++)
        		{
          			  if(!st.contains(nums[i]))
            			{
               			 st.push(nums[i]);
            			}
       		 }
        
        
        		return st.size();
	}

	public static void main(String[] args)
	{
		int[] arr = {1, 2, 2, 1, 4, 5, 5, 9, 10, 10, 10, 11};
		int res = removeDuplicates(arr);
		System.out.println(res);
	}
}