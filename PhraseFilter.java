package EarthQuakeFilter;


/**
 * Write a description of PhraseFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhraseFilter implements Filter {
private String where,phrase;
    PhraseFilter(String whe,String phr){
    where = whe;
    phrase = phr;
    }
    public boolean satisfies(QuakeEntry qe) { 
        if(where.equals("start"))
        return qe.getInfo().indexOf(phrase)==0;
        else if(where.equals("end"))
        return (qe.getInfo().indexOf(phrase)==(qe.getInfo().length()-phrase.length()));
        else if(where.equals("any"))
        return (qe.getInfo().indexOf(phrase)!=-1);
        return false;
    } 
    public String getName(){
    return "Phrase";
    }
}