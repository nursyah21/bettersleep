import React, { useEffect, useReducer, useRef, useState } from "react";

import {
  Animated,
  Image,
  StatusBar,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import Navbar from "./components/Navbar";
import { images } from "./images/images";
import Sleep from "./Pages/Sleep";
import Statistics from "./Pages/Statistics";
import { colors, styles } from "./styles";

const initialStates = {
  statistics: "none",
  sleep: "none",
  navbar: images.home_icon,
};

const reducer = (state, action) => {
  switch (action.type) {
    case "statistics":
      if (state.sleep && state.sleep === "flex") {
        return { ...state, sleep: "none", navbar: images.home_icon };
      }
      return state.statistics === "flex"
        ? { ...state, statistics: "none", navbar: images.home_icon }
        : { ...state, statistics: "flex", navbar: images.statistics_icon };
    case "sleep":
      return { ...state, sleep: "flex", navbar: images.moon_icon };
  }
};

const getTime = () => {
  let hours = new Date().getHours().toString();
  let minutes = new Date().getMinutes().toString();

  if (hours.length === 1) {
    hours = "0" + hours;
  }
  if (minutes.length === 1) {
    minutes = "0" + minutes;
  }
  return { hours, minutes };
};

const App = () => {
  const [states, dispatch] = useReducer(reducer, initialStates);
  const [time, setTime] = useState(getTime);

  const animatedAstronout = useRef(
    new Animated.Value(styles.astronoutSleepImage.width)
  ).current;

  useEffect(() => {
    const _time = setInterval(() => {
      setTime(getTime);
    }, 1000);

    // Animated.timing(animatedAstronout, {
    //   toValue:
    //     Object.values(animatedAstronout)[2] === 120
    //       ? styles.astronoutSleepImage.width
    //       : 120,
    //   timing: 1000,
    //   useNativeDriver: false,
    // }).start();

    return () => clearInterval(_time);
  }, []);

  useEffect(() => {});

  return (
    <View style={styles.home}>
      <StatusBar backgroundColor={colors.purple1} />
      <Image source={images.main_background} style={styles.main_background} />

      <View style={styles.time}>
        <Text style={[styles.text]}>{`${time.hours} : ${time.minutes}`}</Text>
      </View>

      <TouchableOpacity
        onPress={() => dispatch({ type: "sleep" })}
        style={styles.astronoutSleepTouchable}
      >
        <Animated.Image
          source={images.astronout_sleep}
          style={[
            styles.astronoutSleepImage,
            { width: animatedAstronout, height: animatedAstronout },
          ]}
        />
      </TouchableOpacity>

      <Statistics display={states.statistics} />
      <Sleep display={states.sleep} initialStates={{ time }} />

      <Navbar
        onPress={() => dispatch({ type: "statistics" })}
        image={states.navbar}
      />
    </View>
  );
};

export default App;
