using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class ProfilesMenu : MonoBehaviour
{
    [SerializeField] private Dropdown profilesDropdown;
    [SerializeField] private TMP_InputField inputField;
    [SerializeField] private Dropdown colorDropdown;

    private void Start()
    {
        updateProfileDropdown();
        List<string> options = new List<string>();
        options.Add("green");
        options.Add("blue");
        options.Add("red");
        colorDropdown.ClearOptions();
        colorDropdown.AddOptions(options);
        colorDropdown.RefreshShownValue();
    }

    public void AddProfile() 
    {
        if (string.IsNullOrEmpty(inputField.text))
            return;

        Profile profile = new Profile();
        profile.color = colorDropdown.value;
        profile.name = inputField.text;
        DataManager.instance.AddProfile(profile);
        updateProfileDropdown();
        OnSelectedProfile();
    }

    private void updateProfileDropdown() 
    {
        profilesDropdown.ClearOptions();
        List<string> options = new List<string>();
        foreach (Profile p in DataManager.instance.profiles)
        {
            options.Add(p.name);
        }
        profilesDropdown.AddOptions(options);
        profilesDropdown.RefreshShownValue();
        profilesDropdown.value = options.Count > 0 ? options.Count - 1 : 0;
    }

    public void OnSelectedProfile() 
    {
        DataManager.instance.ChooseProfile(profilesDropdown.value);
        colorDropdown.value = DataManager.instance.currentProfile.color;
    }
}
