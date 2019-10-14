import React, { Component } from 'react'

class TicketTypeChooser extends Component {

    render() {
        return (
            <div className="radio">
                    <input type="radio" name="ticketType" value="ONEWAY" onChange={this.props.onChange} defaultChecked/> Oneway
                    <input type="radio" name="ticketType" value="ROUNDTRIP" onChange={this.props.onChange}/> Roundtrip
                    <input type="radio" name="ticketType" value="MULTICITY"  onChange={this.props.onChange}/> Multi-city

            </div>
                            );
                        }
                    }
export default TicketTypeChooser;