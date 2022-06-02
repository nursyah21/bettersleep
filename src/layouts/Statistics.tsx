import { useState } from 'react';
import { StatusBar, TouchableOpacityBase, TouchableOpacity, ScrollView } from 'react-native';
import { StyleSheet, Text, View, Image } from 'react-native';
import LinearGradient from 'react-native-linear-gradient';
import {faker} from "@faker-js/faker"

const color = {
  "active":"#9D91FF",
  "nonActive":"#3828B7"
}

const dummydata = {
  2022:{
    5:{
      1:10,
      2:8,
      3:5,
      4:5,
      5:10,
      6:8,
      7:2,
      8:10,
      9:8,
      10:5,
      11:5,
      12:10,
      13:8,
      14:2
    }
  }
}
const Month = ["","Jan","Feb","Mar","Apr","May","Jun",
"Jul","Aug","Sep","Oct","Nov","Dec"]

function StatisticsChart(){

  // Data
  const test = dummydata[2022][5]
  const maxTest = Math.max(...Object.values(test))
  const yearnow = Number(Object.keys(dummydata)[0])
  const monthnow = Month[5]

  return(
    <View style={{margin:10}}>
      <Text style={{color:"#fff"}}>
        {monthnow} {yearnow}
        </Text>

      {/* // bar */}
      <ScrollView horizontal={true} 
      onScrollAnimationEnd={()=>console.log("end")}
      onMomentumScrollEnd={()=>console.log("  ")}
      >
      <View style={{
        flexDirection:"row", marginTop:18,
        }}>
        {Object.entries(test).map((e,idx)=>{
          return (
            <View style={{
              flexDirection:"column", justifyContent:"space-between"
            }} key={idx}>
              
              <View style={{
                height:e[1]*10, width:20, backgroundColor:"#fff",
                margin:3, top: (maxTest-e[1])*10,
                marginHorizontal:10
              }}>
                <Text style={{
                color:"#fff",
                top:-16, 
                alignSelf:"center",
                fontSize:12  
              }}>{e[1]}h</Text>
              </View>
              
              <Text style={{
                color:"#fff", alignSelf:"center"  
              }}>{e[0]}</Text>
          </View>
          )
        })}
      </View>
      </ScrollView>

    </View>
  )
}

export default function Statistics(props:any) {
  
  const [active, setActive] = useState("#9D91FF");

  return (
    <View style={[styles.container, {display: props.display}]}>
        <Image source={require('../images/sleep_background.png')} style={styles.background}/>
        <View style={{
          top:StatusBar.currentHeight, 
          width:"80%",
          position:"absolute",
        }}>
          <Text style={{
        color: "white",
        alignSelf:"center",
        fontSize:24,
        fontWeight:"bold" }}>Statistics</Text>
        
        {/* Week Mont Year */}
        <View style={{
          width:"100%", height:30,
          backgroundColor:"#3828B7",
          top:10,
          borderRadius:100,
          flexDirection:"row",
          justifyContent:"space-around"
        }}>
          {/* Week */}
          <TouchableOpacity style={{
            backgroundColor:color.nonActive,
            width:80,
            height:30, borderRadius: 6,
            alignItems:"center",
            justifyContent:"center",
            display:"none"
          }}
          >
            <Text style={{
              color:"#fff"
            }}>Week</Text>
          </TouchableOpacity>

          {/* Month */}
          <TouchableOpacity style={{
            backgroundColor:color.nonActive,
            width:80,
            height:30, borderRadius: 6,
            alignItems:"center",
            justifyContent:"center",
          }}
          >
            <Text style={{
              color:"#fff"
            }}>Month</Text>
          </TouchableOpacity>

          {/* Year */}
          <TouchableOpacity style={{
            backgroundColor:color.nonActive,
            width:80,
            height:30, borderRadius: 6,
            alignItems:"center",
            justifyContent:"center",
            display:"none"
          }}
          >
            <Text style={{
              color:"#fff"
            }}>Year</Text>
          </TouchableOpacity>
        </View>
        {/* jan - 1 feb 2022 */}

        <Text style={{
          color:"#fff",
          top:40
        }}>1 Jan - 1 Feb 2022</Text>

        {/* average max */}
        <View style={{
          flexDirection:"row",
          top: 50,
        }}>
          {/* Average */}
          <View style={{
            width:120, height:80,
            borderRadius:10,
            backgroundColor:color.nonActive,
          }}>
            <View style={{
              flexDirection:"row",
              margin:10, alignItems:"center"
            }}>
             <LinearGradient colors={["#311B6B",
             "#6728B7"]}
             style={{
              width:15, height:15, borderRadius:20
             }} />
             <Text style={{
               left: 10,
               color:"#fff"
             }}>Average</Text>
            </View>
            <View style={{
            marginHorizontal:10
            }}>
              <Text style={{
                color:"#fff",
                fontWeight:"bold",
                fontSize:24
              }}>8h</Text>
            </View>
          </View>

          {/* Max */}
          <View style={{
            width:120, height:80,
            borderRadius:10,
            backgroundColor:color.nonActive,
            marginLeft:48
          }}>
            <View style={{
              flexDirection:"row",
              margin:10, alignItems:"center"
            }}>
             <LinearGradient colors={["#9D91FF",
             "#91FFB6"]}
             style={{
              width:15, height:15, borderRadius:20
             }} />
             <Text style={{
               left: 10,
               color:"#fff"
             }}>Max</Text>
            </View>
            <View style={{
            marginHorizontal:10
            }}>
              <Text style={{
                color:"#fff",
                fontWeight:"bold",
                fontSize:24
              }}>10h</Text>
            </View>
          </View>
        </View>

        {/* Statistics */}
        <View style={{
            width:"100%", height:200,
            borderRadius:10,
            backgroundColor:color.nonActive,
            top:70
          }}>
            <StatisticsChart />
          </View>
        </View>
      
    </View>
  );
}

const styles = StyleSheet.create({
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
