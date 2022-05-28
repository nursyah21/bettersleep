import { View, Image, StyleSheet, TouchableOpacity, Touchable } from "react-native"
import React, { useState } from "react"
import { useDispatch } from "react-redux"
import store from "../store"

export const varActivePage = {
  "home":{
    home:"flex",
    sleep: "none",
    statistics: "none",
    navbar: "flex"
  },
  "sleep":{
    home:"none",
    sleep: "flex",
    statistics: "none",
    navbar: "none",
  },
  "statistics":{
    home:"none",
    sleep: "none",
    statistics: "flex",
    navbar: "flex"
  }
}

export const movepage = (e:String) =>{
  store.dispatch({type:`display/${e}`})
  // console.log(store.getState().display)
  // store.subscribe(App)
}

export function HomeTab(props:any){
  return(
    <TouchableOpacity onPress={()=>movepage(props.change)} >
        <Image source={require("../images/home_icon.png")} style={styles.icon} />
    </TouchableOpacity>
  )
}

export function SleepTab(props:any){
  return (
    <TouchableOpacity onPress={()=>movepage(props.change)}>
        <Image source={require("../images/moon_icon.png")} style={styles.icon} /> 
    </TouchableOpacity>
  )
}

export function StatisticsTab(props:any){
  return (
    <TouchableOpacity onPress={()=>movepage(props.change)}>
      <Image source={require("../images/statistics_icon.png")} style={styles.icon} />
    </TouchableOpacity>
  )
}

const styles = StyleSheet.create({
  container:{
    backgroundColor: "#251751",
    position: "absolute",
    flexDirection:"row",
    bottom: 0,
    width: "100%",
    padding:5,
    paddingVertical: 10,
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    justifyContent: "space-evenly"
  },
  icon:{
    width: 40, height: 40,
    marginHorizontal: 10,
    // justifyContent: "space-around",
  }
})


export const stylesBottom = styles

