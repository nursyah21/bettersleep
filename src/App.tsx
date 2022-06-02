import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, Touchable, TouchableWithoutFeedbackBase, View,
TouchableOpacity, Image, BackHandler } from 'react-native';
import store from './store';
import { Provider, useDispatch } from 'react-redux';
import Home from './layouts/Home';
import { stylesBottom, HomeTab, SleepTab, StatisticsTab, movepage as movepageNav,
varActivePage } from './components/Navigation';

import { useEffect, useState } from 'react';
import Statistics from './layouts/Statistics';
import Sleep from './layouts/Sleep';

export default function App() {

  const [display,setDisplay]:any = useState(varActivePage.home)
  const [moveNavbar, setmoveNavbar] = useState("statistics")

  function movepage(e:any){
    switch (e){
      case "home":
        setmoveNavbar("statistics")
        setDisplay(varActivePage.home);break
      case "sleep":setDisplay(varActivePage.sleep);break
      case "statistics":
        setmoveNavbar("home")
        setDisplay(varActivePage.statistics);
        // setImageNavbar("./images/home_icon.png")
        break
    }
  }

  useEffect(()=>{
    // BackHandler.addEventListener('hardwareBackPress',()=>{
    //   if(display.statistics === "flex"){
    //     movepage("home")
    //     console.log(display.statistics)
    //     return true
    //   }else{
    //     console.log(display.statistics)

    //     return false
    //   }
    //   return false
    // })
  })

  return (
    <Provider store={store}>


      <Home display={display.home} />
        
      <Statistics display={display.statistics} />

      {/* bottom nav */}
      <View style={[stylesBottom.container, {display:display.navbar}]}  >

        <TouchableOpacity onPress={()=>movepage(moveNavbar)} >
            <Image source={require("./images/home_icon.png")} style={[stylesBottom.icon, {display: display.statistics}]} />
            <Image source={require("./images/statistics_icon.png")} style={[stylesBottom.icon, {display: display.home}]} />
        </TouchableOpacity>
      </View>



      <StatusBar style="auto" />
    </Provider>
        
      
  );
}

const styles = StyleSheet.create({
  container: {
  },
});
