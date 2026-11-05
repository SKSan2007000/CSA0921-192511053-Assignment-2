import java.util.*;
class Team implements Comparable<Team>{
 final String name;final int points,goalDifference;
 Team(String n,int p,int g){name=n;points=p;goalDifference=g;}
 public int compareTo(Team o){int c=Integer.compare(o.points,points);if(c!=0)return c;c=Integer.compare(o.goalDifference,goalDifference);if(c!=0)return c;return name.compareToIgnoreCase(o.name);}
 public String toString(){return name+" (points="+points+", GD="+goalDifference+")";}
}
class Leaderboard<T>{
 private final List<T> entries;
 Leaderboard(Collection<T> source,Comparator<? super T> comparator){entries=new ArrayList<>(source);entries.sort(comparator);}
 List<T> getEntries(){return Collections.unmodifiableList(entries);}
}
public class LeaderboardDemo{
 public static void main(String[]x){
  List<Team> original=new ArrayList<>(Arrays.asList(new Team("Falcons",18,12),new Team("Bisons",18,15),new Team("Comets",15,20),new Team("Arrows",18,15)));
  Comparator<Team> rule=Comparator.comparingInt((Team z)->z.points).reversed().thenComparing(Comparator.comparingInt((Team z)->z.goalDifference).reversed()).thenComparing(z->z.name,String.CASE_INSENSITIVE_ORDER);
  Leaderboard<Team> board=new Leaderboard<>(original,rule);
  System.out.println("Sorted leaderboard:");int rank=1;for(Team t:board.getEntries())System.out.println((rank++)+". "+t);
  System.out.println("Original order preserved = "+original.get(0).name.equals("Falcons"));
  System.out.println("Tie rule: points DESC, goal difference DESC, name ASC.");
 }
}
