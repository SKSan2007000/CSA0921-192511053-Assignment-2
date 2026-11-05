import java.util.*;
public class InventoryBenchmark {
 static class Item {final String sku;final String name;final int qty;Item(String s,String n,int q){sku=s;name=n;qty=q;}}
 public static void main(String[] args){
  for(int n:new int[]{1000,10000,100000}) runSize(n);
 }
 static void runSize(int n){
  int lookups=100000;
  Map<String,Item> h=new HashMap<>(),t=new TreeMap<>(),l=new LinkedHashMap<>();
  for(int i=0;i<n;i++){String k=String.format(Locale.ROOT,"SKU%06d",i);Item v=new Item(k,"Medicine "+i,i%500);h.put(k,v);t.put(k,v);l.put(k,v);}
  Random r=new Random(42);String[] keys=new String[lookups];for(int i=0;i<lookups;i++)keys[i]=String.format(Locale.ROOT,"SKU%06d",r.nextInt(n));
  System.out.println("DATASET medicines="+n+" lookups="+lookups);
  bench("HashMap",h,keys);bench("TreeMap",t,keys);bench("LinkedHashMap",l,keys);
 }
 static void bench(String name,Map<String,Item> m,String[] keys){
  long s=System.nanoTime();long sum=0;for(String k:keys){Item x=m.get(k);if(x!=null)sum+=x.qty;}long lookup=System.nanoTime()-s;
  s=System.nanoTime();List<String> codeOrder=new ArrayList<>(m.keySet());Collections.sort(codeOrder);long sorted=System.nanoTime()-s;
  s=System.nanoTime();List<String> insertionOrder=new ArrayList<>(m.keySet());long insertion=System.nanoTime()-s;
  System.out.printf(Locale.US,"%s lookup_ms=%.3f code_report_ms=%.3f insertion_report_ms=%.3f checksum=%d codeFirst=%s insertionFirst=%s%n",name,lookup/1e6,sorted/1e6,insertion/1e6,sum,codeOrder.isEmpty()?"-":codeOrder.get(0),insertionOrder.isEmpty()?"-":insertionOrder.get(0));
 }
}
