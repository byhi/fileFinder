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
 *Ez a szutyok is m�kszik 
 */
public class vlami {
private static File mappa= new File("C:/Boltok");   						//a mappa utvonal�t tartalamzza 
private static String[] mappalista = mappa.list(new FileManager());    //a mapp�ban l�v� txt f�jlok list�j�t tartalmazza
private static ArrayList<Termek> termekek;
/**Ez a met�dus el�k�sziti a list�t ami tartalmazza az �ssze bolt �ssze term�k�*/
private static ArrayList<Termek> termekListaFeltolto(){
	termekek = new ArrayList();		// tertelmezni fogja a term�keket
	System.out.println("F�jlok: " + mappalista.length);
	/*A bej�rjuk a t�mb�t ami tartalamzza a f�jlokat , 
	 * minden iter�ci�n�l bet�lt�nk egy f�jt amib�l kiolvassuk a term�kekekt*/	
	for (String fileneve : mappalista) {						
		String boltnev = fileneve.substring(0, fileneve.length()-4);	//elt�roluk a file nev�t kiterjeszt�s n�lk�l
		
		File boltFile = new File(mappa.getPath()+"/"+fileneve); //A teljes f�jl utvonalat tartalmazza a file nev�vel 
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(boltFile)))){		//bet�ltj�k a bufferba a txt tartalm�t.
			String termekSor ;
			/*Bej�rjuk a f�jlt soronk�nt. Minden iter�ci�n�l l�trehozzuk a t�mb �rt�keib�l a Termek egy p�ld�ny�t*/
			while((termekSor = br.readLine()) != null){
				String[] termekAdat = termekSor.split("\\|");		//a sort 2 r�szre bontjuk "|"-seg�ts�g�vel.
				String termekNev  = termekAdat[0];
				int termekAr = new Integer(termekAdat[1]);
				termekek.add(new Termek(boltnev, termekNev,termekAr));
			}
		} catch (IOException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	Collections.sort(termekek);			// remdezz�k a k�sz list�t
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

/**Ki �ratjuk a legolcs�bb term�keket  -  NINCS HASZN�LATBAN*/
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
	System.out.println("A legolcs�bb termekek:");
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
			System.out.println("Nem tartalmaz ennyire olcs� term�ket a lista");	
			
		} else if (termekek.get(0).getAr()<korlat) {			 
			 olcsolistTermekek(x);
		} else {
			x = dei(termekek.size()/2,0,termekek.size()-1,korlat);
			olcsolistTermekek(x);
		} */
	}

	/**Bej�rja a list�t az adott indext�l �s ki�rja az �sszes term�ket.*/
	private static void olcsolistTermekek(int start) {		
		for (int i = start; i < termekek.size(); i++) {
			System.out.println(termekek.get(i).toString());	
		}		
	}
	/**bej�rja �s ki �rja az eg�sz list�t  -  NINCS HASZN�LATBAN*/	
	private static void listTermekek(ArrayList<Termek> termekListaFeltolto) {	
		for (Termek termek : termekListaFeltolto) {
			System.out.println(termek.toString());
		}		
	}
	
/**Divide Et Imperta - Bin�ris keres�s 
 * A l�nyege hogy egy rendezett list�ban , folyamatosan 2 r�szre bontjuk a list�t mig ki nem jukadunk a keresett �rt�kn�l.
 * Az algoritmus implement�ci�ja arra szolg�l hogy az �rszerint rendezet term�k list�bol gyorsan
 * megtal�ljuk az az indexet ahonnan "�lcs�" term�kek kezd�dnek .... teh�t a list�nk �r szerint
 * cs�kken� �s az els� olyan elemet keress�k ami m�r kissebb mint 500.
 * 
 * A met�dus egy int-et ad vissza , ennek az elemnek az index�t vagy 0-t ha nincs ilyen
 * 
 * Egy gif ami beutatja a m�k�d�st - https://blog.penjee.com/wp-content/uploads/2015/04/binary-and-linear-search-animations.gif
 * Oldalak a meg�rt�shez:
 * http://slideplayer.hu/slide/2198401/
 * https://www.slideshare.net/vadzoltan/oszd-meg-s-uralkodj
 * */
private static int dei( int index, int kezd, int veg , int korlat) {
		
	/*Ha a keresett elem index�n �llunk akkor vissza  adja azt */
		if ((termekek.get(index-1).getAr() > korlat) && termekek.get(index).getAr() < korlat) {
			return index;
			} else{
				/*Ha aktu�lis indexen l�v� elemet megel�z� elem is kissebb mint a korl�t akkor visszafel� haladunk a list�n  */
				if (termekek.get(index-1).getAr() < korlat) {		
					return  dei( 
							kezd+ (index-kezd)/2,
							kezd,
							index , 
							korlat);
					/*Ha aktu�lis indexen l�v� elemet megel�z� elem is nagyobb mint a korl�t akkor wl�ew haladunk a list�n  */	
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
