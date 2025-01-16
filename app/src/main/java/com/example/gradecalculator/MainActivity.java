package com.example.gradecalculator;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private OcenaManager ocenaManager;
    private ArrayAdapter<String> adapter;
    private TextView poleSrednia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets paskiSystemowe = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(paskiSystemowe.left, paskiSystemowe.top, paskiSystemowe.right, paskiSystemowe.bottom);
            return insets;
        });

        ocenaManager = new OcenaManager();
        poleSrednia = findViewById(R.id.mainResult);
        ListView listaOcen = findViewById(R.id.listaOcen);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listaOcen.setAdapter(adapter);

        listaOcen.setOnItemClickListener((parent, view, position, id) -> {
            ocenaManager.usunOcene(position);
            aktualizujListeOcen();
            aktualizujSrednia();
        });

        konfigurujPrzyciskDodajOcene(R.id.grade1, R.id.spinnerWagi, R.id.buttonAddGrade1);
    }

    private void konfigurujPrzyciskDodajOcene(int idPolaTekstowego, int idSpinnera, int idPrzycisku) {
        EditText poleOceny = findViewById(idPolaTekstowego);
        Spinner spinnerWagi = findViewById(idSpinnera);
        Button przyciskDodaj = findViewById(idPrzycisku);

        ArrayAdapter<CharSequence> wagiAdapter = ArrayAdapter.createFromResource(
                this, R.array.wagi_array, android.R.layout.simple_spinner_item);
        wagiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWagi.setAdapter(wagiAdapter);

        //wagi_array bierze z folderu strings.xml

        przyciskDodaj.setOnClickListener(view -> {
            String wpisanaOcena = poleOceny.getText().toString();
            if (wpisanaOcena.isEmpty()) {
                Toast.makeText(this, "Pole oceny nie może być puste!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double ocena = Double.parseDouble(wpisanaOcena);
                int waga = Integer.parseInt(spinnerWagi.getSelectedItem().toString());
                if (ocena < 0 || ocena > 6) {
                    Toast.makeText(this, "Wprowadź ocenę w zakresie 0-6!", Toast.LENGTH_SHORT).show();
                } else {
                    ocenaManager.dodajOcene(ocena, waga);
                    aktualizujListeOcen();
                    aktualizujSrednia();
                    poleOceny.setText("");
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Nieprawidłowy format liczby!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void aktualizujListeOcen() {
        adapter.clear();
        adapter.addAll(ocenaManager.pobierzListeOcen());
    }

    private void aktualizujSrednia() {
        poleSrednia.setText("Średnia ważona: " + ocenaManager.obliczSredniaWazona());
    }
}
