import java.util.Scanner;
public class lab1{
	public static void main(String[] args) {
		Point3d a, b, c;
		a=input();
		b=input();
        c=input();
		if(a.sravn(b) || a.sravn(c) || b.sravn(c))
		{
			System.out.println("The points are equal");
		}
		else
			System.out.println("S= "+ computeArea(a, b, c));
	}
	//ввод координат точек
	public static Point3d input()
	{
		Scanner vvod=new Scanner(System.in);
		String v=vvod.nextLine(); // Ввод чисел
		String[] s=v.split(" ");
		double a=Double.parseDouble(s[0]);
		double b=Double.parseDouble(s[1]);
		double c=Double.parseDouble(s[2]);
		return new Point3d(a, b, c);
	}
	//

    //вычисление площади треугольника
	public static double computeArea(Point3d a, Point3d b, Point3d c)
	{
		double x=a.distanceTo(b);//расстояние от a до b
		double y=a.distanceTo(c);//расстояние от a до c
		double z=b.distanceTo(c);//расстояние от b до c
		double per=(x+y+z)/2;
		return Math.sqrt(per*(per-x)*(per-y)*(per-z));
	}
	//

}