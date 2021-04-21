public class Primes
{
	public static void main(String[] args) 
	{
		for (int s=3;s<100;s++) 
		{
			if (isPrime(s)) 
			{
				System.out.println(s);
			}		
		}
	}
	 public static boolean isPrime(int n) 
	{
	 	for (int s=2; s<n; s++) 
	 	{
	 		if (n%s==0) 
	 		{
	 			return false;
	 		}
	 	}
	 	return true;
	}
}