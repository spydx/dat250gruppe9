import React from "react";
import {CanvasJSChart} from "canvasjs-react-charts"
import moment from 'moment';

var yesDataPoints = [];
var noDataPoints = [];
const graphTimeFormat = 'MM YYYY, hh:mm';


class ResultGraph extends React.Component {	

	getAnswers() {
		var currentDate = null;
		var currentYes = 0;
		var currentNos  = 0;
		
		

		for (const element of this.props.votes) {
			var date = element.votetime;
			date = moment(date).format(graphTimeFormat);
			
			if(currentDate !== date) {
				if(currentDate !== null) {
					yesDataPoints.push({
						y: currentYes,
						label: currentDate
					})
					noDataPoints.push({
						y: currentNos,
						label: date
					})
				}

				currentDate = date; 
			}
			
			if(element.answer === true) {
				currentYes++;
			} else {
				currentNos++;
			}
		}

		yesDataPoints.push({
			y: currentYes,
			label: currentDate
		})
		noDataPoints.push({
			y: currentNos,
			label: date
		})
	}

	componentDidMount() {
		this.getAnswers()
		this.setState({})//This is a feature not a bug :)

	}

	render() {
		const yesAnswer = this.props.poll.answeryes
		const noAnswer = this.props.poll.answerno
		const options = {
                height:400,
                width:500,
                theme:"light2",
				animationEnabled: true,	
				title:{
					text: "Answers"
				},
				axisY : {
                    title: "Number of Yes/no"
				},
				toolTip: {
					shared: true
				},
				data: [{
					type: "spline",
                    name: yesAnswer,
					showInLegend: true,
					dataPoints: yesDataPoints
				},
				{
					type: "spline",
					name: noAnswer,
					showInLegend: true,
					dataPoints: noDataPoints
				}]
		}
		
		return (
		<div>
			<CanvasJSChart options = {options} 
				/* onRef={ref => this.chart = ref} */
			/>
			{/*You can get reference to the chart instance as shown above using onRef. This allows you to access all chart properties and methods*/}
		</div>
		);
	}
}

export default ResultGraph