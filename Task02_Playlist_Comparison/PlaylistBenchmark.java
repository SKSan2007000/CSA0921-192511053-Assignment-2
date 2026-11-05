import java.util.*;
public class PlaylistBenchmark {
 static volatile int sink;
 static long run(List<Integer> l,int n,String op) {
   l.clear(); for(int i=0;i<n;i++) l.add(i);
   long t=System.nanoTime();
   switch(op){
    case "append": for(int i=0;i<5000;i++)l.add(i); break;
    case "front-insert": for(int i=0;i<1000;i++)l.add(0,i); break;
    case "middle-insert": for(int i=0;i<1000;i++)l.add(l.size()/2,i); break;
    case "middle-remove": for(int i=0;i<1000 && !l.isEmpty();i++)l.remove(l.size()/2); break;
    case "kth-access": for(int i=0;i<5000;i++)sink=l.get((i*37)%l.size()); break;
    case "iterate": for(int v:l)sink=v; break;
   }
   return System.nanoTime()-t;
 }
 static long upNext(Deque<Integer> d,int n){
   d.clear();for(int i=0;i<n;i++)d.addLast(i);
   long t=System.nanoTime();
   for(int i=0;i<5000;i++){d.addFirst(i);d.addLast(i);d.removeFirst();}
   return System.nanoTime()-t;
 }
 public static void main(String[]x){
  int[] ns={1000,10000,50000}; String[] ops={"append","front-insert","middle-insert","middle-remove","kth-access","iterate"};
  System.out.println("ArrayList vs LinkedList playlist timings (microseconds; indicative single sample)");
  for(int n:ns) for(String op:ops){
   long a=run(new ArrayList<>(),n,op)/1000,b=run(new LinkedList<>(),n,op)/1000;
   System.out.printf("n=%d op=%s ArrayList=%d LinkedList=%d%n",n,op,a,b);
  }
  System.out.println("Up Next deque endpoint workload:");
  for(int n:ns){long d=upNext(new ArrayDeque<>(),n)/1000;System.out.printf("n=%d ArrayDeque endpoint batch=%d us%n",n,d);}
  System.out.println("ArrayDeque is designed for efficient operations at both ends; it has no indexed get(k).");
 }
}
