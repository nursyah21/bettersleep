import { StatusBar } from 'react-native';
import { useEffect, useState } from 'react';
import { StyleSheet, Text, View, Image } from 'react-native';
import Svg, {Circle} from 'react-native-svg';

function CircularProgress(){
  const styles:any = StyleSheet.create({
    v1:{
      width:150, height:150,
      borderWidth:3,borderRadius:100,
      borderColor: "#3828B7",
      top: "30%",
      zIndex: 4,
      position: "absolute"
    }
  })


  return (
    <View>
    </View>
  )
  
}

export default function Home(props:any) {
  //const formatTime;
  const formatTime = () =>{
    var hours = new Date().getHours().toString()
    var minutes = new Date().getMinutes().toString()
    var seconds = new Date().getSeconds().toString()
    minutes = (minutes.length == 1) ? "0"+minutes : minutes
    seconds = (seconds.length == 1) ? "0"+seconds : seconds

    return minutes + ":" + seconds
  }

  

  const [time, setTime] = useState("")
  const [progress, setProgress] = useState(0)
  const [test, setTest]:any = useState()

  useEffect(()=>{
    function sleepProgress(){
      var finish = 60
      var seconds = new Date().getSeconds()
      var myprogress = Math.floor(seconds/finish * 360)
      // setTest(myprogress + ": " + progress.x + " " + progress.y)
      
      var sec = progress
      // setProgress(sec+"deg")
      if (myprogress >= 0){
        console.log(sec)
        // console.log(progress.split("deg")[0])
      }
      // if progress 0 >= 90 >= 180 >= 270 
    }

    const time=setInterval(()=>{
      sleepProgress()
      
      setProgress(progress+1)
      setTime(formatTime())
    }, 1000)

    return () => {
      clearInterval(time)
    }
  }, [])

  return (
    <View style={[styles.container, {display: props.display} ]} >
        <Image source={require('../images/sleep_background.png')} style={styles.background}/>

        <Text style={styles.t1}>{time}</Text>
        {/* <Text style={[styles.t1, {top:80}]}>{test}</Text> */}
        
        <Image source={require("../images/astronout_sleep.png")} style={[styles.i1, {transform:[{rotate:progress+"deg"}]}]} />

        {/* <View style={[styles.v1, ]} /> */}
        {/* CircularProgress */}
        {/* <CircularProgress /> */}
    </View>
  );
}

const styles = StyleSheet.create({
  t1:{
    color:"#fff",
    fontSize:30,
    position:"absolute",
    top:StatusBar.currentHeight
  },
  v1:{
    width:75, height:10,
    borderWidth:1,borderRadius:100,
    position:"absolute",
    alignSelf:"center",
    left:30,
    // color:"#3828B7",
    borderColor: "#3828B7",
    top: "30%",
    zIndex: 4,
    transform:[
      {rotateX:"270deg"},
      {translateX:0},
      {translateY:0}
    ],
  },
  i1:{
    width:150, height: 150, 
    borderColor: "#3828B7",
    borderWidth: 3, borderRadius: 100, 
    zIndex:3,
    position: "absolute", 
    top: "30%"
  },
  background: {
      width: "100%", height: "100%", position: "absolute",
          top: 0, left: 0
  },
  container: {
    flex: 1,
    background: "#fff",
    alignItems: 'center',
    justifyContent: 'center',
  },
  });
