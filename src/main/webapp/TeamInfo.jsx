import React from 'react';
import {PageHeader, Row, Panel} from 'react-bootstrap';

class TeamInfo extends React.Component{
    constructor(props){
        super(props);
        this.state={
            info:[]
        }


    }

    render(){

        let number = []
        for (let element of this.props.teaminfo){
            debugger
            console.log(element)
        }


        return(
            <div>
                {/*<Row className="content">*/}
                    {/*<PageHeader>*/}
                        {/*<small>1. Team Information</small>*/}
                    {/*</PageHeader>*/}
                {/*</Row>*/}

                <Row className="content">
                    <Panel header="Team Information" bsStyle="info">

                    </Panel>
                </Row>

            </div>
        );
    }


}

export default TeamInfo;