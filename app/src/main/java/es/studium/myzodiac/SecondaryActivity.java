package es.studium.myzodiac;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondaryActivity extends AppCompatActivity {

    private final int[] nombres = {
            R.string.aries, R.string.tauro, R.string.geminis, R.string.cancer,
            R.string.leo, R.string.virgo, R.string.libra, R.string.escorpio,
            R.string.sagitario, R.string.capricornio, R.string.acuario, R.string.piscis
    };
    private final int[] imagenes = {
            R.drawable.ic_aries, R.drawable.ic_tauro, R.drawable.ic_gemini, R.drawable.ic_cancer,
            R.drawable.ic_leo, R.drawable.ic_virgo, R.drawable.ic_libra, R.drawable.ic_escorpio,
            R.drawable.ic_sagitario, R.drawable.ic_capricornio, R.drawable.ic_acuario, R.drawable.ic_piscis
    };

    TextView txtEdad, txtSigno;
    ImageView imgSigno;
    Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secondary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtEdad = findViewById(R.id.idEdad);
        txtSigno = findViewById(R.id.idSigno);
        imgSigno = findViewById(R.id.idImagen);
        btnVolver = findViewById(R.id.idVolver);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int edad = extras.getInt("edad");
            int signo = extras.getInt("signo");

            txtEdad.setText(getString(R.string.edad, edad));
            txtSigno.setText(getString(R.string.ser) + " " + getString(nombres[signo]));
            imgSigno.setImageResource(imagenes[signo]);
        }

        btnVolver.setOnClickListener(v -> finish());
    }
}