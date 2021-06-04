public class Palindrome 
{
 public static void main(String[] args) 
 	{
 	 for (int i = 0; i < args.length; i++) //проходим по всем аргументам в командной строке
 		{
 		 String reverse = args[i]; // берем отдельго каждый из них
 		  if (isPalindrome(reverse)==true)
 		  {
 		  	System.out.println(reverse + "   - Palindrome  " ); // Вывод ответов
 		  }
 		  else
 		  	System.out.println (reverse + "  - No Palindrome  "); // Вывод ответов
 		}
 	}
  public static String reverseString(String reverse) 
 	{ 
 	 String b ="";
     for ( int i =reverse.length() - 1; i >= 0; i-- )
       	b = b + reverse.charAt(i);
	 return b;
    }
    
 public static boolean isPalindrome(String reverse)
	{	
 	 String j=reverseString(reverse);
 	 return reverse.equals(j);
	}
}
 