import { StatusBar } from 'expo-status-bar';
import { StatusBar as rnStatusBar } from 'react-native';
import { useEffect, useState } from 'react';
import { StyleSheet, Text, View, Image, InteractionManager } from 'react-native';
import { Dimensions } from 'react-native';

export default function Sleep(props:any) {

  const [mytime, setTime] = useState("")
  
  function time(){
    let hours:String|Number = new Date().getHours()
    let minutes:String|Number = new Date().getMinutes()
    let seconds:String|Number = new Date().getSeconds()
    
    hours = hours.toString().length == 1 ? "0" + hours : hours
    minutes = minutes.toString().length == 1 ? "0" + minutes : minutes
    seconds = seconds.toString().length == 1 ? "0" + seconds : seconds
    
    return hours + " : " + minutes + " : " + seconds
  }
  
  useEffect(()=>{
    const timer = setInterval(()=>{setTime(time())
    }
      , 1000) //update time every seconds
    return () => clearInterval(timer)
  },[])

  return (
    <View style={[styles.container, {display: props.display}]}>
        <Image source={require('../images/sleep_background.png')} style={styles.background}/>

      {/* clock */}
      <Text style={styles.a4}>good night</Text>
      <Text style={styles.a3}>{mytime}</Text>
      <Image source={require("../images/astronout_sleep.png")} style={styles.a1} />
      {/* <View style={styles.a2} /> */}
      
      
    </View>
  );
}

const styles = StyleSheet.create({
  a4:{
    color:"#fff",
    top: rnStatusBar.currentHeight,
    marginTop: 10,
    position: "absolute",
  },
  a3:{
    position:"absolute",
    top: rnStatusBar.currentHeight,
    marginTop: 30,
    color:"#fff",
    fontSize:40,
    fontWeight: "bold"
  },
  a2:{
    // maxWidth: 200, maxHeight: 200, 
    
    top:"30%",
    position:"absolute",
    resizeMode:"contain",
    borderRadius: 200,
    borderWidth: 5,
    borderColor: "#aaa",
  },
  a1:{
    width: 200, height: 200, 
    top:"30%",
    position:"absolute",
    resizeMode:"contain",
    borderRadius: 200,
    borderWidth: 5,
    borderColor: "#fff",
    transform:[
      {rotate:"360deg"}
    ]
  },
  text: {
    color: "white"
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
