import React, { Component } from 'react'

class FormOneWay extends Component {

    render() {
        const now = new Date();
        const tomorrow = new Date(now.getFullYear(), now.getMonth(), now.getDate()+2);
        const ymdFormat = tomorrow.toISOString().split('T')[0];
        return (
            <div>
                <div className="form-group">
                    <label>From Location:</label>
                    <input type="text" placeholder="From Location" name="fromLocation" className="form-control"  onChange={this.props.onChange}/>
                </div>

                <div className="form-group">
                    <label>To Location:</label>
                    <input type="text" placeholder="To Location" name="toLocation" className="form-control"  onChange={this.props.onChange}/>
                </div>

                <div className="form-group">
                    <label>Depart Date:</label>
                    <input type="date" placeholder="Depart Date" name="departDate" className="form-control"  onChange={this.props.onChange} min={ymdFormat}/>
                </div>

            </div>
        );
    }
}

export default FormOneWay;