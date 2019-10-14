import React, { Component } from 'react'
import ApiService from "../service/ApiService";
import FlightSearchForm from './FlightSearchForm';
import GeneralSearchForm from './GeneralSearchForm';

class SearchCriterionComponent extends Component{


    constructor(props){
        super(props);
        this.state ={
            ticketType: 'ONEWAY',
            currencyCode: 'USD',
            cabinClass: 'ECONOMY',
            adults: '1',
            children: '0',
            fromLocation: '',
            toLocation: '',
            departDate: '',
            returnDate: '',
            departDateLong: '',
            returnDateLong: ''

        }
        this.getTickets = this.getTickets.bind(this);
        this.onChange = this.onChange.bind(this);
        this.getInitialState = this.getInitialState.bind(this);
    }

    getTickets = (e) => {
        e.preventDefault();
        let searchCriterion = {currencyCode: this.state.currencyCode, ticketType: this.state.ticketType,
            cabinClass: this.state.cabinClass, adults: this.state.adults, children: this.state.children,
            fromLocation: this.state.fromLocation, toLocation: this.state.toLocation, departDate: this.state.departDateLong,
            returnDate: this.state.returnDateLong};

        ApiService.getTickets(searchCriterion)
            .then(
                response => {
                    console.log(response);
            });
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
        if(e.target.name === "departDate" || e.target.name === "returnDate" ){
            let timeLong = (new Date(e.target.value)).getTime();           
            this.setState({ [e.target.name + 'Long']: timeLong });
        }

    }

    getInitialState = (e) =>
        this.setState(e.target.value);


    render() {
        return(
            <div>
                <h2 className="text-center">Flight Search</h2>
                <form>
                    <FlightSearchForm 
                        ticketType={this.state.ticketType}
                        fromLocation={this.state.fromLocation}
                        toLocation={this.state.toLocation}
                        departDate={this.state.departDate}
                        returnDate={this.state.returnDate}
                        onChange={this.onChange}
                        getInitialState={this.getInitialState}
                   /> 
                    <GeneralSearchForm 
                        currencyCode={this.state.currencyCode}
                        cabinClass={this.state.cabinClass}
                        adults={this.state.adults}
                        children={this.state.children}
                        onChange={this.onChange}
                    />
                    <button className="btn btn-success" onClick={this.getTickets}>Search</button>
                </form>
            </div>
        );
    }
}

export default SearchCriterionComponent;