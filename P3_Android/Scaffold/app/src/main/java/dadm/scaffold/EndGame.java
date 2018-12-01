package dadm.scaffold;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import dadm.scaffold.counter.GameFragment;
import dadm.scaffold.counter.MainMenuFragment;
import dadm.scaffold.model.SettingsInfo;

public class EndGame extends AppCompatActivity {


    public int globalScore =0;
    Button TryAgain,Exit;
    TextView Score,EnemiesKilled,Best1,Best2,Best3,Best4,Best5;
    ImageView Titulo;




    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        final Context contexto = this;

        //Accedemos a los objetos y los metemos en variables
        Exit = (Button) findViewById(R.id.ExitMenu);
        Score = (TextView) findViewById(R.id.FinalScore);
        EnemiesKilled = (TextView) findViewById(R.id.EnemiesKilled);
        Titulo = (ImageView) findViewById(R.id.Titulo);

        //Recojemos el paquete con información de la partida.
        Intent intent = getIntent();
        String[] finJuego = intent.getStringArrayExtra("finJuego");

        //Ganado o perdido
        if(finJuego[0].equals("true"))
            Titulo.setImageResource( R.drawable.won);
        else
            Titulo.setImageResource( R.drawable.lose);

        //Score
        globalScore= Integer.parseInt(finJuego[1]);
        Score.setText("Your Score: "+finJuego[1]);

        EnemiesKilled.setText("Enemies Killed: "+ finJuego[2]);






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
