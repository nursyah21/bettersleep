import { configureStore } from "@reduxjs/toolkit";
import { createSlice } from "@reduxjs/toolkit";
import Sleep from "./layouts/Sleep";

const display = createSlice({
    name: "display",
    initialState:{
        home:"flex",
        sleep:"none",
        statistics:"none"
    },
    reducers:{
        home: (state:any):any =>{
            state.home = "flex"
            state.sleep = "none"
            state.statistics = "none"
        },
        sleep: (state:any) =>{
            state.home = "none"
            state.sleep = "flex"
            state.statistics = "none"
        },
        statistics: (state:any, actions:any) =>{
            state.home = "none"
            state.sleep = "none"
            state.statistics = "flex"
        },
    }
})



export const {homePage, sleepPage, statisticsPage} = display.actions

export default configureStore({
    reducer:{
        display: display.reducer
    }
})
