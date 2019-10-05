package EarthQuakesSelection;


import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    
    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData, double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        //TODO
        
        for (QuakeEntry qe : quakeData) {
            if (qe.getMagnitude() > magMin) {
                answer.add(qe);
            }
        }
        return answer;              
    }
    
    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData, double distMax, Location from) {      
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        
        for (QuakeEntry qe : quakeData) {
            if (qe.getLocation().distanceTo(from) < distMax) {
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData,double minDepth,double maxDepth){
    ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        
        for (QuakeEntry qe : quakeData) {
            if (qe.getDepth()<maxDepth && qe.getDepth()>minDepth) {
                answer.add(qe);
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,String where,String phrase){
            ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
            
            for (QuakeEntry qe : quakeData) {
                String str = qe.getInfo();
                int index;
                if(where.equals("start")){
                index = 0;
                }
                else if(where.equals("end")){
                index = (str.length()-phrase.length());
                }
                else{
                index = -1;
                }
                if(index==-1){
                    if(str.indexOf(phrase)!=-1){
                        answer.add(qe);
                    }
                }
                else{
                if(str.indexOf(phrase)==index){
                    answer.add(qe);
                }
                }
            }
        return answer;
    }
    
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                              qe.getLocation().getLatitude(),
                              qe.getLocation().getLongitude(),
                              qe.getMagnitude(),
                              qe.getInfo());
        }
        
    }
    
    public void bigQuakes() {
    EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        ArrayList<QuakeEntry> listBig = filterByMagnitude(list, -1.0);
        for (QuakeEntry qe : listBig) {
           System.out.println(qe);
        }
                System.out.println("read data for " + listBig.size() + " quakes for that criteria");
    }
    
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        ArrayList<QuakeEntry> listBig = filterByDepth(list, -10000.0 , -8000.0);
        for (QuakeEntry qe : listBig) {
           System.out.println(qe); 
        }
                System.out.println("read data for " + listBig.size() + " quakes for that criteria");
    }
    
    public void quakesOfPhrase() {
    EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        //ArrayList<QuakeEntry> listBig = filterByPhrase(list, "end" , "California");
        ArrayList<QuakeEntry> listBig = filterByPhrase(list, "any" , "Creek");
        //ArrayList<QuakeEntry> listBig = filterByPhrase(list, "start" , "Explosion");
        for (QuakeEntry qe : listBig) {
           System.out.println(qe); 
        }
                System.out.println("read data for " + listBig.size() + " quakes for that criteria");
    }
    
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }
    
    public void closeToMe() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("# quakes read: " + list.size());
        
        //Durham, NC
        //Location city = new Location(35.988, -78.907);
        //Bridgeport, CA
        Location city = new Location(38.17, -118.82);
        ArrayList<QuakeEntry> close = filterByDistanceFrom(list, 1000*1000 , city);
        for (int k=0; k< close.size(); k++) {
            QuakeEntry entry = close.get(k);
            double distanceInMeters = city.distanceTo(entry.getLocation());
            System.out.println(distanceInMeters/1000 + " " + entry.getInfo());
        }
        System.out.println("Found "+close.size()+" quakes in that criteria");
    }
}