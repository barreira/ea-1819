import React from 'react';
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import './App.css';

// Components
import Home from './components/Home/Home';

function App() {
  return (
    <Router>
      <div>
        {/* NAVBAR       */}
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/teste" component={Home} />
          <Route component={Home} />
        </Switch>
      </div>

    </Router>
  );
}

export default App;
