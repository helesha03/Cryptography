package task1;

import java.util.Scanner;
import static task1.Task1.mod;

public class Task1_1_Main {
    public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter integer a: ");
      int a = sc.nextInt();
      System.out.print("Enter integer m: ");
      int m = sc.nextInt();
      int x = mod(a, m);
      System.out.printf("a mod m = x => x = %d mod %d = %d\n", a, m, x);
    }
}
