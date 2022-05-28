import { useState } from "react";
import { StyleSheet, View, Image, StatusBar, TouchableOpacity } from "react-native";
import { useSelector, useDispatch } from "react-redux";

export default function SetAlarm(props:any){
    
    // const [display, setDisplay] = useState(props.display)

    return (
        <View style={[styles.v1, {display:props.display}]}>
            <TouchableOpacity onPress={()=>console.log("none")} style={{
                width:30, height: 30
            }}>
              <Image source={require("../images/reject.png")} style={{ 
                width:30, height: 30, 
                margin:10}} /></TouchableOpacity>
        </View>
    )

}

const styles = StyleSheet.create({
    i1:{
        width:30, height: 30, 
        margin:10
    },
    v1:{
        width:"100%", height:"100%",
        paddingTop: StatusBar.currentHeight,
        backgroundColor: "#251751",
    }
})
