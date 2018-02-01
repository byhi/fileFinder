/**
 * 
 */
package founder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author zoltan.bihari
 *
 *Ez a szutyok is mükszik 
 */
public class vlami {
private static File mappa= new File("C:/Boltok");   						//a mappa utvonalát tartalamzza 
private static String[] mappalista = mappa.list(new FileManager());    //a mappában lévõ txt fájlok listáját tartalmazza
private static ArrayList<Termek> termekek;
/**Ez a metódus elõkésziti a listát ami tartalmazza az össze bolt össze terméké*/
private static ArrayList<Termek> termekListaFeltolto(){
	termekek = new ArrayList();		// tertelmezni fogja a termékeket
	System.out.println("Fájlok: " + mappalista.length);
	/*A bejérjuk a tömböt ami tartalamzza a fájlokat , 
	 * minden iterációnál betöltünk egy fájt amibõl kiolvassuk a termékekekt*/	
	for (String fileneve : mappalista) {						
		String boltnev = fileneve.substring(0, fileneve.length()-4);	//eltároluk a file nevét kiterjesztés nélkül
		
		File boltFile = new File(mappa.getPath()+"/"+fileneve); //A teljes fájl utvonalat tartalmazza a file nevével 
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(boltFile)))){		//betöltjük a bufferba a txt tartalmát.
			String termekSor ;
			/*Bejárjuk a fájlt soronként. Minden iterációnál létrehozzuk a tömb értékeibõl a Termek egy példányát*/
			while((termekSor = br.readLine()) != null){
				String[] termekAdat = termekSor.split("\\|");		//a sort 2 részre bontjuk "|"-segítségével.
				String termekNev  = termekAdat[0];
				int termekAr = new Integer(termekAdat[1]);
				termekek.add(new Termek(boltnev, termekNev,termekAr));
			}
		} catch (IOException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	Collections.sort(termekek);			// remdezzük a kész listát
	return termekek;
}
//private ArrayList<Termek> olcsoTerme() {
private static void olcsoTerme() {
	ArrayList<Termek> ter = new ArrayList<>();
	Termek t = null;
	int i = 0;
	do {
		if (t==null) {
			t = termekek.get(i);
		}
		
		if (termekek.get(i).getAr() < t.getAr()) {
			t = termekek.get(i);
		}
		if (i==termekek.size()-1) {
			ter.add(t);
			
		} else if( 
				!termekek.get(i+1).getNev().equals(t.getNev()) ){
			ter.add(t);
			t = termekek.get(i+1);
		}
		i++;
	} while (i<termekek.size());
	for (Termek termek : ter) {
		System.out.println(termek.toString());
	}
	
}

/**Ki íratjuk a legolcsóbb termékeket  -  NINCS HASZNÁLATBAN*/
private static void olcsoTermekek(ArrayList<Termek> termekek) {
	ArrayList<Termek> olcsotermekek = new ArrayList();
	int i = 0;

	while(i<termekek.size()){
		String termekNev = termekek.get(i).getNev();
		int minAr = 500, minArIndex =  -1 ;
		while( i < termekek.size() && termekNev.equals(termekek.get(i).getNev())){
			if (termekek.get(i).getAr() < minAr) {
				minAr = termekek.get(i).getAr();
				minArIndex = i;			
			}
			i++;
		}
		olcsotermekek.add(termekek.get(minArIndex));
	}
	System.out.println("A legolcsóbb termekek:");
	for (Termek termek : olcsotermekek) {
		System.out.println(termek);
	}
}

//private Termek getTermek
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//olcsoTermekek(termekListaFeltolto());
		//listTermekek(termekListaFeltolto());
		int korlat = 200000;
		termekListaFeltolto();
	//	listTermekek(termekek);
		System.out.println("*******************************************");
		olcsoTerme();
		System.out.println("*******************************************");
		
		
		/*int x = 0;
		if (termekek.get(termekek.size()-1).getAr()>korlat) {
			System.out.println("Nem tartalmaz ennyire olcsó terméket a lista");	
			
		} else if (termekek.get(0).getAr()<korlat) {			 
			 olcsolistTermekek(x);
		} else {
			x = dei(termekek.size()/2,0,termekek.size()-1,korlat);
			olcsolistTermekek(x);
		} */
	}

	/**Bejárja a listát az adott indextõl és kiírja az összes terméket.*/
	private static void olcsolistTermekek(int start) {		
		for (int i = start; i < termekek.size(); i++) {
			System.out.println(termekek.get(i).toString());	
		}		
	}
	/**bejárja és ki írja az egész listát  -  NINCS HASZNÁLATBAN*/	
	private static void listTermekek(ArrayList<Termek> termekListaFeltolto) {	
		for (Termek termek : termekListaFeltolto) {
			System.out.println(termek.toString());
		}		
	}
	
/**Divide Et Imperta - Bináris keresés 
 * A lényege hogy egy rendezett listában , folyamatosan 2 részre bontjuk a listát mig ki nem jukadunk a keresett értéknél.
 * Az algoritmus implementációja arra szolgál hogy az árszerint rendezet termék listábol gyorsan
 * megtaláljuk az az indexet ahonnan "ólcsó" termékek kezdõdnek .... tehát a listánk ár szerint
 * csökkenõ és az elsõ olyan elemet keressük ami már kissebb mint 500.
 * 
 * A metódus egy int-et ad vissza , ennek az elemnek az indexét vagy 0-t ha nincs ilyen
 * 
 * Egy gif ami beutatja a müködést - https://blog.penjee.com/wp-content/uploads/2015/04/binary-and-linear-search-animations.gif
 * Oldalak a megértéshez:
 * http://slideplayer.hu/slide/2198401/
 * https://www.slideshare.net/vadzoltan/oszd-meg-s-uralkodj
 * */
private static int dei( int index, int kezd, int veg , int korlat) {
		
	/*Ha a keresett elem indexén állunk akkor vissza  adja azt */
		if ((termekek.get(index-1).getAr() > korlat) && termekek.get(index).getAr() < korlat) {
			return index;
			} else{
				/*Ha aktuális indexen lévõ elemet megelõzõ elem is kissebb mint a korlát akkor visszafelé haladunk a listán  */
				if (termekek.get(index-1).getAr() < korlat) {		
					return  dei( 
							kezd+ (index-kezd)/2,
							kezd,
							index , 
							korlat);
					/*Ha aktuális indexen lévõ elemet megelõzõ elem is nagyobb mint a korlát akkor wlõew haladunk a listán  */	
				} else if (termekek.get(index-1).getAr() > korlat) {
					System.out.println("index:"+index+ " kezd:"+kezd+ " veg:"+veg+ " korlat:"+korlat);
					return  dei( 
							index + ((veg-index)/2),
							index ,
							veg , 
							korlat);
				}else{
					return termekek.size()-1;
				} 
			}	
	}
}
