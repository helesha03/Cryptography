package elliptic_curve;

import java.math.BigInteger;
import java.util.Random;

public class EC_points{
    public BigInteger x;
    public BigInteger y; 
    public static final BigInteger a = new BigInteger("000000000000000000000000000000000000000000000000", 16);
    public static final BigInteger b = new BigInteger("000000000000000000000000000000000000000000000003", 16);
    public static final BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFEE37", 16);
    public static final BigInteger N = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFE26F2FC170F69466A74DEFD8D", 16);
    
    
    public EC_points(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
    
    public EC_points() {
        
    }
    
    public EC_points ECPointGen(BigInteger x, BigInteger y) {
        EC_points point = new EC_points(x, y); 
        return point;
    }
    
    public EC_points BasePointGGet () {
       BigInteger q = N.divide(BigInteger.TWO);
       BigInteger n = new BigInteger("2");
       while (q.compareTo(BigInteger.ONE) != -1) {
           System.out.println("q = " + q);
           if (N.mod(q).compareTo(BigInteger.ZERO) == 0) {
              if (q.isProbablePrime(1000)) {
                  n = q; 
                  //System.out.println("n = " + n);         
                  break;
              }        
           }  else {
                  q = q.subtract(BigInteger.ONE);
              } 
       }
       BigInteger h = N.divide(n);
       int l = 1;
       BigInteger y_p = new BigInteger("1");
       Random rand = new Random();
       l = (int) (Math.random() * (p.toString().length()) + 1);
       BigInteger x_p = new BigInteger(l, rand);
       BigInteger term1 = x_p.pow(3);  
       BigInteger term2 = x_p.multiply(a).add(b);
       y_p = term1.add(term2).mod(p).sqrt();
       EC_points point_p = new EC_points(x_p, y_p);
       BigInteger x_base = ScalarMult(point_p, h).x;
       BigInteger y_base = ScalarMult(point_p, h).y;
       EC_points base_point = new EC_points(x_base, y_base);
       EC_points check = AddECPoints(point_p, base_point);
       if ((check.x.compareTo(x_p) == 0) && (check.y.compareTo(y_p) == 0)) {
             while (true) {   
                l = (int) (Math.random() * (p.toString().length()) + 1);
                x_p =   new BigInteger(l, rand);
                term1 = x_p.pow(3);
                term2 = x_p.multiply(a).add(b);
                y_p = term1.add(term2).mod(p).sqrt();
                System.out.println("x_p = "+ x_p + ", y_p = " + y_p);
                check = AddECPoints(ECPointGen(x_p, y_p), ScalarMult(ECPointGen(x_p, y_p), h));
                if ((check.x.compareTo(x_p) != 0) || (check.y.compareTo(y_p) != 0)) break;
             } 
          return ScalarMult(ECPointGen(x_p, y_p), h); 
       } else return base_point;
    } 
    
    public static Boolean IsOnCurveCheck (EC_points point) {
        BigInteger x = point.x;
        BigInteger y = point.y;
        BigInteger three = new BigInteger("3");
        BigInteger term1 = x.modPow(three, p);
        BigInteger term2 = x.multiply(a).mod(p);
        BigInteger res1 = term1.add(term2).add(b).mod(p);
        BigInteger res2 = y.multiply(y).mod(p);
        if (res1.compareTo(res2) == 0) return true; else return false; 
    }
    
    public EC_points AddECPoints (EC_points point1, EC_points point2) {
        BigInteger x1 = point1.x;
        BigInteger y1 = point1.y;
        BigInteger x2 = point1.x;
        BigInteger y2 = point1.y;
        
        if ((x1.compareTo(x2) == 0) && (y1.compareTo(y2) == 0)) {
                 return DoubleECPoints(point1);} else 
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
          EC_points point3 = new EC_points(x3, y3);
          return point3; 
        }
    }
    
    public EC_points DoubleECPoints (EC_points point) {
        BigInteger x = point.x;
        BigInteger y = point.y;
        BigInteger three = new BigInteger("3");
        BigInteger term1 = x.multiply(x).multiply(three).add(a).mod(p);
        BigInteger term2 = y.multiply(BigInteger.TWO).modInverse(p);
        BigInteger lambda = term1.multiply(term2).mod(p);
        term1 = lambda.multiply(lambda);
        term2 = x.multiply(BigInteger.TWO);
        BigInteger xx = term1.subtract(term2).mod(p);
        BigInteger yy = x.subtract(xx).multiply(lambda).subtract(y).mod(p);
        EC_points double_point = new EC_points(xx, yy);
        return double_point;
    } 
    
