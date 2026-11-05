import java.util.*;
public class TicketBuilderBenchmark{
 static volatile int sink;
 static String baseline(int n){String s="E-TICKET|";for(int i=0;i<n;i++)s+="ITEM:"+i+";";return s;}
 static String redesigned(int n){StringBuilder b=new StringBuilder(16+n*12);b.append("E-TICKET|");for(int i=0;i<n;i++)b.append("ITEM:").append(i).append(';');return b.toString();}
 static long measure(java.util.function.Supplier<String> f){long t=System.nanoTime();sink=f.get().length();return System.nanoTime()-t;}
 static double avg(long[] a){return Arrays.stream(a).average().orElse(0);}
 public static void main(String[]x){int n=5000,reps=7;for(int i=0;i<3;i++){measure(()->baseline(n));measure(()->redesigned(n));}
 long[] a=new long[reps],b=new long[reps];for(int i=0;i<reps;i++){a[i]=measure(()->baseline(n));b[i]=measure(()->redesigned(n));}
 System.out.printf(Locale.US,"Baseline immutable concatenation avg_ms=%.3f%n",avg(a)/1e6);
 System.out.printf(Locale.US,"Redesign pre-sized StringBuilder avg_ms=%.3f%n",avg(b)/1e6);
 System.out.printf(Locale.US,"Improvement ratio=%.2fx (environment dependent)%n",avg(a)/avg(b));
 System.out.println("Objective: reduce repeated intermediate String allocation while preserving exact ticket text.");
 }
}
