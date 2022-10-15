package task1;

import java.util.ArrayList;
import java.util.Scanner; 

public class Task1 {
    
  public static int mod(int a, int m) {
     if (a >= 0) return a % m;
     else return a % m + m;
  }

  public static int gcd(int a, int b) {
    while (b !=0) {
       int tmp = a % b;
       a = b;
       b = tmp;
    }
    return Math.abs(a);
  }
  
  public static int bin_pow_by_mod(int a, int b, int m) {
      if (b == 0) return 1;     
      if (b % 2 == 0) {
        return mod(mod(bin_pow_by_mod(a, b/2, m), m) * mod(bin_pow_by_mod(a, b/2, m), m), m);} 
    else {
        return mod(a * mod(bin_pow_by_mod(a, b-1, m), m), m);
    }
  }
  
  public static int euler_func(int a) {
    int m = a;
    int result = a;
    for (int p = 2; p <= Math.sqrt(a); p++) {
      if (m % p == 0) result -= result / p; 
      while (m % p == 0) 
         m /= p;
    }
    if (m > 1)
        result --;
    return result;
  }
  
  public static int reverse_by_mod(int a, int m) {
     return bin_pow_by_mod(a, euler_func(m) - 1, m); 
  }
  
  public static int solution_to_mod_eq(int a, int b, int m) {
      return mod(b * reverse_by_mod(a, m), m);
  }
  
  public static boolean is_coprime(int a, int b) {
      if (gcd(a, b) == 1) {return true;} else
            {return false;}
  }
  
  public static boolean[] sieve(int n) {
    boolean sieve[] = new boolean[n+1];
    int i, j;
    for (i = 2; i <= n; i++) {sieve[i] = true;}
    for (i = 2; i*i <= n; i++) {
        j = i*i;        
        if (sieve[i] == true) {
           while (j <= n) {
               sieve[j] = false;              
               j += i;
           }
        }
    }
    sieve[0] = false;
    sieve[1] = false;
    return sieve;
  } 
  
  public static ArrayList<Integer> primes(int a, int b) {
    ArrayList<Integer> primes = new ArrayList<>();
    boolean s[] = new boolean[b+1];
    s = sieve(b);
    for (int i = a; i <= b; i++) {
        if (s[i] == true) primes.add(i);
    }
    return primes;    
  }
  
  public static int rndm(int n) {
    int res = (int)(Math.random() * (n + 1));
    return res;
  }
  
  public static int rndm_prime(int a, int b) {
     int rndm_ind = rndm(primes(a, b).size()); 
     return primes(a, b).get(rndm_ind);
  }
  
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter a: ");
    int a = sc.nextInt();
    System.out.println("Enter b: ");
    int b = sc.nextInt();
   
    System.out.println(primes(a, b));
    System.out.println(rndm_prime(a, b));
 }
}  
