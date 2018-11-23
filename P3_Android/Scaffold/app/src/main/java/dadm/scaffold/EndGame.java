package dadm.scaffold;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.counter.MainMenuFragment;

public class EndGame extends AppCompatActivity {


    public int globalScore =0;
    Button TryAgain,Exit;
    TextView Score, Titulo, Best1,Best2,Best3,Best4,Best5;




    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        final Context contexto = this;

        //Accedemos a los objetos y los metemos en variables
        Exit = (Button) findViewById(R.id.ExitMenu);
        Score = (TextView) findViewById(R.id.FinalScore);
        Titulo = (TextView) findViewById(R.id.Titulo);

        //Recojemos el paquete con información de la partida.
        Intent intent = getIntent();
        String[] finJuego = intent.getStringArrayExtra("finJuego");

        //Ganado o perdido
        if(finJuego[0].equals("true"))
            Titulo.setText("¡Has ganado!");
        else
            Titulo.setText("¡Has perdido!");

        //Score
        globalScore= Integer.parseInt(finJuego[1]);
        Score.setText("Your Score: "+finJuego[1]);






        //Botón Salir al Menú principal

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(contexto, ScaffoldActivity.class);

                startActivity(intent);
            }
        });


    }





}
