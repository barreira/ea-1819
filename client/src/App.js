import React from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import './App.css';

// Components
import Home from './components/Home/Home';
import Navbar from './components/Navbar/Navbar';

function App() {
  return (
    <Router>
      <div>
        <Navbar />
        <div className="container mt-4">
          <Switch>
            <Route exact path="/" component={Home} />
            <Route exact path="/teste" component={Home} />
            <Route component={Home} />
          </Switch>
        </div>
      </div>

    </Router>
  );
}

export default App;
