using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using UnityEngine;
using System;

[RequireComponent(typeof(AudioSource))]
public class MainMenuController : MonoBehaviour {

    // Use this for initialization



    public GameObject button;

    public GameObject selectLevelMenu;

    public GameObject[] difficulties;

    public GameObject[] levels;

    public GameObject dialog;

    public GameObject gameMode;
    public CamaraRotator rotation;

    public GameObject inGame;

    public GameObject next;

    public GameObject message;

    public GameObject settings;

    public GameObject sound;

    public Boolean soundEnabled = true;

    public GameObject vibration;

    public Boolean vibrationEnabled = true;

    public GameObject battery;

    public Boolean batterySaverEnabled = false;

    public PuzzleGenerator puzzleGenerator;

    public Sprite soundEnabledSprite;
    public Sprite soundDisabledSprite;

    public Sprite vibrationEnabledSprite;
    public Sprite vibrationDisabledSprite;

    public Sprite batterySaverEnabledSprite;
    public Sprite batterySaverDisabledSprite;
    private bool settingsEnabled = false;

    public AutoFocus autofocus;

    [Header("Audio")]
    public AudioClip winSound;
    public AudioClip loseSound;
    public AudioClip spawnSound;
    public AudioClip buttonSound;
    public AudioClip ballInSound;
    public AudioSource source;

    void Start () {

        source = this.GetComponent<AudioSource>();
    }
	
	// Update is called once per frame
	void Update () {
		
	}


    public void PlayButton()
    {
        button.SetActive(false);
        gameMode.SetActive(true);

        playClickSound();


    }

    public void selectGameMode(int id)
    {
        for (int j = 0; j < levels.Length; j++)
        {
            levels[j].GetComponent<Button>().interactable = true;
            levels[j].transform.GetChild(0).gameObject.SetActive(true);
        }

        for (int j = puzzleGenerator.level + 1; j < levels.Length; j++)
        {
            levels[j].GetComponent<Button>().interactable = false;
            levels[j].transform.GetChild(0).gameObject.SetActive(false);
            //Debug.Log("HOLA");
        }

        rotation.setGameMode(id);
        gameMode.SetActive(false);
        selectLevelMenu.SetActive(true);

        playClickSound();

    }
    //Selecciona el nivel desde el menú
    public void selectLevel(int i)
    {

        selectLevelMenu.SetActive(false);
        inGame.SetActive(true);
        puzzleGenerator.init(i);

        playClickSound();

        //Paramos todo audio anterior
        source.Stop();
        //Sonido de crear nivel
        source.PlayOneShot(spawnSound);

        //Pausa de 3 segundos para que el jugador pueda ver el escenario antes de que empiece la gravedad a hacer efecto
        Time.timeScale = 0;
        StartCoroutine(pauseStart(3));
        
    }

    //Siguiente pagina de niveles
    public void nextButton(int id)
    {
        for (int i = 0; i< difficulties.Length; i++)
        {
            difficulties[i].SetActive(false);
        }

        difficulties[id].SetActive(true);

        playClickSound();

    }

    //Repetir el nivel
    public void repeatButton()
    {
        puzzleGenerator.repeatLevel();
        dialog.SetActive(false);
        inGame.SetActive(true);

        playClickSound();

        //Pausa de 1 segundo para que el jugador pueda ver el escenario antes de que empiece la gravedad a hacer efecto
        Time.timeScale = 0;
        StartCoroutine(pauseStart(1));
    }

    //Siguiente nivel al completar uno
    public void nextLevelButton()
    {
        puzzleGenerator.nextLevel();
        dialog.SetActive(false);
        inGame.SetActive(true);

        playClickSound();

        //Paramos todo audio anterior
        source.Stop();
        //Sonido de crear nivel
        source.PlayOneShot(spawnSound);

        //Pausa de 3 segundos para que el jugador pueda ver el escenario antes de que empiece la gravedad a hacer efecto
        Time.timeScale = 0;
        StartCoroutine(pauseStart(3));

    }

    //Volver al menu ppal
    public void home()
    {
        //Destroy(puzzleGenerator.ActualMap);
        inGame.SetActive(false);
        dialog.SetActive(false);
        button.SetActive(true);

        playClickSound();

    }

    //Perder la partida
    internal void lose()
    {
        dialog.SetActive(true);
        inGame.SetActive(false);
        next.SetActive(false);
        message.GetComponent<Text>().text = "YOU LOSE";
        //Paramos todo audio anterior
        source.Stop();
        //reproducimos derrota
        source.PlayOneShot(loseSound);
    }

    //Pausar la partida
    public void pause()
    {
        inGame.SetActive(false);
        dialog.SetActive(true);
        message.GetComponent<Text>().text = "PAUSE MENU";
        Destroy(puzzleGenerator.ActualMap);
        // if(puzzleGenerator.level>= puzzleGenerator.id)
        next.SetActive(false);

        playClickSound();
    }

    //Borrar el progreso
    public void erase()
    {
        SaveGame.Instance.Level = 0;
        SaveGame.Save();
        puzzleGenerator.level = 0;
        //PlayButton();

        gameMode.SetActive(true);
        selectLevelMenu.SetActive(false);

        playClickSound();

    }
    //Ganar la partida
    internal void win()
    {
        next.SetActive(true);
        dialog.SetActive(true);
        inGame.SetActive(false);
        message.GetComponent<Text>().text = "YOU WIN";

        //Paramos todo audio anterior
        source.Stop();
        //reproducimos victoria
        source.PlayOneShot(winSound);

    }

    //Desplegable de Opciones
    public void SwitchSettings()
    {
        playClickSound();


        if (settingsEnabled)
        {
            settingsEnabled = false;
            sound.SetActive(false);
            vibration.SetActive(false);
            battery.SetActive(false);
        } else
        {
            settingsEnabled = true;
            sound.SetActive(true);
            vibration.SetActive(true);
            battery.SetActive(true);
        }
    }
    //Sonido on/off
    public void switchSound()
    {
        if (soundEnabled)
        {
            soundEnabled = false;
            sound.GetComponent<Image>().sprite = soundDisabledSprite;
            source.volume = 0;
        }
        else
        {
            soundEnabled = true;
            sound.GetComponent<Image>().sprite = soundEnabledSprite;
            source.volume = 1;

        }
    }

    //Vibracion on/off
    public void switchVibration()
    {
        if (vibrationEnabled)
        {
            vibrationEnabled = false;
            vibration.GetComponent<Image>().sprite = vibrationDisabledSprite;
            playClickSound();
        }
        else
        {
            vibrationEnabled = true;
            vibration.GetComponent<Image>().sprite = vibrationEnabledSprite;
            playClickSound();

        }
    }

    //Modo ahorro de bateria on/off (camerafocus)
    public void switchBattery()
    {
        if (batterySaverEnabled)
        {
            autofocus.switchCameraFocus();
            batterySaverEnabled = false;
            battery.GetComponent<Image>().sprite = batterySaverEnabledSprite;
            playClickSound();

        }
        else
        {
            autofocus.switchCameraFocus();
            batterySaverEnabled = true;
            battery.GetComponent<Image>().sprite = batterySaverDisabledSprite;
            playClickSound();

        }
    }

    //Boton jugar
    void playClickSound()
    {
        //Paramos todo audio anterior
        //source.Stop();
        //reproducimos victoria
        source.PlayOneShot(buttonSound);
    }

    private IEnumerator pauseStart(int time)
    {
        yield return new WaitForSecondsRealtime(time);
        Time.timeScale = 1;
    }
}
