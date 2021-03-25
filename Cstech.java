package cstech;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Scanner;
import java.io.*;

/**
 *
 * @author RC
 */
public class Cstech {
    
    static String deneme(StringBuffer path0,String islem,String key,String yeniKelime) throws FileNotFoundException {
    	//GUI den cagirilan method. path ile gelen dosyayi okuyup degisiklikleri baska bir dosyaya yapip sonra ilk dosyayi siler ve yeni dosyanin ismini degistirir
    	int adet=0;// kelime sayisini tutmak icin olusturulan variable
        int keyt=0;//verilen keywordde - veya * olup olmadığının bilgisini tutacak variable
        int yildizSayisi=0;
        String path=path0.toString();//gelen path direk acacagimiz dosyanin pathi
        path0.insert(path0.length()-4,'1');// yeni olusturacagimiz dosyanin pathi
        String path1=path0.toString();
        FileInputStream fis= new FileInputStream(path);
        Scanner file= new Scanner(fis);//okunulacak dosya
        FileOutputStream fos = new FileOutputStream(path1);
        PrintWriter pw= new PrintWriter(fos);//yeni dosya
        keyt=analiz(key);//analiz methodu keyde - ve ya * olmasi durumuna karsi bir int döner
        String[] yildiza=yildizAnaliz(key);//yildiz olan anahtar kelimelerdeki
        //yildiz sayilarini ve yerlerini ayriyetten yildizlarin oluşturduğu stringleri dönen kod
        yildizSayisi=Integer.parseInt(yildiza[0]);//kac yildiz oldugunu tutan variable
        int[] yildizlar=new int[yildizSayisi];//yildizlarin indexini tutacak array
        String[] kelimeler=new String[yildiza.length-yildizSayisi-1];//kelimeleri tutacak array
        if(!(islem.equals("F")||islem.equals("R")||islem.equals("D"))) {
        	//islem yanlis girildi ise hata döner
        	hata( pw,  fis,  path1);//hata durumunda yeni olusturulmus dosyayi siler. okunacak dosyayi kapatir
        	return("Yanlis islem");
        }
        if (key.equals("")) {
        	//key yanlis girildi ise hata döner
        	hata( pw,  fis,  path1);
        	return("Anahtar Kelimeyi Girin");
        }
        if(islem.equals("R")&&(yeniKelime.equals(""))){
        	//islem R ise ve yeni kelime yoksa hata döner
        	hata( pw,  fis,  path1);
            return "Yeni Kelimeyi Girin";
        }
        if(keyt>1){// keyde * varsa
            for(int i=0;i<yildizSayisi;i++){yildizlar[i]=Integer.parseInt(yildiza[i+1]);}//yildizlar arrayini doldurur
            for(int i=0;i<kelimeler.length;i++){ kelimeler[i]=yildiza[i+1+yildizSayisi];}//kelimeler arrayini doldurur
        }
        while (file.hasNext()){//dosyadaki kelimeleri sirasi ile isleme sokacak döngü. Son kelimeye gelince cöngüden çıkar
            String kelime= file.next();
            if (keyt==0){//keyde - veya * yoksa
                if(islem.equals("F")){// bulma islemi ise
                    adet=adet+normal(kelime,key);//normal methodu - ve ya * bulunmayan kelimeler icin kullanilir
                    //tam eslesme durumunda 1, diger durumlarda 0 döner. Eşlelen kelime sayisini her döngüde bu methodun sonucu ile topluyoruz.
                    pw.print(kelime+" ");// yeni dosyaya bu kelimeyi yaziyoruz.
                }
                if(islem.equals("D")){//Delete methodu
                    if(normal(kelime,key)!=1){//eger kelime eslesiyorsa yeni dosyaya yazmiyoruz.
                        pw.print(kelime+" ");  
                    }
                }
                if(islem.equals("R")){//replace methodu
                    if(normal(kelime,key)!=1){//eslesen kelimeler yerine yeni kelimeyi yeni dosyaya yaziyoruz.
                        pw.print(kelime+" ");  
                    }
                    else{
                        pw.print(yeniKelime+" ");//eslesmiyorsa eskisini yaziyoruz
                    }
                }
            }
            else if(keyt==1){if(islem.equals("F")){// keyde - varsa
                    adet=adet+tire(kelime,key);//tire methodu - olan keyleri eslestirmek için kullanilir. Eslesme varsa 1 döner
                    // bu method haric hersey keyt==0 durumu ile ayni
                    pw.print(kelime+" ");
                }
                if(islem.equals("D")){
                    if(tire(kelime,key)!=1){
                        pw.print(kelime+" ");  
                    }
                }
                if(islem.equals("R")){
                    if(tire(kelime,key)!=1){
                        pw.print(kelime+" ");  
                    }
                    else{
                        pw.print(yeniKelime+" ");
                    }
                }  
            }
            else if(keyt>1){//keyde * varsa
                if(islem.equals("F")){
                    adet=adet+yildiz(kelime,kelimeler, yildizlar);//yildiz methodu * olan keyleri eslestirmek için kullanilir. Eslesme varsa 1 döner
                    // bu method haric hersey keyt==0 durumu ile ayni
                    pw.print(kelime+" ");
                }
                if(islem.equals("D")){
                    if(yildiz(kelime,kelimeler, yildizlar)!=1){
                        pw.print(kelime+" ");  
                    }
                }
                if(islem.equals("R")){
                    if(yildiz(kelime,kelimeler, yildizlar)!=1){
                        pw.print(kelime+" ");  
                    }
                    else{
                        pw.print(yeniKelime+" ");
                    }
                }
            } 
        }
        kapat(pw,fis,path,path1);// dosyalari kapatir, eski dosyayi siler, yeni dosyanin ismini degistirir.
        if(islem.equals("F")){return Integer.toString(adet)+" adet kelime bulundu.";}// eger islem F ise adeti ekrana yazar
        else {return "Bitti";}//diger islemler icinb bitti yazar.
    	
    }
    private static int normal(String kelime, String key){//içerisinde - ve * geçmeyen 
        //anahtar sözcükler kelime ile eşleşiyor mu diye bakan kod
        if (kelime.equals(key)){return 1;}
        return 0;
    }
    private static int tire(String kelime, String key){//içerisinde sadece - geçen
        //anahtar sözcükler kelime ile eşleşiyor mu diye bakan kod.
        if (kelime.length()!=key.length()) return 0;//kelime uzunlulari farkli ise 0 döner
        for (int i=0;i<key.length();i++){
            if(key.charAt(i)=='-' ){//-karakteri olan indexlerin kelimede ki karşılıkları harf değilse 0 döner
                if(Character.isLetter(kelime.charAt(i))){
                continue;}
                else return 0;
            }
            else if (key.charAt(i)==kelime.charAt(i)){continue;}
            else return 0;
        }
        return 1;//for döngüsü tamamlandi ise 1 döner
        
    }
    private static int[] varmi(String kelime, String key,int yer){//verilen anahtar sözcük
        //verilen kelime de yer indexinden sonra var mı, varsa hangi indexler arası olduğunu dönen kod.
        boolean in=false;//o an bir eslesme var mi yok mu onun bilgisini tutar
        int bas=0;
        int son=0;
        int[] arr={0,bas,son};
        if((kelime.length()-yer)<key.length()){return arr;}//kelime uzunlugu - aranmaya baslanacak index key uzunlugundan kucukse key kelimenin icinde yoktur
        for(int i=yer;i<kelime.length();i++){
            if (in){//eslesme var ise
                for (int j=0;j<key.length();j++){// key length kadar harfleri kontrol edecek hepsi esitse key bulundu kabul edecek    
                    if((bas+j==kelime.length()||key.charAt(j)!=kelime.charAt(bas+j))&&(key.charAt(j)!='-'||!Character.isLetter(kelime.charAt(bas+j)))){
	                    //iki kelimedeki karakterler eşit değilse ve keyde - yoksa döngüden çıkar
                            in=false;
	                    bas=0;
	                    break;
                    	}
                    
                    else if(j==key.length()-1){
                    	//eşleşme varsa ve bu son harf ise bu key i kelimede bulundu kabul edip methodu sonlandırır.
                        son=i+j-1;
                        int[] arr1={1,bas,son};
                        return (arr1);
                    }
                    
                }
            }
            else if (kelime.charAt(i)==key.charAt(0)||(key.charAt(0)=='-'&&Character.isLetter(kelime.charAt(i)))){//eslemenin olmadigi ama basladigi durum
                    in=true;//eslesmenin basladigini belirtir
                    bas=i;//baslangic indexini i ye esitler
                }
        }
        return arr;
    }
    private static String[] yildizAnaliz(String key){
        int[] ylist=new int [15];//yildizlarin indexini tutacak array
        int yildizSayisi=0;
        String[] klist= new String[15];//kelimeleri tutacak array
        int stringSayisi=0;
        String s="";
        for (int i=0;i<key.length();i++){
            if (key.charAt(i)=='*'){//* bulundugu zaman yildiz sayisini 1 arttirir ve indexi yliste koyar
                ylist[yildizSayisi]=i;
                yildizSayisi++;
                if (!s.equals("")){//s bos degilse kliste atadıktran sonra s'i bos string yap
                    klist[stringSayisi]=s;
                    s="";
                    stringSayisi++;
                }
            }
            else if(i==key.length()-1){// * yoksa ama keyin sonuna gelindi ise yine kliste stringi ata
                s=s+key.charAt(i);
                klist[stringSayisi]=s;
                s="";
                stringSayisi++;
            }
            else{//* yok ve son harf de degilse s e yeni harfi ekle
                s=s+key.charAt(i);
            }
        }
        String[] output=new String[yildizSayisi+stringSayisi+1];
        output[0]=Integer.toString(yildizSayisi);
        for(int i=0;i<yildizSayisi;i++){output[1+i]=Integer.toString(ylist[i]);}
        for(int i=0;i<stringSayisi;i++){output[1+yildizSayisi+i]=klist[i];}      
        return output;//output olarak yildiz sayisi+yildizlar+kelimeler bilgisi iceren bir String array dön

        }
    private static int analiz(String key){//anahtar sözcükte - ve ya * olup olmadığına bakan method
        int[] output= new int[2];
        for(char c:key.toCharArray()){
            if(c=='-') {output[0]=1;}
            else if(c=='*'){output[1]=2;}
        }
        return output[0]+output[1];
    }
    private static int[] zeros(int[] kontrol){//kontrol arrayinin içindeki 0 ların yerini işaretlemek için kullanılacak method
    	
        boolean in=false;
        int[]sifirlar=new int[10];
        for(int i=0;i<sifirlar.length;i++){sifirlar[i]=-1;}//tüm elemanlari -1 yapar
        int bas=0;
        int son=0;
        int set=0;
        for (int i=0;i<kontrol.length;i++){
            if(in){//bir önceki eleman 0 ise
                if(kontrol[i]!=0 || i==kontrol.length-1){//bu eleman 0 degilse ya da kontrolun sonuna gelindi ise
                    son=i;//son indexini atar
                    in=false;
                    sifirlar[set]=bas;//basi set indexine atar
                    sifirlar[set+5]=son;//sonu set+5 indexine atar
                    set++;
                }
            }
            else{//0 bulunmasmissa
                if(kontrol[i]==0){//kontrolde 0 bulundu ise
                    in=true;//0 bulundugunu belirtir.
                    bas=i;//0 ın baslangici
                    if(i==kontrol.length-1){//eger sona gelindi ise bu 0 setini sifirlar arrayine atar
                    son=i;
                    in=false;
                    sifirlar[set]=bas;
                    sifirlar[set+5]=son;
                    set++;}
                }
            }
        }
        return sifirlar;// mesela[0,7,-1,-1,-1,2,9,-1,-1,-1]  dönerse 0 ile 2 indexi arasinin ve 7 ile 9 indexinin arasinin 0 oldugunu belirtir.
    }
    private static int yildiz(String kelime,String[] kelimeler, int[] yildizlar){
    		// kelimeyi, keyfeki olusan stringleri ve yildizlarin indexini alir. Eslesme varsa 1 yoksa 0 doner
    		int yer=0;
        	int[] kontrol= new int[kelime.length()];//kontrol arrayi kelimenin hangi 
        	//indexteki karakterlerinin anahtar ile uyuştuğunun bilgisini tutacak.
        	//tüm indexler 1 olursa eslesme vardir.
            for(String c:kelimeler){
                int[] ind=varmi(kelime,c,yer);// kelimelerdeki her bir stringi kelimede yer indexinden sonra arar ve bulunan indexleri döner.
                if(ind[0]==0){return 0;}//ind[0] stringin bulunup bulunmadıgını gösterir
                else {yer=ind[2]+1;}//ind[2] stringin bittigi indexi gösterir
                for(int i=ind[1];i<ind[2]+1;i++){kontrol[i]=1;}  //stringin bulundugu indextleri kontrol arrayinde 1 yapar     
            }
            int[] sifirlar= zeros(kontrol);//sifirlar arrayi kontrol arrayindeki bosluklari tutacak
            for(int i=0;i<sifirlar.length/2;i++){//sifirlar arrayinin ilk yarisi 0 setlerinin baslangicini
            	//ikinci yarisi sonunu tutar. O sebepten ilk yariya bakmak yeterlidir.
                if(sifirlar[i]==-1){break;}//-1 varsa döngüden cıkar
                else{
                    int bas=sifirlar[i];
                    int son=sifirlar[i+5];
                    for(int c:yildizlar){// eger bas ve son arasinda * var ise bu indexler arasini 1 yapar
                        if(bas<=c&&c<=son){
                            for(int j=bas;j<son+1;j++){
                                kontrol[j]=1;
                            }
                        }
                    }
                }
            }
        int sum=0;
        for(int i=0;i<kontrol.length;i++) {sum=sum+kontrol[i];}
        if(sum==kontrol.length) {return 1;}//eger tüm elemanlar 1 ise 1 döner. baska türlü 0 döner
        return 0;
    }
    private static void kapat(PrintWriter pw, FileInputStream fis,String path,String path1) {
    	//acilan dosyalari kapatir. eski dosyayi siler yenisinin ismini degistirir
    	pw.close();
        try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File x = new File(path);
        x.delete();
        File y= new File(path1);
        y.renameTo(x);
    }
    private static void hata(PrintWriter pw, FileInputStream fis, String path1) {
    	//acilan dosyaları kapatır yeni dosyayi siler
    	pw.close();
        try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File y= new File(path1);
        y.delete();
    }

    public static void main(String[] args) {
    }  
}