package facades;

import dtos.CityinfoDto;
import entities.Cityinfo;
import org.json.JSONArray;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class CityInfoFacade {

    private static CityInfoFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CityInfoFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CityInfoFacade getCityInfoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CityInfoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CityinfoDto> getAllCityInfo() throws IOException {
        String responseBody = "";
        List<CityinfoDto> cityinfoDtoList = new ArrayList<>();
        try {
            URL url = new URL("https://api.dataforsyningen.dk/postnumre");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == 200) {
                Scanner scan = new Scanner(url.openStream());
                while(scan.hasNext()) {
                    String temp = scan.nextLine();
                     temp = new String (temp.getBytes(), StandardCharsets.UTF_8);
                    responseBody += temp;
                }
            }
            JSONArray jsonArray = new JSONArray(responseBody);
            int nrInt;
            for (int i = 0; i < jsonArray.length(); i++) {
                Object nr = jsonArray.getJSONObject(i).get("nr");
                Object navn = jsonArray.getJSONObject(i).get("navn");
                nrInt = Integer.parseInt(nr.toString());
                cityinfoDtoList.add(new CityinfoDto(new Cityinfo(nrInt, navn.toString())));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cityinfoDtoList;

    }

}
