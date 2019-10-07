import React, { Component } from 'react'
import SearchCriterionComponent from "./component/SearchCriterionComponent";
import ApiService from "../service/ApiService";

class FlightBookingApp extends Component{
    render() {
        return(
            <div>
                <h1 className="text-center">Flight Booking</h1>
                <SearchCriterionComponent/>
            </div>
        );
    }
}

export default FlightBookingApp;