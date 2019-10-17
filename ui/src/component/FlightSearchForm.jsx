import React, { Component } from 'react'
import TicketTypeChooser from './TicketTypeChooser';
import FormOneWay from './FormOneWay';
import FormRoundTrip from './FormRoundTrip';
import FormMultiCity from './FormMultiCity';


class FlightSearchForm extends Component {

    render() {
        return (
                <div>
                    <TicketTypeChooser 
                        ticketType={this.props.ticketType}
                        onChange={this.props.onChange} 
                        getInitialState={ this.props.getInitialState}
                    />
                    { (this.props.ticketType) === "ONEWAY" ? (
                       <FormOneWay 
                       fromLocation={this.props.fromLocation}
                       toLocation={this.props.toLocation}
                       departDate={this.props.departDate}
                       onChange={this.props.onChange}

                   /> 
                     ) : (this.props.ticketType === "ROUNDTRIP") ? (
                        <FormRoundTrip 
                        fromLocation={this.props.fromLocation}
                        toLocation={this.props.toLocation}
                        departDate={this.props.departDate}
                        returnDate={this.props.returnDate}
                        onChange={this.props.onChange}

                     />
                     ) : (
                        <FormMultiCity 
                        fromLocation={this.props.fromLocation}
                        toLocation={this.props.toLocation}
                        departDate={this.props.departDate}
                        onChangeMultiType={this.props.onChangeMultiType}
                        handleAddLeg={this.props.handleAddLeg}
                        handleRemoveLeg={this.props.handleRemoveLeg}
                        legs={this.props.legs}
                        legHide={this.props.legHide}
                   /> 
                    )}
                </div>
        );
    }
}

export default FlightSearchForm;