import React from 'react';
import axios from 'axios';
import TeamInfo from './TeamInfo.jsx';
import {Grid, Row, Jumbotron, Panel} from 'react-bootstrap';

class App extends React.Component{
    constructor(){
        super();
        this.state={
            teaminfo:[],
            loadFinish:false,
            name:"TDW"
        }
        this.componentWillMount = this.componentWillMount.bind(this)

    }

    render(){
        return(
            <Grid id="root">
                <Row>
                    <Jumbotron>
                        <h2><b>Trading Men Team Summary</b></h2>
                        <p>2017 IT Internship Hackathon</p>
                    </Jumbotron>
                </Row>
                {/*<Row className="content">*/}
                    {/*{this.state.loadFinish ? <TeamInfo teaminfo = {this.state.teaminfo}/> :null}*/}
                {/*</Row>*/}

                <Row className="content">
                    <Panel header="Team Information" bsStyle="info">

                    </Panel>
                </Row>

            </Grid>
        );
    }

    componentWillMount(){
        this.getTeamData.then(result =>{
            // for (let element of result){
            //     this.state.teaminfo.push(element.entity)
            //     console.log(element.entity)
            // }
            this.setState({teaminfo:result});
        })
        this.setState({loadFinish:true})
        //this.setState({name:this.state.teaminfo[2]})
        console.log("lalalal"+this.state.name)
    }

    getTeamData = new Promise((resolve, error) =>{
        axios.get("https://cis2017-teamtracker.herokuapp.com/api/teams/3dy93PQ9NP-TtABeab_C1w").then(function(response){
            resolve(Object.values(response.data).map(entity=>{
                return {
                    entity
                }
            }))
        }).catch(function(error){
            console.log(error)
        })

    })

}

export default App;