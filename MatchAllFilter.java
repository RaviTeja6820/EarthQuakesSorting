package EarthQuakeFilter;


/**
 * Write a description of MatchAllFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class MatchAllFilter implements Filter {
private ArrayList<Filter> filters;
MatchAllFilter(){
    filters = new ArrayList<Filter>();
}
public void addFilter(Filter f){
    filters.add(f);
}
public boolean satisfies(QuakeEntry qe){
    int count=0;
    for(Filter f : filters){
        if(f.satisfies(qe)){
            count++;
        }
    }
    if(count==filters.size())
    return true;
    return false;
}
public String getName(){
    String str = " ";
    for(Filter f:filters){
        str=str+" "+f.getName();
    }
    return str;
}
}
