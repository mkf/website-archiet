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
				var tots = mom.grand_total.total_seconds;
				dalist.push([new Date(mom.range.date), {
					v: tots,
					f: tots.toHMS()
				}]);
			}
			console.log(dalist);
			var tabb = new google.visualization.DataTable();
			tabb.addColumn("date", "Date");
			tabb.addColumn("number", "Amount of time");
			tabb.addRows(dalist);
			var chart = new google.visualization.PieChart(document.getElementById("actiwaka"));
			chart.draw(tabb, {});
		},
		error: function(err) {
			console.log(err);
		}
	});
}

Number.prototype.toHMS = function() {
	var hours = Math.floor(this / 3600) < 10 ? ("00" + Math.floor(this / 3600)).slice(-2) : Math.floor(this / 3600);
	var minutes = ("00" + Math.floor((this % 3600) / 60)).slice(-2);
	var seconds = ("00" + (this % 3600) % 60).slice(-2);
	return hours + "h " + minutes + "m " + seconds + "s";
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
				title: "Languages over " + nasz.title + " (Powered by wakatime.com)",
				titleTextStyle: {
					color: "black",
					fontName: "Courier",
					fontSize: 13,
					bold: true,
				},
				legend: {
					position: "left",
					alignment: "center",
				},
				fontSize: 12,
				fontName: "Courier",
				pieSliceText: 'label',
				pieSliceTextStyle: {
					color: "#333333",
					fontSize: 10,
				},
				colors: ['#8dd3c7', '#ffffb3', '#bebada', '#fb8072', '#80b1d3', '#fdb462', '#b3de69', '#fccde5', '#d9d9d9', '#bc80bd', '#ccebc5', '#ffed6f'],
				pieSliceBorderColor: "#aaaaaa",
				pieHole: 0.5,
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
		title: "Last 7 Days",
		url: "wakawaka/7d.php"
	},
	"30d": {
		title: "Last 30 Days",
		url: "wakawaka/30d.php"
	},
	"alltime": {
		title: "All Time",
		url: "wakawaka/alltime.php"
	},
};

function odpalwakatime() {
	langues("7d");
	langues("30d");
	langues("alltime");
	monthtimes();
}
