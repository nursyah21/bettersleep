import { View, Text, Image, TouchableOpacity, StyleSheet } from "react-native";
import React, { useState } from "react";
import { colors, styles } from "../styles";
import { images } from "../images/images";

import { storeData, getData } from "../components/Saved";

const myStyles = StyleSheet.create({
  button: {
    backgroundColor: colors.purple2,
    alignItems: "center",
    width: 40,
  },
  sleepButtonContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
  },
  astronoutSleepImage: {
    width: 60,
    height: 60,
  },
  astronoutSleepTouchable: {
    top: 40,
  },
});

const Button = (props) => {
  return (
    <TouchableOpacity
      onPress={props.onPress}
      style={[myStyles.button, props.style]}
    >
      <Text style={styles.text}>{props.text}</Text>
    </TouchableOpacity>
  );
};

const handleTime = (time, num, type) => {
  if (time === "23" && num === 1 && type === "hours") {
    return "00";
  }
  if (time === "00" && num === -1 && type === "hours") {
    return "23";
  }

  if (time === "59" && num === 1 && type === "minutes") {
    return "00";
  }
  if (time === "00" && num === -1 && type === "minutes") {
    return "59";
  }
  time = (Number(time) + num).toString();

  time = time.length === 1 ? "0" + time : time;

  return time;
};

const sendDataSleep = (initial, newTime) => {
  let res =
    Number(newTime.hours) * 60 +
    Number(newTime.minutes) -
    (Number(initial.hours) * 60 + Number(initial.minutes));

  return res;
};

const totalSleep = (initial, newTime) => {
  initial = Number(initial.hours) * 60 + Number(initial.minutes);
  newTime = Number(newTime.hours) * 60 + Number(newTime.minutes);

  let res = newTime - initial;
  if (res <= 0) {
    return "not sleep :(";
  }
  if (res >= 60) {
    return `Total sleep: ${Math.floor(res / 60)}h ${Math.floor(res % 60)}m`;
  }
  return `Total sleep: ${res}m`;
};

export default function Sleep(props) {
  const [time, setTime] = useState({
    hours: props.initialStates.time.hours,
    minutes: props.initialStates.time.minutes,
    opacityAstronout: 1,
    disabledAstronout: false,
  });

  const helperSetTime = (type, num) => {
    if (type === "hours") {
      setTime({
        hours: handleTime(time.hours, num, "hours"),
        minutes: time.minutes,
      });
    } else if (type === "minutes") {
      setTime({
        hours: time.hours,
        minutes: handleTime(time.minutes, num, "minutes"),
      });
    }
  };

  return (
    <View style={[styles.overlap, { display: props.display }]}>
      <Image source={images.main_background} style={styles.main_background} />

      <View style={styles.setSleepContainer}>
        <Text style={[styles.text]}>Wake me up </Text>

        <View style={styles.setSleep}>
          <View style={myStyles.sleepButtonContainer}>
            <Button onPress={() => helperSetTime("hours", 1)} text={"+"} />
            <Button onPress={() => helperSetTime("minutes", 1)} text={"+"} />
          </View>

          <Text style={[styles.text, styles.textSleep]}>
            {time.hours} : {time.minutes}
          </Text>

          <View style={myStyles.sleepButtonContainer}>
            <Button onPress={() => helperSetTime("hours", -1)} text={"-"} />
            <Button onPress={() => helperSetTime("minutes", -1)} text={"-"} />
          </View>
        </View>

        <Text style={[styles.text]}>
          {totalSleep(
            {
              hours: props.initialStates.time.hours,
              minutes: props.initialStates.time.minutes,
            },
            time
          )}
        </Text>

        <TouchableOpacity
          onPress={() => {
            storeData(
              sendDataSleep(
                {
                  hours: props.initialStates.time.hours,
                  minutes: props.initialStates.time.minutes,
                },
                time
              )
            );
            getData().then((e) => {
              // console.log(JSON.parse(e))
            });
          }}
          style={[
            styles.astronoutSleepTouchable,
            myStyles.astronoutSleepTouchable,
          ]}
          disabled={time.disabledAstronout}
        >
          <Image
            source={images.astronout_sleep}
            style={[
              styles.astronoutSleepImage,
              myStyles.astronoutSleepImage,
              { opacity: time.opacityAstronout },
            ]}
          />
        </TouchableOpacity>
      </View>
    </View>
  );
}
