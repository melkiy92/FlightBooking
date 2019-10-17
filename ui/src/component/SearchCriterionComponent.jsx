import React, { Component } from 'react'
import ApiService from "../service/ApiService";
import FlightSearchForm from './FlightSearchForm';
import GeneralSearchForm from './GeneralSearchForm';
import { updateExpression } from '@babel/types';

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
            returnDateLong: '',
            legs: [{fromLocation: '', toLocation: '', departDate: ''}, {fromLocation: '', toLocation: '', departDate: ''}],
            legHide: true

        }
        this.getTickets = this.getTickets.bind(this);
        this.getMultiCityTickets = this.getMultiCityTickets.bind(this);
        this.onChange = this.onChange.bind(this);
        this.onChangeMultiType = this.onChangeMultiType.bind(this);
        this.handleRemoveLeg = this.handleRemoveLeg.bind(this);
        this.handleAddLeg = this.handleAddLeg.bind(this);
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

    getMultiCityTickets = (e) => {
        e.preventDefault();
        let MultiCitySearchCriterion = {currencyCode: this.state.currencyCode, ticketType: this.state.ticketType,
            cabinClass: this.state.cabinClass, adults: this.state.adults, children: this.state.children, legs: this.state.legs};
            

        ApiService.getMultiCityTickets(MultiCitySearchCriterion)
            .then(
                response => {
                    console.log(response);
            });
    }

    handleAddLeg = () => {
        this.setState(prevState => ({ legHide: false }));
        this.setState({
            legs: this.state.legs.concat([{fromLocation: "", toLocation: "", departDate: ""}])
        });
       
    };

    handleRemoveLeg = idx => () => {
        //this.setState(prevState => ({ legHide: false }));
        this.setState({
            legs: this.state.legs.filter((s, sidx) => idx !== sidx)
        });
    };

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
        if(e.target.name === "departDate" || e.target.name === "returnDate" ){
            let timeLong = (new Date(e.target.value)).getTime();           
            this.setState({ [e.target.name + 'Long']: timeLong });
        }
    }
    

    onChangeMultiType = idx => e => {
    const newLegs = this.state.legs.map((leg, sidx) => {
        if (idx !== sidx) return leg;
        let value1 = e.target.value;
        if(e.target.name === "departDate" ){
            value1 = (new Date(e.target.value)).getTime();
        }
        return { ...leg, [e.target.name] : value1 };  
    });
    this.setState({ legs: newLegs });
    };
/*

    onChangeMultiType = idx => e => {
      
          //return { ...leg,  [e.target.name]: e.target.value };
          const newLegs = this.state.legs.map((leg, sidx) => {
            if (idx !== sidx) return leg;
            this.setState({ [e.target.name]: e.target.value });
            if(e.target.name === "departDate"){
                let timeLong = (new Date(e.target.value)).getTime();           
                this.setState({ [e.target.name + 'Long']: timeLong });
              }
            return { ...leg,   [e.target.name]: e.target.value };  
             //return { ...leg,  fromLocation: e.target.value, toLocation: e.target.value, departDate: e.target.value};
            });    
        this.setState({ legs: newLegs });
       
    };

*/
    getInitialState = (e) =>
        this.setState(e.target.value);


    render() {
        return(
            <div>
                <h2 className="text-center">Flight Search</h2>
                <div>
                    <GeneralSearchForm 
                        currencyCode={this.state.currencyCode}
                        cabinClass={this.state.cabinClass}
                        adults={this.state.adults}
                        children={this.state.children}
                        onChange={this.onChange}
                    />

                    <FlightSearchForm 
                        ticketType={this.state.ticketType}
                        fromLocation={this.state.fromLocation}
                        toLocation={this.state.toLocation}
                        departDate={this.state.departDate}
                        returnDate={this.state.returnDate}
                        onChange={this.onChange}
                        onChangeMultiType={this.onChangeMultiType}
                        getInitialState={this.getInitialState}
                        handleAddLeg={this.handleAddLeg}
                        handleRemoveLeg={this.handleRemoveLeg}
                        legs={this.state.legs}
                        legHide={this.state.legHide}
                   /> 

                  

                    <p><button className="btn btn-success" onClick={(this.state.ticketType === "MULTICITY") ? this.getMultiCityTickets : this.getTickets}>Search</button></p>
                </div>
            </div>
        );
    }
}

export default SearchCriterionComponent;

/*this.state.ticketType === "MULTICITY" &&
<button className="btn btn-success" onClick={this.addLeg}>Add destination</button> */