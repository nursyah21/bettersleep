import { StatusBar } from 'expo-status-bar';
import { StatusBar as rnStatusBar, Touchable, TouchableOpacity } from 'react-native';
import { useEffect, useState } from 'react';
import { StyleSheet, Text, View, Image, InteractionManager } from 'react-native';
import SetAlarm from '../components/SetAlarm';

import { Dimensions } from 'react-native';

export default function Sleep(props:any) {
  const initialAlarm:any = {
    0:["none","flex"],
    1:["flex","none"]
  }
  const [mytime, setTime] = useState("")
  const [alarmMode, setAlarmMode] = useState(initialAlarm[0])
  
  function time(){
    let hours:String|Number = new Date().getHours()
    let minutes:String|Number = new Date().getMinutes()
    let seconds:String|Number = new Date().getSeconds()
    
    hours = hours.toString().length == 1 ? "0" + hours : hours
    minutes = minutes.toString().length == 1 ? "0" + minutes : minutes
    // seconds = seconds.toString().length == 1 ? "0" + seconds : seconds
    
    return hours + " : " + minutes //+ " : " + seconds
  }
  
  useEffect(()=>{
    const timer = setInterval(()=>{setTime(time())
    }
      , 1000) //update time every seconds
    return () => clearInterval(timer)
  },[])

  return (
    <View style={[styles.v3, {display: props.display}]}>
        <SetAlarm display={alarmMode[0]} />

        <View style={[styles.container, ]}>
          <Image source={require('../images/sleep_background.png')} style={styles.background}/>

        {/* clock */}
        <Text style={styles.a4}>good night</Text>
        <Text style={styles.a3}>{mytime}</Text>
        <Image source={require("../images/astronout_sleep.png")} style={styles.a1} />

        {/* set alarm */}
        <TouchableOpacity style={styles.v2} onPress={()=>setAlarmMode(initialAlarm[1])}>
          <Image source={require("../images/alarm.png")} style={styles.i1}/>
          <Text style={[styles.t1]}>04:00</Text>
        </TouchableOpacity>


        {/* <View style={styles.a2} /> */}
        <TouchableOpacity onPress={()=>console.log()} style={[styles.a6, {display:alarmMode[1]}]}>
          <View style={styles.v1}>
            <Text style={styles.a5}>START SLEEPING</Text>
          </View>
        </TouchableOpacity>
        
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  v3:{
    width:"100%",height:"100%"
  },
  t1:{
    position:"absolute",
    alignSelf:"center",
    transform:[
      {translateX:10}
    ],
    color: "#fff"
  },
  i1:{
    height: 20,
    resizeMode: "contain",
  },
  v2:{
    justifyContent:"center",
    width: 100, height: 40,
    borderRadius:60,
    backgroundColor: "#6960AF",
    position:"absolute",
    alignSelf: "center",
    transform:[{translateY:50}],
    paddingVertical: 10
  },
  v1:{
    width: "100%",
    backgroundColor: "#3828B7",
    padding: 5,
    paddingVertical: 10,
    borderRadius: 40
  },
  a6:{
    bottom: 10,
    position: "absolute",
    marginBottom: 10,
    width: "100%",
    padding: 10
  },
  a5:{
    color: "#fff",
    fontSize: 16,
    alignSelf: "center"
  },
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
    top:"30%",
    position:"absolute",
    resizeMode:"contain",
    borderRadius: 200,
    borderWidth: 5,
    borderColor: "#aaa",
  },
  a1:{
    width: 180, height: 180, 
    top:"25%",
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
