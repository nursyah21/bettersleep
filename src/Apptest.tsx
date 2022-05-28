import React, {Component} from "react";
import {StyleSheet, Text, View, StatusBar, Button} from "react-native"

class Cat extends Component {
    state = {isHungry: true}
    render(): React.ReactNode {
        return (
            <View>
                <Text>
                    I am {this.props.name}, and I am {this.state.isHungry ? "hungry" : "full"}!
                </Text>
                <Button onPress={()=>{this.setState({isHungry:false})}} disabled={!this.state.isHungry} title={this.state.isHungry ? "Pour me some milk, please!" : "Thanl you"}/>
            </View>
        )
    }
}

class App extends Component {
    render(): React.ReactNode {
        return(
            <View style={styles.v1}>
                <Text>Test</Text>
                <Cat name="Spot" />
            </View>
        )
    }
}

const styles = StyleSheet.create({
    v1:{
        paddingTop: StatusBar.currentHeight,
    }
})

export default App
