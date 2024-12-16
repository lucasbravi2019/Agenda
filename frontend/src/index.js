import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { Provider } from 'react-redux';
import store from './store';
import { BrowserRouter } from 'react-router-dom';
import AppRoutes from './router';
import Navigation from './components/navigation/navigation';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <Provider store={store}>
    <BrowserRouter>
      <React.StrictMode>
        <Navigation />
        <AppRoutes />
      </React.StrictMode>
    </BrowserRouter>
  </Provider>
);
