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
            Debug.Log("HOLA");
        }

        rotation.setGameMode(id);
        gameMode.SetActive(false);
        selectLevelMenu.SetActive(true);

        playClickSound();

    }
    public void selectLevel(int i)
    {

      

        selectLevelMenu.SetActive(false);
        inGame.SetActive(true);
        puzzleGenerator.init(i);

        playClickSound();



    }

    public void nextButton(int id)
    {
        for (int i = 0; i< difficulties.Length; i++)
        {
            difficulties[i].SetActive(false);
        }

        difficulties[id].SetActive(true);

        playClickSound();

    }


    public void repeatButton()
    {
        puzzleGenerator.repeatLevel();
        dialog.SetActive(false);
        inGame.SetActive(true);

        playClickSound();


    }

    public void nextLevelButton()
    {
        puzzleGenerator.nextLevel();
        dialog.SetActive(false);
        inGame.SetActive(true);

        playClickSound();

    }

    public void home()
    {
        //Destroy(puzzleGenerator.ActualMap);
        inGame.SetActive(false);
        dialog.SetActive(false);
        button.SetActive(true);

        playClickSound();

    }

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

    void playClickSound()
    {
        //Paramos todo audio anterior
        //source.Stop();
        //reproducimos victoria
        source.PlayOneShot(buttonSound);
    }
}
