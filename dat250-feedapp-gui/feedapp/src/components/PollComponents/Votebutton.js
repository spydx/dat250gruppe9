import React from 'react';
import Button from 'react-bootstrap/Button'
import 'bootstrap/dist/css/bootstrap.min.css'


class Votebutton extends React.Component {
    render() {
        return (
            <button class="btn btn-success float-right btn-sm" type="button" style={{width: '5rem', marginLeft: '19.7rem', marginBottom: '0.2rem'}}>Vote</button> 
        )
    }

}

export default Votebutton