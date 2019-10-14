import React from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import SearchCriterionComponent from "./component/SearchCriterionComponent";

function App() {
    return (
        <div className="container">
            <Router>
                <div className="container">
                    <h1 className="text-center" style={style}>Flight Booking</h1>
                    <Switch>
                        <Route path="/flights" component={SearchCriterionComponent} />
                    </Switch>
                </div>
            </Router>
        </div>
    );
}

const style = {
    color: 'grey',
    margin: '10px'
}

export default App;



/*import React from 'react';
import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;

<Route exact path='/' component={Home}/>*/