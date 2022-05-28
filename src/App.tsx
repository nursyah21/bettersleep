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

  function movepage(e:any){
    switch (e){
      case "home":setDisplay(varActivePage.home);break
      case "sleep":setDisplay(varActivePage.sleep);break
      case "statistics":setDisplay(varActivePage.statistics);break
    }
  }

  useEffect(()=>{
    BackHandler.addEventListener('hardwareBackPress',()=>{
      if(display.sleep === "flex"){
        movepage("home")
        return true
      }
    })
  })

  return (
    <Provider store={store}>

      <Home display={display.home} />
      <Statistics display={display.statistics} />


      {/* bottom nav */}
      <View style={[stylesBottom.container, {display:display.navbar}]}  >

        <TouchableOpacity onPress={()=>movepage("statistics")} >
            <Image source={require("./images/statistics_icon.png")} style={stylesBottom.icon} />
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
