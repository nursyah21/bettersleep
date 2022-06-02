import { View, Text, Image, StyleSheet, ScrollView } from "react-native";
import React from "react";
import { colors, styles } from "../styles";
import { images } from "../images/images";

function Card(props) {
  return (
    <View style={[myStyles.card]}>
      <Text style={[styles.text, myStyles.text]}>{props.text.type}</Text>
      <Text style={[styles.text, myStyles.text]}>{props.text.value}</Text>
    </View>
  );
}

const MONTH = [
  "",
  "JAN",
  "FEB",
  "MAR",
  "APR",
  "MAY",
  "JUN",
  "JUL",
  "AUG",
  "SEP",
  "OKT",
  "NOV",
  "DES",
];

const DUMMYDATA = [
  { 1: 120 },
  { 2: 84 },
  { 3: 59 },
  { 4: 46 },
  { 5: 35 },
  { 6: 22 },
  { 7: 30 },
  { 8: 63 },
  { 9: 40 },
  { 10: 37 },
  { 11: 10 },
  { 12: 62 },
  { 13: 83 },
  { 14: 60 },
]; // minutes

function BarStatstics(props) {
  let newHeight = (props.height / max(DUMMYDATA)) * 120; //highest bar 120

  return (
    <View
      style={[
        myStyles.barStatstics,
        {
          height: newHeight,
          top: 120 - newHeight,
        },
      ]}
    />
  );
}

function StatisticsContainer(props) {
  return (
    <View style={[myStyles.statisticsContainer]}>
      <Text style={[styles.text, myStyles.text]}>
        {MONTH[new Date().getMonth()]} - {new Date().getFullYear()}
      </Text>

      <ScrollView style={[myStyles.barContainer]} horizontal={true}>
        {DUMMYDATA.map((e, idx) => {
          return (
            <View key={idx}>
              <BarStatstics height={Object.values(e)[0]} />
              <Text style={[styles.text, myStyles.textBar]}>
                {Object.keys(e)[0]}
              </Text>
            </View>
          );
        })}
      </ScrollView>
    </View>
  );
}

const sum = (e) => {
  let res = 0;
  for (var i = 0; i < e.length; i++) {
    res += Object.values(e[i])[0];
  }

  return res;
};
const max = (e) => {
  let res = Object.values(e[0])[0];
  for (var i = 0; i < e.length; i++) {
    if (Object.values(e[i])[0] > res) {
      res = Object.values(e[i])[0];
    }
  }

  return res;
};

const handleMinutes = (type, data = DUMMYDATA) => {
  const length = data.length;

  let res = "0";

  switch (type) {
    case "total":
      res = sum(data);
      break;
    case "average":
      res = Math.floor(sum(data) / length);
      break;
    case "max":
      res = max(data);
      break;
  }
  if (res < 60) {
    return `${res}m`;
  }
  return `${Math.floor(res / 60)}h ${res % 60}m`;
};

export default function Statistics(props) {
  return (
    <View style={[styles.overlap, { display: props.display }]}>
      <Image source={images.main_background} style={styles.main_background} />

      <View style={[myStyles.view]}>
        <Text style={[styles.text]}>Month Statistics</Text>

        <View style={[myStyles.row]}>
          <Card text={{ type: "total", value: handleMinutes("total") }} />
          <Card text={{ type: "average", value: handleMinutes("average") }} />
          <Card text={{ type: "max", value: handleMinutes("max") }} />
        </View>

        <StatisticsContainer />
      </View>
    </View>
  );
}

const myStyles = StyleSheet.create({
  barStatstics: {
    width: 13,
    height: 10,
    marginRight: 13,
    backgroundColor: colors.white,
  },
  barContainer: {
    marginVertical: 5,
    marginTop: 14,
    overflow: "hidden",
    flexDirection: "row",
  },
  card: {
    width: 100,
    height: 80,
    padding: 10,
    marginHorizontal: 10,
    marginVertical: 20,
    borderRadius: 10,
    backgroundColor: colors.purple2,
  },
  row: {
    flexDirection: "row",
  },
  statisticsContainer: {
    width: 300,
    height: 200,
    padding: 10,
    marginVertical: 30,
    borderRadius: 10,
    backgroundColor: colors.purple2,
  },
  text: {
    fontSize: 17,
  },
  textBar: {
    fontSize: 14,
    bottom: 0,
    position: "absolute",
  },
  view: {
    alignItems: "center",
    padding: 25,
  },
});
