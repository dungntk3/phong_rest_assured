package api.util;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
public class URL {
	private static Logger log = LogManager.getLogger(URL.class.getName());
	public static final String URL_JIRA = "http://localhost:8080";
	public static final String URL_GG_MAP = "https://maps.googleapis.com";
	public static final String URL_GG_MAP_PLACE = "/maps/api/place/nearbysearch/json";
	
	public static String getEndPointJira(){
		log.info("Base URI : " + URL_JIRA);
		return URL_JIRA;
	}
	
	public static String getEndPointJira(String resource){
		log.info("URI End Point : " + URL_JIRA + resource);
		return URL_JIRA + resource;
	}
	
	public static String getEndPointGoogleMap(){
		log.info("Base URI : " + URL_GG_MAP);
		return URL_GG_MAP;
	}
	
	public static String getEndPointGoogleMap(String resource){
		log.info("URI End Point : " + URL_GG_MAP + resource);
		return URL_GG_MAP + resource;
	}

}
