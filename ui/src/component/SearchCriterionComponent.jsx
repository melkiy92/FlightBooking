import React, { Component } from 'react'
import ApiService from "../service/ApiService";

class SearchCriterionComponent extends Component{


    constructor(props){
        super(props);
        this.state ={
            currencyCode: '',
            ticketType: '',
            cabinClass: '',
            adults: '',
            children: '',
            fromLocation: '',
            toLocation: '',
            departDate: '',
            returnDate: ''
        }
        this.getTickets = this.getTickets.bind(this);
    }

    getTickets = (e) => {
        e.preventDefault();
        let searchCriterion = {currencyCode: this.state.currencyCode, ticketType: this.state.ticketType,
            cabinClass: this.state.cabinClass, adults: this.state.adults, children: this.state.children,
            fromLocation: this.state.fromLocation, toLocation: this.state.toLocation, departDate: this.state.departDate,
            returnDate: this.state.returnDate};

        ApiService.getTickets(searchCriterion)
            .then(
                response => {
                    console.log(response);
                    this.setState({ ticketType: response.data.ticketType });
                    this.setState({message : 'searched successfully'});
                //res => {
                //this.setState({message : 'User added successfully.'});
                //this.props.history.push('/users');
            });
    }

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

    render() {
        return(
            <div>
                <h2 className="text-center">Flight Search</h2>
                <form>
                    <div className="form-group">
                        <label>Currency:</label>
                        <input type="text" placeholder="Currency" name="currencyCode" className="form-control" value={this.state.currencyCode} onChange={this.onChange}/>
                    </div>

                    <div className="form-group">
                        <label>Ticket Type:</label>
                        <input type="text" placeholder="Ticket Type" name="ticketType" className="form-control" value={this.state.ticketType} onChange={this.onChange}/>
                    </div>

                    <div className="form-group">
                        <label>Cabin Class:</label>
                        <input type="text" placeholder="Cabin Class" name="cabinClass" className="form-control" value={this.state.cabinClass} onChange={this.onChange}/>
                    </div>

                    <div className="form-group">
                        <label>Adults:</label>
                        <input type="number" placeholder="Adults" name="adults" className="form-control" value={this.state.adults} onChange={this.onChange}/>
                    </div>

                    <div className="form-group">
                        <label>Children:</label>
                        <input type="number" placeholder="Children" name="children" className="form-control" value={this.state.children} onChange={this.onChange}/>
                    </div>

                    <div className="form-group">
                        <label>From Location:</label>
                        <input type="text" placeholder="From Location" name="fromLocation" className="form-control" value={this.state.fromLocation} onChange={this.onChange}/>
                    </div>

                    <div className="form-group">
                        <label>To Location:</label>
                        <input type="text" placeholder="To Location" name="toLocation" className="form-control" value={this.state.toLocation} onChange={this.onChange}/>
                    </div>

                    <div className="form-group">
                        <label>Depart Date:</label>
                        <input type="number" placeholder="Depart Date" name="departDate" className="form-control" value={this.state.departDate} onChange={this.onChange}/>
                    </div>

                    <div className="form-group">
                        <label>Return Date:</label>
                        <input type="number" placeholder="Return Date" name="returnDate" className="form-control" value={this.state.returnDate} onChange={this.onChange}/>
                    </div>

                    <button className="btn btn-success" onClick={this.getTickets}>Search</button>
                </form>
            </div>
        );
    }
}

export default SearchCriterionComponent;