import { BackHandler, FlatList, Modal, ScrollView, StatusBar, TouchableOpacity } from 'react-native';
import { Component, useEffect, useState } from 'react';
import { StyleSheet, Text, View, Image } from 'react-native';
import Svg, {Circle} from 'react-native-svg';

// clean this code

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

function MySleep(props:any){
  const [progress, setProgress] = useState(0)

  const style=StyleSheet.create({
    v1:{
      width:1000, height:10,
      top:0,left:0,
      zIndex:10,
      position:"absolute",
      color:"#fff"
    }
  })

  useEffect(()=>{

    const mytime = setTimeout(()=>{
      setProgress(progress+1)
      if(progress >= 360)setProgress(0)
      // console.log(progress)
    }, 1000)

    return ()=>clearTimeout(mytime)
  })

  return (
    <View style={{
      width:150, height:10, 
      borderRightWidth:3,
      
      top:"30%",
      
      borderColor:"#fff",
      position:"absolute",
      transform:[
        {translateY:70},
        {translateX:0},
        {rotate:progress+"deg"}
        // {rotate:}
      ]
    }}></View>
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

    return hours + ":" + minutes
    // return minutes + ":" + seconds
  }

  

  const [time, setTime] = useState("")
  const [displayAlarm, setDisplayAlarm] = useState({0:false,1:"none",}
  )
  const [progress, setProgress] = useState(0)
  const [test, setTest]:any = useState()

  useEffect(()=>{

    function sleepProgress(){
      var finish = 60
      var seconds = new Date().getSeconds()
      var myprogress = Math.floor(seconds/finish * 360)
      
      var sec = progress
      if (myprogress >= 0){
        // console.log(sec + "deg")
      } 
    }

    const time=setInterval(()=>{
      sleepProgress()
      // setProgress(progress+1)
      setTime(formatTime())
      
    }, 1000)

    
    return () => {
      clearInterval(time)
    }
  })

  const hours = ()=>{
    var res = []
    for(var i=0; i < 24; i++){
      if(i < 10) res.push("0"+i)
      else res.push(i)
    }
    return res
  }
  const minutes = ()=>{
    var res = []
    for(var i=0; i < 60; i++){
      if(i < 10) res.push("0"+i)
      else res.push(i)
    }
    return res
  }

  const [displaySleep, setDisplaySleep] = useState(true)

  return (
    <View style={[styles.container, {display: props.display} ]} >

      {/* modesleep */}

      {/* set sleep */}
      <Modal visible={displayAlarm[0]}>

        <View style={{
          backgroundColor:"#251751",
          width:"100%",
          height:"100%",
          position:"absolute",
          // opacity:0,
          padding:15
        }}>
          <TouchableOpacity style={{
            width:30,height:30
          }} onPress={()=>setDisplayAlarm({0:false, 1:"none"})}>
            <Image source={require("../images/reject.png")} style={{
              width:30,height:30
            }}/>
          </TouchableOpacity>
          
            <View style={{
              alignItems:"center"
            }}>
              <Image source={require("../images/bigclock.png")} style={{
                width:200, height:200
              }}/>
              <Text style={{
                fontSize:40,
                color:"#fff",
                position:"absolute",
                top:70
              }}>8h</Text>
  
            </View>
            
            <View style={{
              flexDirection:"row",
              justifyContent:"space-around"}}>

            {/* set bedtime */} 
            <TouchableOpacity style={{
                    width:120, height:65,
                    backgroundColor:"#fff",
                    borderRadius:10,
                    margin:15,
                    alignItems:"center"
                  }}>
                    <View style={{
                      alignItems:"center",
                      top:-10
                    }}>
                    <View style={{
                      width:25, height:25,
                      backgroundColor:"#251751",
                      justifyContent:"center",
                      alignItems:"center",
                      borderRadius:100,
                      marginBottom:3,
                    }}>
                    <Image source={require("../images/bed.png")} style={{
                      width:20, height:20,
                      backgroundColor:"#251751",
                      borderRadius:100
                      }} /></View>
                      <Text style={{
                        fontSize:12,
                        marginBottom:2
                      }}>BedTime</Text>
                      <Text style={{
                        fontWeight:"bold",
                      }}>10:00 PM</Text>
                  </View>
                </TouchableOpacity>

            {/* set wakeup */}
            <TouchableOpacity style={{
                    width:120, height:65,
                    backgroundColor:"#fff",
                    borderRadius:10,
                    margin:15,
                    alignItems:"center"
                  }}>
                    <View style={{
                      alignItems:"center",
                      top:-10
                    }}>
                    <View style={{
                      width:25, height:25,
                      backgroundColor:"#251751",
                      justifyContent:"center",
                      alignItems:"center",
                      borderRadius:100,
                      marginBottom:3,
                    }}>
                    <Image source={require("../images/bellalarm.png")} style={{
                      width:20, height:20,
                      backgroundColor:"#251751",
                      borderRadius:100
                      }} /></View>
                      <Text style={{
                        fontSize:12,
                        marginBottom:2
                      }}>Wakeup</Text>
                      <Text style={{
                        fontWeight:"bold",
                      }}>06:00 AM</Text>
                  </View>
                </TouchableOpacity>

              </View>
            <View style={{justifyContent:"space-around"}}>
            {/* sleepgoal */}
            <TouchableOpacity style={{
                    width:"88%", height:60,
                    backgroundColor:"#fff",
                    borderRadius:10,
                    margin:15,
                    marginLeft:20,
                    justifyContent:"center",
                  }}>
                    <View style={{
                      flexDirection:"row",
                      alignItems:"center",
                      
                    }}>
                    <Image source={require("../images/check.png")} style={{
                width:30, height:30,
                left:10, marginRight:20,
              }}/>
              <Text style={{
                fontSize:15,
                fontWeight:"bold"
              }}>Sleep Goal</Text>


              <Text style={{
                fontSize:15,
                fontWeight:"bold",
                textDecorationLine:"underline",
                left:120
              }}>8</Text>
              <Text style={{
                fontSize:15,
                fontWeight:"bold",
                left:122
              }}>h</Text>

            </View>
              
            </TouchableOpacity>
            {/* set alarm */}
            <TouchableOpacity style={{
                    width:"88%", height:40,
                    backgroundColor:"#3828B7",
                    borderRadius:100,
                    margin:15,
                    marginLeft:20,
                    justifyContent:"center",
                    alignItems:"center"
                  }}>
                    <Text style={{
                      color:"#fff",
                      fontSize:16,
                      fontWeight:"bold"
                    }}>
                      Set Alarm
                      </Text>
              </TouchableOpacity>
            </View>
            
            

        </View>

        {/* set alarm */}
        <View style={{
           backgroundColor:"#251751",
           width:"100%",
           height:"100%",
           position:"absolute",
           padding:15,
           display:"none"
        }}>
         
        </View>
      </Modal>

        <Image source={require('../images/sleep_background.png')} style={styles.background}/>
        

        <Text style={styles.t1}>{time}</Text>
        
        
        {/* astronout */}
        <TouchableOpacity style={[styles.i1,{
          
        }]} onPress={()=>{console.log("sleep")}}>
          <Image source={require("../images/astronout_sleep.png")} style={[styles.i1, {
            top:0,
            borderWidth:0,
            transform:[{rotate:progress+"deg"}]
            }]} />

        </TouchableOpacity>
        <TouchableOpacity style={{
          width:80,height:25,
          borderRadius: 100,
          top:60,
          backgroundColor: "#6352A0",
          alignItems:"center",
          justifyContent:"center",
          flexDirection:"row",
        }} onPress={()=>{
          setDisplayAlarm({0:true,1:"flex"})}
          }>
          <Image source={require("../images/alarm.png")} style={{
            width:15, height:15,
            marginRight:4,
          }}/>
          <Text style={{
            color:"#fff",
            fontSize: 13
          }}>04:00</Text>


        </TouchableOpacity>
        

      <View style={{
        position:"absolute",
        width:"100%",
        top:0,
        left:0,
        backgroundColor:"#251751",
        height:"100%",
        display: displayAlarm[1]
      }}>

      </View>

    </View>
  );
}

const styles = StyleSheet.create({
  v2:{

  },
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
          top: 0, left: 0,
          
  },
  container: {
    flex: 1,
    background: "#fff",
    alignItems: 'center',
    justifyContent: 'center',
  },
  });
