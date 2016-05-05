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
			var dalist = [];
			for (var i = 0; i < data.data.length; i++) {
				data.push([data.data[i].range.text, data.data[i].grand_total.total_seconds]);
			}
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
			for (var i = 0; i < data.data.length; i++) {
				var mom = zaj[i];
				dalist.push([mom.name, mom.percent]);
			}
			console.log(dalist);
			var chart = new google.visualization.PieChart(document.getElementById(id));
			chart.draw(dalist, {
				title: nasz.title,
				legend: {
					position: "right",
				},
				pieSliceText: 'value',
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
