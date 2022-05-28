import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View, Image } from 'react-native';

export default function Statistics(props:any) {
  return (
    <View style={[styles.container, {display: props.display}]}>
        <Image source={require('../images/sleep_background.png')} style={styles.background}/>
      <Text style={{color: "white"}}>Statistics page</Text>
      
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
