package EarthQuakesSelection;


/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class LargestQuakes {
public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for " + list.size());
        System.out.println(list.get(indexOfLargest(list)));
        ArrayList<QuakeEntry> large = getLargest(list,50);
        for(QuakeEntry qe:large){
            System.out.println(qe);
        }
}
public int indexOfLargest(ArrayList<QuakeEntry> data){
    int index=-1;
    double maxV=0;
    for(QuakeEntry qe:data){
        if(qe.getMagnitude()>maxV){
            maxV=qe.getMagnitude();
            index=data.indexOf(qe);
        }
    }
    return index;
}
public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData,int howMany){
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        for(int j=0; j < howMany; j++) {
            int maxIndex = indexOfLargest(copy);
            ret.add(copy.get(maxIndex));
            copy.remove(maxIndex);
        }
        return ret;
   }
}
