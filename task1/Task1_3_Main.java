package task1;

import java.util.Scanner;
import static task1.Task1.gcd;
import static task1.Task1.mod;
import static task1.Task1.solution_to_mod_eq;

public class Task1_3_Main {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter integer a: ");
      int a = sc.nextInt();
      System.out.print("Enter positive integer b: ");
      int b = sc.nextInt();
      System.out.print("Enter integer m: ");
      int m = sc.nextInt();
      int x;
      if (gcd(a, m) != 1) {
          if (b % gcd(a, m) == 0) {x = solution_to_mod_eq(a/gcd(a,m), b/gcd(a,m), m/gcd(a,m));}
            else {
               x = -1; 
               System.out.printf("No solutions to %d*x mod %d = %b\n", a, m, b); 
             }
      } else {
          x = solution_to_mod_eq(a, b, m);
      }
      if (x != -1) {
          if (b >= m){
              System.out.printf("%d*x mod %d = %d <=> %d*x mod %d = %d" 
                            + " => x mod m = %d\n",a,m,b,a, m, mod(b, m), x);
          } else {
              System.out.printf("%d*x mod %d = %d" 
                            + " => x mod m = %d\n",a,m,b, x);
          }      
      }
    }
}

