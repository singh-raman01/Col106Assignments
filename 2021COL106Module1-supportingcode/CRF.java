import HelperClasses.Pair;
import HelperClasses.sha256;
import java.util.HashMap;
import java.util.Map;


public class CRF extends sha256 {

    // Stores the output size of the function Fn()
    public int outputsize;

    CRF(int size) {
        outputsize = size;
        assert outputsize <= 64;
    }

    // Outputs the mapped outputSize characters long string s' for an input string s
    public String Fn(String s) {
        String shasum = encrypt(s);
        return shasum.substring(0,outputsize);
    }

    /*==========================
    |- To be done by students -|
    ==========================*/
// Collison: Two inputs x, y such that their inputs map to the same value
// means map[value]=true .. already existing true then got populated as true by another y
    public Pair<String, String> FindCollDeterministic() {

        Map<String,String> mp = new HashMap<>();

        String start = "000000";
        CRF CRF_obj = new CRF(5);
        String y0 = CRF_obj.Fn(start);
        mp.put(y0,start); 

        while (true){
            String yi = CRF_obj.Fn(y0);
            if(!mp.containsKey(yi)){
                mp.put(yi,y0);
            }else{
                Pair<String,String> ps = new Pair<>(y0,mp.get(yi));
                return ps;
            }
            y0=yi;
        }

    }


    public void FindCollRandomized() {
        
        String attempt_filename = "FindCollRandomizedAttempts.txt";
        String outcome_filename = "FindCollRandomizedOutcome.txt";

    }
}