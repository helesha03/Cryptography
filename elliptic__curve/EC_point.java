package elliptic__curve;

import java.math.BigInteger;
import java.util.Random;

public class EC_point {
    public BigInteger x;
    public BigInteger y; 
    public static final BigInteger a = new BigInteger("0", 16);
    public static final BigInteger b = new BigInteger("7", 16);
    public static final BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);
    public static final BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141", 16);
    public static final BigInteger G_x = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798", 16);
    public static final BigInteger G_y = new BigInteger("483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8", 16);
    
    public EC_point(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
    
    public EC_point() {
        
    }
    
    public static EC_point ECPointGen(BigInteger x, BigInteger y) {
        EC_point point = new EC_point(x, y); 
        return point;
    }
    
    public EC_point BasePointGen() {
        return ECPointGen(this.G_x, this.G_y);
    }
    
    public Boolean IsOnCurveCheck () {
        BigInteger x = this.x;
        BigInteger y = this.y;
        BigInteger three = new BigInteger("3");
        BigInteger term1 = x.modPow(three, p);
        BigInteger term2 = x.multiply(a).mod(p);
        BigInteger res1 = term1.add(term2).add(b).mod(p);
        BigInteger res2 = y.multiply(y).mod(p);
        if (res1.compareTo(res2) == 0) return true; else return false; 
    }
    
    public static EC_point AddECPoints (EC_point point1, EC_point point2) {
        BigInteger x1 = point1.x;
        BigInteger y1 = point1.y;
        BigInteger x2 = point1.x;
        BigInteger y2 = point1.y;
        
        if ((x1.compareTo(x2) == 0) && (y1.compareTo(y2) == 0)) {
                 return point1.DoubleECPoints();} else 
        {
          BigInteger lambda = new BigInteger("0");
          BigInteger term1 = y2.subtract(y1);
          BigInteger term2 = x2.subtract(x1).modInverse(p);
          lambda = term1.multiply(term2).mod(p);
          BigInteger x3 = new BigInteger("0");
          BigInteger y3 = new BigInteger("0");
          x3 = lambda.multiply(lambda).subtract(x1).subtract(x2).mod(p);
          term1 = x1.subtract(x3);
          y3 = term1.multiply(lambda).subtract(y1).mod(p);
          EC_point point3 = new EC_point(x3, y3);
          return point3; 
        }
    }
    
    public EC_point DoubleECPoints () {
        BigInteger x = this.x;
        BigInteger y = this.y;
        BigInteger three = new BigInteger("3");
        BigInteger term1 = x.multiply(x).multiply(three).add(a).mod(p);
        BigInteger term2 = y.multiply(BigInteger.TWO).modInverse(p);
        BigInteger lambda = term1.multiply(term2).mod(p);
        term1 = lambda.multiply(lambda);
        term2 = x.multiply(BigInteger.TWO);
        BigInteger xx = term1.subtract(term2).mod(p);
        BigInteger yy = x.subtract(xx).multiply(lambda).subtract(y).mod(p);
        EC_point double_point = new EC_point(xx, yy);
        return double_point;
    } 
    
    public EC_point ScalarMult(BigInteger k) {
        k = k.mod(p); 
        if (k.compareTo(BigInteger.ONE) == 0) {
           return this;
       } else {
         String str_k = k.toString(2);
         int len_k = str_k.length();
         EC_point point_res = new EC_point(this.x, this.y); 
         for (int i = 1; i < len_k; i++) {
             if (str_k.charAt(i) == '0') {
                 point_res = point_res.DoubleECPoints();
             } else {
                 point_res = AddECPoints(this, point_res.DoubleECPoints());
             }
         }
         return point_res;
      }  
    }
    
    public String ECpointToString () {
        String str = "(" + this.x.toString() + ", " + this.y.toString() + ")";
        return str;
    } 
    
    public void PrintECPoint() {
        String str = this.ECpointToString();
        System.out.printf("Elliptic curve point: %s\n", str);
    }
    
    
    public BigInteger get_private_key() {
      Random rand = new Random();
      BigInteger result = new BigInteger(n.bitLength(), rand);
      while( result.compareTo(this.n) >= 0 ) {
        result = new BigInteger(n.bitLength(), rand);
      }
      return result;
    }
    
    public static boolean if_equal_points(EC_point point1, EC_point point2) {
        if ((point1.x.compareTo(point2.x) == 0) && (point1.y.compareTo(point2.y) == 0))
           return true;
        else return false;
    }
    
    public EC_point get_public_key(BigInteger private_key) {
       EC_point base_point = this.BasePointGen();
       return base_point.ScalarMult(private_key); 
    }
    
    public static boolean check_by_DH(BigInteger d_a, EC_point H_a, BigInteger d_b, EC_point H_b) {
        EC_point common_secret_1 = H_b.ScalarMult(d_a);
        EC_point common_secret_2 = H_a.ScalarMult(d_b);
        if (if_equal_points(common_secret_1, common_secret_2)) 
            return true;
        else 
            return false;
    }
    
    public static void check_by_DH_result(BigInteger d_a, EC_point H_a, BigInteger d_b, EC_point H_b) {
        if (check_by_DH(d_a, H_a, d_b, H_b)) {
            System.out.println("Everything is OK!");
            EC_point common_secret = H_a.ScalarMult(d_b);
            String str = common_secret.ECpointToString();
            System.out.printf("Common secret is: %s\n",str);
        }
          else
            System.out.println("Smth is wrong with the code");
    }
    
    public static void main(String[] args) {
        EC_point point = new EC_point();
        BigInteger d_a = point.get_private_key();
        EC_point H_a = point.get_public_key(d_a);
        BigInteger d_b = point.get_private_key();
        EC_point H_b = point.get_public_key(d_b);
        System.out.printf("Alice's private key: %s\n", d_a.toString());
        System.out.printf("Alice's public key: %s\n\n", H_a.ECpointToString());
        System.out.printf("Bob's private key: %s\n", d_b.toString());
        System.out.printf("Bob's public key: %s\n\n", H_b.ECpointToString());
        if (check_by_DH(d_a, H_a, d_b, H_b)) {
            System.out.println("Everything is OK!");
            EC_point common_secret = H_a.ScalarMult(d_b);
            String str = common_secret.ECpointToString();
            System.out.printf("Common secret is: %s\n",str);
        }
          else
            System.out.println("Smth is wrong with the code");
    }
    
}
