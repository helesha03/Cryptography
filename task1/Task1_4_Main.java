package task1;

import java.util.Scanner;
import static task1.Task1.primes;
import static task1.Task1.rndm_prime;


public class Task1_4_Main {
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter a: ");
      int a = sc.nextInt();
      System.out.print("Enter b: ");
      int b = sc.nextInt();
      if (a > b) {
          int tmp = a;
          a = b;
          b = tmp;
      }
      System.out.printf("Range: from %d to %d\n", a, b);
      System.out.println("List of prime numbers in range:");
      if (a < 0) {
        if (b > 1) {  
          System.out.println(primes(1, b));
          System.out.printf("Random prime from range: %d\n", rndm_prime(1, b));
        } else {System.out.println("No prime numbers in range");}
      } else { 
          System.out.println(primes(a, b)); 
          System.out.printf("Random prime from range: %d\n", rndm_prime(a, b));
      }
    } 
}
