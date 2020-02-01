import React, { Component } from 'react';
import { AUTHORIZATION_LINK } from './../../services/authorization-link';
import { User } from '../../services/user';
import { SERVER_LINK } from '../../services/server-link';
import { Friend } from '../../services/friend';
import './main-page.css'

interface MainPageState {
    photoUrl: string;
    friends: Friend[] | undefined;
    filteredFriends: Friend[] | undefined;
}

export class MainPage extends Component<any, MainPageState> {
    code = this.props.location.search.split('=')[1];

    constructor(props: any) {
        super(props);

        this.state = {
            photoUrl: "",
            friends: undefined,
            filteredFriends: undefined
        }

        this.getUserInfo();
    }

    getUserInfo = () => {
        if (sessionStorage.getItem("code_is_used") !== undefined &&
        sessionStorage.getItem("code_is_used") === "true") {
            window.location.href = AUTHORIZATION_LINK;
            sessionStorage.setItem("code_is_used", "false");
        } else {   
            const xhr = new XMLHttpRequest();

            xhr.onloadend = () => {
                const result: User = JSON.parse(xhr.response);
                
                if (result != null) {
                    this.setState({ photoUrl: result.photoUrl });
                    this.setState({ friends: result.friends });
                }
            };

            xhr.open('get', `${SERVER_LINK}${this.code}`);
            xhr.send();

            sessionStorage.setItem("code_is_used", "true");
        }
    }

    searchFriendsByName = () => {
        let text = document.getElementsByTagName("input")[0];
        let val = text.value;
        
        this.setState({ filteredFriends: this.state.friends?.filter(x => x.firstName.includes(val)) });
    }

    render() {
        return (
            <div className="main_page">
                <main className="main_content">
                    <div className="left_content_part">
                        <img src={this.state.photoUrl} alt="" />
                        <p className="user_friends_info">
                            Friends amount: {this.state.friends?.length}
                        </p>
                    </div>
                    <div>
                        <input autoComplete="off" type="search" name="q" /> 
                        <button onClick={this.searchFriendsByName}>Find</button>
                        {this.state.filteredFriends?.map((x, i) => 
                            <p key={i}>{x.firstName} {x.lastName}</p>
                        )}
                    </div>
                </main>
                <button 
                    className="logoutButton"
                    onClick={() => this.props.history.push('/')}
                >
                    logout
                </button>
            </div>
        )
    }
}