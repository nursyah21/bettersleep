import { StyleSheet } from "react-native";

const sizes = {
  icon: 40,
  radiusNavbar: 20,
  astronout: 120,
};

const colors = {
  white: "#ffffff",
  black: "#000000",
  purple1: "#251751",
  purple2: "#6960AF",
  purple3: "#3828B7",
};

const styles = StyleSheet.create({
  astronoutSleepImage: {
    width: sizes.astronout,
    height: sizes.astronout,
    resizeMode: "contain",
  },
  astronoutSleepTouchable: {
    borderColor: colors.purple3,
    borderWidth: 2,
    padding: 20,
    borderRadius: 120,
    top: -30,
  },
  home: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  image: {
    width: sizes.icon,
    height: sizes.icon,
    alignSelf: "center",
  },
  main_background: {
    position: "absolute",
    width: "100%",
    height: "100%",
  },
  navbar: {
    backgroundColor: colors.purple1,
    borderWidth: 1,
    borderColor: colors.purple1,
    borderBottomWidth: 0,
    borderTopEndRadius: sizes.radiusNavbar,
    borderTopStartRadius: sizes.radiusNavbar,
    position: "absolute",
    width: "100%",
    padding: 5,
    bottom: 0,
  },
  overlap: {
    height: "100%",
    width: "100%",
    position: "absolute",
  },
  setSleepContainer: {
    top: 80,
    alignItems: "center",
  },
  setSleep: {
    backgroundColor: colors.purple2,
    paddingHorizontal: 20,
    paddingVertical: 10,
    marginVertical: 10,
    borderRadius: 15,
  },
  text: {
    color: colors.white,
    fontSize: 24,
  },
  textSleep: {
    fontSize: 34,
  },
  time: {
    position: "absolute",
    top: 20,
  },
});

export { colors, styles };
