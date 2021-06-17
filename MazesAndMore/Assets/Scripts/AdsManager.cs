using System.Collections;
using UnityEngine;
using UnityEngine.Advertisements;
namespace MazesAndMore
{
    public class AdsManager : MonoBehaviour
    {

        public string gameId = "4173829";
        public string bannerId = "bannerPlacement";
        public string rewardedId = "rewardedVideo";
        public string advertisId = "video";
        public bool testMode = true;

//#ifdef UNITY_EDITOR 
//        bool test;
//#elseif UNITY_ANDROID
//        bool testI;
//#endif


        //void Start()
        //{
        //    // Initialize the SDK if you haven't already done so:
        //    Advertisement.Initialize(gameId, testMode);
        //    StartCoroutine(ShowBannerWhenReady());
        //}

        //IEnumerator ShowBannerWhenReady()
        //{
        //    while (!Advertisement.IsReady(placementId))
        //    {
        //        yield return new WaitForSeconds(0.5f);
        //    }
        //    Advertisement.Banner.Show(placementId);
        //}
    }
}