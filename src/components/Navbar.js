import { View, Image, TouchableOpacity } from "react-native";
import React from "react";
import { styles } from "../styles";

export default function Navbar(props) {
  return (
    <View style={styles.navbar}>
      <TouchableOpacity onPress={props.onPress}>
        <Image source={props.image} style={styles.image} />
      </TouchableOpacity>
    </View>
  );
}
