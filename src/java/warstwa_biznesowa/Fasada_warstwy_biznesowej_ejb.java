/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warstwa_biznesowa;

import java.util.ArrayList;
import java.util.Date;
import javax.ejb.Stateful;
/**
 *
 * @author Paweł L. 6148
 */
@Stateful
public class Fasada_warstwy_biznesowej_ejb {
    
    Fasada_warstwy_biznesowej fasada = new Fasada_warstwy_biznesowej();
    
    public void utworz_produkt(String[] dane, Date data){
        fasada.utworz_produkt(dane, data);
    }
    
    public String[] dane_produktu(){
        return fasada.dane_produktu();
    }
    
    public ArrayList<ArrayList<String>>items(){
        return fasada.items();
    }

}
