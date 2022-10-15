package task1;

import java.util.Scanner;
import static task1.Task1.bin_pow_by_mod;
import static task1.Task1.mod;

public class Task1_2_Main {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter integer a: ");
      int a = sc.nextInt();
      System.out.print("Enter positive integer b: ");
      int b = sc.nextInt();
      System.out.print("Enter integer m: ");
      int m = sc.nextInt();
      int x = mod(bin_pow_by_mod(a, b, m), m);
      System.out.printf("a^b mod m = x => x = %d^%d mod %d = %d\n", a,b, m, x);
    }
}
