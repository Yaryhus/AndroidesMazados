using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Vuforia;


[RequireComponent(typeof(AudioSource))]
[RequireComponent(typeof(Rigidbody))]

public class Ball : MonoBehaviour, ITrackableEventHandler
{


   private TrackableBehaviour mTrackableBehaviour;

    GameObject Floor;
    GameObject Point;

    MainMenuController menuController;



    //public GameObject Spawn;
    private bool collided = false;
    public AudioSource source;

    public bool Scored = false;

    Vector3 savedBallposition;
    Vector3 initialBallPosition;
    Rigidbody rb;

    // Use this for initialization
    void Start () {

        source = this.GetComponent<AudioSource>();

        menuController = GameObject.Find("MainMenuController").GetComponent<MainMenuController>();


        //Aumentamos la velocidad de caida de la ficha.
        rb =this.GetComponent<Rigidbody>();

        mTrackableBehaviour = GetComponent<TrackableBehaviour>();

        if (mTrackableBehaviour)
        {
            mTrackableBehaviour.RegisterTrackableEventHandler(this);
        }

        initialBallPosition = transform.localPosition;

        Floor = GameObject.FindWithTag("Floor");
        Point = GameObject.FindWithTag("Point");

    }

    // Update is called once per frame
    void Update () {

        //Guardamos la posicion de la pelota
        savedBallposition = transform.position;

        //rb.velocity += new Vector3(0, -5, 0);

        //Si se saliese del mapa
        
        if (transform.localPosition.y < Floor.transform.localPosition.y - 10)
        {
            transform.localPosition = initialBallPosition;         
        }
        



        /*
             Vector3 VectorResult;

        Plane plane = new Plane();

        Debug.DrawRay(Floor.transform.position, Floor.transform.up * 100, Color.green);

        

        plane.SetNormalAndPosition(Floor.transform.up, Floor.transform.position);
        if (!plane.SameSide(transform.position, Point.transform.position))
        {
           transform.localPosition = initialBallPosition;
        }
        */

    }

    public void OnTrackableStateChanged(
              TrackableBehaviour.Status previousStatus,
              TrackableBehaviour.Status newStatus)
    {
        if (newStatus == TrackableBehaviour.Status.DETECTED ||
            newStatus == TrackableBehaviour.Status.TRACKED)
        {
            OnTrackingFound();
        }

    }
    private void OnTrackingFound()
    {
        //La colocamos donde la recordamos por ultima vez
        transform.position = savedBallposition;
      //  transform.localPosition = initialBallPosition;


    }
    /*

    public void resetBall()
    {
        //Reseteamos posicion y booleano de ganar
        Scored = false;
        transform.position = Spawn.transform.position;
    }

    */

    
        //Pruebas
    /*
    private void OnCollisionEnter(Collision collision)
    {

    }

    private void OnCollisionExit(Collision collision)
    {
        StartCoroutine(esperaVibrate());
        collided = false;
    }

    IEnumerator esperaVibrate()
    {
        yield return new WaitForSeconds(1.0f);
    }
    */

    private void OnTriggerEnter(Collider collision)
    {

        if (collided == false)
        {
            if (collision.gameObject.CompareTag("Choque"))
            {
                collided = true;

                if (menuController.soundEnabled)               
                    source.Play();

                if (menuController.vibrationEnabled)
                    Vibration.Vibrate(50);
            }
            collided = false;
        }

        if (collision.gameObject.tag == "Exit")
        {
            if (menuController.vibrationEnabled)
                Vibration.Vibrate(300);

            Scored = true;

            gameObject.SetActive(false);

        }


    }
}
