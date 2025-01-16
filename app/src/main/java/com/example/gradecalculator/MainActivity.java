package com.example.gradecalculator;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Double> oceny = new ArrayList<>();
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

        poleSrednia = findViewById(R.id.mainResult);
        ListView listaOcen = findViewById(R.id.listaOcen);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listaOcen.setAdapter(adapter);
        //Konwertuje tablice lub liste na zestaw widokow do wykorzystania w ListView

        listaOcen.setOnItemClickListener((parent, view, position, id) -> {
            oceny.remove(position);
            aktualizujListeOcen();
            aktualizujSrednia();
        });

        konfigurujPrzyciskDodajOcene(R.id.grade1, R.id.buttonAddGrade1);
    }

    private void konfigurujPrzyciskDodajOcene(int idPolaTekstowego, int idPrzycisku) {
        EditText poleOceny = findViewById(idPolaTekstowego);
        Button przyciskDodaj = findViewById(idPrzycisku);

        przyciskDodaj.setOnClickListener(view -> {
            String wpisanaOcena = poleOceny.getText().toString();
            if (wpisanaOcena.isEmpty()) {
                Toast.makeText(this, "Pole oceny nie może być puste!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double ocena = Double.parseDouble(wpisanaOcena);
                if (ocena < 0 || ocena > 6) {
                    Toast.makeText(this, "Wprowadź ocenę w zakresie 0-6!", Toast.LENGTH_SHORT).show();
                } else {
                    oceny.add(ocena);
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
        List<String> tekstyOcen = new ArrayList<>();
        for (int i = 0; i < oceny.size(); i++) {
            tekstyOcen.add((i + 1) + ". " + oceny.get(i));
        }
        adapter.clear();
        adapter.addAll(tekstyOcen);
    }

    private void aktualizujSrednia() {
        if (oceny.isEmpty()) {
            poleSrednia.setText("Średnia: Brak ocen");
        } else {
            double suma = 0;
            for (double ocena : oceny) {
                suma += ocena;
            }
            double srednia = suma / oceny.size();
            poleSrednia.setText(String.format("Średnia: %.2f", srednia));
        }
    }
}
