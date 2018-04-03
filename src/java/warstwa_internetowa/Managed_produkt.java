/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warstwa_internetowa;


import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import pomoc.Zmiana_danych;
import warstwa_biznesowa.Fasada_warstwy_biznesowej_ejb;

/**
 *
 * @author Paweł L. 6148
 */
@Named(value="managed_produkt")
@SessionScoped
public class Managed_produkt  implements ActionListener, Serializable{

    @EJB
    private Fasada_warstwy_biznesowej_ejb fasada;
    
    private String nazwa;
    private String kategoria;
    private float cena;
    private float promocja;
    private float cena_brutto;
    private DataModel items;
    private int stan =1;
    private Date data_produkcji;
    
    private Zmiana_danych zmiana1 = new Zmiana_danych("nazwa");
    private Zmiana_danych zmiana2 = new Zmiana_danych("kategoria");
    private Zmiana_danych zmiana3 = new Zmiana_danych("cena");

    public Zmiana_danych getZmiana1() {
        return zmiana1;
    }

    public void setZmiana1(Zmiana_danych zmiana1) {
        this.zmiana1 = zmiana1;
    }

    public Zmiana_danych getZmiana2() {
        return zmiana2;
    }

    public void setZmiana2(Zmiana_danych zmiana2) {
        this.zmiana2 = zmiana2;
    }

    public Zmiana_danych getZmiana3() {
        return zmiana3;
    }

    public void setZmiana3(Zmiana_danych zmiana3) {
        this.zmiana3 = zmiana3;
    }
       
    private NumberConverter number_convert = new NumberConverter();
    private NumberConverter promotion_convert = new NumberConverter();

    public NumberConverter getNumber_convert() {
        this.number_convert.setPattern("#####.## zł");
        return number_convert;
    }

    public void setNumber_convert(NumberConverter number_convert) {
        this.number_convert = number_convert;
    }

    public NumberConverter getPromotion_convert() {
        this.promotion_convert.setPattern("##.## %");
        return promotion_convert;
    }

    public void setPromotion_convert(NumberConverter promotion_convert) {
        this.promotion_convert = promotion_convert;
    }
    
    

    public Managed_produkt() {   
    }

    public Fasada_warstwy_biznesowej_ejb getFasada() {
        return fasada;
    }

    public void setFasada(Fasada_warstwy_biznesowej_ejb fasada) {
        this.fasada = fasada;
    }
    
    public DataModel utworz_DataModel() {
        return new ListDataModel(fasada.items());
    }

    public DataModel getItems() {
        if (items == null) {
            items = utworz_DataModel();
        }
        return items;
    }

    public void setItems(DataModel items) {
        this.items = items;
    }

    public int getStan() {
        return stan;
    }

    public void setStan(int stan) {
        this.stan = stan;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public float getPromocja() {
        return promocja;
    }

    public void setPromocja(float promocja) {
        this.promocja = promocja;
    }

    public float getCena_brutto() {
        return cena_brutto;
    }

    public void setCena_brutto(float cena_brutto) {
        this.cena_brutto = cena_brutto;
    }

    public Date getData_produkcji() {
        return data_produkcji;
    }

    public void setData_produkcji(Date data_produkcji) {
        this.data_produkcji = data_produkcji;
    }
    
    public void dodaj_produkt()
    {
        String[] dane = {nazwa, kategoria, ""+cena, ""+promocja};
        fasada.utworz_produkt(dane, data_produkcji);
    }
    
    public String dane_produktu()
    {
        stan = 1;
        String[] dane = fasada.dane_produktu();
        if (dane == null) 
        {
            stan = 0;
        } else {
            nazwa=dane[0];
            kategoria = dane[1];
            cena= Float.parseFloat(dane[2]);
            promocja=Float.parseFloat(dane[3]);
            cena_brutto=Float.parseFloat(dane[4]);
            data_produkcji.setTime(Long.parseLong(dane[5]));
        }
        return "rezultat2";
    }
    
    @Override
    public void processAction(ActionEvent event) throws AbortProcessingException{
        dodaj_produkt();
        dane_produktu();
    }
    
    public float getMin(){
        return 0f;
    }
    
    public float getMax(){
        return 100f;
    }
    
    public float getMinPrice(){
        return 0;
    }
    
    public float getMaxPrice(){
        return 1000f;
    }
    
    public void zakrespromocji(FacesContext context, UIComponent toValidate, Object value) {
        stan = 1;
        float input=Float.parseFloat(value.toString());
        if (input < getMin() || input > getMax()) {
            ((UIInput) toValidate).setValid(false);
            FacesMessage message = new FacesMessage("Dane poza zakresem");
            context.addMessage(toValidate.getClientId(context), message);
            stan = 0;
        }
    }
}