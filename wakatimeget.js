google.charts.load('current', {
	'packages': ['corechart']
});
google.charts.setOnLoadCallback(odpalwakatime);

function monthtimes() {
	jQuery.ajax({
		url: activityurl,
		type: "GET",
		dataType: "json",
		complete: function() {},
		success: function(data) {
			console.log(data);
			var zaj = data.data;
			var dalist = [];
			dalist.push(["Date", "Seconds"]);
			for (var i = 0; i < data.data.length; i++) {
				var mom = zaj[i];
				dalist.push([zaj.range.text, zaj.grand_total.total_seconds]);
			}
			console.log(dalist);
			// var
		},
		error: function(err) {
			console.log(err);
		}
	});
}

function langues(id) {
	var nasz = about[id];
	jQuery.ajax({
		url: nasz.url,
		type: "GET",
		dataType: "json",
		complete: function() {},
		success: function(data) {
			console.log(data);
			var zaj = data.data;
			var dalist = [];
			dalist.push(["Name", "Percentage"]);
			for (var i = 0; i < data.data.length; i++) {
				var mom = zaj[i];
				dalist.push([mom.name + " (" + mom.percent + "%)", mom.percent]);
			}
			console.log(dalist);
			var chart = new google.visualization.PieChart(document.getElementById(id));
			chart.draw(google.visualization.arrayToDataTable(dalist), {
				title: nasz.title,
				legend: {
					position: "left",
					alignment: "center",
				},
				fontSize: 12,
				fintName: "Inconsolata",
				pieSliceText: 'label',
				colors: ['#8dd3c7', '#ffffb3', '#bebada', '#fb8072', '#80b1d3', '#fdb462', '#b3de69', '#fccde5', '#d9d9d9', '#bc80bd', '#ccebc5', '#ffed6f'],
				pieSliceBorderColor: "#aaaaaa",
				pieHole: 0.7,
				chartArea: {
					height: "100%",
					width: "100%",
					top: 0,
					left: 0,
				},
				sliceVisibilityThreshold: 0.0,
				tooltip: {
					text: "percentage",
				},
			});
		},
		error: function(err) {
			console.log(err);
		}
	});
}

var activityurl = "wakawaka/actiwaka.php";

var about = {
	"7d": {
		title: "Last 7 days",
		url: "wakawaka/7d.php"
	},
	"30d": {
		title: "Last 30 Days",
		url: "wakawaka/30d.php"
	},
	"alltime": {
		title: "All time",
		url: "wakawaka/alltime.php"
	},
};

function odpalwakatime() {
	langues("7d");
	langues("30d");
	langues("alltime");
	//monthtimes();
}
