package com.example.gradecalculator;

import java.util.ArrayList;
import java.util.List;

public class OcenaManager {

    private List<Double> oceny;
    private List<Integer> wagi;

    public OcenaManager() {
        this.oceny = new ArrayList<>();
        this.wagi = new ArrayList<>();
    }

    public void dodajOcene(double ocena, int waga) {
        oceny.add(ocena);
        wagi.add(waga);
    }

    public void usunOcene(int index) {
        if (index >= 0 && index < oceny.size()) {
            oceny.remove(index);
            wagi.remove(index);
        }
    }

    public List<String> pobierzListeOcen() {
        List<String> tekstyOcen = new ArrayList<>();
        for (int i = 0; i < oceny.size(); i++) {
            tekstyOcen.add((i + 1) + ". " + oceny.get(i) + " (waga: " + wagi.get(i) + ")");
        }
        return tekstyOcen;
    }

    public String obliczSredniaWazona() {
        if (oceny.isEmpty()) {
            return "Brak ocen";
        }

        double sumaWazona = 0;
        int sumaWag = 0;
        for (int i = 0; i < oceny.size(); i++) {
            sumaWazona += oceny.get(i) * wagi.get(i);
            sumaWag += wagi.get(i);
        }

        double sredniaWazona = sumaWazona / sumaWag;
        return String.format("%.2f", sredniaWazona);
    }
}
