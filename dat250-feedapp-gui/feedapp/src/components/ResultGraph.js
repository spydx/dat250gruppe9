import React from "react";
import {CanvasJSChart} from "canvasjs-react-charts"

class ResultGraph extends React.Component {	
	render() {
		const options = {
                height:400,
                width:500,
                backgroundColor:"#F5EDEC",
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
                    name: "Yes",
					showInLegend: true,
					dataPoints: [
						{ y: 1, label: "Jan" },
						{ y: 2, label: "Feb" },
						{ y: 3, label: "Mar" },
						{ y: 4, label: "Apr" },
						{ y: 5, label: "May" },
						{ y: 10, label: "Jun" },
						{ y: 20, label: "Jul" },
						{ y: 22, label: "Aug" },
						{ y: 25, label: "Sept" },
						{ y: 26, label: "Oct" },
						{ y: 30, label: "Nov" },
						{ y: 54, label: "Dec" }
					]
				},
				{
					type: "spline",
					name: "No",
					showInLegend: true,
					dataPoints: [
						{ y: 2, label: "Jan" },
						{ y: 4, label: "Feb" },
						{ y: 6, label: "Mar" },
						{ y: 8, label: "Apr" },
						{ y: 10, label: "May" },
						{ y: 12, label: "Jun" },
						{ y: 14, label: "Jul" },
						{ y: 16, label: "Aug" },
						{ y: 18, label: "Sept" },
						{ y: 20, label: "Oct" },
						{ y: 22, label: "Nov" },
						{ y: 38, label: "Dec" }
					]
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