package elliptic_curve;

import java.math.BigInteger;

public class ModuleAlgebra {
    
    public static BigInteger mod_BigInt(BigInteger a, BigInteger m) {
       if (a.compareTo(BigInteger.ZERO) != -1) return a.mod(m);
           else return a.mod(m).add(m);
    }
    
    public static BigInteger multiply_by_mod_BigInt(BigInteger a, BigInteger b, BigInteger m) {
        BigInteger res = a.mod(m).multiply(b.mod(m));
        return res.mod(m);
    }
    
     public static BigInteger gcd_BigInt(BigInteger a, BigInteger b) {
       BigInteger tmp = new BigInteger("0");
        while (b.compareTo(BigInteger.ZERO) != 0) {
           tmp = a.mod(b);
           a = b;
           b = tmp;
       }     
      return a.abs();
    }
    
    public static BigInteger bin_pow_by_mod_BigInt(BigInteger a, BigInteger b, BigInteger m) {  
       if (b.compareTo(BigInteger.ZERO) == 0) return BigInteger.ONE;
       BigInteger two = new BigInteger("2");
       if (b.mod(two).compareTo(BigInteger.ZERO) == 0) {
           return mod_BigInt(mod_BigInt(bin_pow_by_mod_BigInt(a, b.divide(two), m), m).pow(2), m);
       } else {
           return mod_BigInt(a.multiply(mod_BigInt(bin_pow_by_mod_BigInt(a, b.subtract(BigInteger.ONE), m), m)), m);
       }
    }
    public static BigInteger euler_func_BigInt(BigInteger a) {
      BigInteger m = new BigInteger("1");
      m = a;  
      BigInteger result = new BigInteger("1");
      result = a;
      BigInteger p = new BigInteger("2");
      while (1 < 2) {
          if (p.pow(2).compareTo(a) != 1) {
               if (m.mod(p) == BigInteger.ZERO) result = result.subtract(result.divide(p));   
               while (m.mod(p).compareTo(BigInteger.ZERO) == 0)
                   m = m.divide(p);
               p = p.add(BigInteger.ONE);
          } else break;
      }
      
      if (m.compareTo(BigInteger.ONE) == 1)
          result = result.subtract(result.divide(m));
      return result;
    }  

    public static BigInteger reverse_by_mod_BigInt(BigInteger a, BigInteger m) {
       return bin_pow_by_mod_BigInt(a, euler_func_BigInt(m).subtract(BigInteger.ONE), m); 
    }
}
