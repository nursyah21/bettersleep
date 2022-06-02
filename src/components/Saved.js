import AsyncStorage from "@react-native-async-storage/async-storage";

// we store after we count difference from app sleep to app wake up
// for now we only store data within 30days

const clearData = async () => {
  try {
    await AsyncStorage.setItem("storage_key", "");
  } catch (e) {
    console.error(e);
  }
};

const getData = async () => {
  try {
    const value = await AsyncStorage.getItem("storage_key");
    if (value !== null) {
      return JSON.parse(value);
    }
  } catch (e) {
    console.error(e);
  }
};

const storeData = async (value) => {
  try {
    let DATA = await getData();
    let date = new Date().getDate();

    if (Array.isArray(DATA)) {
      try {
        let last = DATA[DATA.length - 1];
        if (last.date === date) {
          value += last.minutes;
        }
      } catch (e) {
        throw console.error(e);
      }
      DATA.push({ date: date, minutes: value });
    } else {
      DATA = [{ date: date, minutes: value }];
    }
    await AsyncStorage.setItem("storage_key", JSON.stringify(DATA));
  } catch (e) {
    console.error(e);
  }
};

export { storeData, getData, clearData };
