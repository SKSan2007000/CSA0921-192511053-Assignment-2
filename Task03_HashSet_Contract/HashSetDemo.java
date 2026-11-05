import java.util.*;
public class HashSetDemo {
 static class FaultyStudent {
   final String id; FaultyStudent(String id){this.id=id;}
   @Override public boolean equals(Object o){return o instanceof FaultyStudent && id.equals(((FaultyStudent)o).id);}
   // Deliberate defect: inherits Object.hashCode(), inconsistent with equals().
 }
 public static void main(String[] args){
  Set<FaultyStudent> faulty=new HashSet<>();
  faulty.add(new FaultyStudent("S101"));faulty.add(new FaultyStudent("S101"));
  System.out.println("Faulty implementation: equal records may occupy separate hash buckets; observed size="+faulty.size());
  Set<Student> corrected=new HashSet<>();
  corrected.add(new Student("S101","Asha"));corrected.add(new Student("S102","Bala"));
  corrected.add(new Student("s101","Asha Duplicate"));corrected.add(new Student("S103","Charan"));
  System.out.println("Prediction for corrected set: 3 unique IDs; actual size="+corrected.size());
  Student a=new Student("S101","Asha"),b=new Student("s101","Different name");
  System.out.println("Corrected equals="+a.equals(b)+", equal hash codes="+(a.hashCode()==b.hashCode()));
  System.out.println("Extra test: identity ID is final, so hash identity cannot mutate after insertion.");
  System.out.println("Contract: equal objects must have equal hash codes; unequal objects may collide.");
 }
}
