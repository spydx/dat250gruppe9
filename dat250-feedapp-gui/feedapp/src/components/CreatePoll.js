import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import Button from 'react-bootstrap/Button'
import {Form, Row, Col} from "react-bootstrap";
import Datetime from "react-datetime";
import "react-datetime/css/react-datetime.css";
import { Post } from "../utils/actionHandler"
import { API_URL } from "../constants/constants"
import { connect } from "react-redux";
import { Redirect } from "react-router-dom";


class CreatePoll extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
          didCreate: false
        }
    }
    

    async handleSubmit(access, answerno, answeryes, name, question, timeend) {
        
        if (name === "" || question === "") {
            this.props.setReset()
            this.props.setError("Please give a poll name and question.")
            return;
        }
        
        if (answerno === "") {
            answerno = "No"
        }
        if (answeryes === "") {
            answeryes = "Yes"
        }
        const createPollRequest = {
            access: access,
            answerno: answerno,
            answeryes: answeryes,
            name: name,
            question: question,
            timeend: timeend
        }

        await Post(API_URL + "/polls/", createPollRequest, this.props.state.user.token)
            .then((res) => res.json())
            .then(
                (result) => {
                    console.log(result)
                    this.setState({didCreate: true})
                },
                (error) => {
                    this.setError(error)
                }
        )
        
    }

    componentDidMount() {
        this.setState({
            error: null,
            access: "PUBLIC",
            answerno: "No",
            answeryes: "Yes",
            name: "",
            question: "",
            timeend: new Date(),
            didCreate: false
        })
    }

    render() {
        if (this.state.didCreate) {
            return <Redirect to="/"/>
        }
        return (
            <div>
                <Form style = {{marginTop:"13%", width: "150%"}}>
                    <Form.Group as={Row} controlId="PollName">
                        <Form.Label column sm={2}>
                            Name
                        </Form.Label> 
                        <Col sm={10}>
                            <Form.Control type="Name" placeholder="Poll Name" onChange={e => this.setState({name: e.target.value})}/>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="VotingOn">
                        <Form.Label column sm={2}>
                            Question 
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Control type="VotingOn" placeholder="Question" onChange={e => this.setState({question: e.target.value})}/>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="YesOption">
                        <Form.Label column sm={2}>
                            Yes Option 
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Control type="YesOption" placeholder="Default: Yes" onChange={e => this.setState({answeryes: e.target.value})}/>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="NoOption">
                        <Form.Label column sm={2}>
                            No Option 
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Control type="NoOption" placeholder="Default: No" onChange={e => this.setState({answerno: e.target.value})}/>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="Privacy">
                        <Form.Label column sm={2}>
                            Privacy 
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Control type="Privacy" as="select" onChange={e => this.setState({access: e.target.value})}>
                            <option value = "PUBLIC">Public</option>
                            <option value = "PRIVATE">Private</option>
                            <option value = "REGISTERED">Registered</option>
                            </Form.Control>
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="Privacy">
                        <Form.Label column sm={2}>
                            End Time 
                        </Form.Label>
                        <Col sm={10}>
                            <Datetime closeOnClickOutside="true" dateFormat="DD-MM-YYYY" initialValue={new Date()} onChange={e => this.setState({timeend: e})}/>
                        </Col>
                    </Form.Group>
                    
                    {this.props.state.poll.error &&
                    <div>
                        <p style={{color: "red"}}>{this.props.state.poll.error}</p>
                    </div>                       
                    }
                    
                    

                    <div>
                        <Button variant="danger" 
                                style={{ marginLeft: "17%", marginTop: "5%" }}
                                href = "/">
                            Return to overview
                        </Button>
                    </div>
                    <div>
                        <Button
                            variant="success"
                            style={{ marginLeft: "17%", marginTop: "5%" }}
                            onClick={() => this.handleSubmit(
                                    this.state.access,
                                    this.state.answerno,
                                    this.state.answeryes,
                                    this.state.name,
                                    this.state.question,
                                    this.state.timeend.toISOString()
                            )}
                        >
                            Create
                        </Button>  
                    </div>
                </Form>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
      state: state
    };
  };
  
  const mapDispatchToProps = (dispatch) => {
    return {
        setError: (error) => dispatch({
            type: "SET_POLL_ERROR",
            error: error,
            isLoaded: false
        }),
        
        setReset: () => dispatch({
            type: "RESET_POLL_DATA"
        })
    };
  };
  
  export default connect(mapStateToProps, mapDispatchToProps)(CreatePoll);