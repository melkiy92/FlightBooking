import React, { Component } from 'react'
import 'react-dropdown/style.css'



class GeneralSearchForm extends Component {

    render() {

        return (
            <div>
                <div className="form-group">
                    <label>Currency:</label>
                    <input type="text" placeholder="Currency" name="currencyCode" className="form-control" onChange={this.props.onChange} defaultValue={this.props.currencyCode} />
                </div>

                <div className="form-group">
                    <label>Cabin Class:</label>
                    <select
                        name="cabinClass"
                        onChange={this.props.onChange}
                        className="form-control"
                    >
                        <option name="cabinClass" value='ECONOMY' onChange={this.props.onChange}>Economy</option>
                        <option name="cabinClass" value='PREMIUMECONOMY' onChange={this.props.onChange}>Premium Economy</option>
                        <option name="cabinClass" value='BUSINESSCLASS' onChange={this.props.onChange}>Business Class</option>
                        <option name="cabinClass" value='FIRSTCLASS' onChange={this.props.onChange}>First Class</option>
                    </select>		

                </div>

                <div className="form-group">
                    <label>Adults:</label>
                    <input type="number" placeholder="Adults" name="adults" className="form-control" onChange={this.props.onChange} defaultValue={this.props.adults} min="1" step="1"/>
                </div>

                <div className="form-group">
                    <label>Children:</label>
                    <input type="number" placeholder="Children" name="children" className="form-control" onChange={this.props.onChange} defaultValue={this.props.children} min="0" step="1"/>
                </div>
            </div>
        );
    }
}
export default GeneralSearchForm;