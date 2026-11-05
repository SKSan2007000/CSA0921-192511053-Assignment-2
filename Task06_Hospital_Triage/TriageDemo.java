import java.util.*;
class Patient{
 final String name;final int severity;final long arrival;
 Patient(String n,int s,long a){name=n;severity=s;arrival=a;}
 public String toString(){return name+"(S"+severity+")";}
}
public class TriageDemo{
 public static void main(String[]args){
  // Assignment definition: severity 1 is critical; lower number has higher priority.
  Comparator<Patient> order=Comparator.comparingInt((Patient p)->p.severity).thenComparingLong(p->p.arrival);
  PriorityQueue<Patient> q=new PriorityQueue<>(order);
  String[] names={"Ravi","Meena","John","Asha","Kumar","Lata","Nila","Dev"};
  int[] levels={3,1,3,5,1,2,4,2};
  for(int i=0;i<names.length;i++)q.add(new Patient(names[i],levels[i],i));
  System.out.println("Expected hand-check: severity ascending; arrival ascending for ties.");
  System.out.print("Treatment order: ");
  StringJoiner result=new StringJoiner(" ");while(!q.isEmpty())result.add(q.poll().toString());
  System.out.println(result);
  System.out.println("PriorityQueue iteration is not sorted; poll() repeatedly to obtain service order.");
  System.out.println("Starvation: continuous critical arrivals can indefinitely delay minor cases.");
  System.out.println("Fair policy proposal: periodically reassess; escalate by maximum-wait thresholds with clinician approval.");
  System.out.println("Complexity: offer O(log n), poll O(log n), peek O(1); storage O(n).");
 }
}
