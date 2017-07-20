import React from 'react';
import axios from 'axios';
import PieChart from "react-svg-piechart"
import {Grid, Row, Col, Jumbotron, Panel, Table, Image} from 'react-bootstrap';

class App extends React.Component{
    constructor(){
        super();
        this.state={
            teaminfo:[],
            loadFinish:false,
            name:"TDW",
            width:200,
            expandedSector: null
        }
        this.componentWillMount = this.componentWillMount.bind(this)
        this.handleMouseEnterOnSector = this.handleMouseEnterOnSector.bind(this)

    }

    render(){

        var total = parseInt(this.state.teaminfo["3988"]) + parseInt(this.state.teaminfo["0001"]) + parseInt(this.state.teaminfo["0388"])
                    + parseInt(this.state.teaminfo["0005"]) + parseInt(this.state.teaminfo["0386"])

        var style = {color: 'red'};
        var value1 = parseInt(this.state.teaminfo["3988"])
        var value2 = parseInt(this.state.teaminfo["0001"])
        var value3 = parseInt(this.state.teaminfo["0388"])
        var value4 = parseInt(this.state.teaminfo["0005"])
        var value5 = parseInt(this.state.teaminfo["0386"])

        const margin = {top: 40, right: 40, bottom: 60, left: 80};

        var data = [
            {text:'3988', value:100 },
            {text:'0001', value:100 },
            {text:'0388', value:100 },
            {text:'0005', value:100 },
            {text:'0386', value:100 },

        ]

        const data2 = [
            {label: "3988", value: value1, color: "#EECD15"},
            {label: "0001", value: value2, color: "#00aced"},
            {label: "0388", value: value3, color: "#9215EE"},
            {label: "0005", value: value4, color: "#cb2027"},
            {label: "0386", value: value5, color: "#36C700"},
        ]

        const {expandedSector} = this.state

        return(
            <Grid id="root">
                <Row>
                    <Jumbotron>
                        <h2><b>Trading Men Team Summary</b></h2>
                        <p>2017 IT Internship Hackathon</p>
                    </Jumbotron>
                </Row>
                <Row className="content">
                    <Panel header="Trading Men" bsStyle="info">
                        <Row>
                            <Col xs={6} md={4}>
                                <Image width="200" height="200" src="/assets/spiderman.jpg" rounded />
                            </Col>
                            <Col xs={6} md={4}>
                                <Image width="200" height="200" src="/assets/batman.jpg" rounded />
                            </Col>

                            <Col xs={6} md={4}>
                            </Col>
                        </Row>
                    </Panel>
                </Row>
                <Row className="content">
                    <Panel header="Market Data" bsStyle="success">
                        <Table striped bordered condensed hover  >
                            <thead>
                            <tr>
                                <th>Instrument</th>
                                <th>Amount</th>

                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>3988</td>
                                <td>{this.state.teaminfo["3988"]}</td>

                            </tr>
                            <tr>
                                <td>0001</td>
                                <td>{this.state.teaminfo["0001"]}</td>

                            </tr>
                            <tr>
                                <td>0388</td>
                                <td>{this.state.teaminfo["0388"]}</td>
                            </tr>
                            <tr>
                                <td>0005</td>
                                <td>{this.state.teaminfo["0005"]}</td>
                            </tr>
                            <tr>
                                <td>0388</td>
                                <td>{this.state.teaminfo["0386"]}</td>
                            </tr>
                            <tr>
                                <td><b>Total</b></td>
                                <td><b style={style}>{total}</b></td>
                            </tr>
                            </tbody>
                        </Table>

                        <Table striped bordered condensed hover  >
                            <thead>
                            <tr>
                                <th>Instrument</th>
                                <th>Reserved Amount</th>

                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>3988 Reserved</td>
                                <td>{this.state.teaminfo["3988_reserved"]}</td>

                            </tr>
                            <tr>
                                <td>0001 Reserved</td>
                                <td>{this.state.teaminfo["0001_reserved"]}</td>

                            </tr>
                            <tr>
                                <td>0388 Reserved</td>
                                <td>{this.state.teaminfo["0388_reserved"]}</td>
                            </tr>
                            <tr>
                                <td>0005 Reserved</td>
                                <td>{this.state.teaminfo["0005_reserved"]}</td>
                            </tr>
                            <tr>
                                <td>0388 Reserved</td>
                                <td>{this.state.teaminfo["0388_reserved"]}</td>
                            </tr>
                            </tbody>
                        </Table>
                    </Panel>
                </Row>
                <Row className="content">
                    <Panel header="Graph" bsStyle="danger">
                        <div>
                            {
                                data2.map((element, i) => (
                                    <div key={i}>
                                        <span style={{background: element.color}}></span>
                                        <span style={{fontWeight: this.state.expandedSector === i ? "bold" : null}}>
                                {element.label} : {element.value}
                            </span>
                                    </div>
                                ))
                            }
                        </div>

                        <div className="dataPie">
                            <PieChart
                                data={ data2 }
                                expandedSector={expandedSector}
                                onSectorHover={this.handleMouseEnterOnSector}
                                sectorStrokeWidth={2}
                                expandOnHover
                                shrinkOnTouchEnd/>
                        </div>


                    </Panel>
                </Row>
            </Grid>
        );
    }

    componentWillMount(){
        this.getTeamData.then(result =>{
            console.log(result)
            console.log(result["0001"]);
            this.setState({teaminfo:result});
        })
    }

    getTeamData = new Promise((resolve, error) =>{
        axios.get("https://cis2017-teamtracker.herokuapp.com/api/teams/ZvFcySZQsiTY1QJc0mHJqw").then(function(response){
            resolve(response.data);
        }).catch(function(error){
            console.log(error)
        })

    })

    handleMouseEnterOnSector(sector) {
        this.setState({expandedSector: sector})
    }

}

export default App;