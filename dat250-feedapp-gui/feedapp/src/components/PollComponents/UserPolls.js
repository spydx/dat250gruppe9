import React from "react";
import Card from "react-bootstrap/Card";
import Spinner from "react-bootstrap/Spinner";
import Button from "react-bootstrap/Button";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/css/bootstrap.css";
import PollStatus from "./PollStatus";
import { Delete } from "../../utils/actionHandler"
import { API_URL } from "../../constants/constants"
import DeleteIcon from '@material-ui/icons/Delete';
import { IconButton } from "@material-ui/core";

class UserPolls extends React.Component {

    getStatus(endDate) {
        var currentDate = new Date();
        endDate = new Date(endDate);
        var ended = currentDate > endDate && endDate.getFullYear() !== 1970;
        return ended
    }
        
    async handleDelete(pollid) {
        await Delete(API_URL + "/polls/" + pollid, this.props.poll.token)
        
    }

    render() {
        const { error, isLoaded, pollData } = this.props.poll;

        if (error) {
        return <div>Something went wrong: {error.message}</div>;
        } else if (!isLoaded) {
        return (
            <div>
            <Spinner animation="border" role="status" variant="secondary">
                <span className="sr-only">Loading...</span>
            </Spinner>
            </div>
        );
        }
        return (
        
        <div>
            {pollData.map((poll) => (
            
            <div key={poll.id} className="mt-2">
                <Card text={"dark"} className="mb-2" style={{textAlign: "center"}}>
                    <h1 style={{ textAlign: "Left", fontSize: "140%", marginTop: "1%", marginLeft: "1%"}}>
                        <PollStatus poll={poll}/>
                    </h1>
                    <h3 className="font-weight-light" style={{ textAlign: "center" }}>
                        {poll.question}
                    </h3>
                    <div className="container">
                    <div className="row">
                    <div className="col-sm" style={{ textAlign: "right"}}>
                        {!this.getStatus(poll.timeend) && 
                        <div style={{display: "flex"}}>         
                            
                            <IconButton
                                onClick={() => { this.handleDelete(poll.id) }}
                                href="/"
                                style={{ float: "inline-start", marginBottom: "2%"}}
                                >
                                <DeleteIcon /> 
                            </IconButton> 
                              
                            <Button
                            variant="success"
                            style={{width: "20%", marginLeft: "auto", marginBottom: "2%", marginTop: "2%"}}
                            href={"/vote/" + poll.id}
                            >
                            Vote
                            </Button>
                        </div>
                        }
                        {this.getStatus(poll.timeend) &&
                        <div style={{display: "flex"}}>         
                            
                            <IconButton
                                onClick={() => { this.handleDelete(poll.id) }}
                                href="/"
                                style={{float: "inline-start", marginBottom: "2%"}}
                                >
                                <DeleteIcon /> 
                            </IconButton> 
                         
                            <Button
                            style={{width: "20%", marginLeft: "auto", backgroundColor: "#2196F3", marginBottom: "2%", marginTop: "2%"}}
                            href={"/result/" + poll.id}
                            >
                            Result
                            </Button>
                            
                        </div>   
                        } 
                        </div>
                    </div>
                    </div>
                </Card>   
            </div>
            ))}
        </div>
        );
    }
}

export default UserPolls;

