import java.util.Objects;
public final class Student {
 private final String id, name;
 public Student(String id,String name){this.id=id.trim().toUpperCase();this.name=name;}
 public String getId(){return id;}
 @Override public boolean equals(Object o){if(this==o)return true;if(!(o instanceof Student))return false;Student s=(Student)o;return id.equals(s.id);}
 @Override public int hashCode(){return Objects.hash(id);}
 @Override public String toString(){return id+" - "+name;}
}
