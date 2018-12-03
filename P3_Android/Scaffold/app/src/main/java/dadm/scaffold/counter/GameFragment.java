package dadm.scaffold.counter;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import dadm.scaffold.BaseFragment;
import dadm.scaffold.EndGame;
import dadm.scaffold.R;
import dadm.scaffold.ScaffoldActivity;
import dadm.scaffold.engine.FramesPerSecondCounter;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameView;
import dadm.scaffold.engine.ParallaxBackground;
import dadm.scaffold.engine.ScoreCounter;
import dadm.scaffold.input.BasicInputController;
import dadm.scaffold.input.JoystickInputController;
import dadm.scaffold.model.SettingsInfo;
import dadm.scaffold.space.AmmoItem;
import dadm.scaffold.space.Asteroid;
import dadm.scaffold.space.Enemy;
import dadm.scaffold.space.EnemySpawner;
import dadm.scaffold.space.LifeItem;
import dadm.scaffold.space.SpaceShipPlayer;
import dadm.scaffold.space.TimeItem;


public class GameFragment extends BaseFragment implements View.OnClickListener {

    private GameEngine theGameEngine;


    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setBackgroundResource(R.drawable.pause);

        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        final ViewTreeObserver observer = view.getViewTreeObserver();


        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Para evitar que sea llamado múltiples veces,
                //se elimina el listener en cuanto es llamado
                observer.removeOnGlobalLayoutListener(this);
                GameView gameView = (GameView) getView().findViewById(R.id.gameView);
                theGameEngine = new GameEngine(getActivity(), gameView);
                theGameEngine.setTheInputController(new JoystickInputController(getView()));

                //Creamos los fondos
                theGameEngine.addGameObject(new ParallaxBackground(theGameEngine, 50, R.drawable.back));
                theGameEngine.addGameObject(new ParallaxBackground(theGameEngine, 100, R.drawable.back3));
                theGameEngine.addGameObject(new ParallaxBackground(theGameEngine, 200, R.drawable.back2));

                // Creamos la nave
                theGameEngine.addGameObject(new SpaceShipPlayer(theGameEngine, SettingsInfo.getInstance().getDrawableRes()));

                theGameEngine.addGameObject(new ScoreCounter(theGameEngine));

                theGameEngine.addGameObject(new EnemySpawner(theGameEngine));

                theGameEngine.addGameObject(new LifeItem(theGameEngine));
                theGameEngine.addGameObject(new TimeItem(theGameEngine));
                theGameEngine.addGameObject(new AmmoItem(theGameEngine));

                theGameEngine.addGameObject(new FramesPerSecondCounter(theGameEngine));
                theGameEngine.startGame();
            }
        });


    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        theGameEngine.stopGame();
    }

    @Override
    public boolean onBackPressed() {
        if (theGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }

    public void setPlayerImage(int drawableRes) {
        theGameEngine.getPlayer().setImage(theGameEngine, drawableRes);
    }

    //Vamos a la pantalla de final.
    public void onGameFinished() {
        Intent intent = new Intent(getContext(), EndGame.class);

        //Mandamos mensaje de fin de partida, score y tiempo (estos dos ultimo son valores basura).
        String[] finJuego = {"limbo", "-99999", "0"};

        //Mandamos el paquete
        intent.putExtra("finJuego", finJuego);

        startActivity(intent);
    }

    private void pauseGameAndShowPauseDialog() {
        theGameEngine.pauseGame();


        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        theGameEngine.stopGame();
                        ((ScaffoldActivity) getActivity()).navigateBack();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                        theGameEngine.resumeGame();
                    }
                })
                .create()
                .show();


    }


    private void playOrPause() {
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if (theGameEngine.isPaused()) {
            theGameEngine.resumeGame();
            button.setText(R.string.pause);
        } else {
            theGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }
}
