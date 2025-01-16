package com.example.gradecalculator;

import java.util.ArrayList;
import java.util.List;

public class OcenaManager {

    private List<Double> oceny;

    public OcenaManager() {
        this.oceny = new ArrayList<>();
    }

    public void dodajOcene(double ocena) {
        oceny.add(ocena);
    }

    public String pobierzListeOcen() {
        StringBuilder tekstOcen = new StringBuilder("Oceny: \n");

        //Kazda ocena w tablicy to osobny string - StringBuilder

        for (int i = 0; i < oceny.size(); i++) {
            tekstOcen.append(i + 1).append(". ").append(oceny.get(i)).append("\n");
        }
        return tekstOcen.toString();
    }

    public String obliczSrednia() {
        if (oceny.isEmpty()) {
            return "brak ocen";
        }

        double suma = 0;
        for (double ocena : oceny) {
            suma += ocena;
        }
        double srednia = suma / oceny.size();
        return String.format("%.2f", srednia);
    }
}