    public EC_points ScalarMult(EC_points point, BigInteger k) {
        k = k.mod(p); 
        if (k.compareTo(BigInteger.ONE) == 0) {
           return point;
       } else {
         String str_k = k.toString(2);
         int len_k = str_k.length();
         EC_points point_res = new EC_points(point.x, point.y); 
         for (int i = 1; i < len_k; i++) {
             if (str_k.charAt(i) == '0') {
                 point_res = DoubleECPoints(point_res);
             } else {
                 point_res = AddECPoints(point, DoubleECPoints(point_res));
             }
         }
         return point_res;
      }  
    }
    
    public String ECpointToString (EC_points point) {
        String str = "(" + point.x.toString() + ", " + point.y.toString() + ")";
        return str;
    } 
    
    public void PrintECPoint(EC_points point) {
        String str = ECpointToString(point);
        System.out.printf("Elliptic curve point: %s\n", str);
    }
    
    
    
    public static void main(String[] args) {
    
        
        // Генеруємо довільну точку
        int bits = (int) (Math.random()*100+10);
        Random rand = new Random();
        BigInteger x = new BigInteger(bits, rand);
        bits = (int) (Math.random()*100+10);
        BigInteger y = new BigInteger(bits, rand);
        EC_points test_point = new EC_points(x, y);
        
        // Перевіримо чи належить згенерована точка еліптичній кривій  
        if (IsOnCurveCheck(test_point) == true) {
           test_point.PrintECPoint(test_point); 
        } else {
            //String str = ECpointToString (EC_points point);
            String str = test_point.ECpointToString(test_point);
            System.out.println("Point" + str + " is not on curve");
        }
        
        // Отримуємо базову точку 
        EC_points base_point = new EC_points();
        base_point = base_point.BasePointGGet();
        base_point.PrintECPoint(base_point);
        
        // Генеруємо точку, що породжується згенерованою базовою точкою 
        bits = (int) (Math.random()*100+10);
        BigInteger k = new BigInteger(bits, rand);
        test_point = test_point.ScalarMult(base_point, k);
        test_point.PrintECPoint(test_point);
        
        // Перевіряємо чи отримана точка належить еліптичній кривій
        if (IsOnCurveCheck(test_point)) {
            String str = test_point.ECpointToString(test_point);
            System.out.println("Point" + str + "is on curve");
        } else {
            String str = test_point.ECpointToString(test_point);
            System.out.println("Point" + str + "is not on curve");
        }
        
        // Виводимо подвоєну точку та перевіряємо, що вона належить кривій
        test_point.PrintECPoint(test_point.DoubleECPoints(test_point));
        if (IsOnCurveCheck(test_point.DoubleECPoints(test_point))) {
            String str = test_point.ECpointToString(test_point.DoubleECPoints(test_point));
            System.out.println("Point" + str + "is on curve");
        }
        
        // Генеруємо ще одну точку, породжену базовою точкою
        bits = (int) (Math.random()*100+10);
        BigInteger k2 = new BigInteger(bits, rand);
        EC_points test_point2 = new EC_points();
        test_point2 = test_point.ScalarMult(base_point, k2);
        test_point2.PrintECPoint(test_point2);
        
        // Просумуємо дві вже згенеровані точки 
        System.out.print("Sum of two points: ");
        test_point.PrintECPoint(test_point.AddECPoints(test_point, test_point2));
        if (IsOnCurveCheck(test_point.AddECPoints(test_point, test_point2))) {
            String str = test_point.ECpointToString(test_point.AddECPoints(test_point, test_point2));
            System.out.println("Point" + str + "is on curve");
        } else {
            String str = test_point.ECpointToString(test_point.AddECPoints(test_point, test_point2));
            System.out.println("Point" + str + "is not on curve");
        }
        
        // Генеруємо ще одну випадкову точку
        bits = (int) (Math.random()*100+10);
        BigInteger x2 = new BigInteger(bits, rand);
        bits = (int) (Math.random()*100+10);
        BigInteger y2 = new BigInteger(bits, rand);
        EC_points test_point1 = new EC_points(x, y);
        
        // Сума двох точок
        System.out.print("Sum of two points: ");
        test_point.PrintECPoint(test_point.AddECPoints(test_point1, test_point2));
        if (IsOnCurveCheck(test_point.AddECPoints(test_point1, test_point2))) {
            String str = test_point.ECpointToString(test_point.AddECPoints(test_point1, test_point2));
            System.out.println("Point" + str + "is on curve");
        } else {
            String str = test_point.ECpointToString(test_point.AddECPoints(test_point1, test_point2));
            System.out.println("Point" + str + "is not on curve");
        }
    }    
}
