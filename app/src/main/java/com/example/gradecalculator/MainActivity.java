package com.example.gradecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private OcenaManager ocenaManager;
    private TextView listaOcen;
    private TextView wynikSredniej;

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
        listaOcen = findViewById(R.id.gradeList);
        wynikSredniej = findViewById(R.id.mainResult);

        ImageButton przyciskInfo = findViewById(R.id.imageButtonInfo);

        przyciskInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Waga oceny oznacza ile razy masz ją wpisać", Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .show();
            }
        });

        //Snackbar lepsz Toast bo nie zamyka się po chwili i ma przycisk akcji : Wymaga uzycia biblioteki

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
                    ocenaManager.dodajOcene(ocena);
                    aktualizujInterfejs();
                    poleOceny.setText("");
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Nieprawidłowy format liczby!", Toast.LENGTH_SHORT).show();
            }

            //Try - jeśli w obszarze jego dzialania bedzie błąd krytyczny to przejdzie do Catch aby zapobiec crashu aplikacji

        });
    }

    private void aktualizujInterfejs() {
        listaOcen.setText(ocenaManager.pobierzListeOcen());
        wynikSredniej.setText("Średnia: " + ocenaManager.obliczSrednia());
    }
}
