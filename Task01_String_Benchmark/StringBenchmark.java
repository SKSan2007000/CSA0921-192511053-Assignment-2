import java.util.*;
public class StringBenchmark {
 static volatile int sink;
 static String usingString(int tickets,int fields){
  String all="";
  for(int t=0;t<tickets;t++){String ticket="PNR:"+t+"|";for(int f=0;f<fields;f++)ticket += "F"+f+"=VALUE|";all += ticket;}
  return all;
 }
 static String usingBuilder(int tickets,int fields){
  StringBuilder all=new StringBuilder();
  for(int t=0;t<tickets;t++){StringBuilder ticket=new StringBuilder(32+fields*12);ticket.append("PNR:").append(t).append('|');for(int f=0;f<fields;f++)ticket.append('F').append(f).append("=VALUE|");all.append(ticket);}
  return all.toString();
 }
 static String usingBuffer(int tickets,int fields){
  StringBuffer all=new StringBuffer();
  for(int t=0;t<tickets;t++){StringBuffer ticket=new StringBuffer(32+fields*12);ticket.append("PNR:").append(t).append('|');for(int f=0;f<fields;f++)ticket.append('F').append(f).append("=VALUE|");all.append(ticket);}
  return all.toString();
 }
 static long time(java.util.function.Supplier<String> f){long t=System.nanoTime();String s=f.get();sink=s.length();return System.nanoTime()-t;}
 static double avg(long[]a){return Arrays.stream(a).average().orElse(0);}
 static void test(String label,int tickets,int fields,int reps){
  // warm-up on the same workload, then alternate measurement order to reduce order bias
  time(()->usingString(tickets,fields));time(()->usingBuilder(tickets,fields));time(()->usingBuffer(tickets,fields));
  long[]a=new long[reps],b=new long[reps],c=new long[reps];
  for(int i=0;i<reps;i++){a[i]=time(()->usingString(tickets,fields));b[i]=time(()->usingBuilder(tickets,fields));c[i]=time(()->usingBuffer(tickets,fields));}
  System.out.printf(Locale.US,"%s tickets=%d fields=%d String_ms=%.3f Builder_ms=%.3f Buffer_ms=%.3f outputChars=%d estimatedConcatSteps=%d%n",label,tickets,fields,avg(a)/1e6,avg(b)/1e6,avg(c)/1e6,sink,tickets*(fields+1));
 }
 public static void main(String[] args){
  System.out.println("Railway e-ticket workloads; one warm-up and five measured runs; timings are machine-dependent.");
  test("SHORT",1,6,5);
  test("MANY_TICKETS",10000,50,5);
  test("LARGE_TICKET",20,500,5);
  System.out.println("StringBuffer synchronizes append operations; StringBuilder is the single-threaded per-ticket choice.");
  System.out.println("For a shared multithreaded booking log, use a synchronized logging design or a thread-safe logger; do not share one mutable builder unsafely.");
  System.out.println("Intermediate String count is estimated from concatenation steps, not measured by an allocation profiler.");
 }
}
