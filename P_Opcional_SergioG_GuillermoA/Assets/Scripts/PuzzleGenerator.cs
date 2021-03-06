﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;

public class PuzzleGenerator : DefaultTrackableEventHandler
{

    public GameObject[] maps;


   // private TrackableBehaviour mTrackableBehaviour;
  public   GameObject ActualMap;
    GameObject OldMap;

    public int id = 0;

    Vector3 savedBallposition;

    public MainMenuController menuController;

    public int level;

	// Use this for initialization
	void Start () {

        SaveGame.Load();
        //Debug.Log(SaveGame.Instance.Level);
        level = SaveGame.Instance.Level;


        mTrackableBehaviour = GetComponent<TrackableBehaviour>();

        if (mTrackableBehaviour)
        {
            mTrackableBehaviour.RegisterTrackableEventHandler(this);
        }

        Debug.Log("Creando un mapa");

        //Recuperamos la posicion de la bola
        //    ball.transform.position = savedBallposition;

        //Creamos mapa aleatorio
        //   ActualMap = returnRandom();


            //  Instantiate(ActualMap,this.transform);
        //    ActualMap.SetActive(false);

    }

    public void init(int i)
    {


        id = i;
        ActualMap = Instantiate(maps[i], transform.position, transform.rotation);
        ActualMap.transform.parent = gameObject.transform;
        ActualMap.transform.localScale = new Vector3(0.07f, 0.07f, 0.07f);
        ActualMap.transform.localPosition = new Vector3(0, 0.1728f, 0);
        
    }

    public void nextLevel()
    {
        Destroy(ActualMap);
        id++;
 
        init(id);
    }

    public void repeatLevel()
    {
        Destroy(ActualMap);
        init(id);
    }

    // Update is called once per frame
    void Update()
    {

        // Si la bola ha llegado a su meta

        if (ActualMap != null)
        {
            if (ActualMap.GetComponent<PuzzleManager>().lose == true)
            {
                menuController.lose();
                Destroy(ActualMap);

            }
            else if (ActualMap.GetComponent<PuzzleManager>().win == true)
            {
                if (id == level) {
                    level++;
                    SaveGame.Instance.Level = level;
                
                    SaveGame.Save();
                }

                menuController.win();
                Destroy(ActualMap);

            }
        }
		
	}

    
    public new void OnTrackableStateChanged(
         TrackableBehaviour.Status previousStatus,
          TrackableBehaviour.Status newStatus)
    {
        
     if (newStatus == TrackableBehaviour.Status.DETECTED ||
            newStatus == TrackableBehaviour.Status.TRACKED ||
            newStatus == TrackableBehaviour.Status.EXTENDED_TRACKED)
    {
        Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " found");
        OnTrackingFound();
    }
        else if (previousStatus == TrackableBehaviour.Status.TRACKED &&
                 newStatus == TrackableBehaviour.Status.NO_POSE)
        {
            Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " lost");
            OnTrackingLost();
}
        else
        {
            // For combo of previousStatus=UNKNOWN + newStatus=UNKNOWN|NOT_FOUND
            // Vuforia is starting, but tracking has not been lost or found yet
            // Call OnTrackingLost() to hide the augmentations
            OnTrackingLost();
        }

    }



    public override void OnTrackingFound()
    {
        base.OnTrackingFound();
        if (ActualMap != null)
        {
            ActualMap.SetActive(true);
        }
        
    }

    protected void OnTrackingLost()
    {
    }

    public GameObject returnRandom()
    {
        int i = Random.Range(0,maps.Length);      
        return maps[i];

    }



}
