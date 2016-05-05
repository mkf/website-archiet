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
			});
		},
		error: function(err) {
			console.log(err);
		}
	});
}

var activityurl = "https://wakatime.com/@ArchieT/5448ed1b-50cb-44d8-b8cd-e340ff35160d.json";

var about = {
	"7d": {
		title: "Last 7 days",
		url: "https://wakatime.com/@ArchieT/933c5129-04ca-4ddd-936c-312e55294fa5.json"
	},
	"30d": {
		title: "Last 30 Days",
		url: "https://wakatime.com/@ArchieT/773584a1-86e4-4a1c-a5e4-318db548e2f3.json"
	},
	"alltime": {
		title: "All time",
		url: "https://wakatime.com/@ArchieT/7f6feccc-b327-4c65-a3e0-e21c8fa45abe.json"
	},
};

function odpalwakatime() {
	langues("7d");
	langues("30d");
	langues("alltime");
	//monthtimes();
}
