import React, { Component } from 'react'
import TicketTypeChooser from './TicketTypeChooser';
import FormByTicketType from './FormByTicketType';

class FlightSearchForm extends Component {

    render() {
        return (
                <div>
                    <TicketTypeChooser 
                        ticketType={this.props.ticketType}
                        onChange={this.props.onChange} 
                        getInitialState={ this.props.getInitialState}
                    />
                    <FormByTicketType 
                        fromLocation={this.props.fromLocation}
                        toLocation={this.props.toLocation}
                        departDate={this.props.departDate}
                        returnDate={this.props.returnDate}
                        onChange={this.props.onChange}

                    />
                </div>
        );
    }
}

export default FlightSearchForm;