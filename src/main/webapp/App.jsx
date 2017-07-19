import React from 'react';
import axios from 'axios';

class App extends React.Component{
    constructor(props){
        super(props)
        this.state={

        }
        this.componentWillMount = this.componentWillMount.bind(this)

    }

    render(){
        return(
            <h1>Hello World</h1>
        );
    }

    componentWillMount(){
        this.getTeamData.then(result =>{
            console.log(result)
        })
    }

    getTeamData = new Promise((resolve, error) =>{
        axios.get("https://cis2017-teamtracker.herokuapp.com/api/teams/3dy93PQ9NP-TtABeab_C1w").then(function(response){
            debugger
            resolve(response.data.map(entity=>{
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