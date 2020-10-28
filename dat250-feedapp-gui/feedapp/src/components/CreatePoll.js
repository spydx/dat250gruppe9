import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import Button from 'react-bootstrap/Button'
import {Form, Row, Col} from "react-bootstrap";



class CreatePoll extends React.Component {
    render() {
        return (
            <div>
                <div>
                    <h1 style={{ textAlign: "center", marginTop: "2rem" }}><u>Create poll</u></h1>                                                                                           
                </div>
                <Form style = {{marginTop:"13%"}}>
                    <Form.Group as={Row} controlId="PollName">
                        <Form.Label column sm={2}>
                            Name
                        </Form.Label> 
                        <Col sm={10}>
                            <Form.Control type="Name" placeholder="Poll Name" />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="VotingOn">
                        <Form.Label column sm={2}>
                            Voting on 
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Control type="VotingOn" placeholder="Topic" />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="YesOption">
                        <Form.Label column sm={2}>
                            Yes Option 
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Control type="YesOption" placeholder="Default: Yes, otherwise specify" />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="NoOption">
                        <Form.Label column sm={2}>
                            No Option 
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Control type="NoOption" placeholder="Default: No, otherwise specify" />
                        </Col>
                    </Form.Group>
                    <Form.Group as={Row} controlId="Privacy">
                        <Form.Label column sm={2}>
                            Privacy 
                        </Form.Label>
                        <Col sm={10}>
                            <Form.Control type="Privacy" as="select">
                            <option value = "public">Public</option>
                            <option value = "private">Private</option>
                            <option value = "registered">Registered</option>
                            </Form.Control>
                        </Col>
                    </Form.Group>
                    <div>
                        <Button variant="danger" 
                                style={{ marginLeft: "5%", marginTop: "5%" }}
                                href = "/">
                            Return to overview
                        </Button>
                    </div>
                    <div>
                        <Button variant="success" 
                                style={{ marginLeft: "5%", marginTop: "5%" }}
                                onClick={() => alert("Saved")}>
                            Create
                        </Button>  
                    </div>
                </Form>
            </div>
        );
    }
}

export default CreatePoll