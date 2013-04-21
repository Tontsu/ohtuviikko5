package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
 
public class Main {
 
    public static void main(String[] args) throws IOException {
        String studentNr = "13215167";
        if ( args.length>0) {
            studentNr = args[0];
        }
 
        String url = "http://ohtustats-2013.herokuapp.com/opiskelija/"+studentNr+".json";
 
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        client.executeMethod(method);
 
        InputStream stream =  method.getResponseBodyAsStream();
 
        String bodyText = IOUtils.toString(stream);
 

 
        Gson mapper = new Gson();
        Palautukset palautukset = mapper.fromJson(bodyText, Palautukset.class);

       
        
        System.out.println("Opiskelijanumero " + palautukset.getPalautukset().get(0).getOpiskelijanumero() + "\n");
 
        int tehtavat = 0;
        int tunnit = 0;
        for (Palautus palautus : palautukset.getPalautukset()) {
            System.out.println("viikko " + palautus.getViikko() + ": " + palautus.getTehtavia() + 
            " tehtävää " + palautus.getTehtavat() + " aikaa kului " + palautus.getTunteja() + " tuntia ");
            tehtavat = tehtavat + palautus.getTehtavia();
            tunnit = tunnit + palautus.getTunteja();
            
        }
        
        System.out.println("Yhteensä " + tehtavat + " tehtävää " + tunnit + " tuntia");
    }
}
