import React, { Component } from 'react'
import FormOneWay from './FormOneWay';


class FormMultiCity extends Component {

    render() {
        const now = new Date();
        const tomorrow = new Date(now.getFullYear(), now.getMonth(), now.getDate()+2);
        const ymdFormat = tomorrow.toISOString().split('T')[0];
        var index = 0;
        
       
       return (
        <div>
            
                  <FormOneWay 
                       fromLocation={this.props.fromLocation}
                       toLocation={this.props.toLocation}
                       departDate={this.props.departDate}
                       onChange={this.props.onChangeMultiType(index)}
                   /> 

                    <FormOneWay 
                       fromLocation={this.props.fromLocation}
                       toLocation={this.props.toLocation}
                       departDate={this.props.departDate}
                       onChange={this.props.onChangeMultiType(index++)}
                   />  
                
                {
                this.props.legHide
                ? null :
                <div>
            
        
                   {this.props.legs.map((leg, idx) => (
                   <div key={idx}>  
                   <FormOneWay 
                   fromLocation={this.props.fromLocation}
                   toLocation={this.props.toLocation}
                   departDate={this.props.departDate}
                   onChange={this.props.onChangeMultiType(idx)}
                   />

                   <button className="btn btn-success" onClick={this.props.handleRemoveLeg(idx)}> - </button> 

                   </div>
                   ))} 
                   </div>
                }
                   <button className="btn btn-success" onClick={this.props.handleAddLeg}>Add destination</button>
                   
                    
        </div>
           /*
            <div>
              {this.props.legs.map((leg, idx) => (
                <div key={idx}>
                <div className="form-group">
                    <label>From Location:</label>
                    <input type="text" placeholder="From Location" name="fromLocation" className="form-control"  onChange={this.props.onChangeMultiType(idx)}/>
                </div>

                <div className="form-group">
                    <label>To Location:</label>
                    <input type="text" placeholder="To Location" name="toLocation" className="form-control"  onChange={this.props.onChangeMultiType(idx)}/>
                </div>

                <div className="form-group">
                    <label>Depart Date:</label>
                    <input type="date" placeholder="Depart Date" name="departDate" className="form-control"  onChange={this.props.onChangeMultiType(idx)} min={ymdFormat}/>
                </div>

                <button className="btn btn-success" onClick={this.props.handleRemoveLeg(idx)}> - </button> 
                </div>
               ))} 
            <button className="btn btn-success" onClick={this.props.handleAddLeg}>Add destination</button>   
            </div>
            */
        );
    }
}

export default FormMultiCity;
//value={leg.departDate}