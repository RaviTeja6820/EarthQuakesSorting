package EarthQuakeFilter;

import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "data/earthquakesSampleDataSix2.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        Filter fm = new MagnitudeFilter(3.5,4.5); 
        ArrayList<QuakeEntry> m7  = filter(list, fm);
        Filter fd = new DepthFilter(-55000.0,-20000.0);
        ArrayList<QuakeEntry> d7  = filter(list, fd);
        //Location loc = new Location(39.7392,-104.9903);
        //Filter fdi = new DistanceFilter(1000*1000,loc); 
        //ArrayList<QuakeEntry> di7  = filter(list, fdi);
        //Filter fp = new PhraseFilter("end","a");
        //ArrayList<QuakeEntry> p7  = filter(di7, fp);
        for (QuakeEntry qe: d7) {
            //System.out.print(qe.getLocation().distanceTo(loc));
            System.out.println(qe);
        }
        System.out.println("Total EarthQuakes in such criteria :"+d7.size());
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }
    
    public void testMatchAllFilter(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("#read data :"+list.size());
        MatchAllFilter maf=new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(1.0,4.0));
        maf.addFilter(new DepthFilter(-180000.0,-30000.0));
        maf.addFilter(new PhraseFilter("any","o"));
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : list){
            if(maf.satisfies(qe)){
                answer.add(qe);
            }
        }
        for (QuakeEntry qe: answer) { 
            System.out.println(qe);
        }
        System.out.println("Total EarthQuakes in such criteria :"+answer.size());
        System.out.println(maf.getName());
    }
    
    public void testMatchAllFilter2(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("#read data :"+list.size());
        MatchAllFilter maf=new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(0.0,5.0));
        Location loc = new Location(55.7308,-9.1153);
        maf.addFilter(new DistanceFilter(3000*1000,loc));
        maf.addFilter(new PhraseFilter("any","e"));
        ArrayList<QuakeEntry> answer = filter(list,maf);
        for (QuakeEntry qe: answer) { 
            System.out.println(qe);
        }
        System.out.println("Total EarthQuakes in such criteria :"+answer.size());
    }
}
