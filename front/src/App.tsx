import React from 'react';
import './App.css';
import { BrowserRouter, Route } from 'react-router-dom';
import * as Pages from './pages';
import { ROUTES } from './services/routes';
 
const App: React.FC = () => {
  return (
    <BrowserRouter>
      <main className="page__container">
        <Route exact path={ROUTES.LOGIN} component={Pages.LoginPage} />
        <Route exact path={ROUTES.MAIN} component={Pages.MainPage} />
      </main>
    </BrowserRouter>
  );
}

export default App;