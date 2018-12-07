
using UnityEngine;
using System.Collections;
using Vuforia;

public class AutoFocus : MonoBehaviour
{
    bool autoFocusEnabled = true;

    void Start()
    {
        var vuforia = VuforiaARController.Instance;
        vuforia.RegisterVuforiaStartedCallback(OnVuforiaStarted);
        vuforia.RegisterOnPauseCallback(OnPaused);
    }

    private void OnVuforiaStarted()
    {
        CameraDevice.Instance.SetFocusMode(
            CameraDevice.FocusMode.FOCUS_MODE_CONTINUOUSAUTO);
    }

    private void OnPaused(bool paused)
    {
        if (!paused) // resumed
        {
            if (autoFocusEnabled)
            {
                // Set again autofocus mode when app is resumed
                CameraDevice.Instance.SetFocusMode(
               CameraDevice.FocusMode.FOCUS_MODE_CONTINUOUSAUTO);
            }
            else
            {
                CameraDevice.Instance.SetFocusMode(
                CameraDevice.FocusMode.FOCUS_MODE_INFINITY);
            }
        }
    }

    public void switchCameraFocus()
    {
        if (autoFocusEnabled)
        {
            autoFocusEnabled = false;
        CameraDevice.Instance.SetFocusMode(
           CameraDevice.FocusMode.FOCUS_MODE_INFINITY);
        }
        else
        {
            autoFocusEnabled = true;
            CameraDevice.Instance.SetFocusMode(
              CameraDevice.FocusMode.FOCUS_MODE_CONTINUOUSAUTO);
        }
    }
}