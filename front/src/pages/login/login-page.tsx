import React, { Component } from 'react';
import { AUTHORIZATION_LINK } from './../../services/authorization-link';
import './login-page.css'

export class LoginPage extends Component {
    redirectToAuthorizationPage = () => {
        window.location.href = AUTHORIZATION_LINK;
    }

    render() {
        return (
            <button 
                className="loginButton"
                onClick={this.redirectToAuthorizationPage}
            >
                login
            </button>
        )
    }
}