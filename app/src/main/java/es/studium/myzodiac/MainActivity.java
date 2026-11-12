package es.studium.myzodiac;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView txtFecha;
    DatePicker calendario;
    Button btnAceptar;

    boolean fechaElegida = false;
    int dia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtFecha = findViewById(R.id.idFecha);
        calendario = findViewById(R.id.idCalendar);
        btnAceptar = findViewById(R.id.idButton);

        Calendar inicio = Calendar.getInstance();
        int anioIni = inicio.get(Calendar.YEAR);
        int mesIni = inicio.get(Calendar.MONTH);
        int diaIni = inicio.get(Calendar.DAY_OF_MONTH);

        if (savedInstanceState != null) {
            fechaElegida = savedInstanceState.getBoolean("fechaElegida");
            dia = savedInstanceState.getInt("dia");
            mes = savedInstanceState.getInt("mes");
            anio = savedInstanceState.getInt("anio");
            if (fechaElegida) {
                anioIni = anio;
                mesIni = mes;
                diaIni = dia;
                mostrarFecha();
            }
        }

        calendario.init(anioIni, mesIni, diaIni, (view, year, monthOfYear, dayOfMonth) -> {
            anio = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            fechaElegida = true;
            mostrarFecha();
        });

        btnAceptar.setOnClickListener(v -> {
            if (!fechaElegida) {
                mostrarError(R.string.error_fecha_vacia);
                return;
            }
            Calendar hoy = Calendar.getInstance();
            if (esFechaFutura(hoy)) {
                mostrarError(R.string.error_fecha_futura);
                return;
            }
            int edad = calcularEdad(hoy);
            int signo = calcularSigno(dia, mes + 1);

            Intent intent = new Intent(this, SecondaryActivity.class);
            intent.putExtra("edad", edad);
            intent.putExtra("signo", signo);
            startActivity(intent);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fechaElegida", fechaElegida);
        outState.putInt("dia", dia);
        outState.putInt("mes", mes);
        outState.putInt("anio", anio);
    }

    private void mostrarFecha() {
        txtFecha.setText(getString(R.string.formato_fecha, dia, mes + 1, anio));
    }

    private void mostrarError(int idMensaje) {
        Snackbar.make(findViewById(android.R.id.content), idMensaje, Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(this, R.color.error))
                .setTextColor(ContextCompat.getColor(this, R.color.white))
                .show();
    }

    private boolean esFechaFutura(Calendar hoy) {
        int anioHoy = hoy.get(Calendar.YEAR);
        int mesHoy = hoy.get(Calendar.MONTH);
        int diaHoy = hoy.get(Calendar.DAY_OF_MONTH);
        return anio > anioHoy
                || (anio == anioHoy && mes > mesHoy)
                || (anio == anioHoy && mes == mesHoy && dia > diaHoy);
    }

    private int calcularEdad(Calendar hoy) {
        int edad = hoy.get(Calendar.YEAR) - anio;
        if (hoy.get(Calendar.MONTH) < mes
                || (hoy.get(Calendar.MONTH) == mes && hoy.get(Calendar.DAY_OF_MONTH) < dia)) {
            edad--;
        }
        return edad;
    }

    private int calcularSigno(int d, int m) {
        if ((m == 3 && d >= 21) || (m == 4 && d <= 19)) return 0;
        if ((m == 4 && d >= 20) || (m == 5 && d <= 20)) return 1;
        if ((m == 5 && d >= 21) || (m == 6 && d <= 20)) return 2;
        if ((m == 6 && d >= 21) || (m == 7 && d <= 22)) return 3;
        if ((m == 7 && d >= 23) || (m == 8 && d <= 22)) return 4;
        if ((m == 8 && d >= 23) || (m == 9 && d <= 22)) return 5;
        if ((m == 9 && d >= 23) || (m == 10 && d <= 22)) return 6;
        if ((m == 10 && d >= 23) || (m == 11 && d <= 21)) return 7;
        if ((m == 11 && d >= 22) || (m == 12 && d <= 21)) return 8;
        if ((m == 12 && d >= 22) || (m == 1 && d <= 19)) return 9;
        if ((m == 1 && d >= 20) || (m == 2 && d <= 18)) return 10;
        return 11;
    }
}
